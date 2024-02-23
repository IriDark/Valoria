package com.idark.valoria.registries.world.item.types;

import com.idark.valoria.registries.world.item.enchant.ModEnchantments;
import com.idark.valoria.registries.sounds.ModSoundRegistry;
import com.idark.valoria.util.ModUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

public class BlazeReapItem extends PickaxeItem {

    Random rand = new Random();

    public BlazeReapItem(Tier tier, int attackDamageIn, float attackSpeedIn, Properties builder) {
        super(tier, attackDamageIn, attackSpeedIn, builder);
    }

    public int getUseDuration(ItemStack stack) {
        return 30;
    }

    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchant) {
        return enchant.category == EnchantmentCategory.WEAPON || enchant.category == EnchantmentCategory.DIGGER || enchant.category == ModEnchantments.BLAZE;
    }

    /**
     *Some sounds taken from the CalamityMod (Terraria) in a <a href="https://calamitymod.wiki.gg/wiki/Category:Sound_effects">Calamity Mod Wiki.gg</a>
     */
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (player.isShiftKeyDown()) {
            if (isCharged(itemstack) == 0) {
                List<ItemStack> items = player.inventoryMenu.getItems();
                int gunpowder = 0;
                boolean canCharge = false;

                if (!player.isCreative()) {
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

                    if (!player.isCreative()) {
                        for (ItemStack item : items) {
                            if (item.getItem().equals(Items.GUNPOWDER)) {
                                if (gunpowder - item.getCount() >= 0) {
                                    gunpowder = gunpowder - item.getCount();
                                    player.getInventory().removeItem(item);
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
                    player.getCooldowns().addCooldown(this, 20);
                    level.playSound(player, player.blockPosition(), ModSoundRegistry.BLAZECHARGE.get(), SoundSource.AMBIENT, 10f, 1f);
                    player.awardStat(Stats.ITEM_USED.get(this));
                } else {
                    player.displayClientMessage(Component.translatable("tooltip.valoria.recharge").withStyle(ChatFormatting.GRAY), true);
                }

                for(int i = 0; i < 6; ++i) {
                    double d0 = rand.nextGaussian() * 0.02D;
                    double d1 = rand.nextGaussian() * 0.02D;
                    double d2 = rand.nextGaussian() * 0.02D;
                    level.addParticle(ParticleTypes.FLAME, player.getRandomX(1.0D), player.getRandomY() - 0.5D, player.getRandomZ(1.0D), d0, d1, d2);
                }
            }

            return InteractionResultHolder.pass(itemstack);
        } else if (isCharged(itemstack) == 1) {
            setCharge(itemstack, 0);
            player.getCooldowns().addCooldown(this, 50);
            player.awardStat(Stats.ITEM_USED.get(this));
            Vec3 pos = new Vec3(player.getX(), player.getY() + player.getEyeHeight(), player.getZ());
            double pitch = ((player.getRotationVector().x + 90) * Math.PI) / 180;
            double yaw = ((player.getRotationVector().y + 90) * Math.PI) / 180;
            double locYaw = 0;
            double locPitch = 0;
            double locDistance = 15D;
            double X = Math.sin(locPitch + pitch) * Math.cos(locYaw + yaw) * locDistance;
            double Y = Math.cos(locPitch + pitch) * locDistance;
            double Z = Math.sin(locPitch + pitch) * Math.sin(locYaw + yaw) * locDistance;
            Vec3 playerPos = player.getEyePosition();
            Vec3 EndPos = (player.getViewVector(0.0f).scale(60.0d));
            if (ProjectileUtil.getEntityHitResult(player, playerPos, EndPos, new AABB(pos.x + X - 3D, pos.y + Y - 3D, pos.z + Z - 3D, pos.x + X + 3D, pos.y + Y + 3D, pos.z + Z + 3D), (e) -> true, locDistance) == null) {
                HitResult hitresult = ModUtils.getHitResult(playerPos, player, (e) -> true, EndPos, level);
                if (hitresult != null && hitresult.getType() == HitResult.Type.BLOCK) {
                    X = hitresult.getLocation().x() - pos.x;
                    Y = hitresult.getLocation().y() - pos.y;
                    Z = hitresult.getLocation().z() - pos.z;
                } else {
                    Entity entity = ((EntityHitResult)hitresult).getEntity();
                    X = entity.getX() - pos.x;
                    Y = entity.getY() - pos.y;
                    Z = entity.getZ() - pos.z;
                }
            }

            if (EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.EXPLOSIVE_FLAME.get(), itemstack) > 0) {
                if (!level.isClientSide) {
                    level.explode(player, pos.x + X, pos.y + Y, pos.z + Z, 4.0F, Level.ExplosionInteraction.TNT);
                }
            }

            List<Entity> entities = level.getEntitiesOfClass(Entity.class, new AABB(pos.x + X - 3D, pos.y + Y - 3D, pos.z + Z - 3D, pos.x + X + 3D, pos.y + Y + 3D, pos.z + Z + 3D));
            for (Entity entity : entities) {
                if (entity instanceof LivingEntity enemy) {
                    if (!enemy.equals(player)) {
                        enemy.hurt(level.damageSources().generic(), 10F);
                        enemy.knockback(0.6F, player.getX() + X - entity.getX(), player.getZ() + Z - entity.getZ());
                        if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FIRE_ASPECT, itemstack) > 0) {
                            int i = EnchantmentHelper.getFireAspect(player);
                            enemy.setSecondsOnFire(i * 4);
                        }
                    }
                }
            }

            player.knockback(1.2F, X, Z);
            if (!player.isCreative()) {
                itemstack.hurtAndBreak(10, player, (p_220045_0_) -> p_220045_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND));
            }

            for (int i = 0; i < 10; i++) {
                level.addParticle(ParticleTypes.LARGE_SMOKE, pos.x + X + ((rand.nextDouble() - 0.5D) * 3), pos.y + Y + ((rand.nextDouble() - 0.5D) * 3), pos.z + Z + ((rand.nextDouble() - 0.5D) * 3), 0.05d * ((rand.nextDouble() - 0.5D) * 2), 0.05d * ((rand.nextDouble() - 0.5D) * 2), 0.05d * ((rand.nextDouble() - 0.5D) * 2));
            }
            for (int i = 0; i < 25; i++) {
                level.addParticle(ParticleTypes.FLAME, pos.x + X + ((rand.nextDouble() - 0.5D) * 3), pos.y + Y + ((rand.nextDouble() - 0.5D) * 3), pos.z + Z + ((rand.nextDouble() - 0.5D) * 3), 0.05d * ((rand.nextDouble() - 0.5D) * 2), 0.05d * ((rand.nextDouble() - 0.5D) * 2), 0.05d * ((rand.nextDouble() - 0.5D) * 2));
            }

            if (level instanceof ServerLevel srv) {
                //level.addParticle(ParticleTypes.EXPLOSION_EMITTER, pos.x + X, pos.y + Y, player.getZ() + Z, 0d, 0d, 0d);
                srv.sendParticles(ParticleTypes.EXPLOSION_EMITTER, pos.x + X, pos.y + Y, player.getZ() + Z, 1, 0, 0, 0,0);
            }

            level.playSound(null, player.blockPosition().offset((int) X, (int) (Y + player.getEyeHeight()), (int) Z), SoundEvents.GENERIC_EXPLODE, SoundSource.AMBIENT, 10f, 1f);


            return InteractionResultHolder.success(itemstack);
        }

        return InteractionResultHolder.pass(itemstack);
    }

    public static int isCharged(ItemStack stack) {
        CompoundTag nbt = stack.getTag();
        if (nbt == null) {
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
        if (nbt == null) {
            nbt = new CompoundTag();
            stack.setTag(nbt);
        }

        nbt.putInt("charge", charge);
        stack.setTag(nbt);
    }

    public static String getModeString(ItemStack stack) {
        if (isCharged(stack) == 1) {
            return "tooltip.valoria.rmb";
        } else {
            return "tooltip.valoria.rmb_shift";
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags) {
        super.appendHoverText(stack, world, tooltip, flags);
        tooltip.add(1, Component.translatable("tooltip.valoria.familiar").withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
        tooltip.add(2, Component.empty());
        tooltip.add(3, Component.translatable("tooltip.valoria.blazereap").withStyle(ChatFormatting.GRAY));
        tooltip.add(4, Component.translatable(getModeString(stack)).withStyle(ChatFormatting.GREEN));
    }
}