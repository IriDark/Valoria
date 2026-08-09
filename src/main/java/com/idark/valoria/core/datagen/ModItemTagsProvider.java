package com.idark.valoria.core.datagen;

import com.idark.valoria.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.item.types.consumables.*;
import net.minecraft.core.*;
import net.minecraft.data.*;
import net.minecraft.data.tags.*;
import net.minecraft.tags.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.*;
import net.minecraftforge.common.data.*;
import net.minecraftforge.registries.*;
import org.jetbrains.annotations.*;

import java.util.concurrent.*;

public class ModItemTagsProvider extends ItemTagsProvider{

    public ModItemTagsProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider, CompletableFuture<TagLookup<Block>> pBlockTags, @Nullable ExistingFileHelper existingFileHelper){
        super(pOutput, pLookupProvider, pBlockTags, Valoria.ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider){
        copy(BlockTags.SLABS, ItemTags.SLABS);
        copy(BlockTags.STAIRS, ItemTags.STAIRS);
        copy(BlockTags.WALLS, ItemTags.WALLS);
        copy(BlockTags.FENCES, ItemTags.FENCES);
        copy(BlockTags.FENCE_GATES, ItemTags.FENCE_GATES);
        copy(BlockTags.DOORS, ItemTags.DOORS);
        copy(BlockTags.TRAPDOORS, ItemTags.TRAPDOORS);
        copy(BlockTags.BUTTONS, ItemTags.BUTTONS);
        copy(BlockTags.LOGS, ItemTags.LOGS);
        copy(BlockTags.PLANKS, ItemTags.PLANKS);
        copy(BlockTags.LEAVES, ItemTags.LEAVES);
        copy(BlockTags.SAPLINGS, ItemTags.SAPLINGS);

        tag(ItemTags.MUSIC_DISCS)
        .add(ItemsRegistry.necromancerMusicDisc.get());

        for(Item entry : ForgeRegistries.ITEMS){
            if(entry instanceof ValoriaFood) {
                tag(TagsRegistry.ROT_IMMUNE).add(entry);
            }
        }
    }
}
