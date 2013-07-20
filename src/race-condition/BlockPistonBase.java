
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        // Unglitch Change Start
        //this.setBlockBoundsBasedOnState(par1World, par2, par3, par4);
        //return super.getCollisionBoundingBoxFromPool(par1World, par2, par3, par4);

        float minX = 0.0f;
        float minY = 0.0f;
        float minZ = 0.0f;
        float maxX = 0.0f;
        float maxY = 0.0f;
        float maxZ = 0.0f;

        // from setBlockBoundsBasedOnState
        int var5 = par1World.getBlockMetadata(par2, par3, par4);

        if (isExtended(var5))
        {
            switch (getOrientation(var5))
            {
                case 0:
                    //this.setBlockBounds(0.0F, 0.25F, 0.0F, 1.0F, 1.0F, 1.0F);
                    minY = 0.25f;
                    maxX = 1.0f;
                    maxY = 1.0f;
                    maxZ = 1.0f;
                    break;

                case 1:
                    //this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.75F, 1.0F);
                    maxX = 1.0f;
                    maxY = 0.75f;
                    maxZ = 1.0f;
                    break;

                case 2:
                    //this.setBlockBounds(0.0F, 0.0F, 0.25F, 1.0F, 1.0F, 1.0F);
                    minZ = 0.25f;
                    maxX = 1.0f;
                    maxY = 1.0f;
                    maxZ = 1.0f;
                    break;

                case 3:
                    //this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.75F);
                    maxX = 1.0f;
                    maxY = 1.0f;
                    maxZ = 0.75f;
                    break;

                case 4:
                    //this.setBlockBounds(0.25F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
                    minX = 0.25f;
                    maxX = 1.0f;
                    maxY = 1.0f;
                    maxZ = 1.0f;
                    break;

                case 5:
                    //this.setBlockBounds(0.0F, 0.0F, 0.0F, 0.75F, 1.0F, 1.0F);
                    maxX = 0.75f;
                    maxY = 1.0f;
                    maxZ = 1.0f;
            }
        }
        else
        {
            //this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            maxX = 1.0f;
            maxY = 1.0f;
            maxZ = 1.0f;
        }

        // from getCollisionBoundingBoxFromPool
        return AxisAlignedBB.getAABBPool().getAABB((double)par2 + minX, (double)par3 + minY, (double)par4 + minZ, (double)par2 + maxX, (double)par3 + maxY, (double)par4 + maxZ);

        // --
    }

