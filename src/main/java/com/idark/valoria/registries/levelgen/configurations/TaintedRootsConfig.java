package com.idark.valoria.registries.levelgen.configurations;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import net.minecraft.util.*;
import net.minecraft.world.level.levelgen.feature.configurations.*;

public record TaintedRootsConfig(int spreadWidth, int spreadHeight) implements FeatureConfiguration{

    public static final Codec<TaintedRootsConfig> CODEC = RecordCodecBuilder.create((p_191375_) -> p_191375_.group(ExtraCodecs.POSITIVE_INT.fieldOf("spread_width").forGetter(TaintedRootsConfig::spreadWidth), ExtraCodecs.POSITIVE_INT.fieldOf("spread_height").forGetter(TaintedRootsConfig::spreadHeight)).apply(p_191375_, TaintedRootsConfig::new));

    public TaintedRootsConfig(int spreadWidth, int spreadHeight){
        this.spreadWidth = spreadWidth;
        this.spreadHeight = spreadHeight;
    }

    public int spreadWidth(){
        return this.spreadWidth;
    }

    public int spreadHeight(){
        return this.spreadHeight;
    }
}