package com.idark.valoria.registries.block.entity.types;

import com.idark.valoria.registries.block.entity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ModSignBlockEntity extends SignBlockEntity {
    public ModSignBlockEntity(BlockPos pPos, BlockState pState) {
        super(pPos, pState);
    }

    @Override
    public BlockEntityType<?> getType() {
        return ModBlockEntities.SIGN_BLOCK_ENTITIES.get();
    }
}