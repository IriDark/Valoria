package com.idark.valoria.registries.world.block.types;

import com.idark.valoria.client.particle.ModParticles;
import com.idark.valoria.client.particle.types.Particles;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ShadeLeavesBlock extends LeavesBlock {

    public ShadeLeavesBlock(Properties properties) {
        super(properties);
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        if (world.getBlockState(new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ())).isAir()) {
            if (random.nextFloat() < 0.035) {
                Particles.create(ModParticles.SHADEWOOD_LEAF_PARTICLE)
                        .addVelocity(((random.nextDouble() - 0.5D) / 12), ((random.nextDouble() - 0.87D) / 4), ((random.nextDouble() - 0.5D) / 12))
                        .setAlpha(1f, 1f).setScale(0.072f, 0.02f)
                        .setColor(1f, 1f, 1f)
                        .setLifetime(82)
                        .setSpin(((float)Math.toRadians(random.nextBoolean() ? -2 : 4)))
                        .spawn(world, pos.getX() + 0.5F + ((random.nextFloat() - 0.5f) * 0.9f), pos.getY() - 0.05, pos.getZ() + 0.5F + ((random.nextFloat() - 0.5f * 0.9f)));
            }
        }
    }
}
