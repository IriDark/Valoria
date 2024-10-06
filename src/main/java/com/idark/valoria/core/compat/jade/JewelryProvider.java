package com.idark.valoria.core.compat.jade;

import com.idark.valoria.registries.block.entity.*;
import net.minecraft.nbt.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;
import net.minecraft.world.phys.*;
import snownee.jade.api.*;
import snownee.jade.api.config.*;
import snownee.jade.api.ui.*;
import snownee.jade.impl.ui.*;

public enum JewelryProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {

    INSTANCE;

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        CompoundTag data = accessor.getServerData();
        if (!data.contains("progress")) {
            return;
        }

        int progress = data.getInt("progress");
        int total = data.getInt("total");
        tooltip.append(new ProgressArrowElement((float) progress / total));
        IElementHelper elements = tooltip.getElementHelper();
        IElement icon = elements.item(new ItemStack(Items.CLOCK), 0.5f).size(new Vec2(11, 10)).translate(new Vec2(0, -1));
        icon.message(null);
        tooltip.add(icon);
        tooltip.append(Component.translatable("valoria.jade.progress"));
        tooltip.append(Component.literal(" " + progress + "/" + total));
    }

    @Override
    public void appendServerData(CompoundTag data, BlockAccessor accessor) {
        JewelryBlockEntity jewelryBlock = (JewelryBlockEntity) accessor.getBlockEntity();
        data.putInt("progress", jewelryBlock.progress);
        data.putInt("total", jewelryBlock.progressMax);
    }

    @Override
    public ResourceLocation getUid() {
        return ModPlugin.JEWELRY;
    }
}