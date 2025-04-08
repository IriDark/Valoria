package com.idark.valoria.registries.block.types.plants;

import com.idark.valoria.registries.*;
import net.minecraft.core.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.shapes.*;

public class CaveRootPlantBlock extends GrowingPlantBodyBlock{
    public static final VoxelShape SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);

    public CaveRootPlantBlock(BlockBehaviour.Properties p_154975_){
        super(p_154975_, Direction.DOWN, SHAPE, false);
    }

    protected GrowingPlantHeadBlock getHeadBlock(){
        return (GrowingPlantHeadBlock)BlockRegistry.caveRoot.get();
    }
}
