package com.idark.valoria.registries.item.types.curio;

import com.idark.valoria.ValoriaClient;
import com.idark.valoria.util.ValoriaUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;

public class JewelryBagItem extends DyeableCurioItem {
    public JewelryBagItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return ValoriaUtils.onePerTypeEquip(slotContext, stack);
    }

    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags) {
        super.appendHoverText(stack, world, tooltip, flags);
        tooltip.add(Component.translatable("tooltip.valoria.trinkets_bag", ValoriaClient.BAG_MENU_KEY.getKey().getDisplayName()).withStyle(ChatFormatting.GRAY));
    }
}
