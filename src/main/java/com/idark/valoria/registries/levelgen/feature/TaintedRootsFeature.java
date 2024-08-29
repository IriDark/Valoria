package com.idark.valoria.registries.levelgen.feature;

import com.idark.valoria.registries.*;
import com.idark.valoria.registries.block.types.*;
import com.idark.valoria.registries.levelgen.configurations.*;
import com.idark.valoria.util.*;
import com.mojang.serialization.*;
import net.minecraft.core.*;
import net.minecraft.util.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.levelgen.feature.*;

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
            pLevel.setBlock(pPos, BlockRegistry.TAINTED_ROOTS.get().defaultBlockState().setValue(TaintedRootsBlock.AGE, Mth.nextInt(pRandom, pMinAge, pMaxAge)), 2);
        }

        pPos.move(Direction.UP);
    }

    private static boolean isInvalidPlacementLocation(LevelAccessor pLevel, BlockPos pPos){
        if(!pLevel.isEmptyBlock(pPos)){
            return true;
        }else{
            BlockState blockstate = pLevel.getBlockState(pPos.below());
            return !blockstate.is(BlockRegistry.VOID_STONE.get()) && !blockstate.is(BlockRegistry.VOID_TAINT.get());
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
                            if(new ArcRandom().fiftyFifty()){
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
