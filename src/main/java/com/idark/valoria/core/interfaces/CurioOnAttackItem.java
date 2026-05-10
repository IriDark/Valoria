package com.idark.valoria.core.interfaces;

import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;

public interface CurioOnAttackItem{
    void onAttack(ItemStack stack, LivingEntity target, DamageSource source, float damage);
}
