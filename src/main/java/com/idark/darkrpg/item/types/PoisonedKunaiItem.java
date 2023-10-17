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
		builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", 3.0D, AttributeModifier.Operation.ADDITION));
		builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", (double)-1.9F, AttributeModifier.Operation.ADDITION));
		this.tridentAttributes = builder.build();
	}

	public boolean canAttackBlock(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
		return !player.isCreative();
	}
	
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchant){
        return enchant == Enchantments.PIERCING || enchant == Enchantments.LOYALTY || enchant == Enchantments.MENDING || enchant == Enchantments.SWEEPING_EDGE || enchant == Enchantments.MOB_LOOTING || enchant == Enchantments.SHARPNESS || enchant == Enchantments.BANE_OF_ARTHROPODS || enchant == Enchantments.SMITE;
    }

	public UseAction getUseAnimation(ItemStack stack) {
		return UseAction.SPEAR;
	}

	public int getUseDuration(ItemStack stack) {
		return 72000;
	}

	public void releaseUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
		if (entityLiving instanceof PlayerEntity) {
			PlayerEntity playerentity = (PlayerEntity)entityLiving;
			int i = this.getUseDuration(stack) - timeLeft;
			if (i >= 6) {
				if (!worldIn.isClientSide) {
					stack.hurtAndBreak(1, playerentity, (player) -> {
						player.broadcastBreakEvent(entityLiving.getUsedItemHand());
					});
					
                PoisonedKunaiEntity kunai = new PoisonedKunaiEntity(worldIn, playerentity, stack);
                kunai.shootFromRotation(playerentity, playerentity.xRot, playerentity.yRot, 0.0F, 2.5F + (float) 0 * 0.5F, 1.0F);
                if (playerentity.abilities.instabuild) {
                    kunai.pickup = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;
                }

                worldIn.addFreshEntity(kunai);
                worldIn.playSound((PlayerEntity)null, kunai, SoundEvents.TRIDENT_THROW, SoundCategory.PLAYERS, 1.0F, 1.0F);				
                if (!playerentity.abilities.instabuild) {
                    playerentity.inventory.removeItem(stack);
				}
			}
				
			playerentity.awardStat(Stats.ITEM_USED.get(this));
			}
		}
	}

	public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack itemstack = playerIn.getItemInHand(handIn);
		if (itemstack.getDamageValue() >= itemstack.getMaxDamage() - 1) {
			return ActionResult.fail(itemstack);
		} else {
			playerIn.startUsingItem(handIn);
			return ActionResult.consume(itemstack);
		}
	}

	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		stack.hurtAndBreak(1, attacker, (entity) -> {
			entity.broadcastBreakEvent(EquipmentSlotType.MAINHAND);
		});	
		
		if (RandUtils.doWithChance(25)) {
			target.addEffect(new EffectInstance(Effects.POISON, 425, 0));
			if (target.level.isClientSide) {
				for (int i = 0;i<10;i++) {
				target.level.addParticle(ParticleTypes.POOF, target.getX() + rand.nextDouble(), target.getY(), target.getZ() + rand.nextDouble(), 0d, 0.05d, 0d);
				}
			}
		
		return true;
		}
	
	return true;
	}

	public boolean mineBlock(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
		if ((double)state.getDestroySpeed(worldIn, pos) != 0.0D) {
			stack.hurtAndBreak(2, entityLiving, (entity) -> {
				entity.broadcastBreakEvent(EquipmentSlotType.MAINHAND);
			});
		}

	return true;
	}

	public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlotType equipmentSlot) {
		return equipmentSlot == EquipmentSlotType.MAINHAND ? this.tridentAttributes : super.getDefaultAttributeModifiers(equipmentSlot);
	}

	public int getEnchantmentValue() {
		return 1;
	}
	
	@Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flags) {
		super.appendHoverText(stack, world, tooltip, flags);	
		tooltip.add(new TranslationTextComponent("tooltip.darkrpg.kunai").withStyle(TextFormatting.GRAY));
        tooltip.add(new StringTextComponent("                "));
		tooltip.add(new TranslationTextComponent("tooltip.darkrpg.attr").withStyle(TextFormatting.GRAY));
		tooltip.add((new StringTextComponent(" 5 ")).withStyle(TextFormatting.DARK_GREEN).append(new TranslationTextComponent("tooltip.darkrpg.ranged_damage").withStyle(TextFormatting.DARK_GREEN)));
		tooltip.add((new StringTextComponent(" 25% ")).withStyle(TextFormatting.DARK_GREEN).append(new TranslationTextComponent("tooltip.darkrpg.poison_chance").withStyle(TextFormatting.DARK_GREEN)));
		tooltip.add((new StringTextComponent(" 100% ")).withStyle(TextFormatting.DARK_GREEN).append(new TranslationTextComponent("tooltip.darkrpg.poison_chance_ranged").withStyle(TextFormatting.DARK_GREEN)));
	}	
}