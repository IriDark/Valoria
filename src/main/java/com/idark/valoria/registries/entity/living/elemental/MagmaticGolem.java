package com.idark.valoria.registries.entity.living.elemental;

import com.idark.valoria.registries.*;
import com.idark.valoria.registries.entity.ai.attacks.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.pathfinder.*;

public class MagmaticGolem extends AbstractElementalGolem{
    public MagmaticGolem(EntityType<? extends AbstractElementalGolem> type, Level pLevel){
        super(type, pLevel);
        this.xpReward = 5;
        this.getNavigation().setCanFloat(false);
        this.setPathfindingMalus(PathType.LAVA, 8.0F);
        this.setPathfindingMalus(PathType.DAMAGE_OTHER, 8.0F);
        this.setPathfindingMalus(PathType.POWDER_SNOW, 8.0F);

        this.selector.addAttack(new GolemMeleeAttack(this, 1, 2, 0, 10, 20));
        this.selector.addAttack(new GolemMeleeSlapAttack(this, 1, 2, 0, 10, 40));
        this.selector.addAttack(new GolemStompAttack(this, 4, 2, 0, 20, 60));
        this.selector.addAttack(new GolemMagmaGroundPunchAttack(this, 1, 4, 0, 20, 120));
    }

    public MagmaticGolem(Level pLevel){
        this(EntityTypeRegistry.MAGMATIC_GOLEM.get(), pLevel);
    }
}