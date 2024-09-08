package com.idark.valoria.registries.item.types;

import com.idark.valoria.util.*;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.*;

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

    public ParticleOptions getAttackParticle() {
        return ParticleTypes.FLAME;
    }

    public void performEffects(LivingEntity targets, Player player) {
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
}