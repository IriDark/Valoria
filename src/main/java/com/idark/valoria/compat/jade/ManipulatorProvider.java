package com.idark.valoria.compat.jade;

import com.idark.valoria.registries.*;
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

public enum ManipulatorProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor>{

    INSTANCE;

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config){
        CompoundTag data = accessor.getServerData();
        if(!data.contains("progress")){
            return;
        }

        int progress = data.getInt("progress");
        int total = data.getInt("total");
        int nature_core = data.getInt("nature");
        int aquarius_core = data.getInt("aquarius");
        int infernal_core = data.getInt("infernal");
        int void_core = data.getInt("void");

        tooltip.append(new ProgressArrowElement((float)progress / total));
        IElementHelper elements = tooltip.getElementHelper();
        IElement icon = elements.item(new ItemStack(Items.CLOCK), 1).translate(new Vec2(-2, -4));
        IElement element_n = elements.item(new ItemStack(ItemsRegistry.NATURE_CORE.get()), 1).translate(new Vec2(0, -6));
        IElement element_a = elements.item(new ItemStack(ItemsRegistry.AQUARIUS_CORE.get()), 1).translate(new Vec2(0, -6));
        IElement element_v = elements.item(new ItemStack(ItemsRegistry.VOID_CORE.get()), 1).translate(new Vec2(0, -6));
        IElement element_i = elements.item(new ItemStack(ItemsRegistry.INFERNAL_CORE.get()), 1).translate(new Vec2(0, -6));
        icon.message(null);
        tooltip.add(icon);
        tooltip.append(Component.translatable("valoria.jade.progress"));
        tooltip.append(Component.literal(" " + progress + "/" + total));

        tooltip.add(Component.empty());
        tooltip.add(element_n);
        tooltip.append(Component.literal(" " + nature_core + "/8"));
        tooltip.add(element_a);
        tooltip.append(Component.literal(" " + aquarius_core + "/8"));
        tooltip.add(element_i);
        tooltip.append(Component.literal(" " + infernal_core + "/8"));
        tooltip.add(element_v);
        tooltip.append(Component.literal(" " + void_core + "/8"));

    }

    @Override
    public void appendServerData(CompoundTag data, BlockAccessor accessor){
        ManipulatorBlockEntity manipulatorBlockEntity = (ManipulatorBlockEntity)accessor.getBlockEntity();
        data.putInt("progress", manipulatorBlockEntity.progress);
        data.putInt("total", manipulatorBlockEntity.progressMax);

        data.putInt("nature", manipulatorBlockEntity.nature_core);
        data.putInt("aquarius", manipulatorBlockEntity.aquarius_core);
        data.putInt("infernal", manipulatorBlockEntity.infernal_core);
        data.putInt("void", manipulatorBlockEntity.void_core);
    }

    @Override
    public ResourceLocation getUid(){
        return ModPlugin.MANIPULATOR;
    }
}