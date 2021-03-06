#  Unglitch: MC-2025 fix (and more) #
Unglitch is a Minecraft mod to stop entities from (permanently or temporarily) glitching through walls. See [https://mojang.atlassian.net/browse/MC-2025](https://mojang.atlassian.net/browse/MC-2025) and [https://mojang.atlassian.net/browse/MC-10](https://mojang.atlassian.net/browse/MC-10). It also fixes some other glitches related to entities.

Also see:

- [http://chunkbase.com/mods/unglitch](http://chunkbase.com/mods/unglitch)
- [http://www.minecraftforum.net/topic/1920678-162-unglitch-fix-for-escaping-animals-and-more/](http://www.minecraftforum.net/topic/1920678-162-unglitch-fix-for-escaping-animals-and-more/)

This repo includes most of the relevant source code snippets (search for "Unglitch" to quickly find changes in larger files), and some test worlds.

Here's a rather technical description of the issues I've found and how I solved them in the mod: 


## 1) Race Condition (Client vs. Server Thread) ##

Entities are sometimes able to escape fenced areas by "glitching" through blocks during normal gameplay. This can only happen if an **integrated server** is used (single player, or opened to LAN) and only with specific blocks. The more entities collide with a certain block, the likelier it is for them to escape. There are also other parts of the game that are affected by the same underlying bug.

 
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
To my knowledge, there are several possible solutions to fix this. One solution could be  to synchronize the threads, so that they won't collect the collision boxes of the same block at the same type, or use two different block (bounds) objects for client and server. However, I wasn't able to find a solution that wouldn't involve lots of code changes and refactoring; so, for the mod, I decided to implement [Daniel King's fix](https://docs.google.com/file/d/0B89TxHEJMb4BYXdYQVpNaVZjNW8/edit) for this issue ([source](https://docs.google.com/file/d/0B89TxHEJMb4BYXdYQVpNaVZjNW8/edit)), which fixes all related bugs (possibly at a slight a performance cost). 



## 2) Wrong Collision Bounding Boxes (Chests, Anvils)

Minecraft often uses a wrong collision bounding box for chest and anvils, which leads to players being pushed away from them and NPCs being able to walk through them (see [MC-1635](https://mojang.atlassian.net/browse/MC-1635) and [MC-1669](https://mojang.atlassian.net/browse/MC-1669)). A demonstration can be found here: [http://www.youtube.com/watch?v=y1kon5Wax8I](http://www.youtube.com/watch?v=y1kon5Wax8I), although it seems to only affect anvils and chests now.

This can easily be fixed by adding (overriding) the following method to BlockAnvil and BlockChest:

	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
	    this.setBlockBoundsBasedOnState(par1World, par2, par3, par4);
	    return super.getCollisionBoundingBoxFromPool(par1World, par2, par3, par4);
	}

This ensures that the bounding box is updated before collision detection. The same method is also used for other blocks, like trap doors. It is, however, susceptible to the race condition mentioned in 1). The race condition fix used for the mod is able to account for that though.



## 3) Floating Point Errors on Entity Load ##

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


## 4) Entity Position Synchronization Issues


1. In the current *EntityTrackerEntry* implementation (the class that sends position updates of entities) there are several logical bugs that may cause the clients' and server's entity positions to go out of sync. The client-side misplacements result in glitches, which are usually resolved with the next (relative or absolute) position update. I've found the following errors:

	1. when an absolute position update is sent (*Packet34EntityTeleport*), the server does not always save the entity's position (for subsequent relative position updates)
    2. when no packet is sent during the first (attempted) update, the server *does* save the position
    3. the spawn packets contain the entity's current position rather than the one saved in the tracker object

    Interestingly, this also fixes [https://mojang.atlassian.net/browse/MC-19331](https://mojang.atlassian.net/browse/MC-19331 "MC-19331"). That's because when placing a minecart on a slope, the server first sends the lower position (as if it was placed on flat rails) in the spawn packet, and then attempts to update the heightened position, which is, however, canceled (first update), yet the position is saved as if it was sent to the clients.

2. When the client spawns slimes or ageable animals, their real size is set too late, which can lead to temporary visual glitches after the first tick.

3. XP orbs are spawning client-side at a multiple of their server position (32x), essentially rendering them invisible for the first few seconds. See [MC-12013](https://mojang.atlassian.net/browse/MC-12013).   


## 5) Adjusting the Position Received from Server
First off, I'd say this is not really a bug requiring a fix, but rather a limitation due to minecraft updating positions in ints and bytes rather than doubles (for performance reasons), which requires a decent workaround. In the end, the issue boils down to received positions being off by up to 1/32, which leads to massive visual glitches if not adjusted in some way.

There's already a server-side workaround in place which is working quite well, especially with the other issues mentioned here being solved. However, in some set-ups, it works very poorly, so I decided to come up with my own, client-side workaround which works by moving the entity from its maximally possible position (all coordinates +1/32) back to the original position (real position rounded towards negative infinity by the server).



## 6) Miscellaneous

* **Swimmers**: A tiny workaround for the client not considering the swimming capabilities of some mobs, which could falsely make them appear to be sinking
* **Spiders**: A client-side workaround to fix spiders falsely appearing to fall down
* **Boats**: Added collision check to client-side boats when they are not ridden by the player (fixes [MC-3441](https://mojang.atlassian.net/browse/MC-3441)) and added minimal gravity to make them update their onGround flag (reduces the "jerky" movements of boats on the ground).
* **XP Orbs**: Currently, the size of xp orbs changes from 0.5 to 0.25 after restarting the server/game, and it's always 0.5 client-side. I simply changed it to be 0.25 in all cases.
* **XP Orbs & Items**: Changed the pushOutOfBlocks method to be called server-side since it seems to be causing client/server discrepancies.

## Remaining Issues?
Unfortunately, there are several scenarios which may still cause temporary client-side glitches, but I hope that the annoying and permanent ones are solved with this.
