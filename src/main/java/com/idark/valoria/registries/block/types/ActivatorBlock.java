package com.idark.valoria.registries.block.types;

import com.idark.valoria.core.network.PacketHandler;
import com.idark.valoria.core.network.packets.KeypadParticlePacket;
import com.idark.valoria.registries.TagsRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

public class ActivatorBlock extends Block {
    public BlockState state;
    public ActivatorBlock(BlockState pTurnsTo, Properties pProperties) {
        super(pProperties);
        this.state = pTurnsTo;
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        if (level.hasNeighborSignal(pos)) {
            level.playSound(null, pos, SoundEvents.RESPAWN_ANCHOR_SET_SPAWN, SoundSource.BLOCKS, 0.2F, 1.2F);
            level.playSound(null, pos, SoundEvents.ITEM_FRAME_REMOVE_ITEM, SoundSource.BLOCKS, 0.4F, 1.0F);
            for (int i = 0; i <= 3; i++) {
                BlockPos targetPos = pos.above().offset(0, i, 0);
                BlockState targetState = level.getBlockState(targetPos);
                if (targetState.is(TagsRegistry.KEY_BLOCKS)) {
                    level.setBlockAndUpdate(targetPos, Blocks.AIR.defaultBlockState());
                    if (level instanceof ServerLevel p) {
                        PacketHandler.sendToTracking(p, pos, new KeypadParticlePacket(pos.getX(), pos.getY(), pos.getZ(), targetPos.getX(), targetPos.getY(), targetPos.getZ()));
                    }
                }
            }

            level.gameEvent(null, GameEvent.BLOCK_ACTIVATE, pos);
            level.setBlockAndUpdate(pos, this.state);
        }
    }
}
