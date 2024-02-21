package com.idark.valoria.registries.world.block.types;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class VoidTaintBlock extends Block {

    private static final IntegerProperty TAINT = IntegerProperty.create("taint", 0, 1);

    public VoidTaintBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(TAINT, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TAINT);
        super.createBlockStateDefinition(builder);
    }
}