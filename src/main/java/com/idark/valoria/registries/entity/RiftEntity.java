package com.idark.valoria.registries.entity;

import com.idark.valoria.api.events.*;
import com.idark.valoria.registries.*;
import net.minecraft.core.*;
import net.minecraft.core.particles.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.protocol.*;
import net.minecraft.network.protocol.game.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.level.*;
import net.minecraftforge.common.*;
import net.minecraftforge.entity.*;
import net.minecraftforge.network.*;
import org.jetbrains.annotations.*;

import javax.annotation.Nullable;
import java.util.*;

public class RiftEntity extends Entity implements TraceableEntity, IEntityAdditionalSpawnData{
    @Nullable private LivingEntity owner;
    @Nullable private UUID ownerUUID;

    @Nullable private RiftEntity connection;
    @Nullable private UUID connectionUUID;
    private int lifeTime = 1200;
    private int maxLifeTime = 1200;

    public RiftEntity(EntityType<RiftEntity> entityEntityType, Level level){
        super(entityEntityType, level);
    }

    public RiftEntity(Level pLevel, double pX, double pY, double pZ, float pYRot, LivingEntity pOwner){
        this(EntityTypeRegistry.RIFT.get(), pLevel);
        this.setOwner(pOwner);
        this.setPos(pX, pY, pZ);
        this.setYRot(pYRot);
    }

    public void setLifeTime(int pLifeTime){
        this.lifeTime = pLifeTime;
        this.maxLifeTime = pLifeTime;
    }

    public int getMaxLifeTime(){
        return maxLifeTime;
    }

    public int getLifeTime() {
        return this.lifeTime;
    }

    public void setConnection(UUID connection) {
        this.connectionUUID = connection;
    }

    public void setConnection(RiftEntity connection) {
        this.connection = connection;
        this.connectionUUID = connection.getUUID();
    }

    @Override
    public void tick(){
        super.tick();
        var pRandom = this.level().random;
        var pLevel = this.level();
        var pPos = this.blockPosition();
        if(this.level().isClientSide){
            if(pRandom.nextInt(100) == 0){
                pLevel.playLocalSound((double)pPos.getX() + 0.5D, (double)pPos.getY() + 0.5D, (double)pPos.getZ() + 0.5D, SoundEvents.PORTAL_AMBIENT, SoundSource.BLOCKS, 0.25F, pRandom.nextFloat() * 0.4F + 1.2F, false);
            }

            for(int i = 0; i < 4; ++i){
                double d0 = (double)pPos.getX() + pRandom.nextDouble();
                double d1 = (double)pPos.getY() + pRandom.nextDouble();
                double d2 = (double)pPos.getZ() + pRandom.nextDouble();
                double d3 = ((double)pRandom.nextFloat() - 0.5D) * 0.5D;
                double d4 = ((double)pRandom.nextFloat() - 0.5D) * 0.5D;
                double d5 = ((double)pRandom.nextFloat() - 0.5D) * 0.5D;
                pLevel.addParticle(ParticleTypes.PORTAL, d0, d1, d2, d3, d4, d5);
            }
        }

        if(--this.lifeTime < 0){
            this.discard();
        }
    }

    /**
     * Returns null or the connection it's linked to
     */
    @Nullable
    public RiftEntity getConnection(){
        if(this.connection == null && this.connectionUUID != null && this.level() instanceof ServerLevel serverLevel){
            Entity entity = serverLevel.getEntity(this.connectionUUID);
            if (entity == null) {
                for (ServerLevel level : serverLevel.getServer().getAllLevels()) {
                    if (level != serverLevel) {
                        entity = level.getEntity(this.connectionUUID);
                        if (entity != null) {
                            this.discard();
                            break;
                        }
                    }
                }
            }

            if(entity instanceof RiftEntity riftEntity){
                this.connection = riftEntity;
            }
        }

        return this.connection;
    }

    /**
     * Returns null or the entityliving it was ignited by
     */
    @Nullable
    public LivingEntity getOwner(){
        if(this.owner == null && this.ownerUUID != null && this.level() instanceof ServerLevel serverLevel){
            Entity entity = serverLevel.getEntity(this.ownerUUID);
            if(entity instanceof LivingEntity livingEntity){
                this.owner = livingEntity;
            }
        }

        return this.owner;
    }

    public void setOwner(@Nullable LivingEntity pOwner){
        this.owner = pOwner;
        this.ownerUUID = pOwner == null ? null : pOwner.getUUID();
    }

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket(){
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void playerTouch(Player pPlayer){
        super.playerTouch(pPlayer);
        if(pPlayer.isOnPortalCooldown()){
            pPlayer.setPortalCooldown();
        }else if(this.getConnection() != null) {
            BlockPos pos = this.getConnection().getOnPos().above();
            Level targetLevel = this.getConnection().level();

            RiftTeleportEvent event = new RiftTeleportEvent(pPlayer, this, this.getConnection(), pos.getX(), pos.getY(), pos.getZ(), targetLevel);
            if (MinecraftForge.EVENT_BUS.post(event)) return;

            targetLevel = event.getTargetLevel();
            if (targetLevel == this.level()) {
                pPlayer.teleportTo(event.getTargetX(), event.getTargetY(), event.getTargetZ());
            } else if (pPlayer instanceof ServerPlayer serverPlayer && targetLevel instanceof ServerLevel targetServerLevel) {
                serverPlayer.teleportTo(targetServerLevel, event.getTargetX(), event.getTargetY(), event.getTargetZ(), serverPlayer.getYRot(), serverPlayer.getXRot());
            }
            
            pPlayer.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 30, 0, false, false));
            pPlayer.level().playSound(null, pPlayer.blockPosition(), SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 1.0F, 1.0F);
            
            pPlayer.setPortalCooldown();
        }
    }

    @Override
    protected void defineSynchedData(){

    }

    @Override
    public void writeSpawnData(FriendlyByteBuf buffer) {
        buffer.writeInt(this.maxLifeTime);
        buffer.writeInt(this.lifeTime);
    }

    @Override
    public void readSpawnData(FriendlyByteBuf buffer) {
        this.maxLifeTime = buffer.readInt();
        this.lifeTime = buffer.readInt();
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound){
        this.maxLifeTime = pCompound.getInt("MaxLifeTime");
        this.lifeTime = pCompound.getInt("LifeTime");
        if(pCompound.hasUUID("Owner")){
            this.ownerUUID = pCompound.getUUID("Owner");
        }

        if(pCompound.hasUUID("Connection")){
            this.connectionUUID = pCompound.getUUID("Connection");
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound){
        pCompound.putInt("MaxLifeTime", maxLifeTime);
        pCompound.putInt("LifeTime", lifeTime);
        if(this.ownerUUID != null){
            pCompound.putUUID("Owner", this.ownerUUID);
        }

        if(this.connectionUUID != null){
            pCompound.putUUID("Connection", this.connectionUUID);
        }
    }
}