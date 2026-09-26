package com.idark.valoria.registries.block.entity;

import com.idark.valoria.client.ui.menus.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.item.recipe.*;
import net.minecraft.core.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.inventory.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.*;
import pro.komaru.tridot.common.registry.block.entity.*;

public class KilnBlockEntity extends AbstractFurnaceBlockEntity implements TickableBlockEntity{
    public KilnBlockEntity(BlockPos pPos, BlockState pBlockState){
        super(BlockEntitiesRegistry.KILN.get(), pPos, pBlockState, KilnRecipe.Type.INSTANCE);
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("menu.valoria.kiln");
    }

    @Override
    protected AbstractContainerMenu createMenu(int pId, Inventory pPlayer) {
        return new KilnMenu(pId, pPlayer, this, this.dataAccess);
    }

    @Override
    public void tick(){
        if(this.level != null && !this.level.isClientSide){
            serverTick(this.level, this.worldPosition, this.getBlockState(), this);
        }
    }
}
