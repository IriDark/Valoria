package com.idark.valoria.registries.entity.projectile;

import com.idark.valoria.registries.*;
import net.minecraft.core.particles.*;
import net.minecraft.nbt.*;
import net.minecraft.network.protocol.*;
import net.minecraft.network.protocol.game.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.level.*;
import net.minecraftforge.network.*;
import org.jetbrains.annotations.*;

import javax.annotation.Nullable;
import java.util.*;

public class CrystalSpikes extends Entity implements TraceableEntity{
    private int warmupDelayTicks;
    private boolean sentSpikeEvent;
    private int lifeTicks = 35;
    private float damage;
    public final AnimationState disapearAnimationState = new AnimationState();
    public final AnimationState appearAnimationState = new AnimationState();
    private boolean clientSideAttackStarted;
    @Nullable
    private LivingEntity owner;
    @Nullable
    private UUID ownerUUID;

    public CrystalSpikes(EntityType<CrystalSpikes> entityEntityType, Level level){
        super(entityEntityType, level);
    }

    public CrystalSpikes(Level pLevel, double pX, double pY, double pZ, float pYRot, int pWarmupDelay, LivingEntity pOwner){
        this(EntityTypeRegistry.CRYSTAL_SPIKES.get(), pLevel);
        this.warmupDelayTicks = pWarmupDelay;
        this.damage = 10.0F;
        this.setOwner(pOwner);
        this.setPos(pX, pY, pZ);
        this.setYRot(pYRot);
    }

    public CrystalSpikes(Level pLevel, double pX, double pY, double pZ, float pYRot, int pWarmupDelay, float pDamage, LivingEntity pOwner){
        this(EntityTypeRegistry.CRYSTAL_SPIKES.get(), pLevel);
        this.warmupDelayTicks = pWarmupDelay;
        this.damage = pDamage;
        this.setOwner(pOwner);
        this.setPos(pX, pY, pZ);
        this.setYRot(pYRot);
    }

    public CrystalSpikes setDamage(float damage) {
        this.damage = damage;
        return this;
    }

    @Override
    public void tick(){
        super.tick();
        if(this.level().isClientSide){
            if(this.clientSideAttackStarted){
                --this.lifeTicks;
                if(this.lifeTicks == 14){
                    for(int i = 0; i < 12; ++i){
                        this.level().addParticle(ParticleTypes.END_ROD, this.getX() - 1f + this.random.nextDouble() * 2, this.getY() + 1.25D + this.random.nextDouble(), this.getZ() - 1f + this.random.nextDouble() * 2, 0, 0, 0);
                    }
                }

                if(this.lifeTicks == 10) {
                    this.disapearAnimationState.start(tickCount);
                }
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
    public void playerTouch(Player pPlayer){
        super.playerTouch(pPlayer);
        if(pPlayer.getTeam() != null ? this.isAlliedTo(pPlayer) : pPlayer.getType().getCategory().equals(MobCategory.MONSTER)){
            return;
        }

        pPlayer.hurt(this.damageSources().indirectMagic(this, this), damage);
    }

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket(){
        return NetworkHooks.getEntitySpawningPacket(this);
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
                this.level().playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.AMETHYST_BLOCK_CHIME, this.getSoundSource(), 1.0F, this.random.nextFloat() * 0.2F + 0.85F, false);
            }
        }

    }

    public float getAnimationProgress(float pPartialTicks){
        if(!this.clientSideAttackStarted){
            return 0.0F;
        }else{
            int i = this.lifeTicks - 2;
            return i <= 0 ? 1.0F : 1.0F - ((float)i - pPartialTicks) / 20.0F;
        }
    }
}