package com.idark.valoria.registries.entity.living.elemental;

import com.idark.valoria.registries.*;
import com.idark.valoria.registries.entity.ai.attacks.*;
import net.minecraft.core.*;
import net.minecraft.tags.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.pathfinder.*;
import pro.komaru.tridot.common.registry.entity.system.generic.*;

public class RiverGolem extends AbstractElementalGolem{
    public RiverGolem(EntityType<? extends AbstractElementalGolem> type, Level pLevel){
        super(type, pLevel);
        this.xpReward = 5;
        this.getNavigation().setCanFloat(false);
        this.setPathfindingMalus(BlockPathTypes.LAVA, 8.0F);
        this.setPathfindingMalus(BlockPathTypes.DAMAGE_OTHER, 8.0F);
        this.setPathfindingMalus(BlockPathTypes.POWDER_SNOW, 8.0F);

        this.selector.addAttack(new TridotMeleeAttack(this, 1, 2, 0, 10, 20));
        this.selector.addAttack(new GolemMeleeAttack(this, 1, 2, 0, 10, 40));
        this.selector.addAttack(new GolemStompAttack(this, 4, 2, 0, 20, 60));
        this.selector.addAttack(new GolemGroundPunchAttack(this, 1, 4, 0, 15, 120));
    }

    public RiverGolem(Level pLevel){
        this(EntityTypeRegistry.RIVER_GOLEM.get(), pLevel);
    }

    public static boolean checkSpawnRules(EntityType<RiverGolem> pEntity, LevelAccessor pLevel, MobSpawnType pSpawnType, BlockPos pPos, RandomSource pRandom){
        return pLevel.getBlockState(pPos.below()).is(BlockTags.ANIMALS_SPAWNABLE_ON);
    }
}
