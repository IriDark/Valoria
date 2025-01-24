package com.idark.valoria.registries.block.entity;

import com.idark.valoria.registries.BlockEntitiesRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class ValoriaPortalBlockEntity extends BlockEntity{
    protected ValoriaPortalBlockEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState){
        super(pType, pPos, pBlockState);
    }

    public ValoriaPortalBlockEntity(BlockPos pPos, BlockState pBlockState){
        this(BlockEntitiesRegistry.VALORIA_PORTAL_BLOCK_ENTITY.get(), pPos, pBlockState);
    }

    public boolean shouldRenderFace(Direction pFace){
        return pFace.getAxis() == Direction.Axis.Y;
    }
}