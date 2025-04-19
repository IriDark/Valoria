package com.idark.valoria.registries.block.types;

import com.idark.valoria.registries.*;
import net.minecraft.core.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;

public class VoidSandBlock extends FallingBlock{
    private final int dustColor;

    public VoidSandBlock(int pDustColor, BlockBehaviour.Properties pProperties){
        super(pProperties);
        this.dustColor = pDustColor;
    }

    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        if(!pFacingState.is(TagsRegistry.VOID_BLOCKS)){
            pLevel.scheduleTick(pCurrentPos, this, this.getDelayAfterPlace());
            return super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
        }

        return pState;
    }

    public int getDustColor(BlockState pState, BlockGetter pReader, BlockPos pPos){
        return this.dustColor;
    }
}