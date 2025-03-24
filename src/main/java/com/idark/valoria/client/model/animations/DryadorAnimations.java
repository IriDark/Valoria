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
    new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-15.2207F, -9.6559F, 2.613F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.625F, KeyframeAnimations.degreeVec(10.2324F, -19.5047F, 0.8199F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9583F, KeyframeAnimations.degreeVec(19.62F, -8.16F, -0.88F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.25F, KeyframeAnimations.degreeVec(-15.2207F, 9.6559F, -2.613F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.6667F, KeyframeAnimations.degreeVec(10.2324F, 19.5047F, -0.8199F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.degreeVec(17.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.3333F, KeyframeAnimations.posVec(0.0F, -0.4F, 2.3F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.625F, KeyframeAnimations.posVec(2.0F, -2.6F, -12.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9583F, KeyframeAnimations.posVec(0.29F, -3.98F, -18.44F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.25F, KeyframeAnimations.posVec(0.0F, -0.4F, 2.3F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.6667F, KeyframeAnimations.posVec(-2.0F, -2.6F, -12.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, -4.1F, -18.5F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-15.2207F, -9.6559F, 2.613F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.625F, KeyframeAnimations.degreeVec(10.2324F, -19.5047F, 0.8199F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9583F, KeyframeAnimations.degreeVec(17.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.25F, KeyframeAnimations.degreeVec(-15.2207F, 9.6559F, -2.613F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.6667F, KeyframeAnimations.degreeVec(10.2324F, 19.5047F, -0.8199F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.degreeVec(17.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.3333F, KeyframeAnimations.posVec(0.0F, 0.0F, -3.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.625F, KeyframeAnimations.posVec(0.0F, -2.0F, -8.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9583F, KeyframeAnimations.posVec(0.0F, -3.0F, -12.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.25F, KeyframeAnimations.posVec(0.0F, 0.0F, -3.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.6667F, KeyframeAnimations.posVec(0.0F, -2.0F, -8.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, -3.0F, -12.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-22.1916F, -3.8102F, -9.2525F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.625F, KeyframeAnimations.degreeVec(-5.1754F, -14.9416F, 1.3378F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9583F, KeyframeAnimations.degreeVec(2.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.25F, KeyframeAnimations.degreeVec(-14.9346F, 2.711F, -6.5257F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.6667F, KeyframeAnimations.degreeVec(-5.0767F, 9.9616F, -0.8804F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.degreeVec(2.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.3333F, KeyframeAnimations.posVec(2.0F, 0.0F, -3.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.625F, KeyframeAnimations.posVec(0.6F, -4.0F, -7.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9583F, KeyframeAnimations.posVec(0.1F, -4.0F, -13.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.25F, KeyframeAnimations.posVec(0.0F, 0.0F, -4.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.6667F, KeyframeAnimations.posVec(-1.4F, -2.0F, -14.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.posVec(0.1F, -4.0F, -15.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-22.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.625F, KeyframeAnimations.degreeVec(-5.3191F, -19.9207F, 1.8169F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9583F, KeyframeAnimations.degreeVec(2.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.25F, KeyframeAnimations.degreeVec(-22.4807F, 0.9564F, 2.3099F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.6667F, KeyframeAnimations.degreeVec(-5.3191F, 19.9207F, -1.8169F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.degreeVec(-5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.3333F, KeyframeAnimations.posVec(0.0F, 0.0F, -6.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.625F, KeyframeAnimations.posVec(1.2F, -3.0F, -14.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9583F, KeyframeAnimations.posVec(0.4F, -4.0F, -15.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.25F, KeyframeAnimations.posVec(-0.9F, 0.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.6667F, KeyframeAnimations.posVec(-0.2F, -3.0F, -6.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.posVec(0.1F, -4.0F, -14.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.3333F, KeyframeAnimations.degreeVec(17.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.625F, KeyframeAnimations.degreeVec(-5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9583F, KeyframeAnimations.degreeVec(32.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.375F, KeyframeAnimations.degreeVec(10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.75F, KeyframeAnimations.degreeVec(20.0703F, -4.6978F, -1.7139F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.1667F, KeyframeAnimations.degreeVec(20.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.3333F, KeyframeAnimations.posVec(0.0F, 3.0F, -8.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.625F, KeyframeAnimations.posVec(0.0F, -1.8F, -11.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9583F, KeyframeAnimations.posVec(0.0F, -3.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.375F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.25F, KeyframeAnimations.degreeVec(10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.625F, KeyframeAnimations.degreeVec(20.0703F, -4.6978F, -1.7139F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9583F, KeyframeAnimations.degreeVec(20.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.375F, KeyframeAnimations.degreeVec(-5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.6667F, KeyframeAnimations.degreeVec(-5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.degreeVec(32.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.625F, KeyframeAnimations.posVec(0.0F, -0.56F, -1.95F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9167F, KeyframeAnimations.posVec(0.0F, -0.56F, -1.95F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.2917F, KeyframeAnimations.posVec(0.0F, 1.0F, -11.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.6667F, KeyframeAnimations.posVec(0.0F, -2.7F, -11.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, -3.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
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

    public static final AnimationDefinition ATTACK_MELEE = AnimationDefinition.Builder.withLength(1.5417F)
    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.25F, KeyframeAnimations.degreeVec(-7.7614F, 14.8687F, -2.0031F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.7917F, KeyframeAnimations.degreeVec(-21.9687F, 26.5381F, -6.8056F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.8333F, KeyframeAnimations.degreeVec(-13.6078F, 22.1489F, -4.9577F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9167F, KeyframeAnimations.degreeVec(5.4816F, 6.8058F, -7.585F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5417F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.25F, KeyframeAnimations.posVec(2.0F, -0.3F, 7.9F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.7917F, KeyframeAnimations.posVec(2.4F, -1.4F, 13.2F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.8333F, KeyframeAnimations.posVec(-0.51F, -0.67F, 5.74F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9167F, KeyframeAnimations.posVec(-2.3F, -2.4F, -18.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5417F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.25F, KeyframeAnimations.degreeVec(-15.0F, 25.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.7917F, KeyframeAnimations.degreeVec(-27.508F, 33.975F, -5.077F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9167F, KeyframeAnimations.degreeVec(30.8367F, 4.071F, -2.5711F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5417F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, 0.0F, 3.02F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.7917F, KeyframeAnimations.posVec(0.0F, 0.0F, 5.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9167F, KeyframeAnimations.posVec(0.0F, 0.0F, -9.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5417F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.2917F, KeyframeAnimations.degreeVec(-92.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.7917F, KeyframeAnimations.degreeVec(-120.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9167F, KeyframeAnimations.degreeVec(-12.134F, -29.3243F, -110.8212F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5417F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.2917F, KeyframeAnimations.posVec(0.0F, 5.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.7917F, KeyframeAnimations.posVec(0.0F, 11.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9167F, KeyframeAnimations.posVec(7.0F, 11.0F, -16.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5417F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.25F, KeyframeAnimations.degreeVec(17.8695F, 19.939F, 114.8394F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.7917F, KeyframeAnimations.degreeVec(40.7714F, 11.2701F, 133.1241F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.8333F, KeyframeAnimations.degreeVec(-29.6961F, 42.7023F, 44.5844F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9167F, KeyframeAnimations.degreeVec(-76.1386F, 8.6392F, -15.5725F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5417F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.25F, KeyframeAnimations.posVec(-4.6F, 9.0F, 13.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.7917F, KeyframeAnimations.posVec(1.0F, 10.0F, 20.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.8333F, KeyframeAnimations.posVec(-6.21F, 4.15F, 5.67F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9167F, KeyframeAnimations.posVec(-1.0F, 3.0F, -27.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5417F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.2917F, KeyframeAnimations.degreeVec(-7.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.7917F, KeyframeAnimations.degreeVec(-5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9167F, KeyframeAnimations.degreeVec(20.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5417F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.2917F, KeyframeAnimations.posVec(0.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.7917F, KeyframeAnimations.posVec(0.0F, 0.0F, 3.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9167F, KeyframeAnimations.posVec(1.0F, 0.0F, -1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5417F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.2917F, KeyframeAnimations.degreeVec(-7.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.7917F, KeyframeAnimations.degreeVec(-2.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9167F, KeyframeAnimations.degreeVec(-2.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5417F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.2917F, KeyframeAnimations.posVec(0.0F, 0.0F, -4.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.7917F, KeyframeAnimations.posVec(0.0F, 0.0F, -4.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9167F, KeyframeAnimations.posVec(1.0F, 1.0F, -3.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5417F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.SCALE,
    new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.2917F, KeyframeAnimations.scaleVec(1.0F, 1.1F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9167F, KeyframeAnimations.scaleVec(1.0F, 1.1F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .build();
}