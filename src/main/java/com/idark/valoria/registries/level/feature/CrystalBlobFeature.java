package com.idark.valoria.registries.level.feature;

import com.mojang.serialization.*;
import net.minecraft.core.*;
import net.minecraft.util.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.levelgen.feature.*;
import net.minecraft.world.level.levelgen.feature.configurations.*;

public class CrystalBlobFeature extends Feature<BlockStateConfiguration>{
    public CrystalBlobFeature(Codec<BlockStateConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<BlockStateConfiguration> context) {
        BlockPos origin = context.origin();
        WorldGenLevel world = context.level();
        RandomSource random = context.random();
        BlockStateConfiguration config = context.config();
        while (origin.getY() > world.getMinBuildHeight() + 3 && world.isEmptyBlock(origin.below())) {
            origin = origin.below();
        }

        if (origin.getY() <= world.getMinBuildHeight() + 3) {
            return false;
        }

        int baseRadius = 1 + random.nextInt(2);
        int height = 6 + random.nextInt(4);
        for (int y = 0; y < height; y++) {
            float radius = baseRadius * (1.0f - (y / (float) height)) + 0.5f;
            for (BlockPos pos : BlockPos.betweenClosed(origin.offset(-baseRadius, y, -baseRadius), origin.offset(baseRadius, y, baseRadius))) {
                if (pos.distSqr(new Vec3i(origin.getX(), origin.getY() + y, origin.getZ())) <= radius * radius) {
                    world.setBlock(pos, config.state, 3);
                }
            }

            if (random.nextFloat() < 0.5) {
                BlockPos sideOffset = origin.offset(random.nextInt(3) - 1, y, random.nextInt(3) - 1);
                world.setBlock(sideOffset, config.state, 3);
            }
        }

        return true;
    }}