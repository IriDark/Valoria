package com.idark.valoria.registries.block.types.plants;

import com.idark.valoria.registries.*;
import net.minecraft.core.*;
import net.minecraft.server.level.*;
import net.minecraft.tags.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.*;

import javax.annotation.*;

public class TallWaterFlowerBlock extends DoublePlantBlock implements SimpleWaterloggedBlock, net.minecraftforge.common.IPlantable {

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public TallWaterFlowerBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED, true).setValue(HALF, DoubleBlockHalf.LOWER));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HALF, WATERLOGGED);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        FluidState fluid = level.getFluidState(pos);
        BlockPos floorPos = pos.below();
        if (state.getValue(DoublePlantBlock.HALF) == DoubleBlockHalf.LOWER) {
            return super.canSurvive(state, level, pos) && this.mayPlaceOn(level.getBlockState(floorPos), level, floorPos) && fluid.is(FluidTags.WATER) && fluid.getAmount() == 8;
        }
        return super.canSurvive(state, level, pos) && level.getBlockState(pos.below()).getBlock() == BlockRegistry.CATTAIL.get();
    }


    public void tick(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource rand) {
        if (!state.canSurvive(worldIn, pos)) {
            worldIn.destroyBlock(pos, true);
        }
    }

    protected boolean mayPlaceOn(BlockState state, BlockGetter worldIn, BlockPos pos) {
        Block block = state.getBlock();
        return block == BlockRegistry.VOID_TAINT.get() || block == BlockRegistry.VOID_GRASS.get() || block == Blocks.GRASS_BLOCK || block == Blocks.DIRT || block == Blocks.SAND || block == Blocks.GRAVEL || block == Blocks.PODZOL;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        level.setBlock(pos.above(), this.defaultBlockState().setValue(WATERLOGGED, false).setValue(HALF, DoubleBlockHalf.UPPER), 3);
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
        BlockState currentState = super.updateShape(stateIn, facing, facingState, level, currentPos, facingPos);
        DoubleBlockHalf half = stateIn.getValue(HALF);
        if (!currentState.isAir()) {
            level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        if (facing.getAxis() != Direction.Axis.Y || half == DoubleBlockHalf.LOWER != (facing == Direction.UP) || facingState.getBlock() == this && facingState.getValue(HALF) != half) {
            return half == DoubleBlockHalf.LOWER && facing == Direction.DOWN && !stateIn.canSurvive(level, currentPos) ? Blocks.AIR.defaultBlockState() : stateIn;
        } else {
            return Blocks.AIR.defaultBlockState();
        }
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(HALF) == DoubleBlockHalf.LOWER ? Fluids.WATER.getSource(false) : Fluids.EMPTY.defaultFluidState();
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos pos = context.getClickedPos();
        FluidState fluid = context.getLevel().getFluidState(context.getClickedPos());
        return pos.getY() < context.getLevel().getMaxBuildHeight() - 1 && fluid.is(FluidTags.WATER) && fluid.getAmount() == 8 && context.getLevel().getBlockState(pos.above()).isAir() ? super.getStateForPlacement(context) : null;
    }

    @Override
    public boolean canPlaceLiquid(BlockGetter level, BlockPos pos, BlockState state, Fluid fluidIn) {
        return state.getValue(HALF) == DoubleBlockHalf.LOWER;
    }


    @Override
    public net.minecraftforge.common.PlantType getPlantType(BlockGetter world, BlockPos pos) {
        return net.minecraftforge.common.PlantType.BEACH;
    }

    public boolean canBeReplaced(BlockState state, BlockPlaceContext useContext) {
        return false;
    }

    @Override
    public BlockState getPlant(BlockGetter world, BlockPos pos) {
        return defaultBlockState();
    }
}