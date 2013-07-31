 public void onUpdate()
    {
        super.onUpdate();

        if (this.getTimeSinceHit() > 0)
        {
            this.setTimeSinceHit(this.getTimeSinceHit() - 1);
        }

        if (this.getDamageTaken() > 0.0F)
        {
            this.setDamageTaken(this.getDamageTaken() - 1.0F);
        }

        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        byte var1 = 5;
        double var2 = 0.0D;

        for (int var4 = 0; var4 < var1; ++var4)
        {
            double var5 = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * (double)(var4 + 0) / (double)var1 - 0.125D;
            double var7 = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * (double)(var4 + 1) / (double)var1 - 0.125D;
            AxisAlignedBB var9 = AxisAlignedBB.getAABBPool().getAABB(this.boundingBox.minX, var5, this.boundingBox.minZ, this.boundingBox.maxX, var7, this.boundingBox.maxZ);

            if (this.worldObj.isAABBInMaterial(var9, Material.water))
            {
                var2 += 1.0D / (double)var1;
            }
        }

        double var23 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
        double var6;
        double var8;

        if (var23 > 0.26249999999999996D)
        {
            var6 = Math.cos((double)this.rotationYaw * Math.PI / 180.0D);
            var8 = Math.sin((double)this.rotationYaw * Math.PI / 180.0D);

            for (int var10 = 0; (double)var10 < 1.0D + var23 * 60.0D; ++var10)
            {
                double var11 = (double)(this.rand.nextFloat() * 2.0F - 1.0F);
                double var13 = (double)(this.rand.nextInt(2) * 2 - 1) * 0.7D;
                double var15;
                double var17;

                if (this.rand.nextBoolean())
                {
                    var15 = this.posX - var6 * var11 * 0.8D + var8 * var13;
                    var17 = this.posZ - var8 * var11 * 0.8D - var6 * var13;
                    this.worldObj.spawnParticle("splash", var15, this.posY - 0.125D, var17, this.motionX, this.motionY, this.motionZ);
                }
                else
                {
                    var15 = this.posX + var6 + var8 * var11 * 0.7D;
                    var17 = this.posZ + var8 - var6 * var11 * 0.7D;
                    this.worldObj.spawnParticle("splash", var15, this.posY - 0.125D, var17, this.motionX, this.motionY, this.motionZ);
                }
            }
        }

        double var12;
        double var25;

        if (this.worldObj.isRemote && this.field_70279_a)
        {
            if (this.boatPosRotationIncrements > 0)
            {
                var6 = this.posX + (this.boatX - this.posX) / (double)this.boatPosRotationIncrements;
                var8 = this.posY + (this.boatY - this.posY) / (double)this.boatPosRotationIncrements;
                var25 = this.posZ + (this.boatZ - this.posZ) / (double)this.boatPosRotationIncrements;
                var12 = MathHelper.wrapAngleTo180_double(this.boatYaw - (double)this.rotationYaw);
                this.rotationYaw = (float)((double)this.rotationYaw + var12 / (double)this.boatPosRotationIncrements);
                this.rotationPitch = (float)((double)this.rotationPitch + (this.boatPitch - (double)this.rotationPitch) / (double)this.boatPosRotationIncrements);
                --this.boatPosRotationIncrements;
                this.setPosition(var6, var8, var25);
                this.setRotation(this.rotationYaw, this.rotationPitch);
            }
            else
            {
                // Unglitch Change - Do collision detection and try setting .onGround
                //var6 = this.posX + this.motionX;
                //var8 = this.posY + this.motionY;
                //var25 = this.posZ + this.motionZ;
                //this.setPosition(var6, var8, var25);

                this.motionY -= 0.0001d; // add minimal gravity to allow onGround to be set to true
                this.moveEntity(this.motionX, this.motionY, this.motionZ); // do not call setPosition directly (includes collision detection and setting onGround this way)
                // --

                if (this.onGround)
                {
                    this.motionX *= 0.5D;
                    this.motionY *= 0.5D;
                    this.motionZ *= 0.5D;
                }

                this.motionX *= 0.9900000095367432D;
                this.motionY *= 0.949999988079071D;
                this.motionZ *= 0.9900000095367432D;
            }
        }
        else
        {
            if (var2 < 1.0D)
            {
                var6 = var2 * 2.0D - 1.0D;
                this.motionY += 0.03999999910593033D * var6;
            }
            else
            {
                if (this.motionY < 0.0D)
                {
                    this.motionY /= 2.0D;
                }

                this.motionY += 0.007000000216066837D;
            }

            if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityLivingBase)
            {
                var6 = (double)((EntityLivingBase)this.riddenByEntity).moveForward;

                if (var6 > 0.0D)
                {
                    var8 = -Math.sin((double)(this.riddenByEntity.rotationYaw * (float)Math.PI / 180.0F));
                    var25 = Math.cos((double)(this.riddenByEntity.rotationYaw * (float)Math.PI / 180.0F));
                    this.motionX += var8 * this.speedMultiplier * 0.05000000074505806D;
                    this.motionZ += var25 * this.speedMultiplier * 0.05000000074505806D;
                }
            }

            var6 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);

            if (var6 > 0.35D)
            {
                var8 = 0.35D / var6;
                this.motionX *= var8;
                this.motionZ *= var8;
                var6 = 0.35D;
            }

            if (var6 > var23 && this.speedMultiplier < 0.35D)
            {
                this.speedMultiplier += (0.35D - this.speedMultiplier) / 35.0D;

                if (this.speedMultiplier > 0.35D)
                {
                    this.speedMultiplier = 0.35D;
                }
            }
            else
            {
                this.speedMultiplier -= (this.speedMultiplier - 0.07D) / 35.0D;

                if (this.speedMultiplier < 0.07D)
                {
                    this.speedMultiplier = 0.07D;
                }
            }

            if (this.onGround)
            {
                this.motionX *= 0.5D;
                this.motionY *= 0.5D;
                this.motionZ *= 0.5D;
            }

            this.moveEntity(this.motionX, this.motionY, this.motionZ);

            if (this.isCollidedHorizontally && var23 > 0.2D)
            {
                if (!this.worldObj.isRemote && !this.isDead)
                {
                    this.setDead();
                    int var24;

                    for (var24 = 0; var24 < 3; ++var24)
                    {
                        this.dropItemWithOffset(Block.planks.blockID, 1, 0.0F);
                    }

                    for (var24 = 0; var24 < 2; ++var24)
                    {
                        this.dropItemWithOffset(Item.stick.itemID, 1, 0.0F);
                    }
                }
            }
            else
            {
                this.motionX *= 0.9900000095367432D;
                this.motionY *= 0.949999988079071D;
                this.motionZ *= 0.9900000095367432D;
            }

            this.rotationPitch = 0.0F;
            var8 = (double)this.rotationYaw;
            var25 = this.prevPosX - this.posX;
            var12 = this.prevPosZ - this.posZ;

            if (var25 * var25 + var12 * var12 > 0.001D)
            {
                var8 = (double)((float)(Math.atan2(var12, var25) * 180.0D / Math.PI));
            }

            double var14 = MathHelper.wrapAngleTo180_double(var8 - (double)this.rotationYaw);

            if (var14 > 20.0D)
            {
                var14 = 20.0D;
            }

            if (var14 < -20.0D)
            {
                var14 = -20.0D;
            }

            this.rotationYaw = (float)((double)this.rotationYaw + var14);
            this.setRotation(this.rotationYaw, this.rotationPitch);

            if (!this.worldObj.isRemote)
            {
                List var16 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(0.20000000298023224D, 0.0D, 0.20000000298023224D));
                int var26;

                if (var16 != null && !var16.isEmpty())
                {
                    for (var26 = 0; var26 < var16.size(); ++var26)
                    {
                        Entity var18 = (Entity)var16.get(var26);

                        if (var18 != this.riddenByEntity && var18.canBePushed() && var18 instanceof EntityBoat)
                        {
                            var18.applyEntityCollision(this);
                        }
                    }
                }

                for (var26 = 0; var26 < 4; ++var26)
                {
                    int var27 = MathHelper.floor_double(this.posX + ((double)(var26 % 2) - 0.5D) * 0.8D);
                    int var19 = MathHelper.floor_double(this.posZ + ((double)(var26 / 2) - 0.5D) * 0.8D);

                    for (int var20 = 0; var20 < 2; ++var20)
                    {
                        int var21 = MathHelper.floor_double(this.posY) + var20;
                        int var22 = this.worldObj.getBlockId(var27, var21, var19);

                        if (var22 == Block.snow.blockID)
                        {
                            this.worldObj.setBlockToAir(var27, var21, var19);
                        }
                        else if (var22 == Block.waterlily.blockID)
                        {
                            this.worldObj.destroyBlock(var27, var21, var19, true);
                        }
                    }
                }

                if (this.riddenByEntity != null && this.riddenByEntity.isDead)
                {
                    this.riddenByEntity = null;
                }
            }
        }
    }