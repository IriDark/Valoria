package com.idark.valoria.core.interfaces;

import com.idark.valoria.registries.*;
import com.idark.valoria.registries.block.types.*;
import net.minecraft.core.*;
import net.minecraft.util.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.material.*;
import pro.komaru.tridot.core.math.*;

import javax.annotation.*;
import java.util.*;

public interface FleshSpreaderBehaviour{
    FleshSpreaderBehaviour DEFAULT = new FleshSpreaderBehaviour(){
        public boolean attemptSpreadVein(LevelAccessor p_222048_, BlockPos p_222049_, BlockState p_222050_, @Nullable Collection<Direction> p_222051_, boolean p_222052_){
            if(new ArcRandom().chance(0.25f)){
                if(p_222051_ == null){
                    return ((BloodVeinBlock)BlockRegistry.bloodVein.get()).getSameSpaceSpreader().spreadAll(p_222048_.getBlockState(p_222049_), p_222048_, p_222049_, p_222052_) > 0L;
                }else if(!p_222051_.isEmpty()){
                    return (p_222050_.isAir() || p_222050_.getFluidState().is(Fluids.WATER)) && BloodVeinBlock.regrow(p_222048_, p_222049_, p_222050_, p_222051_);
                }

                return FleshSpreaderBehaviour.super.attemptSpreadVein(p_222048_, p_222049_, p_222050_, p_222051_, p_222052_);
            }

            return false;
        }

        public int attemptUseCharge(FleshSpreader.ChargeCursor p_222054_, LevelAccessor p_222055_, BlockPos p_222056_, RandomSource p_222057_, FleshSpreader p_222058_, boolean p_222059_){
            return p_222054_.getDecayDelay() > 0 ? p_222054_.getCharge() : 0;
        }

        public int updateDecayDelay(int p_222061_){
            return Math.max(p_222061_ - 1, 0);
        }
    };

    default byte getSpreadDelay(){
        return 1;
    }

    default void onDischarged(LevelAccessor pLevel, BlockState pState, BlockPos pPos, RandomSource pRandom){
    }

    default boolean depositCharge(LevelAccessor pLevel, BlockPos pPos, RandomSource pRandom){
        return false;
    }

    default boolean attemptSpreadVein(LevelAccessor pLevel, BlockPos pPos, BlockState pState, @Nullable Collection<Direction> pDirections, boolean pMarkForPostprocessing){
        return ((MultifaceBlock)BlockRegistry.bloodVein.get()).getSpreader().spreadAll(pState, pLevel, pPos, pMarkForPostprocessing) > 0L;
    }

    default boolean canChangeBlockStateOnSpread(){
        return true;
    }

    default int updateDecayDelay(int pCurrentDecayDelay){
        return 1;
    }

    int attemptUseCharge(FleshSpreader.ChargeCursor pCursor, LevelAccessor pLevel, BlockPos pPos, RandomSource pRandom, FleshSpreader pSpreader, boolean pShouldConvertBlocks);
}