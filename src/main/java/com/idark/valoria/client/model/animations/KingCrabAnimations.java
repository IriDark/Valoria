package com.idark.valoria.client.model.animations;

import net.minecraft.client.animation.*;

public class KingCrabAnimations {
    public static final AnimationDefinition IDLE = AnimationDefinition.Builder.withLength(2.5F).looping()
    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.25F, KeyframeAnimations.degreeVec(7.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.25F, KeyframeAnimations.posVec(0.0F, 0.0F, -1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("arm_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 15.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("arm_left", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.25F, KeyframeAnimations.posVec(1.0F, -6.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("arm_right", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -15.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("arm_right", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.25F, KeyframeAnimations.posVec(-1.0F, -6.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_left_1", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.1667F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 7.5F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_left_1", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.1667F, KeyframeAnimations.posVec(0.0F, -0.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_right_1", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.1667F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -7.5F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_right_1", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.1667F, KeyframeAnimations.posVec(0.0F, -0.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_left_3", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9167F, KeyframeAnimations.degreeVec(0.0F, 10.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.625F, KeyframeAnimations.degreeVec(0.0F, 10.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_left_3", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9167F, KeyframeAnimations.posVec(0.0F, 0.0F, -1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.625F, KeyframeAnimations.posVec(0.0F, 0.0F, -1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_right_3", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.1667F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -7.5F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_right_3", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.1667F, KeyframeAnimations.posVec(0.0F, -0.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_left_2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.1667F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 7.5F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_left_2", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.1667F, KeyframeAnimations.posVec(0.0F, -0.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_right_2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9167F, KeyframeAnimations.degreeVec(0.0F, -10.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.625F, KeyframeAnimations.degreeVec(0.0F, -10.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_right_2", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9167F, KeyframeAnimations.posVec(0.0F, 0.0F, -1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.625F, KeyframeAnimations.posVec(0.0F, 0.0F, -1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .build();

    public static final AnimationDefinition WALK = AnimationDefinition.Builder.withLength(1.5F).looping()
    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.375F, KeyframeAnimations.degreeVec(0.0F, -7.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5833F, KeyframeAnimations.degreeVec(5.9368F, -6.7842F, -7.5243F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.8333F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0833F, KeyframeAnimations.degreeVec(0.0F, 7.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.2917F, KeyframeAnimations.degreeVec(5.9368F, 6.7842F, 7.5243F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.375F, KeyframeAnimations.posVec(0.0F, 0.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.8333F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0833F, KeyframeAnimations.posVec(0.0F, 0.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("arm_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.375F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 15.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5833F, KeyframeAnimations.degreeVec(17.5F, 0.0F, 12.43F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.8333F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 5.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0833F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.2917F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 17.5F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("arm_left", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.375F, KeyframeAnimations.posVec(-2.0F, -2.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5833F, KeyframeAnimations.posVec(-4.92F, 1.75F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.8333F, KeyframeAnimations.posVec(-3.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0833F, KeyframeAnimations.posVec(3.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.2917F, KeyframeAnimations.posVec(1.0F, -4.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("arm_right", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.375F, KeyframeAnimations.degreeVec(2.5782F, -7.0453F, -20.1587F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5833F, KeyframeAnimations.degreeVec(-10.3042F, -4.6734F, 4.859F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.8333F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0833F, KeyframeAnimations.degreeVec(0.0F, -7.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.2917F, KeyframeAnimations.degreeVec(5.9368F, -6.7842F, -7.5243F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("arm_right", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.375F, KeyframeAnimations.posVec(-4.0F, -2.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5833F, KeyframeAnimations.posVec(-2.5F, -2.0F, -2.14F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.8333F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0833F, KeyframeAnimations.posVec(1.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_left_1", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.375F, KeyframeAnimations.degreeVec(0.0F, -32.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5833F, KeyframeAnimations.degreeVec(-4.7124F, -32.6386F, 21.2907F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9583F, KeyframeAnimations.degreeVec(-11.504F, 10.7237F, 5.7545F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.2083F, KeyframeAnimations.degreeVec(14.2625F, 20.0972F, 26.1088F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_left_1", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.375F, KeyframeAnimations.posVec(0.0F, 0.0F, 3.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5833F, KeyframeAnimations.posVec(0.0F, 0.0F, 4.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9583F, KeyframeAnimations.posVec(-1.0F, 1.0F, -6.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.2083F, KeyframeAnimations.posVec(-2.0F, -2.0F, -7.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_right_1", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.375F, KeyframeAnimations.degreeVec(0.0F, -32.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.6667F, KeyframeAnimations.degreeVec(12.7514F, -31.3798F, -16.1536F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9583F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_right_1", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.375F, KeyframeAnimations.posVec(2.0F, -2.0F, -7.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.6667F, KeyframeAnimations.posVec(2.0F, -1.0F, -3.8F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9583F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_left_3", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.4583F, KeyframeAnimations.degreeVec(0.0F, -22.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.625F, KeyframeAnimations.degreeVec(-6.8205F, -22.0524F, 10.1244F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9583F, KeyframeAnimations.degreeVec(-5.0589F, 20.2466F, 5.5184F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.2083F, KeyframeAnimations.degreeVec(-11.0322F, -2.8325F, 21.3873F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_left_3", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.4583F, KeyframeAnimations.posVec(-1.0F, 0.0F, 4.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.625F, KeyframeAnimations.posVec(-1.0F, 2.0F, 4.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9583F, KeyframeAnimations.posVec(1.0F, 0.0F, -4.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.2083F, KeyframeAnimations.posVec(1.0F, -2.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_right_3", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.2083F, KeyframeAnimations.degreeVec(0.0F, -5.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.375F, KeyframeAnimations.degreeVec(6.8205F, -22.0524F, -10.1244F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.7083F, KeyframeAnimations.degreeVec(5.0589F, 20.2466F, -5.5184F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9583F, KeyframeAnimations.degreeVec(11.0322F, -2.8325F, -21.3873F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.25F, KeyframeAnimations.degreeVec(9.914F, 9.2695F, -10.8936F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_right_3", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.2083F, KeyframeAnimations.posVec(-2.0F, 0.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.375F, KeyframeAnimations.posVec(-2.0F, -1.0F, -4.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.7083F, KeyframeAnimations.posVec(-1.0F, 0.0F, 4.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9583F, KeyframeAnimations.posVec(-1.0F, -2.0F, 2.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.25F, KeyframeAnimations.posVec(0.57F, -0.23F, 1.08F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_left_2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.3333F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -12.5F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.6667F, KeyframeAnimations.degreeVec(0.0F, -17.5F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9583F, KeyframeAnimations.degreeVec(0.3341F, 12.4784F, 17.5651F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.25F, KeyframeAnimations.degreeVec(-1.7507F, 7.1519F, -5.9103F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_left_2", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.3333F, KeyframeAnimations.posVec(0.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.6667F, KeyframeAnimations.posVec(0.0F, 2.0F, 4.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9583F, KeyframeAnimations.posVec(0.0F, 0.0F, -4.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.25F, KeyframeAnimations.posVec(0.0F, -1.18F, -2.33F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_right_2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.25F, KeyframeAnimations.degreeVec(5.0696F, 10.6967F, 19.8127F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.4583F, KeyframeAnimations.degreeVec(0.0F, -7.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.75F, KeyframeAnimations.degreeVec(-12.7514F, 31.3798F, -16.1536F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0417F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_right_2", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.25F, KeyframeAnimations.posVec(0.38F, 2.0F, -0.85F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.4583F, KeyframeAnimations.posVec(1.0F, 0.0F, -4.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.75F, KeyframeAnimations.posVec(2.0F, 0.0F, 1.8F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0417F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .build();

    public static final AnimationDefinition ATTACK1 = AnimationDefinition.Builder.withLength(1.5F)
    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.2917F, KeyframeAnimations.degreeVec(-20.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.degreeVec(-30.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.1667F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.3333F, KeyframeAnimations.degreeVec(10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.2917F, KeyframeAnimations.posVec(0.0F, 2.0F, 5.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 3.0F, 7.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.1667F, KeyframeAnimations.posVec(0.0F, 0.0F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.3333F, KeyframeAnimations.posVec(0.0F, 0.0F, -1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("arm_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.2917F, KeyframeAnimations.degreeVec(-20.0F, 0.0F, -47.5F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.degreeVec(-40.0F, 0.0F, -47.5F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.1667F, KeyframeAnimations.degreeVec(-72.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.3333F, KeyframeAnimations.degreeVec(-36.5523F, 7.3011F, -23.9759F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("arm_left", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.2917F, KeyframeAnimations.posVec(-8.0F, 11.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.posVec(-8.0F, 15.0F, 2.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.1667F, KeyframeAnimations.posVec(12.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.3333F, KeyframeAnimations.posVec(2.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("arm_right", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.2917F, KeyframeAnimations.degreeVec(-20.0F, 0.0F, 47.5F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.degreeVec(-40.0F, 0.0F, 47.5F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.1667F, KeyframeAnimations.degreeVec(-72.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.3333F, KeyframeAnimations.degreeVec(-36.5523F, 7.3011F, -23.9759F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("arm_right", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.2917F, KeyframeAnimations.posVec(8.0F, 11.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.posVec(8.0F, 15.0F, 2.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.1667F, KeyframeAnimations.posVec(-12.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.3333F, KeyframeAnimations.posVec(2.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_left_1", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.2917F, KeyframeAnimations.degreeVec(-0.8704F, -15.0762F, 12.4626F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.degreeVec(-1.9663F, 9.6853F, 1.596F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.1667F, KeyframeAnimations.degreeVec(-2.407F, 24.6793F, 0.8831F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.3333F, KeyframeAnimations.degreeVec(-1.753F, 7.8002F, -2.2045F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_left_1", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.2917F, KeyframeAnimations.posVec(-1.0F, 2.0F, 3.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 5.0F, 2.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.1667F, KeyframeAnimations.posVec(-1.0F, 0.0F, -11.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.3333F, KeyframeAnimations.posVec(1.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_right_1", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.2917F, KeyframeAnimations.degreeVec(-0.8704F, 15.0762F, -12.4626F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.degreeVec(-1.9663F, -9.6853F, -1.596F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.1667F, KeyframeAnimations.degreeVec(-2.407F, -24.6793F, -0.8831F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.3333F, KeyframeAnimations.degreeVec(-1.753F, 7.8002F, -2.2045F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_right_1", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.2917F, KeyframeAnimations.posVec(1.0F, 2.0F, 3.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 5.0F, 2.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.1667F, KeyframeAnimations.posVec(1.0F, 0.0F, -11.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.3333F, KeyframeAnimations.posVec(-1.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_left_3", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.2917F, KeyframeAnimations.degreeVec(0.0F, 20.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 20.0F, 7.5F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.1667F, KeyframeAnimations.degreeVec(22.5F, 20.0F, 7.5F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.3333F, KeyframeAnimations.degreeVec(-2.6212F, 2.5172F, 8.2879F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_left_3", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.2917F, KeyframeAnimations.posVec(0.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 2.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.1667F, KeyframeAnimations.posVec(0.0F, -1.0F, -8.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.3333F, KeyframeAnimations.posVec(0.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_right_3", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.2917F, KeyframeAnimations.degreeVec(0.0F, -20.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, -20.0F, -7.5F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.1667F, KeyframeAnimations.degreeVec(22.5F, -20.0F, -7.5F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.3333F, KeyframeAnimations.degreeVec(-2.6212F, -2.5172F, -8.2879F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_right_3", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.2917F, KeyframeAnimations.posVec(0.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 2.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.1667F, KeyframeAnimations.posVec(0.0F, -1.0F, -8.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.3333F, KeyframeAnimations.posVec(0.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_left_2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.2917F, KeyframeAnimations.degreeVec(-2.5F, 0.0F, 15.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.degreeVec(-7.6841F, -2.1539F, 24.7676F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.1667F, KeyframeAnimations.degreeVec(-7.68F, -2.15F, 24.77F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.3333F, KeyframeAnimations.degreeVec(-0.03F, 0.43F, 15.11F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_left_2", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.2917F, KeyframeAnimations.posVec(-1.0F, 0.0F, 2.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.posVec(-1.4F, 0.0F, 3.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.1667F, KeyframeAnimations.posVec(-1.4F, -2.0F, -9.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.3333F, KeyframeAnimations.posVec(-1.4F, -1.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_right_2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.2917F, KeyframeAnimations.degreeVec(-2.5F, 0.0F, -15.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.degreeVec(-7.6841F, 2.1539F, -24.7676F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.1667F, KeyframeAnimations.degreeVec(-7.68F, 2.15F, -24.77F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.3333F, KeyframeAnimations.degreeVec(-0.03F, -0.43F, -15.11F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_right_2", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.2917F, KeyframeAnimations.posVec(1.0F, 0.0F, 2.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.posVec(1.4F, 0.0F, 3.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.1667F, KeyframeAnimations.posVec(1.4F, -2.0F, -9.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.3333F, KeyframeAnimations.posVec(1.4F, -1.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .build();

    public static final AnimationDefinition ATTACK2 = AnimationDefinition.Builder.withLength(2.0F)
    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.2917F, KeyframeAnimations.degreeVec(-20.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.degreeVec(-30.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.1667F, KeyframeAnimations.degreeVec(25.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.degreeVec(25.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.75F, KeyframeAnimations.degreeVec(-10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.2917F, KeyframeAnimations.posVec(0.0F, 2.0F, 5.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 3.0F, 7.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.1667F, KeyframeAnimations.posVec(0.0F, -2.0F, -12.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.posVec(0.0F, -2.0F, -12.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.75F, KeyframeAnimations.posVec(0.0F, 0.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("arm_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.2917F, KeyframeAnimations.degreeVec(-20.0F, 0.0F, -47.5F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.degreeVec(-40.0F, 0.0F, -47.5F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.1667F, KeyframeAnimations.degreeVec(-100.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.degreeVec(-100.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.75F, KeyframeAnimations.degreeVec(-25.31F, -16.67F, -31.23F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("arm_left", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.2917F, KeyframeAnimations.posVec(-8.0F, 11.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.posVec(-8.0F, 15.0F, 2.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.1667F, KeyframeAnimations.posVec(9.0F, -10.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.posVec(9.0F, -10.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.75F, KeyframeAnimations.posVec(-1.0F, 7.0F, 4.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("arm_right", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.2917F, KeyframeAnimations.degreeVec(-20.0F, 0.0F, 47.5F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.degreeVec(-40.0F, 0.0F, 47.5F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.1667F, KeyframeAnimations.degreeVec(-100.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.degreeVec(-100.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.75F, KeyframeAnimations.degreeVec(-25.3112F, 16.6658F, 31.2325F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("arm_right", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.2917F, KeyframeAnimations.posVec(8.0F, 11.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.posVec(8.0F, 15.0F, 2.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.1667F, KeyframeAnimations.posVec(-9.0F, -10.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.posVec(-9.0F, -10.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.75F, KeyframeAnimations.posVec(1.0F, 7.0F, 4.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_left_1", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.2917F, KeyframeAnimations.degreeVec(-0.8704F, -15.0762F, 12.4626F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.degreeVec(-1.9663F, 9.6853F, 1.596F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.1667F, KeyframeAnimations.degreeVec(-1.8177F, -0.3046F, 1.9633F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.degreeVec(-1.8177F, -0.3046F, 1.9633F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.75F, KeyframeAnimations.degreeVec(-1.8195F, 19.758F, 3.8946F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_left_1", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.2917F, KeyframeAnimations.posVec(-1.0F, 2.0F, 3.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 5.0F, 2.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.1667F, KeyframeAnimations.posVec(-1.0F, -1.0F, -6.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.posVec(-1.0F, -1.0F, -6.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.75F, KeyframeAnimations.posVec(-1.0F, 0.0F, -8.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_right_1", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.2917F, KeyframeAnimations.degreeVec(-0.8704F, 15.0762F, -12.4626F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.degreeVec(-1.9663F, -9.6853F, -1.596F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.1667F, KeyframeAnimations.degreeVec(-1.4076F, 12.7303F, -4.8789F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.degreeVec(-1.4076F, 12.7303F, -4.8789F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.75F, KeyframeAnimations.degreeVec(-1.8195F, -19.758F, -3.8946F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_right_1", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.2917F, KeyframeAnimations.posVec(1.0F, 2.0F, 3.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 5.0F, 2.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.1667F, KeyframeAnimations.posVec(1.0F, -2.0F, -6.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.posVec(1.0F, -2.0F, -6.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.75F, KeyframeAnimations.posVec(1.0F, 0.0F, -8.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_left_3", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.2917F, KeyframeAnimations.degreeVec(0.0F, 20.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 20.0F, 7.5F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.1667F, KeyframeAnimations.degreeVec(22.5F, 20.0F, 7.5F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.degreeVec(22.5F, 20.0F, 7.5F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.75F, KeyframeAnimations.degreeVec(-4.6405F, 8.5483F, 3.2373F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_left_3", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.2917F, KeyframeAnimations.posVec(0.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 2.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.1667F, KeyframeAnimations.posVec(0.0F, 0.0F, -7.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.posVec(0.0F, 0.0F, -7.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.75F, KeyframeAnimations.posVec(1.0F, -1.0F, -7.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_right_3", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.2917F, KeyframeAnimations.degreeVec(0.0F, -20.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, -20.0F, -7.5F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.1667F, KeyframeAnimations.degreeVec(20.941F, -11.5789F, -28.441F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.degreeVec(20.941F, -11.5789F, -28.441F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.75F, KeyframeAnimations.degreeVec(-4.6405F, -8.5483F, -3.2373F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_right_3", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.2917F, KeyframeAnimations.posVec(0.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 2.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.1667F, KeyframeAnimations.posVec(2.0F, -1.0F, -7.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.posVec(2.0F, -1.0F, -7.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.75F, KeyframeAnimations.posVec(-1.0F, -1.0F, -7.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_left_2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.2917F, KeyframeAnimations.degreeVec(-2.5F, 0.0F, 15.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.degreeVec(-7.6841F, -2.1539F, 24.7676F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.1667F, KeyframeAnimations.degreeVec(-7.68F, -2.15F, 24.77F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.degreeVec(-7.68F, -2.15F, 24.77F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.75F, KeyframeAnimations.degreeVec(5.8745F, 7.5446F, -17.4187F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_left_2", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.2917F, KeyframeAnimations.posVec(-1.0F, 0.0F, 2.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.posVec(-1.4F, 0.0F, 3.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.1667F, KeyframeAnimations.posVec(-2.4F, -1.0F, -7.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.posVec(-2.4F, -1.0F, -7.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.75F, KeyframeAnimations.posVec(1.6F, 2.0F, -7.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_right_2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.2917F, KeyframeAnimations.degreeVec(-2.5F, 0.0F, -15.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.degreeVec(-7.6841F, 2.1539F, -24.7676F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.1667F, KeyframeAnimations.degreeVec(9.2362F, -2.4338F, -19.89F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.degreeVec(9.2362F, -2.4338F, -19.89F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.75F, KeyframeAnimations.degreeVec(5.8745F, -7.5446F, 17.4187F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_right_2", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.2917F, KeyframeAnimations.posVec(1.0F, 0.0F, 2.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.posVec(1.4F, 0.0F, 3.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.1667F, KeyframeAnimations.posVec(2.4F, -1.0F, -7.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.posVec(2.4F, -1.0F, -7.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.75F, KeyframeAnimations.posVec(-1.6F, 2.0F, -7.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .build();

    public static final AnimationDefinition ATTACK3 = AnimationDefinition.Builder.withLength(2.0F)
    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.1667F, KeyframeAnimations.degreeVec(1.7538F, 9.8466F, 10.1511F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.7917F, KeyframeAnimations.degreeVec(2.7812F, 22.1422F, 15.8311F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9167F, KeyframeAnimations.degreeVec(15.0F, -12.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.125F, KeyframeAnimations.degreeVec(15.0F, -12.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.375F, KeyframeAnimations.degreeVec(2.7812F, 22.1422F, 15.8311F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.1667F, KeyframeAnimations.posVec(0.0F, 2.0F, 3.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.7917F, KeyframeAnimations.posVec(0.0F, 2.0F, 7.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9167F, KeyframeAnimations.posVec(0.0F, 0.0F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.125F, KeyframeAnimations.posVec(0.0F, 0.0F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.375F, KeyframeAnimations.posVec(0.0F, 2.0F, 7.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("arm_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.1667F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 32.5F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.7917F, KeyframeAnimations.degreeVec(-27.5F, 0.0F, -25.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9167F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 30.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.125F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 30.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.375F, KeyframeAnimations.degreeVec(-27.5F, 0.0F, -25.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("arm_left", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.1667F, KeyframeAnimations.posVec(-6.0F, -2.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.7917F, KeyframeAnimations.posVec(-5.0F, 2.0F, -3.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9167F, KeyframeAnimations.posVec(3.0F, -2.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.125F, KeyframeAnimations.posVec(3.0F, -2.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.375F, KeyframeAnimations.posVec(-5.0F, 2.0F, -3.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("arm_right", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.1667F, KeyframeAnimations.degreeVec(25.5607F, -1.041F, 32.2598F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.7917F, KeyframeAnimations.degreeVec(19.4154F, 16.9848F, -9.5759F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9167F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -32.5F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.125F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -32.5F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.375F, KeyframeAnimations.degreeVec(19.4154F, 16.9848F, -9.5759F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("arm_right", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.1667F, KeyframeAnimations.posVec(9.0F, 13.0F, 3.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.7917F, KeyframeAnimations.posVec(15.0F, 12.0F, 3.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9167F, KeyframeAnimations.posVec(-11.56F, -3.28F, -1.27F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.125F, KeyframeAnimations.posVec(-13.0F, -2.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.375F, KeyframeAnimations.posVec(15.0F, 12.0F, 3.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_left_1", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-0.8704F, -15.0762F, -10.0374F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-1.9631F, -32.8701F, -21.9879F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5F, KeyframeAnimations.degreeVec(3.1749F, -10.7144F, 7.6703F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.7917F, KeyframeAnimations.degreeVec(3.4319F, 4.2654F, 8.5695F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9167F, KeyframeAnimations.degreeVec(3.1268F, -20.6905F, 7.121F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.125F, KeyframeAnimations.degreeVec(3.1268F, -20.6905F, 7.121F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.375F, KeyframeAnimations.degreeVec(3.4319F, 4.2654F, 8.5695F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_left_1", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.1667F, KeyframeAnimations.posVec(0.0F, 1.2F, 2.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.3333F, KeyframeAnimations.posVec(-2.0F, 2.0F, 4.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5F, KeyframeAnimations.posVec(-5.0F, -2.0F, 2.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.7917F, KeyframeAnimations.posVec(-5.0F, -2.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9167F, KeyframeAnimations.posVec(0.0F, -2.0F, -1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.125F, KeyframeAnimations.posVec(0.0F, -2.0F, -1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.375F, KeyframeAnimations.posVec(-5.0F, -2.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_right_1", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-5.5704F, -16.465F, -15.6125F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.375F, KeyframeAnimations.degreeVec(-4.7525F, 20.8082F, -20.79F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5F, KeyframeAnimations.degreeVec(-4.3068F, 15.7311F, -21.1063F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.6667F, KeyframeAnimations.degreeVec(-4.683F, 22.6217F, -29.1554F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.7917F, KeyframeAnimations.degreeVec(-3.5582F, 11.6837F, -11.3883F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9167F, KeyframeAnimations.degreeVec(-2.332F, 24.8297F, 5.8487F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.125F, KeyframeAnimations.degreeVec(-2.332F, 24.8297F, 5.8487F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.375F, KeyframeAnimations.degreeVec(-3.5582F, 11.6837F, -11.3883F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_right_1", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.1667F, KeyframeAnimations.posVec(0.0F, 2.0F, 3.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.375F, KeyframeAnimations.posVec(-0.96F, 3.4F, 9.15F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5F, KeyframeAnimations.posVec(-1.0F, 2.0F, 11.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.6667F, KeyframeAnimations.posVec(-1.0F, 2.0F, 14.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.7917F, KeyframeAnimations.posVec(-1.3F, 4.89F, 11.93F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9167F, KeyframeAnimations.posVec(0.0F, -1.0F, -9.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.125F, KeyframeAnimations.posVec(0.0F, -1.0F, -9.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.375F, KeyframeAnimations.posVec(-1.3F, 4.89F, 11.93F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_left_3", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.1667F, KeyframeAnimations.degreeVec(0.439F, 12.4813F, 4.9236F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.3333F, KeyframeAnimations.degreeVec(0.4745F, -2.5192F, 4.8001F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.4346F, 22.4792F, 4.9988F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.6667F, KeyframeAnimations.degreeVec(-0.1173F, 22.5142F, -7.5119F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.7917F, KeyframeAnimations.degreeVec(1.1965F, 10.0805F, -15.0981F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9167F, KeyframeAnimations.degreeVec(-3.8537F, -4.3114F, 13.8051F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.125F, KeyframeAnimations.degreeVec(-3.8537F, -4.3114F, 13.8051F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.375F, KeyframeAnimations.degreeVec(1.1965F, 10.0805F, -15.0981F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_left_3", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.1667F, KeyframeAnimations.posVec(0.0F, -0.4F, -1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.3333F, KeyframeAnimations.posVec(0.0F, -0.4F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, -0.4F, -1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.6667F, KeyframeAnimations.posVec(0.0F, -0.4F, -1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.7917F, KeyframeAnimations.posVec(0.0F, -0.4F, -0.6F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9167F, KeyframeAnimations.posVec(-3.0F, 0.6F, -3.6F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.125F, KeyframeAnimations.posVec(-3.0F, 0.6F, -3.6F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.375F, KeyframeAnimations.posVec(0.0F, -0.4F, -0.6F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_right_3", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.1667F, KeyframeAnimations.degreeVec(0.6474F, -22.4148F, -27.5136F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.375F, KeyframeAnimations.degreeVec(0.8195F, 17.587F, -26.9827F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5417F, KeyframeAnimations.degreeVec(-0.6768F, -9.9089F, -34.9826F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.6667F, KeyframeAnimations.degreeVec(-0.6768F, -9.9089F, -34.9826F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.7917F, KeyframeAnimations.degreeVec(-0.7946F, 12.5882F, -35.2888F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9167F, KeyframeAnimations.degreeVec(8.453F, 24.2819F, -18.3743F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.125F, KeyframeAnimations.degreeVec(8.453F, 24.2819F, -18.3743F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.375F, KeyframeAnimations.degreeVec(-0.7946F, 12.5882F, -35.2888F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_right_3", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.1667F, KeyframeAnimations.posVec(0.0F, 2.2F, 3.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.375F, KeyframeAnimations.posVec(2.0F, 2.2F, 12.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5417F, KeyframeAnimations.posVec(2.0F, 2.2F, 11.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.6667F, KeyframeAnimations.posVec(2.0F, 2.2F, 11.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.7917F, KeyframeAnimations.posVec(3.0F, 2.2F, 14.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9167F, KeyframeAnimations.posVec(3.0F, -0.8F, -8.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.125F, KeyframeAnimations.posVec(3.0F, -0.8F, -8.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.375F, KeyframeAnimations.posVec(3.0F, 2.2F, 14.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_left_2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-0.9845F, 7.4355F, -7.564F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-0.9803F, -7.5578F, -7.3041F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.3395F, 5.0219F, 7.544F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.7917F, KeyframeAnimations.degreeVec(0.3395F, 5.0219F, 7.544F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9167F, KeyframeAnimations.degreeVec(14.391F, -7.0765F, 28.289F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.125F, KeyframeAnimations.degreeVec(14.391F, -7.0765F, 28.289F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.375F, KeyframeAnimations.degreeVec(0.3395F, 5.0219F, 7.544F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_left_2", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.1667F, KeyframeAnimations.posVec(-2.0F, 1.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.3333F, KeyframeAnimations.posVec(-2.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5F, KeyframeAnimations.posVec(-4.0F, -1.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.7917F, KeyframeAnimations.posVec(-4.0F, -1.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9167F, KeyframeAnimations.posVec(-4.0F, 0.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.125F, KeyframeAnimations.posVec(-4.0F, 0.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.375F, KeyframeAnimations.posVec(-4.0F, -1.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_right_2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-12.2127F, 2.6851F, -17.7873F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.375F, KeyframeAnimations.degreeVec(-10.9972F, 11.2795F, 1.3914F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5F, KeyframeAnimations.degreeVec(-9.6338F, 8.0004F, 9.3225F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.6667F, KeyframeAnimations.degreeVec(-12.5731F, 6.7886F, -34.075F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.7917F, KeyframeAnimations.degreeVec(-10.4184F, 9.7951F, -19.2214F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9167F, KeyframeAnimations.degreeVec(14.7527F, 2.2103F, -7.382F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.125F, KeyframeAnimations.degreeVec(14.7527F, 2.2103F, -7.382F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.375F, KeyframeAnimations.degreeVec(-10.4184F, 9.7951F, -19.2214F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_right_2", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.1667F, KeyframeAnimations.posVec(0.0F, 2.0F, 3.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.375F, KeyframeAnimations.posVec(-0.74F, 4.98F, 9.64F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5F, KeyframeAnimations.posVec(-1.0F, 6.0F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.6667F, KeyframeAnimations.posVec(0.2F, 2.3F, 11.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.7917F, KeyframeAnimations.posVec(2.2F, 3.3F, 11.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9167F, KeyframeAnimations.posVec(0.2F, 0.3F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.125F, KeyframeAnimations.posVec(0.2F, 0.3F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.375F, KeyframeAnimations.posVec(2.2F, 3.3F, 11.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .build();

    public static final AnimationDefinition HIDE = AnimationDefinition.Builder.withLength(1.2917F)
    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.25F, KeyframeAnimations.degreeVec(-20.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.4167F, KeyframeAnimations.degreeVec(-19.1431F, 5.9032F, 16.5037F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.625F, KeyframeAnimations.degreeVec(-20.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.75F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0417F, KeyframeAnimations.degreeVec(0.0F, 7.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, 9.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.4167F, KeyframeAnimations.posVec(4.0F, 9.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.625F, KeyframeAnimations.posVec(0.0F, 9.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, -7.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9167F, KeyframeAnimations.posVec(0.0F, -2.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0833F, KeyframeAnimations.posVec(0.0F, -7.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("arm_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.2917F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -32.5F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -32.5F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("arm_left", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.2917F, KeyframeAnimations.posVec(-2.0F, 19.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5F, KeyframeAnimations.posVec(-2.0F, 8.0F, -9.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("arm_left", new AnimationChannel(AnimationChannel.Targets.SCALE,
    new Keyframe(0.3333F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.4583F, KeyframeAnimations.scaleVec(1.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("arm_right", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.2917F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 32.5F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 32.5F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("arm_right", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.2917F, KeyframeAnimations.posVec(2.0F, 19.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5F, KeyframeAnimations.posVec(2.0F, 14.0F, -15.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("arm_right", new AnimationChannel(AnimationChannel.Targets.SCALE,
    new Keyframe(0.3333F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.4583F, KeyframeAnimations.scaleVec(1.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_left_1", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.2917F, KeyframeAnimations.degreeVec(-29.6158F, -16.9946F, -34.2772F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.375F, KeyframeAnimations.degreeVec(-25.3327F, -5.4363F, -13.1493F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.625F, KeyframeAnimations.degreeVec(-21.741F, -17.2659F, -36.66F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_left_1", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.2917F, KeyframeAnimations.posVec(-3.87F, 14.65F, -0.45F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.625F, KeyframeAnimations.posVec(-16.0F, 0.0F, -1.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_left_1", new AnimationChannel(AnimationChannel.Targets.SCALE,
    new Keyframe(0.3333F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.4167F, KeyframeAnimations.scaleVec(1.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_right_1", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.2917F, KeyframeAnimations.degreeVec(-29.6158F, 16.9946F, 34.2772F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.4583F, KeyframeAnimations.degreeVec(-25.3327F, 5.4363F, 13.1493F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.625F, KeyframeAnimations.degreeVec(-21.741F, 17.2659F, 36.66F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_right_1", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.2917F, KeyframeAnimations.posVec(3.87F, 18.65F, -0.45F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.625F, KeyframeAnimations.posVec(16.0F, 12.0F, -1.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_right_1", new AnimationChannel(AnimationChannel.Targets.SCALE,
    new Keyframe(0.3333F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.4167F, KeyframeAnimations.scaleVec(1.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_left_3", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.2917F, KeyframeAnimations.degreeVec(-12.3292F, -11.4391F, -33.9587F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.375F, KeyframeAnimations.degreeVec(-35.4524F, 4.2705F, 4.2405F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.625F, KeyframeAnimations.degreeVec(-21.741F, -17.2659F, -36.66F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_left_3", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.2917F, KeyframeAnimations.posVec(-3.87F, 11.6F, -3.46F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.625F, KeyframeAnimations.posVec(-16.0F, -2.0F, -7.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_left_3", new AnimationChannel(AnimationChannel.Targets.SCALE,
    new Keyframe(0.3333F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.4167F, KeyframeAnimations.scaleVec(1.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_right_3", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.2917F, KeyframeAnimations.degreeVec(-12.3292F, 11.4391F, 33.9587F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.375F, KeyframeAnimations.degreeVec(-35.4524F, -4.2705F, -4.2405F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.625F, KeyframeAnimations.degreeVec(-21.741F, 17.2659F, 36.66F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_right_3", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.2917F, KeyframeAnimations.posVec(3.87F, 14.6F, -3.46F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.625F, KeyframeAnimations.posVec(16.0F, 9.0F, -7.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_right_3", new AnimationChannel(AnimationChannel.Targets.SCALE,
    new Keyframe(0.3333F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.4167F, KeyframeAnimations.scaleVec(1.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_left_2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.2917F, KeyframeAnimations.degreeVec(-24.7851F, -12.3106F, -24.7851F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.375F, KeyframeAnimations.degreeVec(-29.0183F, 0.1657F, -2.1767F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.625F, KeyframeAnimations.degreeVec(-21.741F, -17.2659F, -36.66F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_left_2", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.2917F, KeyframeAnimations.posVec(-3.87F, 12.5F, -2.63F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.625F, KeyframeAnimations.posVec(-16.0F, 0.0F, -4.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_left_2", new AnimationChannel(AnimationChannel.Targets.SCALE,
    new Keyframe(0.3333F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.4167F, KeyframeAnimations.scaleVec(1.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_right_2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.2917F, KeyframeAnimations.degreeVec(-24.7851F, 12.3106F, 24.7851F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.375F, KeyframeAnimations.degreeVec(-29.0183F, -0.1657F, 2.1767F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.625F, KeyframeAnimations.degreeVec(-21.741F, 17.2659F, 36.66F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_right_2", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.2917F, KeyframeAnimations.posVec(3.87F, 16.5F, -2.63F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.625F, KeyframeAnimations.posVec(16.0F, 11.0F, -4.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_right_2", new AnimationChannel(AnimationChannel.Targets.SCALE,
    new Keyframe(0.3333F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.4167F, KeyframeAnimations.scaleVec(1.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("eye_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.2917F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 32.5F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 32.5F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("eye_left", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("eye_left", new AnimationChannel(AnimationChannel.Targets.SCALE,
    new Keyframe(0.3333F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.4583F, KeyframeAnimations.scaleVec(1.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("eye_right", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.2917F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -32.5F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -32.5F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("eye_right", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("eye_right", new AnimationChannel(AnimationChannel.Targets.SCALE,
    new Keyframe(0.3333F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.4583F, KeyframeAnimations.scaleVec(1.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .build();

    public static final AnimationDefinition REVEAL = AnimationDefinition.Builder.withLength(1.5F)
    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0417F, KeyframeAnimations.degreeVec(0.0F, 7.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.2917F, KeyframeAnimations.degreeVec(-15.0F, 7.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.6667F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -7.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, 7.0F, 4.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.6667F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("arm_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -32.5F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.2917F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -72.5F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.4167F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -72.5F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.6667F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("arm_left", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(-18.0F, 5.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.2917F, KeyframeAnimations.posVec(-16.0F, 13.0F, -3.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.4167F, KeyframeAnimations.posVec(-11.0F, 22.0F, -1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.6667F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("arm_left", new AnimationChannel(AnimationChannel.Targets.SCALE,
    new Keyframe(0.1667F, KeyframeAnimations.scaleVec(1.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.4583F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("arm_right", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 32.5F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.2083F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 72.5F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.3333F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 72.5F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.6667F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("arm_right", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(18.0F, 5.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.2083F, KeyframeAnimations.posVec(16.0F, 13.0F, -3.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.3333F, KeyframeAnimations.posVec(11.0F, 22.0F, -1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.6667F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("arm_right", new AnimationChannel(AnimationChannel.Targets.SCALE,
    new Keyframe(0.1667F, KeyframeAnimations.scaleVec(1.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.4583F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_left_1", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.2917F, KeyframeAnimations.degreeVec(-29.2932F, -14.5348F, -29.9118F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5F, KeyframeAnimations.degreeVec(-9.8786F, -8.1094F, -6.3612F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.6667F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_left_1", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.2917F, KeyframeAnimations.posVec(-13.0F, 13.7F, 7.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5F, KeyframeAnimations.posVec(3.0F, 5.89F, 1.29F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.6667F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_left_1", new AnimationChannel(AnimationChannel.Targets.SCALE,
    new Keyframe(0.1667F, KeyframeAnimations.scaleVec(1.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.3333F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_right_1", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.2917F, KeyframeAnimations.degreeVec(-29.2932F, 14.5348F, 29.9118F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5F, KeyframeAnimations.degreeVec(-9.8786F, 8.1094F, 6.3612F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.6667F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_right_1", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.2917F, KeyframeAnimations.posVec(13.0F, 13.7F, 7.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5F, KeyframeAnimations.posVec(-3.0F, 5.89F, 1.29F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.6667F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_right_1", new AnimationChannel(AnimationChannel.Targets.SCALE,
    new Keyframe(0.1667F, KeyframeAnimations.scaleVec(1.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.3333F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_left_3", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.2917F, KeyframeAnimations.degreeVec(-24.8231F, -3.3115F, -7.2425F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5417F, KeyframeAnimations.degreeVec(2.68F, -3.31F, -7.24F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.6667F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_left_3", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.2917F, KeyframeAnimations.posVec(-17.0F, 11.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5417F, KeyframeAnimations.posVec(0.0F, 6.0F, 2.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.6667F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_left_3", new AnimationChannel(AnimationChannel.Targets.SCALE,
    new Keyframe(0.1667F, KeyframeAnimations.scaleVec(1.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.3333F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_right_3", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.2917F, KeyframeAnimations.degreeVec(-24.8231F, 3.3115F, 7.2425F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5417F, KeyframeAnimations.degreeVec(2.68F, 3.31F, 7.24F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.6667F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_right_3", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.2917F, KeyframeAnimations.posVec(17.0F, 11.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5417F, KeyframeAnimations.posVec(0.0F, 6.0F, 2.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.6667F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_right_3", new AnimationChannel(AnimationChannel.Targets.SCALE,
    new Keyframe(0.1667F, KeyframeAnimations.scaleVec(1.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.3333F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_left_2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.2917F, KeyframeAnimations.degreeVec(-25.2577F, -11.2531F, -22.471F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5F, KeyframeAnimations.degreeVec(-3.275F, -10.9964F, 5.566F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.6667F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_left_2", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.2917F, KeyframeAnimations.posVec(-14.0F, 13.0F, 4.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5F, KeyframeAnimations.posVec(-3.0F, 5.0F, 2.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.6667F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_left_2", new AnimationChannel(AnimationChannel.Targets.SCALE,
    new Keyframe(0.1667F, KeyframeAnimations.scaleVec(1.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_right_2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.2917F, KeyframeAnimations.degreeVec(-25.2577F, 11.2531F, 22.471F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5F, KeyframeAnimations.degreeVec(-3.275F, 10.9964F, -5.566F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.6667F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_right_2", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.2917F, KeyframeAnimations.posVec(14.0F, 13.0F, 4.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5F, KeyframeAnimations.posVec(3.0F, 5.0F, 2.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.6667F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_right_2", new AnimationChannel(AnimationChannel.Targets.SCALE,
    new Keyframe(0.1667F, KeyframeAnimations.scaleVec(1.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("eye_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 32.5F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.2917F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("eye_left", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("eye_left", new AnimationChannel(AnimationChannel.Targets.SCALE,
    new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.1667F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("eye_right", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -32.5F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.2917F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("eye_right", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("eye_right", new AnimationChannel(AnimationChannel.Targets.SCALE,
    new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.1667F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .build();
    public static final AnimationDefinition DEATH = AnimationDefinition.Builder.withLength(2.5417F)
    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.375F, KeyframeAnimations.degreeVec(-72.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.75F, KeyframeAnimations.degreeVec(-175.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9583F, KeyframeAnimations.degreeVec(-175.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.2083F, KeyframeAnimations.degreeVec(-175.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.6667F, KeyframeAnimations.degreeVec(-165.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.8333F, KeyframeAnimations.degreeVec(-175.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.degreeVec(-175.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.375F, KeyframeAnimations.posVec(0.0F, 17.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, -17.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9583F, KeyframeAnimations.posVec(0.0F, -17.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.2083F, KeyframeAnimations.posVec(0.0F, -17.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("arm_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.2083F, KeyframeAnimations.degreeVec(0.0F, -25.0F, -44.12F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.375F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -77.5F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5833F, KeyframeAnimations.degreeVec(0.0F, -25.0F, -157.5F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.75F, KeyframeAnimations.degreeVec(-14.974F, -20.2544F, -119.8106F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9583F, KeyframeAnimations.degreeVec(-14.974F, -20.2544F, -119.8106F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.1667F, KeyframeAnimations.degreeVec(-19.3252F, -6.8587F, -104.0221F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.375F, KeyframeAnimations.degreeVec(-22.4495F, -18.6411F, -91.1086F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.625F, KeyframeAnimations.degreeVec(-23.3014F, -9.3055F, -88.0753F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.8333F, KeyframeAnimations.degreeVec(-14.974F, -20.2544F, -119.8106F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.degreeVec(-14.974F, -20.2544F, -119.8106F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("arm_left", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.2083F, KeyframeAnimations.posVec(-4.4F, 25.06F, 3.43F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.375F, KeyframeAnimations.posVec(-13.0F, 37.0F, -1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5833F, KeyframeAnimations.posVec(-25.0F, 12.0F, 3.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.75F, KeyframeAnimations.posVec(-26.0F, -5.0F, 3.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9583F, KeyframeAnimations.posVec(-26.0F, -5.0F, 3.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.625F, KeyframeAnimations.posVec(-26.0F, -2.0F, 3.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.8333F, KeyframeAnimations.posVec(-26.0F, -5.0F, 3.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.posVec(-26.0F, -5.0F, 3.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("arm_right", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.2083F, KeyframeAnimations.degreeVec(0.0F, 25.0F, 44.12F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.375F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 77.5F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5833F, KeyframeAnimations.degreeVec(0.0F, 25.0F, 157.5F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.75F, KeyframeAnimations.degreeVec(-14.974F, 20.2544F, 119.8106F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9583F, KeyframeAnimations.degreeVec(-14.974F, 20.2544F, 119.8106F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.4167F, KeyframeAnimations.degreeVec(-32.47F, 20.25F, 119.81F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.7917F, KeyframeAnimations.degreeVec(-7.47F, 20.25F, 119.81F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.degreeVec(-14.974F, 20.2544F, 119.8106F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("arm_right", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.2083F, KeyframeAnimations.posVec(4.4F, 25.06F, 3.43F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.375F, KeyframeAnimations.posVec(13.0F, 37.0F, -1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5833F, KeyframeAnimations.posVec(25.0F, 12.0F, 3.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.75F, KeyframeAnimations.posVec(26.0F, -5.0F, 3.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9583F, KeyframeAnimations.posVec(26.0F, -5.0F, 3.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.posVec(26.0F, -5.0F, 3.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_left_1", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.375F, KeyframeAnimations.degreeVec(-107.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5417F, KeyframeAnimations.degreeVec(-155.9441F, 12.5236F, -24.2476F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.75F, KeyframeAnimations.degreeVec(-177.6486F, -17.4846F, -0.7523F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9583F, KeyframeAnimations.degreeVec(-177.6509F, -19.9825F, -0.8548F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.4167F, KeyframeAnimations.degreeVec(-177.7187F, -20.4465F, 8.1259F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.6667F, KeyframeAnimations.degreeVec(-177.71F, -19.472F, -13.3399F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.8333F, KeyframeAnimations.degreeVec(-178.8053F, -22.0244F, 59.1283F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.degreeVec(-178.8053F, -22.0244F, 59.1283F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_left_1", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.375F, KeyframeAnimations.posVec(-1.0F, 32.0F, -8.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5417F, KeyframeAnimations.posVec(-3.07F, 27.86F, -3.66F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.75F, KeyframeAnimations.posVec(-1.0F, 8.0F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9583F, KeyframeAnimations.posVec(-1.0F, 8.0F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.4167F, KeyframeAnimations.posVec(-1.0F, 6.36F, 9.32F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.6667F, KeyframeAnimations.posVec(-1.0F, 10.0F, 9.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.8333F, KeyframeAnimations.posVec(-1.0F, 1.7F, 9.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.posVec(-1.0F, 1.7F, 9.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_right_1", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.375F, KeyframeAnimations.degreeVec(-107.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5417F, KeyframeAnimations.degreeVec(-155.9441F, -12.5236F, 24.2476F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.75F, KeyframeAnimations.degreeVec(-177.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9583F, KeyframeAnimations.degreeVec(-177.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.4167F, KeyframeAnimations.degreeVec(-177.7187F, 20.4465F, -8.1259F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.6667F, KeyframeAnimations.degreeVec(-177.71F, 19.472F, 13.3399F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.8333F, KeyframeAnimations.degreeVec(-178.8053F, 22.0244F, -59.1283F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.degreeVec(-178.8053F, 22.0244F, -59.1283F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_right_1", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.375F, KeyframeAnimations.posVec(1.0F, 32.0F, -8.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5417F, KeyframeAnimations.posVec(3.07F, 27.86F, -3.66F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.75F, KeyframeAnimations.posVec(1.0F, 8.0F, 8.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9583F, KeyframeAnimations.posVec(1.0F, 8.0F, 8.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.4167F, KeyframeAnimations.posVec(1.0F, 6.36F, 9.32F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.6667F, KeyframeAnimations.posVec(1.0F, 10.0F, 9.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.8333F, KeyframeAnimations.posVec(1.0F, 1.7F, 9.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.posVec(1.0F, 1.7F, 9.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_left_3", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.375F, KeyframeAnimations.degreeVec(-56.037F, -12.7512F, -8.8513F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5417F, KeyframeAnimations.degreeVec(-136.923F, 4.4054F, 11.2216F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.75F, KeyframeAnimations.degreeVec(-174.0472F, 32.9257F, -6.6255F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9583F, KeyframeAnimations.degreeVec(-174.0472F, 32.9257F, -6.6255F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.4167F, KeyframeAnimations.degreeVec(-181.4409F, 34.1404F, -37.4836F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.6667F, KeyframeAnimations.degreeVec(-162.0345F, 33.905F, -19.4397F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.8333F, KeyframeAnimations.degreeVec(-171.2579F, 17.8405F, 37.5772F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.degreeVec(-171.2579F, 17.8405F, 37.5772F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_left_3", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.375F, KeyframeAnimations.posVec(0.0F, 21.6F, -12.2F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5417F, KeyframeAnimations.posVec(0.0F, 16.6F, -17.2F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.75F, KeyframeAnimations.posVec(-2.0F, 7.6F, -14.2F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9583F, KeyframeAnimations.posVec(-2.0F, 7.6F, -14.2F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.4167F, KeyframeAnimations.posVec(-3.0F, 8.6F, -14.2F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.6667F, KeyframeAnimations.posVec(-3.0F, 8.6F, -16.2F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.8333F, KeyframeAnimations.posVec(2.4F, 2.6F, -14.5F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.posVec(2.4F, 2.6F, -14.5F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_right_3", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.375F, KeyframeAnimations.degreeVec(-56.037F, 12.7512F, 8.8513F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5417F, KeyframeAnimations.degreeVec(-136.923F, -4.4054F, -11.2216F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.75F, KeyframeAnimations.degreeVec(-174.0472F, -32.9257F, 6.6255F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9583F, KeyframeAnimations.degreeVec(-174.0472F, -32.9257F, 6.6255F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.4167F, KeyframeAnimations.degreeVec(-181.4409F, -34.1404F, 37.4836F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.6667F, KeyframeAnimations.degreeVec(-162.0345F, -33.905F, 19.4397F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.8333F, KeyframeAnimations.degreeVec(-171.2579F, -17.8405F, -37.5772F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.degreeVec(-171.2579F, -17.8405F, -37.5772F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_right_3", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.375F, KeyframeAnimations.posVec(0.0F, 21.6F, -12.2F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5417F, KeyframeAnimations.posVec(0.0F, 16.6F, -17.2F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.75F, KeyframeAnimations.posVec(2.0F, 7.6F, -14.2F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9583F, KeyframeAnimations.posVec(2.0F, 7.6F, -14.2F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.4167F, KeyframeAnimations.posVec(3.0F, 8.6F, -14.2F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.6667F, KeyframeAnimations.posVec(3.0F, 8.6F, -16.2F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.8333F, KeyframeAnimations.posVec(-2.4F, 2.6F, -14.5F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.posVec(-2.4F, 2.6F, -14.5F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_left_2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.375F, KeyframeAnimations.degreeVec(-77.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5417F, KeyframeAnimations.degreeVec(-135.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.75F, KeyframeAnimations.degreeVec(-183.814F, 11.9128F, -17.8981F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9583F, KeyframeAnimations.degreeVec(-183.814F, 11.9128F, -17.8981F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.4167F, KeyframeAnimations.degreeVec(-178.8905F, 12.4476F, 5.1186F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.6667F, KeyframeAnimations.degreeVec(-172.849F, 11.3126F, -25.5312F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.8333F, KeyframeAnimations.degreeVec(-166.9212F, -2.7291F, 43.4482F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.degreeVec(-166.9212F, -2.7291F, 43.4482F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_left_2", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.375F, KeyframeAnimations.posVec(0.0F, 25.0F, -12.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5417F, KeyframeAnimations.posVec(0.0F, 22.0F, -11.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.75F, KeyframeAnimations.posVec(-2.0F, 9.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9583F, KeyframeAnimations.posVec(-2.0F, 9.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.4167F, KeyframeAnimations.posVec(-2.0F, 6.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.6667F, KeyframeAnimations.posVec(-2.0F, 11.0F, -3.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.8333F, KeyframeAnimations.posVec(-2.0F, 3.0F, -3.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.posVec(-2.0F, 3.0F, -3.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_right_2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.375F, KeyframeAnimations.degreeVec(-77.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5417F, KeyframeAnimations.degreeVec(-135.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.75F, KeyframeAnimations.degreeVec(-183.814F, -11.9128F, 17.8981F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9583F, KeyframeAnimations.degreeVec(-183.814F, -11.9128F, 17.8981F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.4167F, KeyframeAnimations.degreeVec(-178.8905F, -12.4476F, -5.1186F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.6667F, KeyframeAnimations.degreeVec(-172.849F, -11.3126F, 25.5312F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.8333F, KeyframeAnimations.degreeVec(-166.9212F, 2.7291F, -43.4482F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.degreeVec(-166.9212F, 2.7291F, -43.4482F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("leg_right_2", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.375F, KeyframeAnimations.posVec(0.0F, 25.0F, -12.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5417F, KeyframeAnimations.posVec(0.0F, 22.0F, -11.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.75F, KeyframeAnimations.posVec(2.0F, 9.0F, -4.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9583F, KeyframeAnimations.posVec(2.0F, 9.0F, -4.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.4167F, KeyframeAnimations.posVec(2.0F, 6.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.6667F, KeyframeAnimations.posVec(2.0F, 11.0F, -3.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.8333F, KeyframeAnimations.posVec(2.0F, 3.0F, -3.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.posVec(2.0F, 3.0F, -3.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .build();
}