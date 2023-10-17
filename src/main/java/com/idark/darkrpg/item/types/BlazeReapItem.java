package com.idark.darkrpg.item.types;

import com.idark.darkrpg.util.ModSoundRegistry;
import com.idark.darkrpg.enchant.*;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.PickaxeItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.*;

import java.util.List;
import java.util.Random;

import net.minecraft.item.Item.Properties;

public class BlazeReapItem extends PickaxeItem {

    Random rand = new Random();

    public BlazeReapItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builder) {
        super(tier, attackDamageIn, attackSpeedIn, builder);
    }

	public int getUseDuration(ItemStack stack) {
		return 30;
	}

    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchant){
        return enchant.category != EnchantmentType.BREAKABLE && enchant.category == EnchantmentType.WEAPON || enchant.category == EnchantmentType.DIGGER  || enchant.category == ModEnchantments.BLAZE;
    }

    // Some sounds taken from the CalamityMod (Terraria) in a https://calamitymod.fandom.com/wiki/Category:Sound_effects
    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        if (playerIn.isShiftKeyDown()) {
			if (isCharged(itemstack) == 0) {
				List<ItemStack> items = playerIn.inventoryMenu.getItems();
				int gunpowder = 0;
				boolean canCharge = false;

            if (!playerIn.isCreative()) {
                for (ItemStack item : items) {
                    if (item.getItem().equals(Items.GUNPOWDER)) {
                        gunpowder = gunpowder + item.getCount();
                        if (gunpowder >= 5) {
                            canCharge = true;
                            break;
                        }
                    }
                }
            } else {
                canCharge = true;
            }

            if (canCharge) {
                gunpowder = 5;

                if (!playerIn.isCreative()) {
                    for (ItemStack item : items) {
                        if (item.getItem().equals(Items.GUNPOWDER)) {
                            if (gunpowder - item.getCount() >= 0) {
                                gunpowder = gunpowder - item.getCount();
                                playerIn.inventory.removeItem(item);
                            } else {
                                item.setCount(item.getCount() - gunpowder);
                                gunpowder = 0;
                            }

                            if (gunpowder <= 0) {
                                break;
                            }
                        }
                    }
                }

                setCharge(itemstack, 1);
                playerIn.getCooldowns().addCooldown(this, 20);
                worldIn.playSound(playerIn, playerIn.blockPosition(), ModSoundRegistry.BLAZECHARGE.get(), SoundCategory.AMBIENT, 10f, 1f);
                playerIn.awardStat(Stats.ITEM_USED.get(this));
				}
			}
	        return new ActionResult<ItemStack>(ActionResultType.SUCCESS, itemstack);
        } else if (isCharged(itemstack) == 1) {
            setCharge(itemstack, 0);
            playerIn.getCooldowns().addCooldown(this, 50);
            playerIn.awardStat(Stats.ITEM_USED.get(this));

            Vector3d pos = new Vector3d(playerIn.getX(), playerIn.getY() + playerIn.getEyeHeight(), playerIn.getZ());

            double pitch = ((playerIn.getRotationVector().x + 90) * Math.PI) / 180;
            double yaw = ((playerIn.getRotationVector().y + 90) * Math.PI) / 180;

            double locYaw = 0;
            double locPitch = 0;
            double locDistance = 5D;

            double X = Math.sin(locPitch + pitch) * Math.cos(locYaw + yaw) * locDistance;
            double Y = Math.cos(locPitch + pitch) * locDistance;
            double Z = Math.sin(locPitch + pitch) * Math.sin(locYaw + yaw) * locDistance;

            BlockRayTraceResult ray = worldIn.clip(new RayTraceContext(pos, new Vector3d(pos.x + X, pos.y + Y, pos.z + Z), RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.NONE, playerIn));
            X = ray.getLocation().x() - pos.x;
            Y = ray.getLocation().y() - pos.y;
            Z = ray.getLocation().z() - pos.z;

			if (EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.EXPLOSIVE_FLAME.get(), itemstack) > 0) {
				if(!worldIn.isClientSide) {
					worldIn.explode(playerIn, pos.x + X, pos.y + Y, pos.z + Z, 4.0F, true, Explosion.Mode.BREAK);
				}
			}

            List<Entity> entities = worldIn.getEntitiesOfClass(Entity.class,  new AxisAlignedBB(pos.x + X - 3D,pos.y + Y - 3D,pos.z + Z - 3D,pos.x + X + 3D,pos.y + Y + 3D,pos.z + Z + 3D));
            for (Entity entity : entities) {
                if (entity instanceof LivingEntity) {
                    LivingEntity enemy = (LivingEntity)entity;
                    if (!enemy.equals(playerIn)) {
                        enemy.hurt(DamageSource.GENERIC.setExplosion(), 10F);
                        enemy.knockback(0.6F, playerIn.getX() + X - entity.getX(), playerIn.getZ() + Z - entity.getZ());
                        if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FIRE_ASPECT, itemstack) > 0) {
                            int i = EnchantmentHelper.getFireAspect(playerIn);
                            enemy.setSecondsOnFire(i * 4);
						}
                    }
                }
            }

            playerIn.knockback(1.2F, X, Z);			
            if (!playerIn.isCreative()) {
                itemstack.hurtAndBreak(10, playerIn, (p_220045_0_) -> {p_220045_0_.broadcastBreakEvent(EquipmentSlotType.MAINHAND);});
            }

            for (int i = 0; i < 10; i++) {
                worldIn.addParticle(ParticleTypes.LARGE_SMOKE, pos.x + X + ((rand.nextDouble() - 0.5D) * 3), pos.y + Y + ((rand.nextDouble() - 0.5D) * 3), pos.z + Z + ((rand.nextDouble() - 0.5D) * 3), 0.05d * ((rand.nextDouble() - 0.5D) * 2), 0.05d * ((rand.nextDouble() - 0.5D) * 2), 0.05d * ((rand.nextDouble() - 0.5D) * 2));
            }
            for (int i = 0; i < 25; i++) {
                worldIn.addParticle(ParticleTypes.FLAME, pos.x + X + ((rand.nextDouble() - 0.5D) * 3), pos.y+ Y + ((rand.nextDouble() - 0.5D) * 3), pos.z + Z + ((rand.nextDouble() - 0.5D) * 3), 0.05d * ((rand.nextDouble() - 0.5D) * 2), 0.05d * ((rand.nextDouble() - 0.5D) * 2), 0.05d * ((rand.nextDouble() - 0.5D) * 2));
            }
            worldIn.addParticle(ParticleTypes.EXPLOSION_EMITTER, pos.x + X, pos.y + Y, playerIn.getZ() + Z, 0d, 0d, 0d);
            worldIn.playSound(playerIn, playerIn.blockPosition().offset(X, Y + playerIn.getEyeHeight(), Z), SoundEvents.GENERIC_EXPLODE, SoundCategory.AMBIENT, 10f, 1f);


		return new ActionResult<ItemStack>(ActionResultType.SUCCESS, itemstack);
		}
		
	return new ActionResult<ItemStack>(ActionResultType.PASS, itemstack);
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
        if (isCharged(stack) == 1) {
            return "tooltip.darkrpg.rmb";
        } else {
			return "tooltip.darkrpg.rmb_shift";
		}
    }

    @Override
    public void appendHoverText(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flags) {
        super.appendHoverText(stack, world, tooltip, flags);
        tooltip.add(1, new TranslationTextComponent("tooltip.darkrpg.familiar").withStyle(TextFormatting.GRAY, TextFormatting.ITALIC));
        tooltip.add(2, new StringTextComponent("                "));
        tooltip.add(3, new TranslationTextComponent("tooltip.darkrpg.blazereap").withStyle(TextFormatting.GRAY));
        tooltip.add(4, new TranslationTextComponent(getModeString(stack)).withStyle(TextFormatting.GREEN));
	}
}