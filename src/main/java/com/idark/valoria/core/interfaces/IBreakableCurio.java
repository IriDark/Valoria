package com.idark.valoria.core.interfaces;

import net.minecraft.core.particles.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;

public interface IBreakableCurio{
    default void accessoryHurt(LivingEntity target, ItemStack stack){
        stack.hurtAndBreak(1, target, (entity) -> {
            entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(),
            SoundEvents.ITEM_BREAK, entity.getSoundSource(), 0.8f, 0.8f + entity.level().random.nextFloat() * 0.4f);
            if(entity.level() instanceof ServerLevel serverLevel){
                serverLevel.sendParticles(new ItemParticleOption(ParticleTypes.ITEM, stack), entity.getX(), entity.getY() + 1.0, entity.getZ(), 10, 0.2, 0.2, 0.2, 0.05);
            }
        });
    }
}