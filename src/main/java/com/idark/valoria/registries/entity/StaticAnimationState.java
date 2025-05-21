package com.idark.valoria.registries.entity;

import net.minecraft.world.entity.*;

public class StaticAnimationState extends AnimationState{
    public boolean isPlaying;
    private long durationTicks = -1;
    private long startTick = -1;

    /**
     * @param pTickCount Entity tick count
     * @param durationTicks Animation duration
     */
    public void start(int pTickCount, long durationTicks) {
        super.start(pTickCount);
        this.durationTicks = durationTicks;
        this.startTick = pTickCount;
        this.isPlaying = true;
    }

    @Override
    public void start(int pTickCount){
        this.start(pTickCount, -1);
    }

    /**
     * Updates the animation state with added auto stopping
     */
    @Override
    public void updateTime(float pAgeInTicks, float pSpeed){
        super.updateTime(pAgeInTicks, pSpeed);
        if (this.isStarted() && durationTicks > 0 && pAgeInTicks >= startTick + durationTicks) {
            this.stop();
        }
    }

    @Override
    public void stop(){
        super.stop();
        this.isPlaying = false;
    }
}
