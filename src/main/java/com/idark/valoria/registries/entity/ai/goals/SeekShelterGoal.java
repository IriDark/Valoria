package com.idark.valoria.registries.entity.ai.goals;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.FleeSunGoal;

public class SeekShelterGoal extends FleeSunGoal{

    private int interval = reducedTickDelay(100);

    public SeekShelterGoal(PathfinderMob mob, double pSpeedModifier){
        super(mob, pSpeedModifier);
    }

    public boolean canUse(){
        if(this.mob.getTarget() == null){
            if(mob.level().isThundering() && mob.level().canSeeSky(this.mob.blockPosition())){
                return this.setWantedPos();
            }else if(this.interval > 0){
                --this.interval;
                return false;
            }else{
                this.interval = 100;
                BlockPos blockpos = this.mob.blockPosition();
                return mob.level().isDay() && mob.level().canSeeSky(blockpos) && !((ServerLevel)mob.level()).isVillage(blockpos) && this.setWantedPos();
            }
        }else{
            return false;
        }
    }

    public void start(){
        super.start();
    }
}
