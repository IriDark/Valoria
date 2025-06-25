package com.idark.valoria.registries.block.types.plants;

import com.idark.valoria.registries.*;
import net.minecraft.core.*;
import net.minecraft.server.level.*;
import net.minecraft.util.*;
import net.minecraft.world.level.block.state.*;

public class TallAloeBlock extends TallSandFlowerBlock{
    public TallAloeBlock(Properties properties){
        super(properties.randomTicks());
    }

    /**
     * Performs a random tick on a block.
     */
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        BlockState aloeSmall = BlockRegistry.aloeSmall.get().defaultBlockState();
        if (pRandom.nextInt(25) == 0) {
            int i = 5;
            for(BlockPos blockpos : BlockPos.betweenClosed(pPos.offset(-4, -1, -4), pPos.offset(4, 1, 4))) {
                if (pLevel.getBlockState(blockpos).is(this)) {
                    --i;
                    if (i <= 0) {
                        return;
                    }
                }
            }

            BlockPos blockpos1 = pPos.offset(pRandom.nextInt(3) - 1, pRandom.nextInt(2) - pRandom.nextInt(2), pRandom.nextInt(3) - 1);
            for(int k = 0; k < 4; ++k) {
                if (pLevel.isEmptyBlock(blockpos1) && aloeSmall.canSurvive(pLevel, blockpos1)) {
                    pPos = blockpos1;
                }

                blockpos1 = pPos.offset(pRandom.nextInt(3) - 1, pRandom.nextInt(2) - pRandom.nextInt(2), pRandom.nextInt(3) - 1);
            }

            if (pLevel.getBlockState(blockpos1).canBeReplaced() && aloeSmall.canSurvive(pLevel, blockpos1)) {
                pLevel.setBlock(blockpos1, aloeSmall, 2);
            }
        }

    }
}