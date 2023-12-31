package com.idark.valoria.item.types;

import com.idark.valoria.effect.ModEffects;
import com.idark.valoria.item.ModArmorMaterial;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.Objects;
import java.util.Random;

public class ModEffectArmorItem extends ArmorItem {
	
	public ModEffectArmorItem(ArmorMaterial material, ArmorItem.Type type, Properties settings) {
	super(material, type, settings);
	}
	
	@Override
	public void onArmorTick(ItemStack stack, Level world, Player player) {
		if(!world.isClientSide()) {
		if(hasFullSuitOfArmorOn(player)) {
		evaluateArmorEffects(player);
	}
	}
	
	super.onArmorTick(stack, world, player);
	}
	
	private void evaluateArmorEffects(Player player) {
		ArmorMaterial ArmorMaterial = ModArmorMaterial.NATURE;
		MobEffect modEffect = ModEffects.ALOEREGEN.get();
		
		if(hasCorrectArmorOn(ArmorMaterial, player)) {
			addStatusEffectForMaterial(player, ArmorMaterial, modEffect);
		}
	}
	
	private void addStatusEffectForMaterial(Player player, ArmorMaterial ArmorMaterial, MobEffect modEffect) {
		boolean hasPlayerEffect = !Objects.equals(player.getEffect(ModEffects.ALOEREGEN.get()), null);
	
	if(hasCorrectArmorOn(ArmorMaterial, player) && !hasPlayerEffect) {
		player.addEffect(new MobEffectInstance(ModEffects.ALOEREGEN.get(), 400));
		if(new Random().nextFloat() > 0.4f) {
			player.getInventory().hurtArmor(player.damageSources().magic(), 6f, Inventory.ALL_ARMOR_SLOTS);
			}
		}
	}
	
	private boolean hasFullSuitOfArmorOn(Player player) {
		ItemStack boots = player.getInventory().getArmor(0);
		ItemStack leggings = player.getInventory().getArmor(1);
		ItemStack breastplate = player.getInventory().getArmor(2);
		ItemStack helmet = player.getInventory().getArmor(3);
	
		return !helmet.isEmpty() && !breastplate.isEmpty() && !leggings.isEmpty() && !boots.isEmpty();
	}
	
	private boolean hasCorrectArmorOn(ArmorMaterial material, Player player) {
		ArmorItem boots = ((ArmorItem)player.getInventory().getArmor(0).getItem());
		ArmorItem leggings = ((ArmorItem)player.getInventory().getArmor(1).getItem());
		ArmorItem breastplate = ((ArmorItem)player.getInventory().getArmor(2).getItem());
		ArmorItem helmet = ((ArmorItem)player.getInventory().getArmor(3).getItem());
	
		return helmet.getMaterial() == material && breastplate.getMaterial() == material && leggings.getMaterial() == material && boots.getMaterial() == material;
	}
}