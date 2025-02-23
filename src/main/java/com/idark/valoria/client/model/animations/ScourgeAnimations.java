package com.idark.valoria.client.model.animations;

import net.minecraft.client.animation.*;

public class ScourgeAnimations{
    public static final AnimationDefinition IDLE = AnimationDefinition.Builder.withLength(3.0F).looping()
    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.4167F, KeyframeAnimations.degreeVec(40.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.25F, KeyframeAnimations.degreeVec(24.0995F, -11.9795F, -3.6599F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.4167F, KeyframeAnimations.posVec(0.0F, -1.9F, -4.1F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.4167F, KeyframeAnimations.degreeVec(10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.4167F, KeyframeAnimations.posVec(0.0F, 0.0F, -1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.SCALE,
    new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.75F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.2F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.6667F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.4583F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.2F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.4167F, KeyframeAnimations.degreeVec(-2.4905F, 0.2178F, 4.9953F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.4167F, KeyframeAnimations.posVec(0.0F, 0.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.4167F, KeyframeAnimations.degreeVec(-10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.4167F, KeyframeAnimations.posVec(0.0F, 0.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .build();

    public static final AnimationDefinition WALK = AnimationDefinition.Builder.withLength(3.0F).looping()
    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(40.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.875F, KeyframeAnimations.degreeVec(40.9808F, -11.4356F, -9.7724F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.2917F, KeyframeAnimations.degreeVec(40.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.125F, KeyframeAnimations.degreeVec(40.9808F, 11.4356F, 9.7724F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.5417F, KeyframeAnimations.degreeVec(40.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0F, KeyframeAnimations.degreeVec(40.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -1.9F, -4.1F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, -1.9F, -6.1F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.875F, KeyframeAnimations.posVec(1.0F, -2.8F, -7.7F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.2917F, KeyframeAnimations.posVec(0.0F, -2.3F, -4.8F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.75F, KeyframeAnimations.posVec(-0.3F, -1.9F, -6.1F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.125F, KeyframeAnimations.posVec(-1.0F, -3.1F, -7.5F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.5417F, KeyframeAnimations.posVec(0.0F, -1.9F, -5.1F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0F, KeyframeAnimations.posVec(0.0F, -1.9F, -4.1F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5F, KeyframeAnimations.degreeVec(10.6276F, -19.6835F, -3.6164F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.875F, KeyframeAnimations.degreeVec(20.63F, -19.68F, -3.62F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.2917F, KeyframeAnimations.degreeVec(10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.75F, KeyframeAnimations.degreeVec(10.6276F, 19.6835F, 3.6164F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.125F, KeyframeAnimations.degreeVec(20.63F, 19.68F, 3.62F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.5417F, KeyframeAnimations.degreeVec(10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0F, KeyframeAnimations.degreeVec(10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, -1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.875F, KeyframeAnimations.posVec(0.0F, 0.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.2917F, KeyframeAnimations.posVec(0.0F, 0.0F, -1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.125F, KeyframeAnimations.posVec(0.0F, 0.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.5417F, KeyframeAnimations.posVec(0.0F, 0.0F, -1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0F, KeyframeAnimations.posVec(0.0F, 0.0F, -1.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.SCALE,
    new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.75F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.2F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.6667F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.4583F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.2F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(-2.4905F, 0.2178F, 4.9953F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5F, KeyframeAnimations.degreeVec(-12.4145F, -1.3994F, -2.3237F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.875F, KeyframeAnimations.degreeVec(-6.5642F, -12.7913F, 1.5723F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.2917F, KeyframeAnimations.degreeVec(-2.4905F, 0.2178F, 4.9953F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.75F, KeyframeAnimations.degreeVec(-2.6992F, 22.6976F, 3.9674F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.125F, KeyframeAnimations.degreeVec(-15.2F, 22.7F, 3.97F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.5417F, KeyframeAnimations.degreeVec(-14.07F, 5.7586F, 8.515F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0F, KeyframeAnimations.degreeVec(-2.4905F, 0.2178F, 4.9953F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, -0.3F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.875F, KeyframeAnimations.posVec(0.3F, -1.0F, -6.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.2917F, KeyframeAnimations.posVec(-0.4F, 0.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.75F, KeyframeAnimations.posVec(0.0F, 0.2F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.125F, KeyframeAnimations.posVec(-0.5F, -0.6F, -2.2F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.5417F, KeyframeAnimations.posVec(-0.7F, -0.7F, -2.5F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0F, KeyframeAnimations.posVec(0.0F, 0.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -7.5F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5F, KeyframeAnimations.degreeVec(-5.9885F, -12.5912F, -5.4786F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.875F, KeyframeAnimations.degreeVec(-3.3214F, -13.7962F, -4.5249F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.2917F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -7.5F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.75F, KeyframeAnimations.degreeVec(0.0F, 15.0F, -7.5F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.125F, KeyframeAnimations.degreeVec(-22.5F, 15.0F, -7.5F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.5417F, KeyframeAnimations.degreeVec(2.583F, -2.4841F, -6.7477F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -7.5F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.4F, 0.1F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5F, KeyframeAnimations.posVec(0.4F, 0.0F, -1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.875F, KeyframeAnimations.posVec(0.7F, -0.9F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.2917F, KeyframeAnimations.posVec(0.7F, 0.1F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.75F, KeyframeAnimations.posVec(-0.07F, -0.77F, -5.36F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.125F, KeyframeAnimations.posVec(-0.07F, -0.77F, -6.36F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.5417F, KeyframeAnimations.posVec(0.73F, -0.27F, -2.36F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0F, KeyframeAnimations.posVec(0.4F, 0.1F, -2.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5F, KeyframeAnimations.degreeVec(-22.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.2917F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.875F, KeyframeAnimations.posVec(0.0F, 0.0F, -2.66F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.2917F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.2917F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.75F, KeyframeAnimations.degreeVec(-22.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.5417F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.2917F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.75F, KeyframeAnimations.posVec(0.0F, 0.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.125F, KeyframeAnimations.posVec(0.0F, 0.0F, -2.66F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.5417F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .build();

    public static final AnimationDefinition ATTACK_MELEE = AnimationDefinition.Builder.withLength(2.0F)
    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.375F, KeyframeAnimations.degreeVec(-15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5833F, KeyframeAnimations.degreeVec(-7.3242F, -1.6189F, -12.3964F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.875F, KeyframeAnimations.degreeVec(-11.7713F, 2.4768F, 9.8481F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.degreeVec(-15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.125F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.2083F, KeyframeAnimations.degreeVec(25.9189F, -19.2737F, -3.3502F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.375F, KeyframeAnimations.posVec(0.0F, -0.2F, 4.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, -0.5F, 5.3F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.125F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.2083F, KeyframeAnimations.posVec(1.3F, -1.7F, -9.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.375F, KeyframeAnimations.degreeVec(-15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5833F, KeyframeAnimations.degreeVec(-15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.degreeVec(-25.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.125F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.2083F, KeyframeAnimations.degreeVec(25.9189F, -19.2737F, -3.3502F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.375F, KeyframeAnimations.posVec(0.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5833F, KeyframeAnimations.posVec(0.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.125F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.2083F, KeyframeAnimations.posVec(0.0F, 0.0F, -4.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.SCALE,
    new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.25F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.3F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5417F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.8333F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.3F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0833F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5833F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.3F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.375F, KeyframeAnimations.degreeVec(-126.3602F, 38.8447F, -24.7851F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5833F, KeyframeAnimations.degreeVec(-94.7788F, 50.9942F, 18.6447F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.875F, KeyframeAnimations.degreeVec(-126.3602F, 38.8447F, -24.7851F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.degreeVec(-126.3602F, 38.8447F, -24.7851F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.125F, KeyframeAnimations.degreeVec(-148.0184F, 7.0947F, -6.5709F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.2083F, KeyframeAnimations.degreeVec(-72.8729F, -20.1897F, -8.1726F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.4167F, KeyframeAnimations.degreeVec(-31.4946F, -15.6896F, 2.4638F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.375F, KeyframeAnimations.posVec(-2.0F, 3.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.875F, KeyframeAnimations.posVec(-2.0F, 3.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.posVec(-2.0F, 3.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.125F, KeyframeAnimations.posVec(-0.6F, 6.0F, -1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.2083F, KeyframeAnimations.posVec(2.8F, 0.9F, -13.6F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.4167F, KeyframeAnimations.posVec(0.46F, 0.24F, -7.22F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.SCALE,
    new Keyframe(1.0833F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.2083F, KeyframeAnimations.scaleVec(1.0F, 1.6F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.2917F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.375F, KeyframeAnimations.degreeVec(-126.3602F, -38.8447F, 24.7851F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5833F, KeyframeAnimations.degreeVec(-94.7788F, -50.9942F, -18.6447F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.875F, KeyframeAnimations.degreeVec(-126.3602F, -38.8447F, 24.7851F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.degreeVec(-126.3602F, -38.8447F, 24.7851F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.125F, KeyframeAnimations.degreeVec(-148.0184F, -7.0947F, 6.5709F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.2083F, KeyframeAnimations.degreeVec(-88.8577F, -6.2014F, 0.3109F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.4167F, KeyframeAnimations.degreeVec(-31.4946F, 15.6896F, -2.4638F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.375F, KeyframeAnimations.posVec(2.0F, 3.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.875F, KeyframeAnimations.posVec(2.0F, 3.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.posVec(2.0F, 3.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.125F, KeyframeAnimations.posVec(0.6F, 6.0F, -1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.2083F, KeyframeAnimations.posVec(-2.8F, 0.9F, -13.6F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.4167F, KeyframeAnimations.posVec(-0.46F, 0.24F, -7.22F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.SCALE,
    new Keyframe(1.0833F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.2083F, KeyframeAnimations.scaleVec(1.0F, 1.6F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.2917F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0417F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.25F, KeyframeAnimations.degreeVec(7.5606F, -12.4879F, -0.554F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0417F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.25F, KeyframeAnimations.posVec(-1.0F, 0.5F, -1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.SCALE,
    new Keyframe(1.0417F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.25F, KeyframeAnimations.scaleVec(1.0F, 1.1F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.625F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.375F, KeyframeAnimations.degreeVec(15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.6667F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.degreeVec(15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.2083F, KeyframeAnimations.degreeVec(37.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5833F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.375F, KeyframeAnimations.posVec(0.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.6667F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.2083F, KeyframeAnimations.posVec(0.0F, 1.0F, 3.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5833F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.SCALE,
    new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.375F, KeyframeAnimations.scaleVec(1.0F, 1.17F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.2083F, KeyframeAnimations.scaleVec(1.0F, 1.17F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5833F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .build();

    public static final AnimationDefinition ATTACK_RANGED = AnimationDefinition.Builder.withLength(1.5F)
    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.25F, KeyframeAnimations.degreeVec(-22.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.7917F, KeyframeAnimations.degreeVec(-30.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9583F, KeyframeAnimations.degreeVec(-2.4149F, -0.6469F, -14.9864F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.2917F, KeyframeAnimations.degreeVec(10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, -0.5F, 5.7F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.7917F, KeyframeAnimations.posVec(0.0F, -1.5F, 7.7F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9583F, KeyframeAnimations.posVec(0.0F, -0.8F, -4.5F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.2917F, KeyframeAnimations.posVec(0.0F, -1.1F, -6.8F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.SCALE,
    new Keyframe(0.875F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9583F, KeyframeAnimations.scaleVec(1.1F, 1.1F, 1.6F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0833F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.25F, KeyframeAnimations.degreeVec(-22.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.7917F, KeyframeAnimations.degreeVec(-30.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9583F, KeyframeAnimations.degreeVec(20.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.2917F, KeyframeAnimations.degreeVec(30.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, 0.0F, 1.7F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.7917F, KeyframeAnimations.posVec(0.0F, -0.4F, 2.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9583F, KeyframeAnimations.posVec(0.0F, -0.4F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.2917F, KeyframeAnimations.posVec(0.0F, -0.4F, -3.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.SCALE,
    new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.75F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.9F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.125F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.4167F, KeyframeAnimations.degreeVec(-147.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.7917F, KeyframeAnimations.degreeVec(-147.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.degreeVec(62.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.4167F, KeyframeAnimations.posVec(0.0F, 5.0F, 2.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.7917F, KeyframeAnimations.posVec(0.0F, 5.0F, 2.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 1.0F, -3.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.2917F, KeyframeAnimations.posVec(0.0F, 1.0F, -4.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.4167F, KeyframeAnimations.degreeVec(-147.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.7917F, KeyframeAnimations.degreeVec(-147.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.degreeVec(62.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.4167F, KeyframeAnimations.posVec(0.0F, 5.0F, 2.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.7917F, KeyframeAnimations.posVec(0.0F, 5.0F, 2.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 1.0F, -3.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.2917F, KeyframeAnimations.posVec(0.0F, 1.0F, -4.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .build();

    public static final AnimationDefinition SPAWN = AnimationDefinition.Builder.withLength(3.0F)
    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.8333F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0417F, KeyframeAnimations.degreeVec(-20.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.875F, KeyframeAnimations.degreeVec(-20.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.2917F, KeyframeAnimations.degreeVec(23.48F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.4583F, KeyframeAnimations.degreeVec(53.6164F, -12.1731F, 8.8315F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.8333F, KeyframeAnimations.posVec(0.0F, -33.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0417F, KeyframeAnimations.posVec(0.0F, -26.11F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.875F, KeyframeAnimations.posVec(0.0F, -26.11F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.125F, KeyframeAnimations.posVec(0.0F, -22.56F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.4583F, KeyframeAnimations.posVec(0.0F, -16.85F, -7.49F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.7083F, KeyframeAnimations.posVec(0.0F, -8.31F, -5.9F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.SCALE,
    new Keyframe(0.0F, KeyframeAnimations.scaleVec(0.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.LINEAR),
    new Keyframe(0.749F, KeyframeAnimations.scaleVec(0.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.LINEAR),
    new Keyframe(0.75F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.875F, KeyframeAnimations.scaleVec(1.0439F, 1.9439F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0833F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.875F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.125F, KeyframeAnimations.degreeVec(35.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.5417F, KeyframeAnimations.degreeVec(58.33F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -33.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.875F, KeyframeAnimations.posVec(0.0F, -27.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.SCALE,
    new Keyframe(0.0F, KeyframeAnimations.scaleVec(0.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.LINEAR),
    new Keyframe(1.874F, KeyframeAnimations.scaleVec(0.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.LINEAR),
    new Keyframe(1.875F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(177.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5417F, KeyframeAnimations.degreeVec(177.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.875F, KeyframeAnimations.degreeVec(164.54F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0833F, KeyframeAnimations.degreeVec(195.36F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.2083F, KeyframeAnimations.degreeVec(187.6F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.2917F, KeyframeAnimations.degreeVec(164.54F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.degreeVec(195.36F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.875F, KeyframeAnimations.degreeVec(237.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0417F, KeyframeAnimations.degreeVec(273.96F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.2083F, KeyframeAnimations.degreeVec(335.49F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.7083F, KeyframeAnimations.degreeVec(335.49F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0F, KeyframeAnimations.degreeVec(360.49F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -29.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5417F, KeyframeAnimations.posVec(0.0F, -29.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.7083F, KeyframeAnimations.posVec(0.0F, -20.54F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.875F, KeyframeAnimations.posVec(0.0F, -21.38F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.2083F, KeyframeAnimations.posVec(0.0F, -23.95F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.875F, KeyframeAnimations.posVec(0.0F, -21.38F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0417F, KeyframeAnimations.posVec(0.0F, -17.48F, -8.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.2083F, KeyframeAnimations.posVec(0.0F, -16.48F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.5417F, KeyframeAnimations.posVec(0.0F, -14.89F, -6.21F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.SCALE,
    new Keyframe(1.7917F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.875F, KeyframeAnimations.scaleVec(1.0F, 1.6F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0417F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(175.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.6667F, KeyframeAnimations.degreeVec(187.6F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.875F, KeyframeAnimations.degreeVec(164.54F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0833F, KeyframeAnimations.degreeVec(195.36F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.2917F, KeyframeAnimations.degreeVec(164.54F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5F, KeyframeAnimations.degreeVec(195.36F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.875F, KeyframeAnimations.degreeVec(175.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0417F, KeyframeAnimations.degreeVec(273.96F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.2083F, KeyframeAnimations.degreeVec(335.49F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.3333F, KeyframeAnimations.degreeVec(335.49F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.7083F, KeyframeAnimations.degreeVec(335.49F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0F, KeyframeAnimations.degreeVec(360.49F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -28.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.1667F, KeyframeAnimations.posVec(0.0F, -20.54F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.6667F, KeyframeAnimations.posVec(0.0F, -23.95F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.875F, KeyframeAnimations.posVec(0.0F, -21.38F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.875F, KeyframeAnimations.posVec(0.0F, -21.38F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0417F, KeyframeAnimations.posVec(0.0F, -17.66F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.2083F, KeyframeAnimations.posVec(0.0F, -16.48F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.5417F, KeyframeAnimations.posVec(0.0F, -14.89F, -6.21F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0F, KeyframeAnimations.posVec(0.2F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.SCALE,
    new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.1667F, KeyframeAnimations.scaleVec(1.0F, 1.8994F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.375F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.7917F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.875F, KeyframeAnimations.scaleVec(1.0F, 1.6F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0417F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(1.875F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.5F, KeyframeAnimations.degreeVec(67.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(1.875F, KeyframeAnimations.posVec(0.0F, -27.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.5F, KeyframeAnimations.posVec(-0.6F, -6.07F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.SCALE,
    new Keyframe(0.0F, KeyframeAnimations.scaleVec(0.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.LINEAR),
    new Keyframe(1.874F, KeyframeAnimations.scaleVec(0.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.LINEAR),
    new Keyframe(1.875F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(1.875F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.5F, KeyframeAnimations.degreeVec(30.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.8333F, KeyframeAnimations.degreeVec(-8.65F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(1.875F, KeyframeAnimations.posVec(0.0F, -27.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.5F, KeyframeAnimations.posVec(0.0F, -8.07F, 7.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.8333F, KeyframeAnimations.posVec(0.0F, -1.9F, 0.65F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.SCALE,
    new Keyframe(0.0F, KeyframeAnimations.scaleVec(0.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.LINEAR),
    new Keyframe(1.874F, KeyframeAnimations.scaleVec(0.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.LINEAR),
    new Keyframe(1.875F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(3.0F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .build();

    public static final AnimationDefinition DEATH = AnimationDefinition.Builder.withLength(3.0F)
    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.125F, KeyframeAnimations.degreeVec(-45.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.4583F, KeyframeAnimations.degreeVec(-15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.8333F, KeyframeAnimations.degreeVec(32.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0833F, KeyframeAnimations.degreeVec(50.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.25F, KeyframeAnimations.degreeVec(7.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.4167F, KeyframeAnimations.degreeVec(62.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.9167F, KeyframeAnimations.degreeVec(87.5806F, -17.5747F, 86.2935F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.2917F, KeyframeAnimations.degreeVec(87.5806F, -17.5747F, 86.2935F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.7083F, KeyframeAnimations.degreeVec(87.6726F, -7.5785F, 85.8662F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.125F, KeyframeAnimations.posVec(0.0F, -1.4F, 14.6F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.4583F, KeyframeAnimations.posVec(0.0F, 0.0F, 8.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.8333F, KeyframeAnimations.posVec(0.0F, 0.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0833F, KeyframeAnimations.posVec(0.0F, -3.38F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.25F, KeyframeAnimations.posVec(0.0F, -7.6F, -16.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.4167F, KeyframeAnimations.posVec(0.0F, -12.6F, -19.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5833F, KeyframeAnimations.posVec(0.0F, -9.6F, -17.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.9167F, KeyframeAnimations.posVec(5.0F, -22.6F, -19.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.2917F, KeyframeAnimations.posVec(5.0F, -22.6F, -19.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.7083F, KeyframeAnimations.posVec(5.0F, -22.6F, -20.8F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.SCALE,
    new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.0833F, KeyframeAnimations.scaleVec(1.0F, 1.7F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.1667F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.125F, KeyframeAnimations.degreeVec(-30.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.degreeVec(12.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.25F, KeyframeAnimations.degreeVec(37.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.4167F, KeyframeAnimations.degreeVec(52.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5833F, KeyframeAnimations.degreeVec(37.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.9167F, KeyframeAnimations.degreeVec(84.0561F, -0.2367F, 85.4425F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.2917F, KeyframeAnimations.degreeVec(84.0561F, -0.2367F, 85.4425F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.7083F, KeyframeAnimations.degreeVec(84.06F, -0.24F, 85.44F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.125F, KeyframeAnimations.posVec(0.0F, 0.0F, 9.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.25F, KeyframeAnimations.posVec(0.0F, -5.0F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.4167F, KeyframeAnimations.posVec(0.0F, -6.0F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5833F, KeyframeAnimations.posVec(0.0F, -5.0F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.9167F, KeyframeAnimations.posVec(5.0F, -12.0F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.2917F, KeyframeAnimations.posVec(5.0F, -12.0F, -10.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.SCALE,
    new Keyframe(0.875F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0833F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.4F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.2917F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5417F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.4F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.7917F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.0417F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.8F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.2083F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.375F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.8F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.7083F, KeyframeAnimations.scaleVec(1.5F, 1.2F, 2.5F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.125F, KeyframeAnimations.degreeVec(-75.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5F, KeyframeAnimations.degreeVec(-82.2323F, -63.8539F, 36.0731F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.7083F, KeyframeAnimations.degreeVec(-102.5537F, -63.4286F, 58.6946F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.8333F, KeyframeAnimations.degreeVec(-49.0057F, 1.2158F, 34.7287F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.degreeVec(-87.5645F, 9.24F, 7.4298F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.25F, KeyframeAnimations.degreeVec(-15.06F, 9.24F, 7.43F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.4167F, KeyframeAnimations.degreeVec(11.7941F, -20.0478F, -59.8298F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5833F, KeyframeAnimations.degreeVec(11.7941F, -20.0478F, -59.8298F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.9167F, KeyframeAnimations.degreeVec(24.3737F, -0.6846F, 59.9014F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.2917F, KeyframeAnimations.degreeVec(24.3737F, -0.6846F, 59.9014F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.7083F, KeyframeAnimations.degreeVec(23.7249F, -5.7956F, 71.3309F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.125F, KeyframeAnimations.posVec(0.0F, 1.0F, 8.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5F, KeyframeAnimations.posVec(3.0F, 2.0F, 4.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.7083F, KeyframeAnimations.posVec(3.0F, 2.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.8333F, KeyframeAnimations.posVec(-0.78F, 2.0F, -2.34F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.posVec(-0.6F, 2.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.25F, KeyframeAnimations.posVec(-0.4F, -5.0F, -13.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.4167F, KeyframeAnimations.posVec(2.6F, -10.0F, -14.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5833F, KeyframeAnimations.posVec(2.6F, -10.0F, -14.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.9167F, KeyframeAnimations.posVec(8.6F, -8.0F, -13.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.2917F, KeyframeAnimations.posVec(8.6F, -8.0F, -13.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.7083F, KeyframeAnimations.posVec(8.6F, -5.0F, -14.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.SCALE,
    new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.0833F, KeyframeAnimations.scaleVec(1.0F, 1.7F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.1667F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.125F, KeyframeAnimations.degreeVec(-75.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.875F, KeyframeAnimations.degreeVec(-73.5297F, -24.0929F, -6.8817F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.25F, KeyframeAnimations.degreeVec(-74.9999F, 0.0029F, 0.0016F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.4167F, KeyframeAnimations.degreeVec(12.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5833F, KeyframeAnimations.degreeVec(12.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.9167F, KeyframeAnimations.degreeVec(-1.1069F, -12.4517F, 95.1208F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.2917F, KeyframeAnimations.degreeVec(-1.1069F, -12.4517F, 95.1208F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.7083F, KeyframeAnimations.degreeVec(-5.3548F, -11.3099F, 115.5295F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.125F, KeyframeAnimations.posVec(0.0F, 1.0F, 8.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 2.0F, 3.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.9583F, KeyframeAnimations.posVec(0.0F, 2.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.25F, KeyframeAnimations.posVec(0.2F, -4.0F, -16.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.4167F, KeyframeAnimations.posVec(0.2F, -8.0F, -13.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5833F, KeyframeAnimations.posVec(0.2F, -8.0F, -13.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.9167F, KeyframeAnimations.posVec(-3.8F, -20.0F, -14.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.2917F, KeyframeAnimations.posVec(-3.8F, -20.0F, -14.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.7083F, KeyframeAnimations.posVec(-5.8F, -22.0F, -14.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.SCALE,
    new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.0833F, KeyframeAnimations.scaleVec(1.0F, 1.7F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.1667F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.125F, KeyframeAnimations.degreeVec(-25.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5F, KeyframeAnimations.degreeVec(30.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.625F, KeyframeAnimations.degreeVec(30.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.7083F, KeyframeAnimations.degreeVec(-2.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.degreeVec(22.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.25F, KeyframeAnimations.degreeVec(52.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.4167F, KeyframeAnimations.degreeVec(52.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5833F, KeyframeAnimations.degreeVec(52.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.9167F, KeyframeAnimations.degreeVec(45.0776F, 6.3545F, 46.8673F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.2917F, KeyframeAnimations.degreeVec(45.0776F, 6.3545F, 46.8673F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.125F, KeyframeAnimations.posVec(0.0F, 1.1F, 4.2F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 1.1F, 6.2F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.625F, KeyframeAnimations.posVec(0.0F, 1.1F, 6.2F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.7083F, KeyframeAnimations.posVec(0.0F, 0.7F, 1.36F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.1F, 1.2F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.25F, KeyframeAnimations.posVec(0.0F, -1.9F, -1.8F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.4167F, KeyframeAnimations.posVec(0.0F, -2.9F, -1.8F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5833F, KeyframeAnimations.posVec(0.0F, -1.9F, -1.8F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.9167F, KeyframeAnimations.posVec(4.0F, -1.9F, -1.8F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.2917F, KeyframeAnimations.posVec(4.0F, -1.9F, -1.8F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.7083F, KeyframeAnimations.posVec(4.0F, -1.9F, 0.2F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.SCALE,
    new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.125F, KeyframeAnimations.scaleVec(1.0F, 1.2F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5F, KeyframeAnimations.scaleVec(1.0F, 1.2F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.scaleVec(1.0F, 1.1F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.125F, KeyframeAnimations.degreeVec(22.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5F, KeyframeAnimations.degreeVec(-7.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5833F, KeyframeAnimations.degreeVec(-7.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.7083F, KeyframeAnimations.degreeVec(20.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.degreeVec(42.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.4167F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5833F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.9167F, KeyframeAnimations.degreeVec(90.0F, 5.0F, 90.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.2917F, KeyframeAnimations.degreeVec(90.0F, 5.0F, 90.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.125F, KeyframeAnimations.posVec(0.0F, 0.0F, 7.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 3.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5833F, KeyframeAnimations.posVec(0.0F, 0.0F, 3.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.7083F, KeyframeAnimations.posVec(0.0F, 0.0F, 4.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 2.0F, 2.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.25F, KeyframeAnimations.posVec(0.0F, -3.0F, -9.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.4167F, KeyframeAnimations.posVec(0.0F, -4.0F, -9.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.5833F, KeyframeAnimations.posVec(0.0F, -3.0F, -9.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.9167F, KeyframeAnimations.posVec(4.0F, -3.0F, 1.3F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.2917F, KeyframeAnimations.posVec(4.0F, -3.0F, 1.3F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(2.7083F, KeyframeAnimations.posVec(4.0F, -3.0F, 3.3F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.SCALE,
    new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.125F, KeyframeAnimations.scaleVec(1.0F, 1.1F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(1.0F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .build();
}