package com.idark.valoria.registries.level.feature;

import com.idark.valoria.core.config.*;
import com.mojang.serialization.*;
import net.minecraft.core.*;
import net.minecraft.util.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.feature.*;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import pro.komaru.tridot.util.*;

public class PotFeature extends Feature<SimpleBlockConfiguration> {
    public PotFeature(Codec<SimpleBlockConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<SimpleBlockConfiguration> context) {
        SimpleBlockConfiguration config = context.config();
        RandomSource randomsource = context.random();
        WorldGenLevel gen = context.level();
        BlockPos origin = context.origin();

        boolean placedAny = false;
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

        int tries = getTries();
        for(int attempts = 0; attempts < tries; attempts++) {
            int x = origin.getX() + randomsource.nextInt(16);
            int z = origin.getZ() + randomsource.nextInt(16);
            int startY = origin.getY();
            if (tryPlacePot(gen, randomsource, config, mutablePos, x, startY, z)) {
                placedAny = true;
            }
        }

        return placedAny;
    }

    protected int getTries() {
        return ServerConfig.POT_TRIES.get();
    }

    protected boolean tryPlacePot(WorldGenLevel gen, RandomSource random, SimpleBlockConfiguration config, BlockPos.MutableBlockPos mutablePos, int x, int startY, int z) {
        mutablePos.set(x, 0, z);
        BlockPos heightPos = gen.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, mutablePos);
        if (gen.isEmptyBlock(heightPos) || gen.getBlockState(heightPos).getCollisionShape(gen, heightPos).isEmpty()) {
            BlockState state = config.toPlace().getState(random, heightPos);
            if (state.canSurvive(gen, heightPos) && gen.getBlockState(heightPos.below()).isSolid()) {
                if (Tmp.rnd.chance(ServerConfig.POT_SPAWN_CHANCE.get())) {
                    gen.setBlock(heightPos, state, 2);
                    return true;
                }
            }
        }

        return false;
    }
}