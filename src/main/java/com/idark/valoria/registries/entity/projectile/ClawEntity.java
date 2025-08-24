package com.idark.valoria.registries.entity.projectile;

import com.idark.valoria.registries.*;
import com.idark.valoria.util.*;
import net.minecraft.sounds.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.navigation.*;
import net.minecraft.world.entity.boss.enderdragon.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.api.distmarker.*;
import org.jetbrains.annotations.*;
import pro.komaru.tridot.client.gfx.*;
import pro.komaru.tridot.client.gfx.particle.*;
import pro.komaru.tridot.client.gfx.particle.data.*;
import pro.komaru.tridot.client.render.*;
import pro.komaru.tridot.common.registry.entity.*;

public class ClawEntity extends AbstractProjectile{
    public ClawEntity(EntityType<? extends AbstractProjectile> pEntityType, Level pLevel){
        super(pEntityType, pLevel);
    }

    public ClawEntity(LivingEntity pShooter, Level pLevel){
        super(EntityTypeRegistry.CLAW.get(), pLevel);
        this.setPos(pShooter.getX(), pShooter.getEyeY() - (double)0.1F, pShooter.getZ());
        this.setOwner(pShooter);
    }

    public @NotNull SoundEvent getDefaultHitGroundSoundEvent(){
        return SoundEvents.GENERIC_SPLASH;
    }

    @Override
    public @NotNull SoundEvent getHitGroundSoundEvent(){
        return SoundEvents.GENERIC_SPLASH;
    }

    protected void onHit(HitResult pResult){
        super.onHit(pResult);
        this.discard();
    }

    @Override
    public void onHitEntity(EntityHitResult result){
        if(level().isClientSide) return;
        super.onHitEntity(result);
        Entity entity = result.getEntity();
        Entity owner = this.getOwner();
        if(owner instanceof LivingEntity pLivingEntity){
            if(entity instanceof LivingEntity target && pLivingEntity.canAttack(target)){
                if(entity instanceof Mob mob && mob.getNavigation() instanceof FlyingPathNavigation) return;
                if(entity instanceof EnderDragon || entity instanceof AbstractBoss) return;
                Vec3 direction = pLivingEntity.position().subtract(entity.position()).normalize();
                double strength = Mth.clamp(pLivingEntity.distanceTo(entity) * 0.25D, 0.5, 4);

                entity.setDeltaMovement(direction.scale(strength));
                entity.hurtMarked = true;
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void spawnParticlesTrail(){
        if(this.shouldRender(this.getX(), this.getY(), this.getZ()) && !this.inGround){
            ParticleBuilder.create(TridotParticles.WISP)
            .setRenderType(TridotRenderTypes.TRANSLUCENT_PARTICLE)
            .setScaleData(GenericParticleData.create(0.35f).build())
            .setTransparencyData(GenericParticleData.create(1, 0.4f, 0).build())
            .setColorData(ColorParticleData.create(Pal.darkRed).build())
            .setGravity(0)
            .setLifetime(5)
            .spawn(this.level(), this.getX(), this.getY(), this.getZ());
        }
    }
}
