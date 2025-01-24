package com.idark.valoria.client.color;

import com.idark.valoria.registries.BlockRegistry;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public class ModBlockColors implements BlockColor{
    private static final ModBlockColors INSTANCE = new ModBlockColors();

    public static final Block[] MODDED_GRASS = {
            BlockRegistry.cattail.get(),
            BlockRegistry.voidTaint.get(),
            BlockRegistry.voidGrass.get(),
            BlockRegistry.voidSerpents.get(),
            BlockRegistry.voidRoots.get(),
            BlockRegistry.soulFlower.get(),
            BlockRegistry.falseFlower.get(),
            BlockRegistry.falseFlowerSmall.get()
    };

    public static final Block[] MODDED_FOLIAGE = {
            BlockRegistry.eldritchLeaves.get(),
            BlockRegistry.eldritchSapling.get(),
            BlockRegistry.pottedEldritchSapling.get(),

            BlockRegistry.shadewoodLeaves.get(),
            BlockRegistry.shadewoodBranch.get(),
            BlockRegistry.shadewoodSapling.get(),
            BlockRegistry.pottedShadewoodSapling.get()
    };

    public static ModBlockColors getInstance(){
        return INSTANCE;
    }

    public int getGrassColor(BlockState pState, @Nullable BlockAndTintGetter pLevel, @Nullable BlockPos pPos, int pTintIndex){
        return pLevel != null && pPos != null ? BiomeColors.getAverageGrassColor(pLevel, pPos) : GrassColor.getDefaultColor();
    }

    public int getFoliageColor(BlockState pState, @Nullable BlockAndTintGetter pLevel, @Nullable BlockPos pPos, int pTintIndex){
        return pLevel != null && pPos != null ? BiomeColors.getAverageFoliageColor(pLevel, pPos) : FoliageColor.getDefaultColor();
    }

    // Just a placeholder
    @Override
    public int getColor(BlockState blockState, @Nullable BlockAndTintGetter blockAndTintGetter, @Nullable BlockPos blockPos, int i){
        return 0;
    }
}