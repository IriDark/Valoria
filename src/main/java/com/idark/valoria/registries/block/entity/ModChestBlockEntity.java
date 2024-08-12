package com.idark.valoria.registries.block.entity;

import net.minecraft.core.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.*;

//todo
public class ModChestBlockEntity extends ChestBlockEntity{
    public ModChestBlockEntity(BlockPos pPos, BlockState pBlockState){
        super(QuarkIntegration.LoadedOnly.CHEST_BLOCK_ENTITY.get(), pPos, pBlockState);
    }

    @Override
    public BlockEntityType<?> getType(){
        return QuarkIntegration.LoadedOnly.CHEST_BLOCK_ENTITY.get();
    }
}
