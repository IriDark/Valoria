package com.idark.valoria.registries.item.armor.item;

import mod.azure.azurelib.rewrite.animation.controller.*;
import mod.azure.azurelib.rewrite.animation.impl.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;
import org.jetbrains.annotations.*;

public class ArmorAnimator extends AzItemAnimator{
    private final ResourceLocation ANIMATIONS;
    public ArmorAnimator(ResourceLocation loc) {
        ANIMATIONS = loc;
    }

    @Override
    public void registerControllers(AzAnimationControllerContainer<ItemStack> animationControllerContainer) {
        animationControllerContainer.add(
            AzAnimationController.builder(this, "base_controller")
                .build()
        );
    }

    @Override
    public @NotNull ResourceLocation getAnimationLocation(ItemStack animatable) {
        return ANIMATIONS;
    }
}