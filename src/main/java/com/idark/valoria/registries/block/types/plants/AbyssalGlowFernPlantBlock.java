package com.idark.valoria.registries.block.types.plants;

import com.idark.valoria.registries.*;
import net.minecraft.core.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.phys.shapes.*;

public class AbyssalGlowFernPlantBlock extends GrowingPlantBodyBlock {

    public AbyssalGlowFernPlantBlock(Properties pProperties) {
        super(pProperties, Direction.UP, Shapes.block(), true);
    }

    @Override
    protected GrowingPlantHeadBlock getHeadBlock() {
        return (GrowingPlantHeadBlock) BlockRegistry.ABYSSAL_GLOWFERN.get();
    }
}