package com.idark.valoria.client.model.animations;

import net.minecraft.client.animation.*;

public class CrystalSpikesAnimation{
    public static final AnimationDefinition APPEAR = AnimationDefinition.Builder.withLength(0.5F)
    .addAnimation("bone", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.125F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.4583F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("bone", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -29.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.125F, KeyframeAnimations.posVec(0.0F, 1.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.4583F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("bone", new AnimationChannel(AnimationChannel.Targets.SCALE,
    new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.125F, KeyframeAnimations.scaleVec(1.0F, 1.7F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.375F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("bone2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.1667F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.4583F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("bone2", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -29.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.1667F, KeyframeAnimations.posVec(0.0F, 1.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.4583F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("bone2", new AnimationChannel(AnimationChannel.Targets.SCALE,
    new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.2083F, KeyframeAnimations.scaleVec(1.0F, 1.7F, 1.2F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.375F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("bone4", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.2083F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("bone4", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -29.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.2083F, KeyframeAnimations.posVec(0.0F, 1.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("bone4", new AnimationChannel(AnimationChannel.Targets.SCALE,
    new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.125F, KeyframeAnimations.scaleVec(1.0F, 1.3F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("bone3", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.125F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.3333F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("bone3", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -29.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.125F, KeyframeAnimations.posVec(0.0F, 1.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.3333F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("bone3", new AnimationChannel(AnimationChannel.Targets.SCALE,
    new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.125F, KeyframeAnimations.scaleVec(1.0F, 1.2F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.25F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .build();

    public static final AnimationDefinition DISAPPEAR = AnimationDefinition.Builder.withLength(0.75F)
    .addAnimation("bone", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0833F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.4167F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("bone", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0833F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.4167F, KeyframeAnimations.posVec(0.0F, 1.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, -29.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("bone", new AnimationChannel(AnimationChannel.Targets.SCALE,
    new Keyframe(0.1667F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.4167F, KeyframeAnimations.scaleVec(1.0F, 1.7F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("bone2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0833F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.375F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("bone2", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0833F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.375F, KeyframeAnimations.posVec(0.0F, 1.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, -29.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("bone2", new AnimationChannel(AnimationChannel.Targets.SCALE,
    new Keyframe(0.1667F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.3333F, KeyframeAnimations.scaleVec(1.0F, 1.7F, 1.2F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("bone4", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.3333F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("bone4", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.3333F, KeyframeAnimations.posVec(0.0F, 1.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, -29.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("bone4", new AnimationChannel(AnimationChannel.Targets.SCALE,
    new Keyframe(0.0417F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.4167F, KeyframeAnimations.scaleVec(1.0F, 1.3F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("bone3", new AnimationChannel(AnimationChannel.Targets.ROTATION,
    new Keyframe(0.2083F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.4167F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("bone3", new AnimationChannel(AnimationChannel.Targets.POSITION,
    new Keyframe(0.2083F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.4167F, KeyframeAnimations.posVec(0.0F, 1.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, -29.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .addAnimation("bone3", new AnimationChannel(AnimationChannel.Targets.SCALE,
    new Keyframe(0.2917F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.4167F, KeyframeAnimations.scaleVec(1.0F, 1.2F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
    new Keyframe(0.5F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
    ))
    .build();
}
