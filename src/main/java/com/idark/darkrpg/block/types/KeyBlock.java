package com.idark.darkrpg.block.types;

import net.minecraft.block.*;

public class KeyBlock extends Block {

    public KeyBlock(AbstractBlock.Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState());
    }
}