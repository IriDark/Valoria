package com.idark.valoria.registries.item.types;

import com.idark.valoria.registries.SoundsRegistry;
import com.idark.valoria.registries.entity.projectile.ThrownSpearEntity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

public class ExplosiveSpearItem extends SpearItem implements Vanishable {
    private final Level.ExplosionInteraction interaction;
    private final float explosive_radius;

    /**
     * @param pEffects Effects applied on attack
     * @param pRadius Explosive radius
     * @param interaction Explosive interaction
     */
    public ExplosiveSpearItem(Tier tier, int attackDamageIn, float attackSpeedIn, float projectileDamageIn, float pRadius, Level.ExplosionInteraction interaction, Item.Properties builderIn, MobEffectInstance... pEffects) {
        super(tier, attackDamageIn, attackSpeedIn, projectileDamageIn, builderIn, pEffects);
        this.interaction = interaction;
        explosive_radius = pRadius;
    }

    /**
     * @param pChance Chance to apply effects
     * @param pRadius Explosive radius
     * @param interaction Explosive interaction
     * @param pEffects Effects applied on attack
     */
    public ExplosiveSpearItem(Tier tier, int attackDamageIn, float attackSpeedIn, float projectileDamageIn, float pChance, float pRadius, Level.ExplosionInteraction interaction, Item.Properties builderIn, MobEffectInstance... pEffects) {
        super(tier, attackDamageIn, attackSpeedIn, projectileDamageIn, pChance, builderIn, pEffects);
        this.interaction = interaction;
        explosive_radius = pRadius;
    }

    public void releaseUsing(ItemStack stack, Level worldIn, LivingEntity entityLiving, int timeLeft) {
        if (entityLiving instanceof Player playerEntity) {
            int i = this.getUseDuration(stack) - timeLeft;
            if (i >= 6) {
                if (!worldIn.isClientSide) {
                    stack.hurtAndBreak(1, playerEntity, (player) -> player.broadcastBreakEvent(entityLiving.getUsedItemHand()));
                    ThrownSpearEntity spear = new ThrownSpearEntity(worldIn, playerEntity, stack, 2, 4);
                    spear.setItem(stack);
                    spear.shootFromRotation(playerEntity, playerEntity.getXRot(), playerEntity.getYRot(), 0.0F, 2.5F + (float) 0 * 0.5F, 1.0F);
                    spear.setExplode(interaction, explosive_radius);
                    if (playerEntity.getAbilities().instabuild) {
                        spear.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                    }

                    if (EnchantmentHelper.getTagEnchantmentLevel(Enchantments.FIRE_ASPECT, stack) > 0) {
                        spear.setSecondsOnFire(100);
                    }

                    spear.setEffectsFromList(effects);
                    worldIn.addFreshEntity(spear);
                    worldIn.playSound(null, spear, SoundsRegistry.SPEAR_THROW.get(), SoundSource.PLAYERS, 1.0F, 0.9F);
                    if (!playerEntity.getAbilities().instabuild) {
                        playerEntity.getInventory().removeItem(stack);
                    }
                }

                playerEntity.awardStat(Stats.ITEM_USED.get(this));
            }
        }
    }
}