package com.idark.valoria.client.model;

import net.minecraft.client.renderer.item.*;
import net.minecraft.resources.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;

public class ModItemModelProperties{

    public static void makeShield(Item item) {
        ItemProperties.register(item, new ResourceLocation("blocking"), (p_174575_, p_174576_, p_174577_, p_174578_) -> p_174577_ != null && p_174577_.isUsingItem() && p_174577_.getUseItem() == p_174575_ ? 1.0F : 0.0F);
    }

    public static void makeSize(Item item){
        ItemProperties.register(item, new ResourceLocation("size"), (sizedStack, clientWorld, livingEntity, player) -> sizedStack.getCount());
    }

    public static void makeSouls(Item item){
        ItemProperties.register(item, new ResourceLocation("souls"), (stack, clientWorld, livingEntity, player) -> stack.getOrCreateTag().getInt("Souls"));
    }

    public static void makeCooldown(Item item){
        ItemProperties.register(item, new ResourceLocation("itemcooldown"), (stack, clientWorld, livingEntity, player) -> {
            if(livingEntity instanceof Player p){
                return p.getCooldowns().isOnCooldown(item) ? 1.0F : 0.0F;
            }
            return 0.0F;
        });
    }
}