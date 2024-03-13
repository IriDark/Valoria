package com.idark.valoria.datagen;

import com.idark.valoria.Valoria;
import com.idark.valoria.registries.world.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
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
        blockWithItem(ModBlocks.POLISHED_DEEP_MARBLE);
        blockWithItem(ModBlocks.EYE_STONE);

        stairsBlock((StairBlock) ModBlocks.DEEP_MARBLE_STAIRS.get(), blockTexture(ModBlocks.DEEP_MARBLE.get()));
        stairsBlock((StairBlock) ModBlocks.POLISHED_DEEP_MARBLE_STAIRS.get(), blockTexture(ModBlocks.DEEP_MARBLE.get()));
        slabBlock((SlabBlock) ModBlocks.DEEP_MARBLE_SLAB.get(), blockTexture(ModBlocks.DEEP_MARBLE.get()), blockTexture(ModBlocks.DEEP_MARBLE.get()));
        slabBlock((SlabBlock) ModBlocks.POLISHED_DEEP_MARBLE_SLAB.get(), blockTexture(ModBlocks.POLISHED_DEEP_MARBLE.get()), blockTexture(ModBlocks.POLISHED_DEEP_MARBLE.get()));

        wallBlock((WallBlock) ModBlocks.DEEP_MARBLE_WALL.get(), blockTexture(ModBlocks.DEEP_MARBLE.get()));
        wallBlock((WallBlock) ModBlocks.POLISHED_DEEP_MARBLE_WALL.get(), blockTexture(ModBlocks.POLISHED_DEEP_MARBLE.get()));

        blockItem(ModBlocks.DEEP_MARBLE_STAIRS);
        blockItem(ModBlocks.POLISHED_DEEP_MARBLE_STAIRS);
        blockItem(ModBlocks.DEEP_MARBLE_SLAB);
        blockItem(ModBlocks.POLISHED_DEEP_MARBLE_SLAB);
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