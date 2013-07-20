    /*public void addCollisionBoxesToList(World par1World, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity)
    {
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.625F, 1.0F);
        super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
        float var8 = 0.125F;
        this.setBlockBounds(0.0F, 0.0F, 0.0F, var8, 1.0F, 1.0F);
        super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, var8);
        super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
        this.setBlockBounds(1.0F - var8, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
        this.setBlockBounds(0.0F, 0.0F, 1.0F - var8, 1.0F, 1.0F, 1.0F);
        super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }*/

    public void addCollisionBoxesToList(World par1World, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity)
    {
        this.addCollisionBoxToListWithBounds(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity, 0.0F, 0.0F, 0.0F, 1.0F, 0.625F, 1.0F);
        float var8 = 0.125F;
        this.addCollisionBoxToListWithBounds(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity, 0.0F, 0.0F, 0.0F, var8, 1.0F, 1.0F);
        this.addCollisionBoxToListWithBounds(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, var8);
        this.addCollisionBoxToListWithBounds(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity, 1.0F - var8, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        this.addCollisionBoxToListWithBounds(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity, 0.0F, 0.0F, 1.0F - var8, 1.0F, 1.0F, 1.0F);
    }

