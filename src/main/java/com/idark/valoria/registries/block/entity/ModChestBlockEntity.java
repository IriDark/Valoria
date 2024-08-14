package com.idark.valoria.registries.block.entity;

import com.idark.valoria.registries.*;
import net.minecraft.core.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.*;

public class ModChestBlockEntity extends ChestBlockEntity{
    public ModChestBlockEntity(BlockPos pPos, BlockState pBlockState){
        super(BlockEntitiesRegistry.CHEST_BLOCK_ENTITY.get(), pPos, pBlockState);
    }

    @Override
    public BlockEntityType<?> getType(){
        return BlockEntitiesRegistry.CHEST_BLOCK_ENTITY.get();
    }
}