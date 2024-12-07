package com.idark.valoria.registries.level.feature;

import com.idark.valoria.core.interfaces.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.block.types.*;
import com.idark.valoria.registries.level.configurations.*;
import com.mojang.serialization.*;
import net.minecraft.core.*;
import net.minecraft.util.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.levelgen.feature.*;

public class FleshFeature extends Feature<FleshConfiguration>{
    public FleshFeature(Codec<FleshConfiguration> pCodec) {
        super(pCodec);
    }

    /**
     * Places the given feature at the given location.
     * During world generation, features are provided with a 3x3 region of chunks, centered on the chunk being generated,
     * that they can safely generate into.
     * @param pContext A context object with a reference to the level and the position the feature is being placed at
     */
    public boolean place(FeaturePlaceContext<FleshConfiguration> pContext) {
        WorldGenLevel worldgenlevel = pContext.level();
        BlockPos blockpos = pContext.origin();
        if (!this.canSpreadFrom(worldgenlevel, blockpos)) {
            return false;
        } else {
            FleshConfiguration config = pContext.config();
            RandomSource randomsource = pContext.random();
            FleshSpreader flesh = FleshSpreader.createWorldGenSpreader();
            int i = config.spreadRounds() + config.growthRounds();

            for(int j = 0; j < i; ++j) {
                for(int k = 0; k < config.chargeCount(); ++k) {
                    flesh.addCursors(blockpos, config.amountPerCharge());
                }

                boolean flag = j < config.spreadRounds();

                for(int l = 0; l < config.spreadAttempts(); ++l) {
                    flesh.updateCursors(worldgenlevel, blockpos, randomsource, flag);
                }

                flesh.clear();
            }

            BlockPos blockpos2 = blockpos.below();
            if (randomsource.nextFloat() <= config.catalystChance() && worldgenlevel.getBlockState(blockpos2).isCollisionShapeFullBlock(worldgenlevel, blockpos2) && worldgenlevel.getBlockState(blockpos.above()).isAir()) {
                worldgenlevel.setBlock(blockpos, BlockRegistry.fleshCyst.get().defaultBlockState(), 3);
            }

            int i1 = config.extraRareGrowths().sample(randomsource);
            for(int j1 = 0; j1 < i1; ++j1) {
                BlockPos blockpos1 = blockpos.offset(randomsource.nextInt(5) - 2, 0, randomsource.nextInt(5) - 2);
                if (worldgenlevel.getBlockState(blockpos1).isAir() && worldgenlevel.getBlockState(blockpos1.below()).isFaceSturdy(worldgenlevel, blockpos1.below(), Direction.UP)) {
                    worldgenlevel.setBlock(blockpos1, BlockRegistry.fleshCyst.get().defaultBlockState(), 3);
                }
            }

            return true;
        }
    }

    private boolean canSpreadFrom(LevelAccessor pLevel, BlockPos pPos) {
        BlockState blockstate = pLevel.getBlockState(pPos);
        if (blockstate.getBlock() instanceof FleshSpreaderBehaviour) {
            return true;
        } else {
            return !blockstate.isAir() && (!blockstate.is(Blocks.WATER) || !blockstate.getFluidState().isSource()) ? false : Direction.stream().map(pPos::relative).anyMatch((p_225245_) -> pLevel.getBlockState(p_225245_).isCollisionShapeFullBlock(pLevel, p_225245_));
        }
    }
}