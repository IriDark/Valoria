package com.idark.valoria.registries.block.types;

import net.minecraft.core.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.context.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.*;
import net.minecraft.world.phys.*;
import net.minecraft.world.phys.shapes.*;

public class BottleBlock extends Block{
    public static final IntegerProperty BOTTLES = IntegerProperty.create("bottles", 1, 4);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private static final VoxelShape ONE_AABB = Shapes.box(0.40625, 0, 0.375, 0.65625, 0.625, 0.625);
    private static final VoxelShape TWO_AABB = Shapes.box(0.15625, 0, 0.375, 0.84375, 0.625, 0.8125);
    private static final VoxelShape THREE_AABB = Shapes.box(0.15625, 0, 0.171875, 0.84375, 0.625, 0.859375);
    private static final VoxelShape FOUR_AABB = Shapes.box(0, 0, 0.0625, 0.90625, 0.625, 0.984375);
    public BottleBlock(BlockBehaviour.Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(BOTTLES, 1).setValue(WATERLOGGED, false));
    }

    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pPlayer.getAbilities().mayBuild && pPlayer.getItemInHand(pHand).isEmpty()) {
            return InteractionResult.sidedSuccess(pLevel.isClientSide);
        } else {
            return InteractionResult.PASS;
        }
    }

    public boolean canBeReplaced(BlockState pState, BlockPlaceContext pUseContext) {
        return !pUseContext.isSecondaryUseActive() && pUseContext.getItemInHand().getItem() == this.asItem() && pState.getValue(BOTTLES) < 4 || super.canBeReplaced(pState, pUseContext);
    }

    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockState blockstate = pContext.getLevel().getBlockState(pContext.getClickedPos());
        if (blockstate.is(this)) {
            return blockstate.cycle(BOTTLES);
        } else {
            FluidState fluidstate = pContext.getLevel().getFluidState(pContext.getClickedPos());
            boolean flag = fluidstate.getType() == Fluids.WATER;
            return super.getStateForPlacement(pContext).setValue(WATERLOGGED, Boolean.valueOf(flag));
        }
    }

    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pPos, BlockPos pNeighborPos) {
        if (pState.getValue(WATERLOGGED)) {
            pLevel.scheduleTick(pPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
        }

        return super.updateShape(pState, pDirection, pNeighborState, pLevel, pPos, pNeighborPos);
    }

    public FluidState getFluidState(BlockState pState) {
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return switch(pState.getValue(BOTTLES)){
            default -> ONE_AABB;
            case 2 -> TWO_AABB;
            case 3 -> THREE_AABB;
            case 4 -> FOUR_AABB;
        };
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(BOTTLES, WATERLOGGED);
    }

    public boolean placeLiquid(LevelAccessor pLevel, BlockPos pPos, BlockState pState, FluidState pFluidState) {
        if (!pState.getValue(WATERLOGGED) && pFluidState.getType() == Fluids.WATER) {
            BlockState blockstate = pState.setValue(WATERLOGGED, true);
            pLevel.setBlock(pPos, blockstate, 3);
            pLevel.scheduleTick(pPos, pFluidState.getType(), pFluidState.getType().getTickDelay(pLevel));
            return true;
        } else {
            return false;
        }
    }

    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        return Block.canSupportCenter(pLevel, pPos.below(), Direction.UP);
    }
}
