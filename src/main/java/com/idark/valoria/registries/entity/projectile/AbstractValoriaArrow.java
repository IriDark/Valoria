package com.idark.valoria.registries.entity.projectile;

import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;

public class AbstractValoriaArrow extends AbstractArrow {
    public ItemStack arrowItem = ItemStack.EMPTY;
    public AbstractValoriaArrow(EntityType<? extends AbstractArrow> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public AbstractValoriaArrow(EntityType<? extends AbstractArrow> pEntityType, Level worldIn, LivingEntity thrower, ItemStack thrownStackIn, int baseDamage) {
        super(pEntityType, thrower, worldIn);
        arrowItem = new ItemStack(thrownStackIn.getItem());
        this.baseDamage = baseDamage;
    }

    public void tick() {
        super.tick();
        if (this.level().isClientSide) {
            this.spawnParticlesTrail();
        }
    }

    public void spawnParticlesTrail() {
    }

    @Override
    public ItemStack getPickupItem() {
        return arrowItem;
    }
}