
    public void sendLocationToAllClients(List par1List)
    {
        this.playerEntitiesUpdated = false;

        if (!this.isDataInitialized || this.myEntity.getDistanceSq(this.posX, this.posY, this.posZ) > 16.0D)
        {
            this.posX = this.myEntity.posX;
            this.posY = this.myEntity.posY;
            this.posZ = this.myEntity.posZ;
            this.isDataInitialized = true;
            this.playerEntitiesUpdated = true;
            this.sendEventsToPlayers(par1List);
        }

        if (this.field_85178_v != this.myEntity.ridingEntity || this.myEntity.ridingEntity != null && this.ticks % 60 == 0)
        {
            this.field_85178_v = this.myEntity.ridingEntity;
            this.sendPacketToAllTrackingPlayers(new Packet39AttachEntity(0, this.myEntity, this.myEntity.ridingEntity));
        }

        if (this.myEntity instanceof EntityItemFrame && this.ticks % 10 == 0)
        {
            EntityItemFrame var23 = (EntityItemFrame)this.myEntity;
            ItemStack var24 = var23.getDisplayedItem();

            if (var24 != null && var24.getItem() instanceof ItemMap)
            {
                MapData var26 = Item.map.getMapData(var24, this.myEntity.worldObj);
                Iterator var27 = par1List.iterator();

                while (var27.hasNext())
                {
                    EntityPlayer var28 = (EntityPlayer)var27.next();
                    EntityPlayerMP var29 = (EntityPlayerMP)var28;
                    var26.updateVisiblePlayers(var29, var24);

                    if (var29.playerNetServerHandler.packetSize() <= 5)
                    {
                        Packet var30 = Item.map.createMapDataPacket(var24, this.myEntity.worldObj, var29);

                        if (var30 != null)
                        {
                            var29.playerNetServerHandler.sendPacketToPlayer(var30);
                        }
                    }
                }
            }

            this.func_111190_b();
        }
        else if (this.ticks % this.updateFrequency == 0 || this.myEntity.isAirBorne || this.myEntity.getDataWatcher().hasChanges())
        {
            int var2;
            int var3;

            if (this.myEntity.ridingEntity == null)
            {
                ++this.ticksSinceLastForcedTeleport;
                var2 = this.myEntity.myEntitySize.multiplyBy32AndRound(this.myEntity.posX);
                var3 = MathHelper.floor_double(this.myEntity.posY * 32.0D);
                int var4 = this.myEntity.myEntitySize.multiplyBy32AndRound(this.myEntity.posZ);
                int var5 = MathHelper.floor_float(this.myEntity.rotationYaw * 256.0F / 360.0F);
                int var6 = MathHelper.floor_float(this.myEntity.rotationPitch * 256.0F / 360.0F);
                int var7 = var2 - this.lastScaledXPosition;
                int var8 = var3 - this.lastScaledYPosition;
                int var9 = var4 - this.lastScaledZPosition;
                Object var10 = null;
                boolean var11 = Math.abs(var7) >= 4 || Math.abs(var8) >= 4 || Math.abs(var9) >= 4 || this.ticks % 60 == 0;
                boolean var12 = Math.abs(var5 - this.lastYaw) >= 4 || Math.abs(var6 - this.lastPitch) >= 4;

                if (this.ticks > 0 || this.myEntity instanceof EntityArrow)
                {
                    if (var7 >= -128 && var7 < 128 && var8 >= -128 && var8 < 128 && var9 >= -128 && var9 < 128 && this.ticksSinceLastForcedTeleport <= 400 && !this.ridingEntity)
                    {
                        if (var11 && var12)
                        {
                            var10 = new Packet33RelEntityMoveLook(this.myEntity.entityId, (byte)var7, (byte)var8, (byte)var9, (byte)var5, (byte)var6);
                        }
                        else if (var11)
                        {
                            var10 = new Packet31RelEntityMove(this.myEntity.entityId, (byte)var7, (byte)var8, (byte)var9);
                        }
                        else if (var12)
                        {
                            var10 = new Packet32EntityLook(this.myEntity.entityId, (byte)var5, (byte)var6);
                        }
                    }
                    else
                    {
                        this.ticksSinceLastForcedTeleport = 0;
                        var10 = new Packet34EntityTeleport(this.myEntity.entityId, var2, var3, var4, (byte)var5, (byte)var6);

                        // Unglitch Start - We'll send a position update, so we have to save the position (avoid sync issues)
                        var11 = true;
                        var12 = true;
                        // --
                    }
                }
                // Unglitch Start - We won't send a position update, so don't save positions (avoid sync issues)
                else {
                    var11 = false;
                    var12 = false;
                }
                // --

                if (this.sendVelocityUpdates)
                {
                    double var13 = this.trackedEntity.motionX - this.lastTrackedEntityMotionX;
                    double var15 = this.trackedEntity.motionY - this.lastTrackedEntityMotionY;
                    double var17 = this.trackedEntity.motionZ - this.motionZ;
                    double var19 = 0.02D;
                    double var21 = var13 * var13 + var15 * var15 + var17 * var17;

                    if (var21 > var19 * var19 || var21 > 0.0D && this.trackedEntity.motionX == 0.0D && this.trackedEntity.motionY == 0.0D && this.trackedEntity.motionZ == 0.0D)
                    {
                        this.lastTrackedEntityMotionX = this.trackedEntity.motionX;
                        this.lastTrackedEntityMotionY = this.trackedEntity.motionY;
                        this.motionZ = this.trackedEntity.motionZ;
                        this.sendPacketToTrackedPlayers(new Packet28EntityVelocity(this.trackedEntity.entityId, this.lastTrackedEntityMotionX, this.lastTrackedEntityMotionY, this.motionZ));
                    }
                }


                if (var10 != null)
                {
                    this.sendPacketToTrackedPlayers((Packet)var10);
                }

                this.func_111190_b();

                if (var11)
                {
                    this.encodedPosX = var2;
                    this.encodedPosY = var3;
                    this.encodedPosZ = var4;
                }

                if (var12)
                {
                    this.encodedRotationYaw = var5;
                    this.encodedRotationPitch = var6;
                }

                this.ridingEntity = false;
            }
            else
            {
                var2 = MathHelper.floor_float(this.trackedEntity.rotationYaw * 256.0F / 360.0F);
                var3 = MathHelper.floor_float(this.trackedEntity.rotationPitch * 256.0F / 360.0F);
                boolean var25 = Math.abs(var2 - this.encodedRotationYaw) >= 4 || Math.abs(var3 - this.encodedRotationPitch) >= 4;

                if (var25)
                {
                    this.sendPacketToTrackedPlayers(new Packet32EntityLook(this.trackedEntity.entityId, (byte)var2, (byte)var3));
                    this.encodedRotationYaw = var2;
                    this.encodedRotationPitch = var3;
                }

                this.encodedPosX = this.trackedEntity.myEntitySize.multiplyBy32AndRound(this.trackedEntity.posX);
                this.encodedPosY = MathHelper.floor_double(this.trackedEntity.posY * 32.0D);
                this.encodedPosZ = this.trackedEntity.myEntitySize.multiplyBy32AndRound(this.trackedEntity.posZ);
                this.func_111190_b();
                this.ridingEntity = true;
            }

            var2 = MathHelper.floor_float(this.trackedEntity.getRotationYawHead() * 256.0F / 360.0F);

            if (Math.abs(var2 - this.lastHeadMotion) >= 4)
            {
                this.sendPacketToTrackedPlayers(new Packet35EntityHeadRotation(this.trackedEntity.entityId, (byte)var2));
                this.lastHeadMotion = var2;
            }

            this.trackedEntity.isAirBorne = false;
        }

        ++this.updateCounter;

        if (this.trackedEntity.velocityChanged)
        {
            this.sendPacketToTrackedPlayersAndTrackedEntity(new Packet28EntityVelocity(this.trackedEntity));
            this.trackedEntity.velocityChanged = false;
        }
    }


    // Unglitch Start - Send correct position on spawn (stops glitches before first teleport packet)
    private Packet getPacketForThisEntity()
    {
        Packet packet = getPacketForThisEntity_old();

        // item frames and leash knots send whole block coordinates (see original getPacketForThisEntity)
        if(this.trackedEntity instanceof EntityItemFrame || this.trackedEntity instanceof EntityLeashKnot){
                return packet;
        }

        if(packet instanceof Packet23VehicleSpawn){
            Packet23VehicleSpawn spawn = (Packet23VehicleSpawn) packet;
            spawn.xPosition = this.lastScaledXPosition;
            spawn.yPosition = this.lastScaledYPosition;
            spawn.zPosition = this.lastScaledZPosition;
        }
        else if(packet instanceof Packet24MobSpawn){
            Packet24MobSpawn spawn = (Packet24MobSpawn) packet;
            spawn.xPosition = this.lastScaledXPosition;
            spawn.yPosition = this.lastScaledYPosition;
            spawn.zPosition = this.lastScaledZPosition;
        }
        else if(packet instanceof Packet20NamedEntitySpawn){
            Packet20NamedEntitySpawn spawn = (Packet20NamedEntitySpawn) packet;
            spawn.xPosition = this.lastScaledXPosition;
            spawn.yPosition = this.lastScaledYPosition;
            spawn.zPosition = this.lastScaledZPosition;
        }
        else if(packet instanceof Packet26EntityExpOrb){
            Packet26EntityExpOrb spawn = (Packet26EntityExpOrb) packet;
            spawn.posX = this.lastScaledXPosition;
            spawn.posY = this.lastScaledYPosition;
            spawn.posZ = this.lastScaledZPosition;
        }

        return packet;
    }
    // --

    // Unglitch Change - Rename
    //private Packet getPacketForThisEntity()
    private Packet getPacketForThisEntity_old()
    {
       ...
    }


