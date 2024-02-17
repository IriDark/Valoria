package com.idark.valoria.world;

import com.idark.valoria.Valoria;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class WorldGen {
    public static ResourceKey<ConfiguredFeature<?, ?>> SHADEWOOD_TREE = WorldGen.registerKey("shadewood_tree");
    public static ResourceKey<ConfiguredFeature<?, ?>> FANCY_SHADEWOOD_TREE = WorldGen.registerKey("fancy_shadewood_tree");

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(Valoria.MOD_ID, name));
    }
}