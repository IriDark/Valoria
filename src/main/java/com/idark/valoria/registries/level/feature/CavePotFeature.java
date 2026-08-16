package com.idark.valoria.registries.level.feature;

import com.idark.valoria.core.config.*;
import com.mojang.serialization.*;
import net.minecraft.core.*;
import net.minecraft.tags.*;
import net.minecraft.util.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import pro.komaru.tridot.util.*;

public class CavePotFeature extends PotFeature {
    public CavePotFeature(Codec<SimpleBlockConfiguration> codec){
        super(codec);
    }

    @Override
    protected int getTries(){
        return ServerConfig.CAVE_POT_TRIES.get();
    }

    @Override
    protected boolean tryPlacePot(WorldGenLevel gen, RandomSource random, SimpleBlockConfiguration config, BlockPos.MutableBlockPos mutablePos, int x, int startY, int z) {
        mutablePos.set(x, startY, z);
        for (int i = 0; i < 32; i++) {
            if (gen.isEmptyBlock(mutablePos) || gen.getBlockState(mutablePos).getCollisionShape(gen, mutablePos).isEmpty()) {
                if (gen.isFluidAtPosition(mutablePos, fluid -> fluid.is(FluidTags.LAVA))) {
                    break;
                }

                BlockState state = config.toPlace().getState(random, mutablePos);
                if (state.canSurvive(gen, mutablePos) && gen.getBlockState(mutablePos.below()).isSolid()) {
                    if (Tmp.rnd.chance(ServerConfig.CAVE_POT_SPAWN_CHANCE.get())) {
                        gen.setBlock(mutablePos, state, 2);
                        return true;
                    }
                    break;
                }
            }

            mutablePos.move(Direction.DOWN);
            if (mutablePos.getY() <= gen.getMinBuildHeight()) break;
        }

        return false;
    }
}