#  MC-2025 Fix (Unglitch) by taurose #
Unglitch is a Minecraft mod to stop entities from (permanently or temporarily) glitching through walls. See [https://mojang.atlassian.net/browse/MC-2025](https://mojang.atlassian.net/browse/MC-2025) and [https://mojang.atlassian.net/browse/MC-10](https://mojang.atlassian.net/browse/MC-10).

Besides the mod download, the repo also includes relevant source code snippets (search for "Unglitch" to quickly find changes in larger files), and some test worlds.

Here's a rather technical description of the issues I've found and how I solved them in the mod: 


## 1) Race Condition (Client vs. Server Thread) ##

Entities are sometimes able to escape fenced areas by "glitching" through blocks during normal gameplay. This can only happen if an **integrated server** is used (single player, or opened to LAN) and only with specific blocks. The more entities collide with a certain block, the likelier it is for them to escape.

 
### The Cause ###
When Minecraft checks the collision of entities with blocks, it first collects all collision bounding boxes that could possibly intersect with the entity in a list. In many cases, these bounding boxes vary depending on the metadata  of the block itself (e.g., hoppers) or depending on the block id of adjacent blocks (e.g., fences) and have to be calculated at runtime.

The problem is that some blocks store the calculated bounds of the collision boxes within their global Block object first, before they later read these values again to add them to the list of bounding boxes.

For example, here's a snippet from BlockFence, which adds its current collision boxes (up to two) to the list: 

	public void addCollisionBoxesToList(...){
		...
	
		if(...){
			// create and add north-south piece
			this.setBlockBounds(var12, 0.0F, var14, var13, 1.5F, var15);
			super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);    
		}
	
		...
		
		if(...){
            // create and add east-west or center piece (isolated fence)
	        this.setBlockBounds(var12, 0.0F, var14, var13, 1.5F, var15);
	        super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
		}
	
		...
	}


The method **this.setBlockBounds** stores the boundaries that are passed as parameters to the object (this.minX, this.minY, this.minZ, this.maxX, this.maxY, this.maxZ), and **super.addCollisionBoxesToList** reads these values to construct a (Axis Aligned) Bounding Box object which it then adds to the list.

This wouldn't be a problem if there was only one thread that executed this method. However, with an integrated server, both the client and the server call the same method on the same object for collision checks. So the following sequence is an example of what can cause glitches:

	CLIENT THREAD: setBlockBounds(...) // set north-south piece
	SERVER THREAD: setBlockBounds(...) // set east-west piece
	SERVER THREAD: addCollisionBoxesToList(...) // east-west piece is added to list (correct)
	CLIENT THREAD: addCollisionBoxesToList(...) // east-west piece is added to list (NOT CORRECT)

So ultimately, wrong collision bounding boxes can be passed to collision check. For example, a normal fence could be treated as a corner fence by the collision check, allowing entities to glitch inside its boundaries, and causing further collision checks to ignore the block entirely (since the entity is already inside of it).


I've found the following blocks to be affected in a similar manner:

- Brewing Stand
- Cauldron
- Cocoa
- Door
- End Portal Frame
- Fence
- Half Slab
- Hopper
- Ladder
- Panes
- Piston Base
- Piston Extension
- Skull
- Stairs
- Trap Door
- Wall



### The Solution ###
To my knowledge, there are several possible solutions to fix this. One solution could be  to synchronize the threads, so that they won't collect the collision boxes of the same block at the same type, or use two different block objects for client and server. However, I wasn't able to find a solution that wouldn't involve lots of code changes; so, for the mod, I decided to rewrite the methods to not call *setBlockBounds* anymore, and instead construct the bounding box object directly with the calculated values. In my opinion, this is the cleanest solution.


## 2) Floating Point Errors on Entity Load ##

Entities can sometimes also glitch trough *any* block on chunk load (when they are read from NBT). This should affect pretty much any environment (client and server). It seems to happen most often when the X or Z coordinate is close to a power of two, or when an entity is moving upwards.

### The Cause ###
Let me start by saying that I'm no expert when it comes to floating point arithmetics, so there might be more to the issue than what I found out.

Anyways, here's what I think is happening:
In Minecraft, every entity has two ways to describe their positions:

- this.posX / this.posY / this.posZ: the center of the entity (min coordinate for y)
- this.boundingBox: contains minimum and maximum coordinates of the entity's bounding box (minX, minY, minZ, maxX, ..)

Collision checks (happen every game tick) are done using only the bounding box. And once they are finished, the bounding box values are applied back to the posX/posY/posZ fields to keep everything in sync. When an entity is saved, only the posX, posY and posZ attributes are saved, and when it is loaded again, the bounding box will be recreated from these values. 
    
The problem I found with this, is that under rare circumstances, the recreated bounding box differs from the original one. For example, if there a was a full solid block from x=0.0 to x=1.0, and an entity with a width=1.0 would collide with it, the x-dimension of its bounding box could end up like this (fictional example with arbitrary values):

	minX=1.000001
	maxX=1.999998 

The center could then be calculated as

	posX=1.49998

which may be leading to a recreated bounding box of

	minX=0.99999
	maxX=1.99998

During the collision check, Minecraft will consider the entity as being inside the block (because 0.99999 is smaller than 1.0) and ignore any collision between them.



### The Solution ###

Again, there might be a couple of valid solutions. Though from what I know about floating point arithmetics, such errors are bound to happen. A common way to prevent errors caused by this is to use an epsilon value for comparisons. So I replaced the comparisons responsible for determining whether a collision box blocks the attempted movement of an entity with a method described here: [http://www.cygnus-software.com/papers/comparingfloats/comparingfloats.htm](http://www.cygnus-software.com/papers/comparingfloats/comparingfloats.htm)


This way, entities which are barely inside of blocks won't be able to move further inside and will be pushed out instead. By comparing the integer values, the accepted error range scales with the magnitude of possible rounding errors. However, even for coordinates at 10M, the range will be below a millionth, so there should be little side effects on game play.


## 3) Entity Position Synchronization Issues

In the current *EntityTrackerEntry* implementation (the class that sends position updates of entities) there are several logical bugs that may cause the clients' and server's entity positions to go out of sync. The client-side misplacements result in glitches, which are usually resolved with the next (relative or absolute) position update. I've found the following errors:

* when an absolute position update is sent (*Packet34EntityTeleport*), the server does not always save the entity's position (for subsequent relative position updates)
* when no packet is sent during the first (attempted) update, the server *does* save the position
* the spawn packets contain the entity's current position rather than the one saved in the tracker object

Interestingly, this also fixes [https://mojang.atlassian.net/browse/MC-19331](https://mojang.atlassian.net/browse/MC-19331 "MC-19331"). That's because when placing a minecart on a slope, the server first sends the lower position (as if it was placed on flat rails) in the spawn packet, and then attempts to update the heightened position, which is, however, canceled (first update), yet the position is saved as if it was sent to the clients.


## 4) Adjusting the Position Received from Server
First off, I guess this is not really a bug requiring a fix, but rather a limitation due to minecraft updating positions in ints and bytes rather than doubles (for performance reasons), which requires a decent workaround. In the end, the issue boils down to received positions being off by up to 1/32, which leads to massive visual glitches if not adjusted in some way.

There's already a server-side workaround in place which is working quite well, especially with the other issues mentioned here being solved. However, in some set-ups, it works very poorly, so I decided to come up with my own, client-side workaround which works by moving the entity from its maximally possible position (all coordinates +1/32) back to the original position (true position rounded towards negative infinity by the server).


## 5) Miscellaneous
* When the client receives a spawn packet of a baby animal, their reduced size is applied after setting the spawn position, which can cause temporary glitches until the first position update  
* I've also implemented a tiny workaround to the client not considering the swimming capabilities of some mobs, which could falsely make them appear to be sinking.


## Remaining Issues
Unfortunately, there are still several scenarios which may still cause temporary client-side glitches, but I hope that all permanent ones are solved with this.

* when starting a game (or teleporting), some mobs may glitch out for a few seconds, because not all adjacents chunks have been received before the (client-side) mob spawn
* an entity barely standing on a cliff can appear to repeatedly fall down client-side
* spiders may appear to fall down even though their climbing skills keep them in the air 
* probably more


## Licensing ##

The original source code of Minecraft belongs to [https://mojang.com/](https://mojang.com/ "Mojang"). I make no claim of ownership to any of the source code modifications shown in this repository. 
	
[![githalytics.com alpha](https://cruel-carlota.gopagoda.com/0ad0fa5d8733c89cd292d40d3f252c3f "githalytics.com")](http://githalytics.com/taurose/Unglitch)