package com.idark.valoria.core.datagen;

import com.idark.valoria.*;
import com.idark.valoria.compat.quark.*;
import com.idark.valoria.registries.*;
import net.minecraft.data.*;
import net.minecraft.resources.*;
import net.minecraft.world.level.block.*;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.*;
import net.minecraftforge.registries.*;

public class BlockStateGen extends BlockStateProvider{

    public BlockStateGen(PackOutput output, ExistingFileHelper exFileHelper){
        super(output, Valoria.ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels(){
        blockWithItem(BlockRegistry.DEEP_MARBLE);
        blockWithItem(BlockRegistry.POLISHED_DEEP_MARBLE);
        blockWithItem(BlockRegistry.PICRITE);
        blockWithItem(BlockRegistry.POLISHED_PICRITE);
        blockWithItem(BlockRegistry.EYE_STONE);
        blockWithItem(BlockRegistry.PYRATITE_ORE);

        paneBlockWithRenderType((IronBarsBlock)BlockRegistry.BRONZE_GLASS_PANE.get(), blockTexture(BlockRegistry.BRONZE_GLASS.get()), blockTexture(BlockRegistry.BRONZE_BLOCK.get()), "cutout");

        blockWithItem(BlockRegistry.ELDRITCH_LOG);
        blockWithItem(BlockRegistry.STRIPPED_ELDRITCH_LOG);
        blockWithItem(BlockRegistry.ELDRITCH_PLANKS);
        blockWithItem(BlockRegistry.ELDRITCH_LEAVES);
        doorBlock((DoorBlock)BlockRegistry.ELDRITCH_DOOR.get(), new ResourceLocation(Valoria.ID, ModelProvider.BLOCK_FOLDER + "/eldritch_door_bottom"), new ResourceLocation(Valoria.ID, ModelProvider.BLOCK_FOLDER + "/eldritch_door_top"));
        trapdoorBlock((TrapDoorBlock)BlockRegistry.ELDRITCH_TRAPDOOR.get(), blockTexture(BlockRegistry.ELDRITCH_TRAPDOOR.get()), true);

        signBlock((StandingSignBlock)BlockRegistry.ELDRITCH_SIGN.get(), (WallSignBlock)BlockRegistry.ELDRITCH_WALL_SIGN.get(), blockTexture(BlockRegistry.ELDRITCH_PLANKS.get()));
        hangingSignBlock(BlockRegistry.ELDRITCH_HANGING_SIGN.get(), BlockRegistry.ELDRITCH_WALL_HANGING_SIGN.get(), blockTexture(BlockRegistry.ELDRITCH_PLANKS.get()));

        buttonBlock((ButtonBlock)BlockRegistry.ELDRITCH_BUTTON.get(), blockTexture(BlockRegistry.ELDRITCH_PLANKS.get()));

        pressurePlateBlock((PressurePlateBlock)BlockRegistry.ELDRITCH_PRESSURE_PLATE.get(), blockTexture(BlockRegistry.ELDRITCH_PLANKS.get()));

        stairsBlock((StairBlock)BlockRegistry.ELDRITCH_PLANKS_STAIRS.get(), blockTexture(BlockRegistry.ELDRITCH_PLANKS.get()));
        slabBlock((SlabBlock)BlockRegistry.ELDRITCH_PLANKS_SLAB.get(), blockTexture(BlockRegistry.ELDRITCH_PLANKS.get()), blockTexture(BlockRegistry.ELDRITCH_PLANKS.get()));

        stairsBlock((StairBlock)BlockRegistry.DEEP_MARBLE_STAIRS.get(), blockTexture(BlockRegistry.DEEP_MARBLE.get()));
        stairsBlock((StairBlock)BlockRegistry.POLISHED_DEEP_MARBLE_STAIRS.get(), blockTexture(BlockRegistry.DEEP_MARBLE.get()));
        slabBlock((SlabBlock)BlockRegistry.DEEP_MARBLE_SLAB.get(), blockTexture(BlockRegistry.DEEP_MARBLE.get()), blockTexture(BlockRegistry.DEEP_MARBLE.get()));
        slabBlock((SlabBlock)BlockRegistry.POLISHED_DEEP_MARBLE_SLAB.get(), blockTexture(BlockRegistry.POLISHED_DEEP_MARBLE.get()), blockTexture(BlockRegistry.POLISHED_DEEP_MARBLE.get()));

        stairsBlock((StairBlock)BlockRegistry.EPHEMARITE_LOW_STAIRS.get(), blockTexture(BlockRegistry.EPHEMARITE_LOW.get()));
        stairsBlock((StairBlock)BlockRegistry.EPHEMARITE_STAIRS.get(), blockTexture(BlockRegistry.EPHEMARITE.get()));
        slabBlock((SlabBlock)BlockRegistry.EPHEMARITE_LOW_SLAB.get(), blockTexture(BlockRegistry.EPHEMARITE_LOW.get()), blockTexture(BlockRegistry.EPHEMARITE_LOW.get()));
        slabBlock((SlabBlock)BlockRegistry.EPHEMARITE_SLAB.get(), blockTexture(BlockRegistry.EPHEMARITE.get()), blockTexture(BlockRegistry.EPHEMARITE.get()));

        stairsBlock((StairBlock)BlockRegistry.POLISHED_EPHEMARITE_LOW_STAIRS.get(), blockTexture(BlockRegistry.POLISHED_EPHEMARITE_LOW.get()));
        stairsBlock((StairBlock)BlockRegistry.POLISHED_EPHEMARITE_STAIRS.get(), blockTexture(BlockRegistry.POLISHED_EPHEMARITE.get()));
        slabBlock((SlabBlock)BlockRegistry.POLISHED_EPHEMARITE_LOW_SLAB.get(), blockTexture(BlockRegistry.POLISHED_EPHEMARITE_LOW.get()), blockTexture(BlockRegistry.POLISHED_EPHEMARITE_LOW.get()));
        slabBlock((SlabBlock)BlockRegistry.POLISHED_EPHEMARITE_SLAB.get(), blockTexture(BlockRegistry.POLISHED_EPHEMARITE.get()), blockTexture(BlockRegistry.POLISHED_EPHEMARITE.get()));

        wallBlock((WallBlock)BlockRegistry.DEEP_MARBLE_WALL.get(), blockTexture(BlockRegistry.DEEP_MARBLE.get()));
        wallBlock((WallBlock)BlockRegistry.POLISHED_DEEP_MARBLE_WALL.get(), blockTexture(BlockRegistry.POLISHED_DEEP_MARBLE.get()));
        wallBlock((WallBlock)BlockRegistry.EPHEMARITE_WALL.get(), blockTexture(BlockRegistry.EPHEMARITE.get()));
        wallBlock((WallBlock)BlockRegistry.EPHEMARITE_LOW_WALL.get(), blockTexture(BlockRegistry.EPHEMARITE_LOW.get()));

        fenceBlock((FenceBlock)BlockRegistry.SHADEWOOD_FENCE.get(), blockTexture(BlockRegistry.SHADEWOOD_PLANKS.get()));
        fenceGateBlock((FenceGateBlock)BlockRegistry.SHADEWOOD_FENCE_GATE.get(), blockTexture(BlockRegistry.SHADEWOOD_PLANKS.get()));
        fenceBlock((FenceBlock)BlockRegistry.ELDRITCH_FENCE.get(), blockTexture(BlockRegistry.ELDRITCH_PLANKS.get()));
        fenceGateBlock((FenceGateBlock)BlockRegistry.ELDRITCH_FENCE_GATE.get(), blockTexture(BlockRegistry.ELDRITCH_PLANKS.get()));

        blockItem(BlockRegistry.DEEP_MARBLE_STAIRS);
        blockItem(BlockRegistry.POLISHED_DEEP_MARBLE_STAIRS);
        blockItem(BlockRegistry.DEEP_MARBLE_SLAB);
        blockItem(BlockRegistry.POLISHED_DEEP_MARBLE_SLAB);
        blockItem(BlockRegistry.EPHEMARITE_STAIRS);
        blockItem(BlockRegistry.EPHEMARITE_LOW_STAIRS);
        blockItem(BlockRegistry.EPHEMARITE_SLAB);
        blockItem(BlockRegistry.EPHEMARITE_LOW_SLAB);
        blockItem(BlockRegistry.POLISHED_EPHEMARITE_STAIRS);
        blockItem(BlockRegistry.POLISHED_EPHEMARITE_LOW_STAIRS);
        blockItem(BlockRegistry.POLISHED_EPHEMARITE_SLAB);
        blockItem(BlockRegistry.POLISHED_EPHEMARITE_LOW_SLAB);

        stairsBlock((StairBlock)BlockRegistry.PICRITE_STAIRS.get(), blockTexture(BlockRegistry.PICRITE.get()));
        stairsBlock((StairBlock)BlockRegistry.POLISHED_PICRITE_STAIRS.get(), blockTexture(BlockRegistry.POLISHED_PICRITE.get()));
        slabBlock((SlabBlock)BlockRegistry.PICRITE_SLAB.get(), blockTexture(BlockRegistry.PICRITE.get()), blockTexture(BlockRegistry.PICRITE.get()));
        slabBlock((SlabBlock)BlockRegistry.POLISHED_PICRITE_SLAB.get(), blockTexture(BlockRegistry.POLISHED_PICRITE.get()), blockTexture(BlockRegistry.POLISHED_PICRITE.get()));

        wallBlock((WallBlock)BlockRegistry.PICRITE_WALL.get(), blockTexture(BlockRegistry.PICRITE.get()));
        wallBlock((WallBlock)BlockRegistry.POLISHED_PICRITE_WALL.get(), blockTexture(BlockRegistry.POLISHED_PICRITE.get()));

        blockItem(BlockRegistry.PICRITE_STAIRS);
        blockItem(BlockRegistry.POLISHED_PICRITE_STAIRS);
        blockItem(BlockRegistry.PICRITE_SLAB);
        blockItem(BlockRegistry.POLISHED_PICRITE_SLAB);
        if(QuarkIntegration.isLoaded()){
            blockItem(BlockRegistry.EPHEMARITE_SLAB);
            blockItem(BlockRegistry.EPHEMARITE_LOW_SLAB);
            blockItem(BlockRegistry.POLISHED_EPHEMARITE_SLAB);
            blockItem(BlockRegistry.POLISHED_EPHEMARITE_LOW_SLAB);
            blockItem(QuarkIntegration.LoadedOnly.ELDRITCH_PLANKS_VERTICAL_SLAB);
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

    private void saplingBlock(RegistryObject<Block> blockRegistryObject){
        simpleBlock(blockRegistryObject.get(),
        models().cross(ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath(), blockTexture(blockRegistryObject.get())).renderType("cutout"));
    }

    public void hangingSignBlock(Block signBlock, Block wallSignBlock, ResourceLocation texture){
        ModelFile sign = models().sign(name(signBlock), texture);
        hangingSignBlock(signBlock, wallSignBlock, sign);
    }

    public void hangingSignBlock(Block signBlock, Block wallSignBlock, ModelFile sign){
        simpleBlock(signBlock, sign);
        simpleBlock(wallSignBlock, sign);
    }

    private String name(Block block){
        return key(block).getPath();
    }

    private ResourceLocation key(Block block){
        return ForgeRegistries.BLOCKS.getKey(block);
    }

    private void leavesBlock(RegistryObject<Block> blockRegistryObject){
        simpleBlockWithItem(blockRegistryObject.get(),
        models().singleTexture(ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath(), new ResourceLocation("minecraft:block/leaves"),
        "all", blockTexture(blockRegistryObject.get())).renderType("cutout"));
    }

    private void blockItem(RegistryObject<Block> blockRegistryObject){
        simpleBlockItem(blockRegistryObject.get(), new ModelFile.UncheckedModelFile(Valoria.ID +
        ":block/" + ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath()));
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject){
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
}