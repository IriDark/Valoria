package com.idark.valoria.registries.block.types;

import net.minecraft.core.*;
import net.minecraft.server.level.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.context.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.*;
import net.minecraft.world.phys.shapes.*;

import javax.annotation.*;

public class SpikeBlock extends DirectionalBlock implements SimpleWaterloggedBlock{
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    private static final VoxelShape upAabb = Block.box(0, 0, 0, 16, 3, 16);
    private static final VoxelShape downAabb = Block.box(0, 13, 0, 16, 16, 16);
    private static final VoxelShape northAabb = Block.box(0, 0, 13, 16, 16, 16);
    private static final VoxelShape southAabb = Block.box(0, 0, 0, 16, 16, 3);
    private static final VoxelShape eastAabb = Block.box(0, 0, 0, 3, 16, 16);
    private static final VoxelShape westAabb = Block.box(13, 0, 0, 16, 16, 16);
    public static final IntegerProperty AGE = BlockStateProperties.AGE_3;

    public SpikeBlock(BlockBehaviour.Properties properties){
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED, Boolean.valueOf(false)).setValue(FACING, Direction.UP).setValue(AGE, 0));
    }

    public void entityInside(BlockState state, Level worldIn, BlockPos pos, Entity entityIn){
        entityIn.hurt(entityIn.damageSources().generic(), 1.5F);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context){
        Direction direction = (state.getValue(FACING));
        return switch(direction){
            case NORTH -> northAabb;
            case SOUTH -> southAabb;
            case EAST -> eastAabb;
            case WEST -> westAabb;
            case DOWN -> downAabb;
            default -> upAabb;
        };
    }

    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos){
        Direction direction = pState.getValue(FACING);
        BlockPos blockpos = pPos.relative(direction.getOpposite());
        return pLevel.getBlockState(blockpos).isFaceSturdy(pLevel, blockpos, direction);
    }

    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pNeighborPos){
        if(pState.getValue(WATERLOGGED)){
            pLevel.scheduleTick(pCurrentPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
        }

        return pDirection == pState.getValue(FACING).getOpposite() && !pState.canSurvive(pLevel, pCurrentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(pState, pDirection, pNeighborState, pLevel, pCurrentPos, pNeighborPos);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext pContext){
        LevelAccessor levelaccessor = pContext.getLevel();
        BlockPos blockpos = pContext.getClickedPos();
        return this.defaultBlockState().setValue(WATERLOGGED, Boolean.valueOf(levelaccessor.getFluidState(blockpos).getType() == Fluids.WATER)).setValue(FACING, pContext.getClickedFace()).setValue(AGE, 0);
    }

    public BlockState rotate(BlockState pState, Rotation pRotation){
        return pState.setValue(FACING, pRotation.rotate(pState.getValue(FACING)));
    }

    public BlockState mirror(BlockState pState, Mirror pMirror){
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    public FluidState getFluidState(BlockState pState){
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder){
        pBuilder.add(WATERLOGGED, FACING, AGE);
    }

    @Override
    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom){
        int i = pState.getValue(AGE);
        if(i < 3){
            pLevel.setBlock(pPos, pState.setValue(AGE, i + 1), 2);
            pLevel.scheduleTick(pPos, this, Mth.nextInt(pRandom, 10, 20));
        }else{
            pLevel.removeBlock(pPos, false);
        }
    }
}