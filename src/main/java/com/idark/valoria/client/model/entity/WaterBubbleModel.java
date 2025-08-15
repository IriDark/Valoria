package com.idark.valoria.client.model.entity;

import com.idark.valoria.registries.entity.projectile.*;
import net.minecraft.client.animation.*;
import net.minecraft.client.animation.AnimationChannel.*;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.*;
import net.minecraft.client.model.geom.builders.*;

public class WaterBubbleModel<T extends WaterBubble> extends HierarchicalModel<T>{
    private final ModelPart root;

    public WaterBubbleModel(ModelPart pRoot) {
        this.root = pRoot;
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition bubble = partdefinition.addOrReplaceChild("bubble", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 20.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void setupAnim(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch){
        this.root.getAllParts().forEach(ModelPart::resetPose);
        this.animate(pEntity.appearAnimationState, WaterBubbleModel.SPAWN, pAgeInTicks);
        this.animate(pEntity.disapearAnimationState, WaterBubbleModel.DESPAWN, pAgeInTicks);
    }

    @Override
    public ModelPart root(){
        return this.root;
    }

    public static final AnimationDefinition SPAWN = AnimationDefinition.Builder.withLength(0.75F)
    .addAnimation("bubble", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
    ))
    .addAnimation("bubble", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -7.0F, 0.0F), Interpolations.LINEAR),
    new Keyframe(0.625F, KeyframeAnimations.posVec(0.0F, 8.0F, 0.0F), Interpolations.LINEAR)
    ))
    .addAnimation("bubble", new AnimationChannel(AnimationChannel.Targets.SCALE,
    new Keyframe(0.0F, KeyframeAnimations.scaleVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
    new Keyframe(0.5F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), Interpolations.LINEAR)
    ))
    .build();

    public static final AnimationDefinition DESPAWN = AnimationDefinition.Builder.withLength(0.4167F)
    .addAnimation("bubble", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0, KeyframeAnimations.posVec(0.0F, 8.0F, 0.0F), Interpolations.LINEAR),
    new Keyframe(0.2483F, KeyframeAnimations.posVec(0.0F, -7.0F, 0.0F), Interpolations.LINEAR)
    ))
    .addAnimation("bubble", new AnimationChannel(AnimationChannel.Targets.SCALE,
    new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), Interpolations.LINEAR),
    new Keyframe(0.375F, KeyframeAnimations.scaleVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
    ))
    .build();
}