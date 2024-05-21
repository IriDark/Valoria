package com.idark.valoria.compat.jade;

import com.idark.valoria.registries.block.entity.*;
import net.minecraft.core.*;
import net.minecraft.nbt.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;
import snownee.jade.api.*;
import snownee.jade.api.config.*;
import snownee.jade.api.ui.*;

public enum CrusherProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor>{

    INSTANCE;

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config){
        CompoundTag data = accessor.getServerData();
        CrusherBlockEntity crusherBlock = (CrusherBlockEntity)accessor.getBlockEntity();
        if(!crusherBlock.getItemHandler().getItem(0).isEmpty()){
            ListTag crusherBlockItems = data.getList("crusherBlock", Tag.TAG_COMPOUND);
            NonNullList<ItemStack> inventory = NonNullList.withSize(1, ItemStack.EMPTY);
            for(int i = 0; i < crusherBlockItems.size(); i++)
                inventory.set(i, ItemStack.of(crusherBlockItems.getCompound(i)));

            IElementHelper helper = IElementHelper.get();
            tooltip.add(helper.item(inventory.get(0)));
        }
    }

    @Override
    public void appendServerData(CompoundTag data, BlockAccessor accessor){
        CrusherBlockEntity crusherBlock = (CrusherBlockEntity)accessor.getBlockEntity();
        if(!crusherBlock.getItemHandler().getItem(0).isEmpty()){
            ListTag items = new ListTag();
            items.add(crusherBlock.getItemHandler().getItem(0).save(new CompoundTag()));
            data.put("crusherBlock", items);
        }
    }

    @Override
    public ResourceLocation getUid(){
        return ModPlugin.CRUSHER;
    }
}