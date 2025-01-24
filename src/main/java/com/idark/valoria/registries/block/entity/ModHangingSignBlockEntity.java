package com.idark.valoria.registries.block.entity;

import com.idark.valoria.registries.BlockEntitiesRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.HangingSignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ModHangingSignBlockEntity extends HangingSignBlockEntity{
    public ModHangingSignBlockEntity(BlockPos pPos, BlockState pState){
        super(pPos, pState);
    }

    @Override
    public BlockEntityType<?> getType(){
        return BlockEntitiesRegistry.HANGING_SIGN_BLOCK_ENTITIES.get();
    }
}