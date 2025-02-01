package com.idark.valoria.registries.item.armor.item;

import net.minecraft.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.event.entity.player.*;
import pro.komaru.tridot.core.math.*;

import java.util.*;

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
            list.add(1, Component.translatable("tooltip.tridot.applies").withStyle(ChatFormatting.GRAY)
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