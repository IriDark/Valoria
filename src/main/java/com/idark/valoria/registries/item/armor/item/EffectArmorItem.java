package com.idark.valoria.registries.item.armor.item;

import com.idark.valoria.registries.*;
import com.idark.valoria.registries.item.armor.*;
import net.minecraft.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraftforge.api.distmarker.*;
import pro.komaru.tridot.util.*;

import java.util.*;

@SuppressWarnings("removal")
public class EffectArmorItem extends SuitArmorItem{
    public static final Map<ArmorMaterial, MobEffect> MATERIAL_TO_EFFECT_MAP = new HashMap<>();

    static{
        MATERIAL_TO_EFFECT_MAP.put(ArmorRegistry.DEPTH, MobEffects.WATER_BREATHING);
        MATERIAL_TO_EFFECT_MAP.put(ArmorRegistry.NATURE, EffectsRegistry.ALOEREGEN.get());
        MATERIAL_TO_EFFECT_MAP.put(ArmorRegistry.INFERNAL, MobEffects.DAMAGE_BOOST);
        MATERIAL_TO_EFFECT_MAP.put(ArmorRegistry.ETHEREAL, MobEffects.NIGHT_VISION);
    }

    public EffectArmorItem(ArmorMaterial material, ArmorItem.Type type, Properties settings){
        super(material, type, settings);
    }

    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player){
        if(!level.isClientSide()){
            if(hasFullSuitOfArmorOn(player)){
                evaluateArmorEffects(player);
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> list, TooltipFlag flags){
        super.appendHoverText(stack, world, list, flags);
        for(Map.Entry<ArmorMaterial, MobEffect> entry : MATERIAL_TO_EFFECT_MAP.entrySet()){
            ArmorMaterial mapArmorMaterial = entry.getKey();
            if(stack.getItem() instanceof ArmorItem armor){
                if(mapArmorMaterial == armor.getMaterial()){
                    String effect = MATERIAL_TO_EFFECT_MAP.get(armor.getMaterial()).getDisplayName().getString();
                    list.add(1, Component.translatable("tooltip.valoria.applies_fullkit").withStyle(ChatFormatting.GRAY).append(Component.literal(effect).withStyle(stack.getRarity().getStyleModifier())));
                }
            }
        }
    }

    private void evaluateArmorEffects(Player player){
        for(Map.Entry<ArmorMaterial, MobEffect> entry : MATERIAL_TO_EFFECT_MAP.entrySet()){
            ArmorMaterial mapArmorMaterial = entry.getKey();
            MobEffect mapStatusEffect = entry.getValue();
            if(hasCorrectArmorOn(mapArmorMaterial, player)){
                if(mapArmorMaterial == ArmorRegistry.DEPTH){
                    if(player.isInWater()){
                        addStatusEffectForMaterial(player, mapArmorMaterial, mapStatusEffect);
                    }
                }else{
                    addStatusEffectForMaterial(player, mapArmorMaterial, mapStatusEffect);
                }
            }
        }
    }

    private void addStatusEffectForMaterial(Player player, ArmorMaterial mapArmorMaterial, MobEffect mapStatusEffect){
        if(hasCorrectArmorOn(mapArmorMaterial, player) && !player.hasEffect(mapStatusEffect)){
            player.addEffect(new MobEffectInstance(mapStatusEffect, 400));
            if(Tmp.rnd.nextFloat() > 0.4f){
                player.getInventory().hurtArmor(player.damageSources().magic(), 2f, Inventory.ALL_ARMOR_SLOTS);
            }
        }
    }
}