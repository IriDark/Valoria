package com.idark.darkrpg.item.types;

import com.idark.darkrpg.util.ModSoundRegistry;
import com.idark.darkrpg.enchant.*;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Random;

public class BlazeReapItem extends PickaxeItem {

    Random rand = new Random();

    public BlazeReapItem(Tier tier, int attackDamageIn, float attackSpeedIn, Properties builder) {
        super(tier, attackDamageIn, attackSpeedIn, builder);
    }

	public int getUseDuration(ItemStack stack) {
		return 30;
	}

    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchant){
        return enchant.category != EnchantmentCategory.BREAKABLE && enchant.category == EnchantmentCategory.WEAPON || enchant.category == EnchantmentCategory.DIGGER  || enchant.category == ModEnchantments.BLAZE;
    }

    // Some sounds taken from the CalamityMod (Terraria) in a https://calamitymod.fandom.com/wiki/Category:Sound_effects
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
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
                                playerIn.getInventory().removeItem(item);
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
                worldIn.playSound(playerIn, playerIn.blockPosition(), ModSoundRegistry.BLAZECHARGE.get(), SoundSource.AMBIENT, 10f, 1f);
                playerIn.awardStat(Stats.ITEM_USED.get(this));
				}
			}
	        return InteractionResultHolder.success(itemstack);
        } else if (isCharged(itemstack) == 1) {
            setCharge(itemstack, 0);
            playerIn.getCooldowns().addCooldown(this, 50);
            playerIn.awardStat(Stats.ITEM_USED.get(this));

            Vec3 pos = new Vec3(playerIn.getX(), playerIn.getY() + playerIn.getEyeHeight(), playerIn.getZ());

            double pitch = ((playerIn.getRotationVector().x + 90) * Math.PI) / 180;
            double yaw = ((playerIn.getRotationVector().y + 90) * Math.PI) / 180;

            double locYaw = 0;
            double locPitch = 0;
            double locDistance = 5D;

            double X = Math.sin(locPitch + pitch) * Math.cos(locYaw + yaw) * locDistance;
            double Y = Math.cos(locPitch + pitch) * locDistance;
            double Z = Math.sin(locPitch + pitch) * Math.sin(locYaw + yaw) * locDistance;

            /*BlockRayTraceResult ray = worldIn.clip(new RayTraceContext(pos, new Vector3d(pos.x + X, pos.y + Y, pos.z + Z), RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.NONE, playerIn));
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
            }*/

            playerIn.knockback(1.2F, X, Z);			
            if (!playerIn.isCreative()) {
                itemstack.hurtAndBreak(10, playerIn, (p_220045_0_) -> {p_220045_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND);});
            }

            for (int i = 0; i < 10; i++) {
                worldIn.addParticle(ParticleTypes.LARGE_SMOKE, pos.x + X + ((rand.nextDouble() - 0.5D) * 3), pos.y + Y + ((rand.nextDouble() - 0.5D) * 3), pos.z + Z + ((rand.nextDouble() - 0.5D) * 3), 0.05d * ((rand.nextDouble() - 0.5D) * 2), 0.05d * ((rand.nextDouble() - 0.5D) * 2), 0.05d * ((rand.nextDouble() - 0.5D) * 2));
            }
            for (int i = 0; i < 25; i++) {
                worldIn.addParticle(ParticleTypes.FLAME, pos.x + X + ((rand.nextDouble() - 0.5D) * 3), pos.y+ Y + ((rand.nextDouble() - 0.5D) * 3), pos.z + Z + ((rand.nextDouble() - 0.5D) * 3), 0.05d * ((rand.nextDouble() - 0.5D) * 2), 0.05d * ((rand.nextDouble() - 0.5D) * 2), 0.05d * ((rand.nextDouble() - 0.5D) * 2));
            }
            worldIn.addParticle(ParticleTypes.EXPLOSION_EMITTER, pos.x + X, pos.y + Y, playerIn.getZ() + Z, 0d, 0d, 0d);
            worldIn.playSound(playerIn, playerIn.blockPosition().offset((int) X, (int) (Y + playerIn.getEyeHeight()), (int) Z), SoundEvents.GENERIC_EXPLODE, SoundSource.AMBIENT, 10f, 1f);


		return InteractionResultHolder.success(itemstack);
		}
		
	return InteractionResultHolder.pass(itemstack);
	}

    public static int isCharged(ItemStack stack) {
        CompoundTag nbt = stack.getTag();
        if (nbt==null) {
            nbt = new CompoundTag();
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
        CompoundTag nbt = stack.getTag();
        if (nbt==null) {
            nbt = new CompoundTag();
            stack.setTag(nbt);
        }
		
        nbt.putInt("charge", charge);
        stack.setTag(nbt);
    }

    public static String getModeString(ItemStack stack) {
        CompoundTag nbt = stack.getTag();
        if (isCharged(stack) == 1) {
            return "tooltip.darkrpg.rmb";
        } else {
			return "tooltip.darkrpg.rmb_shift";
		}
    }

    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags) {
        super.appendHoverText(stack, world, tooltip, flags);
        tooltip.add(1, Component.translatable("tooltip.darkrpg.familiar").withStyle(ChatFormatting.YELLOW, ChatFormatting.ITALIC));
        tooltip.add(2, Component.empty());
        tooltip.add(3, Component.translatable("tooltip.darkrpg.blazereap").withStyle(ChatFormatting.GRAY));
        tooltip.add(4, Component.translatable(getModeString(stack)).withStyle(ChatFormatting.GREEN));
	}
}