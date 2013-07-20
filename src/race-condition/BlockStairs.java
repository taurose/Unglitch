
    /*public void addCollisionBoxesToList(World par1World, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity)
    {
        this.func_82541_d(par1World, par2, par3, par4);
        super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
        boolean var8 = this.func_82542_g(par1World, par2, par3, par4);
        super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);

        if (var8 && this.func_82544_h(par1World, par2, par3, par4))
        {
            super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
        }

        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }*/


    public void addCollisionBoxesToList(World par1World, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity)
    {
        this.func_82541_d_toList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
        boolean var8 = this.func_82542_g_toList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);

        if (var8)
        {
            this.func_82544_h_toList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
        }

        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }


    public void func_82541_d_toList(World world, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity)
    {
        float minX = 0.0f;
        float minY = 0.0f;
        float minZ = 0.0f;
        float maxX = 0.0f;
        float maxY = 0.0f;
        float maxZ = 0.0f;

        // from func_82541_d
        int var5 = world.getBlockMetadata(par2, par3, par4);

        if ((var5 & 4) != 0)
        {
            //   this.setBlockBounds(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
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

        this.addCollisionBoxToListWithBounds(world, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity, minX, minY, minZ, maxX, maxY, maxZ);
    }


    public boolean func_82542_g_toList(World world, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity)
    {
        // from func_82542_g
        int var5 = world.getBlockMetadata(par2, par3, par4);
        int var6 = var5 & 3;
        float var7 = 0.5F;
        float var8 = 1.0F;

        if ((var5 & 4) != 0)
        {
            var7 = 0.0F;
            var8 = 0.5F;
        }

        float var9 = 0.0F;
        float var10 = 1.0F;
        float var11 = 0.0F;
        float var12 = 0.5F;
        boolean var13 = true;
        int var14;
        int var15;
        int var16;

        if (var6 == 0)
        {
            var9 = 0.5F;
            var12 = 1.0F;
            var14 = world.getBlockId(par2 + 1, par3, par4);
            var15 = world.getBlockMetadata(par2 + 1, par3, par4);

            if (isBlockStairsID(var14) && (var5 & 4) == (var15 & 4))
            {
                var16 = var15 & 3;

                if (var16 == 3 && !this.func_82540_f(world, par2, par3, par4 + 1, var5))
                {
                    var12 = 0.5F;
                    var13 = false;
                }
                else if (var16 == 2 && !this.func_82540_f(world, par2, par3, par4 - 1, var5))
                {
                    var11 = 0.5F;
                    var13 = false;
                }
            }
        }
        else if (var6 == 1)
        {
            var10 = 0.5F;
            var12 = 1.0F;
            var14 = world.getBlockId(par2 - 1, par3, par4);
            var15 = world.getBlockMetadata(par2 - 1, par3, par4);

            if (isBlockStairsID(var14) && (var5 & 4) == (var15 & 4))
            {
                var16 = var15 & 3;

                if (var16 == 3 && !this.func_82540_f(world, par2, par3, par4 + 1, var5))
                {
                    var12 = 0.5F;
                    var13 = false;
                }
                else if (var16 == 2 && !this.func_82540_f(world, par2, par3, par4 - 1, var5))
                {
                    var11 = 0.5F;
                    var13 = false;
                }
            }
        }
        else if (var6 == 2)
        {
            var11 = 0.5F;
            var12 = 1.0F;
            var14 = world.getBlockId(par2, par3, par4 + 1);
            var15 = world.getBlockMetadata(par2, par3, par4 + 1);

            if (isBlockStairsID(var14) && (var5 & 4) == (var15 & 4))
            {
                var16 = var15 & 3;

                if (var16 == 1 && !this.func_82540_f(world, par2 + 1, par3, par4, var5))
                {
                    var10 = 0.5F;
                    var13 = false;
                }
                else if (var16 == 0 && !this.func_82540_f(world, par2 - 1, par3, par4, var5))
                {
                    var9 = 0.5F;
                    var13 = false;
                }
            }
        }
        else if (var6 == 3)
        {
            var14 = world.getBlockId(par2, par3, par4 - 1);
            var15 = world.getBlockMetadata(par2, par3, par4 - 1);

            if (isBlockStairsID(var14) && (var5 & 4) == (var15 & 4))
            {
                var16 = var15 & 3;

                if (var16 == 1 && !this.func_82540_f(world, par2 + 1, par3, par4, var5))
                {
                    var10 = 0.5F;
                    var13 = false;
                }
                else if (var16 == 0 && !this.func_82540_f(world, par2 - 1, par3, par4, var5))
                {
                    var9 = 0.5F;
                    var13 = false;
                }
            }
        }

        //this.setBlockBounds(var9, var7, var11, var10, var8, var12);
        this.addCollisionBoxToListWithBounds(world, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity, var9, var7, var11, var10, var8, var12);
        return var13;
    }

    public boolean func_82544_h_toList(World world, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity)
    {
        // from func_82544_h
        int var5 = world.getBlockMetadata(par2, par3, par4);
        int var6 = var5 & 3;
        float var7 = 0.5F;
        float var8 = 1.0F;

        if ((var5 & 4) != 0)
        {
            var7 = 0.0F;
            var8 = 0.5F;
        }

        float var9 = 0.0F;
        float var10 = 0.5F;
        float var11 = 0.5F;
        float var12 = 1.0F;
        boolean var13 = false;
        int var14;
        int var15;
        int var16;

        if (var6 == 0)
        {
            var14 = world.getBlockId(par2 - 1, par3, par4);
            var15 = world.getBlockMetadata(par2 - 1, par3, par4);

            if (isBlockStairsID(var14) && (var5 & 4) == (var15 & 4))
            {
                var16 = var15 & 3;

                if (var16 == 3 && !this.func_82540_f(world, par2, par3, par4 - 1, var5))
                {
                    var11 = 0.0F;
                    var12 = 0.5F;
                    var13 = true;
                }
                else if (var16 == 2 && !this.func_82540_f(world, par2, par3, par4 + 1, var5))
                {
                    var11 = 0.5F;
                    var12 = 1.0F;
                    var13 = true;
                }
            }
        }
        else if (var6 == 1)
        {
            var14 = world.getBlockId(par2 + 1, par3, par4);
            var15 = world.getBlockMetadata(par2 + 1, par3, par4);

            if (isBlockStairsID(var14) && (var5 & 4) == (var15 & 4))
            {
                var9 = 0.5F;
                var10 = 1.0F;
                var16 = var15 & 3;

                if (var16 == 3 && !this.func_82540_f(world, par2, par3, par4 - 1, var5))
                {
                    var11 = 0.0F;
                    var12 = 0.5F;
                    var13 = true;
                }
                else if (var16 == 2 && !this.func_82540_f(world, par2, par3, par4 + 1, var5))
                {
                    var11 = 0.5F;
                    var12 = 1.0F;
                    var13 = true;
                }
            }
        }
        else if (var6 == 2)
        {
            var14 = world.getBlockId(par2, par3, par4 - 1);
            var15 = world.getBlockMetadata(par2, par3, par4 - 1);

            if (isBlockStairsID(var14) && (var5 & 4) == (var15 & 4))
            {
                var11 = 0.0F;
                var12 = 0.5F;
                var16 = var15 & 3;

                if (var16 == 1 && !this.func_82540_f(world, par2 - 1, par3, par4, var5))
                {
                    var13 = true;
                }
                else if (var16 == 0 && !this.func_82540_f(world, par2 + 1, par3, par4, var5))
                {
                    var9 = 0.5F;
                    var10 = 1.0F;
                    var13 = true;
                }
            }
        }
        else if (var6 == 3)
        {
            var14 = world.getBlockId(par2, par3, par4 + 1);
            var15 = world.getBlockMetadata(par2, par3, par4 + 1);

            if (isBlockStairsID(var14) && (var5 & 4) == (var15 & 4))
            {
                var16 = var15 & 3;

                if (var16 == 1 && !this.func_82540_f(world, par2 - 1, par3, par4, var5))
                {
                    var13 = true;
                }
                else if (var16 == 0 && !this.func_82540_f(world, par2 + 1, par3, par4, var5))
                {
                    var9 = 0.5F;
                    var10 = 1.0F;
                    var13 = true;
                }
            }
        }

        if (var13)
        {
            //this.setBlockBounds(var9, var7, var11, var10, var8, var12);
            this.addCollisionBoxToListWithBounds(world, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity, var9, var7, var11, var10, var8, var12);
        }

        return var13;
    }

