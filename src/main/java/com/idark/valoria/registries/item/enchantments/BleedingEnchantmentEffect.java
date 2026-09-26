package com.idark.valoria.registries.item.enchantments;

import com.idark.valoria.registries.*;
import com.mojang.serialization.*;
import net.minecraft.server.level.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.item.enchantment.effects.*;
import net.minecraft.world.phys.*;
import pro.komaru.tridot.util.*;
import pro.komaru.tridot.util.math.*;

public record BleedingEnchantmentEffect() implements EnchantmentEntityEffect{
    public static final MapCodec<BleedingEnchantmentEffect> CODEC = MapCodec.unit(BleedingEnchantmentEffect::new);
    private static final ArcRandom arcRandom = Tmp.rnd;

    @Override
    public void apply(ServerLevel level, int enchantmentLevel, EnchantedItemInUse item, Entity target, Vec3 origin){
        LivingEntity user = item.owner();
        boolean piercing = user != null && EnchantmentsRegistry.getLevel(level, user.getUseItem(), Enchantments.PIERCING) > 0;
        if(arcRandom.chance(0.05f * enchantmentLevel) || piercing){
            if(target instanceof LivingEntity livingentity){
                if(enchantmentLevel > 0){
                    int i = 25 + (user != null ? user.getRandom() : level.getRandom()).nextInt(45 * enchantmentLevel);
                    livingentity.addEffect(new MobEffectInstance(EffectsRegistry.BLEEDING, i, enchantmentLevel - 1, false, false), target);
                }
            }
        }
    }

    @Override
    public MapCodec<BleedingEnchantmentEffect> codec(){
        return CODEC;
    }
}
