
    /*public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        this.setBlockBoundsBasedOnState(par1World, par2, par3, par4);
        this.maxY = 1.5D;
        return super.getCollisionBoundingBoxFromPool(par1World, par2, par3, par4);
    }*/

    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        return this.getBlockBoundsBasedOnState(par1World, par2, par3, par4, 1.5D);
    }

    public AxisAlignedBB getBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, double maxY)
    {
        // from setBlockBoundsBasedOnState
        boolean var5 = this.canConnectWallTo(par1IBlockAccess, par2, par3, par4 - 1);
        boolean var6 = this.canConnectWallTo(par1IBlockAccess, par2, par3, par4 + 1);
        boolean var7 = this.canConnectWallTo(par1IBlockAccess, par2 - 1, par3, par4);
        boolean var8 = this.canConnectWallTo(par1IBlockAccess, par2 + 1, par3, par4);
        float var9 = 0.25F;
        float var10 = 0.75F;
        float var11 = 0.25F;
        float var12 = 0.75F;
        float var13 = 1.0F;

        if (var5)
        {
            var11 = 0.0F;
        }

        if (var6)
        {
            var12 = 1.0F;
        }

        if (var7)
        {
            var9 = 0.0F;
        }

        if (var8)
        {
            var10 = 1.0F;
        }

        if (var5 && var6 && !var7 && !var8)
        {
            var13 = 0.8125F;
            var9 = 0.3125F;
            var10 = 0.6875F;
        }
        else if (!var5 && !var6 && var7 && var8)
        {
            var13 = 0.8125F;
            var11 = 0.3125F;
            var12 = 0.6875F;
        }

        //this.setBlockBounds(var9, 0.0F, var11, var10, var13, var12);
        return AxisAlignedBB.getAABBPool().getAABB(	(double)par2 + (double)var9,
                (double)par3 + (double)0.0f,
                (double)par4 + (double)var11,
                (double)par2 + (double)var10,
                (double)par3 + maxY,
                (double)par4 + (double)var12);
    }

