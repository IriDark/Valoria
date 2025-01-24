package com.idark.valoria.registries.block.types;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CupBlock extends Block{
    public static final IntegerProperty CUPS = IntegerProperty.create("cups", 1, 2);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private static final VoxelShape TWO_AABB = Shapes.box(0.0625, 0, 0.0625, 0.875, 0.3125, 0.875);

    public CupBlock(Properties pProperties){
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(CUPS, 1).setValue(WATERLOGGED, false));
    }

    public VoxelShape makeShape(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.40625, 0, 0.3125, 0.71875, 0.3125, 0.625), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.28125, 0, 0.46875, 0.40625, 0.3125, 0.46875), BooleanOp.OR);

        return shape;
    }

    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit){
        if(pPlayer.getAbilities().mayBuild && pPlayer.getItemInHand(pHand).isEmpty()){
            return InteractionResult.sidedSuccess(pLevel.isClientSide);
        }else{
            return InteractionResult.PASS;
        }
    }

    public boolean canBeReplaced(BlockState pState, BlockPlaceContext pUseContext){
        return !pUseContext.isSecondaryUseActive() && pUseContext.getItemInHand().getItem() == this.asItem() && pState.getValue(CUPS) < 2 || super.canBeReplaced(pState, pUseContext);
    }

    public BlockState getStateForPlacement(BlockPlaceContext pContext){
        BlockState blockstate = pContext.getLevel().getBlockState(pContext.getClickedPos());
        if(blockstate.is(this)){
            return blockstate.cycle(CUPS);
        }else{
            FluidState fluidstate = pContext.getLevel().getFluidState(pContext.getClickedPos());
            boolean flag = fluidstate.getType() == Fluids.WATER;
            return super.getStateForPlacement(pContext).setValue(WATERLOGGED, Boolean.valueOf(flag));
        }
    }

    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pPos, BlockPos pNeighborPos){
        if(pState.getValue(WATERLOGGED)){
            pLevel.scheduleTick(pPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
        }

        return super.updateShape(pState, pDirection, pNeighborState, pLevel, pPos, pNeighborPos);
    }

    public FluidState getFluidState(BlockState pState){
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext){
        if(pState.getValue(CUPS) == 2){
            return TWO_AABB;
        }else{
            return makeShape();
        }
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder){
        pBuilder.add(CUPS, WATERLOGGED);
    }

    public boolean placeLiquid(LevelAccessor pLevel, BlockPos pPos, BlockState pState, FluidState pFluidState){
        if(!pState.getValue(WATERLOGGED) && pFluidState.getType() == Fluids.WATER){
            BlockState blockstate = pState.setValue(WATERLOGGED, true);
            pLevel.setBlock(pPos, blockstate, 3);
            pLevel.scheduleTick(pPos, pFluidState.getType(), pFluidState.getType().getTickDelay(pLevel));
            return true;
        }else{
            return false;
        }
    }

    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos){
        return Block.canSupportCenter(pLevel, pPos.below(), Direction.UP);
    }
}
