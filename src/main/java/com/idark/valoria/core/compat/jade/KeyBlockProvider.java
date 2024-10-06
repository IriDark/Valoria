package com.idark.valoria.core.compat.jade;

import com.idark.valoria.registries.*;
import net.minecraft.nbt.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;
import net.minecraft.world.phys.*;
import snownee.jade.api.*;
import snownee.jade.api.config.*;
import snownee.jade.api.ui.*;

public enum KeyBlockProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {
    INSTANCE;

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        IElementHelper elements = tooltip.getElementHelper();
        int offsetY = 0;
        if (!config.get(Identifiers.MC_HARVEST_TOOL_NEW_LINE)) {
            offsetY = -3;
        }

        IElement key = elements.item(new ItemStack(ItemsRegistry.voidKey.get()), 0.75f).size(new Vec2(11, 0)).translate(new Vec2(0, offsetY));
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