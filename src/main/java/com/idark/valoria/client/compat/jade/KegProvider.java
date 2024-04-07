package com.idark.valoria.client.compat.jade;

import com.idark.valoria.registries.ItemsRegistry;
import com.idark.valoria.registries.TagsRegistry;
import com.idark.valoria.registries.block.entity.KegBlockEntity;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec2;
import snownee.jade.api.*;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.IElement;
import snownee.jade.api.ui.IElementHelper;
import snownee.jade.impl.ui.ProgressArrowElement;

public enum KegProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {

    INSTANCE;

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        CompoundTag data = accessor.getServerData();
        if (!data.contains("progress")) {
            return;
        }

        KegBlockEntity kegBlock = (KegBlockEntity) accessor.getBlockEntity();
        ItemStack itemStack = kegBlock.getItemHandler().getItem(0);
        int progress = data.getInt("progress");
        int total = data.getInt("total");

        tooltip.append(new ProgressArrowElement((float) progress / total));
        IElementHelper elements = tooltip.getElementHelper();
        IElement icon = elements.item(new ItemStack(Items.CLOCK), 0.5f).size(new Vec2(11, 10)).translate(new Vec2(0, -1));
        icon.message(null);
        tooltip.add(icon);
        tooltip.append(Component.translatable("valoria.jade.progress"));
        tooltip.append(Component.literal(" " + progress + "/" + total));

        if (!itemStack.isEmpty()) {
            ListTag crusherBlockItems = data.getList("kegBlock", Tag.TAG_COMPOUND);
            NonNullList<ItemStack> inventory = NonNullList.withSize(1, ItemStack.EMPTY);
            for (int i = 0; i < crusherBlockItems.size(); i++)
                inventory.set(i, ItemStack.of(crusherBlockItems.getCompound(i)));

            IElementHelper helper = IElementHelper.get();
            tooltip.add(helper.item(inventory.get(0)));

            int offsetY = 0;
            if (!config.get(Identifiers.MC_HARVEST_TOOL_NEW_LINE)) {
                offsetY = -3;
            }
            if (itemStack.is(TagsRegistry.CUP_DRINKS)) {
                IElement cup = elements.item(new ItemStack(ItemsRegistry.WOODEN_CUP.get()), 0.75f).size(new Vec2(11, 0)).translate(new Vec2(0, offsetY));
                cup.message(null);
                cup.align(IElement.Align.RIGHT);
                tooltip.add(cup);
            }

            if (itemStack.is(TagsRegistry.BOTTLE_DRINKS)) {
                IElement bottle = elements.item(new ItemStack(ItemsRegistry.BOTTLE.get()), 0.75f).size(new Vec2(11, 0)).translate(new Vec2(0, offsetY));
                bottle.message(null);
                bottle.align(IElement.Align.RIGHT);
                tooltip.add(bottle);
            }
        }
    }

    @Override
    public void appendServerData(CompoundTag data, BlockAccessor accessor) {
        KegBlockEntity kegBlock = (KegBlockEntity) accessor.getBlockEntity();
        if (!kegBlock.getItemHandler().getItem(0).isEmpty()) {
            ListTag items = new ListTag();
            items.add(kegBlock.getItemHandler().getItem(0).save(new CompoundTag()));
            data.put("kegBlock", items);
        }

        data.putInt("progress", kegBlock.progress);
        data.putInt("total", kegBlock.progressMax);
    }

    @Override
    public ResourceLocation getUid() {
        return ModPlugin.KEG;
    }
}