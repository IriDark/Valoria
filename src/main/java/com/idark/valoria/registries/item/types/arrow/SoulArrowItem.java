package com.idark.valoria.registries.item.types.arrow;

import com.idark.valoria.registries.entity.projectile.SoulArrow;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class SoulArrowItem extends ArrowItem {
    public SoulArrowItem(Item.Properties pProperties) {
        super(pProperties);
    }

    public AbstractArrow createArrow(Level pLevel, ItemStack pStack, LivingEntity pShooter) {
        return new SoulArrow(pLevel, pShooter, pStack);
    }
}