package com.idark.valoria.registries.block.types.plants;

import com.idark.valoria.registries.*;
import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import net.minecraft.core.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.phys.shapes.*;

public class VioletSproutPlantBlock extends GrowingPlantBodyBlock{
    public static final MapCodec<VioletSproutPlantBlock> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
        propertiesCodec(),
        Codec.BOOL.fieldOf("glow").forGetter(b -> b.pGlow)
    ).apply(i, VioletSproutPlantBlock::new));

    boolean pGlow;

    @Override
    protected MapCodec<? extends VioletSproutPlantBlock> codec(){
        return CODEC;
    }

    public VioletSproutPlantBlock(Properties pProperties, Boolean pGlow){
        super(pProperties, Direction.UP, Shapes.block(), true);
        this.pGlow = pGlow;
    }

    @Override
    protected GrowingPlantHeadBlock getHeadBlock(){
        return pGlow ? (GrowingPlantHeadBlock)BlockRegistry.glowVioletSprout.get() : (GrowingPlantHeadBlock)BlockRegistry.violetSprout.get();
    }
}
