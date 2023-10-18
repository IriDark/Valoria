package com.idark.darkrpg.block.types;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;

public class WickedOreBlock extends Block {
    Random rand = new Random();
    public WickedOreBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public void wasExploded(Level worldIn, BlockPos pos, Explosion explosionIn) {
        for (int i = 0;i<5;i++) {
        worldIn.addParticle(ParticleTypes.SOUL, pos.getX() + rand.nextDouble(), pos.getY() + 0.5D, pos.getZ() + rand.nextDouble(), 0d, 0.05d, 0d);
        }
    }

    @Override
    public void destroy(LevelAccessor worldIn, BlockPos pos, BlockState state) {
        for (int i = 0;i<5;i++) {
        worldIn.addParticle(ParticleTypes.SOUL, pos.getX() + rand.nextDouble(), pos.getY() + 0.5D, pos.getZ() + rand.nextDouble(), 0d, 0.05d, 0d);
        }
    }
}