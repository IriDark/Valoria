package com.idark.valoria.core.datagen;

import net.minecraft.advancements.*;
import net.minecraft.core.*;
import net.minecraftforge.common.data.*;

import java.util.function.*;

public class ModAdvancements implements ForgeAdvancementProvider.AdvancementGenerator {

    @Override
    public void generate(HolderLookup.Provider registries, Consumer<Advancement> saver, ExistingFileHelper existingFileHelper) {
        //todo
//        Advancement root = Advancement.Builder.advancement()
//                .display(new ItemStack(Blocks.DIRT),
//                        Component.translatable("title"),
//                        Component.translatable("description"),
//                        new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
//                        FrameType.TASK, true, true, false
//                )
//                .addCriterion("has_dirt", InventoryChangeTrigger.TriggerInstance.hasItems(Blocks.DIRT))
//                .save(saver, new ResourceLocation(Valoria.ID, "valoria_root"), existingFileHelper);
    }
}
