package com.idark.valoria.registries.level.configurations;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.levelgen.feature.configurations.*;

import java.util.*;

public class CrystalBlobConfiguration implements FeatureConfiguration{
    public static final Codec<CrystalBlobConfiguration> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        BlockState.CODEC.fieldOf("state").forGetter(config -> config.state),
        BlockState.CODEC.listOf().fieldOf("outer_decoration").forGetter(config -> config.outerDecoration),
        Codec.floatRange(0, 1).fieldOf("decoration_chance").forGetter(config -> config.chance)
    ).apply(instance, CrystalBlobConfiguration::new));

    public final List<BlockState> outerDecoration;
    public final BlockState state;
    public final float chance;

    public CrystalBlobConfiguration(BlockState state, List<BlockState> outerDecoration, float chance){
        this.state = state;
        this.outerDecoration = outerDecoration;
        this.chance = chance;
    }
}
