package com.idark.valoria.client.compat.jade;

import com.idark.valoria.registries.world.item.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec2;
import snownee.jade.api.*;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.IElement;
import snownee.jade.api.ui.IElementHelper;

public enum KeyBlockProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {
    INSTANCE;

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        IElementHelper elements = tooltip.getElementHelper();
        int offsetY = 0;
        if (!config.get(Identifiers.MC_HARVEST_TOOL_NEW_LINE)) {
            offsetY = -3;
        }

        IElement key = elements.item(new ItemStack(ModItems.VOID_KEY.get()), 0.75f).size(new Vec2(11, 0)).translate(new Vec2(0, offsetY));
        key.message(null);
        key.align(IElement.Align.RIGHT);
        tooltip.add(key);
        tooltip.append(Component.translatable("valoria.jade.openable_with"));
    }

    @Override
    public void appendServerData(CompoundTag data, BlockAccessor accessor) {
    }

    @Override
    public ResourceLocation getUid() {
        return ModPlugin.KEY;
    }
}