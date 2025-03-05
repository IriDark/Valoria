package com.idark.valoria.registries.block.types;

import com.idark.valoria.registries.*;
import net.minecraft.core.*;
import net.minecraft.core.particles.*;
import net.minecraft.sounds.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.context.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.gameevent.*;
import net.minecraft.world.phys.shapes.*;

import javax.annotation.*;

public class SpikeTrapBlock extends DirectionalBlock{
    public static final BooleanProperty LIT = RedstoneTorchBlock.LIT;
    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    public BlockState state;
    public BlockState spike;

    public SpikeTrapBlock(BlockState pState, BlockState pSpikes, BlockBehaviour.Properties properties){
        super(properties);
        this.state = pState;
        this.spike = pSpikes;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext){
        return Block.box(0, 0, 0, 16, 15, 16);
    }

    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
        if (pLevel.getDifficulty() != Difficulty.PEACEFUL && pEntity instanceof Player || pEntity instanceof ItemEntity){
            activateTrap(pLevel, pPos, pState);
        }
    }

    private void activateTrap(Level level, BlockPos pos, BlockState state){
        RandomSource rand = level.getRandom();
        Direction direction = state.getValue(DirectionalBlock.FACING);
        BlockPos newPos = pos.offset(direction.getNormal());
        BlockState spikeBlock = BlockRegistry.spikes.get().defaultBlockState().setValue(DirectionalBlock.FACING, direction);
        if(!level.getBlockState(newPos).isSolid()){
            level.setBlockAndUpdate(newPos, spikeBlock);
            level.scheduleTick(newPos, BlockRegistry.spikes.get(), 1);
            level.setBlockAndUpdate(pos, this.state);
            level.playSound(null, pos, SoundEvents.PISTON_EXTEND, SoundSource.BLOCKS, 0.3F, level.random.nextFloat() * 0.25F + 0.6F);
            if(level.isClientSide()){
                for(int i = 0; i < 10; i++){
                    level.addParticle(ParticleTypes.POOF, pos.getX() + rand.nextDouble(), pos.getY() + 0.5D, pos.getZ() + rand.nextDouble(), 0d, 0.05d, 0d);
                }
            }
        }

        level.gameEvent(null, GameEvent.BLOCK_ACTIVATE, pos);
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving){
        if(level.hasNeighborSignal(pos)){
            activateTrap(level, pos, state);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder){
        builder.add(FACING);
        builder.add(LIT);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context){
        return this.defaultBlockState().setValue(FACING, context.getNearestLookingDirection().getOpposite()).setValue(LIT, Boolean.valueOf(context.getLevel().hasNeighborSignal(context.getClickedPos())));
    }
}