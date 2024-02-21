package com.idark.valoria.client.compat.jade;

import com.idark.valoria.registries.world.block.entity.CrusherBlockEntity;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.IElementHelper;

public enum CrusherProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {

    INSTANCE;

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        CompoundTag data = accessor.getServerData();
        CrusherBlockEntity crusherBlock = (CrusherBlockEntity) accessor.getBlockEntity();
        if (!crusherBlock.getItemHandler().getItem(0).isEmpty()) {
            ListTag crusherBlockItems = data.getList("crusherBlock", Tag.TAG_COMPOUND);
            NonNullList<ItemStack> inventory = NonNullList.withSize(1, ItemStack.EMPTY);
            for (int i = 0; i < crusherBlockItems.size(); i++)
                inventory.set(i, ItemStack.of(crusherBlockItems.getCompound(i)));

            IElementHelper helper = IElementHelper.get();
            tooltip.add(helper.item(inventory.get(0)));
        }
    }

    @Override
    public void appendServerData(CompoundTag data, BlockAccessor accessor) {
        CrusherBlockEntity crusherBlock = (CrusherBlockEntity) accessor.getBlockEntity();
        if (!crusherBlock.getItemHandler().getItem(0).isEmpty()) {
            ListTag items = new ListTag();
            items.add(crusherBlock.getItemHandler().getItem(0).save(new CompoundTag()));
            data.put("crusherBlock", items);
        }
    }

    @Override
    public ResourceLocation getUid() {
        return ModPlugin.CRUSHER;
    }
}