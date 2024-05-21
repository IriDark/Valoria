package com.idark.valoria.registries.block.types;

import com.idark.valoria.registries.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.RedstoneTorchBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.gameevent.GameEvent;

import javax.annotation.Nullable;

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
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entityIn){
        RandomSource rand = level.getRandom();
        Direction direction = state.getValue(DirectionalBlock.FACING);
        BlockPos newPos = pos.offset(direction.getNormal());
        BlockState spikeBlock = BlockRegistry.SPIKES.get().defaultBlockState().setValue(DirectionalBlock.FACING, direction);
        if(!level.getBlockState(newPos).isSolid()){
            level.setBlockAndUpdate(newPos, spikeBlock);
            level.scheduleTick(newPos, BlockRegistry.SPIKES.get(), 1);
            level.setBlockAndUpdate(pos, this.state);
            level.playSound(null, pos, SoundEvents.PISTON_EXTEND, SoundSource.BLOCKS, 0.3F, level.random.nextFloat() * 0.25F + 0.6F);
            if(level.isClientSide()){
                for(int i = 0; i < 10; i++){
                    level.addParticle(ParticleTypes.POOF, pos.getX() + rand.nextDouble(), pos.getY() + 0.5D, pos.getZ() + rand.nextDouble(), 0d, 0.05d, 0d);
                }
            }
        }
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving){
        RandomSource rand = level.getRandom();
        Direction direction = state.getValue(DirectionalBlock.FACING);
        BlockPos newPos = pos.offset(direction.getNormal());
        if(level.hasNeighborSignal(pos)){
            if(!level.getBlockState(newPos).isSolid()){
                level.setBlockAndUpdate(newPos, this.spike);
                level.scheduleTick(newPos, BlockRegistry.SPIKES.get(), 1);
                level.playSound(null, pos, SoundEvents.PISTON_EXTEND, SoundSource.BLOCKS, 0.3F, level.random.nextFloat() * 0.25F + 0.6F);
                if(level.isClientSide()){
                    for(int i = 0; i < 10; i++){
                        level.addParticle(ParticleTypes.POOF, pos.getX() + rand.nextDouble(), pos.getY() + 0.5D, pos.getZ() + rand.nextDouble(), 0d, 0.05d, 0d);
                    }
                }
            }

            level.gameEvent(null, GameEvent.BLOCK_ACTIVATE, pos);
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