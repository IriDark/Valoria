package com.idark.valoria.item.types;

import com.idark.valoria.item.ModItems;
import com.idark.valoria.util.ModUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
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
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import org.joml.Vector3d;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PhantomItem extends SwordItem {
    Random rand = new Random();
    public PhantomItem(Tier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn) {
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
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

    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.NONE;
    }

    public int getUseDuration(ItemStack stack) {
        return 30;
    }

    // Some sounds taken from the CalamityMod (Terraria) in a https://calamitymod.fandom.com/wiki/Category:Sound_effects
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entityLiving, int timeLeft) {
        Player player = (Player)entityLiving;
        player.getCooldowns().addCooldown(this, 10);
        player.awardStat(Stats.ITEM_USED.get(this));

        Vector3d pos = new Vector3d(player.getX(), player.getY() + player.getEyeHeight(), player.getZ());
        List<LivingEntity> hitEntities = new ArrayList<LivingEntity>();

        for (int i = 0; i < 360; i += 10) {
            float yawDouble = 0;
            if (i <= 180) {
                yawDouble = ((float) i) / 180F;
            } else {
                yawDouble = 1F - ((((float) i) - 180F) / 180F);
            }

            ModUtils.radiusHit(level, stack, player, ParticleTypes.SOUL_FIRE_FLAME, hitEntities, pos, 0, player.getRotationVector().y + i, 3);
        }

        if (player.level().isClientSide) {
            for (int a = 0; a < 25; a++) {
                level.addParticle(ParticleTypes.SOUL_FIRE_FLAME, player.getX() + ((rand.nextDouble() - 0.7D) * 1), player.getY() + ((rand.nextDouble() - 1D) * 1), player.getZ() + ((rand.nextDouble() - 0.5D) * 1), 0.05d * ((rand.nextDouble() - 0.5D) * 1), 0.05d * ((rand.nextDouble() - 0.5D) * 1), 0.05d * ((rand.nextDouble() - 0.5D) * 1));
                level.addParticle(ParticleTypes.SOUL_FIRE_FLAME, player.getX() + ((rand.nextDouble() - 0.5D) * 2), player.getY() + ((rand.nextDouble() - 0.5D) * 2), player.getZ() + ((rand.nextDouble() - 0.5D) * 2), 0.05d * ((rand.nextDouble() - 0.5D) * 2), 0.05d * ((rand.nextDouble() - 0.5D) * 2), 0.05d * ((rand.nextDouble() - 0.5D) * 2));
            }
        }

        level.playSound(player, player.blockPosition(), SoundEvents.TOTEM_USE, SoundSource.AMBIENT, 1.0F, 1.0F);
        Minecraft.getInstance().gameRenderer.displayItemActivation(new ItemStack(ModItems.ETERNITY.get()));
        if (!player.isCreative()) {
            stack.hurtAndBreak(35, player, (p_220045_0_) -> p_220045_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        }

        float damage = (float) (player.getAttribute(Attributes.ATTACK_DAMAGE).getValue()) + EnchantmentHelper.getSweepingDamageRatio(player);
        for (LivingEntity entity : hitEntities) {
            entity.hurt(level.damageSources().generic(), damage);
            entity.moveTo(player.getX(), player.getY(), player.getZ(), player.getYRot(), player.getXRot());
            level.addParticle(ParticleTypes.EXPLOSION_EMITTER, player.getX(), player.getY(), player.getZ(), 1f, 1f, 1f);
            entity.knockback(3f, entity.getX() + 2f, entity.getZ() + 2f);
            for (int p = 0; p < 4; p++) {
                level.addParticle(ParticleTypes.SOUL_FIRE_FLAME, entity.getX(), entity.getY(), entity.getZ(), 1f, 1f, 1f);
            }

            if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FIRE_ASPECT, stack) > 0) {
                int e = EnchantmentHelper.getFireAspect(player);
                entity.setSecondsOnFire(e * 4);
            }
        }
    }
}