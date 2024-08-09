package com.idark.valoria.registries.block.entity;

import com.idark.valoria.registries.*;
import net.minecraft.core.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.*;

public class ModHangingSignBlockEntity extends HangingSignBlockEntity{
    public ModHangingSignBlockEntity(BlockPos pPos, BlockState pState){
        super(pPos, pState);
    }

    @Override
    public BlockEntityType<?> getType(){
        return BlockEntitiesRegistry.HANGING_SIGN_BLOCK_ENTITIES.get();
    }
}