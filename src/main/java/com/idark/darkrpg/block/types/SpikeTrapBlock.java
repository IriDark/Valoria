package com.idark.darkrpg.block.types;

import com.idark.darkrpg.block.ModBlocks;
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
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.RedstoneTorchBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

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

	// Called when entity stepped on trap
	@Override
	public void stepOn(Level worldIn, BlockPos pos, BlockState state, Entity entityIn) {
		// When stepping on block with state 1 do nothing
		if (state.getValue(STATE) == 1) {
			return;
		}
		
		if (state.getValue(STATE) == 0) {
			// Receive the Spike pos
			Direction direction = state.getValue(DirectionalBlock.FACING);
			BlockPos newPos = pos.offset(direction.getNormal());
			BlockState spikeBlock = ModBlocks.SPIKES.get().defaultBlockState().setValue(DirectionalBlock.FACING, direction);

			worldIn.setBlockAndUpdate(newPos, spikeBlock);
			worldIn.setBlockAndUpdate(pos, state.setValue(STATE, Integer.valueOf(1)).setValue(DirectionalBlock.FACING, state.getValue(DirectionalBlock.FACING)));
			worldIn.playSound((Player) null, pos, SoundEvents.PISTON_EXTEND, SoundSource.BLOCKS, 0.3F, worldIn.random.nextFloat() * 0.25F + 0.6F);
			if (!worldIn.isClientSide()) {
				for (int i = 0; i < 10; i++) {
					worldIn.addParticle(ParticleTypes.POOF, pos.getX() + rand.nextDouble(), pos.getY() + 0.5D, pos.getZ() + rand.nextDouble(), 0d, 0.05d, 0d);
				}
			}
		}
	}

	// Redstone signal
    @Override
    public void neighborChanged(BlockState state, Level worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
		// Receive the Spike pos
		Direction direction = state.getValue(DirectionalBlock.FACING);
		BlockPos newPos = pos.offset(direction.getNormal());
		BlockState spikeBlock = ModBlocks.SPIKES.get().defaultBlockState().setValue(DirectionalBlock.FACING,direction);
 		if (worldIn.hasNeighborSignal(pos)) {
			worldIn.setBlockAndUpdate(newPos,spikeBlock);
			worldIn.playSound((Player)null, pos, SoundEvents.PISTON_EXTEND, SoundSource.BLOCKS, 0.3F, worldIn.random.nextFloat() * 0.25F + 0.6F);
			for (int i = 0;i<10;i++) {
				worldIn.addParticle(ParticleTypes.POOF, pos.getX() + rand.nextDouble(), pos.getY() + 0.5D, pos.getZ() + rand.nextDouble(), 0d, 0.05d, 0d);
			}
		}
		// Destroy Spike when block Powered and Triggered is 0
		BlockState stateTrigger = worldIn.getBlockState(pos);
		if (!worldIn.hasNeighborSignal(pos) && stateTrigger.getValue(STATE) == 0) {
			worldIn.setBlockAndUpdate(newPos, Blocks.AIR.defaultBlockState());
			for (int i = 0;i<10;i++) {
				worldIn.addParticle(ParticleTypes.POOF, pos.getX() + rand.nextDouble(), pos.getY() + 0.5D, pos.getZ() + rand.nextDouble(), 0d, 0.05d, 0d);
			}
		}
	}

    //public boolean isPathfindable(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
    //    return type == PathType.AIR && !this.hasCollision ? true : super.isPathfindable(state, worldIn, pos, type);
    //}

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