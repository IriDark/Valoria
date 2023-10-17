package com.idark.darkrpg.item.types;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.idark.darkrpg.entity.projectile.PoisonedKunaiEntity;
import com.idark.darkrpg.math.*;
import net.minecraft.item.*;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.*;
import net.minecraft.potion.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.stats.Stats;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;
import java.util.List;

public class PoisonedKunaiItem extends Item implements IVanishable {
	private final Multimap<Attribute, AttributeModifier> tridentAttributes;
	Random rand = new Random();

	public PoisonedKunaiItem(Item.Properties builderIn) {
		super(builderIn);
		Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Tool modifier", 3.0D, AttributeModifier.Operation.ADDITION));
		builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier", (double)-1.9F, AttributeModifier.Operation.ADDITION));
		this.tridentAttributes = builder.build();
	}

	public boolean canPlayerBreakBlockWhileHolding(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
		return !player.isCreative();
	}
	
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchant){
        return enchant == Enchantments.PIERCING || enchant == Enchantments.LOYALTY || enchant == Enchantments.MENDING || enchant == Enchantments.SWEEPING || enchant == Enchantments.LOOTING || enchant == Enchantments.SHARPNESS || enchant == Enchantments.BANE_OF_ARTHROPODS || enchant == Enchantments.SMITE;
    }

	public UseAction getUseAction(ItemStack stack) {
		return UseAction.SPEAR;
	}

	public int getUseDuration(ItemStack stack) {
		return 72000;
	}

	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
		if (entityLiving instanceof PlayerEntity) {
			PlayerEntity playerentity = (PlayerEntity)entityLiving;
			int i = this.getUseDuration(stack) - timeLeft;
			if (i >= 6) {
				if (!worldIn.isRemote) {
					stack.damageItem(1, playerentity, (player) -> {
						player.sendBreakAnimation(entityLiving.getActiveHand());
					});
					
                PoisonedKunaiEntity kunai = new PoisonedKunaiEntity(worldIn, playerentity, stack);
                kunai.setDirectionAndMovement(playerentity, playerentity.rotationPitch, playerentity.rotationYaw, 0.0F, 2.5F + (float) 0 * 0.5F, 1.0F);
                if (playerentity.abilities.isCreativeMode) {
                    kunai.pickupStatus = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;
                }

                worldIn.addEntity(kunai);
                worldIn.playMovingSound((PlayerEntity)null, kunai, SoundEvents.ITEM_TRIDENT_THROW, SoundCategory.PLAYERS, 1.0F, 1.0F);				
                if (!playerentity.abilities.isCreativeMode) {
                    playerentity.inventory.deleteStack(stack);
				}
			}
				
			playerentity.addStat(Stats.ITEM_USED.get(this));
			}
		}
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

	public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		stack.damageItem(1, attacker, (entity) -> {
			entity.sendBreakAnimation(EquipmentSlotType.MAINHAND);
		});	
		
		if (RandUtils.doWithChance(25)) {
			target.addPotionEffect(new EffectInstance(Effects.POISON, 425, 0));
			if (target.world.isRemote) {
				for (int i = 0;i<10;i++) {
				target.world.addParticle(ParticleTypes.POOF, target.getPosX() + rand.nextDouble(), target.getPosY(), target.getPosZ() + rand.nextDouble(), 0d, 0.05d, 0d);
				}
			}
		
		return true;
		}
	
	return true;
	}

	public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
		if ((double)state.getBlockHardness(worldIn, pos) != 0.0D) {
			stack.damageItem(2, entityLiving, (entity) -> {
				entity.sendBreakAnimation(EquipmentSlotType.MAINHAND);
			});
		}

	return true;
	}

	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot) {
		return equipmentSlot == EquipmentSlotType.MAINHAND ? this.tridentAttributes : super.getAttributeModifiers(equipmentSlot);
	}

	public int getItemEnchantability() {
		return 1;
	}
	
	@Override
	public void addInformation(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flags) {
		super.addInformation(stack, world, tooltip, flags);	
		tooltip.add(new TranslationTextComponent("tooltip.darkrpg.kunai").mergeStyle(TextFormatting.GRAY));
        tooltip.add(new StringTextComponent("                "));
		tooltip.add(new TranslationTextComponent("tooltip.darkrpg.attr").mergeStyle(TextFormatting.GRAY));
		tooltip.add((new StringTextComponent(" 5 ")).mergeStyle(TextFormatting.DARK_GREEN).appendSibling(new TranslationTextComponent("tooltip.darkrpg.ranged_damage").mergeStyle(TextFormatting.DARK_GREEN)));
		tooltip.add((new StringTextComponent(" 25% ")).mergeStyle(TextFormatting.DARK_GREEN).appendSibling(new TranslationTextComponent("tooltip.darkrpg.poison_chance").mergeStyle(TextFormatting.DARK_GREEN)));
		tooltip.add((new StringTextComponent(" 100% ")).mergeStyle(TextFormatting.DARK_GREEN).appendSibling(new TranslationTextComponent("tooltip.darkrpg.poison_chance_ranged").mergeStyle(TextFormatting.DARK_GREEN)));
	}	
}