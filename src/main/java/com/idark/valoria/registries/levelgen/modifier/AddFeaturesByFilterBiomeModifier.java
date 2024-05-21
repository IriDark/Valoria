package com.idark.valoria.registries.levelgen.modifier;

import com.idark.valoria.registries.levelgen.*;
import com.mojang.serialization.*;
import net.minecraft.core.*;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.common.world.*;

import java.util.*;

public record AddFeaturesByFilterBiomeModifier(HolderSet<Biome> allowedBiomes, Optional<HolderSet<Biome>> deniedBiomes, Optional<Float> minimumTemperature, Optional<Float> maximumTemperature, HolderSet<PlacedFeature> features, GenerationStep.Decoration step) implements BiomeModifier{

    @Override
    public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder){
        if(phase == Phase.ADD && this.allowedBiomes.contains(biome)){
            if(deniedBiomes.isPresent() && this.deniedBiomes.get().contains(biome)){
                return;
            }

            if(minimumTemperature.isPresent() && biome.get().getBaseTemperature() < minimumTemperature.get()){
                return;
            }

            if(maximumTemperature.isPresent() && biome.get().getBaseTemperature() > maximumTemperature.get()){
                return;
            }
            BiomeGenerationSettingsBuilder generationSettings = builder.getGenerationSettings();
            this.features.forEach(holder -> generationSettings.addFeature(this.step, holder));
        }
    }

    @Override
    public Codec<? extends BiomeModifier> codec(){
        return LevelGen.ADD_FEATURES_BY_FILTER.get();
    }
}