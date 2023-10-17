package com.idark.darkrpg.block.types;

import com.idark.darkrpg.block.ModBlocks;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.*;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

public class SpikeTrapBlock extends DirectionalBlock {
    public static final BooleanProperty LIT = RedstoneTorchBlock.LIT;   
    private static IntegerProperty STATE = IntegerProperty.create("triggered", 0, 1);
	public static final DirectionProperty FACING = BlockStateProperties.FACING;
    Random rand = new Random();
    public SpikeTrapBlock(AbstractBlock.Properties properties) {
        super(properties);
        registerDefaultState(this.stateDefinition.any().setValue(STATE, 0).setValue(LIT, Boolean.valueOf(false)).setValue(FACING, Direction.UP));
    }

	// Called when entity stepped on trap
    @Override
	public void stepOn(World worldIn, BlockPos pos, Entity entityIn) {
		BlockState state = worldIn.getBlockState(pos);
		// When stepping on block with state 1 do nothing
		if (state.getValue(STATE) == 1) {
			return;
		}
		
		if (state.getValue(STATE) == 0) {
 		// Receive the Spike pos
		Direction direction = state.getValue(DirectionalBlock.FACING);
		BlockPos newPos = pos.offset(direction.getNormal());
		BlockState spikeBlock = ModBlocks.SPIKES.get().defaultBlockState().setValue(DirectionalBlock.FACING,direction);
		
		worldIn.setBlockAndUpdate(newPos,spikeBlock);
		worldIn.setBlockAndUpdate(pos, state.setValue(STATE, Integer.valueOf(1)).setValue(DirectionalBlock.FACING,state.getValue(DirectionalBlock.FACING)));
		worldIn.playSound((PlayerEntity)null, pos, SoundEvents.PISTON_EXTEND, SoundCategory.BLOCKS, 0.3F, worldIn.random.nextFloat() * 0.25F + 0.6F);
	    if(!worldIn.isClientSide())
		for (int i = 0;i<10;i++) {
			worldIn.addParticle(ParticleTypes.POOF, pos.getX() + rand.nextDouble(), pos.getY() + 0.5D, pos.getZ() + rand.nextDouble(), 0d, 0.05d, 0d);
			}
		}
	}

	// Redstone signal
    @Override
    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {	
		// Receive the Spike pos
		Direction direction = state.getValue(DirectionalBlock.FACING);
		BlockPos newPos = pos.offset(direction.getNormal());
		BlockState spikeBlock = ModBlocks.SPIKES.get().defaultBlockState().setValue(DirectionalBlock.FACING,direction);
 		if (worldIn.hasNeighborSignal(pos)) {
			worldIn.setBlockAndUpdate(newPos,spikeBlock);
			worldIn.playSound((PlayerEntity)null, pos, SoundEvents.PISTON_EXTEND, SoundCategory.BLOCKS, 0.3F, worldIn.random.nextFloat() * 0.25F + 0.6F);
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

    public boolean isPathfindable(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
        return type == PathType.AIR && !this.hasCollision ? true : super.isPathfindable(state, worldIn, pos, type);
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(STATE);
        builder.add(FACING);
        builder.add(LIT);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.defaultBlockState().setValue(FACING, context.getNearestLookingDirection().getOpposite()).setValue(LIT, Boolean.valueOf(context.getLevel().hasNeighborSignal(context.getClickedPos())));
	}
}