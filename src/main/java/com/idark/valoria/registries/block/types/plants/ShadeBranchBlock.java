package com.idark.valoria.registries.block.types.plants;

import com.idark.valoria.registries.*;
import net.minecraft.core.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.shapes.*;

public class ShadeBranchBlock extends BushBlock {

    public ShadeBranchBlock(BlockBehaviour.Properties p_153000_) {
        super(p_153000_);
    }

    private static final VoxelShape shape = Block.box(3, 4, 3, 15, 16, 15);

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return shape;
    }

    public float getMaxVerticalOffset() {
        return 0.0F;
    }

    public boolean mayPlaceOn(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return worldIn.getBlockState(pos.above()).is(BlockRegistry.SHADEWOOD_LEAVES.get());
    }

    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        return !pState.canSurvive(pLevel, pCurrentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
    }

    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        BlockPos blockpos = pPos.above();
        if (pState.getBlock() == this)
            return pLevel.getBlockState(blockpos).is(BlockRegistry.SHADEWOOD_LEAVES.get());
        return this.mayPlaceOn(pLevel.getBlockState(blockpos), pLevel, blockpos);
    }
}