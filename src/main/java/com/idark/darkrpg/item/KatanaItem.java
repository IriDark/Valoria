package com.idark.darkrpg.item;

import com.idark.darkrpg.math.*;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import net.minecraft.client.Minecraft;
import net.minecraft.item.*;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.IVanishable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.MoverType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeMod;

import java.util.UUID;
import java.util.function.Supplier;

public class KatanaItem extends TieredItem implements IVanishable {
   private final float attackDamage;
   private final Multimap<Attribute, AttributeModifier> attributeModifiers;

   public KatanaItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Item.Properties builderIn) {
      super(tier, builderIn);
      this.attackDamage = (float)attackDamageIn + tier.getAttackDamage();
      Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
      builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", (double)this.attackDamage, AttributeModifier.Operation.ADDITION));
      builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", (double)attackSpeedIn, AttributeModifier.Operation.ADDITION));
      this.attributeModifiers = builder.build();
   }
   
   PlayerEntity player = Minecraft.getInstance().player;
	
   public float getAttackDamage() {
      return this.attackDamage;
   }

   public boolean canPlayerBreakBlockWhileHolding(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
      return !player.isCreative();
   }

   public float getDestroySpeed(ItemStack stack, BlockState state) {
      if (state.matchesBlock(Blocks.COBWEB)) {
      return 10.0F;
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
      stack.damageItem(4, entityLiving, (entity) -> {
      entity.sendBreakAnimation(EquipmentSlotType.MAINHAND);
        });
      }
      return true;
    }

    public boolean canHarvestBlock(BlockState blockIn) {
      return blockIn.matchesBlock(Blocks.COBWEB);
    }

    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot) {
      return equipmentSlot == EquipmentSlotType.MAINHAND ? this.attributeModifiers : super.getAttributeModifiers(equipmentSlot);
    }

	public UseAction getUseAction(ItemStack stack) {
      return UseAction.SPEAR;
    }
	
	public int getUseDuration(ItemStack stack) {
      return 72000;
    }
	
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
		Vector3d dir = MathUtils.direction(player);
		float f7 = player.rotationYaw;
        float f = player.rotationPitch;
		float f1 = -MathHelper.sin(f7 * ((float)Math.PI / 180F)) * MathHelper.cos(f * ((float)Math.PI / 180F));
		float f2 = -MathHelper.sin(f * ((float)Math.PI / 180F));
		float f3 = MathHelper.cos(f7 * ((float)Math.PI / 180F)) * MathHelper.cos(f * ((float)Math.PI / 180F));
		float f4 = MathHelper.sqrt(f1 * f1 + f2 * f2 + f3 * f3);
		float f5 = 3.0F * (1.0F + 0 / 4.0F);
		f1 = f1 * (f5 / f4);
		f2 = f2 * (f5 / f4);
		f3 = f3 * (f5 / f4);
		player.startSpinAttack(20);
		player.addVelocity((double)f1, (double)f2, (double)f3);
		if (player.isOnGround()) {
		  float f6 = 1.1999999F;
		  player.move(MoverType.SELF, new Vector3d(0.0D, (double)1.1999999F, 0.0D));
	    }
	}
}
