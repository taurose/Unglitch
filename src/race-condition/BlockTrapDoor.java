
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
        int meta = par1IBlockAccess.getBlockMetadata(par2, par3, par4);

        float minX = 0.0f;
        float minY = 0.0f;
        float minZ = 0.0f;
        float maxX = 0.0f;
        float maxY = 0.0f;
        float maxZ = 0.0f;

        // from setBlockBoundsForBlockRender
        float var2 = 0.1875F;

        if ((meta & 8) != 0)
        {
            //this.setBlockBounds(0.0F, 1.0F - var2, 0.0F, 1.0F, 1.0F, 1.0F);
            minY = 1.0f - var2;
            maxX = 1.0f;
            maxY = 1.0f;
            maxZ = 1.0f;
        }
        else
        {
            //this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, var2, 1.0F);
            maxX = 1.0f;
            maxY = var2;
            maxZ = 1.0f;
        }

        if (isTrapdoorOpen(meta))
        {
            if ((meta & 3) == 0)
            {
                //this.setBlockBounds(0.0F, 0.0F, 1.0F - var2, 1.0F, 1.0F, 1.0F);
                minZ = 1.0f - var2;
                maxX = 1.0f;
                maxY = 1.0f;
                maxZ = 1.0f;
            }

            if ((meta & 3) == 1)
            {
                //this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, var2);
                maxX = 1.0f;
                maxY = 1.0f;
                maxZ = var2;
            }

            if ((meta & 3) == 2)
            {
                //this.setBlockBounds(1.0F - var2, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
                minX = 1.0f - var2;
                maxX = 1.0f;
                maxY = 1.0f;
                maxZ = 1.0f;
            }

            if ((meta & 3) == 3)
            {
                //this.setBlockBounds(0.0F, 0.0F, 0.0F, var2, 1.0F, 1.0F);
                maxX = var2;
                maxY = 1.0f;
                maxZ = 1.0f;
            }
        }

        return AxisAlignedBB.getAABBPool().getAABB(	(double)par2 + (double)minX,
                (double)par3 + (double)minY,
                (double)par4 + (double)minZ,
                (double)par2 + (double)maxX,
                (double)par3 + (double)maxY,
                (double)par4 + (double)maxZ);

    }

