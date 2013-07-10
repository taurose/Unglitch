#  MC-2025 Fix (Unglitch) by taurose #
Unglitch is a Minecraft mod to stop entities from permanently glitching through walls. See [https://mojang.atlassian.net/browse/MC-2025](https://mojang.atlassian.net/browse/MC-2025).
These glitches were most noticable in the past few versions with baby animals. Although this particular bug has been fixed in 1.6.2, there are (at least) two remaining bugs leading to the same issue.

Besides the mod download, there's also the source code (search for "Unglitch" to quickly find the changes), and two of my test worlds (on for each bug) in this repository.

And here's a rather technical description of the two issues I've found and how I solved them in the mod: 


## 1) Race Condition (Client vs. Server Thread) ##

Entities are sometimes able to escape fenced areas by "glitching" through blocks (happens during normal gameplay). This can only happen if an integrated server is used (single player, or opened to LAN) and only with specific blocks. The more entities there are, the likelier it is for them to escape.

 
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

For example, the new code for BlockFence now looks like this:

	public void addCollisionBoxesToList(...){
		...
	
		if(...){
			// create and add north-south piece
			this.addCollisionBoxToListWithBounds(...);  
		}
	
		...
		
		if(...){
            // create and add east-west or center piece (isolated fence)
	        this.addCollisionBoxToListWithBounds(...);
		}
	
		...
	}

I added the method *addCollisionBoxToListWithBounds*, which acts as a replacement for *setBlockBounds* and *super.addCollisionBoxesToList* that does not store any values within the block object.  

In other cases, the original, problematic methods looked like this:

	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        this.setBlockBoundsBasedOnState(par1World, par2, par3, par4);
        return super.getCollisionBoundingBoxFromPool(par1World, par2, par3, par4);
    }

To solve these cases, I duplicated the *setBlockBoundsBasedOnState* method (which calls *setBlockBounds*) to create a *getBlockBoundsBasedOnState* method which  directly returns the bounding box. Now, the code above could be changed to this:

    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        return this.getBlockBoundsBasedOnState(par1World, par2, par3, par4);
    }


There were also a few special cases which had to be modified slightly differently. As I mentioned before, the goal for all blocks was to eliminate all *setBlockBounds* calls which are used to construct a collision bounding box.



## 2) Floating Point Errors on Entity Load ##

Entities can sometimes also glitch trough *any* block on chunk load (when they are read from NBT). This should affect pretty much any environment (client and server). I first encountered this issue with only a few iron golems enclosed within glass walls (2x2 horizontal space). It took me alot of attempts to reproduce this issue, but ultimately I was able to create a save file that causes the glitch to occur on load.

### The Cause ###
Let me start by saying that I'm no expert when it comes to floating point arithmetics, so there might be more to the issue than what I found out.

Anyways, here's what I think is happening:
In Minecraft, every entity has two ways to describe their positions:

- this.posX / this.posY / this.posZ: the center of the entity
- this.boundingBox: contains minimum and maximum coordinates of the entity's bounding box (minX, minY, minZ, maxX, ..)

Collision checks (happen every game tick) are done using only the bounding box. And once they are finished, the bounding box values are applied back to the posX/posY/posZ fields to keep everything in sync:

	this.posX = (this.boundingBox.minX + this.boundingBox.maxX) / 2.0D;
    this.posY = this.boundingBox.minY + (double)this.yOffset - (double)this.ySize;
    this.posZ = (this.boundingBox.minZ + this.boundingBox.maxZ) / 2.0D;

When an entity is saved, only the posX, posY and posZ attributes are saved. To recreate the bounding box after reloading the entity, the following code is executed:

    float var7 = this.width / 2.0F;
    float var8 = this.height;
    this.boundingBox.setBounds(this.posX - (double)var7, 
								this.posY - (double)this.yOffset + (double)this.ySize, 
								this.posZ - (double)var7, 
								this.posX + (double)var7, 
								this.posY - (double)this.yOffset + (double)this.ySize + (double)var8, 
								this.posZ + (double)var7);
    
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

Again, there might be a couple of valid solutions. Though from what I know about floating point arithmetics, such errors are bound to happen. A common way to prevent errors caused by this is to use an epsilon value for comparisons. So I modified the methods responsible for determining whether a collision box blocks the attempted movement of an entity. The same methods are also responsible for reducing the movement vector accordingly.

Here's a (refactored) snippet from the original (MCP) source code of AxisAlignedBoundingBox->calculateXOffset:

	
    if (moveX > 0.0D)  // entity attempts to move east
    {
		// smallest distance between bounding boxes of block and entity
		// can be slightly smaller than 0 in case of floating point errors
        distanceX = this.minX - entityBB.maxX;	

        if (distanceX >= 0 && distanceX < moveX) 
        {
			// reduce movement of entity to block bounds
            moveX = distanceX;
        }
    }

	if(moveX < 0.0D) // entity attempts to move west
	{
		...
	}

The modded source code looks like this:

	if (moveX > 0.0D)
    {
        distanceX = this.minX - entityBB.maxX;
		double eps = 5 * Math.ulp(this.minX);
	
        if (distanceX >= -eps && distanceX < moveX)
        {
            moveX = Math.max(0, distanceX);
        }
    }

	if(moveX < 0.0D) // entity attempts to move west
	{
		...
	}

This way, entities which are barely inside of blocks won't be able to move further inside. By using Math.ulp, the epsilon number scales with the magnitude of possible rounding errors.



## Licensing ##

The original source code of Minecraft belongs to [https://mojang.com/](https://mojang.com/ "Mojang"). I make no claim of ownership to any of the source code modifications shown in this repository. 
	
