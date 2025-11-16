package com.idark.valoria.registries.entity.living.boss.firron;

import com.idark.valoria.*;
import net.minecraft.resources.*;
import net.minecraft.world.entity.*;
import pro.komaru.tridot.common.registry.entity.system.*;

public class RushAttack extends AttackInstance{
    private Firron entity;

    public RushAttack(Firron mob, int attackDelay, int attackDuration, int cooldown){
        super(mob, 0, 16, attackDelay, attackDuration, cooldown);
        this.entity = mob;
    }

    @Override
    public ResourceLocation getId(){
        return Valoria.loc("firron_rush");
    }

    @Override
    public boolean canUse(LivingEntity target){
        return super.canUse(target) && this.mob.distanceToSqr(target) > 6;
    }

    @Override
    public void stop(){
        super.stop();
        this.setAttackOnCooldown();
        this.entity.stopRush();
        if(this.mob instanceof AttackSystemMob sm) sm.setActiveAttack(null);
    }

    @Override
    public void start(AttackSystemMob systemMob){
        super.start(systemMob);
        mob.setAggressive(true);
        LivingEntity target = mob.getTarget();
        if (target != null) {
            this.entity.startRush(target);
        }
    }

    @Override
    public int preference(Entity entity){
        return 0;
    }

    @Override
    public void performAttack(){
    }
}