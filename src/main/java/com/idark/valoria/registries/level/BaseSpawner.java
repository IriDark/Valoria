package com.idark.valoria.registries.level;

import net.minecraft.core.*;
import net.minecraft.nbt.*;
import net.minecraft.server.level.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;

import javax.annotation.*;

//todo delete
public abstract class BaseSpawner{
    public int spawnDelay = 20;
    public int minSpawnDelay = 200;
    public int maxSpawnDelay = 800;
    public int spawnCount = 4;
    public int maxNearbyEntities = 6;
    public int requiredPlayerRange = 16;
    public int spawnRange = 4;
    public EntityType<?> entityType;

    public EntityType<?> getEntityType(){
        return entityType;
    }

    public boolean isNearPlayer(Level pLevel, BlockPos pPos) {
        return pLevel.hasNearbyAlivePlayer((double)pPos.getX() + 0.5D, (double)pPos.getY() + 0.5D, (double)pPos.getZ() + 0.5D, this.requiredPlayerRange);
    }

    public void clientTick(Level pLevel, BlockPos pPos) {

    }

    public void serverTick(ServerLevel pServerLevel, BlockPos pPos) {
        if (this.isNearPlayer(pServerLevel, pPos)) {
            if (this.spawnDelay == -1) {
                this.delay(pServerLevel, pPos);
            }

            if (this.spawnDelay > 0) {
                --this.spawnDelay;
            } else {
                RandomSource randomsource = pServerLevel.getRandom();
                for(int i = 0; i < this.spawnCount; ++i){
                    var entity = getEntityType().create(pServerLevel);
                    if(entity != null){
                        double d0 = (double)pPos.getX() + (randomsource.nextDouble() - randomsource.nextDouble()) * (double)this.spawnRange + 0.5D;
                        double d1 = pPos.getY() + randomsource.nextInt(3) - 1;
                        double d2 = (double)pPos.getZ() + (randomsource.nextDouble() - randomsource.nextDouble()) * (double)this.spawnRange + 0.5D;

                        entity.moveTo(d0, d1, d2, 0, 0);
                        onEntityConfiguration(entity, pPos);
                        if(pServerLevel.noCollision(getEntityType().getAABB(d0, d1, d2))){
                            if(skipSpawnReason(pServerLevel)){
                                this.delay(pServerLevel, pPos);
                                return;
                            }

                            int k = pServerLevel.getEntitiesOfClass(entity.getClass(), (new AABB(pPos.getX(), pPos.getY(), pPos.getZ(), pPos.getX() + 1, pPos.getY() + 1, pPos.getZ() + 1)).inflate(this.spawnRange)).size();
                            if(k >= this.maxNearbyEntities){
                                this.delay(pServerLevel, pPos);
                                return;
                            }

                            pServerLevel.addFreshEntity(entity);
                            onEntitySpawn(pServerLevel, entity, pPos);
                            this.delay(pServerLevel, pPos);
                        }
                    }
                }
            }
        }
    }

    public boolean skipSpawnReason(ServerLevel pServerLevel) {
        return getEntityType().getCategory().isFriendly() && pServerLevel.getDifficulty() == Difficulty.PEACEFUL;
    };

    /**
     * Called on configuration of the Entity
     */
    public abstract void onEntityConfiguration(Entity entity, BlockPos pPos);

    /**
     * Called on Entity spawn finishing, this can be onSpawn particles for example
     */
    public abstract void onEntitySpawn(ServerLevel pServerLevel, Entity entity, BlockPos pPos);

    private void delay(Level pLevel, BlockPos pPos) {
        RandomSource randomsource = pLevel.random;
        if (this.maxSpawnDelay <= this.minSpawnDelay) {
            this.spawnDelay = this.minSpawnDelay;
        } else {
            this.spawnDelay = this.minSpawnDelay + randomsource.nextInt(this.maxSpawnDelay - this.minSpawnDelay);
        }

        this.broadcastEvent(pLevel, pPos, 1);
    }

    public void load(@Nullable Level pLevel, BlockPos pPos, CompoundTag pTag) {
        this.spawnDelay = pTag.getShort("Delay");
        if (pTag.contains("MinSpawnDelay", 99)) {
            this.minSpawnDelay = pTag.getShort("MinSpawnDelay");
            this.maxSpawnDelay = pTag.getShort("MaxSpawnDelay");
            this.spawnCount = pTag.getShort("SpawnCount");
        }

        if (pTag.contains("MaxNearbyEntities", 99)) {
            this.maxNearbyEntities = pTag.getShort("MaxNearbyEntities");
            this.requiredPlayerRange = pTag.getShort("RequiredPlayerRange");
        }

        if (pTag.contains("SpawnRange", 99)) {
            this.spawnRange = pTag.getShort("SpawnRange");
        }
    }

    public CompoundTag save(CompoundTag pTag) {
        pTag.putShort("Delay", (short)this.spawnDelay);
        pTag.putShort("MinSpawnDelay", (short)this.minSpawnDelay);
        pTag.putShort("MaxSpawnDelay", (short)this.maxSpawnDelay);
        pTag.putShort("SpawnCount", (short)this.spawnCount);
        pTag.putShort("MaxNearbyEntities", (short)this.maxNearbyEntities);
        pTag.putShort("RequiredPlayerRange", (short)this.requiredPlayerRange);
        pTag.putShort("SpawnRange", (short)this.spawnRange);
        return pTag;
    }

    public boolean onEventTriggered(Level pLevel, int pId) {
        if (pId == 1) {
            if (pLevel.isClientSide) {
                this.spawnDelay = this.minSpawnDelay;
            }

            return true;
        } else {
            return false;
        }
    }

    public abstract void broadcastEvent(Level pLevel, BlockPos pPos, int pEventId);
}