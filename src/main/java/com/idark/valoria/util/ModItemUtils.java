package com.idark.valoria.util;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.joml.Vector3d;

import java.util.List;

public class ModItemUtils {

    public static void radiusHit(Level level, Player player, ParticleOptions type, List<LivingEntity> hitEntities, Vector3d pos, float pitchRaw, float yawRaw, float radius) {
        double pitch = ((pitchRaw + 90) * Math.PI) / 180;
        double yaw = ((yawRaw + 90) * Math.PI) / 180;

        double locYaw = 0;
        double locPitch = 0;
        double X = Math.sin(locPitch + pitch) * Math.cos(locYaw + yaw) * (double) radius;
        double Y = Math.cos(locPitch + pitch) * (double) radius;
        double Z = Math.sin(locPitch + pitch) * Math.sin(locYaw + yaw) * (double) radius;

        AABB boundingBox = new AABB(pos.x, pos.y - radius + ((Math.random() - 0.5D) * 0.2F), pos.z, pos.x + X, pos.y + Y + ((Math.random() - 0.5D) * 0.2F), pos.z + Z);
        List<Entity> entities = level.getEntitiesOfClass(Entity.class, boundingBox);
        for (Entity entity : entities) {
            if (entity instanceof LivingEntity livingEntity && !hitEntities.contains(livingEntity) && !livingEntity.equals(player)) {
                hitEntities.add(livingEntity);
            }
        }

        X = Math.sin(locPitch + pitch) * Math.cos(locYaw + yaw) * (double) radius * 0.75F;
        Y = Math.cos(locPitch + pitch) * (double) radius * 0.75F;
        Z = Math.sin(locPitch + pitch) * Math.sin(locYaw + yaw) * (double) radius * 0.75F;
        level.addParticle(type, pos.x + X, pos.y + Y + ((Math.random() - 0.5D) * 0.2F), pos.z + Z, 0d, 0d, 0d);
    }
}