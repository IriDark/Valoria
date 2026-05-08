package com.idark.valoria.client.cinema;

import net.minecraft.server.level.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.memory.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;

import java.util.*;

public class CutsceneHelper{
    public static void init(Level level, AABB boundingBox, int ticks) {
        for (ServerPlayer player : level.getEntitiesOfClass(ServerPlayer.class, boundingBox.inflate(32.0D))) {
            player.getPersistentData().putInt("ValoriaCinematicTicks", ticks);
            player.getPersistentData().putBoolean("ValoriaCinematic", true);
            stopAnger(player);
        }
    }

    public static void init(ServerPlayer player, int ticks) {
        player.getPersistentData().putBoolean("ValoriaCinematic", true);
        player.getPersistentData().putInt("ValoriaCinematicTicks", ticks);
        stopAnger(player);
    }

    public static void stop(ServerPlayer player) {
        player.getPersistentData().putBoolean("ValoriaCinematic", false);
    }

    public static void stopAnger(ServerPlayer player) {
        AABB bounds = player.getBoundingBox().inflate(64.0D);
        List<Mob> mobs = player.level().getEntitiesOfClass(Mob.class, bounds);
        for (Mob mob : mobs) {
            if (mob.getTarget() == player) {
                mob.setTarget(null);
                var brain = mob.getBrain();
                if (brain.hasMemoryValue(MemoryModuleType.ATTACK_TARGET)) {
                    brain.eraseMemory(MemoryModuleType.ATTACK_TARGET);
                }
            }
        }
    }
}