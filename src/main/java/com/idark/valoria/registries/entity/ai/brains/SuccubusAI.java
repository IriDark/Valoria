package com.idark.valoria.registries.entity.ai.brains;

import com.google.common.collect.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.entity.ai.memory.*;
import com.idark.valoria.registries.entity.living.*;
import com.mojang.datafixers.util.*;
import net.minecraft.core.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.ai.behavior.*;
import net.minecraft.world.entity.ai.memory.*;
import net.minecraft.world.entity.ai.sensing.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.entity.schedule.*;
import net.minecraft.world.level.*;

import java.util.*;

public class SuccubusAI{
    public static Brain<?> makeBrain(Succubus mob, Brain<Succubus> pBrain) {
        initCoreActivity(pBrain);
        initIdleActivity(pBrain);
        initRetreatActivity(pBrain);
        initFightActivity(mob, pBrain);
        pBrain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        pBrain.setDefaultActivity(Activity.IDLE);
        pBrain.useDefaultActivity();
        return pBrain;
    }

    public static void initMemories(Succubus mob) {
        GlobalPos globalpos = GlobalPos.of(mob.level().dimension(), mob.blockPosition());
        mob.getBrain().setMemory(MemoryModuleType.HOME, globalpos);
    }

    private static void initCoreActivity(Brain<Succubus> pBrain) {
        pBrain.addActivity(Activity.CORE, 0, ImmutableList.of(new LookAtTargetSink(45, 90), new MoveToTargetSink(), InteractWithDoor.create(), StopBeingAngryIfTargetDead.create()));
    }

    private static void initIdleActivity(Brain<Succubus> pBrain) {
        pBrain.addActivity(Activity.IDLE, 10, ImmutableList.of(StartAttacking.create(SuccubusAI::findNearestValidAttackTarget), createIdleLookBehaviors(), createIdleMovementBehaviors(), SetLookAndInteract.create(EntityType.PLAYER, 4)));
    }

    private static void initFightActivity(Succubus mob, Brain<Succubus> pBrain) {
        pBrain.addActivityAndRemoveMemoryWhenStopped(
        Activity.FIGHT,
        10,
        ImmutableList.of(
        StopAttackingIfTargetInvalid.create((target) -> !mob.canTargetEntity(target)),
        SetEntityLookTarget.create((target) -> isTarget(mob, target), (float)mob.getAttributeValue(Attributes.FOLLOW_RANGE)),
        SetWalkTargetFromAttackTargetIfTargetOutOfReach.create(1.2F),
        MeleeAttack.create(18)),
        MemoryModuleType.ATTACK_TARGET
        );
    }

    private static boolean isTarget(Succubus mob, LivingEntity pEntity) {
        return mob.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).filter((p_219509_) -> p_219509_ == pEntity).isPresent();
    }

    private static RunOne<Succubus> createIdleLookBehaviors() {
        return new RunOne<>(ImmutableList.of(Pair.of(SetEntityLookTarget.create(EntityTypeRegistry.SUCCUBUS.get(), 8.0F), 1), Pair.of(SetEntityLookTarget.create(EntityType.PIGLIN, 8.0F), 1), Pair.of(SetEntityLookTarget.create(EntityType.PIGLIN_BRUTE, 8.0F), 1), Pair.of(SetEntityLookTarget.create(8.0F), 1), Pair.of(new DoNothing(30, 60), 1)));
    }

    private static RunOne<Succubus> createIdleMovementBehaviors() {
        return new RunOne<>(ImmutableList.of(Pair.of(RandomStroll.stroll(0.6F), 2), Pair.of(InteractWith.of(EntityType.PIGLIN, 8, MemoryModuleType.INTERACTION_TARGET, 0.6F, 2), 2), Pair.of(InteractWith.of(EntityType.PIGLIN_BRUTE, 8, MemoryModuleType.INTERACTION_TARGET, 0.6F, 2), 2), Pair.of(StrollToPoi.create(MemoryModuleType.HOME, 0.6F, 2, 100), 2), Pair.of(StrollAroundPoi.create(MemoryModuleType.HOME, 0.6F, 5), 2), Pair.of(new DoNothing(30, 60), 1)));
    }

    public static void updateActivity(Succubus mob) {
        Brain<Succubus> brain = mob.getBrain();
        Activity activity = brain.getActiveNonCoreActivity().orElse(null);
        brain.setActiveActivityToFirstValid(ImmutableList.of(Activity.FIGHT, Activity.IDLE));
        Activity activity1 = brain.getActiveNonCoreActivity().orElse(null);
        if (activity != activity1) {
            playActivitySound(mob);
        }

        mob.setAggressive(brain.hasMemoryValue(MemoryModuleType.ATTACK_TARGET));
    }

    private static boolean isNearestValidAttackTarget(Succubus mob, LivingEntity pTarget) {
        return findNearestValidAttackTarget(mob).filter((p_35085_) -> p_35085_ == pTarget).isPresent();
    }

    private static Optional<? extends LivingEntity> findNearestValidAttackTarget(Succubus p_35087_) {
        Optional<LivingEntity> optional = BehaviorUtils.getLivingEntityFromUUIDMemory(p_35087_, MemoryModuleType.ANGRY_AT);
        if (optional.isPresent() && Sensor.isEntityAttackableIgnoringLineOfSight(p_35087_, optional.get())) {
            return optional;
        } else {
            Optional<? extends LivingEntity> optional1 = getTargetIfWithinRange(p_35087_, MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER);
            return optional1.isPresent() ? optional1 : p_35087_.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_NEMESIS);
        }
    }

    private static Optional<? extends LivingEntity> getTargetIfWithinRange(Succubus mob, MemoryModuleType<? extends LivingEntity> pMemoryType) {
        return mob.getBrain().getMemory(pMemoryType).filter((p_35108_) -> p_35108_.closerThan(mob, 12.0D));
    }

    public static void wasHurtBy(Succubus mob, LivingEntity pTarget){
        if(!(pTarget instanceof Succubus)){
            SuccubusAI.maybeRetaliate(mob, pTarget);
        }

        Brain<Succubus> brain = mob.getBrain();
        if(mob.isBaby()){
            brain.setMemoryWithExpiry(MemoryModuleType.AVOID_TARGET, pTarget, 100L);
        }
    }
    
    private static List<Succubus> getAdultSuccubs(Succubus mob) {
        return mob.getBrain().getMemory(MemoryModules.NEARBY_ADULT_SUCCUBUS.get()).orElse(ImmutableList.of());
    }

    private static void stopWalking(Succubus mob) {
        mob.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
        mob.getNavigation().stop();
    }

    protected static void broadcastUniversalAnger(Succubus mob) {
        getAdultSuccubs(mob).forEach((p_34991_) -> getNearestVisibleTargetablePlayer(p_34991_).ifPresent((p_149964_) -> {
            setAngerTarget(p_34991_, p_149964_);
        }));
    }

    protected static void setAngerTarget(Succubus mob, LivingEntity pTarget) {
        if (Sensor.isEntityAttackableIgnoringLineOfSight(mob, pTarget)) {
            mob.getBrain().eraseMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
            mob.getBrain().setMemoryWithExpiry(MemoryModuleType.ANGRY_AT, pTarget.getUUID(), 600L);
            if (pTarget.getType() == EntityType.PLAYER && mob.level().getGameRules().getBoolean(GameRules.RULE_UNIVERSAL_ANGER)) {
                mob.getBrain().setMemoryWithExpiry(MemoryModuleType.UNIVERSAL_ANGER, true, 600L);
            }

        }
    }

    private static boolean wantsToStopFleeing(Succubus mob) {
        Brain<Succubus> brain = mob.getBrain();
        return !brain.hasMemoryValue(MemoryModuleType.AVOID_TARGET);
    }

    private static void initRetreatActivity(Brain<Succubus> pBrain) {
        pBrain.addActivityAndRemoveMemoryWhenStopped(Activity.AVOID, 10, ImmutableList.of(SetWalkTargetAwayFrom.entity(MemoryModuleType.AVOID_TARGET, 1.0F, 12, true), createIdleLookBehaviors(), createIdleMovementBehaviors(), EraseMemoryIf.create(SuccubusAI::wantsToStopFleeing, MemoryModuleType.AVOID_TARGET)), MemoryModuleType.AVOID_TARGET);
    }

    protected static void maybeRetaliate(Succubus mob, LivingEntity pTarget) {
        if (!mob.getBrain().isActive(Activity.AVOID)) {
            if (Sensor.isEntityAttackableIgnoringLineOfSight(mob, pTarget)) {
                if (!BehaviorUtils.isOtherTargetMuchFurtherAwayThanCurrentAttackTarget(mob, pTarget, 4.0D)) {
                    if (pTarget.getType() == EntityType.PLAYER && mob.level().getGameRules().getBoolean(GameRules.RULE_UNIVERSAL_ANGER)) {
                        setAngerTargetToNearestTargetablePlayerIfFound(mob, pTarget);
                        broadcastUniversalAnger(mob);
                    } else {
                        setAngerTarget(mob, pTarget);
                    }
                }
            }
        }
    }

    public static Optional<Player> getNearestVisibleTargetablePlayer(Succubus mob) {
        return mob.getBrain().hasMemoryValue(MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER) ? mob.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER) : Optional.empty();
    }

    private static void setAngerTargetToNearestTargetablePlayerIfFound(Succubus mob, LivingEntity pCurrentTarget) {
        Optional<Player> optional = getNearestVisibleTargetablePlayer(mob);
        if (optional.isPresent()) {
            setAngerTarget(mob, optional.get());
        } else {
            setAngerTarget(mob, pCurrentTarget);
        }

    }

    public static void maybePlayActivitySound(Succubus mob) {
        if ((double)mob.level().random.nextFloat() < 0.0125D) {
            playActivitySound(mob);
        }

    }

    private static void playActivitySound(Succubus mob) {
        mob.getBrain().getActiveNonCoreActivity().ifPresent((p_35104_) -> {
            if (p_35104_ == Activity.FIGHT) {
                mob.playAngrySound();
            }
        });
    }
}