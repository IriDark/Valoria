package com.idark.valoria.client.color;

import com.idark.valoria.registries.world.block.ModBlocks;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class ModBlockColors implements BlockColor {

    private static final ModBlockColors INSTANCE = new ModBlockColors();

    public static final Block[] MODDED_GRASS = {
            ModBlocks.CATTAIL.get()
    };

    public static final Block[] MODDED_FOLIAGE = {
            //
    };

    public static final Block[] QUARK = {
            //
    };

    public static ModBlockColors getInstance() {
        return INSTANCE;
    }

    public int getGrassColor(BlockState pState, @Nullable BlockAndTintGetter pLevel, @Nullable BlockPos pPos, int pTintIndex) {
        return pLevel != null && pPos != null ? BiomeColors.getAverageGrassColor(pLevel, pPos) : -1;
    }

    public int getFoliageColor(BlockState pState, @Nullable BlockAndTintGetter pLevel, @Nullable BlockPos pPos, int pTintIndex) {
        return pLevel != null && pPos != null ? BiomeColors.getAverageFoliageColor(pLevel, pPos) : FoliageColor.getDefaultColor();
    }

    @Override
    public int getColor(BlockState blockState, @Nullable BlockAndTintGetter blockAndTintGetter, @Nullable BlockPos blockPos, int i) {
        return 0;
    }
}