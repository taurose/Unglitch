
    public void addCollisionBoxesToList(World par1World, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity)
    {
        boolean var8 = this.canConnectFenceTo(par1World, par2, par3, par4 - 1);
        boolean var9 = this.canConnectFenceTo(par1World, par2, par3, par4 + 1);
        boolean var10 = this.canConnectFenceTo(par1World, par2 - 1, par3, par4);
        boolean var11 = this.canConnectFenceTo(par1World, par2 + 1, par3, par4);
        float var12 = 0.375F;
        float var13 = 0.625F;
        float var14 = 0.375F;
        float var15 = 0.625F;

        if (var8)
        {
            var14 = 0.0F;
        }

        if (var9)
        {
            var15 = 1.0F;
        }

        if (var8 || var9)
        {
            // Unglitch Change
            //this.setBlockBounds(var12, 0.0F, var14, var13, 1.5F, var15);
            //super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
            this.addCollisionBoxToListWithBounds(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity, var12, 0.0F, var14, var13, 1.5f, var15);
        }

        var14 = 0.375F;
        var15 = 0.625F;

        if (var10)
        {
            var12 = 0.0F;
        }

        if (var11)
        {
            var13 = 1.0F;
        }

        if (var10 || var11 || !var8 && !var9)
        {
            // Unglitch Change
            //this.setBlockBounds(var12, 0.0F, var14, var13, 1.5F, var15);
            //super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
            this.addCollisionBoxToListWithBounds(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity, var12, 0.0F, var14, var13, 1.5f, var15);
        }

        if (var8)
        {
            var14 = 0.0F;
        }

        if (var9)
        {
            var15 = 1.0F;
        }

        this.setBlockBounds(var12, 0.0F, var14, var13, 1.0F, var15);
    }
