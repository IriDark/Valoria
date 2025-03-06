package com.idark.valoria.registries.block.types;

import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.properties.*;

public class VoidTaintBlock extends Block{
    public static final IntegerProperty TAINT = IntegerProperty.create("taint", 0, 1);
    public VoidTaintBlock(BlockBehaviour.Properties properties){
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(TAINT, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder){
        builder.add(TAINT);
        super.createBlockStateDefinition(builder);
    }
}