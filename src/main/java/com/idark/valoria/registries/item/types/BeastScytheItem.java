package com.idark.valoria.registries.item.types;

import com.idark.valoria.client.particle.ModParticles;
import com.idark.valoria.registries.SoundsRegistry;
import com.idark.valoria.util.ValoriaUtils;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.joml.Vector3d;

import java.util.ArrayList;
import java.util.List;

public class BeastScytheItem extends ScytheItem implements Vanishable {

    public BeastScytheItem(Tier tier, int attackDamageIn, float attackSpeedIn, Item.Properties builderIn) {
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
    }

    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        pStack.hurtAndBreak(1, pAttacker, (p_43296_) -> p_43296_.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        pAttacker.level().playSound(null, pTarget.getOnPos(), SoundEvents.EVOKER_FANGS_ATTACK, SoundSource.AMBIENT, 1, 1);
        return true;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entityLiving) {
        Player player = (Player) entityLiving;
        player.awardStat(Stats.ITEM_USED.get(this));
        float damage = (float) (player.getAttributeValue(Attributes.ATTACK_DAMAGE)) + EnchantmentHelper.getSweepingDamageRatio(player);
        for (Item item : ForgeRegistries.ITEMS) {
            if (item instanceof ScytheItem) {
                player.getCooldowns().addCooldown(item, 100);
            }
        }

        Vector3d pos = new Vector3d(player.getX(), player.getY() + player.getEyeHeight(), player.getZ());
        List<LivingEntity> hitEntities = new ArrayList<>();
        List<LivingEntity> markEntities = new ArrayList<>();
        ValoriaUtils.radiusHit(level, stack, player, null, hitEntities, pos, 0, player.getRotationVector().y, 3);
        ValoriaUtils.spawnParticlesMark(level, player, markEntities, ModParticles.CHOMP.get(), pos, 0, player.getRotationVector().y, 3);
        for (LivingEntity entity : hitEntities) {
            entity.hurt(level.damageSources().playerAttack(player), (damage + EnchantmentHelper.getDamageBonus(stack, entity.getMobType())) * 1.35f);
            entity.knockback(0.4F, player.getX() - entity.getX(), player.getZ() - entity.getZ());
            if (EnchantmentHelper.getFireAspect(player) > 0) {
                int i = EnchantmentHelper.getFireAspect(player);
                entity.setSecondsOnFire(i * 4);
            }

            if (!player.isCreative()) {
                stack.hurtAndBreak(hitEntities.size(), player, (p_220045_0_) -> p_220045_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND));
            }
        }

        level.playSound(null, player.blockPosition(), SoundEvents.EVOKER_FANGS_ATTACK, SoundSource.AMBIENT, 10f, 1f);
        level.playSound(null, player.blockPosition(), SoundsRegistry.SWIFTSLICE.get(), SoundSource.AMBIENT, 10f, 1f);
        return stack;
    }
}