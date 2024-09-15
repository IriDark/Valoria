package com.idark.valoria.registries.entity.ai.goals;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;

public class ReasonableAvoidEntityGoal<T extends LivingEntity> extends AvoidEntityGoal<T> {
    private final boolean reason;

    public ReasonableAvoidEntityGoal(PathfinderMob pMob, Class<T> pEntityClassToAvoid, float pMaxDistance, double pWalkSpeedModifier, double pSprintSpeedModifier, boolean pReason) {
        super(pMob, pEntityClassToAvoid, pMaxDistance, pWalkSpeedModifier, pSprintSpeedModifier);
        reason = pReason;
    }

    public boolean canUse() {
        return super.canUse() && reason;
    }
}
