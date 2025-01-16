package com.idark.valoria.client.model;

import com.idark.valoria.registries.item.types.ranged.bows.*;
import net.minecraft.client.renderer.item.*;
import net.minecraft.resources.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;

public class ModItemModelProperties {

    public static void makeBow(Item item) {
        animateBow(item);
    }

    public static void makeBow(Item... item) {
        for(Item bow : item) animateBow(bow);
    }

    private static void animateBow(Item item) {
        ItemProperties.register(item, new ResourceLocation("pull"), (stack, clientLvl, living, p_174638_) -> {
            if (living == null) {
                return 0.0F;
            } else {
                float time = item instanceof ConfigurableBowItem bow ? bow.time : 20.0F;
                return living.getUseItem() != stack ? 0.0F : (float) (stack.getUseDuration() - living.getUseItemRemainingTicks()) / time;
            }
        });

        ItemProperties.register(item, new ResourceLocation("pulling"), (p_174630_, p_174631_, p_174632_, p_174633_) -> p_174632_ != null && p_174632_.isUsingItem() && p_174632_.getUseItem() == p_174630_ ? 1.0F : 0.0F);
    }

    public static void makeSize(Item item) {
        ItemProperties.register(item, new ResourceLocation("size"), (sizedStack, clientWorld, livingEntity, player) -> sizedStack.getCount());
    }

    public static void makeCooldown(Item item) {
        ItemProperties.register(item, new ResourceLocation("itemcooldown"), (stack, clientWorld, livingEntity, player) -> {
            if (livingEntity instanceof Player p) {
                return p.getCooldowns().isOnCooldown(item) ? 1.0F : 0.0F;
            }
            return 0.0F;
        });
    }
}