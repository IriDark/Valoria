package com.idark.valoria.registries.level.configurations;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.levelgen.feature.configurations.*;

import java.util.*;

public class GradientOreConfiguration implements FeatureConfiguration{
    public final int radius;
    public final List<BlockState> gradientBlocks;
    public final BlockState targetBlock;

    public GradientOreConfiguration(int radius, List<BlockState> gradientBlocks, BlockState targetBlock) {
        this.radius = radius;
        this.gradientBlocks = gradientBlocks;
        this.targetBlock = targetBlock;
    }

    public static final Codec<GradientOreConfiguration> CODEC = RecordCodecBuilder.create(instance -> instance.group(
    Codec.INT.fieldOf("radius").forGetter(config -> config.radius),
    BlockState.CODEC.listOf().fieldOf("gradient_blocks").forGetter(config -> config.gradientBlocks),
    BlockState.CODEC.fieldOf("target_block").forGetter(config -> config.targetBlock)
    ).apply(instance, GradientOreConfiguration::new));
}