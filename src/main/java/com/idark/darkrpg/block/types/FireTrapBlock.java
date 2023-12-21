package com.idark.darkrpg.block.types;

import com.idark.darkrpg.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
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
import net.minecraft.world.phys.AABB;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class FireTrapBlock extends Block {
	private static IntegerProperty STATE = IntegerProperty.create("triggered", 0, 1);
	Random rand = new Random();

	public FireTrapBlock(BlockBehaviour.Properties properties) {
		super(properties);
		registerDefaultState(this.stateDefinition.any().setValue(STATE, 0));
	}

	public static boolean isWaterNearby(Level world, BlockPos centerPos, int radius) {
		for (int xOffset = -radius; xOffset <= radius; xOffset++) {
			for (int yOffset = -radius; yOffset <= radius; yOffset++) {
				for (int zOffset = -radius; zOffset <= radius; zOffset++) {
					BlockPos currentPos = centerPos.offset(xOffset, yOffset, zOffset);
					FluidState fluidState = world.getFluidState(currentPos);

					if (fluidState.getType() == Fluids.WATER) {
						return true;
					}
				}
			}
		}

		return false;
	}

	@Override
	public void stepOn(Level level, BlockPos pos, BlockState state, Entity entityIn) {
		BlockState tombstone = ModBlocks.POLISHED_TOMBSTONE.get().defaultBlockState();
		int radius = 1;
		boolean isWaterNearby = isWaterNearby(level, pos, radius);
		if (isWaterNearby) {
			level.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 0.05F, level.random.nextFloat() * 0.5F + 0.5F);
			level.addParticle(ParticleTypes.POOF, pos.getX() + rand.nextDouble(), pos.getY() + 0.7D, pos.getZ() + rand.nextDouble(), 0d, 0.05d, 0d);
        } else {
			level.playSound(null, pos, SoundEvents.FIREWORK_ROCKET_BLAST, SoundSource.BLOCKS, 0.3F, level.random.nextFloat() * 0.25F + 0.6F);
			for (int i = 0; i < 25; i++) {
				level.addParticle(ParticleTypes.LARGE_SMOKE, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, 0.0D, 0.0D, 0.0D);
				level.addParticle(ParticleTypes.FLAME, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 0.0D, 0.5D, 0.0D);
				level.addParticle(ParticleTypes.FLAME, pos.getX() + 0.3, pos.getY() + 0.3, pos.getZ() + 0.5, 0.0D, 0.5D, 0.0D);
				level.addParticle(ParticleTypes.FLAME, pos.getX() - 0.3, pos.getY() + 0.2, pos.getZ() + 0.5, 0.0D, 0.5D, 0.0D);
				level.addParticle(ParticleTypes.FLAME, pos.getX() - 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 0.05D, 0.5D, 0.0D);
				level.addParticle(ParticleTypes.FLAME, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, 0.0D, 0.5D, 0.0D);
				level.addParticle(ParticleTypes.FLAME, pos.getX() - 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, 0.05D, 0.5D, 0.0D);
				level.addParticle(ParticleTypes.FLAME, pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() - 0.5, 0.0D, 0.5D, 0.05D);
				level.addParticle(ParticleTypes.FLAME, pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5, 0.0D, 0.0D, 0.0D);
				level.addParticle(ParticleTypes.LAVA, pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5, 0.0D, 0.0D, 0.0D);
			}

			entityIn.hurt(entityIn.damageSources().inFire(), 4.0F);
			level.setBlockAndUpdate(pos, tombstone);
		}
	}

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(STATE);
	}
}