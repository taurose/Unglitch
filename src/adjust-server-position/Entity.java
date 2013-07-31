
    public void setPositionAndRotation2(double par1, double par3, double par5, float par7, float par8, int par9)
    {
        this.setPosition(par1, par3, par5);
        this.setRotation(par7, par8);

        // Unglitch Start - remove old workaround
        /*
        List var10 = this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox.contract(0.03125D, 0.0D, 0.03125D));

        if (!var10.isEmpty())
        {
            double var11 = 0.0D;

            for (int var13 = 0; var13 < var10.size(); ++var13)
            {
                AxisAlignedBB var14 = (AxisAlignedBB)var10.get(var13);

                if (var14.maxY > var11)
                {
                    var11 = var14.maxY;
                }
            }

            par3 += var11 - this.boundingBox.minY;
            this.setPosition(par1, par3, par5);
        } */
        // --
    }

    /**
     * Takes a position (x, y, z) and returns a collision-free position (if possible) whereas each coordinate is at least
     * as big as the original and at most increased by 1/16, with a tendency to return low values (e.g. when
     * there are no collisions)
     */
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
        if(worldObj.getCollidingBoundingBoxes(this, bb).size() < 1){
            // no collision anyway - we're fine
            return new double[]{x, y, z};
        }

        // offset to max server position
        bb.offset(d, d, d);

        double moveX = -d;
        double moveY = -d;
        double moveZ = -d;

        // get all possible collisions
        AxisAlignedBB maxBB = bb.addCoord(moveX, moveY, moveZ);
        List collisions = worldObj.getCollidingBoundingBoxes(this, maxBB);


        // move entity back to minimum position (with collision detection)
        // prevents collisions at low coordinates and moves out of collisions at high coordinates
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

        // return resulting position
        return new double[]{(bb.maxX + bb.minX) / 2d, bb.minY + (double)this.yOffset - (double)this.ySize, (bb.maxZ + bb.minZ) / 2d};

    }

    /**
     * Teleports the entity to its last known server position, also moving it out of collisions (if possible).
     * Currently only used for entities ridden by the player shortly after starting the game.
     */
    public void readjustServerPosition(){
        double[] adj = this.adjustServerPosition(this.serverPosX / 32.0d, this.serverPosY / 32.0d, this.serverPosZ / 32.0d);
        this.setPosition(adj[0], adj[1], adj[2]);
    }