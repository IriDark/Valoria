package com.idark.darkrpg.block;

import net.minecraft.block.*;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
	
public class AwakeningBlock extends Block {
    private static IntegerProperty STATE = IntegerProperty.create("awakened", 0, 1);

    public AwakeningBlock(AbstractBlock.Properties properties) {
		super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(STATE, 0));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(STATE);
        super.fillStateContainer(builder);
    }
}