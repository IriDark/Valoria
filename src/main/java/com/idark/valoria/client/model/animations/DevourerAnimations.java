package com.idark.valoria.client.model.animations;

import net.minecraft.client.animation.*;

public class DevourerAnimations{
    public static final AnimationDefinition JUMP = AnimationDefinition.Builder.withLength(3.0F)
    .addAnimation("skull", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.375F, KeyframeAnimations.degreeVec(20.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.8333F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.25F, KeyframeAnimations.degreeVec(-107.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.75F, KeyframeAnimations.degreeVec(-90.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.1667F, KeyframeAnimations.degreeVec(-180.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.5417F, KeyframeAnimations.degreeVec(-197.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.9167F, KeyframeAnimations.degreeVec(-180.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("skull", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -31.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, 107.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.25F, KeyframeAnimations.posVec(0.0F, 110.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.625F, KeyframeAnimations.posVec(0.0F, 108.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.8333F, KeyframeAnimations.posVec(0.0F, 110.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.8333F, KeyframeAnimations.posVec(0.0F, 117.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0F, KeyframeAnimations.posVec(0.0F, -4.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("mouth_bottom", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.125F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5833F, KeyframeAnimations.degreeVec(-37.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.9583F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.3333F, KeyframeAnimations.degreeVec(-52.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.7917F, KeyframeAnimations.degreeVec(-52.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.9167F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .build();

    public static final AnimationDefinition ATTACK = AnimationDefinition.Builder.withLength(1.5F)
    .addAnimation("skull", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -31.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.375F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.posVec(0.0F, -31.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("skull", new AnimationChannel(AnimationChannel.Targets.SCALE,
    new Keyframe(0.0F, KeyframeAnimations.scaleVec(0.2F, 0.2F, 0.2F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.7083F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.scaleVec(0.2F, 0.2F, 0.2F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("mouth_bottom", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.2917F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5417F, KeyframeAnimations.degreeVec(-62.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.7083F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .build();
}