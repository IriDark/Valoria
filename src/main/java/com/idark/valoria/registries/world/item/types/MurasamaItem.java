package com.idark.valoria.registries.world.item.types;

import com.idark.valoria.client.gui.overlay.DashOverlayRender;
import com.idark.valoria.network.PacketHandler;
import com.idark.valoria.network.packets.MurasamaParticlePacket;
import com.idark.valoria.registries.sounds.ModSoundRegistry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3d;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MurasamaItem extends KatanaItem implements Vanishable {

    static Random rand = new Random();

    public MurasamaItem(Tier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn) {
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
    }

    /**
     * Some sounds taken from the CalamityMod (Terraria) in a <a href="https://calamitymod.wiki.gg/wiki/Category:Sound_effects">Calamity Mod Wiki.gg</a>
     */
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level worldIn, Player playerIn, @NotNull InteractionHand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        if (itemstack.getDamageValue() >= itemstack.getMaxDamage() - 1) {
            return InteractionResultHolder.fail(itemstack);
        } else {
            playerIn.startUsingItem(handIn);
            return InteractionResultHolder.consume(itemstack);
        }
    }

    public void onUseTick(@NotNull Level worldIn, @NotNull LivingEntity livingEntityIn, @NotNull ItemStack stack, int count) {
        Player player = (Player) livingEntityIn;
        addCharge(stack, 1);
        if (worldIn instanceof ServerLevel srv) {
            for (int ii = 0; ii < 1 + Mth.nextInt(RandomSource.create(), 0, 2); ii += 1) {
                PacketHandler.sendToTracking(srv, player.getOnPos(), new MurasamaParticlePacket(3F, (float) player.getX(), (float) (player.getY() + (player.getEyeHeight() / 2)), (float) player.getZ(), 255, 0, 0));
            }
        }

        if (getCharge(stack) == 20) {
            player.playNotifySound(ModSoundRegistry.RECHARGE.get(), SoundSource.PLAYERS, 0.6f, 1);
        }
    }

    public int getUseDuration(@NotNull ItemStack stack) {
        return 78000;
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        if (!slotChanged) {
            return false;
        }
        return super.shouldCauseReequipAnimation(oldStack, newStack, true);
    }

    /**
     * Some sounds taken from the CalamityMod (Terraria) in a <a href="https://calamitymod.wiki.gg/wiki/Category:Sound_effects">Calamity Mod Wiki.gg</a>
     */
    public void releaseUsing(@NotNull ItemStack stack, @NotNull Level level, @NotNull LivingEntity entityLiving, int timeLeft) {
        Player player = (Player) entityLiving;
        player.awardStat(Stats.ITEM_USED.get(this));
        double pitch = ((player.getRotationVector().x + 90) * Math.PI) / 180;
        double yaw = ((player.getRotationVector().y + 90) * Math.PI) / 180;

        if (getCharge(stack) >= 20) {
            Vec3 dir = (player.getViewVector(0.0f).scale(2.0d));
            if (dir.x < 5f) {
                player.push(dir.x, dir.y * 0.25, dir.z);
            } else {
                player.push(5, 2 * 0.25, 5);
            }

            for (Item item : ForgeRegistries.ITEMS) {
                if (item instanceof KatanaItem) {
                    player.getCooldowns().addCooldown(item, 125);
                }
            }

            Vector3d pos = new Vector3d(player.getX(), player.getY() + player.getEyeHeight(), player.getZ());
            List<LivingEntity> hitEntities = new ArrayList<>();
            double maxDistance = distance(pos, level, player);
            for (int i = 0; i < 25; i += 1) {
                double locYaw = 0;
                double locPitch = 0;
                double locDistance = i * 0.5D;
                double X = Math.sin(locPitch + pitch) * Math.cos(locYaw + yaw) * locDistance;
                double Y = Math.cos(locPitch + pitch) * locDistance;
                double Z = Math.sin(locPitch + pitch) * Math.sin(locYaw + yaw) * locDistance;

                level.addParticle(ParticleTypes.WAX_OFF, pos.x + X + (rand.nextDouble() - 0.5D), pos.y + Y, pos.z + Z + (rand.nextDouble() - 0.5D), 0d, 0.05d, 0d);
                if (level instanceof ServerLevel srv) {
                    for (int ii = 0; ii < 1 + Mth.nextInt(RandomSource.create(), 0, 2); ii += 1) {
                        PacketHandler.sendToTracking(srv, player.getOnPos(), new MurasamaParticlePacket(3F, (float) (pos.x + X), (float) (pos.y + Y), (float) (pos.z + Z), 255, 0, 0));
                    }
                }

                List<Entity> entities = level.getEntitiesOfClass(Entity.class, new AABB(pos.x + X - 0.5D, pos.y + Y - 0.5D, pos.z + Z - 0.5D, pos.x + X + 0.5D, pos.y + Y + 0.5D, pos.z + Z + 0.5D));
                for (Entity entity : entities) {
                    if (entity instanceof LivingEntity enemy) {
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
                entity.hurt(level.damageSources().playerAttack(player), (float) ((player.getAttributeValue(Attributes.ATTACK_DAMAGE) * (double) ii) + EnchantmentHelper.getSweepingDamageRatio(player) + EnchantmentHelper.getDamageBonus(stack, entity.getMobType())) * 1.35f);
                entity.knockback(0.4F, player.getX() - entity.getX(), player.getZ() - entity.getZ());
                if (EnchantmentHelper.getTagEnchantmentLevel(Enchantments.FIRE_ASPECT, stack) > 0) {
                    int i = EnchantmentHelper.getFireAspect(player);
                    entity.setSecondsOnFire(i * 4);
                }
                ii = ii - (1F / (hits * 2));
            }

            if (!player.isCreative()) {
                stack.hurtAndBreak(hits, player, (p_220045_0_) -> p_220045_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND));
            }

            for (int i = 0; i < 4; i++) {
                level.addParticle(ParticleTypes.POOF, player.getX() + (rand.nextDouble() - 0.5D), player.getY(), player.getZ() + (rand.nextDouble() - 0.5D), 0d, 0.05d, 0d);
            }

            level.playSound(player, player.blockPosition(), ModSoundRegistry.SWIFTSLICE.get(), SoundSource.AMBIENT, 10f, 1f);
            DashOverlayRender.isDash = true;
            hitEntities.clear();
            double locYaw = 0;
            double locPitch = 0;
            double X = Math.sin(locPitch + pitch) * Math.cos(locYaw + yaw) * maxDistance;
            double Y = Math.cos(locPitch + pitch) * maxDistance;
            double Z = Math.sin(locPitch + pitch) * Math.sin(locYaw + yaw) * maxDistance;

            List<Entity> entities = level.getEntitiesOfClass(Entity.class, new AABB(pos.x + X - 3D, pos.y + Y - 3D, pos.z + Z - 2.5D, pos.x + X + 3D, pos.y + Y + 3D, pos.z + Z + 3D));
            for (Entity entity : entities) {
                if (entity instanceof LivingEntity enemy) {
                    if (!hitEntities.contains(enemy) && (!enemy.equals(player))) {
                        hitEntities.add(enemy);
                    }
                }
            }

            for (LivingEntity entity : hitEntities) {
                entity.hurt(level.damageSources().generic(), (float) (player.getAttributeValue(Attributes.ATTACK_DAMAGE) * ii) + EnchantmentHelper.getSweepingDamageRatio(player));
                entity.knockback(0.4F, player.getX() - entity.getX(), player.getZ() - entity.getZ());
                if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FIRE_ASPECT, entity) > 0) {
                    int i = EnchantmentHelper.getFireAspect(player);
                    entity.setSecondsOnFire(i * 4);
                }
            }
        }

        setCharge(stack, 0);
    }

    public static int getCharge(ItemStack stack) {
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

    public static void addCharge(ItemStack stack, int charge) {
        setCharge(stack, getCharge(stack) + charge);
    }
}