package com.idark.valoria.registries.block.types;

import com.idark.valoria.registries.BlockRegistry;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

public class AbyssalGlowFernBlock extends GrowingPlantHeadBlock {
    protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 9.0D, 16.0D);
    public AbyssalGlowFernBlock(BlockBehaviour.Properties p_54300_) {
        super(p_54300_, Direction.UP, SHAPE, true, 0.14D);
    }

    @Override
    protected int getBlocksToGrowWhenBonemealed(RandomSource pRandom) {
        return 1;
    }

    @Override
    protected boolean canGrowInto(BlockState pState) {
        return pState.isAir();
    }

    @Override
    protected Block getBodyBlock() {
        return BlockRegistry.ABYSSAL_GLOWFERN_PLANT.get();
    }
}
