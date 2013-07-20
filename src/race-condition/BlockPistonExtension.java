
    public void addCollisionBoxesToList(World par1World, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity)
    {
        int var8 = par1World.getBlockMetadata(par2, par3, par4);
        float var9 = 0.25F;
        float var10 = 0.375F;
        float var11 = 0.625F;
        float var12 = 0.25F;
        float var13 = 0.75F;

        switch (getDirectionMeta(var8))
        {
            case 0:
                //Unglitch Change
                //this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.25F, 1.0F);
                //super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
                //this.setBlockBounds(0.375F, 0.25F, 0.375F, 0.625F, 1.0F, 0.625F);
                //super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
                this.addCollisionBoxToListWithBounds(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity, 0.0F, 0.0F, 0.0F, 1.0F, 0.25F, 1.0F);
                this.addCollisionBoxToListWithBounds(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity, 0.375F, 0.25F, 0.375F, 0.625F, 1.0F, 0.625F);
                break;

            case 1:
                // Unglitch Change
                //this.setBlockBounds(0.0F, 0.75F, 0.0F, 1.0F, 1.0F, 1.0F);
                //super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
                //this.setBlockBounds(0.375F, 0.0F, 0.375F, 0.625F, 0.75F, 0.625F);
                //super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
                this.addCollisionBoxToListWithBounds(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity, 0.0F, 0.75F, 0.0F, 1.0F, 1.0F, 1.0F);
                this.addCollisionBoxToListWithBounds(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity, 0.375F, 0.0F, 0.375F, 0.625F, 0.75F, 0.625F);
                break;

            case 2:
                // Unglitch Change
                //this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.25F);
                //super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
                //this.setBlockBounds(0.25F, 0.375F, 0.25F, 0.75F, 0.625F, 1.0F);
                //super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
                this.addCollisionBoxToListWithBounds(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.25F);
                this.addCollisionBoxToListWithBounds(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity, 0.25F, 0.375F, 0.25F, 0.75F, 0.625F, 1.0F);
                break;

            case 3:
                // Unglitch Change
                //this.setBlockBounds(0.0F, 0.0F, 0.75F, 1.0F, 1.0F, 1.0F);
                //super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
                //this.setBlockBounds(0.25F, 0.375F, 0.0F, 0.75F, 0.625F, 0.75F);
                //super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
                this.addCollisionBoxToListWithBounds(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity, 0.0F, 0.0F, 0.75F, 1.0F, 1.0F, 1.0F);
                this.addCollisionBoxToListWithBounds(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity, 0.25F, 0.375F, 0.0F, 0.75F, 0.625F, 0.75F);
                break;

            case 4:
                // Unglitch Change
                //this.setBlockBounds(0.0F, 0.0F, 0.0F, 0.25F, 1.0F, 1.0F);
                //super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
                //this.setBlockBounds(0.375F, 0.25F, 0.25F, 0.625F, 0.75F, 1.0F);
                //super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
                this.addCollisionBoxToListWithBounds(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity, 0.0F, 0.0F, 0.0F, 0.25F, 1.0F, 1.0F);
                this.addCollisionBoxToListWithBounds(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity, 0.375F, 0.25F, 0.25F, 0.625F, 0.75F, 1.0F);
                break;

            case 5:
                // Unglitch Change
                //this.setBlockBounds(0.75F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
                //super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
                //this.setBlockBounds(0.0F, 0.375F, 0.25F, 0.75F, 0.625F, 0.75F);
                //super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
                this.addCollisionBoxToListWithBounds(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity, 0.75F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
                this.addCollisionBoxToListWithBounds(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity, 0.0F, 0.375F, 0.25F, 0.75F, 0.625F, 0.75F);
        }

        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

