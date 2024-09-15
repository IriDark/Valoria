package com.idark.valoria.registries.block.entity;

import com.idark.valoria.registries.BlockEntitiesRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ModChestBlockEntity extends ChestBlockEntity {
    public ModChestBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntitiesRegistry.CHEST_BLOCK_ENTITY.get(), pPos, pBlockState);
    }

    @Override
    public BlockEntityType<?> getType() {
        return BlockEntitiesRegistry.CHEST_BLOCK_ENTITY.get();
    }
}