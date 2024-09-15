package com.idark.valoria.core.datagen;

import com.idark.valoria.Valoria;
import com.idark.valoria.registries.BlockRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockStateGen extends BlockStateProvider {

    public BlockStateGen(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Valoria.ID, exFileHelper);
    }

    public void registerMarbles() {
        blockWithItem(BlockRegistry.DEEP_MARBLE);
        blockWithItem(BlockRegistry.POLISHED_DEEP_MARBLE);

        stairsBlock((StairBlock) BlockRegistry.DEEP_MARBLE_STAIRS.get(), blockTexture(BlockRegistry.DEEP_MARBLE.get()));
        stairsBlock((StairBlock) BlockRegistry.POLISHED_DEEP_MARBLE_STAIRS.get(), blockTexture(BlockRegistry.DEEP_MARBLE.get()));
        slabBlock((SlabBlock) BlockRegistry.DEEP_MARBLE_SLAB.get(), blockTexture(BlockRegistry.DEEP_MARBLE.get()), blockTexture(BlockRegistry.DEEP_MARBLE.get()));
        slabBlock((SlabBlock) BlockRegistry.POLISHED_DEEP_MARBLE_SLAB.get(), blockTexture(BlockRegistry.POLISHED_DEEP_MARBLE.get()), blockTexture(BlockRegistry.POLISHED_DEEP_MARBLE.get()));

        wallBlock((WallBlock) BlockRegistry.DEEP_MARBLE_WALL.get(), blockTexture(BlockRegistry.DEEP_MARBLE.get()));
        wallBlock((WallBlock) BlockRegistry.POLISHED_DEEP_MARBLE_WALL.get(), blockTexture(BlockRegistry.POLISHED_DEEP_MARBLE.get()));

        blockItem(BlockRegistry.DEEP_MARBLE_STAIRS);
        blockItem(BlockRegistry.POLISHED_DEEP_MARBLE_STAIRS);
        blockItem(BlockRegistry.DEEP_MARBLE_SLAB);
        blockItem(BlockRegistry.POLISHED_DEEP_MARBLE_SLAB);
    }

    public void registerCobbledShale() {
        blockWithItem(BlockRegistry.COBBLED_SHALE);
        blockWithItem(BlockRegistry.POLISHED_COBBLED_SHALE);

        stairsBlock((StairBlock) BlockRegistry.COBBLED_SHALE_STAIRS.get(), blockTexture(BlockRegistry.COBBLED_SHALE.get()));
        stairsBlock((StairBlock) BlockRegistry.POLISHED_COBBLED_SHALE_STAIRS.get(), blockTexture(BlockRegistry.POLISHED_COBBLED_SHALE.get()));
        slabBlock((SlabBlock) BlockRegistry.COBBLED_SHALE_SLAB.get(), blockTexture(BlockRegistry.COBBLED_SHALE.get()), blockTexture(BlockRegistry.DEEP_MARBLE.get()));
        slabBlock((SlabBlock) BlockRegistry.POLISHED_COBBLED_SHALE_SLAB.get(), blockTexture(BlockRegistry.POLISHED_COBBLED_SHALE.get()), blockTexture(BlockRegistry.POLISHED_DEEP_MARBLE.get()));

        wallBlock((WallBlock) BlockRegistry.COBBLED_SHALE_WALL.get(), blockTexture(BlockRegistry.COBBLED_SHALE.get()));
        wallBlock((WallBlock) BlockRegistry.POLISHED_COBBLED_SHALE_WALL.get(), blockTexture(BlockRegistry.POLISHED_COBBLED_SHALE.get()));

        blockItem(BlockRegistry.COBBLED_SHALE_STAIRS);
        blockItem(BlockRegistry.POLISHED_COBBLED_SHALE_STAIRS);
        blockItem(BlockRegistry.COBBLED_SHALE_SLAB);
        blockItem(BlockRegistry.POLISHED_COBBLED_SHALE_SLAB);
    }

    @Override
    protected void registerStatesAndModels() {
        registerMarbles();
        registerCobbledShale();
        blockWithItem(BlockRegistry.PICRITE);
        blockWithItem(BlockRegistry.POLISHED_PICRITE);
        blockWithItem(BlockRegistry.EYE_STONE);
        blockWithItem(BlockRegistry.PYRATITE_ORE);

        paneBlockWithRenderType((IronBarsBlock) BlockRegistry.BRONZE_GLASS_PANE.get(), blockTexture(BlockRegistry.BRONZE_GLASS.get()), blockTexture(BlockRegistry.BRONZE_BLOCK.get()), "cutout");

        blockWithItem(BlockRegistry.ELDRITCH_LOG);
        blockWithItem(BlockRegistry.STRIPPED_ELDRITCH_LOG);
        blockWithItem(BlockRegistry.ELDRITCH_PLANKS);
        blockWithItem(BlockRegistry.ELDRITCH_LEAVES);
        doorBlock((DoorBlock) BlockRegistry.ELDRITCH_DOOR.get(), new ResourceLocation(Valoria.ID, ModelProvider.BLOCK_FOLDER + "/eldritch_door_bottom"), new ResourceLocation(Valoria.ID, ModelProvider.BLOCK_FOLDER + "/eldritch_door_top"));
        trapdoorBlock((TrapDoorBlock) BlockRegistry.ELDRITCH_TRAPDOOR.get(), blockTexture(BlockRegistry.ELDRITCH_TRAPDOOR.get()), true);

        signBlock((StandingSignBlock) BlockRegistry.ELDRITCH_SIGN.get(), (WallSignBlock) BlockRegistry.ELDRITCH_WALL_SIGN.get(), blockTexture(BlockRegistry.ELDRITCH_PLANKS.get()));
        hangingSignBlock(BlockRegistry.ELDRITCH_HANGING_SIGN.get(), BlockRegistry.ELDRITCH_WALL_HANGING_SIGN.get(), blockTexture(BlockRegistry.ELDRITCH_PLANKS.get()));

        buttonBlock((ButtonBlock) BlockRegistry.ELDRITCH_BUTTON.get(), blockTexture(BlockRegistry.ELDRITCH_PLANKS.get()));

        pressurePlateBlock((PressurePlateBlock) BlockRegistry.ELDRITCH_PRESSURE_PLATE.get(), blockTexture(BlockRegistry.ELDRITCH_PLANKS.get()));

        stairsBlock((StairBlock) BlockRegistry.ELDRITCH_PLANKS_STAIRS.get(), blockTexture(BlockRegistry.ELDRITCH_PLANKS.get()));
        slabBlock((SlabBlock) BlockRegistry.ELDRITCH_PLANKS_SLAB.get(), blockTexture(BlockRegistry.ELDRITCH_PLANKS.get()), blockTexture(BlockRegistry.ELDRITCH_PLANKS.get()));

        stairsBlock((StairBlock) BlockRegistry.EPHEMARITE_LOW_STAIRS.get(), blockTexture(BlockRegistry.EPHEMARITE_LOW.get()));
        stairsBlock((StairBlock) BlockRegistry.EPHEMARITE_STAIRS.get(), blockTexture(BlockRegistry.EPHEMARITE.get()));
        slabBlock((SlabBlock) BlockRegistry.EPHEMARITE_LOW_SLAB.get(), blockTexture(BlockRegistry.EPHEMARITE_LOW.get()), blockTexture(BlockRegistry.EPHEMARITE_LOW.get()));
        slabBlock((SlabBlock) BlockRegistry.EPHEMARITE_SLAB.get(), blockTexture(BlockRegistry.EPHEMARITE.get()), blockTexture(BlockRegistry.EPHEMARITE.get()));

        stairsBlock((StairBlock) BlockRegistry.POLISHED_EPHEMARITE_LOW_STAIRS.get(), blockTexture(BlockRegistry.POLISHED_EPHEMARITE_LOW.get()));
        stairsBlock((StairBlock) BlockRegistry.POLISHED_EPHEMARITE_STAIRS.get(), blockTexture(BlockRegistry.POLISHED_EPHEMARITE.get()));
        slabBlock((SlabBlock) BlockRegistry.POLISHED_EPHEMARITE_LOW_SLAB.get(), blockTexture(BlockRegistry.POLISHED_EPHEMARITE_LOW.get()), blockTexture(BlockRegistry.POLISHED_EPHEMARITE_LOW.get()));
        slabBlock((SlabBlock) BlockRegistry.POLISHED_EPHEMARITE_SLAB.get(), blockTexture(BlockRegistry.POLISHED_EPHEMARITE.get()), blockTexture(BlockRegistry.POLISHED_EPHEMARITE.get()));

        fenceBlock((FenceBlock) BlockRegistry.SHADEWOOD_FENCE.get(), blockTexture(BlockRegistry.SHADEWOOD_PLANKS.get()));
        fenceGateBlock((FenceGateBlock) BlockRegistry.SHADEWOOD_FENCE_GATE.get(), blockTexture(BlockRegistry.SHADEWOOD_PLANKS.get()));
        fenceBlock((FenceBlock) BlockRegistry.ELDRITCH_FENCE.get(), blockTexture(BlockRegistry.ELDRITCH_PLANKS.get()));
        fenceGateBlock((FenceGateBlock) BlockRegistry.ELDRITCH_FENCE_GATE.get(), blockTexture(BlockRegistry.ELDRITCH_PLANKS.get()));

        blockItem(BlockRegistry.EPHEMARITE_STAIRS);
        blockItem(BlockRegistry.EPHEMARITE_LOW_STAIRS);
        blockItem(BlockRegistry.EPHEMARITE_SLAB);
        blockItem(BlockRegistry.EPHEMARITE_LOW_SLAB);
        blockItem(BlockRegistry.POLISHED_EPHEMARITE_STAIRS);
        blockItem(BlockRegistry.POLISHED_EPHEMARITE_LOW_STAIRS);
        blockItem(BlockRegistry.POLISHED_EPHEMARITE_SLAB);
        blockItem(BlockRegistry.POLISHED_EPHEMARITE_LOW_SLAB);

        stairsBlock((StairBlock) BlockRegistry.PICRITE_STAIRS.get(), blockTexture(BlockRegistry.PICRITE.get()));
        stairsBlock((StairBlock) BlockRegistry.POLISHED_PICRITE_STAIRS.get(), blockTexture(BlockRegistry.POLISHED_PICRITE.get()));
        slabBlock((SlabBlock) BlockRegistry.PICRITE_SLAB.get(), blockTexture(BlockRegistry.PICRITE.get()), blockTexture(BlockRegistry.PICRITE.get()));
        slabBlock((SlabBlock) BlockRegistry.POLISHED_PICRITE_SLAB.get(), blockTexture(BlockRegistry.POLISHED_PICRITE.get()), blockTexture(BlockRegistry.POLISHED_PICRITE.get()));

        wallBlock((WallBlock) BlockRegistry.PICRITE_WALL.get(), blockTexture(BlockRegistry.PICRITE.get()));
        wallBlock((WallBlock) BlockRegistry.POLISHED_PICRITE_WALL.get(), blockTexture(BlockRegistry.POLISHED_PICRITE.get()));
        wallBlock((WallBlock) BlockRegistry.EPHEMARITE_WALL.get(), blockTexture(BlockRegistry.EPHEMARITE.get()));
        wallBlock((WallBlock) BlockRegistry.EPHEMARITE_LOW_WALL.get(), blockTexture(BlockRegistry.EPHEMARITE_LOW.get()));

        blockItem(BlockRegistry.PICRITE_STAIRS);
        blockItem(BlockRegistry.POLISHED_PICRITE_STAIRS);
        blockItem(BlockRegistry.PICRITE_SLAB);
        blockItem(BlockRegistry.POLISHED_PICRITE_SLAB);
        blockItem(BlockRegistry.EPHEMARITE_SLAB);
        blockItem(BlockRegistry.EPHEMARITE_LOW_SLAB);
        blockItem(BlockRegistry.POLISHED_EPHEMARITE_SLAB);
        blockItem(BlockRegistry.POLISHED_EPHEMARITE_LOW_SLAB);
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
        simpleBlockItem(blockRegistryObject.get(), new ModelFile.UncheckedModelFile(Valoria.ID +
                ":block/" + ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath()));
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
}