package com.idark.valoria.registries.world.item.tiers;

import com.google.common.collect.ImmutableMap;
import com.idark.valoria.registries.world.effect.ModEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.Map;
import java.util.Random;

@SuppressWarnings("removal")
public class ModArmorItem extends ArmorItem {
    private static final Map<ArmorMaterial, MobEffect> MATERIAL_TO_EFFECT_MAP = new ImmutableMap.Builder<ArmorMaterial, MobEffect>()
            .put(ModArmorMaterial.DEPTH, MobEffects.WATER_BREATHING)
            .put(ModArmorMaterial.NATURE, ModEffects.ALOEREGEN.get())
            .put(ModArmorMaterial.INFERNAL, MobEffects.DAMAGE_BOOST)
            .build();

    public ModArmorItem(ArmorMaterial material, ArmorItem.Type type, Properties settings) {
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