package com.idark.valoria.client.color;

import com.idark.valoria.registries.*;
import net.minecraft.client.color.block.*;
import net.minecraft.client.renderer.*;
import net.minecraft.core.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraftforge.api.distmarker.*;
import org.jetbrains.annotations.*;

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

            BlockRegistry.shadeLeaves.get(),
            BlockRegistry.shadeBranchVine.get(),
            BlockRegistry.shadeBranch.get(),
            BlockRegistry.shadeSapling.get(),
            BlockRegistry.pottedShadewoodSapling.get()
    };

    public static int brightenColor(int color, float factor) {
        int r = Math.min(255, (int)(((color >> 16) & 0xFF) * factor));
        int g = Math.min(255, (int)(((color >> 8) & 0xFF) * factor));
        int b = Math.min(255, (int)((color & 0xFF) * factor));
        return (r << 16) | (g << 8) | b;
    }

    public static ModBlockColors getInstance(){
        return INSTANCE;
    }

    public int getAloeColor(BlockState pState, @Nullable BlockAndTintGetter pLevel, @Nullable BlockPos pPos, int pTintIndex){
        return brightenColor(getGrassColor(pState, pLevel, pPos, pTintIndex), 1.125f);
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