package com.idark.valoria.registries.block.types.altars;

import com.idark.valoria.client.render.tile.*;
import com.idark.valoria.registries.block.entity.*;
import com.idark.valoria.util.*;
import net.minecraft.core.*;
import net.minecraft.sounds.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.*;
import net.minecraft.world.phys.*;
import org.jetbrains.annotations.*;

public abstract class AbstractBossAltar extends Block implements EntityBlock, SimpleWaterloggedBlock{

    public AbstractBossAltar(Properties pProperties){
        super(pProperties);
    }

    public abstract BlockEntity newBlockEntity(BlockPos pPos, BlockState pState);

    public abstract Item getSummonItem();

    @javax.annotation.Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type){
        return TickableBlockEntity.getTickerHelper();
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

    public abstract SoundEvent getSummonSound();

    @Override
    public boolean triggerEvent(BlockState state, Level world, BlockPos pos, int id, int param){
        super.triggerEvent(state, world, pos, id, param);
        BlockEntity tile = world.getBlockEntity(pos);
        return tile != null && tile.triggerEvent(id, param);
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit){
        AbstractAltarBlockEntity tile = (AbstractAltarBlockEntity) world.getBlockEntity(pos);
        ItemStack stack = player.getItemInHand(hand).copy();
        if(!stack.isEmpty() && tile.getItemHandler().getItem(0).isEmpty() && stack.is(this.getSummonItem())){
            tile.getItemHandler().setItem(0, stack);
            tile.startSummoning();
            world.playSound(null, pos, getSummonSound(), SoundSource.PLAYERS, 10, 1);
            if(!player.isCreative()){
                player.getItemInHand(hand).shrink(1);
            }

            ValoriaUtils.SUpdateTileEntityPacket(tile);
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

}
