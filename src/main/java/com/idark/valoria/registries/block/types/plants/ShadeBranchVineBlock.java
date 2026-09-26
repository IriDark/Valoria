package com.idark.valoria.registries.block.types.plants;

import com.idark.valoria.registries.*;
import com.mojang.serialization.*;
import net.minecraft.core.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.shapes.*;

public class ShadeBranchVineBlock extends GrowingPlantBodyBlock{
    public static final MapCodec<ShadeBranchVineBlock> CODEC = simpleCodec(ShadeBranchVineBlock::new); // PORT NOTE: BlockBehaviour#codec() is abstract in 1.21
    @Override protected MapCodec<? extends ShadeBranchVineBlock> codec(){ return CODEC; }

    public static final VoxelShape SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);
    public ShadeBranchVineBlock(BlockBehaviour.Properties p_153000_){
        super(p_153000_, Direction.DOWN, SHAPE, false);
    }

    @Override
    protected GrowingPlantHeadBlock getHeadBlock(){
        return (GrowingPlantHeadBlock)BlockRegistry.shadeBranch.get();
    }
}