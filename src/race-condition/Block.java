
    // Unglitch Start
    /**
     *  Works like addCollisionBoxesToList. Instead of using the boundaries stored in the block object, the boundaries
     *  are directly passed by parameter.
     */
    public void addCollisionBoxToListWithBounds(World world, int blockX, int blockY, int blockZ, AxisAlignedBB otherAABB, List list, Entity entity, float minX, float minY, float minZ, float maxX, float maxY, float maxZ)
    {
        AxisAlignedBB myAABB = AxisAlignedBB.getAABBPool().getAABB(	(double)blockX + (double)minX,
                (double)blockY + (double)minY,
                (double)blockZ + (double)minZ,
                (double)blockX + (double)maxX,
                (double)blockY + (double)maxY,
                (double)blockZ + (double)maxZ);

        if(myAABB != null && otherAABB.intersectsWith(myAABB))
        {
            list.add(myAABB);
        }
    }
    // --

