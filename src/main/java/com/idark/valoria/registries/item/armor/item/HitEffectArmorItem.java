package com.idark.valoria.registries.item.armor.item;

import com.idark.valoria.util.ArcRandom;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.player.AttackEntityEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HitEffectArmorItem extends SuitArmorItem{
    public List<MobEffectInstance> effects = new ArrayList<>();
    public float chance;
    public Type type;

    public HitEffectArmorItem(ArmorMaterial material, Type type, Properties settings, float chance, MobEffectInstance... effects){
        super(material, type, settings);
        this.chance = chance;
        this.type = type;
        Collections.addAll(this.effects, effects);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> list, TooltipFlag flags){
        super.appendHoverText(stack, world, list, flags);
        for(MobEffectInstance entry : effects){
            String effect = entry.getEffect().getDisplayName().getString();
            list.add(1, Component.translatable("tooltip.valoria.applies").withStyle(ChatFormatting.GRAY)
                    .append(Component.literal(effect).withStyle(stack.getRarity().getStyleModifier()))
                    .append(Component.literal(" "))
                    .append(Component.translatable("tooltip.valoria.with_chance", String.format("%.1f%%", chance * 100)).withStyle(ChatFormatting.GRAY))
            );
        }
    }

    public void onAttack(AttackEntityEvent event){
        if(new ArcRandom().chance(chance)){
            for(MobEffectInstance effect : effects){
                if(event.getTarget() instanceof LivingEntity target){
                    target.addEffect(effect);
                }
            }
        }
    }
}