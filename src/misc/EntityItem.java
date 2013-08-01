    public void onUpdate()
    {
        super.onUpdate();

        if (this.delayBeforeCanPickup > 0)
        {
            --this.delayBeforeCanPickup;
        }

        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.motionY -= 0.03999999910593033D;
        // Unglitch Change - don't push out of blocks client-side
        //this.noClip = this.pushOutOfBlocks(this.posX, (this.boundingBox.minY + this.boundingBox.maxY) / 2.0D, this.posZ);
        if(!worldObj.isRemote){
            this.noClip = this.pushOutOfBlocks(this.posX, (this.boundingBox.minY + this.boundingBox.maxY) / 2.0D, this.posZ);
            if(this.noClip){
                //this.isAirBorne = true;     // send motion updates when pushing (can be omitted to save bandwidth)
            }
        }
        else{
            this.noClip = false;
        }
        // --
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        boolean var1 = (int)this.prevPosX != (int)this.posX || (int)this.prevPosY != (int)this.posY || (int)this.prevPosZ != (int)this.posZ;

        if (var1 || this.ticksExisted % 25 == 0)
        {
            if (this.worldObj.getBlockMaterial(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)) == Material.lava)
            {
                this.motionY = 0.20000000298023224D;
                this.motionX = (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F);
                this.motionZ = (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F);
                this.playSound("random.fizz", 0.4F, 2.0F + this.rand.nextFloat() * 0.4F);
            }

            if (!this.worldObj.isRemote)
            {
                this.searchForOtherItemsNearby();
            }
        }

        float var2 = 0.98F;

        if (this.onGround)
        {
            var2 = 0.58800006F;
            int var3 = this.worldObj.getBlockId(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ));

            if (var3 > 0)
            {
                var2 = Block.blocksList[var3].slipperiness * 0.98F;
            }
        }

        this.motionX *= (double)var2;
        this.motionY *= 0.9800000190734863D;
        this.motionZ *= (double)var2;

        if (this.onGround)
        {
            this.motionY *= -0.5D;
        }

        ++this.age;

        if (!this.worldObj.isRemote && this.age >= 6000)
        {
            this.setDead();
        }
    }