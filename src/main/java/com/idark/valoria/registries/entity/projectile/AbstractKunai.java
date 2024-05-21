package com.idark.valoria.registries.entity.projectile;

import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public abstract class AbstractKunai extends AbstractArrow{

    public boolean returnToPlayer;

    public AbstractKunai(EntityType<? extends AbstractKunai> pEntityType, Level pLevel){
        super(pEntityType, pLevel);
    }

    protected AbstractKunai(EntityType<? extends AbstractKunai> pEntityType, double pX, double pY, double pZ, Level pLevel){
        this(pEntityType, pLevel);
        this.setPos(pX, pY, pZ);
    }

    protected AbstractKunai(EntityType<? extends AbstractKunai> pEntityType, LivingEntity pShooter, Level pLevel){
        this(pEntityType, pShooter.getX(), pShooter.getEyeY() - (double)0.1F, pShooter.getZ(), pLevel);
        this.setOwner(pShooter);
        if(pShooter instanceof Player){
            this.pickup = AbstractArrow.Pickup.ALLOWED;
        }
    }

    @Override
    public void onRemovedFromWorld(){
        if(this.getOwner() instanceof Player player){
            if(!player.getAbilities().instabuild){
                player.spawnAtLocation(this.getPickupItem());
            }
        }

        super.onRemovedFromWorld();
    }

    public boolean shouldReturnToThrower(){
        Entity entity = this.getOwner();
        if(entity != null && entity.isAlive()){
            return !(entity instanceof ServerPlayer) || !entity.isSpectator();
        }else{
            return false;
        }
    }

    @Nullable
    public EntityHitResult findHitEntity(@NotNull Vec3 startVec, @NotNull Vec3 endVec){
        return this.returnToPlayer ? null : super.findHitEntity(startVec, endVec);
    }

    public @NotNull SoundEvent getDefaultHitGroundSoundEvent(){
        return SoundEvents.TRIDENT_HIT_GROUND;
    }

    @Override
    public @NotNull SoundEvent getHitGroundSoundEvent(){
        return SoundEvents.TRIDENT_HIT_GROUND;
    }

    public void playerTouch(@NotNull Player pEntity){
        if(this.ownedBy(pEntity) || this.getOwner() == null){
            super.playerTouch(pEntity);
        }
    }

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket(){
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public float getWaterInertia(){
        return 0.5F;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean shouldRender(double x, double y, double z){
        return true;
    }
}