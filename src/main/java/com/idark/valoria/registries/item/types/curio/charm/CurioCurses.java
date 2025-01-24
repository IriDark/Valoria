package com.idark.valoria.registries.item.types.curio.charm;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.idark.valoria.registries.SoundsRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class CurioCurses extends CurioRune{
    private static List<MobEffect> effects = new ArrayList<>();

    public CurioCurses(Properties properties){
        super(properties);
    }

    public static void effects(MobEffect... T){
        Collections.addAll(effects, T);
    }

    public static void setEffects(List<MobEffect> effects){
        CurioCurses.effects = effects;
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
                MobEffect[] effectsArray = effects.toArray(new MobEffect[0]);
                pServer.addEffect(new MobEffectInstance(effectsArray[Mth.nextInt(RandomSource.create(), 0, 5)], 60, 0, false, true));
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