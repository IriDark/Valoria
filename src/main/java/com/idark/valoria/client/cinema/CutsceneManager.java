package com.idark.valoria.client.cinema;

import net.minecraft.client.*;
import net.minecraft.world.entity.decoration.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.api.distmarker.*;
import pro.komaru.tridot.util.struct.data.*;

@OnlyIn(Dist.CLIENT)
public class CutsceneManager {
    public static boolean active = false;
    public static int ticks = 0;
    public static int maxTicks = 60;
    public static int startFov;

    public static Vec3 startPos;
    public static float startYaw, startPitch;

    private static ArmorStand dummyEntity;

    public static Seq<CutsceneNode> nodes;
    public static int currentNodeIndex;
    public static int ticksInCurrentNode;
    public static int lastTriggeredNodeIndex = -1;
    public static int skipTicks = 0;
    public static final int skipThreshold = 20;

    public static void start(Seq<CutsceneNode> pathNodes) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null || pathNodes == null || pathNodes.isEmpty()) return;

        active = true;
        skipTicks = 0;
        startFov = 90;
        ticks = 0;
        nodes = pathNodes;
        currentNodeIndex = 0;
        ticksInCurrentNode = 0;
        lastTriggeredNodeIndex = -1;

        maxTicks = 0;
        for (CutsceneNode node : nodes) {
            maxTicks += node.duration;
        }

        startPos = mc.player.getEyePosition();
        startYaw = mc.player.getYRot();
        startPitch = mc.player.getXRot();

        dummyEntity = new ArmorStand(mc.level, startPos.x, startPos.y, startPos.z);
        dummyEntity.setInvisible(true);
        dummyEntity.setNoGravity(true);
        dummyEntity.setInvulnerable(true);
        dummyEntity.setYRot(startYaw);
        dummyEntity.setXRot(startPitch);

        mc.level.addFreshEntity(dummyEntity);
        mc.setCameraEntity(dummyEntity);
    }

    public static float getYawToTarget(Vec3 source, Vec3 target) {
        double dx = target.x - source.x;
        double dz = target.z - source.z;
        return (float)(Math.atan2(dz, dx) * (180 / Math.PI)) - 90.0F;
    }

    public static float getPitchToTarget(Vec3 source, Vec3 target) {
        double dx = target.x - source.x;
        double dy = target.y - source.y;
        double dz = target.z - source.z;
        double distanceXZ = Math.sqrt(dx * dx + dz * dz);
        return (float)(-(Math.atan2(dy, distanceXZ) * (180 / Math.PI)));
    }

    public static void stop() {
        active = false;
        Minecraft mc = Minecraft.getInstance();
        if (mc.player != null) {
            mc.setCameraEntity(mc.player);
        }

        if (dummyEntity != null) {
            dummyEntity.discard();
            dummyEntity = null;
        }
    }

    public static ArmorStand getDummy() {
        return dummyEntity;
    }
}