

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
        float minX = 0.0f;
        float minY = 0.0f;
        float minZ = 0.0f;
        float maxX = 0.0f;
        float maxY = 0.0f;
        float maxZ = 0.0f;

        // from setBlockBoundsBasedOnState
        int var5 = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
        int var6 = getDirection(var5);
        int var7 = func_72219_c(var5);
        int var8 = 4 + var7 * 2;
        int var9 = 5 + var7 * 2;
        float var10 = (float)var8 / 2.0F;

        switch (var6)
        {
            case 0:
                //this.setBlockBounds((8.0F - var10) / 16.0F, (12.0F - (float)var9) / 16.0F, (15.0F - (float)var8) / 16.0F, (8.0F + var10) / 16.0F, 0.75F, 0.9375F);
                minX = (8.0F - var10) / 16.0F;
                minY = (12.0F - (float)var9) / 16.0F;
                minZ = (15.0F - (float)var8) / 16.0F;
                maxX = (8.0F + var10) / 16.0F;
                maxY =  0.75F;
                maxZ = 0.9375F;
                break;

            case 1:
                //this.setBlockBounds(0.0625F, (12.0F - (float)var9) / 16.0F, (8.0F - var10) / 16.0F, (1.0F + (float)var8) / 16.0F, 0.75F, (8.0F + var10) / 16.0F);
                minX = 0.0625F;
                minY = (12.0F - (float)var9) / 16.0F;
                minZ = (8.0F - var10) / 16.0F;
                maxX = (1.0F + (float)var8) / 16.0F;
                maxY = 0.75F;
                maxZ = (8.0F + var10) / 16.0F;
                break;

            case 2:
                //this.setBlockBounds((8.0F - var10) / 16.0F, (12.0F - (float)var9) / 16.0F, 0.0625F, (8.0F + var10) / 16.0F, 0.75F, (1.0F + (float)var8) / 16.0F);
                minX = (8.0F - var10) / 16.0F;
                minY = (12.0F - (float)var9) / 16.0F;
                minZ = 0.0625F;
                maxX = (8.0F + var10) / 16.0F;
                maxY = 0.75F;
                maxZ = (1.0F + (float)var8) / 16.0F;
                break;

            case 3:
                //this.setBlockBounds((15.0F - (float)var8) / 16.0F, (12.0F - (float)var9) / 16.0F, (8.0F - var10) / 16.0F, 0.9375F, 0.75F, (8.0F + var10) / 16.0F);
                minX = (15.0F - (float)var8) / 16.0F;
                minY = (12.0F - (float)var9) / 16.0F;
                minZ = (8.0F - var10) / 16.0F;
                maxX = 0.9375F;
                maxY = 0.75F;
                maxZ = (8.0F + var10) / 16.0F;

        }

        return AxisAlignedBB.getAABBPool().getAABB(
                (double)par2 + (double)minX,
                (double)par3 + (double)minY,
                (double)par4 + (double)minZ,
                (double)par2 + (double)maxX,
                (double)par3 + (double)maxY,
                (double)par4 + (double)maxZ);
    }

    // --


