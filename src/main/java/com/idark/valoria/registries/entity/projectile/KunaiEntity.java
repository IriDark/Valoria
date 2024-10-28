package com.idark.valoria.registries.entity.projectile;

import com.idark.valoria.registries.*;
import net.minecraft.core.particles.*;
import net.minecraft.network.protocol.*;
import net.minecraft.network.protocol.game.*;
import net.minecraft.sounds.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.network.*;
import org.jetbrains.annotations.*;

import javax.annotation.Nullable;

public class KunaiEntity extends AbstractSupplierProjectile {
    public float rotationVelocity = 0;
    public boolean returnToPlayer;
    public KunaiEntity(EntityType<? extends KunaiEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public KunaiEntity(LivingEntity pShooter, Level pLevel, ItemStack thrownStackIn) {
        super(EntityTypeRegistry.KUNAI.get(), pLevel, pShooter, thrownStackIn);
        this.setPos(pShooter.getX(), pShooter.getEyeY() - (double) 0.1F, pShooter.getZ());
        this.setOwner(pShooter);
        if (pShooter instanceof Player) {
            this.pickup = AbstractArrow.Pickup.ALLOWED;
        }
    }

    @Nullable
    public EntityHitResult findHitEntity(@NotNull Vec3 startVec, @NotNull Vec3 endVec) {
        return this.returnToPlayer ? null : super.findHitEntity(startVec, endVec);
    }

    public @NotNull SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.TRIDENT_HIT_GROUND;
    }

    @Override
    public @NotNull SoundEvent getHitGroundSoundEvent() {
        return SoundEvents.TRIDENT_HIT_GROUND;
    }

    @Override
    public SoundEvent getReturnSound() {
        return SoundEvents.TRIDENT_RETURN;
    }

    public void spawnParticlesTrail(){
        if(this.shouldRender(this.getX(), this.getY(), this.getZ()) && !this.inGround){
            Vec3 vector3d = this.getDeltaMovement();
            double a3 = vector3d.x;
            double a4 = vector3d.y;
            double a0 = vector3d.z;
            for(int a = 0; a < 3; ++a){
                this.level().addParticle(ParticleTypes.WHITE_ASH, this.getX() + a3 * (double)a / 4.0D, this.getY() + a4 * (double)a / 4.0D, this.getZ() + a0 * (double)a / 4.0D, -a3, -a4 + 0.2D, -a0);
            }
        }
    }

    public void playerTouch(@NotNull Player pEntity) {
        if (this.ownedBy(pEntity) || this.getOwner() == null) {
            super.playerTouch(pEntity);
        }
    }

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public float getWaterInertia() {
        return 0.5F;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean shouldRender(double x, double y, double z) {
        return true;
    }
}