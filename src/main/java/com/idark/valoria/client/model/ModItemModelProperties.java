package com.idark.valoria.client.model;

import com.idark.valoria.registries.item.types.ranged.bows.*;
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

    public static void makeCrossbow(Item item){
        ItemProperties.register(item, new ResourceLocation("pull"), (p_174610_, p_174611_, p_174612_, p_174613_) -> {
            if (p_174612_ != null && item instanceof ConfigurableCrossbow crossbow){
                return ConfigurableCrossbow.isCharged(p_174610_) ? 0.0F : (float)(p_174610_.getUseDuration() - p_174612_.getUseItemRemainingTicks()) / (float)crossbow.getCustomChargeDuration(p_174610_);
            }

            return 0;
        });
        ItemProperties.register(item, new ResourceLocation("pulling"), (p_174605_, p_174606_, p_174607_, p_174608_) -> {
            return p_174607_ != null && p_174607_.isUsingItem() && p_174607_.getUseItem() == p_174605_ && !ConfigurableCrossbow.isCharged(p_174605_) ? 1.0F : 0.0F;
        });
        ItemProperties.register(item, new ResourceLocation("charged"), (p_275891_, p_275892_, p_275893_, p_275894_) -> {
            return ConfigurableCrossbow.isCharged(p_275891_) ? 1.0F : 0.0F;
        });
        ItemProperties.register(item, new ResourceLocation("charged"), (p_275891_, p_275892_, p_275893_, p_275894_) -> {
            return ConfigurableCrossbow.isCharged(p_275891_) ? 1.0F : 0.0F;
        });
        ItemProperties.register(item, new ResourceLocation("firework"), (p_275887_, p_275888_, p_275889_, p_275890_) -> {
            return ConfigurableCrossbow.isCharged(p_275887_) && ConfigurableCrossbow.containsChargedProjectile(p_275887_, Items.FIREWORK_ROCKET) ? 1.0F : 0.0F;
        });
    }
}