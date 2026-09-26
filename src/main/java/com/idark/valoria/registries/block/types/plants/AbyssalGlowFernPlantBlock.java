package com.idark.valoria.registries.block.types.plants;

import com.idark.valoria.registries.*;
import com.mojang.serialization.*;
import net.minecraft.core.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.phys.shapes.*;

public class AbyssalGlowFernPlantBlock extends GrowingPlantBodyBlock{
    public static final MapCodec<AbyssalGlowFernPlantBlock> CODEC = simpleCodec(AbyssalGlowFernPlantBlock::new);
    @Override protected MapCodec<? extends AbyssalGlowFernPlantBlock> codec(){ return CODEC; }


    public AbyssalGlowFernPlantBlock(Properties pProperties){
        super(pProperties, Direction.UP, Shapes.block(), true);
    }

    @Override
    protected GrowingPlantHeadBlock getHeadBlock(){
        return (GrowingPlantHeadBlock)BlockRegistry.abyssalGlowfern.get();
    }
}