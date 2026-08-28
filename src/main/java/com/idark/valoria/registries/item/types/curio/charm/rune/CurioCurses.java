package com.idark.valoria.registries.item.types.curio.charm.rune;

import com.google.common.collect.*;
import com.idark.valoria.core.interfaces.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.util.*;
import net.minecraft.*;
import net.minecraft.network.chat.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.inventory.tooltip.*;
import net.minecraft.world.item.*;
import pro.komaru.tridot.common.registry.item.*;
import pro.komaru.tridot.common.registry.item.components.*;
import pro.komaru.tridot.util.*;
import pro.komaru.tridot.util.struct.data.*;
import top.theillusivec4.curios.api.*;

import java.util.*;

public class CurioCurses extends AbstractRuneItem implements TooltipComponentItem, CurioOnAttackItem{
    private final float chance;
    public CurioCurses(float chance, Properties properties){
        super(properties);
        this.chance = chance;
    }

    // Calamity sounds used
    @Override
    public void onAttack(ItemStack stack, LivingEntity target, DamageSource source, float damage) {
        if (!target.level().isClientSide() && source.getEntity() instanceof ServerPlayer pServer) {
            if (Tmp.rnd.chance(chance) && !pServer.getCooldowns().isOnCooldown(this)) {
                MobEffect randomEffect = ValoriaUtils.getRandomEffectFromTag(target.level().random, TagsRegistry.CURSES);
                if (randomEffect != null) {
                    target.addEffect(new MobEffectInstance(randomEffect, 200, 0, false, true));
                    pServer.getCooldowns().addCooldown(this, 100);
                    target.level().playSound(null, target.getOnPos(), SoundsRegistry.EQUIP_CURSE.get(), SoundSource.AMBIENT, 0.5f, 1f);
                }
            }
        }
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack){
        Multimap<Attribute, AttributeModifier> atts = LinkedHashMultimap.create();
        atts.put(Attributes.MAX_HEALTH, new AttributeModifier(uuid, "bonus", -2, AttributeModifier.Operation.ADDITION));
        if(stack.is(ItemsRegistry.voidSlateRuneCurses.get())) {
            atts.put(AttributeReg.NIHILITY_RESILIENCE.get(), new AttributeModifier(uuid, "debuff", -0.15, AttributeModifier.Operation.ADDITION));
        }

        return atts;
    }

    @Override
    public Seq<TooltipComponent> getTooltips(ItemStack pStack){
        ImmutableList.Builder<MobEffectInstance> effectBuilder = ImmutableList.builder();
        List<MobEffect> effects = ValoriaUtils.getEffectsFromTag(TagsRegistry.CURSES);
        for (MobEffect effect : effects) {
            effectBuilder.add(new MobEffectInstance(effect, 200, 0, false, true));
        }

        return Seq.with(new EffectsListComponent(effectBuilder.build(), Component.translatable("tooltip.valoria.curses", String.format("%.1f%%", chance * 100)).withStyle(ChatFormatting.GRAY)));
    }

    @Override
    public RuneType runeType(){
        return RuneType.CURSES;
    }
}