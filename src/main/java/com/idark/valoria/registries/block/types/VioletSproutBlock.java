package com.idark.valoria.registries.block.types;

import com.idark.valoria.registries.*;
import net.minecraft.core.*;
import net.minecraft.util.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.shapes.*;

public class VioletSproutBlock extends GrowingPlantHeadBlock{
    protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 9.0D, 16.0D);
    boolean pGlow;

    public VioletSproutBlock(BlockBehaviour.Properties p_54300_, boolean pGlow){
        super(p_54300_, Direction.UP, SHAPE, true, 0.14D);
        this.pGlow = pGlow;
    }

    @Override
    protected int getBlocksToGrowWhenBonemealed(RandomSource pRandom){
        return 1;
    }

    @Override
    protected boolean canGrowInto(BlockState pState){
        return pState.isAir();
    }

    @Override
    protected Block getBodyBlock(){
        return pGlow ? BlockRegistry.GLOW_VIOLET_SPROUT_PLANT.get() : BlockRegistry.VIOLET_SPROUT_PLANT.get();
    }
}
