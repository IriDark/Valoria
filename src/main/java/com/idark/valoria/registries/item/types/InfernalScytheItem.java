package com.idark.valoria.registries.item.types;

import com.idark.valoria.registries.*;
import com.idark.valoria.util.*;
import net.minecraft.*;
import net.minecraft.core.particles.*;
import net.minecraft.network.chat.*;
import net.minecraft.sounds.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.level.*;
import org.joml.*;
import pro.komaru.tridot.client.graphics.gui.screenshake.*;
import pro.komaru.tridot.core.math.*;

import java.util.*;

public class InfernalScytheItem extends ScytheItem{
    public ArcRandom arcRandom = new ArcRandom();

    public InfernalScytheItem(Tier tier, int attackDamageIn, float attackSpeedIn, Item.Properties builderIn){
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
    }

    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker){
        pStack.hurtAndBreak(1, pAttacker, (p_43296_) -> p_43296_.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        if(EnchantmentHelper.getFireAspect(pAttacker) > 0){
            pAttacker.level().playSound(null, pTarget.getOnPos(), SoundEvents.FIRECHARGE_USE, SoundSource.AMBIENT, 1, 1);
        }else if(arcRandom.chance(0.07f)){
            pTarget.setSecondsOnFire(4);
            pAttacker.level().playSound(null, pTarget.getOnPos(), SoundEvents.FIRECHARGE_USE, SoundSource.AMBIENT, 1, 1);
        }

        return true;
    }

    public void performAttack(Level level, ItemStack stack, Player player){
        List<LivingEntity> hitEntities = new ArrayList<>();
        Vector3d pos = new Vector3d(player.getX(), player.getY() + player.getEyeHeight(), player.getZ());
        float damage = (float)(player.getAttributeValue(Attributes.ATTACK_DAMAGE)) + EnchantmentHelper.getSweepingDamageRatio(player);
        float radius = (float)player.getAttributeValue(AttributeReg.ATTACK_RADIUS.get());

        ValoriaUtils.radiusHit(level, player, ParticleTypes.FLAME, hitEntities, pos, 0, player.getRotationVector().y, radius);
        applyCooldown(player, hitEntities.isEmpty() ? builder.minCooldownTime : builder.cooldownTime);
        for(LivingEntity entity : hitEntities){
            entity.hurt(level.damageSources().playerAttack(player), (damage + EnchantmentHelper.getDamageBonus(stack, entity.getMobType())) * 1.35f);
            performEffects(entity, player);
            ValoriaUtils.chanceEffect(entity, builder.effects, builder.chance, arcRandom);
            if(!player.isCreative()){
                stack.hurtAndBreak(hitEntities.size(), player, (p_220045_0_) -> p_220045_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND));
            }
        }

        ScreenshakeHandler.addScreenshake(new ScreenshakeInstance(4).setIntensity(0.35f).setEasing(Interp.circleOut));
    }

    public void performEffects(LivingEntity targets, Player player){
        targets.knockback(0.4F, player.getX() - targets.getX(), player.getZ() - targets.getZ());
        if(EnchantmentHelper.getFireAspect(player) > 0){
            int i = EnchantmentHelper.getFireAspect(player);
            targets.setSecondsOnFire(i * 4);
            targets.level().playSound(null, targets.getOnPos(), SoundEvents.FIRECHARGE_USE, SoundSource.AMBIENT, 1, 1);
        }else if(arcRandom.chance(0.07f)){
            targets.setSecondsOnFire(4);
            targets.level().playSound(null, targets.getOnPos(), SoundEvents.FIRECHARGE_USE, SoundSource.AMBIENT, 1, 1);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags){
        super.appendHoverText(stack, world, tooltip, flags);
        tooltip.add(1, Component.translatable("tooltip.valoria.infernal_scythe", Component.literal(String.format("%.1f%%", 0.07f * 100))).withStyle(ChatFormatting.GRAY));
        ValoriaUtils.addEffectsTooltip(builder.effects, tooltip, 1, builder.chance);
    }
}