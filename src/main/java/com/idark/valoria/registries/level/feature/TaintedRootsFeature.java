package com.idark.valoria.registries.level.feature;

import com.idark.valoria.registries.*;
import com.idark.valoria.registries.block.types.plants.*;
import com.idark.valoria.registries.level.configurations.*;
import com.mojang.serialization.*;
import net.minecraft.core.*;
import net.minecraft.util.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.levelgen.feature.*;
import pro.komaru.tridot.util.*;

public class TaintedRootsFeature extends Feature<TaintedRootsConfig>{

    public TaintedRootsFeature(Codec<TaintedRootsConfig> pCodec){
        super(pCodec);
    }

    private static boolean findFirstAirBlockAboveGround(LevelAccessor pLevel, BlockPos.MutableBlockPos pPos){
        do{
            pPos.move(0, -1, 0);
            if(pLevel.isOutsideBuildHeight(pPos)){
                return false;
            }
        }while(pLevel.getBlockState(pPos).isAir());

        pPos.move(0, 1, 0);
        return true;
    }

    public static void placeRoot(LevelAccessor pLevel, RandomSource pRandom, BlockPos.MutableBlockPos pPos, int pMinAge, int pMaxAge){
        if(!pLevel.isEmptyBlock(pPos.above())){
            pLevel.setBlock(pPos, BlockRegistry.taintedRoots.get().defaultBlockState().setValue(TaintedRootsBlock.AGE, Mth.nextInt(pRandom, pMinAge, pMaxAge)), 2);
        }

        pPos.move(Direction.UP);
    }

    private static boolean isInvalidPlacementLocation(LevelAccessor pLevel, BlockPos pPos){
        if(!pLevel.isEmptyBlock(pPos)){
            return true;
        }else{
            BlockState blockstate = pLevel.getBlockState(pPos.below());
            return !blockstate.is(BlockRegistry.voidStone.get()) && !blockstate.is(BlockRegistry.voidTaint.get());
        }
    }

    public boolean place(FeaturePlaceContext<TaintedRootsConfig> pContext){
        WorldGenLevel worldgenlevel = pContext.level();
        BlockPos blockpos = pContext.origin();
        if(isInvalidPlacementLocation(worldgenlevel, blockpos)){
            return false;
        }else{
            RandomSource randomsource = pContext.random();
            TaintedRootsConfig roots = pContext.config();
            int i = roots.spreadWidth();
            int j = roots.spreadHeight();
            BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

            int step = 3;
            for(int x = -i; x <= i; x += step){
                for(int y = -j; y <= j; y += step){
                    for(int z = -i; z <= i; z += step){
                        blockpos$mutableblockpos.set(blockpos).move(Mth.nextInt(randomsource, -i, i), Mth.nextInt(randomsource, -j, j), Mth.nextInt(randomsource, -i, i));
                        if(findFirstAirBlockAboveGround(worldgenlevel, blockpos$mutableblockpos) && !isInvalidPlacementLocation(worldgenlevel, blockpos$mutableblockpos)){
                            if(Tmp.rnd.fiftyFifty()){
                                placeRoot(worldgenlevel, randomsource, blockpos$mutableblockpos, 0, 2);
                            }
                        }
                    }
                }
            }

            return true;
        }
    }
}
