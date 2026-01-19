package com.idark.valoria.registries.entity.ai.attacks;

import com.idark.valoria.*;
import net.minecraft.resources.*;
import net.minecraft.world.entity.*;
import pro.komaru.tridot.common.registry.entity.system.*;
import pro.komaru.tridot.common.registry.entity.system.generic.*;

public class GolemMeleeAttack extends TridotMeleeAttack{
    public GolemMeleeAttack(PathfinderMob mob, float speedMod, float range, int attackDelay, int attackDuration, int cooldown){
        super(mob, speedMod, range, attackDelay, attackDuration, cooldown);
    }

    @Override
    public ResourceLocation getId(){
        return Valoria.loc("golem_melee_attack");
    }

    @Override
    public void start(AttackSystemMob systemMob){
        mob.setAggressive(true);
        mob.level().broadcastEntityEvent(mob, (byte)61);

        this.ticksUntilNextPathRecalc = 0;
        this.mob.getNavigation().moveTo(mob.getTarget(), speedModifier);
        storeTargetPosition();
    }

}
