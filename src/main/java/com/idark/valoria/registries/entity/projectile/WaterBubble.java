package com.idark.valoria.registries.entity.projectile;

import com.idark.valoria.registries.*;
import net.minecraft.nbt.*;
import net.minecraft.network.protocol.*;
import net.minecraft.network.protocol.game.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.network.*;
import org.jetbrains.annotations.*;

import javax.annotation.Nullable;
import java.util.*;

public class WaterBubble extends Entity implements TraceableEntity{
    private int warmupDelayTicks;
    private boolean sentSpikeEvent;
    public int lifeTicks = 35;
    private float damage;
    private boolean clientSideAttackStarted;
    public final AnimationState disapearAnimationState = new AnimationState();
    public final AnimationState appearAnimationState = new AnimationState();

    @Nullable
    private LivingEntity owner;
    @Nullable
    private UUID ownerUUID;

    public WaterBubble(EntityType<WaterBubble> entityEntityType, Level level){
        super(entityEntityType, level);
    }

    public WaterBubble(Level pLevel, double pX, double pY, double pZ, float pYRot, int pWarmupDelay, LivingEntity pOwner){
        this(EntityTypeRegistry.WATER_BUBBLE.get(), pLevel);
        this.warmupDelayTicks = pWarmupDelay;
        this.damage = 5.0F;
        this.setOwner(pOwner);
        this.setPos(pX, pY, pZ);
        this.setYRot(pYRot);
    }

    public WaterBubble(Level pLevel, double pX, double pY, double pZ, float pYRot, int pWarmupDelay, float pDamage, LivingEntity pOwner){
        this(EntityTypeRegistry.WATER_BUBBLE.get(), pLevel);
        this.warmupDelayTicks = pWarmupDelay;
        this.damage = pDamage;
        this.setOwner(pOwner);
        this.setPos(pX, pY, pZ);
        this.setYRot(pYRot);
    }

    public WaterBubble setDamage(float damage) {
        this.damage = damage;
        return this;
    }

    @Override
    public void tick(){
        super.tick();
        if(this.level().isClientSide){
            if(this.clientSideAttackStarted){
                --this.lifeTicks;
            }

            if(this.lifeTicks == 5) {
                this.disapearAnimationState.start(tickCount);
                this.appearAnimationState.stop();
            }
        }else if(--this.warmupDelayTicks < 0){
            if(!this.sentSpikeEvent){
                this.level().broadcastEntityEvent(this, (byte)4);
                this.sentSpikeEvent = true;
            }

            if(--this.lifeTicks < 0){
                this.discard();
            }
        }

        AABB bounds = this.getBoundingBox().inflate(0.2);
        List<LivingEntity> entities = this.level().getEntitiesOfClass(LivingEntity.class, bounds, (e) -> e != this.owner || !e.isAlliedTo(this));
        for(LivingEntity entity : entities) {
            entity.hurt(this.damageSources().mobProjectile(this, owner), damage);
            entity.addEffect(new MobEffectInstance(EffectsRegistry.STUN.get(), 20, 0));
            this.discard();
        }
    }

    /**
     * Returns null or the entityliving it was ignited by
     */
    @Nullable
    public LivingEntity getOwner(){
        if(this.owner == null && this.ownerUUID != null && this.level() instanceof ServerLevel){
            Entity entity = ((ServerLevel)this.level()).getEntity(this.ownerUUID);
            if(entity instanceof LivingEntity){
                this.owner = (LivingEntity)entity;
            }
        }

        return this.owner;
    }

    public void setOwner(@Nullable LivingEntity pOwner){
        this.owner = pOwner;
        this.ownerUUID = pOwner == null ? null : pOwner.getUUID();
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readAdditionalSaveData(CompoundTag pCompound){
        this.warmupDelayTicks = pCompound.getInt("Warmup");
        if(pCompound.hasUUID("Owner")){
            this.ownerUUID = pCompound.getUUID("Owner");
        }

    }

    public void addAdditionalSaveData(CompoundTag pCompound){
        pCompound.putInt("Warmup", this.warmupDelayTicks);
        if(this.ownerUUID != null){
            pCompound.putUUID("Owner", this.ownerUUID);
        }
    }

    @Override
    protected void defineSynchedData(){
    }

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket(){
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public float getAnimationProgress(float pPartialTicks){
        if(!this.clientSideAttackStarted){
            return 0.0F;
        }else{
            int i = this.lifeTicks - 2;
            return i <= 0 ? 1.0F : 1.0F - ((float)i - pPartialTicks) / 20.0F;
        }
    }

    /**
     * Handles an entity event received from a {@link ClientboundEntityEventPacket}.
     */
    public void handleEntityEvent(byte pId){
        super.handleEntityEvent(pId);
        if(pId == 4){
            this.clientSideAttackStarted = true;
            if(!this.isSilent()){
                this.appearAnimationState.start(this.tickCount);
                this.level().playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.BUBBLE_COLUMN_BUBBLE_POP, this.getSoundSource(), 12.0F, this.random.nextFloat() * 0.2F + 0.85F, false);
            }
        }
    }
}