package com.idark.valoria.registries.block.types;

import com.idark.valoria.registries.*;
import net.minecraft.world.level.block.*;

public class BloodVineBlock extends WeepingVinesBlock{
    public BloodVineBlock(Properties p_154966_){
        super(p_154966_);
    }

    protected Block getBodyBlock() {
        return BlockRegistry.bloodVinePlant.get();
    }
}
