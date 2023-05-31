package com.idark.darkrpg.block.types;

import net.minecraft.block.*;
import net.minecraft.particles.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import java.util.Random;

public class WickedOreBlock extends Block {
    Random rand = new Random();
    public WickedOreBlock(AbstractBlock.Properties properties) {
        super(properties);
    }

    @Override
    public void onExplosionDestroy(World worldIn, BlockPos pos, Explosion explosionIn) {
        for (int i = 0;i<5;i++) {
        worldIn.addParticle(ParticleTypes.SOUL, pos.getX() + rand.nextDouble(), pos.getY() + 0.5D, pos.getZ() + rand.nextDouble(), 0d, 0.05d, 0d);
        }
    }

    @Override
    public void onPlayerDestroy(IWorld worldIn, BlockPos pos, BlockState state) {
        for (int i = 0;i<5;i++) {
        worldIn.addParticle(ParticleTypes.SOUL, pos.getX() + rand.nextDouble(), pos.getY() + 0.5D, pos.getZ() + rand.nextDouble(), 0d, 0.05d, 0d);
        }
    }
}