package com.idark.valoria.registries.level;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementFilter;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.minecraftforge.registries.ForgeRegistries;

public class BiomeTagFilter extends PlacementFilter{
    public static final Codec<BiomeTagFilter> CODEC = RecordCodecBuilder.create((builder) ->
            builder.group(
                    TagKey.codec(ForgeRegistries.BIOMES.getRegistryKey()).fieldOf("tag").forGetter((instance) -> instance.biomeTag)
            ).apply(builder, BiomeTagFilter::new));
    private final TagKey<Biome> biomeTag;

    private BiomeTagFilter(TagKey<Biome> biomeTag){
        this.biomeTag = biomeTag;
    }

    public static BiomeTagFilter biomeIsInTag(TagKey<Biome> biomeTag){
        return new BiomeTagFilter(biomeTag);
    }

    @Override
    protected boolean shouldPlace(PlacementContext context, RandomSource random, BlockPos pos){
        Holder<Biome> biome = context.getLevel().getBiome(pos);
        return biome.is(biomeTag);
    }

    @Override
    public PlacementModifierType<?> type(){
        return LevelGen.BIOME_TAG.get();
    }
}
