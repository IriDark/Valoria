package com.idark.darkrpg.block.types;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoubleBlockCombiner;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.Random;

public class SarcoBlock extends HorizontalDirectionalBlock {

    public static final EnumProperty<BedPart> PART = BlockStateProperties.BED_PART;
    private static IntegerProperty STATE = IntegerProperty.create("open", 0, 1);

    private static final VoxelShape shape = Block.box(0, 0, 0, 16, 12, 16);
    Random rand = new Random();

    public SarcoBlock(BlockBehaviour.Properties pProperties) {
        super(pProperties);
        this.registerDefaultState((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(PART, BedPart.FOOT).setValue(STATE, 0)));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return shape;
    }

    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        BedPart part = pState.getValue(PART);
        BedPart footPart = (part == BedPart.FOOT) ? BedPart.HEAD : BedPart.FOOT;
        BedPart headPart = (part == BedPart.HEAD) ? BedPart.HEAD : BedPart.FOOT;
        pLevel.setBlockAndUpdate(pPos, pState.setValue(STATE, 1));

        BlockPos oppositePos = pPos.relative(pState.getValue(FACING));
        if (pState.getValue(PART) == BedPart.HEAD) {
            oppositePos = pPos.relative(pState.getValue(FACING).getOpposite());
        }
        BlockState oppositeState = pLevel.getBlockState(oppositePos);
        if (oppositeState.getBlock() == this) {
            pLevel.setBlockAndUpdate(oppositePos, oppositeState.setValue(STATE, 1));
        }

        return InteractionResult.SUCCESS;
    }

    @Nullable
    public static Direction getOrientation(BlockGetter pLevel, BlockPos pPos) {
        BlockState $$2 = pLevel.getBlockState(pPos);
        return $$2.getBlock() instanceof SarcoBlock ? (Direction)$$2.getValue(FACING) : null;
    }

    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        if (pFacing == getNeighbourDirection((BedPart)pState.getValue(PART), (Direction)pState.getValue(FACING))) {
            return pFacingState.is(this) && pFacingState.getValue(PART) != pState.getValue(PART) ? pState : Blocks.AIR.defaultBlockState();
        } else {
            return super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
        }
    }

    private static Direction getNeighbourDirection(BedPart pPart, Direction pDirection) {
        return pPart == BedPart.FOOT ? pDirection : pDirection.getOpposite();
    }

    public void playerWillDestroy(Level pLevel, BlockPos pPos, BlockState pState, Player pPlayer) {
        if (!pLevel.isClientSide && pPlayer.isCreative()) {
            BedPart $$4 = (BedPart)pState.getValue(PART);
            if ($$4 == BedPart.FOOT) {
                BlockPos $$5 = pPos.relative(getNeighbourDirection($$4, (Direction)pState.getValue(FACING)));
                BlockState $$6 = pLevel.getBlockState($$5);
                if ($$6.is(this) && $$6.getValue(PART) == BedPart.HEAD) {
                    pLevel.setBlock($$5, Blocks.AIR.defaultBlockState(), 35);
                    pLevel.levelEvent(pPlayer, 2001, $$5, Block.getId($$6));
                }
            }

        }

        super.playerWillDestroy(pLevel, pPos, pState, pPlayer);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        Direction $$1 = pContext.getHorizontalDirection();
        BlockPos $$2 = pContext.getClickedPos();
        BlockPos $$3 = $$2.relative($$1);
        Level $$4 = pContext.getLevel();
        return $$4.getBlockState($$3).canBeReplaced(pContext) && $$4.getWorldBorder().isWithinBounds($$3) ? (BlockState)this.defaultBlockState().setValue(FACING, $$1) : null;
    }

    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
        if (!pLevel.isClientSide) {
            BlockPos $$5 = pPos.relative((Direction)pState.getValue(FACING));
            pLevel.setBlock($$5, (BlockState)pState.setValue(PART, BedPart.HEAD), 3);
            pLevel.blockUpdated(pPos, Blocks.AIR);
            pState.updateNeighbourShapes(pLevel, pPos, 3);
        }
    }

    public static Direction getConnectedDirection(BlockState pState) {
        Direction $$1 = (Direction)pState.getValue(FACING);
        return pState.getValue(PART) == BedPart.HEAD ? $$1.getOpposite() : $$1;
    }

    private static boolean isBunkBed(BlockGetter pLevel, BlockPos pPos) {
        return pLevel.getBlockState(pPos.below()).getBlock() instanceof SarcoBlock;
    }

    public static DoubleBlockCombiner.BlockType getBlockType(BlockState pState) {
        BedPart $$1 = (BedPart)pState.getValue(PART);
        return $$1 == BedPart.HEAD ? DoubleBlockCombiner.BlockType.FIRST : DoubleBlockCombiner.BlockType.SECOND;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
        builder.add(PART);
        builder.add(STATE);
        super.createBlockStateDefinition(builder);
    }

    public long getSeed(BlockState pState, BlockPos pPos) {
        BlockPos $$2 = pPos.relative((Direction)pState.getValue(FACING), pState.getValue(PART) == BedPart.HEAD ? 0 : 1);
        return Mth.getSeed($$2.getX(), pPos.getY(), $$2.getZ());
    }

    public boolean isPathfindable(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
        return false;
    }
}
