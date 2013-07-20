
    // Unglitch Change Start
    /*public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        this.setBlockBoundsBasedOnState(par1World, par2, par3, par4);
        return super.getCollisionBoundingBoxFromPool(par1World, par2, par3, par4);
    }*/

    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        return this.getBlockBoundsBasedOnState(par1World, par2, par3, par4);
    }

    // --


    // Unglitch Start
    public AxisAlignedBB getBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        int meta = this.getFullMetadata(par1IBlockAccess, par2, par3, par4);

        float minX = 0.0f;
        float minY = 0.0f;
        float minZ = 0.0f;
        float maxX = 0.0f;
        float maxY = 0.0f;
        float maxZ = 0.0f;


        // from setDoorRotation
        float var2 = 0.1875F;
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F);
        int var3 = meta & 3;
        boolean var4 = (meta & 4) != 0;
        boolean var5 = (meta & 16) != 0;

        if (var3 == 0)
        {
            if (var4)
            {
                if (!var5)
                {
                    //this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, var2);
                    maxX = 1.0f;
                    maxY = 1.0f;
                    maxZ = var2;
                }
                else
                {
                    //this.setBlockBounds(0.0F, 0.0F, 1.0F - var2, 1.0F, 1.0F, 1.0F);
                    minZ = 1.0f - var2;
                    maxX = 1.0f;
                    maxY = 1.0f;
                    maxZ = 1.0f;
                }
            }
            else
            {
                //this.setBlockBounds(0.0F, 0.0F, 0.0F, var2, 1.0F, 1.0F);
                maxX = var2;
                maxY = 1.0f;
                maxZ = 1.0f;
            }
        }
        else if (var3 == 1)
        {
            if (var4)
            {
                if (!var5)
                {
                    //this.setBlockBounds(1.0F - var2, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
                    minX = 1.0f - var2;
                    maxX = 1.0f;
                    maxY = 1.0f;
                    maxZ = 1.0f;
                }
                else
                {
                    //this.setBlockBounds(0.0F, 0.0F, 0.0F, var2, 1.0F, 1.0F);
                    maxX = var2;
                    maxY = 1.0f;
                    maxZ = 1.0f;
                }
            }
            else
            {
                //this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, var2);
                maxX = 1.0f;
                maxY = 1.0f;
                maxZ = var2;
            }
        }
        else if (var3 == 2)
        {
            if (var4)
            {
                if (!var5)
                {
                    //this.setBlockBounds(0.0F, 0.0F, 1.0F - var2, 1.0F, 1.0F, 1.0F);
                    minZ = 1.0f - var2;
                    maxX = 1.0f;
                    maxY = 1.0f;
                    maxZ = 1.0f;
                }
                else
                {
                    //this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, var2);
                    maxX = 1.0f;
                    maxY = 1.0f;
                    maxZ = var2;
                }
            }
            else
            {
                //this.setBlockBounds(1.0F - var2, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
                minX = 1.0f - var2;
                maxX = 1.0f;
                maxY = 1.0f;
                maxZ = 1.0f;
            }
        }
        else if (var3 == 3)
        {
            if (var4)
            {
                if (!var5)
                {
                    //this.setBlockBounds(0.0F, 0.0F, 0.0F, var2, 1.0F, 1.0F);
                    maxX = var2;
                    maxY = 1.0f;
                    maxZ = 1.0f;
                }
                else
                {
                    //this.setBlockBounds(1.0F - var2, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
                    minX = 1.0f - var2;
                    maxX = 1.0f;
                    maxY = 1.0f;
                    maxZ = 1.0f;
                }
            }
            else
            {
                //this.setBlockBounds(0.0F, 0.0F, 1.0F - var2, 1.0F, 1.0F, 1.0F);
                minZ = 1.0f - var2;
                maxX = 1.0f;
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

    // --

