package com.idark.valoria.registries.entity.ai.attacks;

import com.idark.valoria.*;
import net.minecraft.resources.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import pro.komaru.tridot.common.registry.entity.system.*;
import pro.komaru.tridot.common.registry.entity.system.generic.*;

public class EntMeleeSlapAttack extends TridotMeleeAttack{
    public EntMeleeSlapAttack(PathfinderMob mob, float speedMod, float range, int attackDelay, int attackDuration, int cooldown){
        super(mob, speedMod, range, attackDelay, attackDuration, cooldown);
    }

    @Override
    public ResourceLocation getId(){
        return Valoria.loc("ent_melee_slap_attack");
    }

    @Override
    public void start(AttackSystemMob systemMob){
        mob.setAggressive(true);
        mob.level().broadcastEntityEvent(mob, (byte)62);

        this.ticksUntilNextPathRecalc = 0;
        this.mob.getNavigation().moveTo(mob.getTarget(), speedModifier);
        storeTargetPosition();
    }

    @Override
    public void performAttack(){
        super.performAttack();
        LivingEntity target = mob.getTarget();
        if (target != null && target.isAlive()){
            float f = (float)mob.getAttributeValue(Attributes.ATTACK_DAMAGE);
            float f1 = (int)f > 0 ? f / 2.0F + (float)mob.level().random.nextInt((int)f) : f;

            boolean flag = target.hurt(mob.damageSources().mobAttack(mob), f1);
            if(flag){
                double d0 = target.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
                double d1 = Math.max(0.0D, 1.0D - d0);
                target.setDeltaMovement(target.getDeltaMovement().add(0.0D, (double)0.2F * d1, 0.0D));
                mob.doEnchantDamageEffects(mob, target);
            }
        }
    }
}
