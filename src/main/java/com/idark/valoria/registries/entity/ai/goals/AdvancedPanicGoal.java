package com.idark.valoria.registries.entity.ai.goals;

import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.*;

public class AdvancedPanicGoal extends PanicGoal{

    private final boolean reason;

    public AdvancedPanicGoal(PathfinderMob mob, double pSpeedModifier, boolean pReason){
        super(mob, pSpeedModifier);
        reason = pReason;
    }

    protected boolean shouldPanic(){
        return this.mob.isFreezing() || this.mob.isOnFire() || reason;
    }
}
