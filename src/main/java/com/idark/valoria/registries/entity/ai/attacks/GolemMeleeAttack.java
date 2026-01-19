package com.idark.valoria.registries.entity.ai.attacks;

import com.idark.valoria.*;
import com.idark.valoria.registries.*;
import net.minecraft.resources.*;
import net.minecraft.sounds.*;
import net.minecraft.world.entity.*;
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
    public SoundEvent getAttackSound(){
        return SoundsRegistry.ELEMENTAL_GOLEM_ATTACK_1.get();
    }
}
