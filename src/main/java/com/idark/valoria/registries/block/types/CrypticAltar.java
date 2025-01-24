package com.idark.valoria.registries.block.types;

import com.idark.valoria.client.render.tile.TickableBlockEntity;
import com.idark.valoria.registries.ItemsRegistry;
import com.idark.valoria.registries.block.entity.CrypticAltarBlockEntity;
import com.idark.valoria.util.ValoriaUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CrypticAltar extends Block implements EntityBlock, SimpleWaterloggedBlock{
    public CrypticAltar(Properties pProperties){
        super(pProperties);
        registerDefaultState(defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, false));
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit){
        CrypticAltarBlockEntity tile = (CrypticAltarBlockEntity)world.getBlockEntity(pos);
        ItemStack stack = player.getItemInHand(hand).copy();
        if(!stack.isEmpty() && tile.getItemHandler().getItem(0).isEmpty() && stack.is(ItemsRegistry.necromancerGrimoire.get())){
            tile.getItemHandler().setItem(0, stack);
            tile.startSummoning();
            if(!player.isCreative()){
                player.getInventory().removeItem(player.getItemInHand(hand));
            }

            ValoriaUtils.tileEntity.SUpdateTileEntityPacket(tile);
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    @javax.annotation.Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type){
        return TickableBlockEntity.getTickerHelper();
    }

    public VoxelShape makeShape(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.3125, 0.1875, 0.3125, 0.6875, 0.375, 0.6875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.0625, 0.75, 0.0625, 0.9375, 1, 0.9375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.625, 0, 1, 0.75, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.375, 0, 1, 0.625, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.125, 0, 0.125, 0.875, 0.1875, 0.875), BooleanOp.OR);

        return shape;
    }

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext){
        return makeShape();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder){
        builder.add(BlockStateProperties.WATERLOGGED);
    }

    @Override
    public FluidState getFluidState(BlockState state){
        return state.getValue(BlockStateProperties.WATERLOGGED) ? Fluids.WATER.getSource(false) : Fluids.EMPTY.defaultFluidState();
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pNeighborPos){
        if(pState.getValue(BlockStateProperties.WATERLOGGED)){
            pLevel.scheduleTick(pCurrentPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
        }

        return super.updateShape(pState, pDirection, pNeighborState, pLevel, pCurrentPos, pNeighborPos);
    }

    @Override
    public boolean triggerEvent(BlockState state, Level world, BlockPos pos, int id, int param){
        super.triggerEvent(state, world, pos, id, param);
        BlockEntity tile = world.getBlockEntity(pos);
        return tile != null && tile.triggerEvent(id, param);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState){
        return new CrypticAltarBlockEntity(pPos, pState);
    }
}
