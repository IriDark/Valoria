package com.idark.valoria.registries.item.types.curio.charm;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;

public class RuneDeep extends CurioRune {
    public RuneDeep(Properties properties) {
        super(properties);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        Player player = (Player) slotContext.entity();
        if (!player.level().isClientSide() && !player.hasEffect(MobEffects.WATER_BREATHING)) {
            if (player.isUnderWater() || player.isInWater()) {
                player.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 200));
                if (arcRandom.fiftyFifty()) {
                    stack.hurtAndBreak(1, player, (p_220045_0_) -> p_220045_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND));
                }
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags) {
        super.appendHoverText(stack, world, tooltip, flags);
        tooltip.add(Component.translatable("tooltip.valoria.deep").withStyle(ChatFormatting.GRAY));
    }
}
