package com.idark.valoria.core.interfaces;

import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;

public interface CurioOnHurtItem{
    void onHurt(ItemStack stack, LivingEntity target, DamageSource source, float damage);
}
