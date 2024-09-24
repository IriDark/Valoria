package com.idark.valoria.registries.entity.projectile;

import com.idark.valoria.registries.*;
import net.minecraft.sounds.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import org.jetbrains.annotations.*;

public class ThrownSpearEntity extends AbstractSupplierProjectile {
    public float rotationVelocity = 50;
    private float explosive_radius;
    private boolean shouldExplode;
    private boolean isExploded;
    private Level.ExplosionInteraction interaction;
    public ThrownSpearEntity(Level worldIn, LivingEntity thrower, ItemStack thrownStackIn) {
        super(EntityTypeRegistry.SPEAR.get(), worldIn, thrower, thrownStackIn);
    }

    public ThrownSpearEntity(EntityType<? extends ThrownSpearEntity> type, Level worldIn) {
        super(type, worldIn);
    }

    public @NotNull SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundsRegistry.SPEAR_GROUND_IMPACT.get();
    }

    @Override
    public @NotNull SoundEvent getHitGroundSoundEvent() {
        return SoundsRegistry.SPEAR_GROUND_IMPACT.get();
    }

    @Override
    public SoundEvent getReturnSound() {
        return SoundsRegistry.SPEAR_RETURN.get();
    }

    public void setExplode(Level.ExplosionInteraction pInteraction, float radius) {
        this.shouldExplode = true;
        interaction = pInteraction;
        explosive_radius = radius;
    }

    protected void onHit(HitResult pResult) {
        super.onHit(pResult);
        if (this.shouldExplode && !this.isExploded) {
            if (!this.level().isClientSide) {
                this.level().explode(this, this.getX(), this.getY(), this.getZ(), explosive_radius, interaction);
            }

            this.isExploded = true;
        }
    }
}