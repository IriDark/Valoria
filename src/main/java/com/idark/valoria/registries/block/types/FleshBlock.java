package com.idark.valoria.registries.block.types;

import com.idark.valoria.core.interfaces.*;
import com.idark.valoria.registries.*;
import net.minecraft.core.*;
import net.minecraft.sounds.*;
import net.minecraft.util.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.*;
import pro.komaru.tridot.core.math.*;

public class FleshBlock extends Block implements FleshSpreaderBehaviour{
    public FleshBlock(Properties pProperties){
        super(pProperties);
    }

    public int attemptUseCharge(FleshSpreader.ChargeCursor pCursor, LevelAccessor pLevel, BlockPos pPos, RandomSource pRandom, FleshSpreader pSpreader, boolean pShouldConvertBlocks){
        int i = pCursor.getCharge();
        if(i != 0 && pRandom.nextInt(pSpreader.chargeDecayRate()) == 0){
            BlockPos blockpos = pCursor.getPos();
            boolean flag = blockpos.closerThan(pPos, pSpreader.noGrowthRadius());
            if(!flag && canPlaceGrowth(pLevel, blockpos)){
                int j = pSpreader.growthSpawnCost();
                if(pRandom.nextInt(j) < i){
                    BlockPos blockpos1 = blockpos.above();
                    BlockState blockstate = this.getRandomGrowthState(pLevel, blockpos1, pRandom);
                    pLevel.setBlock(blockpos1, blockstate, 3);
                    pLevel.playSound(null, blockpos, blockstate.getSoundType().getPlaceSound(), SoundSource.BLOCKS, 1.0F, 1.0F);
                }

                return Math.max(0, i - j);
            }else{
                return pRandom.nextInt(pSpreader.additionalDecayRate()) != 0 ? i : i - (flag ? 1 : getDecayPenalty(pSpreader, blockpos, pPos, i));
            }
        }else{
            return i;
        }
    }

    private static int getDecayPenalty(FleshSpreader pSpreader, BlockPos pCursorPos, BlockPos pRootPos, int pCharge){
        int i = pSpreader.noGrowthRadius();
        float f = Mth.square((float)Math.sqrt(pCursorPos.distSqr(pRootPos)) - (float)i);
        int j = Mth.square(24 - i);
        float f1 = Math.min(1.0F, f / (float)j);
        return Math.max(1, (int)((float)pCharge * f1 * 0.5F));
    }

    private BlockState getRandomGrowthState(LevelAccessor pLevel, BlockPos pPos, RandomSource pRandom){
        BlockState blockstate;
        if(new ArcRandom().chance(25)){
            blockstate = BlockRegistry.fleshCyst.get().defaultBlockState();
        }else{
            blockstate = BlockRegistry.bloodVein.get().defaultBlockState();
        }

        return blockstate.hasProperty(BlockStateProperties.WATERLOGGED) && !pLevel.getFluidState(pPos).isEmpty() ? blockstate.setValue(BlockStateProperties.WATERLOGGED, Boolean.TRUE) : blockstate;
    }

    private static boolean canPlaceGrowth(LevelAccessor pLevel, BlockPos pPos){
        BlockState blockstate = pLevel.getBlockState(pPos.above());
        if(blockstate.isAir() || blockstate.is(Blocks.WATER) && blockstate.getFluidState().is(Fluids.WATER)){
            int i = 0;

            for(BlockPos blockpos : BlockPos.betweenClosed(pPos.offset(-4, 0, -4), pPos.offset(4, 2, 4))){
                BlockState blockstate1 = pLevel.getBlockState(blockpos);
                if(!blockstate.is(BlockRegistry.fleshCyst.get()) || !blockstate1.is(BlockRegistry.bloodVein.get())){
                    ++i;
                }

                if(i > 2){
                    return false;
                }
            }

            return true;
        }else{
            return false;
        }
    }

    public boolean canChangeBlockStateOnSpread(){
        return false;
    }
}
