package com.idark.valoria.registries.entity.ai.brains;

import com.google.common.collect.*;
import com.idark.valoria.registries.entity.ai.behaviour.*;
import com.idark.valoria.registries.entity.living.*;
import com.mojang.datafixers.util.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.behavior.*;
import net.minecraft.world.entity.ai.memory.*;
import net.minecraft.world.entity.ai.village.poi.*;

import java.util.*;

public class HauntedMerchantAI{
    public static ImmutableList<Pair<Integer,? extends BehaviorControl<? extends LivingEntity>>> getCorePackage(float pSpeedModifier) {
        return ImmutableList.of(Pair.of(0, InteractWithDoor.create()), Pair.of(0, new LookAtTargetSink(45, 90)), Pair.of(1, new MoveToTargetSink()), Pair.of(3, new LookAndFollowTradingPlayerSink(pSpeedModifier)), Pair.of(4, AcquirePoi.create((p_217499_) -> {
            return p_217499_.is(PoiTypes.HOME);
        }, MemoryModuleType.HOME, false, Optional.of((byte)14))));
    }

    public static ImmutableList<Pair<Integer, ? extends BehaviorControl<? super HauntedMerchant>>> getIdlePackage(float pSpeedModifier) {
        return ImmutableList.of(Pair.of(2, new RunOne<>(ImmutableList.of(Pair.of(InteractWith.of(EntityType.CAT, 8, MemoryModuleType.INTERACTION_TARGET, pSpeedModifier, 2), 1), Pair.of(VillageBoundRandomStroll.create(pSpeedModifier), 1), Pair.of(SetWalkTargetFromLookTarget.create(pSpeedModifier, 2), 1), Pair.of(new DoNothing(30, 60), 1)))), Pair.of(3, SetLookAndInteract.create(EntityType.PLAYER, 4)), Pair.of(3, new GateBehavior<>(ImmutableMap.of(), ImmutableSet.of(MemoryModuleType.INTERACTION_TARGET), GateBehavior.OrderPolicy.ORDERED, GateBehavior.RunningPolicy.RUN_ONE, ImmutableList.of(Pair.of(new TradeWithMerchant(), 1)))), Pair.of(99, UpdateActivityFromSchedule.create()));
    }

    private static Pair<Integer, BehaviorControl<LivingEntity>> getFullLookBehavior() {
        return Pair.of(5, new RunOne<>(ImmutableList.of(Pair.of(SetEntityLookTarget.create(EntityType.CAT, 8.0F), 8), Pair.of(SetEntityLookTarget.create(EntityType.VILLAGER, 8.0F), 2), Pair.of(SetEntityLookTarget.create(EntityType.PLAYER, 8.0F), 2), Pair.of(SetEntityLookTarget.create(MobCategory.CREATURE, 8.0F), 1), Pair.of(SetEntityLookTarget.create(MobCategory.WATER_CREATURE, 8.0F), 1), Pair.of(SetEntityLookTarget.create(MobCategory.AXOLOTLS, 8.0F), 1), Pair.of(SetEntityLookTarget.create(MobCategory.UNDERGROUND_WATER_CREATURE, 8.0F), 1), Pair.of(SetEntityLookTarget.create(MobCategory.WATER_AMBIENT, 8.0F), 1), Pair.of(SetEntityLookTarget.create(MobCategory.MONSTER, 8.0F), 1), Pair.of(new DoNothing(30, 60), 2))));
    }

    private static Pair<Integer, BehaviorControl<LivingEntity>> getMinimalLookBehavior() {
        return Pair.of(5, new RunOne<>(ImmutableList.of(Pair.of(SetEntityLookTarget.create(EntityType.VILLAGER, 8.0F), 2), Pair.of(SetEntityLookTarget.create(EntityType.PLAYER, 8.0F), 2), Pair.of(new DoNothing(30, 60), 8))));
    }
}
