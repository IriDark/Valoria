package com.idark.valoria.registries.item.types.curio.charm.rune;

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
import pro.komaru.tridot.api.*;
import pro.komaru.tridot.util.*;
import top.theillusivec4.curios.api.*;

import java.util.*;

public class CurioCurses extends AbstractRuneItem{
    private static List<MobEffect> effects = new ArrayList<>();
    private final float chance;
    public CurioCurses(float chance, Properties properties){
        super(properties);
        this.chance = chance;
    }

    public static void effects(MobEffect... T){
        Collections.addAll(effects, T);
    }

    public static void setEffects(List<MobEffect> effects){
        CurioCurses.effects = effects;
    }

    // Calamity sounds used
    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack){
        Player player = (Player)slotContext.entity();
        if(!player.level().isClientSide() && player instanceof ServerPlayer pServer){
            if(Tmp.rnd.chance(chance) && !pServer.getCooldowns().isOnCooldown(this)){
                MobEffect[] effectsArray = effects.toArray(new MobEffect[0]);
                pServer.addEffect(new MobEffectInstance(effectsArray[Mth.nextInt(RandomSource.create(), 0, effects.size() - 1)], 60, 0, false, true));
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
        ImmutableList.Builder<MobEffectInstance> effectBuilder = ImmutableList.builder();
        for (MobEffect effect : effects) {
            effectBuilder.add(new MobEffectInstance(effect, 60, 0, false, true));
        }

        tooltip.add(Component.translatable("tooltip.valoria.curses").withStyle(ChatFormatting.GRAY));
        Utils.Items.effectTooltip(effectBuilder.build(), tooltip, 60, chance);
    }

    @Override
    public RuneType runeType(){
        return RuneType.CURSES;
    }
}