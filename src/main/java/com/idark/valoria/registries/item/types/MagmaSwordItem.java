package com.idark.valoria.registries.item.types;

import com.idark.valoria.registries.sounds.ModSoundRegistry;
import com.idark.valoria.util.RandomUtil;
import com.idark.valoria.util.ValoriaUtils;
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
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import org.joml.Vector3d;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MagmaSwordItem extends SwordItem implements Vanishable {

    Random rand = new Random();

    public MagmaSwordItem(Tier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn) {
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
    }

    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchant) {
        return enchant != Enchantments.FIRE_ASPECT;
    }

    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        pStack.hurtAndBreak(1, pAttacker, (p_43296_) -> p_43296_.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        if (isCharged(pStack) < 2) {
            if (RandomUtil.percentChance(10d)) {
                addCharge(pStack, 1);
            }
        }

        if (EnchantmentHelper.getFireAspect(pAttacker) > 0) {
            pAttacker.level().playSound(null, pTarget.getOnPos(), SoundEvents.FIRECHARGE_USE, SoundSource.AMBIENT, 1, 1);
        } else if (RandomUtil.percentChance(0.07f)) {
            pTarget.setSecondsOnFire(4);
            pAttacker.level().playSound(null, pTarget.getOnPos(), SoundEvents.FIRECHARGE_USE, SoundSource.AMBIENT, 1, 1);
        }

        return true;
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player playerIn, InteractionHand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        if (itemstack.getDamageValue() >= itemstack.getMaxDamage() - 1) {
            return InteractionResultHolder.fail(itemstack);
        } else {
            playerIn.startUsingItem(handIn);
            return InteractionResultHolder.consume(itemstack);
        }
    }

    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.NONE;
    }

    public int getUseDuration(ItemStack stack) {
        return 30;
    }

    /**
     * Some sounds taken from the CalamityMod (Terraria) in a <a href="https://calamitymod.wiki.gg/wiki/Category:Sound_effects">Calamity Mod Wiki.gg</a>
     */
    public void releaseUsing(ItemStack stack, Level worldIn, LivingEntity entityLiving, int timeLeft) {
        Player player = (Player) entityLiving;
        player.awardStat(Stats.ITEM_USED.get(this));
        if (isCharged(stack) == 2) {
            if (entityLiving.isInWaterOrRain()) {
                player.getCooldowns().addCooldown(this, 150);
                player.displayClientMessage(Component.translatable("tooltip.valoria.wet").withStyle(ChatFormatting.GRAY), true);
                setCharges(stack, 1);
                worldIn.playSound(player, player.blockPosition(), SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 10f, 1f);
                if (!player.isCreative()) {
                    stack.hurtAndBreak(5, player, (p_220045_0_) -> p_220045_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND));
                }

                if (!worldIn.isClientSide() && worldIn instanceof ServerLevel pServer) {
                    for (int i = 0; i < 20; i++) {
                        pServer.sendParticles(ParticleTypes.POOF, player.getX() + ((rand.nextDouble() - 0.5D) * 3), player.getY() + ((rand.nextDouble() - 0.5D) * 3), player.getZ() + ((rand.nextDouble() - 0.5D) * 3), 1, 0.05d * ((rand.nextDouble() - 0.5D) * 2), 0.05d * ((rand.nextDouble() - 0.5D) * 2), 0.05d * ((rand.nextDouble() - 0.5D) * 2), 0);
                        pServer.sendParticles(ParticleTypes.LARGE_SMOKE, player.getX() + ((rand.nextDouble() - 0.5D) * 3), player.getY() + ((rand.nextDouble() - 0.5D) * 3), player.getZ() + ((rand.nextDouble() - 0.5D) * 3), 1, 0.05d * ((rand.nextDouble() - 0.5D) * 2), 0.05d * ((rand.nextDouble() - 0.5D) * 2), 0.05d * ((rand.nextDouble() - 0.5D) * 2), 0);
                    }
                }
            } else {
                player.getCooldowns().addCooldown(this, 300);
                setCharges(stack, 0);
                Vector3d pos = new Vector3d(player.getX(), player.getY() + 0.3f, player.getZ());
                List<LivingEntity> hitEntities = new ArrayList<>();
                for (int i = 0; i < 360; i += 10) {
                    ValoriaUtils.spawnParticlesInRadius(worldIn, stack, ParticleTypes.LARGE_SMOKE, pos, 0, player.getRotationVector().y + i, 1);
                    ValoriaUtils.spawnParticlesInRadius(worldIn, stack, ParticleTypes.LARGE_SMOKE, pos, 0, player.getRotationVector().y + i, 4);
                    ValoriaUtils.radiusHit(worldIn, stack, player, ParticleTypes.FLAME, hitEntities, pos, 0, player.getRotationVector().y + i, 4);
                }

                float damage = (float) (player.getAttributeValue(Attributes.ATTACK_DAMAGE) + 5) + EnchantmentHelper.getSweepingDamageRatio(player);
                for (LivingEntity damagedEntity : hitEntities) {
                    damagedEntity.hurt(worldIn.damageSources().playerAttack(player), (damage + EnchantmentHelper.getDamageBonus(stack, damagedEntity.getMobType())) * 1.35f);
                    damagedEntity.knockback(0.4F, player.getX() - entityLiving.getX(), player.getZ() - entityLiving.getZ());
                    damagedEntity.setSecondsOnFire(12);
                }

                worldIn.playSound(player, player.blockPosition(), ModSoundRegistry.ERUPTION.get(), SoundSource.AMBIENT, 10f, 1f);
                if (!player.isCreative()) {
                    stack.hurtAndBreak(hitEntities.size(), player, (p_220045_0_) -> p_220045_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND));
                }
            }
        } else {
            player.displayClientMessage(Component.translatable("tooltip.valoria.charges").withStyle(ChatFormatting.GRAY), true);
        }
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

    public static void addCharge(ItemStack stack, int charge) {
        CompoundTag nbt = stack.getTag();
        if (nbt == null) {
            nbt = new CompoundTag();
            stack.setTag(nbt);
        }

        int charges = nbt.getInt("charge");
        nbt.putInt("charge", charges + charge);
        stack.setTag(nbt);
    }

    public static void setCharges(ItemStack stack, int charge) {
        CompoundTag nbt = stack.getTag();
        if (nbt == null) {
            nbt = new CompoundTag();
            stack.setTag(nbt);
        }

        nbt.putInt("charge", charge);
        stack.setTag(nbt);
    }

    public static String getModeString(ItemStack stack) {
        if (isCharged(stack) == 2) {
            return "tooltip.valoria.magma_charge_full";
        } else if (isCharged(stack) == 1) {
            return "tooltip.valoria.magma_charge_half";
        }

        return "tooltip.valoria.magma_charge_empty";
    }

    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags) {
        super.appendHoverText(stack, world, tooltip, flags);
        tooltip.add(1, Component.translatable("tooltip.valoria.infernal_sword").withStyle(ChatFormatting.GRAY));
        tooltip.add(2, Component.translatable(getModeString(stack)).withStyle(ChatFormatting.YELLOW));
        tooltip.add(3, Component.empty());
        tooltip.add(4, Component.translatable("tooltip.valoria.rmb").withStyle(ChatFormatting.GREEN));
    }
}