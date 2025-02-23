package com.idark.valoria.registries.entity.ai.goals;

import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.phys.*;

public class DashAttackGoal extends Goal {
    private boolean randomDirection;
    private final Mob mob;
    private final float distance;

    public DashAttackGoal(Mob warm, float distance) {
        this.mob = warm;
        this.distance = distance * distance;
    }

    @Override
    public boolean canUse() {
        return mob.getTarget() != null;
    }

    public boolean canContinueToUse() {
        return this.canUse() && mob.getTarget()!=null&& mob.getTarget().isAlive();
    }

    public static double angleBetween(Vec3 v1, Vec3 v2){
        return Math.acos(v1.dot(v2)/v1.length()/v2.length());
    }

    public void tick() {
        LivingEntity target = mob.getTarget();
        if (target == null) return;
        double distance = mob.distanceToSqr(target);
        double angle = angleBetween(mob.getLookAngle(), target.position().subtract(mob.position()));
        if (distance > this.distance) {
            mob.lookAt(target, 5, 5);
            if (angle > Math.PI / 6.0D) {
                mob.addDeltaMovement(mob.getLookAngle().scale(0.06f).add(0, 0.01f * (randomDirection ? 1 : -1), 0));
                return;
            }
        }

        if (angle < Math.PI / 2) mob.lookAt(target, 2f, 2f);
        mob.addDeltaMovement(mob.getLookAngle().scale(0.1f));
        randomDirection = !randomDirection;
    }
}