package com.idark.valoria.registries.entity.ai.goals;

import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.ai.goal.*;

public class AvoidStrongEntityGoal<T extends LivingEntity> extends AvoidEntityGoal<T>{

    public AvoidStrongEntityGoal(PathfinderMob pEntity, Class<T> pEntityClassToAvoid, float pMaxDist, double pWalkSpeedModifier, double pSprintSpeedModifier){
        super(pEntity, pEntityClassToAvoid, pMaxDist, pWalkSpeedModifier, pSprintSpeedModifier);
    }

    public boolean canUse(){
        if(super.canUse() && this.toAvoid != null){
            return this.avoidStrongEntity(this.toAvoid);
        }else{
            return false;
        }
    }

    private boolean avoidStrongEntity(LivingEntity strong){
        return strong.getAttributeValue(Attributes.ATTACK_DAMAGE) >= this.mob.getAttributeValue(Attributes.ATTACK_DAMAGE);
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void start(){
        this.mob.setTarget(null);
        super.start();
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void tick(){
        this.mob.setTarget(null);
        super.tick();
    }
}
