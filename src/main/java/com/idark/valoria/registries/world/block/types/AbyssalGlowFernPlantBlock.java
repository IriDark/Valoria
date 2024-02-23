package com.idark.valoria.registries.world.block.types;

import com.idark.valoria.registries.world.block.ModBlocks;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.GrowingPlantBodyBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.phys.shapes.Shapes;

public class AbyssalGlowFernPlantBlock extends GrowingPlantBodyBlock {

    public AbyssalGlowFernPlantBlock(Properties pProperties) {
        super(pProperties, Direction.UP, Shapes.block(), true);
    }

    @Override
    protected GrowingPlantHeadBlock getHeadBlock() {
        return (GrowingPlantHeadBlock) ModBlocks.ABYSSAL_GLOWFERN.get();
    }
}
