package com.idark.valoria.client.model.animations;

import net.minecraft.client.animation.*;

public class DryadorAnimations {
    public static final AnimationDefinition IDLE = AnimationDefinition.Builder.withLength(4.0F).looping()
    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.8333F, KeyframeAnimations.degreeVec(-10.0F, -9.54F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.625F, KeyframeAnimations.degreeVec(-10.0F, -9.54F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.4167F, KeyframeAnimations.degreeVec(0.0F, 27.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0833F, KeyframeAnimations.degreeVec(0.0F, 27.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(4.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.625F, KeyframeAnimations.posVec(0.0F, -1.0F, -4.2F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.4167F, KeyframeAnimations.posVec(0.0F, -0.5F, -1.27F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0833F, KeyframeAnimations.posVec(0.0F, 0.0F, 2.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(4.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.625F, KeyframeAnimations.degreeVec(7.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0833F, KeyframeAnimations.degreeVec(-5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(4.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.625F, KeyframeAnimations.posVec(0.0F, -1.0F, -2.9F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0833F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(4.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.625F, KeyframeAnimations.degreeVec(-22.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0417F, KeyframeAnimations.degreeVec(-35.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(4.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.625F, KeyframeAnimations.posVec(0.0F, -2.0F, -4.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0833F, KeyframeAnimations.posVec(0.0F, 0.0F, -3.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(4.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.625F, KeyframeAnimations.degreeVec(-7.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.125F, KeyframeAnimations.degreeVec(10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(4.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.625F, KeyframeAnimations.posVec(0.0F, -2.0F, -3.9F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0833F, KeyframeAnimations.posVec(0.0F, 0.0F, 2.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(4.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.625F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0833F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.625F, KeyframeAnimations.posVec(0.0F, 0.0F, -0.7F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0833F, KeyframeAnimations.posVec(0.0F, 0.0F, -0.1F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.625F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0833F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.625F, KeyframeAnimations.posVec(0.0F, 0.0F, -0.6F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0833F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .build();

    public static final AnimationDefinition WALK = AnimationDefinition.Builder.withLength(2.5F).looping()
    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.4583F, KeyframeAnimations.degreeVec(-15.2207F, -9.6559F, 2.613F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.75F, KeyframeAnimations.degreeVec(2.7324F, -19.5047F, 0.8199F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.4167F, KeyframeAnimations.degreeVec(-15.2207F, 9.6559F, -2.613F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.8333F, KeyframeAnimations.degreeVec(7.2324F, 19.5047F, -0.8199F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.4583F, KeyframeAnimations.posVec(0.0F, -0.7F, 2.3F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.75F, KeyframeAnimations.posVec(2.0F, -2.1F, -8.9F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.4167F, KeyframeAnimations.posVec(0.0F, -0.6F, 2.3F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.8333F, KeyframeAnimations.posVec(-2.0F, -2.2F, -10.3F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.4583F, KeyframeAnimations.degreeVec(-15.2207F, -9.6559F, 2.613F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.75F, KeyframeAnimations.degreeVec(2.7324F, -19.5047F, 0.8199F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.4167F, KeyframeAnimations.degreeVec(-15.2207F, 9.6559F, -2.613F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.8333F, KeyframeAnimations.degreeVec(5.2324F, 19.5047F, -0.8199F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.4583F, KeyframeAnimations.posVec(0.0F, 0.0F, -3.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, -2.0F, -8.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.4167F, KeyframeAnimations.posVec(0.0F, 0.0F, -3.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.8333F, KeyframeAnimations.posVec(0.0F, -2.0F, -8.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.4583F, KeyframeAnimations.degreeVec(-22.1916F, -3.8102F, -9.2525F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.75F, KeyframeAnimations.degreeVec(-5.1754F, -14.9416F, 1.3378F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.4167F, KeyframeAnimations.degreeVec(-14.9346F, 2.711F, -6.5257F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.8333F, KeyframeAnimations.degreeVec(-5.0767F, 9.9616F, -0.8804F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.4583F, KeyframeAnimations.posVec(1.8F, 0.0F, -3.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.75F, KeyframeAnimations.posVec(0.3F, -4.0F, -7.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.4167F, KeyframeAnimations.posVec(0.0F, 0.0F, -4.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.8333F, KeyframeAnimations.posVec(-1.4F, -2.0F, -14.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.4583F, KeyframeAnimations.degreeVec(-22.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.75F, KeyframeAnimations.degreeVec(-5.3191F, -19.9207F, 1.8169F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.4167F, KeyframeAnimations.degreeVec(-22.4807F, 0.9564F, 2.3099F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.8333F, KeyframeAnimations.degreeVec(-5.3191F, 19.9207F, -1.8169F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.4583F, KeyframeAnimations.posVec(0.0F, 0.0F, -6.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.75F, KeyframeAnimations.posVec(1.2F, -2.1F, -12.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.4167F, KeyframeAnimations.posVec(-0.9F, -0.5F, -2.8F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.8333F, KeyframeAnimations.posVec(0.0F, -3.0F, -6.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.4583F, KeyframeAnimations.degreeVec(17.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.75F, KeyframeAnimations.degreeVec(-5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5417F, KeyframeAnimations.degreeVec(10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.9167F, KeyframeAnimations.degreeVec(20.0703F, -4.6978F, -1.7139F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.4583F, KeyframeAnimations.posVec(0.0F, 3.0F, -8.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, -1.8F, -11.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5417F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.375F, KeyframeAnimations.degreeVec(10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.75F, KeyframeAnimations.degreeVec(20.0703F, -4.6978F, -1.7139F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.4583F, KeyframeAnimations.degreeVec(-5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.8333F, KeyframeAnimations.degreeVec(-5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.375F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, -0.56F, -1.95F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.4583F, KeyframeAnimations.posVec(0.0F, 1.0F, -11.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.8333F, KeyframeAnimations.posVec(0.0F, -2.7F, -11.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .build();

    public static final AnimationDefinition ATTACK_RANGED = AnimationDefinition.Builder.withLength(3.0F)
    .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.2083F, KeyframeAnimations.degreeVec(-17.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.625F, KeyframeAnimations.degreeVec(7.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0833F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.6667F, KeyframeAnimations.degreeVec(37.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.9167F, KeyframeAnimations.degreeVec(12.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.375F, KeyframeAnimations.degreeVec(12.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.5417F, KeyframeAnimations.degreeVec(7.24F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.7917F, KeyframeAnimations.degreeVec(23.97F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.2083F, KeyframeAnimations.posVec(0.0F, 1.2F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.625F, KeyframeAnimations.posVec(0.0F, 2.2F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0833F, KeyframeAnimations.posVec(0.0F, 4.2F, -6.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.posVec(0.0F, 3.2F, -8.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.6667F, KeyframeAnimations.posVec(0.0F, -0.6F, -14.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.9167F, KeyframeAnimations.posVec(0.0F, -0.6F, -9.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.375F, KeyframeAnimations.posVec(0.0F, -0.6F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.SCALE,
    new Keyframe(1.375F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.6667F, KeyframeAnimations.degreeVec(27.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.9167F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.375F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.5417F, KeyframeAnimations.degreeVec(-7.24F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.7917F, KeyframeAnimations.degreeVec(-23.97F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.3333F, KeyframeAnimations.posVec(0.0F, 0.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.posVec(0.0F, 0.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.6667F, KeyframeAnimations.posVec(0.0F, 0.0F, -14.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.9167F, KeyframeAnimations.posVec(0.0F, 0.0F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.375F, KeyframeAnimations.posVec(0.0F, 0.0F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-30.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.degreeVec(-47.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.6667F, KeyframeAnimations.degreeVec(72.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.9167F, KeyframeAnimations.degreeVec(70.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.375F, KeyframeAnimations.degreeVec(70.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.3333F, KeyframeAnimations.posVec(0.0F, -2.0F, 11.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.posVec(0.0F, -4.0F, 16.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5833F, KeyframeAnimations.posVec(0.0F, -4.62F, -22.06F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.6667F, KeyframeAnimations.posVec(0.0F, -23.0F, -51.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.9167F, KeyframeAnimations.posVec(0.0F, -23.0F, -48.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.375F, KeyframeAnimations.posVec(0.0F, -23.0F, -47.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.6667F, KeyframeAnimations.posVec(0.0F, -9.46F, -29.39F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-17.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.degreeVec(-25.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.6667F, KeyframeAnimations.degreeVec(82.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.9167F, KeyframeAnimations.degreeVec(82.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.375F, KeyframeAnimations.degreeVec(82.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.3333F, KeyframeAnimations.posVec(0.0F, 0.0F, 3.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 4.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.6667F, KeyframeAnimations.posVec(0.0F, -7.0F, -30.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.9167F, KeyframeAnimations.posVec(0.0F, -7.0F, -26.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.375F, KeyframeAnimations.posVec(0.0F, -7.0F, -26.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-195.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.degreeVec(-219.8925F, 3.2115F, 3.8342F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.6667F, KeyframeAnimations.degreeVec(-52.3079F, -2.4589F, -4.6161F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.9167F, KeyframeAnimations.degreeVec(-52.3079F, -2.4589F, -4.6161F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.375F, KeyframeAnimations.degreeVec(-52.3079F, -2.4589F, -4.6161F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.125F, KeyframeAnimations.posVec(0.0F, 4.1F, -9.09F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.3333F, KeyframeAnimations.posVec(0.0F, 16.0F, 8.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.posVec(0.6F, 16.0F, 15.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.6667F, KeyframeAnimations.posVec(0.6F, -10.0F, -44.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.9167F, KeyframeAnimations.posVec(0.6F, -10.0F, -40.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.375F, KeyframeAnimations.posVec(0.6F, -10.0F, -40.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.SCALE,
    new Keyframe(1.5417F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.6667F, KeyframeAnimations.scaleVec(1.0F, 1.2F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.75F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-195.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.degreeVec(-219.5687F, -6.4086F, -7.6926F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.6667F, KeyframeAnimations.degreeVec(-52.3079F, -2.4589F, -4.6161F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.9167F, KeyframeAnimations.degreeVec(-52.3079F, -2.4589F, -4.6161F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.2083F, KeyframeAnimations.degreeVec(-52.3079F, -2.4589F, -4.6161F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.5833F, KeyframeAnimations.degreeVec(-3.6093F, -1.0596F, 12.3494F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.125F, KeyframeAnimations.posVec(0.0F, 4.1F, -7.09F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.3333F, KeyframeAnimations.posVec(0.0F, 16.0F, 8.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.posVec(-1.7F, 16.0F, 14.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.6667F, KeyframeAnimations.posVec(0.6F, -14.0F, -48.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.9167F, KeyframeAnimations.posVec(0.6F, -10.0F, -40.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.2083F, KeyframeAnimations.posVec(0.6F, -10.0F, -40.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.5833F, KeyframeAnimations.posVec(-1.58F, -9.96F, -25.82F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.SCALE,
    new Keyframe(1.5417F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.6667F, KeyframeAnimations.scaleVec(1.0F, 1.2F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.75F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .build();

    public static final AnimationDefinition ATTACK_MELEE = AnimationDefinition.Builder.withLength(1.5F)
    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.25F, KeyframeAnimations.degreeVec(-7.7614F, 14.8687F, -2.0031F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.7917F, KeyframeAnimations.degreeVec(-21.9687F, 26.5381F, -6.8056F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.8333F, KeyframeAnimations.degreeVec(-13.6078F, 22.1489F, -4.9577F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9167F, KeyframeAnimations.degreeVec(5.4816F, 6.8058F, -7.585F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.25F, KeyframeAnimations.posVec(2.0F, -0.3F, 7.9F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.7917F, KeyframeAnimations.posVec(2.4F, -1.4F, 13.2F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.8333F, KeyframeAnimations.posVec(-0.51F, -0.67F, 5.74F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9167F, KeyframeAnimations.posVec(-2.3F, -2.4F, -18.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.25F, KeyframeAnimations.degreeVec(-15.0F, 25.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.7917F, KeyframeAnimations.degreeVec(-27.508F, 33.975F, -5.077F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9167F, KeyframeAnimations.degreeVec(30.8367F, 4.071F, -2.5711F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, 0.0F, 3.02F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.7917F, KeyframeAnimations.posVec(0.0F, 0.0F, 5.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9167F, KeyframeAnimations.posVec(0.0F, 0.0F, -9.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.2917F, KeyframeAnimations.degreeVec(-92.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.7917F, KeyframeAnimations.degreeVec(-120.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9167F, KeyframeAnimations.degreeVec(-12.134F, -29.3243F, -110.8212F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.2917F, KeyframeAnimations.posVec(0.0F, 5.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.7917F, KeyframeAnimations.posVec(0.0F, 11.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9167F, KeyframeAnimations.posVec(7.0F, 11.0F, -16.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.25F, KeyframeAnimations.degreeVec(17.8695F, 19.939F, 114.8394F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.7917F, KeyframeAnimations.degreeVec(40.7714F, 11.2701F, 133.1241F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.8333F, KeyframeAnimations.degreeVec(-29.6961F, 42.7023F, 44.5844F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9167F, KeyframeAnimations.degreeVec(-76.1386F, 8.6392F, -15.5725F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.25F, KeyframeAnimations.posVec(-4.6F, 9.0F, 13.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.7917F, KeyframeAnimations.posVec(1.0F, 10.0F, 20.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.8333F, KeyframeAnimations.posVec(-6.21F, 4.15F, 5.67F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9167F, KeyframeAnimations.posVec(-1.0F, 3.0F, -27.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.2917F, KeyframeAnimations.degreeVec(-7.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.7917F, KeyframeAnimations.degreeVec(-5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9167F, KeyframeAnimations.degreeVec(20.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.2917F, KeyframeAnimations.posVec(0.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.7917F, KeyframeAnimations.posVec(0.0F, 0.0F, 3.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9167F, KeyframeAnimations.posVec(1.0F, 0.0F, -1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.2917F, KeyframeAnimations.degreeVec(-7.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.7917F, KeyframeAnimations.degreeVec(-2.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9167F, KeyframeAnimations.degreeVec(-2.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.2917F, KeyframeAnimations.posVec(0.0F, 0.0F, -4.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.7917F, KeyframeAnimations.posVec(0.0F, 0.0F, -4.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9167F, KeyframeAnimations.posVec(1.0F, 1.0F, -3.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.SCALE,
    new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.2917F, KeyframeAnimations.scaleVec(1.0F, 1.1F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9167F, KeyframeAnimations.scaleVec(1.0F, 1.1F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .build();

    public static final AnimationDefinition PHASE_TRANSITION = AnimationDefinition.Builder.withLength(4.5F)
    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.1667F, KeyframeAnimations.degreeVec(26.4032F, 7.9813F, -15.6249F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.4583F, KeyframeAnimations.degreeVec(42.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.7083F, KeyframeAnimations.degreeVec(22.0184F, -2.8257F, 6.9502F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.6667F, KeyframeAnimations.degreeVec(32.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.125F, KeyframeAnimations.degreeVec(22.98F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.3333F, KeyframeAnimations.degreeVec(-30.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.4583F, KeyframeAnimations.degreeVec(-47.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0F, KeyframeAnimations.degreeVec(-51.04F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.25F, KeyframeAnimations.degreeVec(17.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.5833F, KeyframeAnimations.degreeVec(12.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.7083F, KeyframeAnimations.degreeVec(20.0317F, -7.0927F, -2.5685F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.8333F, KeyframeAnimations.degreeVec(20.0317F, -7.0927F, -2.5685F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(4.0F, KeyframeAnimations.degreeVec(21.16F, 6.6425F, -10.7182F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(4.0833F, KeyframeAnimations.degreeVec(-3.2634F, 7.0354F, 2.7931F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(4.2917F, KeyframeAnimations.degreeVec(-10.7667F, -7.936F, 3.6415F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(4.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.1667F, KeyframeAnimations.posVec(-2.0F, -8.0F, -6.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.4583F, KeyframeAnimations.posVec(0.0F, -10.5F, -11.3F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.7083F, KeyframeAnimations.posVec(1.3F, -3.41F, -5.98F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.6667F, KeyframeAnimations.posVec(0.0F, -3.0F, -18.41F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.125F, KeyframeAnimations.posVec(0.0F, -1.0F, -11.76F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.3333F, KeyframeAnimations.posVec(0.0F, -1.0F, 13.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.4583F, KeyframeAnimations.posVec(0.0F, -3.0F, 20.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0F, KeyframeAnimations.posVec(0.0F, -6.4F, 22.8F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.25F, KeyframeAnimations.posVec(0.0F, 0.0F, -3.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.5833F, KeyframeAnimations.posVec(0.0F, 0.3F, -1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.7083F, KeyframeAnimations.posVec(-1.4F, -0.2F, -7.1F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.8333F, KeyframeAnimations.posVec(-1.4F, -0.2F, -7.1F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(4.0F, KeyframeAnimations.posVec(-3.4F, -0.2F, -8.1F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(4.0833F, KeyframeAnimations.posVec(1.5F, -0.2F, 2.9F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(4.2917F, KeyframeAnimations.posVec(0.5F, -0.2F, 5.9F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(4.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.1667F, KeyframeAnimations.degreeVec(40.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.4583F, KeyframeAnimations.degreeVec(50.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9583F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.6667F, KeyframeAnimations.degreeVec(27.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.125F, KeyframeAnimations.degreeVec(15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.3333F, KeyframeAnimations.degreeVec(-20.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.4583F, KeyframeAnimations.degreeVec(-30.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0F, KeyframeAnimations.degreeVec(-37.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.5833F, KeyframeAnimations.degreeVec(-5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.7083F, KeyframeAnimations.degreeVec(10.0952F, 21.5815F, 1.3568F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.8333F, KeyframeAnimations.degreeVec(10.0952F, 21.5815F, 1.3568F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(4.0F, KeyframeAnimations.degreeVec(10.0952F, 21.5815F, 1.3568F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(4.0833F, KeyframeAnimations.degreeVec(-6.9306F, 6.6973F, 3.2814F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(4.2917F, KeyframeAnimations.degreeVec(-7.0688F, -13.1539F, 5.7089F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(4.4583F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.1667F, KeyframeAnimations.posVec(0.0F, -4.0F, 7.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9583F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.6667F, KeyframeAnimations.posVec(0.0F, 0.0F, -8.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.125F, KeyframeAnimations.posVec(0.0F, 0.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.3333F, KeyframeAnimations.posVec(0.0F, 0.0F, 5.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.4583F, KeyframeAnimations.posVec(0.0F, 0.0F, 7.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0F, KeyframeAnimations.posVec(0.0F, -1.0F, 8.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.25F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.5833F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.7F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.7083F, KeyframeAnimations.posVec(0.0F, 0.0F, -2.3F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.8333F, KeyframeAnimations.posVec(0.0F, 0.0F, -2.3F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(4.0F, KeyframeAnimations.posVec(0.0F, 0.0F, -2.3F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(4.0833F, KeyframeAnimations.posVec(0.0F, 0.0F, 1.7F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(4.2917F, KeyframeAnimations.posVec(-1.0F, 0.0F, 2.7F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(4.4583F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-33.126F, 10.5179F, -6.7929F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.4583F, KeyframeAnimations.degreeVec(-67.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0417F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5833F, KeyframeAnimations.degreeVec(-123.649F, 3.1072F, -33.3024F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.125F, KeyframeAnimations.degreeVec(-128.65F, 3.11F, -33.3F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.3333F, KeyframeAnimations.degreeVec(-117.9891F, -13.1581F, 7.0623F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.4583F, KeyframeAnimations.degreeVec(-131.7325F, -46.6439F, 33.1265F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0F, KeyframeAnimations.degreeVec(-66.0718F, -55.9094F, -45.871F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.5833F, KeyframeAnimations.degreeVec(-37.5F, 0.0F, -20.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.7083F, KeyframeAnimations.degreeVec(15.6928F, 16.8855F, -15.3347F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.8333F, KeyframeAnimations.degreeVec(15.6928F, 16.8855F, -15.3347F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(4.0F, KeyframeAnimations.degreeVec(15.6928F, 16.8855F, -15.3347F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(4.0833F, KeyframeAnimations.degreeVec(-51.81F, 16.89F, -15.33F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(4.2917F, KeyframeAnimations.degreeVec(-23.8372F, -12.9148F, -2.2135F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(4.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.1667F, KeyframeAnimations.posVec(0.0F, -4.0F, -1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.4583F, KeyframeAnimations.posVec(0.0F, -2.0F, -7.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0417F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5833F, KeyframeAnimations.posVec(-4.22F, 10.66F, -21.44F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.125F, KeyframeAnimations.posVec(-4.0F, 11.0F, -16.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.3333F, KeyframeAnimations.posVec(2.0F, 11.0F, -1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.4583F, KeyframeAnimations.posVec(7.0F, 9.0F, 4.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0F, KeyframeAnimations.posVec(7.0F, 6.0F, 8.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.25F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.5833F, KeyframeAnimations.posVec(3.0F, 3.0F, -7.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.7083F, KeyframeAnimations.posVec(2.0F, 2.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.8333F, KeyframeAnimations.posVec(2.0F, 2.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(4.0F, KeyframeAnimations.posVec(2.0F, 2.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(4.0833F, KeyframeAnimations.posVec(0.9F, 2.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(4.2917F, KeyframeAnimations.posVec(1.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(4.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-32.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.4583F, KeyframeAnimations.degreeVec(-50.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0417F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5833F, KeyframeAnimations.degreeVec(-123.649F, -3.1072F, 33.3024F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.125F, KeyframeAnimations.degreeVec(-128.65F, -3.11F, 33.3F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.3333F, KeyframeAnimations.degreeVec(-102.727F, 8.6153F, -0.5867F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.4583F, KeyframeAnimations.degreeVec(-131.7325F, 46.6439F, -33.1265F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0F, KeyframeAnimations.degreeVec(-66.0718F, 55.9094F, 45.871F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.5F, KeyframeAnimations.degreeVec(-37.5F, 0.0F, 20.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.7083F, KeyframeAnimations.degreeVec(15.0F, 0.0F, 20.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.8333F, KeyframeAnimations.degreeVec(15.0F, 0.0F, 20.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(4.0F, KeyframeAnimations.degreeVec(15.0F, 0.0F, 20.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(4.0833F, KeyframeAnimations.degreeVec(-25.0F, 0.0F, 20.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(4.2917F, KeyframeAnimations.degreeVec(-7.2472F, -1.936F, 5.1226F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(4.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.1667F, KeyframeAnimations.posVec(0.0F, -4.0F, -1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.4583F, KeyframeAnimations.posVec(0.0F, -4.0F, -7.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0417F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5833F, KeyframeAnimations.posVec(4.27F, 10.68F, -21.44F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.125F, KeyframeAnimations.posVec(4.0F, 11.0F, -17.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.3333F, KeyframeAnimations.posVec(-1.0F, 9.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.4583F, KeyframeAnimations.posVec(-7.0F, 9.0F, 4.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0F, KeyframeAnimations.posVec(-7.0F, 6.0F, 8.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.25F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.5F, KeyframeAnimations.posVec(-3.0F, 3.0F, -7.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.7083F, KeyframeAnimations.posVec(-4.0F, 3.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.8333F, KeyframeAnimations.posVec(-4.0F, 3.0F, 2.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(4.0F, KeyframeAnimations.posVec(-4.0F, 3.0F, 2.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(4.0833F, KeyframeAnimations.posVec(-1.9F, 2.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(4.2917F, KeyframeAnimations.posVec(-0.2F, 1.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(4.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.1667F, KeyframeAnimations.degreeVec(7.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.4583F, KeyframeAnimations.degreeVec(25.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.7083F, KeyframeAnimations.degreeVec(-8.91F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9583F, KeyframeAnimations.degreeVec(-10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.6667F, KeyframeAnimations.degreeVec(12.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.125F, KeyframeAnimations.degreeVec(7.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.4583F, KeyframeAnimations.degreeVec(-17.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0F, KeyframeAnimations.degreeVec(-17.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.3333F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.5833F, KeyframeAnimations.degreeVec(0.0F, 17.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.9583F, KeyframeAnimations.degreeVec(0.0F, 17.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(4.0833F, KeyframeAnimations.degreeVec(-7.5F, 11.45F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(4.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.1667F, KeyframeAnimations.posVec(0.0F, 0.0F, 11.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.4583F, KeyframeAnimations.posVec(0.0F, 0.0F, 15.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.7083F, KeyframeAnimations.posVec(0.0F, 0.0F, 2.75F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9583F, KeyframeAnimations.posVec(0.0F, 0.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.6667F, KeyframeAnimations.posVec(0.0F, 0.0F, -3.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.125F, KeyframeAnimations.posVec(0.0F, 0.0F, -3.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.4583F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.3333F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(4.0833F, KeyframeAnimations.posVec(0.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(4.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-12.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.4583F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.7083F, KeyframeAnimations.degreeVec(10.78F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9583F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.6667F, KeyframeAnimations.degreeVec(14.01F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.125F, KeyframeAnimations.degreeVec(3.03F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.4583F, KeyframeAnimations.degreeVec(-6.57F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.1667F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.5833F, KeyframeAnimations.degreeVec(-10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.7083F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.8333F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(4.0F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(4.0833F, KeyframeAnimations.degreeVec(-10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(4.3333F, KeyframeAnimations.degreeVec(12.24F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(4.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.1667F, KeyframeAnimations.posVec(0.0F, 0.0F, 7.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.4583F, KeyframeAnimations.posVec(0.0F, 0.0F, 9.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.7083F, KeyframeAnimations.posVec(0.0F, 0.0F, 7.63F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9583F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.6667F, KeyframeAnimations.posVec(0.0F, -0.24F, -2.21F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.125F, KeyframeAnimations.posVec(0.0F, -0.42F, -2.34F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.4583F, KeyframeAnimations.posVec(0.0F, -0.55F, 2.88F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.1667F, KeyframeAnimations.posVec(0.0F, 6.0F, -9.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.5833F, KeyframeAnimations.posVec(0.0F, 8.0F, -9.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.7083F, KeyframeAnimations.posVec(0.0F, -1.0F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.8333F, KeyframeAnimations.posVec(0.0F, -1.0F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(4.0F, KeyframeAnimations.posVec(0.0F, -1.0F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(4.0833F, KeyframeAnimations.posVec(0.0F, 5.0F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(4.3333F, KeyframeAnimations.posVec(0.0F, 0.0F, -6.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(4.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .build();

    public static final AnimationDefinition SUMMON = AnimationDefinition.Builder.withLength(3.0F)
    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5F, KeyframeAnimations.degreeVec(8.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.degreeVec(-12.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.6667F, KeyframeAnimations.degreeVec(18.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.9583F, KeyframeAnimations.degreeVec(18.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.25F, KeyframeAnimations.degreeVec(-19.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, -0.3F, -4.7F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.posVec(0.0F, -0.3F, 4.9F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.6667F, KeyframeAnimations.posVec(0.0F, -1.1F, -10.1F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.9583F, KeyframeAnimations.posVec(0.0F, -1.1F, -10.1F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.25F, KeyframeAnimations.posVec(0.0F, -1.1F, 7.9F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5F, KeyframeAnimations.degreeVec(7.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.degreeVec(-7.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.6667F, KeyframeAnimations.degreeVec(17.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.9583F, KeyframeAnimations.degreeVec(17.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.25F, KeyframeAnimations.degreeVec(-10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.0F, -1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 2.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.6667F, KeyframeAnimations.posVec(0.0F, 0.0F, -3.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.9583F, KeyframeAnimations.posVec(0.0F, 0.0F, -3.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.25F, KeyframeAnimations.posVec(0.0F, 0.0F, 3.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.2083F, KeyframeAnimations.degreeVec(-45.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.6667F, KeyframeAnimations.degreeVec(-70.8934F, 20.7048F, 22.2077F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.degreeVec(-95.89F, 20.7F, 22.21F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.6667F, KeyframeAnimations.degreeVec(-59.0314F, -27.8227F, -4.5721F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.9583F, KeyframeAnimations.degreeVec(-59.0314F, -27.8227F, -4.5721F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.25F, KeyframeAnimations.degreeVec(-49.3761F, -60.023F, -51.3094F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.2083F, KeyframeAnimations.posVec(0.0F, 0.0F, -11.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.6667F, KeyframeAnimations.posVec(-5.0F, 4.0F, -15.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.posVec(-5.0F, 8.0F, -11.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.6667F, KeyframeAnimations.posVec(3.0F, 1.0F, -16.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.9583F, KeyframeAnimations.posVec(3.0F, 1.0F, -16.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.25F, KeyframeAnimations.posVec(8.0F, 7.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.2083F, KeyframeAnimations.degreeVec(-45.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.6667F, KeyframeAnimations.degreeVec(-70.8934F, -20.7048F, -22.2077F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.degreeVec(-95.89F, -20.7F, -22.21F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.6667F, KeyframeAnimations.degreeVec(-59.0314F, 27.8227F, 4.5721F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.9583F, KeyframeAnimations.degreeVec(-59.0314F, 27.8227F, 4.5721F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.25F, KeyframeAnimations.degreeVec(-49.3761F, 60.023F, 51.3094F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.2083F, KeyframeAnimations.posVec(0.0F, 0.0F, -11.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.6667F, KeyframeAnimations.posVec(5.0F, 4.0F, -15.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.posVec(5.0F, 8.0F, -11.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.6667F, KeyframeAnimations.posVec(-3.0F, 1.0F, -16.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.9583F, KeyframeAnimations.posVec(-3.0F, 1.0F, -16.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.25F, KeyframeAnimations.posVec(-8.0F, 7.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .build();
}