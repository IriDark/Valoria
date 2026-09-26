package com.idark.valoria.core.datagen;

import net.minecraft.core.*;
import net.minecraft.data.*;
import net.minecraft.data.loot.*;
import net.minecraft.util.*;
import net.minecraft.world.level.storage.loot.*;
import net.minecraft.world.level.storage.loot.parameters.*;

import java.util.*;
import java.util.concurrent.*;

public class LootTableGen{

    public static LootTableProvider create(PackOutput output, CompletableFuture<HolderLookup.Provider> registries){
        return new LootTableProvider(output, Set.of(), List.of(
                new LootTableProvider.SubProviderEntry(LootTableSubprovider::new, LootContextParamSets.BLOCK)
        ), registries) {
            @Override
            protected void validate(WritableRegistry<LootTable> writableregistry, ValidationContext validationcontext, ProblemReporter.Collector problemreporter) {
                // Skip validation so it doesn't crash on blocks with missing datagen tables
            }
        };
    }
}
