package com.idark.darkrpg.block.types;

import java.util.Random;
import javax.annotation.Nullable;
import com.idark.darkrpg.block.ModBlocks;
import com.idark.darkrpg.entity.ModEntityTypes;
import net.minecraft.block.*;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.pathfinding.PathType;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.*;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import static net.minecraft.state.properties.BlockStateProperties.WATERLOGGED;

public class FireTrapBlock extends DirectionalBlock {
	public static final BooleanProperty LIT = RedstoneTorchBlock.LIT;   
    private static IntegerProperty STATE = IntegerProperty.create("triggered", 0, 1);
	public static final DirectionProperty FACING = BlockStateProperties.FACING;
    Random rand = new Random();
    public FireTrapBlock(AbstractBlock.Properties properties) {
        super(properties);
        setDefaultState(this.stateContainer.getBaseState().with(WATERLOGGED, false).with(STATE, 0).with(LIT, Boolean.valueOf(false)).with(FACING, Direction.UP));
    }
	
	public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
		if (state.get(STATE) == 1) {	
			if (state.get(WATERLOGGED) == false) {
				entityIn.attackEntityFrom(DamageSource.GENERIC, 2.0F);
			}
		}
	}
	
	// Called when entity stepped on trap
    @Override
	public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
		BlockState state = worldIn.getBlockState(pos);
		Direction direction = state.get(DirectionalBlock.FACING);
		// When block in water do nothing and spawn POOF particles
		if (state.get(WATERLOGGED) == true) {
			worldIn.playSound((PlayerEntity)null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.05F, worldIn.rand.nextFloat() * 0.5F + 0.5F);
			worldIn.addParticle(ParticleTypes.POOF, pos.getX() + rand.nextDouble(), pos.getY() + 0.7D, pos.getZ() + rand.nextDouble(), 0d, 0.05d, 0d);
			entityIn.attackEntityFrom(DamageSource.GENERIC, 1.0F);
			return;
		}
		
		// When stepping on block with state 1 do nothing
		if (state.get(STATE) == 1) {
			return;
		}
		
		if (state.get(WATERLOGGED) == false) {
			worldIn.playSound((PlayerEntity)null, pos, SoundEvents.ENTITY_FIREWORK_ROCKET_BLAST, SoundCategory.BLOCKS, 0.3F, worldIn.rand.nextFloat() * 0.25F + 0.6F);
			worldIn.setBlockState(pos, ModBlocks.TOMBSTONE_FIRECHARGE_TRAP.get().getDefaultState().with(STATE, Integer.valueOf(1)).with(DirectionalBlock.FACING,state.get(DirectionalBlock.FACING)));
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
		Direction direction = state.get(DirectionalBlock.FACING);
		// When block in water do nothing and spawn POOF particles
		if (state.get(WATERLOGGED) == true) {
			worldIn.playSound((PlayerEntity)null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.1F, worldIn.rand.nextFloat() * 0.5F + 0.6F);
			worldIn.addParticle(ParticleTypes.POOF, pos.getX() + rand.nextDouble(), pos.getY() + 0.7D, pos.getZ() + rand.nextDouble(), 0d, 0.05d, 0d);
			return;
		}
		
		else if (state.get(WATERLOGGED) == false) {
			if (worldIn.isBlockPowered(pos)) {
				worldIn.playSound((PlayerEntity)null, pos, SoundEvents.ENTITY_FIREWORK_ROCKET_BLAST, SoundCategory.BLOCKS, 0.3F, worldIn.rand.nextFloat() * 0.25F + 0.6F);
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

    public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
        return type == PathType.AIR && !this.canCollide ? true : super.allowsMovement(state, worldIn, pos, type);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(STATE);
        builder.add(FACING);
        builder.add(LIT);
        builder.add(WATERLOGGED);
	}

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        FluidState fluidState = context.getWorld().getFluidState(context.getPos());
		return this.getDefaultState().with(FACING, context.getNearestLookingDirection().getOpposite()).with(LIT, Boolean.valueOf(context.getWorld().isBlockPowered(context.getPos()))).with(WATERLOGGED, Boolean.valueOf(fluidState.getFluid() == Fluids.WATER));
	}

	@Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : Fluids.EMPTY.getDefaultState();
    }
	
    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction side, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		if (stateIn.get(WATERLOGGED)) {
            worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
		}
		return stateIn;
    }
}