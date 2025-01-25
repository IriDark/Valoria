package com.idark.valoria.registries.entity.ai.behaviour;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;

public class SkeletonMovement{
    private final Mob mob;
    private int seeTime;
    private boolean strafingClockwise;
    private boolean strafingBackwards;
    private int strafingTime = -1;
    private double radius = 32;

    public SkeletonMovement(Mob mob) {
        this.mob = mob;
    }

    public SkeletonMovement(Mob mob, double radius){
        this.mob = mob;
        this.radius = radius;
    }

    public void setupMovement(){
        LivingEntity livingentity = mob.getTarget();
        if(livingentity != null){
            double d0 = mob.distanceToSqr(livingentity.getX(), livingentity.getY(), livingentity.getZ());
            boolean flag = mob.getSensing().hasLineOfSight(livingentity);
            boolean flag1 = this.seeTime > 0;
            if(flag != flag1){
                this.seeTime = 0;
            }

            if(flag){
                ++this.seeTime;
            }else{
                --this.seeTime;
            }

            if(!(d0 > radius) && this.seeTime >= 20){
                mob.getNavigation().stop();
                ++this.strafingTime;
            }else{
                mob.getNavigation().moveTo(livingentity, 0.75f);
                this.strafingTime = -1;
            }

            if(this.strafingTime >= 20){
                if((double)mob.getRandom().nextFloat() < 0.3D){
                    this.strafingClockwise = !this.strafingClockwise;
                }

                if((double)mob.getRandom().nextFloat() < 0.3D){
                    this.strafingBackwards = !this.strafingBackwards;
                }

                this.strafingTime = 0;
            }

            if(this.strafingTime > -1){
                if(d0 > (radius * 0.75F)){
                    this.strafingBackwards = false;
                }else if(d0 < (radius * 0.25F)){
                    this.strafingBackwards = true;
                }

                mob.getMoveControl().strafe(this.strafingBackwards ? -0.5F : 0.5F, this.strafingClockwise ? 0.5F : -0.5F);
                Entity entity = mob.getControlledVehicle();
                if(entity instanceof Mob vehicle){
                    vehicle.lookAt(livingentity, 30.0F, 30.0F);
                }

                mob.lookAt(livingentity, 30.0F, 30.0F);
            }else{
                mob.getLookControl().setLookAt(livingentity, 30.0F, 30.0F);
            }
        }
    }
}
