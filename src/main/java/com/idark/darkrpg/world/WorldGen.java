package com.idark.darkrpg.world;

import com.idark.darkrpg.DarkRPG;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class WorldGen {
    public static ResourceKey<ConfiguredFeature<?, ?>> SHADEWOOD_TREE = WorldGen.registerKey("shadewood_tree");

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(DarkRPG.MOD_ID, name));
    }
}