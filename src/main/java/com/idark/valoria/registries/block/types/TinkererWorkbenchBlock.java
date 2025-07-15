package com.idark.valoria.registries.block.types;

import com.idark.valoria.client.ui.menus.*;
import net.minecraft.core.*;
import net.minecraft.network.chat.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.*;
import net.minecraft.world.phys.*;
import net.minecraft.world.phys.shapes.*;

import javax.annotation.*;

public class TinkererWorkbenchBlock extends HorizontalDirectionalBlock implements SimpleWaterloggedBlock{
    public static final EnumProperty<BedPart> PART = BlockStateProperties.BED_PART;
    private static final Component CONTAINER_TITLE = Component.translatable("menu.valoria.tinkerer_workbench");
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public TinkererWorkbenchBlock(Properties pProperties){
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(PART, BedPart.FOOT).setValue(WATERLOGGED, false));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder){
        pBuilder.add(WATERLOGGED, PART, FACING);
    }

    public BlockState getStateForPlacement(BlockPlaceContext pContext){
        FluidState fluidstate = pContext.getLevel().getFluidState(pContext.getClickedPos());
        Direction direction = pContext.getHorizontalDirection().getClockWise();
        BlockPos pos = pContext.getClickedPos();
        BlockPos relativePos = pos.relative(direction);
        Level level = pContext.getLevel();
        boolean flag = fluidstate.getType() == Fluids.WATER;
        return level.getBlockState(relativePos).canBeReplaced(pContext) && level.getWorldBorder().isWithinBounds(relativePos) ? this.defaultBlockState().setValue(FACING, direction).setValue(WATERLOGGED, flag) : null;

    }

    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pPos, BlockPos pNeighborPos){
        if(pDirection == getNeighbourDirection(pState.getValue(PART), pState.getValue(FACING))){
            return pNeighborState.is(this) && pNeighborState.getValue(PART) != pState.getValue(PART) ? pState : Blocks.AIR.defaultBlockState();
        } else if(pState.getValue(WATERLOGGED)){
            pLevel.scheduleTick(pPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
        }

        return super.updateShape(pState, pDirection, pNeighborState, pLevel, pPos, pNeighborPos);
    }

    public FluidState getFluidState(BlockState pState){
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
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

    public VoxelShape makeShape(BlockState state){
        //I hate voxel shapes, the worst thing ive seen...
        Direction direction = (state.getValue(FACING));
        VoxelShape shape = Shapes.empty();
        if(state.getValue(PART) == BedPart.FOOT){
            switch(direction){
                case WEST -> {
                    shape = Shapes.join(shape, Shapes.box(0.8125, 0, 0.0625, 0.9375, 0.75, 0.1875), BooleanOp.OR);
                    shape = Shapes.join(shape, Shapes.box(0, 0.5, 0.0625, 0.8125, 0.625, 0.1875), BooleanOp.OR);
                    shape = Shapes.join(shape, Shapes.box(0, 0.5, 0.8125, 0.8125, 0.625, 0.9375), BooleanOp.OR);
                    shape = Shapes.join(shape, Shapes.box(0.8125, 0.5, 0.1875, 0.9375, 0.625, 0.8125), BooleanOp.OR);
                    shape = Shapes.join(shape, Shapes.box(0.8125, 0, 0.8125, 0.9375, 0.75, 0.9375), BooleanOp.OR);
                    shape = Shapes.join(shape, Shapes.box(0, 0.75, 0, 1, 1, 1), BooleanOp.OR);
                }

                case EAST -> {
                    shape = Shapes.join(shape, Shapes.box(0.0625, 0, 0.8125, 0.1875, 0.75, 0.9375), BooleanOp.OR);
                    shape = Shapes.join(shape, Shapes.box(0.1875, 0.5, 0.8125, 1, 0.625, 0.9375), BooleanOp.OR);
                    shape = Shapes.join(shape, Shapes.box(0.1875, 0.5, 0.0625, 1, 0.625, 0.1875), BooleanOp.OR);
                    shape = Shapes.join(shape, Shapes.box(0.0625, 0.5, 0.1875, 0.1875, 0.625, 0.8125), BooleanOp.OR);
                    shape = Shapes.join(shape, Shapes.box(0.0625, 0, 0.0625, 0.1875, 0.75, 0.1875), BooleanOp.OR);
                    shape = Shapes.join(shape, Shapes.box(0, 0.75, 0, 1, 1, 1), BooleanOp.OR);
                }

                case NORTH -> {
                    shape = Shapes.join(shape, Shapes.box(0.8125, 0, 0.8125, 0.9375, 0.75, 0.9375), BooleanOp.OR);
                    shape = Shapes.join(shape, Shapes.box(0.8125, 0.5, 0, 0.9375, 0.625, 0.8125), BooleanOp.OR);
                    shape = Shapes.join(shape, Shapes.box(0.0625, 0.5, 0, 0.1875, 0.625, 0.8125), BooleanOp.OR);
                    shape = Shapes.join(shape, Shapes.box(0.1875, 0.5, 0.8125, 0.8125, 0.625, 0.9375), BooleanOp.OR);
                    shape = Shapes.join(shape, Shapes.box(0.0625, 0, 0.8125, 0.1875, 0.75, 0.9375), BooleanOp.OR);
                    shape = Shapes.join(shape, Shapes.box(0, 0.75, 0, 1, 1, 1), BooleanOp.OR);
                }

                case SOUTH -> {
                    shape = Shapes.join(shape, Shapes.box(0.0625, 0, 0.0625, 0.1875, 0.75, 0.1875), BooleanOp.OR);
                    shape = Shapes.join(shape, Shapes.box(0.0625, 0.5, 0.1875, 0.1875, 0.625, 1), BooleanOp.OR);
                    shape = Shapes.join(shape, Shapes.box(0.8125, 0.5, 0.1875, 0.9375, 0.625, 1), BooleanOp.OR);
                    shape = Shapes.join(shape, Shapes.box(0.1875, 0.5, 0.0625, 0.8125, 0.625, 0.1875), BooleanOp.OR);
                    shape = Shapes.join(shape, Shapes.box(0.8125, 0, 0.0625, 0.9375, 0.75, 0.1875), BooleanOp.OR);
                    shape = Shapes.join(shape, Shapes.box(0, 0.75, 0, 1, 1, 1), BooleanOp.OR);
                }
            }
        }

        if(state.getValue(PART) == BedPart.HEAD){
            switch(direction){
                case WEST -> {
                    shape = Shapes.join(shape, Shapes.box(0, 0.75, 0, 1, 1, 1), BooleanOp.OR);
                    shape = Shapes.join(shape, Shapes.box(0.0625, 0, 0.0625, 1, 0.75, 0.9375), BooleanOp.OR);
                }
                case EAST -> {
                    shape = Shapes.join(shape, Shapes.box(0, 0.75, 0, 1, 1, 1), BooleanOp.OR);
                    shape = Shapes.join(shape, Shapes.box(0, 0, 0.0625, 0.9375, 0.75, 0.9375), BooleanOp.OR);
                }

                case NORTH -> {
                    shape = Shapes.join(shape, Shapes.box(0, 0.75, 0, 1, 1, 1), BooleanOp.OR);
                    shape = Shapes.join(shape, Shapes.box(0.0625, 0, 0.0625, 0.9375, 0.75, 1), BooleanOp.OR);
                }

                case SOUTH -> {
                    shape = Shapes.join(shape, Shapes.box(0, 0.75, 0, 1, 1, 1), BooleanOp.OR);
                    shape = Shapes.join(shape, Shapes.box(0.0625, 0, 0, 0.9375, 0.75, 0.9375), BooleanOp.OR);
                }
            }
        }

        return shape;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context){
        return makeShape(state);
    }

    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit){
        if(pLevel.isClientSide){
            return InteractionResult.SUCCESS;
        }else{
            pPlayer.openMenu(pState.getMenuProvider(pLevel, pPos));
            return InteractionResult.CONSUME;
        }
    }

    @Nullable
    public MenuProvider getMenuProvider(BlockState pState, Level pLevel, BlockPos pPos){
        return new SimpleMenuProvider((p_57074_, p_57075_, p_57076_) -> new TinkererMenu(p_57074_, p_57075_, ContainerLevelAccess.create(pLevel, pPos)), CONTAINER_TITLE);
    }

    private static Direction getNeighbourDirection(BedPart pPart, Direction pDirection){
        return pPart == BedPart.FOOT ? pDirection : pDirection.getOpposite();
    }

    public RenderShape getRenderShape(BlockState pState){
        return RenderShape.MODEL;
    }

    public void playerWillDestroy(Level pLevel, BlockPos pPos, BlockState pState, Player pPlayer){
        if(!pLevel.isClientSide && pPlayer.isCreative()){
            BedPart part = pState.getValue(PART);
            if(part == BedPart.FOOT){
                BlockPos relativePos = pPos.relative(getNeighbourDirection(part, pState.getValue(FACING)));
                BlockState state = pLevel.getBlockState(relativePos);
                if(state.is(this) && state.getValue(PART) == BedPart.HEAD){
                    pLevel.setBlock(relativePos, Blocks.AIR.defaultBlockState(), 35);
                    pLevel.levelEvent(pPlayer, 2001, relativePos, Block.getId(state));
                }
            }
        }

        super.playerWillDestroy(pLevel, pPos, pState, pPlayer);
    }

    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack){
        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
        if(!pLevel.isClientSide){
            BlockPos relativePos = pPos.relative(pState.getValue(FACING));
            pLevel.setBlock(relativePos, pState.setValue(PART, BedPart.HEAD), 3);
            pLevel.blockUpdated(pPos, Blocks.AIR);
            pState.updateNeighbourShapes(pLevel, pPos, 3);
        }
    }

    public long getSeed(BlockState pState, BlockPos pPos){
        BlockPos relativePos = pPos.relative(pState.getValue(FACING), pState.getValue(PART) == BedPart.HEAD ? 0 : 1);
        return Mth.getSeed(relativePos.getX(), pPos.getY(), relativePos.getZ());
    }
}
