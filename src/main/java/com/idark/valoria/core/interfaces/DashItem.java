package com.idark.valoria.core.interfaces;

import com.idark.valoria.registries.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.phys.*;

public interface DashItem{
    default double getEnchantmentBonus(ItemStack stack) {
        return stack.getEnchantmentLevel(EnchantmentsRegistry.DASH.get()) * 0.1;
    }

    default void performDash(Player player, ItemStack stack) {
        Vec3 dir = (player.getViewVector(0.0f).scale(getEnchantmentBonus(stack)));
        player.hurtMarked = true;
        player.push(dir.x, dir.y * 0.25, dir.z);
    }

    default void performDash(Player player, double dashDistance) {
        Vec3 dir = (player.getViewVector(0.0f).scale(dashDistance));
        player.hurtMarked = true;
        player.push(dir.x, dir.y * 0.25, dir.z);
    }

    default void performDash(Player player, ItemStack stack, double dashDistance) {
        Vec3 dir = (player.getViewVector(0.0f).scale(dashDistance + getEnchantmentBonus(stack)));
        player.hurtMarked = true;
        player.push(dir.x, dir.y * 0.25, dir.z);
    }
}
