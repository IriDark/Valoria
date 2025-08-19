package com.idark.valoria.registries.block.types;

import net.minecraft.core.*;
import net.minecraft.server.level.*;
import net.minecraft.tags.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.item.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;

public class VoidSandBlock extends SandBlock{
    public VoidSandBlock(int pDustColor, Properties pProperties){
        super(pDustColor, pProperties);
    }

    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (isFree(pLevel.getBlockState(pPos.below())) && pPos.getY() >= pLevel.getMinBuildHeight()) {
            FallingBlockEntity fallingblockentity = FallingBlockEntity.fall(pLevel, pPos, pState);
            this.falling(fallingblockentity);
        }
    }

    public static boolean isFree(BlockState pState) {
        return pState.isAir() || pState.is(BlockTags.FIRE) || pState.canBeReplaced();
    }

    @Override
    protected int getDelayAfterPlace(){
        return 60;
    }
}
