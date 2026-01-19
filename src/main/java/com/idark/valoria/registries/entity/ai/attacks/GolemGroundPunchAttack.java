package com.idark.valoria.registries.entity.ai.attacks;

import com.idark.valoria.*;
import com.idark.valoria.registries.*;
import net.minecraft.resources.*;
import net.minecraft.sounds.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.targeting.*;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.phys.*;
import pro.komaru.tridot.api.*;
import pro.komaru.tridot.common.registry.entity.system.*;
import pro.komaru.tridot.common.registry.entity.system.generic.*;

import java.util.*;

public class GolemGroundPunchAttack extends TridotMeleeAttack{
    private final TargetingConditions targeting;

    public GolemGroundPunchAttack(PathfinderMob mob, float speedMod, float range, int attackDelay, int attackDuration, int cooldown){
        super(mob, speedMod, range, attackDelay, attackDuration, cooldown);
        this.targeting = TargetingConditions.forCombat().range(range).ignoreLineOfSight().ignoreInvisibilityTesting();
    }

    @Override
    public ResourceLocation getId(){
        return Valoria.loc("ground_punch");
    }

    @Override
    public SoundEvent getAttackSound(){
        return SoundsRegistry.ELEMENTAL_GOLEM_ATTACK_4.get();
    }

    @Override
    public void start(AttackSystemMob systemMob){
        mob.setAggressive(true);
        mob.level().broadcastEntityEvent(mob, (byte)64);

        this.ticksUntilNextPathRecalc = 0;
        this.mob.getNavigation().moveTo(mob.getTarget(), speedModifier);
        storeTargetPosition();
    }

    @Override
    public void performAttack(){
        Vec3 vec3 = new Vec3(mob.getX(), mob.getY(), mob.getZ());
        List<LivingEntity> entities = mob.level().getNearbyEntities(LivingEntity.class, this.targeting, mob, mob.getBoundingBox().inflate(range));
        for(LivingEntity entity : entities){
            double distance = Math.sqrt(entity.distanceToSqr(vec3)) / range;
            double dX = entity.getX() - vec3.x;
            double dY = entity.getEyeY() - vec3.y;
            double dZ = entity.getZ() - vec3.z;
            double sqrt = Math.sqrt(dX * dX + dY * dY + dZ * dZ);
            if(sqrt != 0.0D){
                dX /= sqrt;
                dY /= sqrt;
                dZ /= sqrt;
                double seenPercent = Utils.Hit.seenPercent(vec3, entity, 2);
                double power = (1.0D - distance) * seenPercent;
                double powerAfterDamp = ProtectionEnchantment.getExplosionKnockbackAfterDampener(entity, power);
                dX *= powerAfterDamp;
                dY *= powerAfterDamp;
                dZ *= powerAfterDamp;
                Vec3 vec31 = new Vec3(dX * 2, dY * 0.5f, dZ * 2);

                entity.hurtMarked = true; //Sync movements
                mob.doHurtTarget(entity);
                entity.addEffect(new MobEffectInstance(EffectsRegistry.STUN.get(), 30));
                entity.setDeltaMovement(entity.getDeltaMovement().add(vec31));
            }
        }
    }
}
