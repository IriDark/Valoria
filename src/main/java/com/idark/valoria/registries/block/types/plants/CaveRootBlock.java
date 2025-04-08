package com.idark.valoria.registries.block.types.plants;

import com.idark.valoria.registries.*;
import net.minecraft.world.level.block.*;

public class CaveRootBlock extends WeepingVinesBlock{
    public CaveRootBlock(Properties p_154966_){
        super(p_154966_);
    }

    protected Block getBodyBlock(){
        return BlockRegistry.caveRootPlant.get();
    }
}
