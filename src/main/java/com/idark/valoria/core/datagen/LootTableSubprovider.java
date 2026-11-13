package com.idark.valoria.core.datagen;

import com.idark.valoria.registries.*;
import com.idark.valoria.registries.block.types.*;
import com.idark.valoria.registries.block.types.plants.*;
import net.minecraft.advancements.critereon.*;
import net.minecraft.data.loot.*;
import net.minecraft.world.flag.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.storage.loot.*;
import net.minecraft.world.level.storage.loot.entries.*;
import net.minecraft.world.level.storage.loot.functions.*;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.minecraft.world.level.storage.loot.providers.number.*;
import net.minecraftforge.registries.*;

import java.util.*;
import java.util.function.*;

public class LootTableSubprovider extends BlockLootSubProvider {
    public final List<Block> blocks = new ArrayList<>();

    public LootTableSubprovider() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void add(Block pBlock, Function<Block, LootTable.Builder> pFactory) {
        this.add(pBlock, pFactory.apply(pBlock));
        blocks.add(pBlock);
    }

    @Override
    protected void add(Block pBlock, LootTable.Builder pBuilder) {
        super.add(pBlock, pBuilder);
        blocks.add(pBlock);
    }

    @Override
    protected void dropOther(Block pBlock, ItemLike pItem) {
        super.dropOther(pBlock, pItem);
        blocks.add(pBlock);
    }

    @Override
    protected void dropSelf(Block pBlock) {
        this.dropOther(pBlock, pBlock);
    }

    private void dropItem(Block block, ItemLike item) {
        this.add(block, ignored -> createSingleItemTable(item));
    }

    protected LootTable.Builder createCrystalDrop(Block crystalBlock, ItemLike gem) {
        return LootTable.lootTable().withPool(
            LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1.0F))
                .when(ExplosionCondition.survivesExplosion())
                .add(AlternativesEntry.alternatives(
                    LootItem.lootTableItem(crystalBlock).when(HAS_SILK_TOUCH),
                    LootItem.lootTableItem(gem)
                ))
        );
    }

    protected LootTable.Builder createCountOreDrop(Block block, ItemLike item, float min, float max) {
        return createSilkTouchDispatchTable(block, this.applyExplosionDecay(block, LootItem.lootTableItem(item)
                .apply(SetItemCountFunction.setCount(UniformGenerator.between(min, max)))
                .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return blocks;
    }

    private final Set<Block> ignoredBlocks = Set.of(
        BlockRegistry.sarcophagus.get(),
        BlockRegistry.cryptPot.get(),
        BlockRegistry.decoratedCryptPot.get(),
        BlockRegistry.potSmall.get(),
        BlockRegistry.potSmallHandles.get(),
        BlockRegistry.potLong.get(),
        BlockRegistry.potLongHandles.get(),
        BlockRegistry.potLongMossy.get(),
        BlockRegistry.potLongMossyHandles.get(),
        BlockRegistry.potDesert.get(),
        BlockRegistry.potDesertHandles.get(),
        BlockRegistry.potCaveSmall.get(),
        BlockRegistry.potCaveSmallHandles.get(),
        BlockRegistry.potCaveLong.get(),
        BlockRegistry.potCaveLongHandles.get(),
        BlockRegistry.potDeepslateSmall.get(),
        BlockRegistry.potDeepslateSmallHandles.get(),
        BlockRegistry.potDeepslateLong.get(),
        BlockRegistry.potDeepslateLongHandles.get(),
        BlockRegistry.shadeBlossom.get(),
        BlockRegistry.shadeBranch.get(),
        BlockRegistry.shadeBranchVine.get(),
        BlockRegistry.fleshCyst.get(),
        BlockRegistry.aloe.get(),
        BlockRegistry.soulFlower.get(),
        BlockRegistry.violetSprout.get(),
        BlockRegistry.violetSproutPlant.get()
    );

    @Override
    protected void generate() {
        this.dropOther(BlockRegistry.tombstoneSpikesTrap.get(), BlockRegistry.polishedTombstone.get());
        this.dropOther(BlockRegistry.tombstoneFirechargeTrap.get(), BlockRegistry.polishedTombstone.get());
        this.dropOther(BlockRegistry.voidSpikesTrap.get(), BlockRegistry.polishedVoidStone.get());
        this.dropOther(BlockRegistry.voidFirechargeTrap.get(), BlockRegistry.polishedVoidStone.get());
        this.dropOther(BlockRegistry.cobbledShaleSpikesTrap.get(), BlockRegistry.polishedCobbledShale.get());
        this.dropOther(BlockRegistry.cobbledShaleFirechargeTrap.get(), BlockRegistry.polishedCobbledShale.get());

        this.add(BlockRegistry.eldritchDoor.get(), block -> createDoorTable(BlockRegistry.eldritchDoor.get()));
        this.add(BlockRegistry.shadeDoor.get(), block -> createDoorTable(BlockRegistry.shadeDoor.get()));
        this.add(BlockRegistry.dreadwoodDoor.get(), block -> createDoorTable(BlockRegistry.dreadwoodDoor.get()));
        this.add(BlockRegistry.bronzeDoor.get(), block -> createDoorTable(BlockRegistry.bronzeDoor.get()));

        this.add(BlockRegistry.eldritchLeaves.get(), block -> createLeavesDrops(block, BlockRegistry.eldritchSapling.get(), NORMAL_LEAVES_SAPLING_CHANCES));
        this.add(BlockRegistry.shadeLeaves.get(), block -> createLeavesDrops(block, BlockRegistry.shadeSapling.get(), NORMAL_LEAVES_SAPLING_CHANCES));

        this.add(BlockRegistry.eldritchSign.get(), block -> createSingleItemTable(block));
        this.add(BlockRegistry.eldritchWallSign.get(), block -> createSingleItemTable(block));
        this.add(BlockRegistry.eldritchHangingSign.get(), block -> createSingleItemTable(block));
        this.add(BlockRegistry.eldritchWallHangingSign.get(), block -> createSingleItemTable(block));

        this.add(BlockRegistry.shadeSign.get(), block -> createSingleItemTable(block));
        this.add(BlockRegistry.shadeWallSign.get(), block -> createSingleItemTable(block));
        this.add(BlockRegistry.shadeHangingSign.get(), block -> createSingleItemTable(block));
        this.add(BlockRegistry.shadeWallHangingSign.get(), block -> createSingleItemTable(block));

        this.add(BlockRegistry.dreadwoodSign.get(), block -> createSingleItemTable(block));
        this.add(BlockRegistry.dreadwoodWallSign.get(), block -> createSingleItemTable(block));
        this.add(BlockRegistry.dreadwoodHangingSign.get(), block -> createSingleItemTable(block));
        this.add(BlockRegistry.dreadwoodWallHangingSign.get(), block -> createSingleItemTable(block));

        // Ore Drops
        this.add(BlockRegistry.pyratiteOre.get(), block -> createOreDrop(block, ItemsRegistry.pyratite.get()));
        this.add(BlockRegistry.amberOre.get(), block -> createOreDrop(block, ItemsRegistry.amberGem.get()));
        this.add(BlockRegistry.deepslateAmberOre.get(), block -> createOreDrop(block, ItemsRegistry.amberGem.get()));
        this.add(BlockRegistry.sapphireOre.get(), block -> createOreDrop(block, ItemsRegistry.sapphireGem.get()));
        this.add(BlockRegistry.deepslateSapphireOre.get(), block -> createOreDrop(block, ItemsRegistry.sapphireGem.get()));
        this.add(BlockRegistry.rubyOre.get(), block -> createOreDrop(block, ItemsRegistry.rubyGem.get()));
        this.add(BlockRegistry.deepslateRubyOre.get(), block -> createOreDrop(block, ItemsRegistry.rubyGem.get()));
        this.add(BlockRegistry.cobaltOre.get(), block -> createOreDrop(block, ItemsRegistry.rawCobalt.get()));
        this.add(BlockRegistry.deepslateCobaltOre.get(), block -> createOreDrop(block, ItemsRegistry.rawCobalt.get()));

        // Crystal Drops
        this.add(BlockRegistry.amberCrystal.get(), block -> createCrystalDrop(block, ItemsRegistry.amberGem.get()));
        this.add(BlockRegistry.amethystCrystal.get(), block -> createCrystalDrop(block, ItemsRegistry.amethystGem.get()));
        this.add(BlockRegistry.rubyCrystal.get(), block -> createCrystalDrop(block, ItemsRegistry.rubyGem.get()));
        this.add(BlockRegistry.sapphireCrystal.get(), block -> createCrystalDrop(block, ItemsRegistry.sapphireGem.get()));
        this.add(BlockRegistry.pyratiteCrystal.get(), block -> createCrystalDrop(block, ItemsRegistry.pyratite.get()));

        // Multi-drop Ore Drops
        this.add(BlockRegistry.jadeOre.get(), block -> createCountOreDrop(block, ItemsRegistry.jade.get(), 2.0F, 5.0F));
        this.add(BlockRegistry.picriteJadeOre.get(), block -> createCountOreDrop(block, ItemsRegistry.jade.get(), 2.0F, 5.0F));
        this.add(BlockRegistry.pearliumOre.get(), block -> createCountOreDrop(block, ItemsRegistry.oceanicPearl.get(), 2.0F, 5.0F));
        this.add(BlockRegistry.wickedAmethystOre.get(), block -> createCountOreDrop(block, ItemsRegistry.wickedAmethyst.get(), 1.0F, 3.0F));
        this.add(BlockRegistry.dormantCrystals.get(), block -> createCountOreDrop(block, ItemsRegistry.unchargedShard.get(), 1.0F, 3.0F));

        // Remains & Flora Drops
        this.add(BlockRegistry.fleshRemains.get(), block -> createCountOreDrop(block, ItemsRegistry.remains.get(), 2.0F, 5.0F));
        this.add(BlockRegistry.aloeSmall.get(), block -> LootTable.lootTable().withPool(
                LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .when(ExplosionCondition.survivesExplosion())
                        .add(AlternativesEntry.alternatives(
                                LootItem.lootTableItem(block).when(HAS_SHEARS),
                                LootItem.lootTableItem(ItemsRegistry.aloePiece.get())
                        ))));

        this.add(BlockRegistry.eyeFlesh.get(), block -> createCrystalDrop(block, ItemsRegistry.eyeChunk.get()));
        this.add(BlockRegistry.eyeMeat.get(), block -> createCrystalDrop(block, ItemsRegistry.eyeChunk.get()));
        this.add(BlockRegistry.eyeStone.get(), block -> createCrystalDrop(block, ItemsRegistry.eyeChunk.get()));

        this.add(BlockRegistry.ephemarite.get(), block -> LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).when(ExplosionCondition.survivesExplosion())
                .add(LootItem.lootTableItem(BlockRegistry.ephemarite.get()))
                .add(LootItem.lootTableItem(ItemsRegistry.stoneGeode.get()).when(LootItemRandomChanceCondition.randomChance(0.15F)))
                .add(LootItem.lootTableItem(ItemsRegistry.dirtGeode.get()).when(LootItemRandomChanceCondition.randomChance(0.25F)))));

        this.add(BlockRegistry.ephemariteLow.get(), block -> LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).when(ExplosionCondition.survivesExplosion())
                .add(LootItem.lootTableItem(BlockRegistry.ephemariteLow.get()))
                .add(LootItem.lootTableItem(ItemsRegistry.stoneGeode.get()).when(LootItemRandomChanceCondition.randomChance(0.15F)))
                .add(LootItem.lootTableItem(ItemsRegistry.dirtGeode.get()).when(LootItemRandomChanceCondition.randomChance(0.25F)))));

        this.add(BlockRegistry.taintedRoots.get(), (p_249159_) -> {
            return this.applyExplosionDecay(p_249159_, LootTable.lootTable()
                    .withPool(LootPool.lootPool().when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(BlockRegistry.taintedRoots.get()).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(TaintedRootsBlock.AGE, 2))).add(LootItem.lootTableItem(ItemsRegistry.taintedBerries.get())).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 3.0F))).apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE)))
                    .withPool(LootPool.lootPool().when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(BlockRegistry.taintedRoots.get()).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(TaintedRootsBlock.AGE, 1))).add(LootItem.lootTableItem(Items.STICK)).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))).apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE))));
        });

        this.add(BlockRegistry.gaibRoots.get(), (block) -> {
            return LootTable.lootTable().apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 6.0F))).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).when(ExplosionCondition.survivesExplosion()).add(LootItem.lootTableItem(ItemsRegistry.gaibRoot.get()).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.LOWER)))));
        });

        this.dropItem(BlockRegistry.abyssalGlowfernPlant.get(), BlockRegistry.abyssalGlowfern.get());
        this.dropItem(BlockRegistry.bloodVinePlant.get(), BlockRegistry.bloodVine.get());
        this.dropItem(BlockRegistry.caveRootPlant.get(), BlockRegistry.caveRoot.get());
        this.dropItem(BlockRegistry.glowVioletSproutPlant.get(), BlockRegistry.glowVioletSprout.get());
        this.dropItem(BlockRegistry.driedPlant.get(), Items.STICK);
        this.dropItem(BlockRegistry.driedRoots.get(), Items.STICK);

        for (RegistryObject<Block> entry : BlockRegistry.BLOCK.getEntries()) {
            Block block = entry.get();
            if (ignoredBlocks.contains(block) || block.getLootTable().equals(BuiltInLootTables.EMPTY)) {
                continue;
            }

            if (!this.blocks.contains(block)) {
                if (block instanceof SlabBlock slab) {
                    this.add(slab, b -> createSlabItemTable(b));
                } else if (block instanceof DoorBlock door) {
                    this.add(door, b -> createDoorTable(b));
                } else if (block instanceof LeavesBlock leaves) {
                    this.add(leaves, b -> createSingleItemTable(b));
                } else if (block instanceof FlowerPotBlock pot) {
                    if (pot.getContent() != null && pot.getContent() != Blocks.AIR) {
                        this.add(pot, b -> createPotFlowerItemTable(pot.getContent()));
                    } else {
                        this.dropSelf(pot);
                    }
                } else if (block instanceof DoublePlantBlock plant) {
                    this.add(plant, b -> LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).when(ExplosionCondition.survivesExplosion()).add(LootItem.lootTableItem(b).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(b).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.LOWER))))));
                } else if (block instanceof AlchemyStationBlock station) {
                    this.add(station, b -> createSinglePropConditionTable(b, AlchemyStationBlock.PART, WorkbenchPart.BOTTOM_RIGHT));
                } else if (block instanceof HeavyWorkbenchBlock workbench) {
                    this.add(workbench, b -> createSinglePropConditionTable(b, HeavyWorkbenchBlock.PART, WorkbenchPart.BOTTOM_RIGHT));
                } else if (block instanceof HeavyAnvil anvil) {
                    this.add(anvil, b -> createSinglePropConditionTable(b, BlockStateProperties.BED_PART, BedPart.HEAD));
                } else {
                    this.dropSelf(block);
                }
            }
        }
    }
}
