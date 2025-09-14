package com.idark.valoria.registries.item.types.curio.charm;

import com.google.common.collect.*;
import com.idark.valoria.*;
import com.idark.valoria.core.capability.*;
import com.idark.valoria.core.interfaces.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.item.component.*;
import com.idark.valoria.registries.item.types.curio.*;
import com.idark.valoria.registries.level.*;
import net.minecraft.*;
import net.minecraft.network.chat.*;
import net.minecraft.sounds.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.inventory.*;
import net.minecraft.world.inventory.tooltip.*;
import net.minecraft.world.item.*;
import pro.komaru.tridot.client.gfx.text.*;
import pro.komaru.tridot.util.struct.data.*;
import top.theillusivec4.curios.api.*;

import java.util.*;

public class NihilityMonitorItem extends AbstractCurioItem implements TooltipComponentItem{
    public NihilityMonitorItem(Properties properties){
        super(properties);
    }

    public Seq<TooltipComponent> getTooltips(ItemStack pStack) {
        return Seq.with(
        new AbilityComponent(Component.translatable("tooltip.valoria.nihility_monitor").withStyle(ChatFormatting.GRAY), Valoria.loc("textures/gui/tooltips/sound.png")),
        new ClientTextComponent(Component.translatable("tooltip.valoria.toggle", getState(pStack)).withStyle(getState(pStack) ? ChatFormatting.GREEN : ChatFormatting.RED)),
        new ClientTextComponent(Component.translatable("tooltip.valoria.rmb").withStyle(DotStyle.of().font(Valoria.FONT)))
        );
    }

    public boolean getState(ItemStack pStack){
        return pStack.getOrCreateTag().getBoolean("ToggleState");
    }

    public void toggle(ItemStack pStack, boolean state){
        pStack.getOrCreateTag().putBoolean("ToggleState", !state);
    }

    @Override
    public boolean overrideOtherStackedOnMe(ItemStack pStack, ItemStack pOther, Slot pSlot, ClickAction pAction, Player pPlayer, SlotAccess pAccess){
        if (pAction == ClickAction.SECONDARY) {
            toggle(pStack, getState(pStack));
            pPlayer.playSound(SoundEvents.CHERRY_WOOD_BUTTON_CLICK_ON);
            return true;
        }

        return super.overrideOtherStackedOnMe(pStack, pOther, pSlot, pAction, pPlayer, pAccess);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack){
        Multimap<Attribute, AttributeModifier> atts = LinkedHashMultimap.create();
        atts.put(AttributeReg.NIHILITY_RESILIENCE.get(), new AttributeModifier(uuid, "bonus", 0.05, AttributeModifier.Operation.ADDITION));
        return atts;
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack){
        super.curioTick(slotContext, stack);
        LivingEntity wearer = slotContext.entity();
        if(wearer instanceof Player player){
            if(!getState(stack)) return;
            player.getCapability(INihilityLevel.INSTANCE).ifPresent((nihilityLevel) -> {
                float max = nihilityLevel.getMaxAmount(player, false);
                float amount = nihilityLevel.getAmount(false);
                float ratio = amount / max;
                if(ratio >= NihilityMeter.damagingLevel) {
                    if(player.tickCount % (ratio >= NihilityMeter.criticalLevel ? 15 : 30) == 0){
                        player.level().playSound(null, player.blockPosition(), SoundsRegistry.NIHILITY_ALERT.get(), SoundSource.PLAYERS, 0.5f, 1);
                    }
                }
            });
        }
    }
}
