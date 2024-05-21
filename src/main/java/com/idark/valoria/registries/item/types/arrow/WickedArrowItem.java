package com.idark.valoria.registries.item.types.arrow;

import com.idark.valoria.registries.entity.projectile.WickedArrow;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class WickedArrowItem extends ArrowItem{
    public WickedArrowItem(Item.Properties pProperties){
        super(pProperties);
    }

    public AbstractArrow createArrow(Level pLevel, ItemStack pStack, LivingEntity pShooter){
        return new WickedArrow(pLevel, pShooter, pStack);
    }
}