    // also used in adjust-server-position

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

            // Unglitch Start - Adjust size of baby animals and slimes when spawning client-side
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

        // Unglitch Start (part of adjust-server-position)
        double[] adj = var10.adjustServerPosition(var2, var4, var6);
        var10.setPosition(adj[0], adj[1], adj[2]);
        // --
    }


