package com.idark.darkrpg.item.types;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.google.common.collect.Multimap;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.IVanishable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.particles.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BeastScytheItem extends TieredItem implements IVanishable {
   private final float attackDamage;
   private final Multimap<Attribute, AttributeModifier> attributeModifiers;
   Random rand = new Random();

   public BeastScytheItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Item.Properties builderIn) {
      super(tier, builderIn);
      this.attackDamage = (float)attackDamageIn + tier.getAttackDamageBonus();
      Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
      builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", (double)this.attackDamage, AttributeModifier.Operation.ADDITION));
      builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", (double)attackSpeedIn, AttributeModifier.Operation.ADDITION));
      this.attributeModifiers = builder.build();
   }

   public float getAttackDamage() {
      return this.attackDamage;
   }

   public boolean canAttackBlock(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
      return !player.isCreative();
   }
   
   public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
      stack.hurtAndBreak(1, attacker, (entity) -> {
         entity.broadcastBreakEvent(EquipmentSlotType.MAINHAND);
	});	
		target.level.addParticle(ParticleTypes.CRIT, target.getX() + rand.nextDouble(), target.getY(), target.getZ() + rand.nextDouble(), 0d, 0.05d, 0d);
	return true;
   }

   public boolean mineBlock(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
      if (state.getDestroySpeed(worldIn, pos) != 0.0F) {
      stack.hurtAndBreak(4, entityLiving, (entity) -> {
      entity.broadcastBreakEvent(EquipmentSlotType.MAINHAND);
        });
      }
      return true;
    }

   public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlotType equipmentSlot) {
      return equipmentSlot == EquipmentSlotType.MAINHAND ? this.attributeModifiers : super.getDefaultAttributeModifiers(equipmentSlot);
    }
}