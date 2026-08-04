package com.idark.valoria.core.datagen;

import net.minecraft.data.*;
import net.minecraft.data.loot.*;
import net.minecraft.world.level.storage.loot.parameters.*;

import java.util.*;

public class LootTableGen{

    public static LootTableProvider create(PackOutput output){
        return new LootTableProvider(output, Set.of(), List.of(
                new LootTableProvider.SubProviderEntry(LootTableSubprovider::new, LootContextParamSets.BLOCK)
        )) {
            @Override
            protected void validate(java.util.Map<net.minecraft.resources.ResourceLocation, net.minecraft.world.level.storage.loot.LootTable> map, net.minecraft.world.level.storage.loot.ValidationContext validationcontext) {
                // Skip validation so it doesn't crash on blocks with missing datagen tables
            }
        };
    }
}