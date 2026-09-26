package com.idark.valoria.core.datagen;

import com.idark.valoria.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.level.*;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.*;
import net.minecraft.advancements.critereon.EntityPredicate.*;
import net.minecraft.core.*;
import net.minecraft.core.registries.*;
import net.minecraft.nbt.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraft.tags.*;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.common.data.*;

import java.util.function.*;

public class ModAdvancements implements AdvancementProvider.AdvancementGenerator {

    @Override
    public void generate(HolderLookup.Provider registries, Consumer<AdvancementHolder> saver, ExistingFileHelper existingFileHelper) {
        AdvancementHolder the_valoria = Advancement.Builder.advancement()
            .display(new ItemStack(ItemsRegistry.codex.get()), Component.translatable("advancements.valoria.the_valoria.title"), Component.translatable("advancements.valoria.the_valoria.description"), ResourceLocation.parse("valoria:textures/block/deep_marble.png"), AdvancementType.TASK, true, true, false)
            .addCriterion("valoria_enter_world", PlayerTrigger.TriggerInstance.tick())
            .rewards(AdvancementRewards.Builder.loot(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.parse("valoria:advancements/starter_bundle"))))
            .save(saver, Valoria.loc("valoria/the_valoria"), existingFileHelper);

        AdvancementHolder aloe_piece = Advancement.Builder.advancement()
            .parent(the_valoria)
            .display(new ItemStack(ItemsRegistry.aloePiece.get()), Component.translatable("advancements.valoria.aloe.title"), Component.translatable("advancements.valoria.aloe.description"), null, AdvancementType.TASK, true, true, false)
            .addCriterion("requirement", InventoryChangeTrigger.TriggerInstance.hasItems(ItemsRegistry.aloePiece.get()))
            .rewards(AdvancementRewards.Builder.experience(80))
            .save(saver, Valoria.loc("valoria/aloe_piece"), existingFileHelper);

        AdvancementHolder elemental_crystal = Advancement.Builder.advancement()
            .parent(the_valoria)
            .display(new ItemStack(ItemsRegistry.elementalCrystal.get()), Component.translatable("advancements.valoria.elemental_crystal.title"), Component.translatable("advancements.valoria.elemental_crystal.description"), null, AdvancementType.TASK, true, true, false)
            .addCriterion("requirement", InventoryChangeTrigger.TriggerInstance.hasItems(ItemsRegistry.elementalCrystal.get()))
            .save(saver, Valoria.loc("valoria/elemental_crystal"), existingFileHelper);

        AdvancementHolder elemental_manipulator = Advancement.Builder.advancement()
            .parent(elemental_crystal)
            .display(new ItemStack(BlockRegistry.elementalManipulator.get()), Component.translatable("advancements.valoria.elemental_manipulator.title"), Component.translatable("advancements.valoria.elemental_manipulator.description"), null, AdvancementType.TASK, true, true, false)
            .addCriterion("requirement", InventoryChangeTrigger.TriggerInstance.hasItems(BlockRegistry.elementalManipulator.get()))
            .save(saver, Valoria.loc( "valoria/elemental_manipulator"), existingFileHelper);

        AdvancementHolder aquarius_core = Advancement.Builder.advancement()
            .parent(elemental_manipulator)
            .display(new ItemStack(ItemsRegistry.aquariusCore.get()), Component.translatable("advancements.valoria.aquarius_core.title"), Component.translatable(""), null, AdvancementType.TASK, true, true, false)
            .addCriterion("requirement", InventoryChangeTrigger.TriggerInstance.hasItems(ItemsRegistry.aquariusCore.get()))
            .save(saver, Valoria.loc("valoria/aquarius_core"), existingFileHelper);

        AdvancementHolder first_help = Advancement.Builder.advancement()
            .parent(aloe_piece)
            .display(new ItemStack(ItemsRegistry.aloeBandage.get()), Component.translatable("advancements.valoria.first_help.title"), Component.translatable("advancements.valoria.first_help.description"), null, AdvancementType.TASK, true, true, false)
            .addCriterion("requirement", ConsumeItemTrigger.TriggerInstance.usedItem(ItemsRegistry.aloeBandage.get()))
            .rewards(AdvancementRewards.Builder.experience(80))
            .save(saver, Valoria.loc("valoria/first_help"), existingFileHelper);

        AdvancementHolder basic_medicine = Advancement.Builder.advancement()
            .parent(first_help)
            .display(new ItemStack(ItemsRegistry.aloeBandageUpgraded.get()), Component.translatable("advancements.valoria.basic_medicine.title"), Component.translatable("advancements.valoria.basic_medicine.description"), null, AdvancementType.GOAL, true, true, false)
            .addCriterion("bandage", InventoryChangeTrigger.TriggerInstance.hasItems(ItemsRegistry.aloeBandageUpgraded.get()))
            .rewards(AdvancementRewards.Builder.experience(80))
            .save(saver, Valoria.loc("valoria/basic_medicine"), existingFileHelper);

        AdvancementHolder harmony_heart = Advancement.Builder.advancement()
            .parent(the_valoria)
            .display(new ItemStack(ItemsRegistry.harmonyHeart.get()), Component.translatable("advancements.valoria.harmony_heart.title"), Component.translatable("advancements.valoria.harmony_heart.description"), null, AdvancementType.TASK, true, true, false)
            .addCriterion("requirement", InventoryChangeTrigger.TriggerInstance.hasItems(ItemsRegistry.harmonyHeart.get()))
            .rewards(AdvancementRewards.Builder.experience(40))
            .save(saver, Valoria.loc("valoria/harmony_heart"), existingFileHelper);

        AdvancementHolder devil_heart = Advancement.Builder.advancement()
            .parent(harmony_heart)
            .display(new ItemStack(ItemsRegistry.devilHeart.get()), Component.translatable("advancements.valoria.devil_heart.title"), Component.translatable("advancements.valoria.devil_heart.description"), null, AdvancementType.TASK, true, true, false)
            .addCriterion("requirement", InventoryChangeTrigger.TriggerInstance.hasItems(ItemsRegistry.devilHeart.get()))
            .rewards(AdvancementRewards.Builder.experience(80))
            .save(saver, Valoria.loc("valoria/devil_heart"), existingFileHelper);

        AdvancementHolder necromancer = Advancement.Builder.advancement()
            .parent(the_valoria)
            .display(new ItemStack(ItemsRegistry.necromancerGrimoire.get()), Component.translatable("advancements.valoria.necromancer.title"), Component.translatable("advancements.valoria.necromancer.description"), null, AdvancementType.CHALLENGE, true, true, false)
            .addCriterion("requirement", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(EntityTypeRegistry.NECROMANCER.get())))
            .rewards(AdvancementRewards.Builder.experience(100))
            .save(saver, Valoria.loc("valoria/necromancer"), existingFileHelper);

        AdvancementHolder dryador = Advancement.Builder.advancement()
            .parent(necromancer)
            .display(new ItemStack(ItemsRegistry.gaibRoot.get()), Component.translatable("advancements.valoria.dryador.title"), Component.translatable("advancements.valoria.dryador.description"), null, AdvancementType.CHALLENGE, true, true, false)
            .addCriterion("requirement", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(EntityTypeRegistry.DRYADOR.get())))
            .rewards(AdvancementRewards.Builder.experience(150))
            .save(saver, Valoria.loc("valoria/dryador"), existingFileHelper);

        AdvancementHolder firron = Advancement.Builder.advancement()
            .parent(dryador)
            .display(new ItemStack(ItemsRegistry.obsidianHeart.get()), Component.translatable("advancements.valoria.firron.title"), Component.translatable("advancements.valoria.firron.description"), null, AdvancementType.CHALLENGE, true, true, false)
            .addCriterion("requirement", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(EntityTypeRegistry.FIRRON.get())))
            .rewards(AdvancementRewards.Builder.experience(100))
            .save(saver, Valoria.loc("valoria/firron"), existingFileHelper);

        AdvancementHolder visit_the_valoria = Advancement.Builder.advancement()
            .parent(firron)
            .display(new ItemStack(ItemsRegistry.valoriaPortalFrameShard.get()), Component.translatable("advancements.valoria.visit_the_valoria.title"), Component.translatable("advancements.valoria.visit_the_valoria.description"), null, AdvancementType.GOAL, true, true, false)
            .addCriterion("requirement", ChangeDimensionTrigger.TriggerInstance.changedDimensionTo(LevelGen.VALORIA_KEY))
            .rewards(AdvancementRewards.Builder.experience(50))
            .save(saver, Valoria.loc("valoria/visit_the_valoria"), existingFileHelper);

        AdvancementHolder dosimetry = Advancement.Builder.advancement()
            .parent(visit_the_valoria)
            .display(new ItemStack(ItemsRegistry.nihilityMonitor.get()), Component.translatable("advancements.valoria.dosimetry.title"), Component.translatable("advancements.valoria.dosimetry.description"), null, AdvancementType.TASK, true, true, false)
            .addCriterion("has_nihility_monitor", InventoryChangeTrigger.TriggerInstance.hasItems(ItemsRegistry.nihilityMonitor.get()))
            .save(saver, Valoria.loc("valoria/dosimetry"), existingFileHelper);

        AdvancementHolder first_breath = Advancement.Builder.advancement()
            .parent(dosimetry)
            .display(new ItemStack(ItemsRegistry.respirator.get()), Component.translatable("advancements.valoria.first_breath.title"), Component.translatable("advancements.valoria.first_breath.description"), null, AdvancementType.TASK, true, true, false)
            .addCriterion("has_respirator", InventoryChangeTrigger.TriggerInstance.hasItems(ItemsRegistry.respirator.get()))
            .save(saver, Valoria.loc("valoria/first_breath"), existingFileHelper);

        AdvancementHolder geodes = Advancement.Builder.advancement()
            .parent(the_valoria)
            .display(new ItemStack(ItemsRegistry.stoneGeode.get()), Component.translatable("advancements.valoria.geodes.title"), Component.translatable("advancements.valoria.geodes.description"), null, AdvancementType.GOAL, true, true, false)
            .addCriterion("geodes", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(TagsRegistry.GEODES).build()))
            .rewards(AdvancementRewards.Builder.experience(50))
            .save(saver, Valoria.loc("valoria/geodes"), existingFileHelper);

        AdvancementHolder harmony_core = Advancement.Builder.advancement()
            .parent(elemental_manipulator)
            .display(new ItemStack(ItemsRegistry.natureCore.get()), Component.translatable("advancements.valoria.harmony_core.title"), Component.translatable(""), null, AdvancementType.TASK, true, true, false)
            .addCriterion("requirement", InventoryChangeTrigger.TriggerInstance.hasItems(ItemsRegistry.natureCore.get()))
            .save(saver, Valoria.loc("valoria/harmony_core"), existingFileHelper);

        AdvancementHolder infernal_core = Advancement.Builder.advancement()
            .parent(elemental_manipulator)
            .display(new ItemStack(ItemsRegistry.infernalCore.get()), Component.translatable("advancements.valoria.infernal_core.title"), Component.translatable(""), null, AdvancementType.TASK, true, true, false)
            .addCriterion("requirement", InventoryChangeTrigger.TriggerInstance.hasItems(ItemsRegistry.infernalCore.get()))
            .save(saver, Valoria.loc("valoria/infernal_core"), existingFileHelper);

        AdvancementHolder jade = Advancement.Builder.advancement()
            .parent(visit_the_valoria)
            .display(new ItemStack(ItemsRegistry.jade.get()), Component.translatable("advancements.valoria.jade.title"), Component.translatable("advancements.valoria.jade.description"), null, AdvancementType.TASK, true, true, false)
            .addCriterion("requirement", InventoryChangeTrigger.TriggerInstance.hasItems(ItemsRegistry.jade.get()))
            .save(saver, Valoria.loc("valoria/jade"), existingFileHelper);

        AdvancementHolder multitasker = Advancement.Builder.advancement()
            .parent(the_valoria)
            .display(new ItemStack(ItemsRegistry.cobaltMultiTool.get()), Component.translatable("advancements.valoria.multitasker.title"), Component.translatable("advancements.valoria.multitasker.description"), null, AdvancementType.TASK, true, true, false)
            .addCriterion("has_multitool", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(TagsRegistry.MULTI_TOOLS).build()))
            .save(saver, Valoria.loc("valoria/multitasker"), existingFileHelper);

        AdvancementHolder pyratite = Advancement.Builder.advancement()
            .parent(visit_the_valoria)
            .display(new ItemStack(ItemsRegistry.pyratite.get()), Component.translatable("advancements.valoria.pyratite.title"), Component.translatable("advancements.valoria.pyratite.description"), null, AdvancementType.TASK, true, true, false)
            .addCriterion("requirement", InventoryChangeTrigger.TriggerInstance.hasItems(ItemsRegistry.pyratite.get()))
            .save(saver, Valoria.loc("valoria/pyratite"), existingFileHelper);

        AdvancementHolder stalker = Advancement.Builder.advancement()
            .parent(first_breath)
            .display(new ItemStack(ItemsRegistry.gasMask.get()), Component.translatable("advancements.valoria.stalker.title"), Component.translatable("advancements.valoria.stalker.description"), null, AdvancementType.TASK, true, true, false)
            .addCriterion("has_gas_mask", InventoryChangeTrigger.TriggerInstance.hasItems(ItemsRegistry.gasMask.get()))
            .save(saver, Valoria.loc("valoria/stalker"), existingFileHelper);

        AdvancementHolder use_stone_crusher = useStoneCrusher(geodes, saver, existingFileHelper);
        AdvancementHolder void_core = Advancement.Builder.advancement()
            .parent(elemental_manipulator)
            .display(new ItemStack(ItemsRegistry.voidCore.get()), Component.translatable("advancements.valoria.void_core.title"), Component.translatable(""), null, AdvancementType.TASK, true, true, false)
            .addCriterion("requirement", InventoryChangeTrigger.TriggerInstance.hasItems(ItemsRegistry.voidCore.get()))
            .save(saver, Valoria.loc("valoria/void_core"), existingFileHelper);

        AdvancementHolder wicked_crystal = Advancement.Builder.advancement()
            .parent(dryador)
            .display(new ItemStack(ItemsRegistry.suspiciousGem.get()), Component.translatable("advancements.valoria.wicked_crystal.title"), Component.translatable("advancements.valoria.wicked_crystal.description"), null, AdvancementType.CHALLENGE, true, true, false)
            .addCriterion("requirement", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(EntityTypeRegistry.WICKED_CRYSTAL.get())))
            .rewards(AdvancementRewards.Builder.experience(200))
            .save(saver, Valoria.loc("valoria/wicked_crystal"), existingFileHelper);

        AdvancementHolder lumberjack = Advancement.Builder.advancement()
            .parent(the_valoria)
            .display(new ItemStack(Items.IRON_AXE), Component.translatable("advancements.valoria.lumberjack.title"), Component.translatable("advancements.valoria.lumberjack.description"), null, AdvancementType.TASK, true, true, false)
            // PORT NOTE: trigger instances are wrapped in Criterion via createCriterion and take Optional predicates in 1.21.
            .addCriterion("requirement", CriteriaTriggers.PLAYER_KILLED_ENTITY.createCriterion(new KilledTrigger.TriggerInstance(java.util.Optional.of(EntityPredicate.wrap(Builder.entity().equipment(EntityEquipmentPredicate.Builder.equipment().mainhand(ItemPredicate.Builder.item().of(ItemTags.AXES))))), java.util.Optional.of(EntityPredicate.wrap(Builder.entity().of(EntityTypeRegistry.ENT.get()))), java.util.Optional.empty())))
            .save(saver, Valoria.loc("valoria/lumberjack"), existingFileHelper);

        AdvancementHolder stonemason = Advancement.Builder.advancement()
            .parent(the_valoria)
            .display(new ItemStack(Items.IRON_PICKAXE), Component.translatable("advancements.valoria.stonemason.title"), Component.translatable("advancements.valoria.stonemason.description"), null, AdvancementType.TASK, true, true, false)
            .addCriterion("requirement", CriteriaTriggers.PLAYER_KILLED_ENTITY.createCriterion(new KilledTrigger.TriggerInstance(java.util.Optional.of(EntityPredicate.wrap(Builder.entity().equipment(EntityEquipmentPredicate.Builder.equipment().mainhand(ItemPredicate.Builder.item().of(ItemTags.PICKAXES))))), java.util.Optional.of(EntityPredicate.wrap(Builder.entity().of(TagsRegistry.STONE_GOLEMS))), java.util.Optional.empty())))
            .requirements(AdvancementRequirements.Strategy.OR)
            .save(saver, Valoria.loc("valoria/stonemason"), existingFileHelper);
}

    public AdvancementHolder useStoneCrusher(AdvancementHolder parent, Consumer<AdvancementHolder> saver, ExistingFileHelper existingFileHelper) {
        CompoundTag itemsNbt = new CompoundTag();
        itemsNbt.put("Items", new ListTag());

        // PORT NOTE: LocationPredicate.Builder#setBlock takes the BlockPredicate.Builder in 1.21.
        BlockPredicate.Builder stoneCrusherBlock = BlockPredicate.Builder.block()
        .of(BlockRegistry.stoneCrusher.get())
        .hasNbt(itemsNbt);

        return Advancement.Builder.advancement()
        .parent(parent)
        .display(new ItemStack(BlockRegistry.stoneCrusher.get()),
            Component.translatable("advancements.valoria.use_stone_crusher"),
            Component.translatable("advancements.valoria.use_stone_crusher.description"),
            null,
            AdvancementType.TASK,
            true,
            true,
            false
        )

        .addCriterion("use_stone_crusher", ItemUsedOnLocationTrigger.TriggerInstance.itemUsedOnBlock(LocationPredicate.Builder.location().setBlock(stoneCrusherBlock), ItemPredicate.Builder.item().of(TagsRegistry.STONE_CRUSHER_TOOL)))
        .save(saver, Valoria.loc("valoria/use_stone_crusher"), existingFileHelper);
    }
}

