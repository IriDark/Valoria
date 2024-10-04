package com.idark.valoria.registries.entity.ai.brains;

import com.google.common.collect.*;
import com.idark.valoria.registries.entity.ai.behaviour.*;
import com.idark.valoria.registries.entity.living.*;
import com.mojang.datafixers.util.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.*;
import net.minecraft.world.entity.ai.behavior.*;
import net.minecraft.world.entity.ai.memory.*;
import net.minecraft.world.entity.schedule.*;

public class HauntedMerchantAI {
    public static ImmutableList<Pair<Integer, ? extends BehaviorControl<? super AbstractHauntedMerchant>>> getCorePackage(float pSpeedModifier) {
        return ImmutableList.of(
        Pair.of(0, new LookAtTargetSink(45, 90)),
        Pair.of(1, new MoveToTargetSink()),
        Pair.of(2, new TradingBehaviour(pSpeedModifier))
        );
    }

    public static void updateActivity(AbstractHauntedMerchant mob) {
        Brain<AbstractHauntedMerchant> brain = mob.getBrain();
        brain.setActiveActivityToFirstValid(ImmutableList.of(Activity.IDLE));
    }

    public static ImmutableList<Pair<Integer, ? extends BehaviorControl<? super AbstractHauntedMerchant>>> getIdlePackage(float pSpeedModifier) {
        return ImmutableList.of(
                Pair.of(1, new RunOne<>(ImmutableList.of(
                        Pair.of(InteractWith.of(EntityType.CAT, 8, MemoryModuleType.INTERACTION_TARGET, pSpeedModifier, 2), 1),
                        Pair.of(SetWalkTargetFromLookTarget.create(pSpeedModifier, 2), 1),
                        Pair.of(new DoNothing(30, 60), 1)))),
                Pair.of(2, SetLookAndInteract.create(EntityType.PLAYER, 4)),
                Pair.of(3, new GateBehavior<>(ImmutableMap.of(), ImmutableSet.of(MemoryModuleType.INTERACTION_TARGET), GateBehavior.OrderPolicy.ORDERED, GateBehavior.RunningPolicy.RUN_ONE, ImmutableList.of(Pair.of(new TradeWithMerchant(), 1)))),
                Pair.of(99, UpdateActivityFromSchedule.create()));
    }
}
