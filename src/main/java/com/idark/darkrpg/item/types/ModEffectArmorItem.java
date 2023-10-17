package com.idark.darkrpg.item.types;

import com.idark.darkrpg.effect.*;
import com.idark.darkrpg.item.ModArmorMaterial;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import java.util.Objects;
import java.util.Random;
	
import net.minecraft.item.Item.Properties;

	public class ModEffectArmorItem extends ArmorItem {
	
	public ModEffectArmorItem(IArmorMaterial material, EquipmentSlotType slot, Properties settings) {
	super(material, slot, settings);
	}
	
	@Override
	public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
		if(!world.isClientSide()) {
		if(hasFullSuitOfArmorOn(player)) {
		evaluateArmorEffects(player);
	}
	}
	
	super.onArmorTick(stack, world, player);
	}
	
	private void evaluateArmorEffects(PlayerEntity player) {
		IArmorMaterial ArmorMaterial = ModArmorMaterial.NATURE;
		Effect modEffect = ModEffects.ALOEREGEN.get();
		
		if(hasCorrectArmorOn(ArmorMaterial, player)) {
			addStatusEffectForMaterial(player, ArmorMaterial, modEffect);
		}
	}
	
	private void addStatusEffectForMaterial(PlayerEntity player, IArmorMaterial ArmorMaterial, Effect modEffect) {
		boolean hasPlayerEffect = !Objects.equals(player.getEffect(ModEffects.ALOEREGEN.get()), null);
	
	if(hasCorrectArmorOn(ArmorMaterial, player) && !hasPlayerEffect) {
		player.addEffect(new EffectInstance(ModEffects.ALOEREGEN.get(), 400));
		if(new Random().nextFloat() > 0.4f) {
			player.inventory.hurtArmor(DamageSource.MAGIC, 6f);
			}
		}
	}
	
	private boolean hasFullSuitOfArmorOn(PlayerEntity player) {
		ItemStack boots = player.inventory.getArmor(0);
		ItemStack leggings = player.inventory.getArmor(1);
		ItemStack breastplate = player.inventory.getArmor(2);
		ItemStack helmet = player.inventory.getArmor(3);
	
		return !helmet.isEmpty() && !breastplate.isEmpty() && !leggings.isEmpty() && !boots.isEmpty();
	}
	
	private boolean hasCorrectArmorOn(IArmorMaterial material, PlayerEntity player) {
		ArmorItem boots = ((ArmorItem)player.inventory.getArmor(0).getItem());
		ArmorItem leggings = ((ArmorItem)player.inventory.getArmor(1).getItem());
		ArmorItem breastplate = ((ArmorItem)player.inventory.getArmor(2).getItem());
		ArmorItem helmet = ((ArmorItem)player.inventory.getArmor(3).getItem());
	
		return helmet.getMaterial() == material && breastplate.getMaterial() == material && leggings.getMaterial() == material && boots.getMaterial() == material;
	}
}