package com.idark.valoria.registries.item.types;

import com.idark.valoria.client.particle.ParticleRegistry;
import com.idark.valoria.util.ValoriaUtils;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import org.joml.Vector3d;

import java.util.ArrayList;
import java.util.List;

public class BeastScytheItem extends ScytheItem {

    public BeastScytheItem(Tier tier, int attackDamageIn, float attackSpeedIn, Item.Properties builderIn) {
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
    }

    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        pStack.hurtAndBreak(1, pAttacker, (p_43296_) -> p_43296_.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        pAttacker.level().playSound(null, pTarget.getOnPos(), SoundEvents.EVOKER_FANGS_ATTACK, SoundSource.AMBIENT, 1, 1);
        return true;
    }

    public void performAttack(Level level, ItemStack stack, Player player) {
        float damage = (float) (player.getAttributeValue(Attributes.ATTACK_DAMAGE)) + EnchantmentHelper.getSweepingDamageRatio(player);
        Vector3d pos = new Vector3d(player.getX(), player.getY() + player.getEyeHeight(), player.getZ());
        List<LivingEntity> hitEntities = new ArrayList<>();
        List<LivingEntity> markEntities = new ArrayList<>();
        ValoriaUtils.radiusHit(level, stack, player, null, hitEntities, pos, 0, player.getRotationVector().y, 3);
        ValoriaUtils.spawnParticlesMark(level, player, markEntities, ParticleRegistry.CHOMP.get(), pos, 0, player.getRotationVector().y, 3);
        applyCooldown(hitEntities, player);
        for (LivingEntity entity : hitEntities) {
            entity.hurt(level.damageSources().playerAttack(player), (damage + EnchantmentHelper.getDamageBonus(stack, entity.getMobType())) * 1.35f);
            performEffects(entity, player);
            ValoriaUtils.chanceEffect(entity, effects, chance, arcRandom);
            if (!player.isCreative()) {
                stack.hurtAndBreak(hitEntities.size(), player, (p_220045_0_) -> p_220045_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND));
            }
        }

        level.playSound(null, player.blockPosition(), SoundEvents.EVOKER_FANGS_ATTACK, SoundSource.AMBIENT, 1f, 1f);
    }
}