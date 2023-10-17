package com.idark.darkrpg.block.types;

import com.idark.darkrpg.block.ModBlocks;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.*;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

import static net.minecraft.state.properties.BlockStateProperties.WATERLOGGED;

public class FireTrapBlock extends DirectionalBlock {
	public static final BooleanProperty LIT = RedstoneTorchBlock.LIT;   
    private static IntegerProperty STATE = IntegerProperty.create("triggered", 0, 1);
	public static final DirectionProperty FACING = BlockStateProperties.FACING;
    Random rand = new Random();
    public FireTrapBlock(AbstractBlock.Properties properties) {
        super(properties);
        registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, false).setValue(STATE, 0).setValue(LIT, Boolean.valueOf(false)).setValue(FACING, Direction.UP));
    }
	
	public void entityInside(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
		if (state.getValue(STATE) == 1) {	
			if (state.getValue(WATERLOGGED) == false) {
				entityIn.hurt(DamageSource.GENERIC, 2.0F);
			}
		}
	}
	
	// Called when entity stepped on trap
    @Override
	public void stepOn(World worldIn, BlockPos pos, Entity entityIn) {
		BlockState state = worldIn.getBlockState(pos);
		Direction direction = state.getValue(DirectionalBlock.FACING);
		// When block in water do nothing and spawn POOF particles
		if (state.getValue(WATERLOGGED) == true) {
			worldIn.playSound((PlayerEntity)null, pos, SoundEvents.FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.05F, worldIn.random.nextFloat() * 0.5F + 0.5F);
			worldIn.addParticle(ParticleTypes.POOF, pos.getX() + rand.nextDouble(), pos.getY() + 0.7D, pos.getZ() + rand.nextDouble(), 0d, 0.05d, 0d);
			entityIn.hurt(DamageSource.GENERIC, 1.0F);
			return;
		}
		
		// When stepping on block with state 1 do nothing
		if (state.getValue(STATE) == 1) {
			return;
		}
		
		if (state.getValue(WATERLOGGED) == false) {
			worldIn.playSound((PlayerEntity)null, pos, SoundEvents.FIREWORK_ROCKET_BLAST, SoundCategory.BLOCKS, 0.3F, worldIn.random.nextFloat() * 0.25F + 0.6F);
			worldIn.setBlockAndUpdate(pos, ModBlocks.TOMBSTONE_FIRECHARGE_TRAP.get().defaultBlockState().setValue(STATE, Integer.valueOf(1)).setValue(DirectionalBlock.FACING,state.getValue(DirectionalBlock.FACING)));
			for (int i = 0;i<25;i++) {
				worldIn.addParticle(ParticleTypes.LARGE_SMOKE, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, 0.0D, 0.0D, 0.0D);
				worldIn.addParticle(ParticleTypes.FLAME, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 0.0D, 0.5D, 0.0D);
				worldIn.addParticle(ParticleTypes.FLAME, pos.getX() + 0.3, pos.getY() + 0.3, pos.getZ() + 0.5, 0.0D, 0.5D, 0.0D);
				worldIn.addParticle(ParticleTypes.FLAME, pos.getX() - 0.3, pos.getY() + 0.2, pos.getZ() + 0.5, 0.0D, 0.5D, 0.0D);
				worldIn.addParticle(ParticleTypes.FLAME, pos.getX() - 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 0.05D, 0.5D, 0.0D);
				worldIn.addParticle(ParticleTypes.FLAME, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, 0.0D, 0.5D, 0.0D);
				worldIn.addParticle(ParticleTypes.FLAME, pos.getX() - 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, 0.05D, 0.5D, 0.0D);
				worldIn.addParticle(ParticleTypes.FLAME, pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() - 0.5, 0.0D, 0.5D, 0.05D);
				worldIn.addParticle(ParticleTypes.FLAME, pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5, 0.0D, 0.0D, 0.0D);
				worldIn.addParticle(ParticleTypes.LAVA, pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5, 0.0D, 0.0D, 0.0D);
			}
			return;
		}
	}

	// Redstone signal
    @Override
    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
		Direction direction = state.getValue(DirectionalBlock.FACING);
		// When block in water do nothing and spawn POOF particles
		if (state.getValue(WATERLOGGED) == true) {
			worldIn.playSound((PlayerEntity)null, pos, SoundEvents.FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.1F, worldIn.random.nextFloat() * 0.5F + 0.6F);
			worldIn.addParticle(ParticleTypes.POOF, pos.getX() + rand.nextDouble(), pos.getY() + 0.7D, pos.getZ() + rand.nextDouble(), 0d, 0.05d, 0d);
			return;
		}
		
		else if (state.getValue(WATERLOGGED) == false) {
			if (worldIn.hasNeighborSignal(pos)) {
				worldIn.playSound((PlayerEntity)null, pos, SoundEvents.FIREWORK_ROCKET_BLAST, SoundCategory.BLOCKS, 0.3F, worldIn.random.nextFloat() * 0.25F + 0.6F);
				for (int i = 0;i<25;i++) {
				worldIn.addParticle(ParticleTypes.LARGE_SMOKE, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, 0.0D, 0.0D, 0.0D);
				worldIn.addParticle(ParticleTypes.FLAME, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 0.0D, 0.5D, 0.0D);
				worldIn.addParticle(ParticleTypes.FLAME, pos.getX() + 0.3, pos.getY() + 0.3, pos.getZ() + 0.5, 0.0D, 0.5D, 0.0D);
				worldIn.addParticle(ParticleTypes.FLAME, pos.getX() - 0.3, pos.getY() + 0.2, pos.getZ() + 0.5, 0.0D, 0.5D, 0.0D);
				worldIn.addParticle(ParticleTypes.FLAME, pos.getX() - 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 0.05D, 0.5D, 0.0D);
				worldIn.addParticle(ParticleTypes.FLAME, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, 0.0D, 0.5D, 0.0D);
				worldIn.addParticle(ParticleTypes.FLAME, pos.getX() - 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, 0.05D, 0.5D, 0.0D);
				worldIn.addParticle(ParticleTypes.FLAME, pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() - 0.5, 0.0D, 0.5D, 0.05D);
				worldIn.addParticle(ParticleTypes.FLAME, pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5, 0.0D, 0.0D, 0.0D);
				worldIn.addParticle(ParticleTypes.LAVA, pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5, 0.0D, 0.0D, 0.0D);
				}
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
        builder.add(WATERLOGGED);
	}

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
		return this.defaultBlockState().setValue(FACING, context.getNearestLookingDirection().getOpposite()).setValue(LIT, Boolean.valueOf(context.getLevel().hasNeighborSignal(context.getClickedPos()))).setValue(WATERLOGGED, Boolean.valueOf(fluidState.getType() == Fluids.WATER));
	}

	@Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : Fluids.EMPTY.defaultFluidState();
    }
	
    @Override
    public BlockState updateShape(BlockState stateIn, Direction side, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		if (stateIn.getValue(WATERLOGGED)) {
            worldIn.getLiquidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
		}
		return stateIn;
    }
}