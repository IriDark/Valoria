package com.idark.valoria.registries.block.types;

import com.idark.valoria.client.ui.menus.*;
import net.minecraft.core.*;
import net.minecraft.network.chat.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
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
import net.minecraft.world.level.border.*;
import net.minecraft.world.level.material.*;
import net.minecraft.world.phys.*;
import net.minecraft.world.phys.shapes.*;
import net.minecraftforge.network.*;

import javax.annotation.*;

public class AlchemyStationBlock extends HorizontalDirectionalBlock implements SimpleWaterloggedBlock{
    public static final EnumProperty<WorkbenchPart> PART = EnumProperty.create("part", WorkbenchPart.class);
    private static final Component CONTAINER_TITLE = Component.translatable("menu.valoria.alchemy_station");
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public final int level;
    private final SoundEvent upgradeSound;

    public AlchemyStationBlock(int level, Properties pProperties){
        super(pProperties);
        this.level = level;
        this.upgradeSound = null;
        this.registerDefaultState(this.stateDefinition.any().setValue(PART, WorkbenchPart.BOTTOM_LEFT).setValue(WATERLOGGED, false));
    }

    public AlchemyStationBlock(SoundEvent upgradeSound, int level, Properties pProperties){
        super(pProperties);
        this.level = level;
        this.upgradeSound = upgradeSound;
        this.registerDefaultState(this.stateDefinition.any().setValue(PART, WorkbenchPart.BOTTOM_LEFT).setValue(WATERLOGGED, false));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder){
        pBuilder.add(WATERLOGGED, PART, FACING);
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState state){
        return PushReaction.BLOCK;
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext pContext){
        FluidState fluidstate = pContext.getLevel().getFluidState(pContext.getClickedPos());
        Direction direction = pContext.getHorizontalDirection().getOpposite();
        BlockPos pos = pContext.getClickedPos();
        WorldBorder border = pContext.getLevel().getWorldBorder();

        BlockPos pos1 = pos.relative(direction.getCounterClockWise()); // bottom_left
        BlockPos pos2 = pos.above(); // top_right
        BlockPos pos3 = pos.above().relative(direction.getCounterClockWise()); // top_left
        if (pContext.getLevel().getBlockState(pos1).canBeReplaced(pContext) && pContext.getLevel().getBlockState(pos2).canBeReplaced(pContext) && pContext.getLevel().getBlockState(pos3).canBeReplaced(pContext) && border.isWithinBounds(pos) && border.isWithinBounds(pos1) && border.isWithinBounds(pos2) && border.isWithinBounds(pos3)) {
            boolean flag = fluidstate.getType() == Fluids.WATER;
            return this.defaultBlockState().setValue(FACING, direction).setValue(PART, WorkbenchPart.BOTTOM_RIGHT).setValue(WATERLOGGED, flag);
        }

        return null;
    }

    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pPos, BlockPos pNeighborPos){
        if (pState.getValue(WATERLOGGED)) {
            pLevel.scheduleTick(pPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
        }

        Direction facing = pState.getValue(FACING);
        WorkbenchPart part = pState.getValue(PART);
        BlockPos mainPos = pPos;
        switch (part) {
            case TOP_RIGHT:
                mainPos = pPos.below();
                break;
            case BOTTOM_LEFT:
                mainPos = pPos.relative(facing.getClockWise());
                break;
            case TOP_LEFT:
                mainPos = pPos.relative(facing.getClockWise()).below();
                break;
            default:
                break;
        }

        BlockState mainState = pLevel.getBlockState(mainPos);
        if (!(mainState.getBlock() instanceof AlchemyStationBlock)) {
            return Blocks.AIR.defaultBlockState();
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

    public final VoxelShape makeShape(BlockState state){
        if (state.getValue(AlchemyStationBlock.PART) == WorkbenchPart.BOTTOM_LEFT || state.getValue(AlchemyStationBlock.PART) == WorkbenchPart.BOTTOM_RIGHT || level > 2) {
            return Shapes.block();
        }

        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0, 0, 0, 1, 0.375, 1), BooleanOp.OR);
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
            if (pPlayer instanceof ServerPlayer serverPlayer) {
                NetworkHooks.openScreen(serverPlayer, getMenuProvider(pState, pLevel, pPos), buf -> {
                    buf.writeBlockPos(pPos);
                    buf.writeInt(this.level);
                });
            }

            return InteractionResult.CONSUME;
        }
    }

    @Nullable
    public MenuProvider getMenuProvider(BlockState pState, Level pLevel, BlockPos pPos){
        return new SimpleMenuProvider((p_57074_, p_57075_, p_57076_) -> new AlchemyStationMenu(p_57074_, pPos, p_57075_, ContainerLevelAccess.create(pLevel, pPos), this.level), CONTAINER_TITLE);
    }

    public RenderShape getRenderShape(BlockState pState) {
        WorkbenchPart part = pState.getValue(PART);
        if (part == WorkbenchPart.TOP_LEFT || part == WorkbenchPart.TOP_RIGHT || part == WorkbenchPart.BOTTOM_LEFT) {
            return RenderShape.INVISIBLE;
        }

        return RenderShape.MODEL;
    }

    public void upgrade(BlockPos pPos, BlockState pState, Level pLevel, BlockState to) {
        WorkbenchPart part = pState.getValue(PART);
        Direction facing = pState.getValue(FACING);
        BlockPos mainPos = pPos;
        switch (part) {
            case TOP_RIGHT:
                mainPos = pPos.below();
                break;
            case BOTTOM_LEFT:
                mainPos = pPos.relative(facing.getClockWise());
                break;
            case TOP_LEFT:
                mainPos = pPos.relative(facing.getClockWise()).below();
                break;
            default:
                break;
        }

        BlockState mainState = pLevel.getBlockState(mainPos);
        Direction mainFacing = mainState.getValue(FACING);

        BlockPos bottomLeftPos = mainPos.relative(mainFacing.getCounterClockWise());
        BlockPos topRightPos = mainPos.above();
        BlockPos topLeftPos = mainPos.relative(mainFacing.getCounterClockWise()).above();

        boolean waterBottomRight = pLevel.getFluidState(mainPos).getType() == Fluids.WATER;
        boolean waterBottomLeft = pLevel.getFluidState(bottomLeftPos).getType() == Fluids.WATER;
        boolean waterTopRight = pLevel.getFluidState(topRightPos).getType() == Fluids.WATER;
        boolean waterTopLeft = pLevel.getFluidState(topLeftPos).getType() == Fluids.WATER;

        pLevel.setBlock(mainPos, to
        .setValue(FACING, mainFacing)
        .setValue(PART, WorkbenchPart.BOTTOM_RIGHT)
        .setValue(WATERLOGGED, waterBottomRight), 3);

        pLevel.setBlock(bottomLeftPos, to
        .setValue(FACING, mainFacing)
        .setValue(PART, WorkbenchPart.BOTTOM_LEFT)
        .setValue(WATERLOGGED, waterBottomLeft), 3);

        pLevel.setBlock(topRightPos, to
        .setValue(FACING, mainFacing)
        .setValue(PART, WorkbenchPart.TOP_RIGHT)
        .setValue(WATERLOGGED, waterTopRight), 3);

        pLevel.setBlock(topLeftPos, to
        .setValue(FACING, mainFacing)
        .setValue(PART, WorkbenchPart.TOP_LEFT)
        .setValue(WATERLOGGED, waterTopLeft), 3);

        if(to.getBlock() instanceof AlchemyStationBlock alchemyStationBlock) {
            if(alchemyStationBlock.getUpgradeSound() == null) return;
            pLevel.playSound(null, mainPos, alchemyStationBlock.getUpgradeSound(), SoundSource.BLOCKS);
        }
    }

    @Nullable
    public SoundEvent getUpgradeSound() {
        return upgradeSound;
    }

    public void playerWillDestroy(Level pLevel, BlockPos pPos, BlockState pState, Player pPlayer){
        if(!pLevel.isClientSide){
            WorkbenchPart part = pState.getValue(PART);
            Direction facing = pState.getValue(FACING);
            BlockPos mainPos = pPos;
            switch(part) {
                case TOP_RIGHT:
                    mainPos = pPos.below();
                    break;
                case BOTTOM_LEFT:
                    mainPos = pPos.relative(facing.getClockWise());
                    break;
                case TOP_LEFT:
                    mainPos = pPos.relative(facing.getClockWise()).below();
                    break;
                default:
                    break;
            }

            BlockState mainState = pLevel.getBlockState(mainPos);
            BlockState leftState = pLevel.getBlockState(mainPos.relative(facing.getCounterClockWise()));
            BlockState topState = pLevel.getBlockState(mainPos.above());
            BlockState topLeftState = pLevel.getBlockState(mainPos.relative(facing.getCounterClockWise()).above());
            if (mainState.getBlock() instanceof AlchemyStationBlock && mainPos != pPos) {
                pLevel.destroyBlock(mainPos, !pPlayer.isCreative());
            }

            if (leftState.getBlock() instanceof AlchemyStationBlock && mainPos.relative(facing.getCounterClockWise()) != pPos) {
                pLevel.destroyBlock(mainPos.relative(facing.getCounterClockWise()), !pPlayer.isCreative());
            }

            if (topState.getBlock() instanceof AlchemyStationBlock && mainPos.above() != pPos) {
                pLevel.destroyBlock(mainPos.above(), !pPlayer.isCreative());
            }

            if (topLeftState.getBlock() instanceof AlchemyStationBlock && mainPos.relative(facing.getCounterClockWise()).above() != pPos) {
                pLevel.destroyBlock(mainPos.relative(facing.getCounterClockWise()).above(), !pPlayer.isCreative());
            }
        }

        super.playerWillDestroy(pLevel, pPos, pState, pPlayer);
    }

    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack){
        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
        if(!pLevel.isClientSide){
            Direction direction = pState.getValue(FACING);
            BlockPos bottom_left_pos = pPos.relative(direction.getCounterClockWise());
            BlockPos top_right_pos = pPos.above();
            BlockPos top_left_pos = pPos.relative(direction.getCounterClockWise()).above();

            pLevel.setBlock(bottom_left_pos, pState.setValue(PART, WorkbenchPart.BOTTOM_LEFT).setValue(WATERLOGGED, pLevel.getFluidState(bottom_left_pos).getType() == Fluids.WATER), 3);
            pLevel.setBlock(top_right_pos, pState.setValue(PART, WorkbenchPart.TOP_RIGHT).setValue(WATERLOGGED, pLevel.getFluidState(top_right_pos).getType() == Fluids.WATER), 3);
            pLevel.setBlock(top_left_pos, pState.setValue(PART, WorkbenchPart.TOP_LEFT).setValue(WATERLOGGED, pLevel.getFluidState(top_left_pos).getType() == Fluids.WATER), 3);
        }
    }

    @Override
    public long getSeed(BlockState pState, BlockPos pPos){
        return pState.getValue(PART).ordinal() + (long)pPos.getX() + (long)pPos.getY() + (long)pPos.getZ();
    }
}