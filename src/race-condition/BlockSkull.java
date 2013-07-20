
    /*public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        this.setBlockBoundsBasedOnState(par1World, par2, par3, par4);
        return super.getCollisionBoundingBoxFromPool(par1World, par2, par3, par4);
    }*/

    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        return this.getBlockBoundsBasedOnState(par1World, par2, par3, par4);
    }


    public AxisAlignedBB getBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        int var5 = par1IBlockAccess.getBlockMetadata(par2, par3, par4) & 7;

        float minX = 0.0f;
        float minY = 0.0f;
        float minZ = 0.0f;
        float maxX = 0.0f;
        float maxY = 0.0f;
        float maxZ = 0.0f;

        // from setBlockBoundsBasedOnState
        switch (var5)
        {
            case 1:
            default:
                //this.setBlockBounds(0.25F, 0.0F, 0.25F, 0.75F, 0.5F, 0.75F);
                minX = 0.25f;
                minZ = 0.25f;
                maxX = 0.75f;
                maxY = 0.5f;
                maxZ = 0.75f;
                break;

            case 2:
                //this.setBlockBounds(0.25F, 0.25F, 0.5F, 0.75F, 0.75F, 1.0F);
                minX = 0.25f;
                minY = 0.25f;
                minZ = 0.5f;
                maxX = 0.75f;
                maxY = 0.75f;
                maxZ = 1.0f;
                break;

            case 3:
                //this.setBlockBounds(0.25F, 0.25F, 0.0F, 0.75F, 0.75F, 0.5F);
                minX = 0.25f;
                minY = 0.25f;
                maxX = 0.75f;
                maxY = 0.75f;
                maxZ = 0.5f;
                break;

            case 4:
                //this.setBlockBounds(0.5F, 0.25F, 0.25F, 1.0F, 0.75F, 0.75F);
                minX = 0.5f;
                minY = 0.25f;
                minZ = 0.25f;
                maxX = 1.0f;
                maxY = 0.75f;
                maxZ = 0.75f;
                break;

            case 5:
                //this.setBlockBounds(0.0F, 0.25F, 0.25F, 0.5F, 0.75F, 0.75F);
                minY = 0.25f;
                minZ = 0.25f;
                maxX = 0.5f;
                maxY = 0.75f;
                maxZ = 0.75f;
        }

        return AxisAlignedBB.getAABBPool().getAABB(	(double)par2 + (double)minX,
                (double)par3 + (double)minY,
                (double)par4 + (double)minZ,
                (double)par2 + (double)maxX,
                (double)par3 + (double)maxY,
                (double)par4 + (double)maxZ);
    }
