package com.idark.valoria.registries.level.feature;

import com.idark.valoria.registries.*;
import com.idark.valoria.registries.block.types.*;
import com.mojang.serialization.*;
import net.minecraft.core.*;
import net.minecraft.util.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.feature.*;
import net.minecraft.world.level.levelgen.feature.configurations.*;

public class CattailFeature extends Feature<RandomPatchConfiguration> {

    public CattailFeature(Codec<RandomPatchConfiguration> configFactoryIn) {
        super(configFactoryIn);
    }

    @Override
    public boolean place(FeaturePlaceContext<RandomPatchConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        RandomPatchConfiguration config = context.config();
        RandomSource rand = context.random();
        BlockPos blockpos = level.getHeightmapPos(Heightmap.Types.OCEAN_FLOOR_WG, origin);

        int i = 0;
        BlockPos.MutableBlockPos blockpos$mutable = new BlockPos.MutableBlockPos();
        for (int j = 0; j < config.tries(); ++j) {
            blockpos$mutable.set(blockpos).move(rand.nextInt(config.xzSpread() + 1) - rand.nextInt(config.xzSpread() + 1), rand.nextInt(config.ySpread() + 1) - rand.nextInt(config.ySpread() + 1), rand.nextInt(config.xzSpread() + 1) - rand.nextInt(config.xzSpread() + 1));
            if (level.getBlockState(blockpos$mutable).getBlock() == Blocks.WATER && level.getBlockState(blockpos$mutable.above()).getBlock() == Blocks.AIR) {
                BlockState bottom = BlockRegistry.CATTAIL.get().defaultBlockState().setValue(TallWaterFlowerBlock.HALF, DoubleBlockHalf.LOWER);
                if (bottom.canSurvive(level, blockpos$mutable)) {
                    DoublePlantBlock.placeAt(level, bottom, blockpos$mutable, 2);
                    ++i;
                }
            }
        }

        return i > 0;
    }
}