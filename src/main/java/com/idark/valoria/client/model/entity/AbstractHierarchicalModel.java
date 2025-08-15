package com.idark.valoria.client.model.entity;

import net.minecraft.client.animation.*;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.*;

public abstract class AbstractHierarchicalModel<T extends Entity> extends HierarchicalModel<T>{

    public boolean applyHeadRotations() {
        return true;
    }

    public ModelPart getHead() {
        return null;
    };

    protected void animateIdle(AnimationState pAnimationState, AnimationDefinition pAnimationDefinition, float pLimbSwingAmount, float pAgeInTicks, float pAnimationSpeed) {
        if(pLimbSwingAmount < 0.001) {
            this.animate(pAnimationState, pAnimationDefinition, pAgeInTicks, pAnimationSpeed);
        }
    }

    public void setupAnim(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch){
        this.root().getAllParts().forEach(ModelPart::resetPose);
        if(applyHeadRotations() && this.getHead() != null){
            this.applyHeadRotation(pNetHeadYaw, pHeadPitch);
        }

        animateIdlePose(pAgeInTicks);
    }

    private void applyHeadRotation(float pNetHeadYaw, float pHeadPitch) {
        pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30.0F, 30.0F);
        pHeadPitch = Mth.clamp(pHeadPitch, 5, 10.0F);

        this.getHead().yRot = pNetHeadYaw * ((float)Math.PI / 180F);
        this.getHead().xRot = pHeadPitch * ((float)Math.PI / 180F);
    }

    private void animateIdlePose(float pAgeInTicks) {
        float f = pAgeInTicks * 0.1F;
        float f1 = Mth.cos(f);
        float f2 = Mth.sin(f);
        this.getHead().zRot += 0.06F * f1;
        this.getHead().xRot += 0.06F * f2;
        this.getHead().zRot += 0.025F * f2;
        this.getHead().xRot += 0.025F * f1;
    }
}
