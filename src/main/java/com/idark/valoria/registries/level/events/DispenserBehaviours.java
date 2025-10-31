package com.idark.valoria.registries.level.events;

import com.idark.valoria.registries.*;
import com.idark.valoria.registries.item.types.ranged.bows.*;
import net.minecraft.core.*;
import net.minecraft.core.dispenser.*;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.phys.*;

public class DispenserBehaviours{

    public static void bootStrap() {
        arrowBehaviour(ItemsRegistry.natureArrow.get());
        arrowBehaviour(ItemsRegistry.aquariusArrow.get());
        arrowBehaviour(ItemsRegistry.infernalArrow.get());
        arrowBehaviour(ItemsRegistry.wickedArrow.get());
        arrowBehaviour(ItemsRegistry.soulArrow.get());
        arrowBehaviour(ItemsRegistry.pyratiteArrow.get());
    }

    public static void arrowBehaviour(Item item) {
        DispenserBlock.registerBehavior(item, new AbstractProjectileDispenseBehavior() {
            protected Projectile getProjectile(Level p_123407_, Position p_123408_, ItemStack p_123409_) {
                if(p_123409_.getItem() instanceof DispensedArrow arrow){
                    AbstractArrow projectile = arrow.createArrow(p_123407_, p_123409_);
                    projectile.setPos(new Vec3(p_123408_.x(), p_123408_.y(), p_123408_.z()));
                    projectile.pickup = AbstractArrow.Pickup.ALLOWED;
                    return projectile;
                }

                Arrow arrow = new Arrow(p_123407_, p_123408_.x(), p_123408_.y(), p_123408_.z());
                arrow.pickup = AbstractArrow.Pickup.ALLOWED;
                return arrow;
            }
        });
    }
}