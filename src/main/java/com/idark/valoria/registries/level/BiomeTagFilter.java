package com.idark.valoria.registries.level;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import net.minecraft.core.*;
import net.minecraft.tags.*;
import net.minecraft.util.*;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.registries.*;

public class BiomeTagFilter extends PlacementFilter {
    public static final Codec<BiomeTagFilter> CODEC = RecordCodecBuilder.create((builder) ->
            builder.group(
                    TagKey.codec(ForgeRegistries.BIOMES.getRegistryKey()).fieldOf("tag").forGetter((instance) -> instance.biomeTag)
            ).apply(builder, BiomeTagFilter::new));
    private final TagKey<Biome> biomeTag;

    private BiomeTagFilter(TagKey<Biome> biomeTag) {
        this.biomeTag = biomeTag;
    }

    public static BiomeTagFilter biomeIsInTag(TagKey<Biome> biomeTag) {
        return new BiomeTagFilter(biomeTag);
    }

    @Override
    protected boolean shouldPlace(PlacementContext context, RandomSource random, BlockPos pos) {
        Holder<Biome> biome = context.getLevel().getBiome(pos);
        return biome.is(biomeTag);
    }

    @Override
    public PlacementModifierType<?> type() {
        return LevelGen.BIOME_TAG.get();
    }
}
