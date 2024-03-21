package com.idark.valoria.registries.world.item.types;

import com.idark.valoria.registries.sounds.ModSoundRegistry;
import com.idark.valoria.util.ModUtils;
import com.idark.valoria.util.math.RandomUtil;
import net.minecraft.core.particles.ParticleTypes;
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

public class InfernalScytheItem extends ScytheItem implements Vanishable {

    public InfernalScytheItem(Tier tier, int attackDamageIn, float attackSpeedIn, Item.Properties builderIn) {
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
    }

    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        pStack.hurtAndBreak(1, pAttacker, (p_43296_) -> p_43296_.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        if (EnchantmentHelper.getFireAspect(pAttacker) > 0) {
            pAttacker.level().playSound(null, pTarget.getOnPos(), SoundEvents.FIRECHARGE_USE, SoundSource.AMBIENT, 1, 1);
        } else if (RandomUtil.percentChance(0.07f)) {
            pTarget.setSecondsOnFire(4);
            pAttacker.level().playSound(null, pTarget.getOnPos(), SoundEvents.FIRECHARGE_USE, SoundSource.AMBIENT, 1, 1);
        }

        return true;
    }

    /**
     * Some sounds taken from the CalamityMod (Terraria) in a <a href="https://calamitymod.wiki.gg/wiki/Category:Sound_effects">Calamity Mod Wiki.gg</a>
     */
    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entityLiving) {
        Player player = (Player) entityLiving;
        player.awardStat(Stats.ITEM_USED.get(this));
        for (Item item : ForgeRegistries.ITEMS) {
            if (item instanceof ScytheItem) {
                player.getCooldowns().addCooldown(item, 100);
            }
        }

        Vector3d pos = new Vector3d(player.getX(), player.getY() + player.getEyeHeight(), player.getZ());
        List<LivingEntity> hitEntities = new ArrayList<LivingEntity>();
        for (int i = 0; i < 360; i += 10) {
            ModUtils.radiusHit(level, stack, player, ParticleTypes.FLAME, hitEntities, pos, 0, player.getRotationVector().y + i, 3);
            ModUtils.spawnParticlesInRadius(level, stack, ParticleTypes.SMOKE, pos, 0, player.getRotationVector().y + i, 3);

        }

        float damage = (float) (player.getAttributeValue(Attributes.ATTACK_DAMAGE)) + EnchantmentHelper.getSweepingDamageRatio(player);
        for (LivingEntity entity : hitEntities) {
            entity.hurt(level.damageSources().generic(), damage);
            entity.knockback(0.4F, player.getX() - entity.getX(), player.getZ() - entity.getZ());
            if (EnchantmentHelper.getFireAspect(player) > 0) {
                int i = EnchantmentHelper.getFireAspect(player);
                entity.setSecondsOnFire(i * 4);
                entity.level().playSound(null, entity.getOnPos(), SoundEvents.FIRECHARGE_USE, SoundSource.AMBIENT, 1, 1);
            } else if (RandomUtil.percentChance(0.07f)) {
                entity.setSecondsOnFire(4);
                entity.level().playSound(null, entity.getOnPos(), SoundEvents.FIRECHARGE_USE, SoundSource.AMBIENT, 1, 1);
            }

            if (!player.isCreative()) {
                stack.hurtAndBreak(hitEntities.size(), player, (p_220045_0_) -> p_220045_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND));
            }
        }

        level.playSound(null, player.blockPosition(), SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.AMBIENT, 10f, 1f);
        level.playSound(null, player.blockPosition(), ModSoundRegistry.SWIFTSLICE.get(), SoundSource.AMBIENT, 10f, 1f);
        return stack;
    }
}