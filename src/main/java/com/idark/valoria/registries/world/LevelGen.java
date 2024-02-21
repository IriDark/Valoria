package com.idark.valoria.registries.world;

import com.idark.valoria.Valoria;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class LevelGen {
    public static ResourceKey<ConfiguredFeature<?, ?>> SHADEWOOD_TREE = LevelGen.registerKey("shadewood_tree");
    public static ResourceKey<ConfiguredFeature<?, ?>> FANCY_SHADEWOOD_TREE = LevelGen.registerKey("fancy_shadewood_tree");

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(Valoria.MOD_ID, name));
    }
}