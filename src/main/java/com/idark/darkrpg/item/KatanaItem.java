package com.idark.darkrpg.item;

import com.idark.darkrpg.math.*;
import com.idark.darkrpg.util.ModSoundRegistry;
import net.minecraft.block.SoundType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import net.minecraft.client.Minecraft;
import net.minecraft.stats.Stats;
import net.minecraft.particles.*;
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
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeMod;

import java.util.Random;
import java.util.UUID;
import java.util.List;
import java.util.function.Supplier;

public class KatanaItem extends TieredItem implements IVanishable {
   private final float attackDamage;
   private final Multimap<Attribute, AttributeModifier> attributeModifiers;
   Random rand = new Random();

   public KatanaItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Item.Properties builderIn) {
      super(tier, builderIn);
      this.attackDamage = (float)attackDamageIn + tier.getAttackDamage();
      Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
      builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", (double)this.attackDamage, AttributeModifier.Operation.ADDITION));
      builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", (double)attackSpeedIn, AttributeModifier.Operation.ADDITION));
      this.attributeModifiers = builder.build();
   }
   	
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
	
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
      ItemStack itemstack = playerIn.getHeldItem(handIn);
      if (itemstack.getDamage() >= itemstack.getMaxDamage() - 1) {
         return ActionResult.resultFail(itemstack);
		} else {
         playerIn.setActiveHand(handIn);
         return ActionResult.resultConsume(itemstack);
      }
	}

	public UseAction getUseAction(ItemStack stack) {
      return UseAction.NONE;
    }
	
	public int getUseDuration(ItemStack stack) {
      return 78000;
	}
	
	//Sounds taken from the CalamityMod (Terraria) in a https://calamitymod.fandom.com/wiki/Category:Sound_effects
	//TODO Overlay & Sound after cooldown
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
        PlayerEntity player = (PlayerEntity)entityLiving;
        Vector3d dir = MathUtils.direction(player);
	    player.addVelocity(dir.x,dir.y * 0.25f,dir.z);
        player.move(MoverType.SELF,new Vector3d(dir.x,dir.y,dir.z));
		player.getCooldownTracker().setCooldown(this, 45);
		player.addStat(Stats.ITEM_USED.get(this));
		if (worldIn.isRemote) {
		for (int i = 0;i<10;i++) {
		  worldIn.addParticle(ParticleTypes.POOF, player.getPosX() + rand.nextDouble(), player.getPosY(), player.getPosZ() + rand.nextDouble(), 0d, 0.05d, 0d);
		}
          worldIn.playSound(player, player.getPosition(), ModSoundRegistry.SWIFTSLICE.get(), SoundCategory.AMBIENT, 10f, 1f);
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flags) {
	super.addInformation(stack, world, tooltip, flags);
	tooltip.add(new TranslationTextComponent("tooltip.darkrpg.katana").mergeStyle(TextFormatting.GRAY));
	tooltip.add(new TranslationTextComponent("tooltip.darkrpg.rmb").mergeStyle(TextFormatting.GREEN));
	}
}