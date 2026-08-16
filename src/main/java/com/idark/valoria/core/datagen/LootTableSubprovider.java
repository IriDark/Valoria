package com.idark.valoria.core.datagen;

import com.idark.valoria.registries.*;
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
    protected void dropSelf(Block pBlock) {
        this.dropOther(pBlock, pBlock);
        blocks.add(pBlock);
    }


    private void dropItem(Block block, ItemLike item) {
        this.add(block, ignored -> createSingleItemTable(item));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return blocks;
    }

    private final Set<Block> ignoredBlocks = Set.of(
        BlockRegistry.cryptPot.get(),
        BlockRegistry.decoratedCryptPot.get(),
        BlockRegistry.potSmall.get(),
        BlockRegistry.potLong.get(),
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
        BlockRegistry.shadeBranchVine.get()
    );

    @Override
    protected void generate() {
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
        this.dropItem(BlockRegistry.violetSproutPlant.get(), BlockRegistry.violetSprout.get());
        this.dropItem(BlockRegistry.shadeBranch.get(), Items.STICK);
        this.dropItem(BlockRegistry.driedPlant.get(), Items.STICK);
        this.dropItem(BlockRegistry.driedRoots.get(), Items.STICK);
        for (RegistryObject<Block> entry : BlockRegistry.BLOCK.getEntries()){
            Block block = entry.get();
            if (ignoredBlocks.contains(block) || block.getLootTable().equals(BuiltInLootTables.EMPTY)) {
                continue;
            }

            if(!this.blocks.contains(block)){
                if(block instanceof SlabBlock slab){
                    this.add(slab, b -> createSlabItemTable(b));
                }else if(block instanceof DoorBlock door){
                    this.add(door, b -> createDoorTable(b));
                }else if(block instanceof LeavesBlock leaves){
                    this.add(leaves, b -> createSingleItemTable(b));
                }else if(block instanceof FlowerPotBlock pot){
                    if(pot.getContent() != null && pot.getContent() != Blocks.AIR){
                        this.add(pot, b -> createPotFlowerItemTable(pot.getContent()));
                    }else{
                        this.dropSelf(pot);
                    }
                }else{
                    this.dropSelf(block);
                }
            }
        }
    }
}
