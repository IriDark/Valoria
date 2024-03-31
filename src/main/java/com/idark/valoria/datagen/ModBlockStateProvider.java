package com.idark.valoria.datagen;

import com.idark.valoria.Valoria;
import com.idark.valoria.client.compat.quark.QuarkIntegration;
import com.idark.valoria.registries.world.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Valoria.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(ModBlocks.DEEP_MARBLE);
        blockWithItem(ModBlocks.POLISHED_DEEP_MARBLE);
        blockWithItem(ModBlocks.PICRITE);
        blockWithItem(ModBlocks.POLISHED_PICRITE);
        blockWithItem(ModBlocks.EYE_STONE);

        stairsBlock((StairBlock) ModBlocks.DEEP_MARBLE_STAIRS.get(), blockTexture(ModBlocks.DEEP_MARBLE.get()));
        stairsBlock((StairBlock) ModBlocks.POLISHED_DEEP_MARBLE_STAIRS.get(), blockTexture(ModBlocks.DEEP_MARBLE.get()));
        slabBlock((SlabBlock) ModBlocks.DEEP_MARBLE_SLAB.get(), blockTexture(ModBlocks.DEEP_MARBLE.get()), blockTexture(ModBlocks.DEEP_MARBLE.get()));
        slabBlock((SlabBlock) ModBlocks.POLISHED_DEEP_MARBLE_SLAB.get(), blockTexture(ModBlocks.POLISHED_DEEP_MARBLE.get()), blockTexture(ModBlocks.POLISHED_DEEP_MARBLE.get()));

        wallBlock((WallBlock) ModBlocks.DEEP_MARBLE_WALL.get(), blockTexture(ModBlocks.DEEP_MARBLE.get()));
        wallBlock((WallBlock) ModBlocks.POLISHED_DEEP_MARBLE_WALL.get(), blockTexture(ModBlocks.POLISHED_DEEP_MARBLE.get()));

        fenceBlock((FenceBlock) ModBlocks.SHADEWOOD_FENCE.get(), blockTexture(ModBlocks.SHADEWOOD_PLANKS.get()));
        fenceGateBlock((FenceGateBlock) ModBlocks.SHADEWOOD_FENCE_GATE.get(), blockTexture(ModBlocks.SHADEWOOD_PLANKS.get()));

        blockItem(ModBlocks.DEEP_MARBLE_STAIRS);
        blockItem(ModBlocks.POLISHED_DEEP_MARBLE_STAIRS);
        blockItem(ModBlocks.DEEP_MARBLE_SLAB);
        blockItem(ModBlocks.POLISHED_DEEP_MARBLE_SLAB);

        stairsBlock((StairBlock) ModBlocks.PICRITE_STAIRS.get(), blockTexture(ModBlocks.PICRITE.get()));
        stairsBlock((StairBlock) ModBlocks.POLISHED_PICRITE_STAIRS.get(), blockTexture(ModBlocks.POLISHED_PICRITE.get()));
        slabBlock((SlabBlock) ModBlocks.PICRITE_SLAB.get(), blockTexture(ModBlocks.PICRITE.get()), blockTexture(ModBlocks.PICRITE.get()));
        slabBlock((SlabBlock) ModBlocks.POLISHED_PICRITE_SLAB.get(), blockTexture(ModBlocks.POLISHED_PICRITE.get()), blockTexture(ModBlocks.POLISHED_PICRITE.get()));

        wallBlock((WallBlock) ModBlocks.PICRITE_WALL.get(), blockTexture(ModBlocks.PICRITE.get()));
        wallBlock((WallBlock) ModBlocks.POLISHED_PICRITE_WALL.get(), blockTexture(ModBlocks.POLISHED_PICRITE.get()));

        blockItem(ModBlocks.PICRITE_STAIRS);
        blockItem(ModBlocks.POLISHED_PICRITE_STAIRS);
        blockItem(ModBlocks.PICRITE_SLAB);
        blockItem(ModBlocks.POLISHED_PICRITE_SLAB);
        if(QuarkIntegration.isLoaded()) {
            blockItem(QuarkIntegration.LoadedOnly.BRONZE_VERTICAL_SLAB);
            blockItem(QuarkIntegration.LoadedOnly.CUT_BRONZE_VERTICAL_SLAB);
            blockItem(QuarkIntegration.LoadedOnly.AMBANE_STONE_BRICKS_VERTICAL_SLAB);
            blockItem(QuarkIntegration.LoadedOnly.AMBANE_STONE_VERTICAL_SLAB);
            blockItem(QuarkIntegration.LoadedOnly.POLISHED_AMBANE_STONE_VERTICAL_SLAB);
            blockItem(QuarkIntegration.LoadedOnly.LIMESTONE_VERTICAL_SLAB);
            blockItem(QuarkIntegration.LoadedOnly.LIMESTONE_BRICKS_VERTICAL_SLAB);
            blockItem(QuarkIntegration.LoadedOnly.POLISHED_LIMESTONE_VERTICAL_SLAB);
            blockItem(QuarkIntegration.LoadedOnly.CUT_LIMESTONE_VERTICAL_SLAB);
            blockItem(QuarkIntegration.LoadedOnly.CRACKED_LIMESTONE_BRICKS_VERTICAL_SLAB);
            blockItem(QuarkIntegration.LoadedOnly.DUNESTONE_VERTICAL_SLAB);
            blockItem(QuarkIntegration.LoadedOnly.DUNESTONE_BRICKS_VERTICAL_SLAB);
            blockItem(QuarkIntegration.LoadedOnly.CRYSTAL_STONE_VERTICAL_SLAB);
            blockItem(QuarkIntegration.LoadedOnly.CRYSTAL_STONE_BRICKS_VERTICAL_SLAB);
            blockItem(QuarkIntegration.LoadedOnly.TOMBSTONE_VERTICAL_SLAB);
            blockItem(QuarkIntegration.LoadedOnly.TOMBSTONE_BRICKS_VERTICAL_SLAB);
            blockItem(QuarkIntegration.LoadedOnly.DEEP_MARBLE_VERTICAL_SLAB);
            blockItem(QuarkIntegration.LoadedOnly.POLISHED_DEEP_MARBLE_VERTICAL_SLAB);
            blockItem(QuarkIntegration.LoadedOnly.PICRITE_VERTICAL_SLAB);
            blockItem(QuarkIntegration.LoadedOnly.POLISHED_PICRITE_VERTICAL_SLAB);
            blockItem(QuarkIntegration.LoadedOnly.VOID_STONE_VERTICAL_SLAB);
            blockItem(QuarkIntegration.LoadedOnly.VOID_BRICK_VERTICAL_SLAB);
            blockItem(QuarkIntegration.LoadedOnly.VOID_CRACKED_BRICK_VERTICAL_SLAB);
            blockItem(QuarkIntegration.LoadedOnly.CHISELED_VOID_BRICKS_VERTICAL_SLAB);
        }
    }

    private void saplingBlock(RegistryObject<Block> blockRegistryObject) {
        simpleBlock(blockRegistryObject.get(),
                models().cross(ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath(), blockTexture(blockRegistryObject.get())).renderType("cutout"));
    }

    public void hangingSignBlock(Block signBlock, Block wallSignBlock, ResourceLocation texture) {
        ModelFile sign = models().sign(name(signBlock), texture);
        hangingSignBlock(signBlock, wallSignBlock, sign);
    }

    public void hangingSignBlock(Block signBlock, Block wallSignBlock, ModelFile sign) {
        simpleBlock(signBlock, sign);
        simpleBlock(wallSignBlock, sign);
    }

    private String name(Block block) {
        return key(block).getPath();
    }

    private ResourceLocation key(Block block) {
        return ForgeRegistries.BLOCKS.getKey(block);
    }

    private void leavesBlock(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(),
                models().singleTexture(ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath(), new ResourceLocation("minecraft:block/leaves"),
                        "all", blockTexture(blockRegistryObject.get())).renderType("cutout"));
    }

    private void blockItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockItem(blockRegistryObject.get(), new ModelFile.UncheckedModelFile(Valoria.MOD_ID +
                ":block/" + ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath()));
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
}