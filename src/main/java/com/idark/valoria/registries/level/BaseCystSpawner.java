package com.idark.valoria.registries.level;

import com.idark.valoria.*;
import com.idark.valoria.core.network.*;
import com.idark.valoria.core.network.packets.particle.*;
import net.minecraft.core.*;
import net.minecraft.core.registries.*;
import net.minecraft.nbt.*;
import net.minecraft.server.level.*;
import net.minecraft.util.*;
import net.minecraft.util.random.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.gameevent.*;
import net.minecraft.world.phys.*;

import javax.annotation.*;
import java.util.*;

public abstract class BaseCystSpawner extends BaseSpawner{
    public int spawnDelay = 20;
    public SimpleWeightedRandomList<SpawnData> spawnPotentials = SimpleWeightedRandomList.empty();
    @Nullable
    private SpawnData nextSpawnData;
    public double spin;
    public double oSpin;
    public int minSpawnDelay = 200;
    public int maxSpawnDelay = 800;
    public int spawnCount = 4;
    /** Cached instance of the entity to render inside the spawner. */
    @Nullable
    public Entity displayEntity;
    public int maxNearbyEntities = 6;
    public int requiredPlayerRange = 16;
    public int spawnRange = 4;

    public void setEntityId(EntityType<?> pType, @Nullable Level pLevel, RandomSource pRandom, BlockPos pPos) {
        this.getOrCreateNextSpawnData(pLevel, pRandom, pPos).getEntityToSpawn().putString("id", BuiltInRegistries.ENTITY_TYPE.getKey(pType).toString());
    }

    public boolean isNearPlayer(Level pLevel, BlockPos pPos) {
        return pLevel.hasNearbyAlivePlayer((double)pPos.getX() + 0.5D, (double)pPos.getY() + 0.5D, (double)pPos.getZ() + 0.5D, (double)this.requiredPlayerRange);
    }

    public void serverTick(ServerLevel pServerLevel, BlockPos pPos) {
        if (this.isNearPlayer(pServerLevel, pPos)) {
            if (this.spawnDelay == -1) {
                this.delay(pServerLevel, pPos);
            }

            if (this.spawnDelay > 0) {
                --this.spawnDelay;
            } else {
                boolean flag = false;
                RandomSource randomsource = pServerLevel.getRandom();
                SpawnData spawndata = this.getOrCreateNextSpawnData(pServerLevel, randomsource, pPos);
                for(int i = 0; i < this.spawnCount; ++i) {
                    CompoundTag compoundtag = spawndata.getEntityToSpawn();
                    Optional<EntityType<?>> optional = EntityType.by(compoundtag);
                    if (optional.isEmpty()) {
                        this.delay(pServerLevel, pPos);
                        return;
                    }

                    ListTag listtag = compoundtag.getList("Pos", 6);
                    int j = listtag.size();
                    double d0 = j >= 1 ? listtag.getDouble(0) : (double)pPos.getX() + (randomsource.nextDouble() - randomsource.nextDouble()) * (double)this.spawnRange + 0.5D;
                    double d1 = j >= 2 ? listtag.getDouble(1) : (double)(pPos.getY() + randomsource.nextInt(3) - 1);
                    double d2 = j >= 3 ? listtag.getDouble(2) : (double)pPos.getZ() + (randomsource.nextDouble() - randomsource.nextDouble()) * (double)this.spawnRange + 0.5D;
                    if (pServerLevel.noCollision(optional.get().getAABB(d0, d1, d2))) {
                        BlockPos blockpos = BlockPos.containing(d0, d1, d2);
                        if (spawndata.getCustomSpawnRules().isPresent()) {
                            if (!optional.get().getCategory().isFriendly() && pServerLevel.getDifficulty() == Difficulty.PEACEFUL) {
                                continue;
                            }

                            SpawnData.CustomSpawnRules spawndata$customspawnrules = spawndata.getCustomSpawnRules().get();
                            if (!spawndata$customspawnrules.blockLightLimit().isValueInRange(pServerLevel.getBrightness(LightLayer.BLOCK, blockpos)) || !spawndata$customspawnrules.skyLightLimit().isValueInRange(pServerLevel.getBrightness(LightLayer.SKY, blockpos))) {
                                continue;
                            }
                        } else if (!SpawnPlacements.checkSpawnRules(optional.get(), pServerLevel, MobSpawnType.SPAWNER, blockpos, pServerLevel.getRandom())) {
                            continue;
                        }

                        Entity entity = EntityType.loadEntityRecursive(compoundtag, pServerLevel, (p_151310_) -> {
                            p_151310_.moveTo(d0, d1, d2, p_151310_.getYRot(), p_151310_.getXRot());
                            return p_151310_;
                        });
                        if (entity == null) {
                            this.delay(pServerLevel, pPos);
                            return;
                        }

                        int k = pServerLevel.getEntitiesOfClass(entity.getClass(), (new AABB((double)pPos.getX(), (double)pPos.getY(), (double)pPos.getZ(), (double)(pPos.getX() + 1), (double)(pPos.getY() + 1), (double)(pPos.getZ() + 1))).inflate((double)this.spawnRange)).size();
                        if (k >= this.maxNearbyEntities) {
                            this.delay(pServerLevel, pPos);
                            return;
                        }

                        entity.moveTo(entity.getX(), entity.getY(), entity.getZ(), randomsource.nextFloat() * 360.0F, 0.0F);
                        if (entity instanceof Mob) {
                            Mob mob = (Mob)entity;
                            if (!net.minecraftforge.event.ForgeEventFactory.checkSpawnPositionSpawner(mob, pServerLevel, MobSpawnType.SPAWNER, spawndata, this)) {
                                continue;
                            }

                            // Forge: Patch in FinalizeSpawn for spawners so it may be fired unconditionally, instead of only when vanilla normally would trigger it.
                            var event = net.minecraftforge.event.ForgeEventFactory.onFinalizeSpawnSpawner(mob, pServerLevel, pServerLevel.getCurrentDifficultyAt(entity.blockPosition()), null, compoundtag, this);
                            if (event != null && spawndata.getEntityToSpawn().size() == 1 && spawndata.getEntityToSpawn().contains("id", 8)) {
                                ((Mob)entity).finalizeSpawn(pServerLevel, event.getDifficulty(), event.getSpawnType(), event.getSpawnData(), event.getSpawnTag());
                            }
                        }

                        if (!pServerLevel.tryAddFreshEntityWithPassengers(entity)) {
                            this.delay(pServerLevel, pPos);
                            return;
                        }

                        PacketHandler.sendToTracking(pServerLevel, pPos, new CystSummonParticlePacket(entity.getId(), pPos));
                        pServerLevel.gameEvent(entity, GameEvent.ENTITY_PLACE, blockpos);
                        if (entity instanceof Mob) {
                            //((Mob)entity).spawnAnim();
                        }

                        flag = true;
                    }
                }

                if (flag) {
                    this.delay(pServerLevel, pPos);
                }

            }
        }
    }

    private void delay(Level pLevel, BlockPos pPos) {
        RandomSource randomsource = pLevel.random;
        if (this.maxSpawnDelay <= this.minSpawnDelay) {
            this.spawnDelay = this.minSpawnDelay;
        } else {
            this.spawnDelay = this.minSpawnDelay + randomsource.nextInt(this.maxSpawnDelay - this.minSpawnDelay);
        }

        this.spawnPotentials.getRandom(randomsource).ifPresent((p_186386_) -> {
            this.setNextSpawnData(pLevel, pPos, p_186386_.getData());
        });
        this.broadcastEvent(pLevel, pPos, 1);
    }

    public void load(@Nullable Level pLevel, BlockPos pPos, CompoundTag pTag) {
        this.spawnDelay = pTag.getShort("Delay");
        boolean flag = pTag.contains("SpawnData", 10);
        if (flag) {
            SpawnData spawndata = SpawnData.CODEC.parse(NbtOps.INSTANCE, pTag.getCompound("SpawnData")).resultOrPartial((p_186391_) -> {
                Valoria.LOGGER.warn("Invalid SpawnData: {}", p_186391_);
            }).orElseGet(SpawnData::new);
            this.setNextSpawnData(pLevel, pPos, spawndata);
        }

        boolean flag1 = pTag.contains("SpawnPotentials", 9);
        if (flag1) {
            ListTag listtag = pTag.getList("SpawnPotentials", 10);
            this.spawnPotentials = SpawnData.LIST_CODEC.parse(NbtOps.INSTANCE, listtag).resultOrPartial((p_186388_) -> {
                Valoria.LOGGER.warn("Invalid SpawnPotentials list: {}", p_186388_);
            }).orElseGet(SimpleWeightedRandomList::empty);
        } else {
            this.spawnPotentials = SimpleWeightedRandomList.single(this.nextSpawnData != null ? this.nextSpawnData : new SpawnData());
        }

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

        this.displayEntity = null;
    }

    public CompoundTag save(CompoundTag pTag) {
        pTag.putShort("Delay", (short)this.spawnDelay);
        pTag.putShort("MinSpawnDelay", (short)this.minSpawnDelay);
        pTag.putShort("MaxSpawnDelay", (short)this.maxSpawnDelay);
        pTag.putShort("SpawnCount", (short)this.spawnCount);
        pTag.putShort("MaxNearbyEntities", (short)this.maxNearbyEntities);
        pTag.putShort("RequiredPlayerRange", (short)this.requiredPlayerRange);
        pTag.putShort("SpawnRange", (short)this.spawnRange);
        if (this.nextSpawnData != null) {
            pTag.put("SpawnData", SpawnData.CODEC.encodeStart(NbtOps.INSTANCE, this.nextSpawnData).result().orElseThrow(() -> new IllegalStateException("Invalid SpawnData")));
        }

        pTag.put("SpawnPotentials", SpawnData.LIST_CODEC.encodeStart(NbtOps.INSTANCE, this.spawnPotentials).result().orElseThrow());
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

    protected void setNextSpawnData(@Nullable Level pLevel, BlockPos pPos, SpawnData pNextSpawnData) {
        this.nextSpawnData = pNextSpawnData;
    }

    private SpawnData getOrCreateNextSpawnData(@Nullable Level pLevel, RandomSource pRandom, BlockPos pPos) {
        if (this.nextSpawnData != null) {
            return this.nextSpawnData;
        } else {
            this.setNextSpawnData(pLevel, pPos, this.spawnPotentials.getRandom(pRandom).map(WeightedEntry.Wrapper::getData).orElseGet(SpawnData::new));
            return this.nextSpawnData;
        }
    }

    public abstract void broadcastEvent(Level pLevel, BlockPos pPos, int pEventId);

    public double getSpin() {
        return this.spin;
    }

    public double getoSpin() {
        return this.oSpin;
    }
}