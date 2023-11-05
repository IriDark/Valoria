package com.idark.darkrpg.item.types;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.idark.darkrpg.entity.projectile.KunaiEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class KunaiItem extends Item implements Vanishable {
	private final Multimap<Attribute, AttributeModifier> tridentAttributes;

	public KunaiItem(Item.Properties builderIn) {
		super(builderIn);
		ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", 3.0D, AttributeModifier.Operation.ADDITION));
		builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", (double)-1.9F, AttributeModifier.Operation.ADDITION));
		this.tridentAttributes = builder.build();
	}

	public boolean canAttackBlock(BlockState state, Level worldIn, BlockPos pos, Player player) {
		return !player.isCreative();
	}
	
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchant){
        return enchant == Enchantments.PIERCING ||  enchant == Enchantments.FIRE_ASPECT || enchant == Enchantments.LOYALTY || enchant == Enchantments.MENDING || enchant == Enchantments.SWEEPING_EDGE || enchant == Enchantments.MOB_LOOTING || enchant == Enchantments.SHARPNESS || enchant == Enchantments.BANE_OF_ARTHROPODS || enchant == Enchantments.SMITE;
    }

	public UseAnim getUseAnimation(ItemStack stack) {
		return UseAnim.SPEAR;
	}

	public int getUseDuration(ItemStack stack) {
		return 72000;
	}

	public void releaseUsing(ItemStack stack, Level worldIn, LivingEntity entityLiving, int timeLeft) {
		if (entityLiving instanceof Player playerEntity) {
			int i = this.getUseDuration(stack) - timeLeft;
			if (i >= 6) {
				if (!worldIn.isClientSide) {
					stack.hurtAndBreak(1, playerEntity, (player) -> {
						player.broadcastBreakEvent(entityLiving.getUsedItemHand());
					});
					
                KunaiEntity kunaiEntity = new KunaiEntity(worldIn, playerEntity, stack);
				kunaiEntity.shootFromRotation(playerEntity, playerEntity.getXRot(), playerEntity.getYRot(), 0.0F, 2.5F + (float) 0 * 0.5F, 1.0F);
                if (playerEntity.getAbilities().instabuild) {
					kunaiEntity.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                }

                worldIn.addFreshEntity(kunaiEntity);
                worldIn.playSound(playerEntity, kunaiEntity, SoundEvents.TRIDENT_THROW, SoundSource.PLAYERS, 1.0F, 1.0F);
                if (!playerEntity.getAbilities().instabuild) {
					playerEntity.getInventory().removeItem(stack);
				}
			}

				playerEntity.awardStat(Stats.ITEM_USED.get(this));
			}
		}
	}

	public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
		ItemStack itemstack = playerIn.getItemInHand(handIn);
		if (itemstack.getDamageValue() >= itemstack.getMaxDamage() - 1) {
			return InteractionResultHolder.fail(itemstack);
		} else {
			playerIn.startUsingItem(handIn);
			return InteractionResultHolder.consume(itemstack);
		}
	}

	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		stack.hurtAndBreak(1, attacker, (entity) -> entity.broadcastBreakEvent(EquipmentSlot.MAINHAND));
		
		return true;
	}

	public boolean mineBlock(ItemStack stack, Level worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
		if ((double)state.getDestroySpeed(worldIn, pos) != 0.0D) {
			stack.hurtAndBreak(2, entityLiving, (entity) -> entity.broadcastBreakEvent(EquipmentSlot.MAINHAND));
		}

	return true;
	}

	public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot equipmentSlot) {
		return equipmentSlot == EquipmentSlot.MAINHAND ? this.tridentAttributes : super.getDefaultAttributeModifiers(equipmentSlot);
	}

	public int getEnchantmentValue() {
		return 1;
	}
	
	@Override
	public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags) {
		super.appendHoverText(stack, world, tooltip, flags);	
		tooltip.add(Component.translatable("tooltip.darkrpg.kunai").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.empty());
		tooltip.add(Component.translatable("tooltip.darkrpg.attr").withStyle(ChatFormatting.GRAY));
		tooltip.add(Component.literal(" 5 ").withStyle(ChatFormatting.DARK_GREEN).append(Component.translatable("tooltip.darkrpg.ranged_damage").withStyle(ChatFormatting.DARK_GREEN)));
		if(stack.isEnchanted()) {
			tooltip.add(Component.empty());
			tooltip.add(Component.translatable("tooltip.darkrpg.encht").withStyle(ChatFormatting.GRAY));
		} else {
			return;
		}
	}
}