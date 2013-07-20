
    /*public void addCollisionBoxesToList(World par1World, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity)
    {
        this.setBlockBoundsBasedOnState(par1World, par2, par3, par4);
        super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
    }*/

    public void addCollisionBoxesToList(World par1World, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity)
    {

        float minX = 0.0f;
        float minY = 0.0f;
        float minZ = 0.0f;
        float maxX = 0.0f;
        float maxY = 0.0f;
        float maxZ = 0.0f;

        // from setBlockBoundsBasedOnState
        if (this.isDoubleSlab)
        {
            //this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            maxX = 1.0f;
            maxY = 1.0f;
            maxZ = 1.0f;
        }
        else
        {
            boolean var5 = (par1World.getBlockMetadata(par2, par3, par4) & 8) != 0;

            if (var5)
            {
                //this.setBlockBounds(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
                minY = 0.5f;
                maxX = 1.0f;
                maxY = 1.0f;
                maxZ = 1.0f;
            }
            else
            {
                //this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
                maxX = 1.0f;
                maxY = 0.5f;
                maxZ = 1.0f;
            }
        }

        this.addCollisionBoxToListWithBounds(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity, minX, minY, minZ, maxX, maxY, maxZ);
    }

