package com.idark.valoria.registries.level.feature;

import com.mojang.serialization.*;
import net.minecraft.core.*;
import net.minecraft.util.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.feature.*;
import net.minecraft.world.level.levelgen.feature.configurations.*;

public class RootFeature extends Feature<BlockStateConfiguration> {
    public RootFeature(Codec<BlockStateConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<BlockStateConfiguration> context) {
        BlockPos origin = context.origin();
        BlockPos blockpos = context.level().getHeightmapPos(Heightmap.Types.OCEAN_FLOOR_WG, origin);
        WorldGenLevel world = context.level();
        RandomSource random = context.random();
        BlockStateConfiguration config = context.config();
        int rootCount = 3 + random.nextInt(3);
        for (int i = 0; i < rootCount; i++) {
            BlockPos rootStart = blockpos.offset(random.nextInt(3) - 1, 0, random.nextInt(3) - 1);
            generateRoot(world, rootStart, random, config.state);
        }

        return true;
    }

    private void generateRoot(WorldGenLevel world, BlockPos start, RandomSource random, BlockState wood) {
        BlockPos pos = start;
        int length = 4 + random.nextInt(3);

        for (int i = 0; i < length; i++) {
            if (random.nextFloat() < 0.6) {
                pos = pos.offset(random.nextInt(3) - 1, -random.nextInt(2), random.nextInt(3) - 1);
            } else {
                pos = pos.below();
            }

            if (world.getBlockState(pos).isAir()) {
                world.setBlock(pos, wood, 3);
            }

            if (random.nextFloat() < 0.3) {
                BlockPos side = pos.offset(random.nextInt(3) - 1, 0, random.nextInt(3) - 1);
                if (world.getBlockState(side).isAir()) {
                    world.setBlock(side, wood, 3);
                }
            }
        }
    }
}
