    // Unglitch
    private boolean chunksLoaded = false;


    public void onUpdate()
    {
        if (this.worldObj.blockExists(MathHelper.floor_double(this.posX), 0, MathHelper.floor_double(this.posZ)))
        {
            super.onUpdate();

            // Unglitch Start - Reset vehicle position after chunks are loaded and filled
            // Without this change players in boats would glitch into the ground client-side after starting the game,
            // because the boat is loaded before the blocks. Vanilla works around this by initially spawning boats
            // at a higher position, but we correct that false position in NetClientHandler.
            if(!this.chunksLoaded){

                // check surrounding chunks, too, just to be safe
                int playerX = MathHelper.floor_double(this.posX) >> 4;
                int playerZ = MathHelper.floor_double(this.posZ) >> 4;
                boolean allLoaded = true;

                for (int x = playerX - 1 ; x <= playerX + 1 && allLoaded; x++) {
                    for (int z = playerZ - 1 ; z <= playerZ + 1 && allLoaded; z++) {
                        if(worldObj.getChunkProvider().provideChunk(x, z) instanceof EmptyChunk){
                            allLoaded = false;
                        }
                    }
                }

                if(allLoaded){
                    this.chunksLoaded = true;
                    if(this.ridingEntity != null){
                        this.ridingEntity.readjustServerPosition();
                    }
                }
            }
            // --

            if (this.isRiding())
            {
                this.sendQueue.addToSendQueue(new Packet12PlayerLook(this.rotationYaw, this.rotationPitch, this.onGround));
                this.sendQueue.addToSendQueue(new Packet27PlayerInput(this.moveStrafing, this.moveForward, this.movementInput.jump, this.movementInput.sneak));
            }
            else
            {
                this.sendMotionUpdates();
            }
        }
    }