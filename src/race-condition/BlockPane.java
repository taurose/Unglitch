
    public void addCollisionBoxesToList(World par1World, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity)
    {
        boolean var8 = this.canThisPaneConnectToThisBlockID(par1World.getBlockId(par2, par3, par4 - 1));
        boolean var9 = this.canThisPaneConnectToThisBlockID(par1World.getBlockId(par2, par3, par4 + 1));
        boolean var10 = this.canThisPaneConnectToThisBlockID(par1World.getBlockId(par2 - 1, par3, par4));
        boolean var11 = this.canThisPaneConnectToThisBlockID(par1World.getBlockId(par2 + 1, par3, par4));

        if ((!var10 || !var11) && (var10 || var11 || var8 || var9))
        {
            if (var10 && !var11)
            {
                // Unglitch Change
                //this.setBlockBounds(0.0F, 0.0F, 0.4375F, 0.5F, 1.0F, 0.5625F);
                //super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
                this.addCollisionBoxToListWithBounds(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity, 0.0F, 0.0F, 0.4375F, 0.5F, 1.0F, 0.5625F);
            }
            else if (!var10 && var11)
            {
                // Unglitch Change
                //this.setBlockBounds(0.5F, 0.0F, 0.4375F, 1.0F, 1.0F, 0.5625F);
                //super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
                this.addCollisionBoxToListWithBounds(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity, 0.5F, 0.0F, 0.4375F, 1.0F, 1.0F, 0.5625F);
            }
        }
        else
        {
            // Unglitch Change
            //this.setBlockBounds(0.0F, 0.0F, 0.4375F, 1.0F, 1.0F, 0.5625F);
            //super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
            this.addCollisionBoxToListWithBounds(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity, 0.0F, 0.0F, 0.4375F, 1.0F, 1.0F, 0.5625F);
        }

        if ((!var8 || !var9) && (var10 || var11 || var8 || var9))
        {
            if (var8 && !var9)
            {
                // Unglitch Change
                //this.setBlockBounds(0.4375F, 0.0F, 0.0F, 0.5625F, 1.0F, 0.5F);
                //super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
                this.addCollisionBoxToListWithBounds(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity, 0.4375F, 0.0F, 0.0F, 0.5625F, 1.0F, 0.5F);
            }
            else if (!var8 && var9)
            {
                // Unglitch Change
                //this.setBlockBounds(0.4375F, 0.0F, 0.5F, 0.5625F, 1.0F, 1.0F);
                //super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
                this.addCollisionBoxToListWithBounds(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity, 0.4375F, 0.0F, 0.5F, 0.5625F, 1.0F, 1.0F);
            }
        }
        else
        {
            // Unglitch Change
            //this.setBlockBounds(0.4375F, 0.0F, 0.0F, 0.5625F, 1.0F, 1.0F);
            //super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
            this.addCollisionBoxToListWithBounds(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity, 0.4375F, 0.0F, 0.0F, 0.5625F, 1.0F, 1.0F);
        }
    }

