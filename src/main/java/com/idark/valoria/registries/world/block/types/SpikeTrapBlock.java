package com.idark.valoria.registries.world.block.types;

import com.idark.valoria.registries.world.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
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
import net.minecraft.world.level.block.state.properties.IntegerProperty;

import javax.annotation.Nullable;
import java.util.Random;

public class SpikeTrapBlock extends DirectionalBlock {
    public static final BooleanProperty LIT = RedstoneTorchBlock.LIT;   
    private static IntegerProperty STATE = IntegerProperty.create("triggered", 0, 1);
	public static final DirectionProperty FACING = BlockStateProperties.FACING;
    Random rand = new Random();
    public SpikeTrapBlock(BlockBehaviour.Properties properties) {
        super(properties);
        registerDefaultState(this.stateDefinition.any().setValue(STATE, 0).setValue(LIT, Boolean.valueOf(false)).setValue(FACING, Direction.UP));
    }

	@Override
	public void stepOn(Level level, BlockPos pos, BlockState state, Entity entityIn) {
		if (state.getValue(STATE) == 1) {
			return;
		}

		if (state.getValue(STATE) == 0) {
			Direction direction = state.getValue(DirectionalBlock.FACING);
			BlockPos newPos = pos.offset(direction.getNormal());
			BlockState spikeBlock = ModBlocks.SPIKES.get().defaultBlockState().setValue(DirectionalBlock.FACING, direction);
			BlockState tombstone = ModBlocks.POLISHED_TOMBSTONE.get().defaultBlockState();

			if (!level.getBlockState(newPos).isSolid()) {
				level.setBlockAndUpdate(newPos, spikeBlock);
				level.scheduleTick(newPos, ModBlocks.SPIKES.get(), 1);
				level.setBlockAndUpdate(pos, state.setValue(STATE, Integer.valueOf(1)).setValue(DirectionalBlock.FACING, state.getValue(DirectionalBlock.FACING)));
				level.setBlockAndUpdate(pos, tombstone);
				level.playSound((Player) null, pos, SoundEvents.PISTON_EXTEND, SoundSource.BLOCKS, 0.3F, level.random.nextFloat() * 0.25F + 0.6F);
				if (level.isClientSide()) {
					for (int i = 0; i < 10; i++) {
						level.addParticle(ParticleTypes.POOF, pos.getX() + rand.nextDouble(), pos.getY() + 0.5D, pos.getZ() + rand.nextDouble(), 0d, 0.05d, 0d);
					}
				}
			}
		}
	}

	@Override
	public void neighborChanged(BlockState state, Level level, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
		Direction direction = state.getValue(DirectionalBlock.FACING);
		BlockPos newPos = pos.offset(direction.getNormal());
		BlockState spikeBlock = ModBlocks.SPIKES.get().defaultBlockState().setValue(DirectionalBlock.FACING, direction);
		BlockState tombstone = ModBlocks.POLISHED_TOMBSTONE.get().defaultBlockState();
		if (level.hasNeighborSignal(pos)) {
			if (!level.getBlockState(newPos).isSolid()) {
				level.setBlockAndUpdate(newPos, spikeBlock);
				level.scheduleTick(newPos, ModBlocks.SPIKES.get(), 1);
				level.setBlockAndUpdate(pos, tombstone);
				level.playSound((Player) null, pos, SoundEvents.PISTON_EXTEND, SoundSource.BLOCKS, 0.3F, level.random.nextFloat() * 0.25F + 0.6F);
				if (level.isClientSide()) {
					for (int i = 0; i < 10; i++) {
						level.addParticle(ParticleTypes.POOF, pos.getX() + rand.nextDouble(), pos.getY() + 0.5D, pos.getZ() + rand.nextDouble(), 0d, 0.05d, 0d);
					}
				}
			}
		}
	}

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(STATE);
        builder.add(FACING);
        builder.add(LIT);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
		return this.defaultBlockState().setValue(FACING, context.getNearestLookingDirection().getOpposite()).setValue(LIT, Boolean.valueOf(context.getLevel().hasNeighborSignal(context.getClickedPos())));
	}
}