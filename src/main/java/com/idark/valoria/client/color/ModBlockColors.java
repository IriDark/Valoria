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
public class ModBlockColors implements BlockColor {

    private static final ModBlockColors INSTANCE = new ModBlockColors();

    public static final Block[] MODDED_GRASS = {
            BlockRegistry.CATTAIL.get(),
            BlockRegistry.VOID_TAINT.get(),
            BlockRegistry.VOID_GRASS.get(),
            BlockRegistry.VOID_SERPENTS.get(),
            BlockRegistry.VOID_ROOTS.get(),
            BlockRegistry.SOULFLOWER.get(),
            BlockRegistry.FALSEFLOWER.get(),
            BlockRegistry.FALSEFLOWER_SMALL.get()
    };

    public static final Block[] MODDED_FOLIAGE = {
            BlockRegistry.SHADEWOOD_LEAVES.get(),
            BlockRegistry.SHADEWOOD_BRANCH.get(),
            BlockRegistry.SHADEWOOD_SAPLING.get()
    };

    public static ModBlockColors getInstance() {
        return INSTANCE;
    }

    public int getGrassColor(BlockState pState, @Nullable BlockAndTintGetter pLevel, @Nullable BlockPos pPos, int pTintIndex) {
        return pLevel != null && pPos != null ? BiomeColors.getAverageGrassColor(pLevel, pPos) : GrassColor.getDefaultColor();
    }

    public int getFoliageColor(BlockState pState, @Nullable BlockAndTintGetter pLevel, @Nullable BlockPos pPos, int pTintIndex) {
        return pLevel != null && pPos != null ? BiomeColors.getAverageFoliageColor(pLevel, pPos) : FoliageColor.getDefaultColor();
    }

    // Just a placeholder
    @Override
    public int getColor(BlockState blockState, @Nullable BlockAndTintGetter blockAndTintGetter, @Nullable BlockPos blockPos, int i) {
        return 0;
    }
}