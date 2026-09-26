package com.idark.valoria.registries.block.types;

import net.minecraft.core.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.util.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.gameevent.*;

public class UmbralActivatorBlock extends Block{
    public static final IntegerProperty AGE = BlockStateProperties.AGE_3;

    public UmbralActivatorBlock(Properties pProperties){
        super(pProperties);
        this.registerDefaultState(this.defaultBlockState().setValue(AGE, 0));
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving){
        if(level.hasNeighborSignal(pos) && !(state.getValue(AGE) > 0)){
            level.playSound(null, pos, SoundEvents.RESPAWN_ANCHOR_SET_SPAWN, SoundSource.BLOCKS, 0.2F, 1.2F);
            level.playSound(null, pos, SoundEvents.ITEM_FRAME_REMOVE_ITEM, SoundSource.BLOCKS, 0.4F, 1.0F);
            if(level.getBlockState(pos.above()).getBlock() instanceof UmbralBlock block){
                block.onActivation(level, pos.above(), level.getBlockState(pos.above()));
            }

            level.scheduleTick(pos, this, 0);
            level.gameEvent(null, GameEvent.BLOCK_ACTIVATE, pos);
        }
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder){
        pBuilder.add(AGE);
    }

    @Deprecated
    @Override
    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom){
        int i = pState.getValue(AGE);
        if(i < 3){
            pLevel.setBlock(pPos, pState.setValue(AGE, i + 1), 2);
            pLevel.scheduleTick(pPos, this, 100);
        }else{
            pLevel.setBlock(pPos, pState.setValue(AGE, 0), 2);
            pLevel.gameEvent(null, GameEvent.BLOCK_ACTIVATE, pPos);
        }
    }
}
