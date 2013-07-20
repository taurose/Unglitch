
    /*public void addCollisionBoxesToList(World par1World, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity)
    {
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.8125F, 1.0F);
        super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
        int var8 = par1World.getBlockMetadata(par2, par3, par4);

        if (isEnderEyeInserted(var8))
        {
            this.setBlockBounds(0.3125F, 0.8125F, 0.3125F, 0.6875F, 1.0F, 0.6875F);
            super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
        }

        this.setBlockBoundsForItemRender();
    }*/

    public void addCollisionBoxesToList(World par1World, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity)
    {
        this.addCollisionBoxToListWithBounds(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity, 0.0F, 0.0F, 0.0F, 1.0F, 0.8125F, 1.0F);
        int var8 = par1World.getBlockMetadata(par2, par3, par4);
        if (isEnderEyeInserted(var8))
        {
            this.addCollisionBoxToListWithBounds(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity, 0.3125F, 0.8125F, 0.3125F, 0.6875F, 1.0F, 0.6875F);
        }

        this.setBlockBoundsForItemRender();
    }

