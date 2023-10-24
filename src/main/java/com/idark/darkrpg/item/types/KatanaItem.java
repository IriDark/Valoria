package com.idark.darkrpg.item.types;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.idark.darkrpg.client.render.DashOverlayRender;
import com.idark.darkrpg.config.ClientConfig;
import com.idark.darkrpg.util.ModSoundRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.*;
import org.joml.Vector3d;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class KatanaItem extends TieredItem implements Vanishable {
	private final float attackDamage;
	private final Multimap<Attribute, AttributeModifier> attributeModifiers;
	Random rand = new Random();

	public KatanaItem(Tier tier, int attackDamageIn, float attackSpeedIn, Item.Properties builderIn) {
		super(tier, builderIn);
		this.attackDamage = (float)attackDamageIn + tier.getAttackDamageBonus();
		ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", (double)this.attackDamage, AttributeModifier.Operation.ADDITION));
		builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", (double)attackSpeedIn, AttributeModifier.Operation.ADDITION));
		this.attributeModifiers = builder.build();
	}
	
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchant){
		return enchant.category != EnchantmentCategory.BREAKABLE && enchant.category == EnchantmentCategory.WEAPON || enchant.category == EnchantmentCategory.DIGGER;
    }
   	
	public float getAttackDamage() {
		return this.attackDamage;
	}

	public boolean canAttackBlock(BlockState state, Level worldIn, BlockPos pos, Player player) {
		return !player.isCreative();
	}

	public float getDestroySpeed(ItemStack stack, BlockState state) {
		if (state.is(Blocks.COBWEB)) {
			return 15.0F;
		} else {
			return state.is(BlockTags.SWORD_EFFICIENT) ? 1.5F : 1.0F;
		}
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
	
	public boolean isCorrectToolForDrops(BlockState blockIn) {
		return blockIn.is(Blocks.COBWEB);
    }

    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot equipmentSlot) {
      return equipmentSlot == EquipmentSlot.MAINHAND ? this.attributeModifiers : super.getDefaultAttributeModifiers(equipmentSlot);
    }

	public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
		ItemStack itemstack = playerIn.getItemInHand(handIn);
		if (itemstack.getDamageValue() >= itemstack.getMaxDamage() - 1) {
			return InteractionResultHolder.fail(itemstack);
		} else {
			playerIn.startUsingItem(handIn);
			itemstack.hurtAndBreak(10, playerIn, (entity) -> {
			playerIn.broadcastBreakEvent(EquipmentSlot.MAINHAND);
		});
		
		return InteractionResultHolder.consume(itemstack);
		}
	}

	public UseAnim getUseAnimation(ItemStack stack) {
      return UseAnim.NONE;
    }
	
	public int getUseDuration(ItemStack stack) {
      return 30;
	}
	
    // Some sounds taken from the CalamityMod (Terraria) in a https://calamitymod.fandom.com/wiki/Category:Sound_effects
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entityLiving, int timeLeft) {
		Player player = (Player)entityLiving;
		if (!player.isFallFlying()) {
			Vec3 dir = (player.getViewVector(0.0f).scale(2.0d));
			if (dir.x < 5f) {
				player.push(dir.x, dir.y * 0.25, dir.z);
			} else {
				player.push(5, 2 * 0.25, 5);
			}

			player.getCooldowns().addCooldown(this, 75);
			player.awardStat(Stats.ITEM_USED.get(this));

			Vector3d pos = new Vector3d(player.getX(), player.getY() + player.getEyeHeight(), player.getZ());
			List<LivingEntity> hitEntities = new ArrayList<LivingEntity>();
			double maxDistance = distance(pos, level, player);

			for (int i = 0; i < 10; i += 1) {
				double pitch = ((player.getRotationVector().x + 90) * Math.PI) / 180;
				double yaw = ((player.getRotationVector().y + 90) * Math.PI) / 180;

				double locYaw = 0;
				double locPitch = 0;
				double locDistance = i * 0.5D;

				double X = Math.sin(locPitch + pitch) * Math.cos(locYaw + yaw) * locDistance;
				double Y = Math.cos(locPitch + pitch) * locDistance;
				double Z = Math.sin(locPitch + pitch) * Math.sin(locYaw + yaw) * locDistance;

				level.addParticle(ParticleTypes.POOF, pos.x + X + (rand.nextDouble() - 0.5D), pos.y + Y, pos.z + Z + (rand.nextDouble() - 0.5D), 0d, 0.05d, 0d);
				List<Entity> entities = level.getEntitiesOfClass(Entity.class,  new AABB(pos.x + X - 0.5D,pos.y + Y - 0.5D,pos.z + Z - 0.5D,pos.x + X + 0.5D,pos.y + Y + 0.5D,pos.z + Z + 0.5D));
				for (Entity entity : entities) {
					if (entity instanceof  LivingEntity) {
						LivingEntity enemy = (LivingEntity)entity;
						if (!hitEntities.contains(enemy) && (!enemy.equals(player))) {
							hitEntities.add(enemy);
						}
					}
				}

				if (locDistance >= maxDistance) {
					break;
				}
			}

			int hits = hitEntities.size();
			float ii = 1F;

			for (LivingEntity entity : hitEntities) {
				entity.hurt(level.damageSources().generic(), (float) (player.getAttribute(Attributes.ATTACK_DAMAGE).getValue() * ii) + EnchantmentHelper.getSweepingDamageRatio(player));
				entity.knockback(0.4F, player.getX() - entity.getX(), player.getZ() - entity.getZ());
				if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FIRE_ASPECT, stack) > 0) {
					int i = EnchantmentHelper.getFireAspect(player);
					entity.setSecondsOnFire(i * 4);
				}
				ii = ii - (1F / (hits * 2));
			}

			if (!player.isCreative()) {
				stack.hurtAndBreak(hits, player, (p_220045_0_) -> {p_220045_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND);});
			}

			for (int i = 0;i<4;i++) {
				level.addParticle(ParticleTypes.POOF, player.getX() + (rand.nextDouble() - 0.5D), player.getY(), player.getZ() + (rand.nextDouble() - 0.5D), 0d, 0.05d, 0d);
			}
		
			level.playSound(player, player.blockPosition(), ModSoundRegistry.SWIFTSLICE.get(), SoundSource.AMBIENT, 10f, 1f);
			if (ClientConfig.DASH_OVERLAY.get()) {
				DashOverlayRender.isDash = true;
			}
		}
	}

    public static double distance(Vector3d pos1, Level worldIn, Player player) {
        double pitch = ((player.getRotationVector().x + 90) * Math.PI) / 180;
        double yaw = ((player.getRotationVector().y + 90) * Math.PI) / 180;

        double locYaw = 0;
        double locPitch = 0;
        double locDistance = 5D;

		Vec3 pos = new Vec3(player.getX(), player.getY() + player.getEyeHeight(), player.getZ());

		double X = Math.sin(locPitch + pitch) * Math.cos(locYaw + yaw) * locDistance;
        double Y = Math.cos(locPitch + pitch) * locDistance;
        double Z = Math.sin(locPitch + pitch) * Math.sin(locYaw + yaw) * locDistance;

		Vec3 playerPos = player.getEyePosition();
		Vec3 EndPos = (player.getViewVector(0.0f).scale(2.0d));
		Vec3 vec3 = playerPos.add(EndPos);
		HitResult hitresult = worldIn.clip(new ClipContext(playerPos, vec3, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, null));
		if (hitresult.getType() != HitResult.Type.MISS) {
			vec3 = hitresult.getLocation();
		}

		X = hitresult.getLocation().x() - pos.x;
		Y = hitresult.getLocation().y() - pos.y;
		Z = hitresult.getLocation().z() - pos.z;

        return Math.sqrt((X - pos1.x) * (X - pos1.x) + (Y - pos1.y)*(Y - pos1.y) + (Z - pos1.z)*(Z - pos1.z));
    }

    @Override
	public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags) {
		super.appendHoverText(stack, world, tooltip, flags);
		tooltip.add(Component.translatable("tooltip.darkrpg.katana").withStyle(ChatFormatting.GRAY));
		tooltip.add(Component.translatable("tooltip.darkrpg.rmb").withStyle(ChatFormatting.GREEN));
   }
}