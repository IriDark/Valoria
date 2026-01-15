package com.idark.valoria.core.datagen;

import com.idark.valoria.*;
import com.idark.valoria.registries.*;
import net.minecraft.data.*;
import net.minecraft.resources.*;
import net.minecraft.world.level.block.*;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.*;

public class BlockStateGen extends CoreStateGen{

    public BlockStateGen(PackOutput output, ExistingFileHelper exFileHelper){
        super(output, Valoria.ID, exFileHelper);
    }

    public void registerKits(){
        registerKit(BlockRegistry.ancientStone.get(), BlockRegistry.ancientStoneStairs.get(), BlockRegistry.ancientStoneSlab.get(), BlockRegistry.ancientStoneWall.get());
        registerKit(BlockRegistry.polishedAncientStone.get(), BlockRegistry.polishedAncientStoneStairs.get(), BlockRegistry.polishedAncientStoneSlab.get(), BlockRegistry.polishedAncientStoneWall.get());
        registerKit(BlockRegistry.picrite.get(), BlockRegistry.picriteStairs.get(), BlockRegistry.picriteSlab.get(), BlockRegistry.picriteWall.get());
        registerKit(BlockRegistry.polishedPicrite.get(), BlockRegistry.polishedPicriteStairs.get(), BlockRegistry.polishedPicriteSlab.get(), BlockRegistry.polishedPicriteWall.get());
        registerKit(BlockRegistry.picriteBricks.get(), BlockRegistry.picriteBricksStairs.get(), BlockRegistry.picriteBricksSlab.get(), BlockRegistry.picriteBricksWall.get());
        registerKit(BlockRegistry.deepMarble.get(), BlockRegistry.deepMarbleStairs.get(), BlockRegistry.deepMarbleSlab.get(), BlockRegistry.deepMarbleWall.get());
        registerKit(BlockRegistry.polishedDeepMarble.get(), BlockRegistry.polishedDeepMarbleStairs.get(), BlockRegistry.polishedDeepMarbleSlab.get(), BlockRegistry.polishedDeepMarbleWall.get());
        registerKit(BlockRegistry.cobbledShale.get(), BlockRegistry.cobbledShaleStairs.get(), BlockRegistry.cobbledShaleSlab.get(), BlockRegistry.cobbledShaleWall.get());
        registerKit(BlockRegistry.cobbledShaleBricks.get(), BlockRegistry.cobbledShaleBricksStairs.get(), BlockRegistry.cobbledShaleBricksSlab.get(), BlockRegistry.cobbledShaleBricksWall.get());
        registerKit(BlockRegistry.crackedCobbledShaleBricks.get(), BlockRegistry.crackedCobbledShaleBricksStairs.get(), BlockRegistry.crackedCobbledShaleBricksSlab.get(), BlockRegistry.crackedCobbledShaleBricksWall.get());
        registerKit(BlockRegistry.polishedCobbledShale.get(), BlockRegistry.polishedCobbledShaleStairs.get(), BlockRegistry.polishedCobbledShaleSlab.get(), BlockRegistry.polishedCobbledShaleWall.get());
        registerKit(BlockRegistry.ephemarite.get(), BlockRegistry.ephemariteStairs.get(), BlockRegistry.ephemariteSlab.get(), BlockRegistry.ephemariteWall.get());
        registerKit(BlockRegistry.ephemariteLow.get(), BlockRegistry.ephemariteLowStairs.get(), BlockRegistry.ephemariteLowSlab.get(), BlockRegistry.ephemariteLowWall.get());
        registerKit(BlockRegistry.polishedEphemarite.get(), BlockRegistry.polishedEphemariteStairs.get(), BlockRegistry.polishedEphemariteSlab.get(), BlockRegistry.polishedEphemariteWall.get());
        registerKit(BlockRegistry.polishedEphemariteLow.get(), BlockRegistry.polishedEphemariteLowStairs.get(), BlockRegistry.polishedEphemariteLowSlab.get(), BlockRegistry.polishedEphemariteLowWall.get());

        registerKit(BlockRegistry.ash.get(), BlockRegistry.ashStairs.get(), BlockRegistry.ashSlab.get(), BlockRegistry.ashWall.get());
        registerKit(BlockRegistry.ashBricks.get(), BlockRegistry.ashBricksStairs.get(), BlockRegistry.ashBricksSlab.get(), BlockRegistry.ashBricksWall.get());
        blockWithItem(BlockRegistry.ashTiles.get());
        registerKit(BlockRegistry.polishedAsh.get(), BlockRegistry.polishedAshStairs.get(), BlockRegistry.polishedAshSlab.get(), BlockRegistry.polishedAshWall.get());
        registerKit(BlockRegistry.polishedAshBricks.get(), BlockRegistry.polishedAshBricksStairs.get(), BlockRegistry.polishedAshBricksSlab.get(), BlockRegistry.polishedAshBricksWall.get());
        blockWithItem(BlockRegistry.polishedAshTiles.get());
        registerKit(BlockRegistry.smoothAsh.get(), BlockRegistry.smoothAshStairs.get(), BlockRegistry.smoothAshSlab.get(), BlockRegistry.smoothAshWall.get());
    }

    @Override
    protected void registerStatesAndModels(){
        registerKits();
        blockWithItem(BlockRegistry.eyeStone);
        blockWithItem(BlockRegistry.pyratiteOre);

        paneBlockWithRenderType((IronBarsBlock)BlockRegistry.bronzeGlassPane.get(), blockTexture(BlockRegistry.bronzeGlass.get()), blockTexture(BlockRegistry.bronzeBlock.get()), "cutout");

        blockWithItem(BlockRegistry.eldritchLog);
        blockWithItem(BlockRegistry.strippedEldritchLog);
        blockWithItem(BlockRegistry.eldritchPlanks);
        blockWithItem(BlockRegistry.eldritchLeaves);
        doorBlock((DoorBlock)BlockRegistry.eldritchDoor.get(), new ResourceLocation(Valoria.ID, ModelProvider.BLOCK_FOLDER + "/eldritch_door_bottom"), new ResourceLocation(Valoria.ID, ModelProvider.BLOCK_FOLDER + "/eldritch_door_top"));
        trapdoorBlock((TrapDoorBlock)BlockRegistry.eldritchTrapdoor.get(), blockTexture(BlockRegistry.eldritchTrapdoor.get()), true);

        signBlock((StandingSignBlock)BlockRegistry.eldritchSign.get(), (WallSignBlock)BlockRegistry.eldritchWallSign.get(), blockTexture(BlockRegistry.eldritchPlanks.get()));
        hangingSignBlock(BlockRegistry.eldritchHangingSign.get(), BlockRegistry.eldritchWallHangingSign.get(), blockTexture(BlockRegistry.eldritchPlanks.get()));

        buttonBlock((ButtonBlock)BlockRegistry.eldritchButton.get(), blockTexture(BlockRegistry.eldritchPlanks.get()));

        pressurePlateBlock((PressurePlateBlock)BlockRegistry.eldritchPressurePlate.get(), blockTexture(BlockRegistry.eldritchPlanks.get()));

        stairsBlock((StairBlock)BlockRegistry.eldritchPlanksStairs.get(), blockTexture(BlockRegistry.eldritchPlanks.get()));
        slabBlock((SlabBlock)BlockRegistry.eldritchPlanksSlab.get(), blockTexture(BlockRegistry.eldritchPlanks.get()), blockTexture(BlockRegistry.eldritchPlanks.get()));

        fenceBlock((FenceBlock)BlockRegistry.shadeFence.get(), blockTexture(BlockRegistry.shadePlanks.get()));
        fenceGateBlock((FenceGateBlock)BlockRegistry.shadeFenceGate.get(), blockTexture(BlockRegistry.shadePlanks.get()));
        fenceBlock((FenceBlock)BlockRegistry.eldritchFence.get(), blockTexture(BlockRegistry.eldritchPlanks.get()));
        fenceGateBlock((FenceGateBlock)BlockRegistry.eldritchFenceGate.get(), blockTexture(BlockRegistry.eldritchPlanks.get()));
    }
}