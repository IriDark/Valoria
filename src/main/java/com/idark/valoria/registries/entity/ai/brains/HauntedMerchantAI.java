package com.idark.valoria.registries.entity.ai.brains;

import com.google.common.collect.*;
import com.idark.valoria.registries.entity.ai.behaviour.*;
import com.idark.valoria.registries.entity.living.*;
import com.mojang.datafixers.util.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.*;
import net.minecraft.world.entity.ai.behavior.*;
import net.minecraft.world.entity.ai.memory.*;
import net.minecraft.world.entity.ai.sensing.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.entity.schedule.*;
import net.minecraft.world.level.*;

import java.util.*;

public class HauntedMerchantAI {
    public static ImmutableList<Pair<Integer, ? extends BehaviorControl<? super HauntedMerchant>>> getCorePackage(float pSpeedModifier) {
        return ImmutableList.of(
        Pair.of(0, new Swim(0.8F)),
        Pair.of(0, InteractWithDoor.create()),
        Pair.of(1, new LookAtTargetSink(45, 90)),
        Pair.of(2, new MoveToTargetSink()),
        Pair.of(3, new TradingBehaviour(pSpeedModifier))
        );
    }

    public static ImmutableList<Pair<Integer, ? extends BehaviorControl<? super HauntedMerchant>>> getAngryPackage(HauntedMerchant pMob, float pSpeedModifier) {
        return ImmutableList.of(
        Pair.of(0, MeleeAttack.create(20)),
        Pair.of(0, StartAttacking.create(HauntedMerchantAI::findNearestValidAttackTarget)),
        Pair.of(1, StopBeingAngryIfTargetDead.create()),
        Pair.of(1, StopAttackingIfTargetInvalid.create()),
        Pair.of(2, SetWalkTargetFromAttackTargetIfTargetOutOfReach.create(pSpeedModifier))
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
        if(Sensor.isEntityAttackableIgnoringLineOfSight(mob, pTarget)){
            if(!BehaviorUtils.isOtherTargetMuchFurtherAwayThanCurrentAttackTarget(mob, pTarget, 4.0D)){
                if(pTarget.getType() == EntityType.PLAYER && mob.level().getGameRules().getBoolean(GameRules.RULE_UNIVERSAL_ANGER)){
                    setAngerTargetToNearestTargetablePlayerIfFound(mob, pTarget);
                }else{
                    setAngerTarget(mob, pTarget);
                }

                mob.setAggressive(true);
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
    }

    public static ImmutableList<Pair<Integer, ? extends BehaviorControl<? super HauntedMerchant>>> getHidePackage(float pSpeedModifier) {
        return ImmutableList.of(Pair.of(0, SetHiddenState.create(15, 3)), Pair.of(1, LocateHidingPlace.create(32, pSpeedModifier * 1.25F, 2)), getMinimalLookBehavior());
    }

    public static void initFightActivity(HauntedMerchant pMob, Brain<HauntedMerchant> pBrain){
        pBrain.addActivityWithConditions(Activity.FIGHT, getAngryPackage(pMob, 1), ImmutableSet.of(Pair.of(MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT)));
    }

    public static boolean isTarget(HauntedMerchant pMob, LivingEntity pEntity) {
        return pMob.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).filter((p_219509_) -> p_219509_ == pEntity).isPresent();
    }

    public static ImmutableList<Pair<Integer, ? extends BehaviorControl<? super HauntedMerchant>>> getIdlePackage(float pSpeedModifier) {
        return ImmutableList.of(
                Pair.of(1, new RunOne<>(ImmutableList.of(
                        Pair.of(InteractWith.of(EntityType.CAT, 8, MemoryModuleType.INTERACTION_TARGET, pSpeedModifier, 2), 1),
                        Pair.of(RandomStroll.stroll(pSpeedModifier), 1),
                        Pair.of(SetWalkTargetFromLookTarget.create(pSpeedModifier, 2), 1),
                        Pair.of(new DoNothing(30, 60), 1)))),
                Pair.of(2, SetLookAndInteract.create(EntityType.PLAYER, 4)),
                Pair.of(3, new GateBehavior<>(ImmutableMap.of(), ImmutableSet.of(MemoryModuleType.INTERACTION_TARGET), GateBehavior.OrderPolicy.ORDERED, GateBehavior.RunningPolicy.RUN_ONE, ImmutableList.of(Pair.of(new TradeWithMerchant(), 1)))),
                Pair.of(99, UpdateActivityFromSchedule.create()));
    }

    private static Pair<Integer, BehaviorControl<LivingEntity>> getMinimalLookBehavior() {
        return Pair.of(5, new RunOne<>(ImmutableList.of(Pair.of(SetEntityLookTarget.create(EntityType.PLAYER, 8.0F), 2), Pair.of(new DoNothing(30, 60), 8))));
    }
}
