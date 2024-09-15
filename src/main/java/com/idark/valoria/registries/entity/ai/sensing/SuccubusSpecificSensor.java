package com.idark.valoria.registries.entity.ai.sensing;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.idark.valoria.registries.entity.living.Succubus;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.WitherSkeleton;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class SuccubusSpecificSensor extends Sensor<LivingEntity> {
    public Set<MemoryModuleType<?>> requires() {
        return ImmutableSet.of(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES, MemoryModuleType.NEAREST_VISIBLE_NEMESIS);
    }

    protected void doTick(ServerLevel pLevel, LivingEntity pEntity) {
        Brain<?> brain = pEntity.getBrain();
        List<Succubus> list = Lists.newArrayList();
        NearestVisibleLivingEntities nearestvisiblelivingentities = brain.getMemory(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES).orElse(NearestVisibleLivingEntities.empty());
        Optional<Mob> optional = nearestvisiblelivingentities.findClosest((p_186155_) -> {
            return p_186155_ instanceof WitherSkeleton || p_186155_ instanceof WitherBoss;
        }).map(Mob.class::cast);

        for (LivingEntity livingentity : brain.getMemory(MemoryModuleType.NEAREST_LIVING_ENTITIES).orElse(ImmutableList.of())) {
            if (livingentity instanceof Succubus && ((Succubus) livingentity).isAdult()) {
                list.add((Succubus) livingentity);
            }
        }

        brain.setMemory(MemoryModuleType.NEAREST_VISIBLE_NEMESIS, optional);
    }
}