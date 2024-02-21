package com.idark.valoria.registries.world.item.types;

import com.idark.valoria.util.ModUtils;
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
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import org.joml.Vector3d;

import java.util.ArrayList;
import java.util.List;

public class CoralReefItem extends SwordItem {

    public CoralReefItem(Tier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn) {
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

    /**
     *Some sounds taken from the CalamityMod (Terraria) in a <a href="https://calamitymod.wiki.gg/wiki/Category:Sound_effects">Calamity Mod Wiki.gg</a>
     */
    public void releaseUsing(ItemStack stack, Level worldIn, LivingEntity entityLiving, int timeLeft) {
        Player player = (Player)entityLiving;
        player.getCooldowns().addCooldown(this, 300);
        player.awardStat(Stats.ITEM_USED.get(this));
        Vector3d pos = new Vector3d(player.getX(), player.getY() + 0.3f, player.getZ());
        List<LivingEntity> hitEntities = new ArrayList<LivingEntity>();

        for (int i = 0; i < 360; i += 10) {
            float yawDouble = 0;
            if (i <= 180) {
                yawDouble = ((float) i) / 180F;
            } else {
                yawDouble = 1F - ((((float) i) - 180F) / 180F);
            }

            ModUtils.spawnParticlesInRadius(worldIn, stack, ParticleTypes.ENCHANTED_HIT, new Vector3d(player.getX(), player.getY() +0.6f, player.getZ()), 0, player.getRotationVector().y + i, 2);
            ModUtils.radiusHit(worldIn, stack, player, ParticleTypes.ENCHANTED_HIT, hitEntities, pos, 0, player.getRotationVector().y + i, 4);
        }

        float damage = (float) (player.getAttribute(Attributes.ATTACK_DAMAGE).getValue()) + EnchantmentHelper.getSweepingDamageRatio(player);
        for (LivingEntity damagedEntity : hitEntities) {
            damagedEntity.hurt(worldIn.damageSources().generic(), damage);
            damagedEntity.knockback(0.4F, player.getX() - entityLiving.getX(), player.getZ() - entityLiving.getZ());
        }

        if (!player.isCreative()) {
            stack.hurtAndBreak(hitEntities.size(), player, (p_220045_0_) -> {p_220045_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND);});
        }

        worldIn.playSound(player, player.blockPosition(), SoundType.CORAL_BLOCK.getPlaceSound(), SoundSource.AMBIENT, 10f, 1f);
    }
}