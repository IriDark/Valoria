package com.idark.darkrpg.item.types;

import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.google.common.collect.Multimap;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.IVanishable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeMod;

import java.util.UUID;
import java.util.function.Supplier;

public class SpearItem extends TieredItem implements IVanishable {

   private final float attackDamage;
   private final float attackSpeed;
   protected static final UUID ATTACK_RANGE_MODIFIER = UUID.fromString("B2392114-1C63-2123-AB29-6456FD734102");


   private Supplier<Multimap<Attribute, AttributeModifier>> attributeModifiers = Suppliers.memoize(this::createAttributes);

   public SpearItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Item.Properties builderIn) {
      super(tier, builderIn);
      this.attackDamage = (float)attackDamageIn + tier.getAttackDamage();
      this.attackSpeed = attackSpeedIn;
   }

   private Multimap<Attribute, AttributeModifier> createAttributes(){
      Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
      builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", (double)this.attackDamage, AttributeModifier.Operation.ADDITION));
      builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", (double)attackSpeed, AttributeModifier.Operation.ADDITION));
      builder.put(ForgeMod.REACH_DISTANCE.get(), new AttributeModifier(ATTACK_RANGE_MODIFIER,"Spear modifier",10, AttributeModifier.Operation.ADDITION));
      return builder.build();
   }

   public float getAttackDamage() {
      return this.attackDamage;
   }

   public boolean canPlayerBreakBlockWhileHolding(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
      return !player.isCreative();
   }

   public float getDestroySpeed(ItemStack stack, BlockState state) {
      if (state.matchesBlock(Blocks.COBWEB)) {
         return 2.0F;
      } else {
         Material material = state.getMaterial();
         return material != Material.PLANTS && material != Material.TALL_PLANTS && material != Material.CORAL && !state.isIn(BlockTags.LEAVES) && material != Material.GOURD ? 1.0F : 1.5F;
      }
   }

   public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
      stack.damageItem(1, attacker, (entity) -> {
         entity.sendBreakAnimation(EquipmentSlotType.MAINHAND);
      });
      return true;
   }

   public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
      if (state.getBlockHardness(worldIn, pos) != 0.0F) {
         stack.damageItem(2, entityLiving, (entity) -> {
            entity.sendBreakAnimation(EquipmentSlotType.MAINHAND);
         });
      }

      return true;
   }

   public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot) {
      return equipmentSlot == EquipmentSlotType.MAINHAND ? this.attributeModifiers.get() : super.getAttributeModifiers(equipmentSlot);
   }
}