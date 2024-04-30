package com.idark.valoria.registries.item.types;

import com.idark.valoria.registries.SoundsRegistry;
import com.idark.valoria.util.RandomUtil;
import com.idark.valoria.util.ValoriaUtils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import org.joml.Vector3d;

import java.util.ArrayList;
import java.util.List;

public class CoralReefItem extends SwordItem {

    public CoralReefItem(Tier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn) {
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
    }

    /**
     * Some sounds taken from the CalamityMod (Terraria) in a <a href="https://calamitymod.wiki.gg/wiki/Category:Sound_effects">Calamity Mod Wiki.gg</a>
     */
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        pStack.hurtAndBreak(1, pAttacker, (p_43296_) -> p_43296_.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        if (RandomUtil.percentChance(0.07f)) {
            pTarget.knockback(0.6F, pAttacker.getX() - pTarget.getX(), pAttacker.getZ() - pTarget.getZ());
            pTarget.level().playSound(null, pTarget.getOnPos(), SoundsRegistry.WATER_ABILITY.get(), SoundSource.AMBIENT, 0.7f, 1.2f);
        }

        return true;
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

    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    /**
     * Some sounds taken from the CalamityMod (Terraria) in a <a href="https://calamitymod.wiki.gg/wiki/Category:Sound_effects">Calamity Mod Wiki.gg</a>
     */
    public void releaseUsing(ItemStack stack, Level worldIn, LivingEntity entityLiving, int timeLeft) {
        Player player = (Player) entityLiving;
        player.getCooldowns().addCooldown(this, 300);
        player.awardStat(Stats.ITEM_USED.get(this));

        float damage = (float) (player.getAttributeValue(Attributes.ATTACK_DAMAGE)) + EnchantmentHelper.getSweepingDamageRatio(player);
        Vector3d pos = new Vector3d(player.getX(), player.getY() + player.getEyeHeight(), player.getZ());
        List<LivingEntity> hitEntities = new ArrayList<>();

        ValoriaUtils.radiusHit(worldIn, stack, player, ParticleTypes.BUBBLE_POP, hitEntities, pos, 0, player.getRotationVector().y, 3);
        ValoriaUtils.spawnParticlesInRadius(worldIn, stack, ParticleTypes.UNDERWATER, pos, 0, player.getRotationVector().y, 3);
        for (LivingEntity damagedEntity : hitEntities) {
            damagedEntity.hurt(worldIn.damageSources().playerAttack(player), (damage + EnchantmentHelper.getDamageBonus(stack, damagedEntity.getMobType())) * 1.35f);
            damagedEntity.knockback(0.4F, player.getX() - entityLiving.getX(), player.getZ() - entityLiving.getZ());
            if (RandomUtil.percentChance(0.25f)) {
                damagedEntity.knockback(0.6F, player.getX() - damagedEntity.getX(), player.getZ() - damagedEntity.getZ());
                worldIn.playSound(null, damagedEntity.getOnPos(), SoundsRegistry.WATER_ABILITY.get(), SoundSource.AMBIENT, 0.2f, 1.2f);
            }
        }

        if (!player.isCreative()) {
            stack.hurtAndBreak(hitEntities.size(), player, (p_220045_0_) -> p_220045_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        }

        worldIn.playSound(null, player.blockPosition(), SoundsRegistry.WATER_ABILITY.get(), SoundSource.AMBIENT, 0.8f, 1f);
    }
}