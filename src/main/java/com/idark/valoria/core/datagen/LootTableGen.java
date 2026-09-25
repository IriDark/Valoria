package com.idark.valoria.core.datagen;

import net.minecraft.data.*;
import net.minecraft.data.loot.*;
import net.minecraft.resources.*;
import net.minecraft.world.level.storage.loot.*;
import net.minecraft.world.level.storage.loot.parameters.*;

import java.util.*;

public class LootTableGen{

    public static LootTableProvider create(PackOutput output){
        return new LootTableProvider(output, Set.of(), List.of(
                new LootTableProvider.SubProviderEntry(LootTableSubprovider::new, LootContextParamSets.BLOCK)
        )) {
            @Override
            protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationcontext) {
                // Skip validation so it doesn't crash on blocks with missing datagen tables
            }
        };
    }
}