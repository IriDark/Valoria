package com.idark.valoria.registries.block.types;

import net.minecraft.core.*;
import net.minecraft.core.particles.*;
import net.minecraft.server.level.*;
import net.minecraft.util.*;
import net.minecraft.util.valueproviders.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;

public class WickedOreBlock extends Block{
    private final IntProvider xpRange;

    public WickedOreBlock(BlockBehaviour.Properties properties){
        this(properties, ConstantInt.of(0));
    }

    public WickedOreBlock(BlockBehaviour.Properties properties, IntProvider pXpRange){
        super(properties);
        this.xpRange = pXpRange;
    }

    public void spawnAfterBreak(BlockState pState, ServerLevel pLevel, BlockPos pPos, ItemStack pStack, boolean pDropExperience){
        super.spawnAfterBreak(pState, pLevel, pPos, pStack, pDropExperience);
    }

    @Override
    public int getExpDrop(BlockState state, net.minecraft.world.level.LevelReader level, net.minecraft.util.RandomSource randomSource, BlockPos pos, int fortuneLevel, int silkTouchLevel){
        return silkTouchLevel == 0 ? this.xpRange.sample(randomSource) : 0;
    }

    @Override
    public void wasExploded(Level worldIn, BlockPos pos, Explosion explosionIn){
        RandomSource rand = worldIn.getRandom();
        for(int i = 0; i < 5; i++){
            worldIn.addParticle(ParticleTypes.REVERSE_PORTAL, pos.getX() + rand.nextDouble(), pos.getY() + 0.5D, pos.getZ() + rand.nextDouble(), 0d, 0.05d, 0d);
        }
    }

    @Override
    public void destroy(LevelAccessor worldIn, BlockPos pos, BlockState state){
        RandomSource rand = worldIn.getRandom();
        for(int i = 0; i < 5; i++){
            worldIn.addParticle(ParticleTypes.REVERSE_PORTAL, pos.getX() + rand.nextDouble(), pos.getY() + 0.5D, pos.getZ() + rand.nextDouble(), 0d, 0.05d, 0d);
        }
    }
}