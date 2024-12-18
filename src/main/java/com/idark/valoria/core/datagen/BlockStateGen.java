package com.idark.valoria.core.datagen;

import com.idark.valoria.*;
import com.idark.valoria.registries.*;
import net.minecraft.data.*;
import net.minecraft.resources.*;
import net.minecraft.world.level.block.*;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.*;
import net.minecraftforge.registries.*;

public class BlockStateGen extends BlockStateProvider {

    public BlockStateGen(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Valoria.ID, exFileHelper);
    }

    public void registerMarbles() {
        blockWithItem(BlockRegistry.deepMarble);
        blockWithItem(BlockRegistry.polishedDeepMarble);

        stairsBlock((StairBlock) BlockRegistry.deepMarbleStairs.get(), blockTexture(BlockRegistry.deepMarble.get()));
        stairsBlock((StairBlock) BlockRegistry.polishedDeepMarbleStairs.get(), blockTexture(BlockRegistry.polishedDeepMarble.get()));
        slabBlock((SlabBlock) BlockRegistry.deepMarbleSlab.get(), blockTexture(BlockRegistry.deepMarble.get()), blockTexture(BlockRegistry.deepMarble.get()));
        slabBlock((SlabBlock) BlockRegistry.polishedDeepMarbleSlab  .get(), blockTexture(BlockRegistry.polishedDeepMarble.get()), blockTexture(BlockRegistry.polishedDeepMarble.get()));

        wallBlock((WallBlock) BlockRegistry.deepMarbleWall.get(), blockTexture(BlockRegistry.deepMarble.get()));
        wallBlock((WallBlock) BlockRegistry.polishedAmbaneStoneWall.get(), blockTexture(BlockRegistry.polishedDeepMarble.get()));

        blockItem(BlockRegistry.deepMarbleStairs);
        blockItem(BlockRegistry.polishedDeepMarbleStairs);
        blockItem(BlockRegistry.deepMarbleSlab);
        blockItem(BlockRegistry.polishedDeepMarbleSlab);
    }

    public void registerCobbledShale() {
        blockWithItem(BlockRegistry.cobbledShale);
        blockWithItem(BlockRegistry.polishedCobbledShale);

        stairsBlock((StairBlock) BlockRegistry.cobbledShaleStairs.get(), blockTexture(BlockRegistry.cobbledShale.get()));
        stairsBlock((StairBlock) BlockRegistry.polishedCobbledShaleStairs.get(), blockTexture(BlockRegistry.polishedCobbledShale.get()));
        slabBlock((SlabBlock) BlockRegistry.cobbledShaleSlab.get(), blockTexture(BlockRegistry.cobbledShale.get()), blockTexture(BlockRegistry.deepMarble.get()));
        slabBlock((SlabBlock) BlockRegistry.polishedCobbledShaleSlab.get(), blockTexture(BlockRegistry.polishedCobbledShale.get()), blockTexture(BlockRegistry.polishedDeepMarble.get()));

        wallBlock((WallBlock) BlockRegistry.cobbledShaleWall.get(), blockTexture(BlockRegistry.cobbledShale.get()));
        wallBlock((WallBlock) BlockRegistry.polishedCobbledShaleWall.get(), blockTexture(BlockRegistry.polishedCobbledShale.get()));

        blockItem(BlockRegistry.cobbledShaleStairs);
        blockItem(BlockRegistry.polishedCobbledShaleStairs);
        blockItem(BlockRegistry.cobbledShaleSlab);
        blockItem(BlockRegistry.polishedCobbledShaleSlab);
    }

    @Override
    protected void registerStatesAndModels() {
        registerMarbles();
        registerCobbledShale();
        blockWithItem(BlockRegistry.picrite);
        blockWithItem(BlockRegistry.polishedPicrite);
        blockWithItem(BlockRegistry.eyeStone);
        blockWithItem(BlockRegistry.pyratiteOre);

        paneBlockWithRenderType((IronBarsBlock) BlockRegistry.bronzeGlassPane.get(), blockTexture(BlockRegistry.bronzeGlass.get()), blockTexture(BlockRegistry.bronzeBlock.get()), "cutout");

        blockWithItem(BlockRegistry.eldritchLog);
        blockWithItem(BlockRegistry.strippedEldritchLog);
        blockWithItem(BlockRegistry.eldritchPlanks);
        blockWithItem(BlockRegistry.eldritchLeaves);
        doorBlock((DoorBlock) BlockRegistry.eldritchDoor.get(), new ResourceLocation(Valoria.ID, ModelProvider.BLOCK_FOLDER + "/eldritch_door_bottom"), new ResourceLocation(Valoria.ID, ModelProvider.BLOCK_FOLDER + "/eldritch_door_top"));
        trapdoorBlock((TrapDoorBlock) BlockRegistry.eldritchTrapdoor.get(), blockTexture(BlockRegistry.eldritchTrapdoor.get()), true);

        signBlock((StandingSignBlock) BlockRegistry.eldritchSign.get(), (WallSignBlock) BlockRegistry.eldritchWallSign.get(), blockTexture(BlockRegistry.eldritchPlanks.get()));
        hangingSignBlock(BlockRegistry.eldritchHangingSign.get(), BlockRegistry.eldritchWallHangingSign.get(), blockTexture(BlockRegistry.eldritchPlanks.get()));

        buttonBlock((ButtonBlock) BlockRegistry.eldritchButton.get(), blockTexture(BlockRegistry.eldritchPlanks.get()));

        pressurePlateBlock((PressurePlateBlock) BlockRegistry.eldritchPressurePlate.get(), blockTexture(BlockRegistry.eldritchPlanks.get()));

        stairsBlock((StairBlock) BlockRegistry.eldritchPlanksStairs.get(), blockTexture(BlockRegistry.eldritchPlanks.get()));
        slabBlock((SlabBlock) BlockRegistry.eldritchPlanksSlab.get(), blockTexture(BlockRegistry.eldritchPlanks.get()), blockTexture(BlockRegistry.eldritchPlanks.get()));

        stairsBlock((StairBlock) BlockRegistry.ephemariteLowStairs.get(), blockTexture(BlockRegistry.ephemariteLow.get()));
        stairsBlock((StairBlock) BlockRegistry.ephemariteStairs.get(), blockTexture(BlockRegistry.ephemarite.get()));
        slabBlock((SlabBlock) BlockRegistry.ephemariteLowSlab.get(), blockTexture(BlockRegistry.ephemariteLow.get()), blockTexture(BlockRegistry.ephemariteLow.get()));
        slabBlock((SlabBlock) BlockRegistry.ephemariteSlab.get(), blockTexture(BlockRegistry.ephemarite.get()), blockTexture(BlockRegistry.ephemarite.get()));

        stairsBlock((StairBlock) BlockRegistry.polishedEphemariteLowStairs.get(), blockTexture(BlockRegistry.polishedEphemariteLow.get()));
        stairsBlock((StairBlock) BlockRegistry.polishedEphemariteStairs.get(), blockTexture(BlockRegistry.polishedEphemarite.get()));
        slabBlock((SlabBlock) BlockRegistry.polishedEphemariteLowSlab.get(), blockTexture(BlockRegistry.polishedEphemariteLow.get()), blockTexture(BlockRegistry.polishedEphemariteLow.get()));
        slabBlock((SlabBlock) BlockRegistry.polishedEphemariteSlab.get(), blockTexture(BlockRegistry.polishedEphemarite.get()), blockTexture(BlockRegistry.polishedEphemarite.get()));

        fenceBlock((FenceBlock) BlockRegistry.shadewoodFence.get(), blockTexture(BlockRegistry.shadewoodPlanks.get()));
        fenceGateBlock((FenceGateBlock) BlockRegistry.shadewoodFenceGate.get(), blockTexture(BlockRegistry.shadewoodPlanks.get()));
        fenceBlock((FenceBlock) BlockRegistry.eldritchFence.get(), blockTexture(BlockRegistry.eldritchPlanks.get()));
        fenceGateBlock((FenceGateBlock) BlockRegistry.eldritchFenceGate.get(), blockTexture(BlockRegistry.eldritchPlanks.get()));

        blockItem(BlockRegistry.ephemariteStairs);
        blockItem(BlockRegistry.ephemariteLowStairs);
        blockItem(BlockRegistry.ephemariteSlab);
        blockItem(BlockRegistry.ephemariteLowSlab);
        blockItem(BlockRegistry.polishedEphemariteStairs);
        blockItem(BlockRegistry.polishedEphemariteLowStairs);
        blockItem(BlockRegistry.polishedEphemariteSlab);
        blockItem(BlockRegistry.polishedEphemariteLowSlab);

        stairsBlock((StairBlock) BlockRegistry.picriteStairs.get(), blockTexture(BlockRegistry.picrite.get()));
        stairsBlock((StairBlock) BlockRegistry.polishedPicriteStairs.get(), blockTexture(BlockRegistry.polishedPicrite.get()));
        slabBlock((SlabBlock) BlockRegistry.picriteSlab.get(), blockTexture(BlockRegistry.picrite.get()), blockTexture(BlockRegistry.picrite.get()));
        slabBlock((SlabBlock) BlockRegistry.polishedPicriteSlab.get(), blockTexture(BlockRegistry.polishedPicrite.get()), blockTexture(BlockRegistry.polishedPicrite.get()));

        wallBlock((WallBlock) BlockRegistry.picriteWall.get(), blockTexture(BlockRegistry.picrite.get()));
        wallBlock((WallBlock) BlockRegistry.polishedPicriteWall.get(), blockTexture(BlockRegistry.polishedPicrite.get()));
        wallBlock((WallBlock) BlockRegistry.ephemariteWall.get(), blockTexture(BlockRegistry.ephemarite.get()));
        wallBlock((WallBlock) BlockRegistry.ephemariteLowWall.get(), blockTexture(BlockRegistry.ephemariteLow.get()));

        blockItem(BlockRegistry.picriteStairs);
        blockItem(BlockRegistry.polishedPicriteStairs);
        blockItem(BlockRegistry.picriteSlab);
        blockItem(BlockRegistry.polishedPicriteSlab);
        blockItem(BlockRegistry.ephemariteSlab);
        blockItem(BlockRegistry.ephemariteLowSlab);
        blockItem(BlockRegistry.polishedEphemariteSlab);
        blockItem(BlockRegistry.polishedEphemariteLowSlab);
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