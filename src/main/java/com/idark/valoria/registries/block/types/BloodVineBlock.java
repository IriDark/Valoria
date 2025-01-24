package com.idark.valoria.registries.block.types;

import com.idark.valoria.registries.BlockRegistry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WeepingVinesBlock;

public class BloodVineBlock extends WeepingVinesBlock{
    public BloodVineBlock(Properties p_154966_){
        super(p_154966_);
    }

    protected Block getBodyBlock(){
        return BlockRegistry.bloodVinePlant.get();
    }
}
