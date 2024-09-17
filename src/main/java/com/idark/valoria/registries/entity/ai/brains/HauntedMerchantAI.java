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
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.*;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.level.GameRules;

import java.util.Optional;

public class HauntedMerchantAI {
    public static ImmutableList<Pair<Integer, ? extends BehaviorControl<? super HauntedMerchant>>> getCorePackage(float pSpeedModifier) {
        return ImmutableList.of(
                Pair.of(0, StopBeingAngryIfTargetDead.create()),
                Pair.of(1, new LookAtTargetSink(45, 90)),
                Pair.of(2, new MoveToTargetSink()),
                Pair.of(3, new TradingBehaviour(pSpeedModifier)),
                Pair.of(4, InteractWithDoor.create())
                );
    }

    protected static void setAngerTarget(HauntedMerchant mob, LivingEntity pTarget) {
        if (Sensor.isEntityAttackableIgnoringLineOfSight(mob, pTarget)) {
            mob.getBrain().eraseMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
            mob.getBrain().setMemoryWithExpiry(MemoryModuleType.ANGRY_AT, pTarget.getUUID(), 600L);
            if (pTarget.getType() == EntityType.PLAYER && mob.level().getGameRules().getBoolean(GameRules.RULE_UNIVERSAL_ANGER)) {
                mob.getBrain().setMemoryWithExpiry(MemoryModuleType.UNIVERSAL_ANGER, true, 600L);
            }
        }
    }

    private static Optional<? extends LivingEntity> findNearestValidAttackTarget(HauntedMerchant p_35087_) {
        Optional<LivingEntity> optional = BehaviorUtils.getLivingEntityFromUUIDMemory(p_35087_, MemoryModuleType.ANGRY_AT);
        if (optional.isPresent() && Sensor.isEntityAttackableIgnoringLineOfSight(p_35087_, optional.get())) {
            return optional;
        } else {
            Optional<? extends LivingEntity> optional1 = getTargetIfWithinRange(p_35087_, MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER);
            return optional1.isPresent() ? optional1 : p_35087_.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_NEMESIS);
        }
    }

    private static Optional<? extends LivingEntity> getTargetIfWithinRange(HauntedMerchant mob, MemoryModuleType<? extends LivingEntity> pMemoryType) {
        return mob.getBrain().getMemory(pMemoryType).filter((p_35108_) -> p_35108_.closerThan(mob, 12.0D));
    }

    public static void wasHurtBy(HauntedMerchant mob, LivingEntity pTarget) {
        if (!(pTarget instanceof HauntedMerchant)) {
            if (Sensor.isEntityAttackableIgnoringLineOfSight(mob, pTarget)) {
                if (!BehaviorUtils.isOtherTargetMuchFurtherAwayThanCurrentAttackTarget(mob, pTarget, 4.0D)) {
                    if (pTarget.getType() == EntityType.PLAYER && mob.level().getGameRules().getBoolean(GameRules.RULE_UNIVERSAL_ANGER)) {
                        setAngerTargetToNearestTargetablePlayerIfFound(mob, pTarget);
                    } else {
                        setAngerTarget(mob, pTarget);
                    }
                }
            }
        }
    }

    public static Optional<Player> getNearestVisibleTargetablePlayer(HauntedMerchant mob) {
        return mob.getBrain().hasMemoryValue(MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER) ? mob.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER) : Optional.empty();
    }

    private static void setAngerTargetToNearestTargetablePlayerIfFound(HauntedMerchant mob, LivingEntity pCurrentTarget) {
        Optional<Player> optional = getNearestVisibleTargetablePlayer(mob);
        if (optional.isPresent()) {
            setAngerTarget(mob, optional.get());
        } else {
            setAngerTarget(mob, pCurrentTarget);
        }
    }

    public static void updateActivity(HauntedMerchant mob) {
        Brain<HauntedMerchant> brain = mob.getBrain();
        brain.setActiveActivityToFirstValid(ImmutableList.of(Activity.FIGHT, Activity.IDLE));
        mob.setAggressive(brain.hasMemoryValue(MemoryModuleType.ATTACK_TARGET));
    }

    public static ImmutableList<Pair<Integer, ? extends BehaviorControl<? super HauntedMerchant>>> getHidePackage(float pSpeedModifier) {
        return ImmutableList.of(Pair.of(0, SetHiddenState.create(15, 3)), Pair.of(1, LocateHidingPlace.create(32, pSpeedModifier * 1.25F, 2)), getMinimalLookBehavior());
    }

    public static void initFightActivity(HauntedMerchant pMob, Brain<HauntedMerchant> pBrain) {
        pBrain.addActivityAndRemoveMemoryWhenStopped(Activity.FIGHT, 1, ImmutableList.of(StopAttackingIfTargetInvalid.create((p_219540_) -> !pMob.canTargetEntity(p_219540_)), SetEntityLookTarget.create((p_219535_) -> isTarget(pMob, p_219535_), (float) pMob.getAttributeValue(Attributes.FOLLOW_RANGE)), SetWalkTargetFromAttackTargetIfTargetOutOfReach.create(1F), MeleeAttack.create(20)), MemoryModuleType.ATTACK_TARGET);
    }

    public static boolean isTarget(HauntedMerchant pMob, LivingEntity pEntity) {
        return pMob.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).filter((p_219509_) -> p_219509_ == pEntity).isPresent();
    }

    public static ImmutableList<Pair<Integer, ? extends BehaviorControl<? super HauntedMerchant>>> getIdlePackage(float pSpeedModifier) {
        return ImmutableList.of(
                Pair.of(0, StartAttacking.create(HauntedMerchantAI::findNearestValidAttackTarget)),
                Pair.of(1, new RunOne<>(ImmutableList.of(
                        Pair.of(InteractWith.of(EntityType.CAT, 8, MemoryModuleType.INTERACTION_TARGET, pSpeedModifier, 2), 1),
                        Pair.of(RandomStroll.stroll(pSpeedModifier), 1),
                        Pair.of(SetWalkTargetFromLookTarget.create(pSpeedModifier, 2), 1),
                        Pair.of(new DoNothing(30, 60), 1)))),
                Pair.of(2, SetLookAndInteract.create(EntityType.PLAYER, 4)),
                Pair.of(3, new GateBehavior<>(ImmutableMap.of(), ImmutableSet.of(MemoryModuleType.INTERACTION_TARGET), GateBehavior.OrderPolicy.ORDERED, GateBehavior.RunningPolicy.RUN_ONE, ImmutableList.of(
                        Pair.of(new TradeWithMerchant(), 1)))),
                Pair.of(99, UpdateActivityFromSchedule.create()));
    }

    private static Pair<Integer, BehaviorControl<LivingEntity>> getMinimalLookBehavior() {
        return Pair.of(5, new RunOne<>(ImmutableList.of(Pair.of(SetEntityLookTarget.create(EntityType.PLAYER, 8.0F), 2), Pair.of(new DoNothing(30, 60), 8))));
    }
}
