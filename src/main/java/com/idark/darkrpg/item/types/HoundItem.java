package com.idark.darkrpg.item.types;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;

public class HoundItem extends TieredItem implements Vanishable {
	private final float attackDamage;
	private final Multimap<Attribute, AttributeModifier> attributeModifiers;
	Random rand = new Random();

	public HoundItem(Tier tier, int attackDamageIn, float attackSpeedIn, Item.Properties builderIn) {
		super(tier, builderIn);
		this.attackDamage = (float)attackDamageIn + tier.getAttackDamageBonus();
		ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", (double)this.attackDamage, AttributeModifier.Operation.ADDITION));
		builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", (double)attackSpeedIn, AttributeModifier.Operation.ADDITION));
		this.attributeModifiers = builder.build();
	}

	public float getAttackDamage() {
      return this.attackDamage;
	}

	public boolean canAttackBlock(BlockState state, Level worldIn, BlockPos pos, Player player) {
      return !player.isCreative();
	}
   
	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		stack.hurtAndBreak(1, attacker, (entity) -> {
		entity.broadcastBreakEvent(EquipmentSlot.MAINHAND);
		});	
	return true;
	}

	public boolean mineBlock(ItemStack stack, Level worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
		if (state.getDestroySpeed(worldIn, pos) != 0.0F) {
		stack.hurtAndBreak(4, entityLiving, (entity) -> {
		entity.broadcastBreakEvent(EquipmentSlot.MAINHAND);
			});
		}
		return true;
	}

	public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot equipmentSlot) {
		return equipmentSlot == EquipmentSlot.MAINHAND ? this.attributeModifiers : super.getDefaultAttributeModifiers(equipmentSlot);
    }
}