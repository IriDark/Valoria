package com.idark.valoria.client.model;

import com.idark.valoria.registries.item.types.ranged.bows.*;
import mod.maxbogomol.fluffy_fur.client.model.item.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.client.renderer.item.*;
import net.minecraft.resources.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;

public class ModItemModelProperties extends BowItemOverrides{

    @Override
    public float getPull(ItemStack stack, ClientLevel level, LivingEntity entity, int seed){
        if (entity == null) {
            return 0.0F;
        } else {
            float time = stack.getItem() instanceof ConfigurableBowItem bow ? bow.time : 20.0F;
            return entity.getUseItem() != stack ? 0.0F : (float)(stack.getUseDuration() - entity.getUseItemRemainingTicks()) / time;
        }
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