package com.idark.valoria.registries.item.types;

import com.idark.valoria.client.render.model.item.ItemAnims;
import com.idark.valoria.client.render.model.item.RadiusAttackAnim;
import com.idark.valoria.registries.sounds.ModSoundRegistry;
import com.idark.valoria.util.ValoriaUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
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
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;
import org.joml.Vector3d;

import java.util.ArrayList;
import java.util.List;

public class ScytheItem extends SwordItem implements ICustomAnimationItem, Vanishable {
    public static RadiusAttackAnim animation = new RadiusAttackAnim();

    public ScytheItem(Tier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn) {
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

    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.CUSTOM;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public ItemAnims getAnimation(ItemStack stack) {
        return animation;
    }

    public int getUseDuration(ItemStack stack) {
        return 5;
    }

    /**
     * Some sounds taken from the CalamityMod (Terraria) in a <a href="https://calamitymod.wiki.gg/wiki/Category:Sound_effects">Calamity Mod Wiki.gg</a>
     */
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entityLiving) {
        Player player = (Player) entityLiving;
        player.awardStat(Stats.ITEM_USED.get(this));
        for (Item item : ForgeRegistries.ITEMS) {
            if (item instanceof ScytheItem) {
                player.getCooldowns().addCooldown(item, 100);
            }
        }

        Vector3d pos = new Vector3d(player.getX(), player.getY() + player.getEyeHeight(), player.getZ());
        List<LivingEntity> hitEntities = new ArrayList<>();
        for (int i = 0; i < 360; i += 10) {
            ValoriaUtils.radiusHit(level, stack, player, ParticleTypes.POOF, hitEntities, pos, 0, player.getRotationVector().y + i, 3);
        }

        float damage = (float) (player.getAttributeValue(Attributes.ATTACK_DAMAGE)) + EnchantmentHelper.getSweepingDamageRatio(player);
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

        level.playSound(null, player.blockPosition(), ModSoundRegistry.SWIFTSLICE.get(), SoundSource.AMBIENT, 10f, 1f);
        return stack;
    }

    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags) {
        super.appendHoverText(stack, world, tooltip, flags);
        tooltip.add(Component.translatable("tooltip.valoria.scythe").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("tooltip.valoria.rmb").withStyle(ChatFormatting.GREEN));
    }
}