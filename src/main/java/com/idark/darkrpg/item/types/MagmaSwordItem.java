package com.idark.darkrpg.item.types;

import com.idark.darkrpg.util.ModSoundRegistry;
import com.idark.darkrpg.math.*;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.stats.Stats;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SuppressWarnings("ALL")
public class MagmaSwordItem extends SwordItem {

    Random rand = new Random();

    public MagmaSwordItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn) {
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
    }

    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchant){
        return enchant != Enchantments.FIRE_ASPECT;
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
		return 30;
	}

    /**
     *     Some sounds taken from the CalamityMod (Terraria) in a https://calamitymod.fandom.com/wiki/Category:Sound_effects
      */
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
        PlayerEntity player = (PlayerEntity)entityLiving;
        Entity entity = (Entity)entityLiving;			
		if (isCharged(stack) == 2) {
			if (entity.isWet() == true) {
			player.getCooldownTracker().setCooldown(this, 150);
			player.addStat(Stats.ITEM_USED.get(this));
            setCharge(stack, 1);
			worldIn.playSound(player, player.getPosition(), SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 10f, 1f);			
			if (!player.isCreative()) {
				stack.damageItem(5, player, (p_220045_0_) -> {p_220045_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);});
			}
			
            for (int i = 0; i < 20; i++) {
                worldIn.addParticle(ParticleTypes.POOF, player.getPosX() + ((rand.nextDouble() - 0.5D) * 3), player.getPosY() + ((rand.nextDouble() - 0.5D) * 3), player.getPosZ() + ((rand.nextDouble() - 0.5D) * 3), 0.05d * ((rand.nextDouble() - 0.5D) * 2), 0.05d * ((rand.nextDouble() - 0.5D) * 2), 0.05d * ((rand.nextDouble() - 0.5D) * 2));
                worldIn.addParticle(ParticleTypes.LARGE_SMOKE, player.getPosX() + ((rand.nextDouble() - 0.5D) * 3), player.getPosY() + ((rand.nextDouble() - 0.5D) * 3), player.getPosZ() + ((rand.nextDouble() - 0.5D) * 3), 0.05d * ((rand.nextDouble() - 0.5D) * 2), 0.05d * ((rand.nextDouble() - 0.5D) * 2), 0.05d * ((rand.nextDouble() - 0.5D) * 2));
			}
		} else if (entity.isWet() == false) {
			player.getCooldownTracker().setCooldown(this, 300);
			player.addStat(Stats.ITEM_USED.get(this));
            setCharge(stack, 0);						
			Vector3d pos = new Vector3d(player.getPosX(), player.getPosY() + player.getEyeHeight(), player.getPosZ());
			List<LivingEntity> hitEntities = new ArrayList<LivingEntity>();

			for (int i = 0; i < 360; i += 10) {
				float yawDouble = 0;
				if (i <= 180) {
					yawDouble = ((float) i) / 180F;
				} else {
					yawDouble = 1F - ((((float) i) - 180F) / 180F);
				}
				hitDirection(worldIn, player, hitEntities, pos, 0, player.getPitchYaw().y + i, 4);
			}

			float damage = (float) (player.getAttribute(Attributes.ATTACK_DAMAGE).getValue()) + EnchantmentHelper.getSweepingDamageRatio(player);

			for (LivingEntity entityHitted : hitEntities) {
				entityHitted.attackEntityFrom(DamageSource.GENERIC, damage);
				entityHitted.applyKnockback(0.4F, player.getPosX() - entity.getPosX(), player.getPosZ() - entity.getPosZ());
				entityHitted.setFire(5);
			}
			
			if (!player.isCreative()) {
				stack.damageItem(hitEntities.size(), player, (p_220045_0_) -> {p_220045_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);});
				}
				
			worldIn.playSound(player, player.getPosition(), ModSoundRegistry.ERUPTION.get(), SoundCategory.AMBIENT, 10f, 1f);	
			}
		}
	}

    public static int isCharged(ItemStack stack) {
        CompoundNBT nbt = stack.getTag();
        if (nbt==null) {
            nbt = new CompoundNBT();
            stack.setTag(nbt);
        }
        if (!nbt.contains("charge")) {
            nbt.putInt("charge", 0);
            stack.setTag(nbt);
            return 0;
        } else {
            return nbt.getInt("charge");
        }
    }
	
    public static void setCharge(ItemStack stack, int charge) {
        CompoundNBT nbt = stack.getTag();
        if (nbt==null) {
            nbt = new CompoundNBT();
            stack.setTag(nbt);
        }
        nbt.putInt("charge", charge);
        stack.setTag(nbt);
    }
	
    public static String getModeString(ItemStack stack) {
        CompoundNBT nbt = stack.getTag();
        if (isCharged(stack) == 2) {
            return "tooltip.darkrpg.magma_charge_full";
        } else if (isCharged(stack) == 1) {
			return "tooltip.darkrpg.magma_charge_half";
		}
		
		return "tooltip.darkrpg.magma_charge_empty";
	}
	
    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flags) {
        super.addInformation(stack, world, tooltip, flags);
        tooltip.add(1, new TranslationTextComponent("tooltip.darkrpg.infernal_sword").mergeStyle(TextFormatting.GRAY));
        tooltip.add(2, new TranslationTextComponent(getModeString(stack)).mergeStyle(TextFormatting.YELLOW));
        tooltip.add(3, new StringTextComponent("                "));
        tooltip.add(4, new TranslationTextComponent("tooltip.darkrpg.rmb").mergeStyle(TextFormatting.GREEN));
	}	

	public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		stack.damageItem(1, attacker, (entity) -> {
			entity.sendBreakAnimation(EquipmentSlotType.MAINHAND);
		});	

		if (isCharged(stack) < 2) {
			if (RandUtils.doWithChance(5)) {
				if (isCharged(stack) == 0) {
					setCharge(stack, 1);
				} else if (isCharged(stack) == 1) {
					setCharge(stack, 2);
				}
				
			if (target.world.isRemote) {
				for (int i = 0;i<25;i++) {
					target.world.addParticle(ParticleTypes.FLAME, target.getPosX() + rand.nextDouble(), target.getPosY(), target.getPosZ() + rand.nextDouble(), 0d, 0.05d, 0d);
				}
			}
		
			return true;
			}
		}
		
	return true;
	}

    public void hitDirection(World worldIn, PlayerEntity player, List<LivingEntity> hitEntities, Vector3d pos, float pitchRaw, float yawRaw, float distance) {
        double pitch = ((pitchRaw + 90) * Math.PI) / 180;
        double yaw = ((yawRaw + 90) * Math.PI) / 180;

        double locYaw = 0;
        double locPitch = 0;
        double locDistance = distance;

        double X = Math.sin(locPitch + pitch) * Math.cos(locYaw + yaw) * locDistance;
        double Y = Math.cos(locPitch + pitch) * locDistance;
        double Z = Math.sin(locPitch + pitch) * Math.sin(locYaw + yaw) * locDistance;
        BlockRayTraceResult ray = worldIn.rayTraceBlocks(new RayTraceContext(pos, new Vector3d(pos.x + X, pos.y + Y, pos.z + Z), RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.NONE, player));
        X = ray.getHitVec().getX();
        Y = ray.getHitVec().getY();
        Z = ray.getHitVec().getZ();

        worldIn.addParticle(ParticleTypes.POOF, X + ((rand.nextDouble() - 0.5D) * 2), Y + ((rand.nextDouble() - 0.5D) * 2), Z + ((rand.nextDouble() - 0.5D) * 2), 0.05d * ((rand.nextDouble() - 0.5D) * 2), 0.05d * ((rand.nextDouble() - 0.5D) * 2), 0.05d * ((rand.nextDouble() - 0.5D) * 2));
        List<Entity> entities = worldIn.getEntitiesWithinAABB(Entity.class,  new AxisAlignedBB(X - 2D,Y - 2D,Z - 2D,X + 2D,Y + 2D,Z + 2D));
        for (Entity entity : entities) {
            if (entity instanceof  LivingEntity) {
                LivingEntity enemy = (LivingEntity)entity;
                if (!hitEntities.contains(enemy) && (!enemy.equals(player))) {
                    hitEntities.add(enemy);
                }
            }
        }

        locDistance = distance(pos, new Vector3d(X, Y, Z));

        X = Math.sin(locPitch + pitch) * Math.cos(locYaw + yaw) * locDistance * 0.75F;
        Y = Math.cos(locPitch + pitch) * locDistance * 0.75F;
        Z = Math.sin(locPitch + pitch) * Math.sin(locYaw + yaw) * locDistance * 0.75F;
        worldIn.addParticle(ParticleTypes.LAVA, pos.x + X + ((rand.nextDouble() - 0.5D) * 0.2F), pos.y + Y + ((rand.nextDouble() - 0.5D) * 0.2F), pos.z + Z + ((rand.nextDouble() - 0.5D) * 0.2F), 0d, 0d, 0d);
    }

    public static double distance(Vector3d pos1, Vector3d pos2){
        return Math.sqrt((pos2.x - pos1.x) * (pos2.x - pos1.x) + (pos2.y - pos1.y)*(pos2.y - pos1.y) + (pos2.z - pos1.z)*(pos2.z - pos1.z));
    }
}