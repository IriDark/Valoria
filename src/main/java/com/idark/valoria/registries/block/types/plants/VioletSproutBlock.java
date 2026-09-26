package com.idark.valoria.registries.block.types.plants;

import com.idark.valoria.registries.*;
import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import net.minecraft.core.*;
import net.minecraft.util.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.shapes.*;

public class VioletSproutBlock extends GrowingPlantHeadBlock{
   public static final MapCodec<VioletSproutBlock> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
        propertiesCodec(),
        Codec.BOOL.fieldOf("glow").forGetter(b -> b.pGlow)
    ).apply(i, VioletSproutBlock::new));
    protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 9.0D, 16.0D);
    boolean pGlow;

    @Override
    protected MapCodec<? extends VioletSproutBlock> codec(){
        return CODEC;
    }

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
        return pGlow ? BlockRegistry.glowVioletSproutPlant.get() : BlockRegistry.violetSproutPlant.get();
    }
}
