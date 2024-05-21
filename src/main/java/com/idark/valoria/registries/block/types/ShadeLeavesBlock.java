package com.idark.valoria.registries.block.types;

import com.idark.valoria.client.particle.ParticleRegistry;
import com.idark.valoria.client.particle.types.Particles;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ShadeLeavesBlock extends LeavesBlock{

    public ShadeLeavesBlock(Properties properties){
        super(properties);
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random){
        super.animateTick(state, world, pos, random);
        int i = Minecraft.getInstance().getBlockColors().getColor(state, world, pos, 0);
        if(world.getBlockState(new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ())).isAir()){
            if(random.nextFloat() < 0.035){
                Particles.create(ParticleRegistry.SHADEWOOD_LEAF_PARTICLE)
                .setColor((i >> 16 & 255) / 255.0F, (i >> 8 & 255) / 255.0F, (i & 255) / 255.0F)
                .addVelocity(((random.nextDouble() - 0.5D) / 6), ((random.nextDouble() - 1.25D) / 3), ((random.nextDouble() - 0.5D) / 6))
                .setAlpha(0f, 0.65f).setScale(0.072f, 0f)
                .setLifetime(75)
                .setSpin(((float)Math.toRadians(random.nextBoolean() ? -2 : 4)))
                .spawn(world, pos.getX() + 0.5F + ((random.nextFloat() - 0.5f) * 0.9f), pos.getY() + 0.05, pos.getZ() + 0.5F + ((random.nextFloat() - 0.5f * 0.9f)));
            }
        }
    }
}
