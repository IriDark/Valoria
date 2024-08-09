package com.idark.valoria.registries.block.entity;

import com.idark.valoria.registries.*;
import net.minecraft.core.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.*;

public class ModSignBlockEntity extends SignBlockEntity{
    public ModSignBlockEntity(BlockPos pPos, BlockState pState){
        super(pPos, pState);
    }

    @Override
    public BlockEntityType<?> getType(){
        return BlockEntitiesRegistry.SIGN_BLOCK_ENTITIES.get();
    }
}