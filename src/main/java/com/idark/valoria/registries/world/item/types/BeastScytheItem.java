package com.idark.valoria.registries.world.item.types;

import com.idark.valoria.client.particle.ModParticles;
import com.idark.valoria.registries.sounds.ModSoundRegistry;
import com.idark.valoria.util.ModUtils;
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
        ModUtils.applyCooldownToItemList(player, scytheItems, 100);

        Vector3d pos = new Vector3d(player.getX(), player.getY() + player.getEyeHeight(), player.getZ());
        List<LivingEntity> hitEntities = new ArrayList<>();
        List<LivingEntity> markEntities = new ArrayList<>();
        for (int i = 0; i < 360; i += 10) {
            ModUtils.radiusHit(level, stack, player, null, hitEntities, pos, 0, player.getRotationVector().y + i, 3);
            ModUtils.spawnParticlesMark(level, player, markEntities, ModParticles.CHOMP.get(), pos, 0, player.getRotationVector().y + i, 3);
        }

        float damage = (float) (player.getAttributeValue(Attributes.ATTACK_DAMAGE)) + EnchantmentHelper.getSweepingDamageRatio(player);
        for (LivingEntity entity : hitEntities) {
            entity.hurt(level.damageSources().generic(), damage);
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
        level.playSound(null, player.blockPosition(), ModSoundRegistry.SWIFTSLICE.get(), SoundSource.AMBIENT, 10f, 1f);
        return stack;
    }
}