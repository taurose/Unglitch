    // also used in position-sync

     public void handleVehicleSpawn(Packet23VehicleSpawn par1Packet23VehicleSpawn)
    {
        double var2 = (double)par1Packet23VehicleSpawn.xPosition / 32.0D;
        double var4 = (double)par1Packet23VehicleSpawn.yPosition / 32.0D;
        double var6 = (double)par1Packet23VehicleSpawn.zPosition / 32.0D;
        Object var8 = null;

        if (par1Packet23VehicleSpawn.type == 10)
        {
            var8 = EntityMinecart.createMinecart(this.worldClient, var2, var4, var6, par1Packet23VehicleSpawn.throwerEntityId);
        }
        else if (par1Packet23VehicleSpawn.type == 90)
        {
            Entity var9 = this.getEntityByID(par1Packet23VehicleSpawn.throwerEntityId);

            if (var9 instanceof EntityPlayer)
            {
                var8 = new EntityFishHook(this.worldClient, var2, var4, var6, (EntityPlayer)var9);
            }

            par1Packet23VehicleSpawn.throwerEntityId = 0;
        }
        else if (par1Packet23VehicleSpawn.type == 60)
        {
            var8 = new EntityArrow(this.worldClient, var2, var4, var6);
        }
        else if (par1Packet23VehicleSpawn.type == 61)
        {
            var8 = new EntitySnowball(this.worldClient, var2, var4, var6);
        }
        else if (par1Packet23VehicleSpawn.type == 71)
        {
            var8 = new EntityItemFrame(this.worldClient, (int)var2, (int)var4, (int)var6, par1Packet23VehicleSpawn.throwerEntityId);
            par1Packet23VehicleSpawn.throwerEntityId = 0;
        }
        else if (par1Packet23VehicleSpawn.type == 77)
        {
            var8 = new EntityLeashKnot(this.worldClient, (int)var2, (int)var4, (int)var6);
            par1Packet23VehicleSpawn.throwerEntityId = 0;
        }
        else if (par1Packet23VehicleSpawn.type == 65)
        {
            var8 = new EntityEnderPearl(this.worldClient, var2, var4, var6);
        }
        else if (par1Packet23VehicleSpawn.type == 72)
        {
            var8 = new EntityEnderEye(this.worldClient, var2, var4, var6);
        }
        else if (par1Packet23VehicleSpawn.type == 76)
        {
            var8 = new EntityFireworkRocket(this.worldClient, var2, var4, var6, (ItemStack)null);
        }
        else if (par1Packet23VehicleSpawn.type == 63)
        {
            var8 = new EntityLargeFireball(this.worldClient, var2, var4, var6, (double)par1Packet23VehicleSpawn.speedX / 8000.0D, (double)par1Packet23VehicleSpawn.speedY / 8000.0D, (double)par1Packet23VehicleSpawn.speedZ / 8000.0D);
            par1Packet23VehicleSpawn.throwerEntityId = 0;
        }
        else if (par1Packet23VehicleSpawn.type == 64)
        {
            var8 = new EntitySmallFireball(this.worldClient, var2, var4, var6, (double)par1Packet23VehicleSpawn.speedX / 8000.0D, (double)par1Packet23VehicleSpawn.speedY / 8000.0D, (double)par1Packet23VehicleSpawn.speedZ / 8000.0D);
            par1Packet23VehicleSpawn.throwerEntityId = 0;
        }
        else if (par1Packet23VehicleSpawn.type == 66)
        {
            var8 = new EntityWitherSkull(this.worldClient, var2, var4, var6, (double)par1Packet23VehicleSpawn.speedX / 8000.0D, (double)par1Packet23VehicleSpawn.speedY / 8000.0D, (double)par1Packet23VehicleSpawn.speedZ / 8000.0D);
            par1Packet23VehicleSpawn.throwerEntityId = 0;
        }
        else if (par1Packet23VehicleSpawn.type == 62)
        {
            var8 = new EntityEgg(this.worldClient, var2, var4, var6);
        }
        else if (par1Packet23VehicleSpawn.type == 73)
        {
            var8 = new EntityPotion(this.worldClient, var2, var4, var6, par1Packet23VehicleSpawn.throwerEntityId);
            par1Packet23VehicleSpawn.throwerEntityId = 0;
        }
        else if (par1Packet23VehicleSpawn.type == 75)
        {
            var8 = new EntityExpBottle(this.worldClient, var2, var4, var6);
            par1Packet23VehicleSpawn.throwerEntityId = 0;
        }
        else if (par1Packet23VehicleSpawn.type == 1)
        {
            var8 = new EntityBoat(this.worldClient, var2, var4, var6);
        }
        else if (par1Packet23VehicleSpawn.type == 50)
        {
            var8 = new EntityTNTPrimed(this.worldClient, var2, var4, var6, (EntityLivingBase)null);
        }
        else if (par1Packet23VehicleSpawn.type == 51)
        {
            var8 = new EntityEnderCrystal(this.worldClient, var2, var4, var6);
        }
        else if (par1Packet23VehicleSpawn.type == 2)
        {
            var8 = new EntityItem(this.worldClient, var2, var4, var6);
        }
        else if (par1Packet23VehicleSpawn.type == 70)
        {
            var8 = new EntityFallingSand(this.worldClient, var2, var4, var6, par1Packet23VehicleSpawn.throwerEntityId & 65535, par1Packet23VehicleSpawn.throwerEntityId >> 16);
            par1Packet23VehicleSpawn.throwerEntityId = 0;
        }

        if (var8 != null)
        {
            ((Entity)var8).serverPosX = par1Packet23VehicleSpawn.xPosition;
            ((Entity)var8).serverPosY = par1Packet23VehicleSpawn.yPosition;
            ((Entity)var8).serverPosZ = par1Packet23VehicleSpawn.zPosition;
            ((Entity) var8).rotationPitch = (float)(par1Packet23VehicleSpawn.pitch * 360) / 256.0F;
            ((Entity)var8).rotationYaw = (float)(par1Packet23VehicleSpawn.yaw * 360) / 256.0F;
            Entity[] var12 = ((Entity)var8).getParts();

            if (var12 != null)
            {
                int var10 = par1Packet23VehicleSpawn.entityId - ((Entity)var8).entityId;

                for (int var11 = 0; var11 < var12.length; ++var11)
                {
                    var12[var11].entityId += var10;
                }
            }

            ((Entity)var8).entityId = par1Packet23VehicleSpawn.entityId;
            this.worldClient.addEntityToWorld(par1Packet23VehicleSpawn.entityId, (Entity)var8);

            // Unglitch Start - find collision-free position
            // avoid issues with item frames and leash knots
            if(!(var8 instanceof  EntityItemFrame || var8 instanceof EntityLeashKnot)){
                double[] adj = ((Entity)var8).adjustServerPosition(var2, var4, var6);
                ((Entity)var8).setPosition(adj[0], adj[1], adj[2]);
            }
            // --

            if (par1Packet23VehicleSpawn.throwerEntityId > 0)
            {
                if (par1Packet23VehicleSpawn.type == 60)
                {
                    Entity var13 = this.getEntityByID(par1Packet23VehicleSpawn.throwerEntityId);

                    if (var13 instanceof EntityLivingBase)
                    {
                        EntityArrow var14 = (EntityArrow)var8;
                        var14.shootingEntity = var13;
                    }
                }

                ((Entity)var8).setVelocity((double)par1Packet23VehicleSpawn.speedX / 8000.0D, (double)par1Packet23VehicleSpawn.speedY / 8000.0D, (double)par1Packet23VehicleSpawn.speedZ / 8000.0D);
            }
        }
    }


    public void handleEntityTeleport(Packet34EntityTeleport par1Packet34EntityTeleport)
    {
        Entity var2 = this.getEntityByID(par1Packet34EntityTeleport.entityId);

        if (var2 != null)
        {
            var2.serverPosX = par1Packet34EntityTeleport.xPosition;
            var2.serverPosY = par1Packet34EntityTeleport.yPosition;
            var2.serverPosZ = par1Packet34EntityTeleport.zPosition;
            double var3 = (double)var2.serverPosX / 32.0D;
            // Unglitch Change - Remove old workaround
            // double var5 = (double)var2.serverPosY / 32.0D + 0.015625D;
            double var5 = (double)var2.serverPosY / 32.0D;
            double var7 = (double)var2.serverPosZ / 32.0D;

            // Unglitch Start - find collision-free position
            double[] adj = var2.adjustServerPosition(var3, var5, var7);
            var3 = adj[0];
            var5 = adj[1];
            var7 = adj[2];
            // --

            float var9 = (float)(par1Packet34EntityTeleport.yaw * 360) / 256.0F;
            float var10 = (float)(par1Packet34EntityTeleport.pitch * 360) / 256.0F;
            var2.setPositionAndRotation2(var3, var5, var7, var9, var10, 3);
        }
    }



    public void handleEntity(Packet30Entity par1Packet30Entity)
    {
        Entity var2 = this.getEntityByID(par1Packet30Entity.entityId);

        if (var2 != null)
        {
            var2.serverPosX += par1Packet30Entity.xPosition;
            var2.serverPosY += par1Packet30Entity.yPosition;
            var2.serverPosZ += par1Packet30Entity.zPosition;
            double var3 = (double)var2.serverPosX / 32.0D;
            double var5 = (double)var2.serverPosY / 32.0D;
            double var7 = (double)var2.serverPosZ / 32.0D;

            // Unglitch Start - find collision-free position
            double[] adj = var2.adjustServerPosition(var3, var5, var7);
            var3 = adj[0];
            var5 = adj[1];
            var7 = adj[2];
            // --

            float var9 = par1Packet30Entity.rotating ? (float)(par1Packet30Entity.yaw * 360) / 256.0F : var2.rotationYaw;
            float var10 = par1Packet30Entity.rotating ? (float)(par1Packet30Entity.pitch * 360) / 256.0F : var2.rotationPitch;
            var2.setPositionAndRotation2(var3, var5, var7, var9, var10, 3);
        }
    }


    public void handleMobSpawn(Packet24MobSpawn par1Packet24MobSpawn)
    {
        double var2 = (double)par1Packet24MobSpawn.xPosition / 32.0D;
        double var4 = (double)par1Packet24MobSpawn.yPosition / 32.0D;
        double var6 = (double)par1Packet24MobSpawn.zPosition / 32.0D;
        float var8 = (float)(par1Packet24MobSpawn.yaw * 360) / 256.0F;
        float var9 = (float)(par1Packet24MobSpawn.pitch * 360) / 256.0F;
        EntityLivingBase var10 = (EntityLivingBase)EntityList.createEntityByID(par1Packet24MobSpawn.type, this.mc.theWorld);
        var10.serverPosX = par1Packet24MobSpawn.xPosition;
        var10.serverPosY = par1Packet24MobSpawn.yPosition;
        var10.serverPosZ = par1Packet24MobSpawn.zPosition;
        var10.rotationYawHead = (float)(par1Packet24MobSpawn.headYaw * 360) / 256.0F;
        Entity[] var11 = var10.getParts();

        if (var11 != null)
        {
            int var12 = par1Packet24MobSpawn.entityId - var10.entityId;

            for (int var13 = 0; var13 < var11.length; ++var13)
            {
                var11[var13].entityId += var12;
            }
        }

        var10.entityId = par1Packet24MobSpawn.entityId;



        var10.setPositionAndRotation(var2, var4, var6, var8, var9);
        var10.motionX = (double)((float)par1Packet24MobSpawn.velocityX / 8000.0F);
        var10.motionY = (double)((float)par1Packet24MobSpawn.velocityY / 8000.0F);
        var10.motionZ = (double)((float)par1Packet24MobSpawn.velocityZ / 8000.0F);
        this.worldClient.addEntityToWorld(par1Packet24MobSpawn.entityId, var10);
        List var14 = par1Packet24MobSpawn.getMetadata();

        if (var14 != null)
        {
            var10.getDataWatcher().updateWatchedObjectsFromList(var14);

            // Unglitch Start (part of position-sync)
            if(var10 instanceof EntityAgeable){
                EntityAgeable entityAgeable = (EntityAgeable) var10;
                entityAgeable.func_98054_a(entityAgeable.isChild());
            }
            else if(var10 instanceof EntitySlime){
                EntitySlime entitySlime = (EntitySlime) var10;
                entitySlime.setSlimeSize(entitySlime.getSlimeSize());

            }
            // --

        }

        // Unglitch Start - find collision-free position
        double[] adj = var10.adjustServerPosition(var2, var4, var6);
        var10.setPosition(adj[0], adj[1], adj[2]);
        // --
    }


        /**
     * Handle a entity experience orb packet.
     */
    public void handleEntityExpOrb(Packet26EntityExpOrb par1Packet26EntityExpOrb)
    {
        EntityXPOrb var2 = new EntityXPOrb(this.worldClient, (double)par1Packet26EntityExpOrb.posX, (double)par1Packet26EntityExpOrb.posY, (double)par1Packet26EntityExpOrb.posZ, par1Packet26EntityExpOrb.xpValue);
        var2.serverPosX = par1Packet26EntityExpOrb.posX;
        var2.serverPosY = par1Packet26EntityExpOrb.posY;
        var2.serverPosZ = par1Packet26EntityExpOrb.posZ;
        var2.rotationYaw = 0.0F;
        var2.rotationPitch = 0.0F;
        var2.entityId = par1Packet26EntityExpOrb.entityId;
        this.worldClient.addEntityToWorld(par1Packet26EntityExpOrb.entityId, var2);

        // Unglitch Start - find collision-free position (also fix the wrong position set at the beginning of the method)
        double[] adj = var2.adjustServerPosition(par1Packet26EntityExpOrb.posX / 32.0d, par1Packet26EntityExpOrb.posY / 32.0d, par1Packet26EntityExpOrb.posZ / 32.0d);
        var2.setPosition(adj[0], adj[1], adj[2]);
        // --
    }
