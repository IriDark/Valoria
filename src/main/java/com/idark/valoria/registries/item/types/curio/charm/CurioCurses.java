package com.idark.valoria.registries.item.types.curio.charm;

import com.google.common.collect.*;
import com.idark.valoria.registries.*;
import net.minecraft.*;
import net.minecraft.network.chat.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.util.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import top.theillusivec4.curios.api.*;
import top.theillusivec4.curios.api.type.capability.*;

import javax.annotation.*;
import java.util.*;

@SuppressWarnings("removal")
public class CurioCurses extends Item implements ICurioItem{
    MobEffect[] effects = {
    MobEffects.DARKNESS, MobEffects.WEAKNESS, MobEffects.WITHER, MobEffects.POISON, MobEffects.MOVEMENT_SLOWDOWN, MobEffects.DIG_SLOWDOWN
    };

    public CurioCurses(Properties properties){
        super(properties);
    }

    @Override
    public boolean canEquipFromUse(SlotContext slot, ItemStack stack){
        return true;
    }

    @Override
    public boolean isEnchantable(ItemStack pStack){
        return false;
    }

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack){
        List<ItemStack> items = new ArrayList<>();
        List<SlotResult> curioSlots = CuriosApi.getCuriosHelper().findCurios(slotContext.getWearer(), stack.getItem());
        for(SlotResult slot : curioSlots){
            items.add(slot.stack());
        }

        return items.isEmpty() || slotContext.cosmetic();
    }

    @Nonnull
    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack){
        return new ICurio.SoundInfo(SoundEvents.CALCITE_PLACE, 1.0f, 1.0f);
    }

    // Calamity sounds used
    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack){
        Player player = (Player)slotContext.entity();
        if(!player.level().isClientSide() && player instanceof ServerPlayer pServer){
            if(pServer.getActiveEffects().isEmpty() && !pServer.getCooldowns().isOnCooldown(this)){
                pServer.addEffect(new MobEffectInstance(effects[Mth.nextInt(RandomSource.create(), 0, 5)], 60, 0, false, true));
                pServer.getCooldowns().addCooldown(this, 300);
                pServer.level().playSound(null, pServer.getOnPos(), SoundsRegistry.EQUIP_CURSE.get(), SoundSource.AMBIENT, 0.5f, 1f);
            }
        }
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack){
        Multimap<Attribute, AttributeModifier> atts = LinkedHashMultimap.create();
        atts.put(Attributes.MAX_HEALTH, new AttributeModifier(uuid, "bonus", 3, AttributeModifier.Operation.ADDITION));
        return atts;
    }

    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags){
        super.appendHoverText(stack, world, tooltip, flags);
        tooltip.add(Component.translatable("tooltip.valoria.curses").withStyle(ChatFormatting.GRAY));
    }
}