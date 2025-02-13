package com.idark.valoria.registries.level.feature;

import com.idark.valoria.registries.level.configurations.*;
import com.mojang.serialization.*;
import net.minecraft.core.*;
import net.minecraft.util.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.levelgen.feature.*;

import java.util.*;

public class GradientOreFeature extends Feature<GradientOreConfiguration> {

    public GradientOreFeature(Codec<GradientOreConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<GradientOreConfiguration> context) {
        GradientOreConfiguration config = context.config();
        WorldGenLevel level = context.level();
        RandomSource random = context.random();
        BlockPos origin = context.origin();

        int radius = config.radius;
        List<BlockState> gradientBlocks = config.gradientBlocks;
        int segments = gradientBlocks.size() - 1;
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    BlockPos pos = origin.offset(x, y, z);
                    double dist = Math.sqrt(x * x + y * y + z * z);
                    if (dist > radius) continue;

                    int index = (int) Math.floor(dist / radius * segments);
                    BlockState chosenState;
                    if (index >= segments) {
                        chosenState = gradientBlocks.get(segments);
                    } else {
                        if (random.nextDouble() < (1 - (dist / radius * segments) - index)) {
                            chosenState = gradientBlocks.get(index);
                        } else {
                            chosenState = gradientBlocks.get(index + 1);
                        }
                    }

                    double chance = 1.0 - 0.9 * dist / radius;
                    if (random.nextDouble() < chance && canReplace(level, pos, config)) {
                        level.setBlock(pos, chosenState, 2);
                    }
                }
            }
        }
        return true;
    }

    private boolean canReplace(WorldGenLevel level, BlockPos pos, GradientOreConfiguration config) {
        return level.getBlockState(pos).is(config.targetBlock.getBlock());
    }
}