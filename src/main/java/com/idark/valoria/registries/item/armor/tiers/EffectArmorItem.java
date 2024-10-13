package com.idark.valoria.registries.item.armor.tiers;

import com.google.common.collect.*;
import com.idark.valoria.core.enums.*;
import com.idark.valoria.registries.*;
import net.minecraft.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraftforge.api.distmarker.*;

import java.util.*;

@SuppressWarnings("removal")
public class EffectArmorItem extends SuitArmorItem {
    private static final Map<ArmorMaterial, MobEffect> MATERIAL_TO_EFFECT_MAP = new ImmutableMap.Builder<ArmorMaterial, MobEffect>()
            .put(ModArmorMaterial.DEPTH, MobEffects.WATER_BREATHING)
            .put(ModArmorMaterial.NATURE, EffectsRegistry.ALOEREGEN.get())
            .put(ModArmorMaterial.INFERNAL, MobEffects.DAMAGE_BOOST)
            .build();

    public EffectArmorItem(ArmorMaterial material, ArmorItem.Type type, Properties settings) {
        super(material, type, settings);
    }

    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player) {
        if (!level.isClientSide()) {
            if (hasFullSuitOfArmorOn(player)) {
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
                    list.add(1, Component.translatable("tooltip.valoria.applies_fullkit").withStyle(ChatFormatting.GRAY)
                        .append(Component.literal(effect).withStyle(stack.getRarity().getStyleModifier())));
                }
            }
        }
    }

    private void evaluateArmorEffects(Player player) {
        for (Map.Entry<ArmorMaterial, MobEffect> entry : MATERIAL_TO_EFFECT_MAP.entrySet()) {
            ArmorMaterial mapArmorMaterial = entry.getKey();
            MobEffect mapStatusEffect = entry.getValue();
            if (hasCorrectArmorOn(mapArmorMaterial, player)) {
                if (mapArmorMaterial == ModArmorMaterial.DEPTH) {
                    if (player.isInWater()) {
                        addStatusEffectForMaterial(player, mapArmorMaterial, mapStatusEffect);
                    }
                } else {
                    addStatusEffectForMaterial(player, mapArmorMaterial, mapStatusEffect);
                }
            }
        }
    }

    private void addStatusEffectForMaterial(Player player, ArmorMaterial mapArmorMaterial, MobEffect mapStatusEffect) {
        if (hasCorrectArmorOn(mapArmorMaterial, player) && !player.hasEffect(mapStatusEffect)) {
            player.addEffect(new MobEffectInstance(mapStatusEffect, 400));
            if (new Random().nextFloat() > 0.4f) {
                player.getInventory().hurtArmor(player.damageSources().magic(), 2f, Inventory.ALL_ARMOR_SLOTS);
            }
        }
    }

    private boolean hasFullSuitOfArmorOn(Player player) {
        ItemStack boots = player.getInventory().getArmor(0);
        ItemStack leggings = player.getInventory().getArmor(1);
        ItemStack chestplate = player.getInventory().getArmor(2);
        ItemStack helmet = player.getInventory().getArmor(3);

        return !helmet.isEmpty() && !chestplate.isEmpty() && !leggings.isEmpty() && !boots.isEmpty();
    }

    private boolean hasCorrectArmorOn(ArmorMaterial material, Player player) {
        ArmorItem boots = ((ArmorItem) player.getInventory().getArmor(0).getItem());
        ArmorItem leggings = ((ArmorItem) player.getInventory().getArmor(1).getItem());
        ArmorItem chestplate = ((ArmorItem) player.getInventory().getArmor(2).getItem());
        ArmorItem helmet = ((ArmorItem) player.getInventory().getArmor(3).getItem());

        return helmet.getMaterial() == material && chestplate.getMaterial() == material && leggings.getMaterial() == material && boots.getMaterial() == material;
    }
}