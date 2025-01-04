package com.idark.valoria.client.model.animations;

import net.minecraft.client.animation.*;

public class DevilAnimation {
    public static final AnimationDefinition IDLE = AnimationDefinition.Builder.withLength(3.0F).looping()
    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0417F, KeyframeAnimations.degreeVec(2.5F, -7.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.125F, KeyframeAnimations.degreeVec(-2.512F, 4.9883F, 0.543F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("left_ear", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0417F, KeyframeAnimations.degreeVec(2.5F, -7.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.125F, KeyframeAnimations.degreeVec(-2.512F, 4.9883F, 0.543F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("left_ear", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0417F, KeyframeAnimations.posVec(-0.15F, 0.025F, 0.7F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.125F, KeyframeAnimations.posVec(0.08F, -0.095F, -0.3F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("right_ear", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0417F, KeyframeAnimations.degreeVec(2.5F, -7.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.125F, KeyframeAnimations.degreeVec(-2.512F, 4.9883F, 0.543F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("right_ear", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0417F, KeyframeAnimations.posVec(-0.025F, 0.025F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.125F, KeyframeAnimations.posVec(0.1F, 0.03F, 0.5F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.SCALE,
    new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.7917F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.1F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5833F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.3333F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.1F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -3.5F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 3.5F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.8333F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -2.5F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.25F, KeyframeAnimations.degreeVec(-2.5F, 0.0F, -2.5F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.375F, KeyframeAnimations.degreeVec(5.0F, 0.0F, -2.5F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.degreeVec(-2.5F, 0.0F, -2.5F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.625F, KeyframeAnimations.degreeVec(5.0F, 0.0F, -2.5F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -2.5F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("left_wing", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.7917F, KeyframeAnimations.degreeVec(0.0F, -2.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5833F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.3333F, KeyframeAnimations.degreeVec(0.0F, -2.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("left_wing", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.7917F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.62F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5833F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.3333F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.62F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("right_wing", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.7917F, KeyframeAnimations.degreeVec(0.0F, 2.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5833F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.3333F, KeyframeAnimations.degreeVec(0.0F, 2.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("right_wing", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.7917F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.62F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5833F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.3333F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.62F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .build();

    public static final AnimationDefinition WALK = AnimationDefinition.Builder.withLength(2.0F).looping()
    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.6667F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -7.5F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.375F, KeyframeAnimations.degreeVec(7.5F, 0.0F, 7.37F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 3.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 2.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 3.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.posVec(0.0F, 2.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 3.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("left_ear", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.6667F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -7.5F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.375F, KeyframeAnimations.degreeVec(7.5F, 0.0F, 7.37F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("left_ear", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 3.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5F, KeyframeAnimations.posVec(-0.125F, 2.675F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.8333F, KeyframeAnimations.posVec(-0.06F, 3.24F, -2.03F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 3.1F, -2.05F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.1667F, KeyframeAnimations.posVec(0.07F, 2.43F, -2.08F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.posVec(0.1F, 1.375F, -2.1F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 3.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("right_ear", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.6667F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -7.5F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.375F, KeyframeAnimations.degreeVec(7.5F, 0.0F, 7.37F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("right_ear", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 3.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 1.3F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.6667F, KeyframeAnimations.posVec(0.0F, 1.545F, -2.01F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 3.0F, -2.05F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.1667F, KeyframeAnimations.posVec(0.07F, 3.205F, -2.08F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.posVec(0.2F, 2.775F, -2.1F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 3.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(22.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 3.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 2.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 3.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.posVec(0.0F, 2.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 3.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.SCALE,
    new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.4583F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.2F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.4167F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.2F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.6667F, KeyframeAnimations.degreeVec(5.0F, 0.0F, -2.5F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.degreeVec(-8.2812F, 0.0F, -2.1094F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 2.4F, -1.5F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 1.4F, -1.5F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 2.4F, -1.5F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.posVec(0.0F, 1.4F, -1.5F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 2.4F, -1.5F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.6667F, KeyframeAnimations.degreeVec(-5.0F, 0.0F, 2.5F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.degreeVec(8.2812F, 0.0F, 2.1094F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 2.4F, -1.5F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 1.4F, -1.5F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 2.4F, -1.5F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.posVec(0.0F, 1.4F, -1.5F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 2.4F, -1.5F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(22.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5833F, KeyframeAnimations.degreeVec(24.9146F, 2.7554F, -4.1376F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.4583F, KeyframeAnimations.degreeVec(35.3295F, 1.1515F, -2.2192F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.degreeVec(22.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 2.8F, 2.3F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 1.8F, 2.3F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 2.8F, 2.3F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.posVec(0.0F, 1.8F, 2.3F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 2.8F, 2.3F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(27.4777F, -1.1541F, 2.2178F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.7917F, KeyframeAnimations.degreeVec(39.9916F, 0.4566F, 0.3045F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.2917F, KeyframeAnimations.degreeVec(27.8447F, 0.2946F, 0.1798F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.degreeVec(27.4777F, -1.1541F, 2.2178F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 3.6F, 2.3F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 2.6F, 2.3F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 3.6F, 2.3F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.posVec(0.0F, 2.6F, 2.3F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 3.6F, 2.3F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .build();

    public static final AnimationDefinition ATTACK_MAGIC = AnimationDefinition.Builder.withLength(4.0F)
    .addAnimation("Head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.375F, KeyframeAnimations.degreeVec(-37.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0417F, KeyframeAnimations.degreeVec(13.93F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.625F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.2083F, KeyframeAnimations.degreeVec(-17.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.75F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 12.5F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.125F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 12.5F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.5F, KeyframeAnimations.degreeVec(25.3376F, 9.0548F, 4.2617F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(4.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("Head", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.375F, KeyframeAnimations.posVec(0.0F, 14.8F, 12.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0417F, KeyframeAnimations.posVec(0.0F, 14.0F, 7.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.625F, KeyframeAnimations.posVec(0.0F, 14.0F, 8.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.4167F, KeyframeAnimations.posVec(0.0F, 15.0F, 9.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.625F, KeyframeAnimations.posVec(0.0F, 15.0F, 8.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.75F, KeyframeAnimations.posVec(0.7F, 0.0F, 2.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.875F, KeyframeAnimations.posVec(0.7F, 0.0F, 6.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.125F, KeyframeAnimations.posVec(0.7F, 0.0F, 2.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.5F, KeyframeAnimations.posVec(0.41F, 0.0F, -1.1F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(4.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("Head", new AnimationChannel(AnimationChannel.Targets.SCALE,
    new Keyframe(2.4583F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.5F, KeyframeAnimations.scaleVec(1.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
    new Keyframe(2.7073F, KeyframeAnimations.scaleVec(1.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
    new Keyframe(2.7083F, KeyframeAnimations.scaleVec(0.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.LINEAR),
    new Keyframe(2.749F, KeyframeAnimations.scaleVec(0.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.LINEAR),
    new Keyframe(2.75F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("Left_ear", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.375F, KeyframeAnimations.degreeVec(-37.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0417F, KeyframeAnimations.degreeVec(13.93F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.625F, KeyframeAnimations.degreeVec(-0.16F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.2083F, KeyframeAnimations.degreeVec(-17.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.3333F, KeyframeAnimations.degreeVec(-16.33F, 1.0F, 2.94F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.375F, KeyframeAnimations.degreeVec(-14.8F, 1.18F, 3.18F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.4167F, KeyframeAnimations.degreeVec(-12.7106F, 1.099F, 4.1057F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.4583F, KeyframeAnimations.degreeVec(-10.29F, 1.0F, 4.85F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.75F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 12.5F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.125F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 12.5F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.5F, KeyframeAnimations.degreeVec(25.3376F, 9.0548F, 4.2617F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.6667F, KeyframeAnimations.degreeVec(13.7481F, 15.8727F, -26.9088F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.7917F, KeyframeAnimations.degreeVec(15.846F, -4.7851F, 44.5337F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(4.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("Left_ear", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.375F, KeyframeAnimations.posVec(0.0F, 14.2F, 12.3F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0417F, KeyframeAnimations.posVec(0.0F, 14.125F, 6.8F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.625F, KeyframeAnimations.posVec(0.0F, 14.015F, 8.005F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.9583F, KeyframeAnimations.posVec(0.0F, 14.195F, 8.2F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.2083F, KeyframeAnimations.posVec(0.0F, 14.535F, 8.225F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.2917F, KeyframeAnimations.posVec(-0.03F, 14.5F, 8.19F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.3333F, KeyframeAnimations.posVec(-0.05F, 14.46F, 8.185F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.375F, KeyframeAnimations.posVec(-0.05F, 14.45F, 8.165F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.4167F, KeyframeAnimations.posVec(0.05F, 14.405F, 8.13F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.4583F, KeyframeAnimations.posVec(0.01F, 14.615F, 8.19F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.75F, KeyframeAnimations.posVec(0.725F, -1.2F, 2.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.875F, KeyframeAnimations.posVec(0.725F, -1.325F, 6.025F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.125F, KeyframeAnimations.posVec(0.75F, -1.21F, 2.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.5F, KeyframeAnimations.posVec(0.485F, -0.125F, -2.375F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.6667F, KeyframeAnimations.posVec(-0.68F, 0.59F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.7917F, KeyframeAnimations.posVec(-0.32F, -0.88F, -1.57F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(4.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("Left_ear", new AnimationChannel(AnimationChannel.Targets.SCALE,
    new Keyframe(2.4583F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.5F, KeyframeAnimations.scaleVec(1.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
    new Keyframe(2.7073F, KeyframeAnimations.scaleVec(1.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
    new Keyframe(2.7083F, KeyframeAnimations.scaleVec(0.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.LINEAR),
    new Keyframe(2.749F, KeyframeAnimations.scaleVec(0.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.LINEAR),
    new Keyframe(2.75F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("Right_ear", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.375F, KeyframeAnimations.degreeVec(-37.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0417F, KeyframeAnimations.degreeVec(13.93F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.25F, KeyframeAnimations.degreeVec(14.8146F, 3.8412F, 26.1999F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.375F, KeyframeAnimations.degreeVec(-5.2982F, -4.3033F, -21.5959F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.625F, KeyframeAnimations.degreeVec(-0.16F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.2083F, KeyframeAnimations.degreeVec(-17.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.3333F, KeyframeAnimations.degreeVec(-16.3593F, 0.1261F, 0.8984F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.4583F, KeyframeAnimations.degreeVec(-10.3172F, 0.658F, 4.2506F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.75F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 12.5F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.125F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 12.5F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.5F, KeyframeAnimations.degreeVec(25.3376F, 9.0548F, 4.2617F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(4.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("Right_ear", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.375F, KeyframeAnimations.posVec(0.0F, 14.2F, 12.3F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0417F, KeyframeAnimations.posVec(0.0F, 14.125F, 6.8F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.25F, KeyframeAnimations.posVec(1.2F, 14.75F, 6.66F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.375F, KeyframeAnimations.posVec(0.34F, 13.14F, 7.39F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.625F, KeyframeAnimations.posVec(0.0F, 14.015F, 8.005F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.9583F, KeyframeAnimations.posVec(0.0F, 14.195F, 8.7F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.2083F, KeyframeAnimations.posVec(0.0F, 14.51F, 9.075F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.2917F, KeyframeAnimations.posVec(0.03F, 14.675F, 9.115F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.3333F, KeyframeAnimations.posVec(0.05F, 14.885F, 9.16F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.375F, KeyframeAnimations.posVec(0.05F, 15.025F, 9.14F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.4167F, KeyframeAnimations.posVec(0.05F, 15.18F, 9.155F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.4583F, KeyframeAnimations.posVec(0.065F, 15.64F, 9.24F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.75F, KeyframeAnimations.posVec(1.0F, 1.15F, 2.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.875F, KeyframeAnimations.posVec(1.0F, 1.3F, 5.975F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.125F, KeyframeAnimations.posVec(0.975F, 1.19F, 2.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.5F, KeyframeAnimations.posVec(0.635F, 0.65F, -0.625F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(4.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("Right_ear", new AnimationChannel(AnimationChannel.Targets.SCALE,
    new Keyframe(2.4583F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.5F, KeyframeAnimations.scaleVec(1.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
    new Keyframe(2.7073F, KeyframeAnimations.scaleVec(1.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
    new Keyframe(2.7083F, KeyframeAnimations.scaleVec(0.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.LINEAR),
    new Keyframe(2.749F, KeyframeAnimations.scaleVec(0.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.LINEAR),
    new Keyframe(2.75F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("Body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.375F, KeyframeAnimations.degreeVec(-20.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0417F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.75F, KeyframeAnimations.degreeVec(-15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.125F, KeyframeAnimations.degreeVec(-15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("Body", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.375F, KeyframeAnimations.posVec(0.0F, 15.0F, 8.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0417F, KeyframeAnimations.posVec(0.0F, 15.0F, 8.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.625F, KeyframeAnimations.posVec(0.0F, 14.0F, 8.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.4167F, KeyframeAnimations.posVec(0.0F, 15.0F, 8.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.625F, KeyframeAnimations.posVec(0.0F, 15.0F, 8.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.75F, KeyframeAnimations.posVec(0.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.875F, KeyframeAnimations.posVec(0.0F, 0.0F, 5.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.125F, KeyframeAnimations.posVec(0.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("Body", new AnimationChannel(AnimationChannel.Targets.SCALE,
    new Keyframe(2.4583F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.5F, KeyframeAnimations.scaleVec(0.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.LINEAR),
    new Keyframe(2.7073F, KeyframeAnimations.scaleVec(0.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.LINEAR),
    new Keyframe(2.7083F, KeyframeAnimations.scaleVec(0.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.LINEAR),
    new Keyframe(2.749F, KeyframeAnimations.scaleVec(0.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.LINEAR),
    new Keyframe(2.75F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("Left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.375F, KeyframeAnimations.degreeVec(-20.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0417F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.3333F, KeyframeAnimations.degreeVec(-112.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.75F, KeyframeAnimations.degreeVec(-85.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.125F, KeyframeAnimations.degreeVec(-85.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.375F, KeyframeAnimations.degreeVec(-49.2759F, 50.461F, 38.1174F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.5F, KeyframeAnimations.degreeVec(-58.0396F, 38.3179F, 25.6926F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.625F, KeyframeAnimations.degreeVec(-49.2759F, 50.461F, 38.1174F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.75F, KeyframeAnimations.degreeVec(-58.0396F, 38.3179F, 25.6926F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(4.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("Left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.375F, KeyframeAnimations.posVec(0.0F, 15.0F, 9.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, 15.81F, 8.43F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0417F, KeyframeAnimations.posVec(0.0F, 15.0F, 8.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.625F, KeyframeAnimations.posVec(0.0F, 14.0F, 8.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.4167F, KeyframeAnimations.posVec(0.0F, 15.0F, 8.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.625F, KeyframeAnimations.posVec(0.0F, 15.0F, 8.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.75F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.875F, KeyframeAnimations.posVec(0.0F, 0.0F, 4.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.125F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.375F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(4.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("Left_arm", new AnimationChannel(AnimationChannel.Targets.SCALE,
    new Keyframe(2.4583F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.5F, KeyframeAnimations.scaleVec(0.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.LINEAR),
    new Keyframe(2.7073F, KeyframeAnimations.scaleVec(0.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.LINEAR),
    new Keyframe(2.7083F, KeyframeAnimations.scaleVec(0.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.LINEAR),
    new Keyframe(2.749F, KeyframeAnimations.scaleVec(0.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.LINEAR),
    new Keyframe(2.75F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("Right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.375F, KeyframeAnimations.degreeVec(-20.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0417F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.3333F, KeyframeAnimations.degreeVec(-21.5561F, -6.608F, -16.2407F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.9167F, KeyframeAnimations.degreeVec(-36.56F, -6.61F, -16.24F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.3333F, KeyframeAnimations.degreeVec(-21.5561F, -6.608F, -16.2407F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.75F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.875F, KeyframeAnimations.degreeVec(-23.4F, 0.49F, 1.2F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.125F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.4167F, KeyframeAnimations.degreeVec(-39.3247F, -7.9972F, -9.6385F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.7083F, KeyframeAnimations.degreeVec(-39.3247F, -7.9972F, -9.6385F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.9583F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("Right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.375F, KeyframeAnimations.posVec(0.0F, 15.0F, 9.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0417F, KeyframeAnimations.posVec(0.0F, 15.0F, 8.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.625F, KeyframeAnimations.posVec(0.0F, 14.0F, 8.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.4167F, KeyframeAnimations.posVec(0.0F, 15.0F, 8.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.625F, KeyframeAnimations.posVec(0.0F, 15.0F, 8.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.75F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.875F, KeyframeAnimations.posVec(0.0F, 0.0F, 5.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.125F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.4167F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.7083F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.9583F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("Right_arm", new AnimationChannel(AnimationChannel.Targets.SCALE,
    new Keyframe(2.4583F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.5F, KeyframeAnimations.scaleVec(0.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.LINEAR),
    new Keyframe(2.7073F, KeyframeAnimations.scaleVec(0.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.LINEAR),
    new Keyframe(2.7083F, KeyframeAnimations.scaleVec(0.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.LINEAR),
    new Keyframe(2.749F, KeyframeAnimations.scaleVec(0.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.LINEAR),
    new Keyframe(2.75F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("Left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.375F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.75F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.2083F, KeyframeAnimations.degreeVec(-90.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.4167F, KeyframeAnimations.degreeVec(-90.0F, 22.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.7083F, KeyframeAnimations.degreeVec(-120.0F, 22.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0833F, KeyframeAnimations.degreeVec(-80.0F, 22.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.625F, KeyframeAnimations.degreeVec(-80.0F, 22.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.75F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.875F, KeyframeAnimations.degreeVec(-17.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.125F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.2917F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("Left_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.375F, KeyframeAnimations.posVec(0.0F, 15.325F, 6.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, 16.0F, 8.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.625F, KeyframeAnimations.posVec(0.0F, 15.0F, 8.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.4167F, KeyframeAnimations.posVec(0.0F, 16.0F, 8.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.625F, KeyframeAnimations.posVec(0.0F, 16.0F, 8.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.75F, KeyframeAnimations.posVec(0.0F, 0.0F, -1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.875F, KeyframeAnimations.posVec(0.0F, 1.0F, 3.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.125F, KeyframeAnimations.posVec(0.0F, 0.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.2917F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("Left_leg", new AnimationChannel(AnimationChannel.Targets.SCALE,
    new Keyframe(2.4583F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.5F, KeyframeAnimations.scaleVec(0.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.LINEAR),
    new Keyframe(2.7073F, KeyframeAnimations.scaleVec(0.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.LINEAR),
    new Keyframe(2.7083F, KeyframeAnimations.scaleVec(0.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.LINEAR),
    new Keyframe(2.749F, KeyframeAnimations.scaleVec(0.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.LINEAR),
    new Keyframe(2.75F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("Right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.375F, KeyframeAnimations.degreeVec(-20.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0417F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.3333F, KeyframeAnimations.degreeVec(-55.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0417F, KeyframeAnimations.degreeVec(-55.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.125F, KeyframeAnimations.degreeVec(-47.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.4167F, KeyframeAnimations.degreeVec(-55.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.625F, KeyframeAnimations.degreeVec(-55.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.75F, KeyframeAnimations.degreeVec(-72.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.125F, KeyframeAnimations.degreeVec(-72.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.2917F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("Right_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.375F, KeyframeAnimations.posVec(0.0F, 15.325F, 6.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0417F, KeyframeAnimations.posVec(0.0F, 15.0F, 8.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.625F, KeyframeAnimations.posVec(0.0F, 14.0F, 8.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.4167F, KeyframeAnimations.posVec(0.0F, 15.0F, 8.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.625F, KeyframeAnimations.posVec(0.0F, 15.0F, 8.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.75F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.875F, KeyframeAnimations.posVec(0.0F, 0.0F, 4.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.125F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.2917F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("Right_leg", new AnimationChannel(AnimationChannel.Targets.SCALE,
    new Keyframe(2.4583F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.5F, KeyframeAnimations.scaleVec(1.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
    new Keyframe(2.7073F, KeyframeAnimations.scaleVec(1.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
    new Keyframe(2.7083F, KeyframeAnimations.scaleVec(0.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.LINEAR),
    new Keyframe(2.749F, KeyframeAnimations.scaleVec(0.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.LINEAR),
    new Keyframe(2.75F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("Left_wing", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.375F, KeyframeAnimations.degreeVec(-34.2333F, -47.7241F, 26.6165F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0417F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.6667F, KeyframeAnimations.degreeVec(-5.0F, -25.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.3333F, KeyframeAnimations.degreeVec(-10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.4583F, KeyframeAnimations.degreeVec(-12.95F, 1.75F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.625F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.75F, KeyframeAnimations.degreeVec(-15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.875F, KeyframeAnimations.degreeVec(-15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.125F, KeyframeAnimations.degreeVec(-15.8705F, -14.4652F, 4.0742F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(4.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("Left_wing", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.375F, KeyframeAnimations.posVec(-4.2F, 13.0F, 15.4F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0417F, KeyframeAnimations.posVec(-0.4F, 15.0F, 8.7F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.6667F, KeyframeAnimations.posVec(-1.5F, 14.0F, 12.2F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.3333F, KeyframeAnimations.posVec(-0.35F, 15.0F, 8.4F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.4583F, KeyframeAnimations.posVec(-0.05F, 15.86F, 8.54F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.625F, KeyframeAnimations.posVec(0.0F, 15.0F, 8.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.75F, KeyframeAnimations.posVec(0.0F, 0.0F, 1.5F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.875F, KeyframeAnimations.posVec(0.0F, 0.0F, 5.75F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.125F, KeyframeAnimations.posVec(-1.4F, 0.0F, 3.5F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(4.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("Left_wing", new AnimationChannel(AnimationChannel.Targets.SCALE,
    new Keyframe(2.4583F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.5F, KeyframeAnimations.scaleVec(0.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.LINEAR),
    new Keyframe(2.7073F, KeyframeAnimations.scaleVec(0.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.LINEAR),
    new Keyframe(2.7083F, KeyframeAnimations.scaleVec(0.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.LINEAR),
    new Keyframe(2.749F, KeyframeAnimations.scaleVec(0.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.LINEAR),
    new Keyframe(2.75F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("Right_wing", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.375F, KeyframeAnimations.degreeVec(-34.2333F, 47.7241F, -26.6165F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0417F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.6667F, KeyframeAnimations.degreeVec(-5.0F, 25.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.3333F, KeyframeAnimations.degreeVec(-10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.4583F, KeyframeAnimations.degreeVec(-12.95F, 1.75F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.625F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.75F, KeyframeAnimations.degreeVec(-15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.875F, KeyframeAnimations.degreeVec(-15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.125F, KeyframeAnimations.degreeVec(-15.8705F, 14.4652F, -4.0742F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(4.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("Right_wing", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.375F, KeyframeAnimations.posVec(4.2F, 13.0F, 15.3F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0417F, KeyframeAnimations.posVec(0.4F, 15.0F, 8.7F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.6667F, KeyframeAnimations.posVec(1.5F, 14.0F, 12.1F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.3333F, KeyframeAnimations.posVec(0.35F, 15.0F, 8.5F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.4583F, KeyframeAnimations.posVec(0.04F, 15.86F, 9.1F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.625F, KeyframeAnimations.posVec(0.0F, 15.0F, 8.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.75F, KeyframeAnimations.posVec(0.0F, 0.0F, 1.5F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.875F, KeyframeAnimations.posVec(0.0F, 0.0F, 5.7F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.125F, KeyframeAnimations.posVec(1.4F, 0.0F, 3.5F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(4.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("Right_wing", new AnimationChannel(AnimationChannel.Targets.SCALE,
    new Keyframe(2.4583F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.5F, KeyframeAnimations.scaleVec(0.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.LINEAR),
    new Keyframe(2.7073F, KeyframeAnimations.scaleVec(0.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.LINEAR),
    new Keyframe(2.7083F, KeyframeAnimations.scaleVec(0.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.LINEAR),
    new Keyframe(2.749F, KeyframeAnimations.scaleVec(0.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.LINEAR),
    new Keyframe(2.75F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .build();

    public static final AnimationDefinition ATTACK_RANGE = AnimationDefinition.Builder.withLength(2.0F)
    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-20.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.degreeVec(10.0845F, 7.3854F, 1.3096F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.125F, KeyframeAnimations.degreeVec(45.4976F, -1.4499F, 3.0355F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.25F, KeyframeAnimations.degreeVec(45.4976F, -1.4499F, 3.0355F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.625F, KeyframeAnimations.degreeVec(118.0F, -1.45F, 3.04F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.3333F, KeyframeAnimations.posVec(1.0F, 0.0F, 2.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.posVec(2.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0833F, KeyframeAnimations.posVec(0.0F, -7.0F, -14.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.125F, KeyframeAnimations.posVec(0.8F, -9.0F, -14.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.2083F, KeyframeAnimations.posVec(0.9F, -8.0F, -13.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.25F, KeyframeAnimations.posVec(0.8F, -9.0F, -14.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.625F, KeyframeAnimations.posVec(0.8F, -19.0F, -14.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-14.6535F, 42.9983F, 3.9161F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.degreeVec(-21.9928F, 43.8113F, 7.2942F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0833F, KeyframeAnimations.degreeVec(62.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.25F, KeyframeAnimations.degreeVec(62.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.625F, KeyframeAnimations.degreeVec(92.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0833F, KeyframeAnimations.posVec(0.0F, -3.0F, -6.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.25F, KeyframeAnimations.posVec(0.0F, -3.0F, -6.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.625F, KeyframeAnimations.posVec(0.0F, -6.0F, -6.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.3333F, KeyframeAnimations.degreeVec(0.0F, 37.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.degreeVec(-60.0F, 37.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0833F, KeyframeAnimations.degreeVec(46.5943F, -0.7574F, -9.4166F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.25F, KeyframeAnimations.degreeVec(46.5943F, -0.7574F, -9.4166F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.625F, KeyframeAnimations.degreeVec(-0.91F, -0.76F, -9.42F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.3333F, KeyframeAnimations.posVec(-0.7F, 0.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.posVec(0.4F, -0.4F, -1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0833F, KeyframeAnimations.posVec(0.2F, -6.0F, -9.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.25F, KeyframeAnimations.posVec(0.2F, -6.0F, -9.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.625F, KeyframeAnimations.posVec(0.2F, -13.0F, -9.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-171.3037F, 39.1792F, -20.2414F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.degreeVec(-206.3F, 39.18F, -20.24F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.125F, KeyframeAnimations.degreeVec(-85.1648F, 0.5443F, -0.8513F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.25F, KeyframeAnimations.degreeVec(-85.1504F, -4.4378F, -1.2734F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.625F, KeyframeAnimations.degreeVec(-2.65F, -4.44F, -1.27F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.3333F, KeyframeAnimations.posVec(1.8F, -2.0F, 3.3F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.posVec(2.6F, -2.0F, 3.3F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.125F, KeyframeAnimations.posVec(0.5F, -6.4F, -10.7F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.25F, KeyframeAnimations.posVec(0.5F, -6.4F, -10.7F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.625F, KeyframeAnimations.posVec(0.5F, -11.4F, -10.7F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.SCALE,
    new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9167F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0417F, KeyframeAnimations.scaleVec(1.0F, 1.5F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.1667F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.3333F, KeyframeAnimations.degreeVec(10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.degreeVec(15.0136F, 2.4148F, 0.6474F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.3333F, KeyframeAnimations.posVec(-1.0F, 0.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.posVec(-2.0F, 0.1F, -3.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.125F, KeyframeAnimations.posVec(0.0F, 0.1F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.SCALE,
    new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.3333F, KeyframeAnimations.scaleVec(1.0F, 1.1F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("Right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-8.4409F, 41.4137F, -1.5602F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.degreeVec(-31.6426F, 38.1915F, 10.0952F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0833F, KeyframeAnimations.degreeVec(76.7896F, 3.3101F, 7.4151F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.25F, KeyframeAnimations.degreeVec(76.7896F, 3.3101F, 7.4151F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.625F, KeyframeAnimations.degreeVec(126.79F, 3.31F, 7.42F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.3333F, KeyframeAnimations.posVec(-0.9F, 1.0F, 0.3F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.posVec(-1.3F, 1.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0833F, KeyframeAnimations.posVec(-0.3F, 0.3F, -0.8F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.25F, KeyframeAnimations.posVec(-0.3F, 0.3F, -0.8F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.SCALE,
    new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("left_wing", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-14.6535F, 42.9983F, 3.9161F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.degreeVec(-25.4928F, 43.8113F, 7.2942F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0833F, KeyframeAnimations.degreeVec(76.5774F, -19.7079F, -50.7715F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.25F, KeyframeAnimations.degreeVec(76.5774F, -19.7079F, -50.7715F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.625F, KeyframeAnimations.degreeVec(90.7483F, 4.9754F, -27.7508F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.9583F, KeyframeAnimations.degreeVec(17.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("left_wing", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.3333F, KeyframeAnimations.posVec(-0.325F, -0.875F, -5.425F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.posVec(-0.42F, -1.57F, -5.25F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0833F, KeyframeAnimations.posVec(-3.8F, 4.4F, -5.4F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.25F, KeyframeAnimations.posVec(-3.8F, 4.4F, -5.4F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.625F, KeyframeAnimations.posVec(-1.3F, -1.1F, -10.4F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.9583F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.3F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("right_wing", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-14.6535F, 42.9983F, 3.9161F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.degreeVec(-25.4928F, 43.8113F, 7.2942F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0833F, KeyframeAnimations.degreeVec(76.5774F, 19.7079F, 50.7715F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.25F, KeyframeAnimations.degreeVec(76.5774F, 19.7079F, 50.7715F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.625F, KeyframeAnimations.degreeVec(90.7483F, -4.9754F, 27.7508F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.9583F, KeyframeAnimations.degreeVec(10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("right_wing", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.3333F, KeyframeAnimations.posVec(4.6F, 0.0F, 6.3F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.posVec(5.1F, 0.0F, 6.75F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0833F, KeyframeAnimations.posVec(4.1F, 4.4F, -5.4F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.25F, KeyframeAnimations.posVec(4.1F, 4.4F, -5.4F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.625F, KeyframeAnimations.posVec(1.3F, -1.1F, -10.4F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.9583F, KeyframeAnimations.posVec(0.1F, 0.05F, 0.4F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("right_ear", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-20.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.degreeVec(10.0845F, 7.3854F, 1.3096F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.125F, KeyframeAnimations.degreeVec(45.4976F, -1.4499F, 3.0355F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.2083F, KeyframeAnimations.degreeVec(41.06F, -1.78F, 3.1F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.25F, KeyframeAnimations.degreeVec(45.4976F, -1.4499F, 3.0355F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.625F, KeyframeAnimations.degreeVec(118.0F, -1.45F, 3.04F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("right_ear", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.3333F, KeyframeAnimations.posVec(1.0F, -0.3F, 2.225F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.posVec(2.15F, 0.25F, 1.55F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0833F, KeyframeAnimations.posVec(0.05F, -6.45F, -14.45F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.125F, KeyframeAnimations.posVec(0.875F, -8.375F, -14.925F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.2083F, KeyframeAnimations.posVec(0.975F, -7.4F, -13.875F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.25F, KeyframeAnimations.posVec(0.875F, -8.4F, -14.9F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.625F, KeyframeAnimations.posVec(0.85F, -19.15F, -15.9F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("left_ear", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-20.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.degreeVec(10.0845F, 7.3854F, 1.3096F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.125F, KeyframeAnimations.degreeVec(45.4976F, -1.4499F, 3.0355F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.2083F, KeyframeAnimations.degreeVec(41.06F, -1.78F, 3.1F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.25F, KeyframeAnimations.degreeVec(45.4976F, -1.4499F, 3.0355F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.625F, KeyframeAnimations.degreeVec(118.0F, -1.45F, 3.04F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("left_ear", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.3333F, KeyframeAnimations.posVec(1.0F, -0.3F, 2.225F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.posVec(2.05F, 0.0F, 0.15F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0833F, KeyframeAnimations.posVec(0.05F, -6.95F, -14.75F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.125F, KeyframeAnimations.posVec(0.875F, -8.975F, -14.625F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.2083F, KeyframeAnimations.posVec(0.975F, -7.975F, -13.5F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.25F, KeyframeAnimations.posVec(0.875F, -9.0F, -14.6F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.625F, KeyframeAnimations.posVec(0.85F, -19.75F, -15.6F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .build();
}