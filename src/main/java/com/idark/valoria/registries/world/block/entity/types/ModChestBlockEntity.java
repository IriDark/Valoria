package com.idark.valoria.registries.world.block.entity.types;

import com.idark.valoria.compat.quark.QuarkIntegration;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ModChestBlockEntity extends ChestBlockEntity {
    public ModChestBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(QuarkIntegration.LoadedOnly.CHEST_BLOCK_ENTITY.get(), pPos, pBlockState);
    }

    @Override
    public BlockEntityType<?> getType() {
        return QuarkIntegration.LoadedOnly.CHEST_BLOCK_ENTITY.get();
    }
}
