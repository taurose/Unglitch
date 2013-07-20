
    public double[] adjustServerPosition(double x, double y, double z){
        if(this.noClip){
            return new double[]{x, y, z};
        }

        double d = 1d/32d;

        if(this instanceof EntityLivingBase){
            this.yOffset = 0;   // might not be needed
        }

        // construct bounding box at min coordinates
        float halfWidth = this.width / 2.0F;
        AxisAlignedBB bb = AxisAlignedBB.getAABBPool().getAABB(
                x - (double)halfWidth,
                y - (double)this.yOffset + (double)this.ySize,
                z - (double)halfWidth,
                x + (double)halfWidth,
                y - (double)this.yOffset + (double)this.ySize + (double) this.height,
                z + (double)halfWidth);

        // optimization
        if(worldObj.getCollidingBlockBounds(bb).size() < 1){
            // no collision
            return new double[]{x, y, z};
        }

        // offset to max server position
        bb.offset(d, d, d);

        double moveX = -d;
        double moveY = -d;
        double moveZ = -d;

        // get all possible collisions
        AxisAlignedBB maxBB = bb.addCoord(moveX, moveY, moveZ);
        List collisions = worldObj.getCollidingBlockBounds(maxBB);


        // move entity back to minimum position
        for(Object c: collisions){
            moveY = ((AxisAlignedBB) c).calculateYOffset(bb, moveY);
        }
        bb.offset(0d, moveY, 0d);

        for(Object c: collisions){
            moveX = ((AxisAlignedBB) c).calculateXOffset(bb, moveX);
        }
        bb.offset(moveX, 0d, 0d);

        for(Object c: collisions){
            moveZ = ((AxisAlignedBB) c).calculateZOffset(bb, moveZ);
        }
        bb.offset(0d, 0d, moveZ);

        return new double[]{(bb.maxX + bb.minX) / 2d, bb.minY + (double)this.yOffset - (double)this.ySize, (bb.maxZ + bb.minZ) / 2d};

    }
