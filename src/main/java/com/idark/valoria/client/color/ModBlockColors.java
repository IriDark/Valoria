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
    BlockRegistry.ELDRITCH_LEAVES.get(),
    BlockRegistry.ELDRITCH_SAPLING.get(),
    BlockRegistry.POTTED_SHADEWOOD_SAPLING.get(),
    BlockRegistry.POTTED_ELDRITCH_SAPLING.get(),

    BlockRegistry.SHADEWOOD_LEAVES.get(),
    BlockRegistry.SHADEWOOD_BRANCH.get(),
    BlockRegistry.SHADEWOOD_SAPLING.get()
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