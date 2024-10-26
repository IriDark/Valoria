package com.idark.valoria.client.model.entity;

import com.idark.valoria.registries.entity.living.minions.*;
import net.minecraft.client.animation.*;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.*;
import net.minecraft.client.model.geom.builders.*;

public class FleshSentinelModel<T extends FleshSentinel> extends HierarchicalModel<T>{
    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart rightWing;
    private final ModelPart leftWing;

    public FleshSentinelModel(ModelPart root) {
        this.root = root;
        this.body = root.getChild("body");
        this.rightWing = body.getChild("right_wing");
        this.leftWing = body.getChild("left_wing");

    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -6.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));
        PartDefinition rightWing = body.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(0, 18).addBox(-0.0F, 4.25F, -5F, 0.0F, 7.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.4938F, -11.5F, -0.2835F, 0.0F, 1.6144F, 0.0F));
        PartDefinition leftWing = body.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(0, 11).mirror().addBox(-0.0F, 4.25F, -2F, 0.0F, 7.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-6.4938F, -11.5F, 0.2835F, 0.0F, 1.6144F, 0.0F));
        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public ModelPart root(){
        return root;
    }

    private void animateHeadLookTarget(float pYaw, float pPitch) {
        this.body.xRot = pPitch * ((float) Math.PI / 180F);
        this.body.yRot = pYaw * ((float) Math.PI / 180F);
    }

    @Override
    public void setupAnim(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch){
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.animateHeadLookTarget(pNetHeadYaw, pHeadPitch);
        this.animate(pEntity.idleAnimationState, idle, pAgeInTicks * 2);
    }

    public final AnimationDefinition idle = AnimationDefinition.Builder.withLength(1.0F).looping()
    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -0.25F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.25F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, -0.25F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("left_wing", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -8.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 8.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -8.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("left_wing", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(-1.0F, 0.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5F, KeyframeAnimations.posVec(-0.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.posVec(-1.0F, 0.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("right_wing", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 8.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -8.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 8.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("right_wing", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(1.0F, 0.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5F, KeyframeAnimations.posVec(0.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.posVec(1.0F, 0.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    )).build();
}