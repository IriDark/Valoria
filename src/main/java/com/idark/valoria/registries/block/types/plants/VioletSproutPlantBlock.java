package com.idark.valoria.registries.block.types.plants;

import com.idark.valoria.registries.*;
import net.minecraft.core.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.phys.shapes.*;

public class VioletSproutPlantBlock extends GrowingPlantBodyBlock {

    boolean pGlow;

    public VioletSproutPlantBlock(Properties pProperties, Boolean pGlow) {
        super(pProperties, Direction.UP, Shapes.block(), true);
        this.pGlow = pGlow;
    }

    @Override
    protected GrowingPlantHeadBlock getHeadBlock() {
        return pGlow ? (GrowingPlantHeadBlock) BlockRegistry.glowVioletSprout.get() : (GrowingPlantHeadBlock) BlockRegistry.violetSprout.get();
    }
}
