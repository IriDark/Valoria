package com.idark.valoria.registries.block.types;

import com.idark.valoria.client.particle.ModParticles;
import com.idark.valoria.client.particle.types.Particles;
import com.idark.valoria.registries.BlockRegistry;
import com.idark.valoria.util.RandomUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

import java.util.Random;

public class FireTrapBlock extends Block {
    private static final IntegerProperty STATE = IntegerProperty.create("triggered", 0, 1);
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
        BlockState tombstone = BlockRegistry.POLISHED_TOMBSTONE.get().defaultBlockState();
        int radius = 1;
        boolean isWaterNearby = isWaterNearby(level, pos, radius);
        if (isWaterNearby) {
            level.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 0.05F, level.random.nextFloat() * 0.5F + 0.5F);
            level.addParticle(ParticleTypes.POOF, pos.getX() + rand.nextDouble(), pos.getY() + 0.7D, pos.getZ() + rand.nextDouble(), 0d, 0.05d, 0d);
        } else {
            level.playSound(null, pos, SoundEvents.FIREWORK_ROCKET_BLAST, SoundSource.BLOCKS, 0.3F, level.random.nextFloat() * 0.25F + 0.6F);
            for (int i = 0; i < 20; i++) {
                Particles.create(ModParticles.GLOWING_SPHERE)
                        .addVelocity((new Random().nextDouble() - 0.5D) / 30, (new Random().nextDouble() + 0.5D) / 6, (new Random().nextDouble() - 0.5D) / 30)
                        .setAlpha(0.15f, 0)
                        .setScale(0.45f + RandomUtil.randomValueUpTo(0.2f), 0)
                        .setColor((float) 255 / 255, (float) 145 / 255, (float) 45 / 255, (float) 45 / 255, 0, 0)
                        .setLifetime(16)
                        .setSpin(0.5f)
                        .spawn(level, pos.getCenter().x, pos.getY() + 1.2f, pos.getCenter().z);
                Particles.create(ModParticles.SPHERE)
                        .addVelocity((new Random().nextDouble() - 0.2D) / 30, (new Random().nextDouble() + 0.2D) / 6, (new Random().nextDouble() - 0.2D) / 30)
                        .setAlpha(0.25f, 0)
                        .setScale(0.25f, 0)
                        .setColor(0, 0, 0, 0, 0, 0)
                        .setLifetime(32)
                        .setSpin(0.5f)
                        .spawn(level, pos.getCenter().x, pos.getY() + 1.2f, pos.getCenter().z);
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