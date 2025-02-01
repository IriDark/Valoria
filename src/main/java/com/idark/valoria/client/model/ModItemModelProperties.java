package com.idark.valoria.client.model;

import com.idark.valoria.registries.item.types.ranged.bows.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.client.renderer.item.*;
import net.minecraft.resources.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import pro.komaru.tridot.client.model.item.*;

public class ModItemModelProperties extends BowItemOverrides{

    @Override
    public float getPull(ItemStack stack, ClientLevel level, LivingEntity entity, int seed){
        if(entity == null){
            return 0.0F;
        }else{
            float time = stack.getItem() instanceof ConfigurableBowItem bow ? bow.time : 20.0F;
            return entity.getUseItem() != stack ? 0.0F : (float)(stack.getUseDuration() - entity.getUseItemRemainingTicks()) / time;
        }
    }

    public static void makeShield(Item item) {
        ItemProperties.register(item, new ResourceLocation("blocking"), (p_174575_, p_174576_, p_174577_, p_174578_) -> p_174577_ != null && p_174577_.isUsingItem() && p_174577_.getUseItem() == p_174575_ ? 1.0F : 0.0F);
    }

    public static void makeSize(Item item){
        ItemProperties.register(item, new ResourceLocation("size"), (sizedStack, clientWorld, livingEntity, player) -> sizedStack.getCount());
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