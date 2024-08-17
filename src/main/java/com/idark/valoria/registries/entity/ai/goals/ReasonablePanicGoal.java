package com.idark.valoria.registries.entity.ai.goals;

import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.*;

public class ReasonablePanicGoal extends PanicGoal{
    private final boolean reason;
    public ReasonablePanicGoal(PathfinderMob mob, double pSpeedModifier, boolean pReason){
        super(mob, pSpeedModifier);
        reason = pReason;
    }

    protected boolean shouldPanic(){
        return this.mob.isFreezing() || this.mob.isOnFire() || reason;
    }
}
