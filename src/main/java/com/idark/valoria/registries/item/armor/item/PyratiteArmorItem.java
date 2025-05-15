package com.idark.valoria.registries.item.armor.item;

import net.minecraft.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraftforge.api.distmarker.*;

import java.util.*;

public class PyratiteArmorItem extends InfernalArmorItem{

    public PyratiteArmorItem(Type type, ArmorMaterial material, Properties properties){
        super(type, material, properties);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> list, TooltipFlag flags){
        super.appendHoverText(stack, world, list, flags);
        list.add(1, Component.translatable("tooltip.valoria.pyratite_armor").withStyle(ChatFormatting.GRAY));
    }
}