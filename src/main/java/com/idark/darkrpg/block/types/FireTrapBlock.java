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

public class FireTrapBlock extends DirectionalBlock {
	public static final BooleanProperty LIT = RedstoneTorchBlock.LIT;
    private static IntegerProperty STATE = IntegerProperty.create("triggered", 0, 1);
	public static final DirectionProperty FACING = BlockStateProperties.FACING;
    Random rand = new Random();
    public FireTrapBlock(BlockBehaviour.Properties properties) {
        super(properties);
        registerDefaultState(this.stateDefinition.any().setValue(BlockStateProperties.WATERLOGGED, false).setValue(STATE, 0).setValue(LIT, Boolean.valueOf(false)).setValue(FACING, Direction.UP));
    }
	
	public void entityInside(BlockState state, Level worldIn, BlockPos pos, Entity entityIn) {
		if (state.getValue(STATE) == 1) {	
			if (!state.getValue(BlockStateProperties.WATERLOGGED)) {
				entityIn.hurt(entityIn.damageSources().generic(), 2.0F);
			}
		}
	}
	
    @Override
	public void stepOn(Level worldIn, BlockPos pos, BlockState state, Entity entityIn) {
		Direction direction = state.getValue(DirectionalBlock.FACING);
		if (state.getValue(BlockStateProperties.WATERLOGGED)) {
			worldIn.playSound((Player)null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 0.05F, worldIn.random.nextFloat() * 0.5F + 0.5F);
			worldIn.addParticle(ParticleTypes.POOF, pos.getX() + rand.nextDouble(), pos.getY() + 0.7D, pos.getZ() + rand.nextDouble(), 0d, 0.05d, 0d);
			entityIn.hurt(entityIn.damageSources().generic(), 1.0F);
			return;
		}
		
		if (state.getValue(STATE) == 1) {
			return;
		}
		
		if (!state.getValue(BlockStateProperties.WATERLOGGED)) {
			worldIn.playSound((Player)null, pos, SoundEvents.FIREWORK_ROCKET_BLAST, SoundSource.BLOCKS, 0.3F, worldIn.random.nextFloat() * 0.25F + 0.6F);
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
        }
	}

    @Override
    public void neighborChanged(BlockState state, Level worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
		Direction direction = state.getValue(DirectionalBlock.FACING);
		if (state.getValue(BlockStateProperties.WATERLOGGED)) {
			worldIn.playSound((Player)null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 0.1F, worldIn.random.nextFloat() * 0.5F + 0.6F);
			worldIn.addParticle(ParticleTypes.POOF, pos.getX() + rand.nextDouble(), pos.getY() + 0.7D, pos.getZ() + rand.nextDouble(), 0d, 0.05d, 0d);
        }
		
		else if (!state.getValue(BlockStateProperties.WATERLOGGED)) {
			if (worldIn.hasNeighborSignal(pos)) {
				worldIn.playSound((Player)null, pos, SoundEvents.FIREWORK_ROCKET_BLAST, SoundSource.BLOCKS, 0.3F, worldIn.random.nextFloat() * 0.25F + 0.6F);
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

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(STATE);
        builder.add(FACING);
        builder.add(LIT);
        builder.add(BlockStateProperties.WATERLOGGED);
	}

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
		return this.defaultBlockState().setValue(FACING, context.getNearestLookingDirection().getOpposite()).setValue(LIT, Boolean.valueOf(context.getLevel().hasNeighborSignal(context.getClickedPos()))).setValue(BlockStateProperties.WATERLOGGED, Boolean.valueOf(fluidState.getType() == Fluids.WATER));
	}

	@Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(BlockStateProperties.WATERLOGGED) ? Fluids.WATER.getSource(false) : Fluids.EMPTY.defaultFluidState();
    }
}