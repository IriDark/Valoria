package com.idark.valoria.registries.item.types;

import com.idark.valoria.core.config.ClientConfig;
import com.idark.valoria.registries.ItemsRegistry;
import com.idark.valoria.registries.SoundsRegistry;
import com.idark.valoria.util.ValoriaUtils;
import net.minecraft.client.Minecraft;
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
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import org.joml.Vector3d;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//TODO:
// Fix to the ability (Works weird on server)
// Work on GFX side of the ability
// Something like on-screen particles would be cool
public class PhantomItem extends SwordItem implements IRadiusItem {
    Random rand = new Random();
    public float pRadius = 3;
    public PhantomItem(Tier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn) {
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
    }

    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        if(!playerIn.isShiftKeyDown()) {
            playerIn.startUsingItem(handIn);
            return InteractionResultHolder.consume(itemstack);
        }

        return InteractionResultHolder.pass(itemstack);
    }

    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    /**
     * Some sounds taken from the CalamityMod (Terraria) in a <a href="https://calamitymod.wiki.gg/wiki/Category:Sound_effects">Calamity Mod Wiki.gg</a>
     */
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entityLiving, int timeLeft) {
        Player player = (Player) entityLiving;
        player.awardStat(Stats.ITEM_USED.get(this));
        player.getCooldowns().addCooldown(this, 450);
        float damage = (float) (player.getAttributeValue(Attributes.ATTACK_DAMAGE)) + EnchantmentHelper.getSweepingDamageRatio(player);

        Vector3d pos = new Vector3d(player.getX(), player.getY() + player.getEyeHeight(), player.getZ());
        List<LivingEntity> hitEntities = new ArrayList<>();
        ValoriaUtils.radiusHit(level, stack, player, ParticleTypes.SOUL_FIRE_FLAME, hitEntities, pos, 0, player.getRotationVector().y, pRadius);
        if (level.isClientSide()) {
            for (int a = 0; a < 12; a++) {
                level.addParticle(ParticleTypes.SOUL_FIRE_FLAME, player.getX() + ((rand.nextDouble() - 0.7D) * 1), player.getY() + ((rand.nextDouble() - 1D) * 1), player.getZ() + ((rand.nextDouble() - 0.5D) * 1), 0.05d * ((rand.nextDouble() - 0.5D) * 1), 0.05d * ((rand.nextDouble() - 0.5D) * 1), 0.05d * ((rand.nextDouble() - 0.5D) * 1));
                level.addParticle(ParticleTypes.SOUL_FIRE_FLAME, player.getX() + ((rand.nextDouble() - 0.5D) * 2), player.getY() + ((rand.nextDouble() - 0.5D) * 2), player.getZ() + ((rand.nextDouble() - 0.5D) * 2), 0.05d * ((rand.nextDouble() - 0.5D) * 2), 0.05d * ((rand.nextDouble() - 0.5D) * 2), 0.05d * ((rand.nextDouble() - 0.5D) * 2));
            }
        }

        level.playSound(null, player.blockPosition(), SoundsRegistry.PHANTASM_ABILITY.get(), SoundSource.AMBIENT, 1.0F, 1.0F);
        if (ClientConfig.PHANTOM_ACTIVATION.get()) {
            Minecraft.getInstance().gameRenderer.displayItemActivation(ItemsRegistry.ETERNITY.get().getDefaultInstance());
        }

        if (!player.isCreative()) {
            stack.hurtAndBreak(35, player, (p_220045_0_) -> p_220045_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        }

        for (LivingEntity entityInRadius : hitEntities) {
            if (entityInRadius instanceof Player && ((Player) entityInRadius).isCreative()) {
                continue;
            }

            entityInRadius.hurt(level.damageSources().playerAttack(player), (damage + EnchantmentHelper.getDamageBonus(stack, entityInRadius.getMobType())) * 1.35f);
            entityInRadius.moveTo(player.getX(), player.getY(), player.getZ(), player.getYRot(), player.getXRot());
            entityInRadius.knockback(1f, entityInRadius.getX() + 2f, entityInRadius.getZ() + 2f);
            if (EnchantmentHelper.getTagEnchantmentLevel(Enchantments.FIRE_ASPECT, stack) > 0) {
                int e = EnchantmentHelper.getFireAspect(player);
                entityInRadius.setSecondsOnFire(e * 4);
            }
        }
    }
}