package com.idark.valoria.registries.entity.ai.brains;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.idark.valoria.registries.entity.ai.behaviour.TradeWithMerchant;
import com.idark.valoria.registries.entity.ai.behaviour.TradingBehaviour;
import com.idark.valoria.registries.entity.living.HauntedMerchant;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.*;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

public class HauntedMerchantAI {
    public static ImmutableList<Pair<Integer, ? extends BehaviorControl<? super HauntedMerchant>>> getCorePackage(float pSpeedModifier) {
        return ImmutableList.of(
                Pair.of(0, InteractWithDoor.create()),
                Pair.of(1, new LookAtTargetSink(45, 90)),
                Pair.of(2, new MoveToTargetSink()),
                Pair.of(3, new TradingBehaviour(pSpeedModifier))
        );
    }

    public static ImmutableList<Pair<Integer, ? extends BehaviorControl<? super HauntedMerchant>>> getHidePackage(float pSpeedModifier) {
        return ImmutableList.of(Pair.of(0, SetHiddenState.create(15, 3)), Pair.of(1, LocateHidingPlace.create(32, pSpeedModifier * 1.25F, 2)), getMinimalLookBehavior());
    }

    public static ImmutableList<Pair<Integer, ? extends BehaviorControl<? super HauntedMerchant>>> getIdlePackage(float pSpeedModifier) {
        return ImmutableList.of(
                Pair.of(0, new RunOne<>(ImmutableList.of(
                        Pair.of(InteractWith.of(EntityType.CAT, 8, MemoryModuleType.INTERACTION_TARGET, pSpeedModifier, 2), 1),
                        Pair.of(RandomStroll.stroll(pSpeedModifier), 1),
                        Pair.of(SetWalkTargetFromLookTarget.create(pSpeedModifier, 2), 1),
                        Pair.of(new DoNothing(30, 60), 1)))),

                Pair.of(1, SetLookAndInteract.create(EntityType.PLAYER, 4)),
                Pair.of(2, new GateBehavior<>(ImmutableMap.of(), ImmutableSet.of(MemoryModuleType.INTERACTION_TARGET), GateBehavior.OrderPolicy.ORDERED, GateBehavior.RunningPolicy.RUN_ONE, ImmutableList.of(
                        Pair.of(new TradeWithMerchant(), 1)))),


                Pair.of(99, UpdateActivityFromSchedule.create()));
    }

    private static Pair<Integer, BehaviorControl<LivingEntity>> getMinimalLookBehavior() {
        return Pair.of(5, new RunOne<>(ImmutableList.of(Pair.of(SetEntityLookTarget.create(EntityType.PLAYER, 8.0F), 2), Pair.of(new DoNothing(30, 60), 8))));
    }
}
