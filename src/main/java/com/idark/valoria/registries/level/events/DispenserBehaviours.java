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
        DispenserBlock.registerBehavior(item, new DefaultDispenseItemBehavior() {
            @Override
            public ItemStack execute(BlockSource source, ItemStack stack){
                Level level = source.level();
                Position position = DispenserBlock.getDispensePosition(source);
                Direction direction = source.state().getValue(DispenserBlock.FACING);
                Projectile projectile = getProjectile(level, position, stack);
                projectile.shoot(direction.getStepX(), (float)direction.getStepY() + 0.1F, direction.getStepZ(), 1.1F, 6.0F);
                level.addFreshEntity(projectile);
                stack.shrink(1);
                return stack;
            }

            @Override
            protected void playSound(BlockSource source){
                source.level().levelEvent(1002, source.pos(), 0);
            }

            protected Projectile getProjectile(Level level, Position position, ItemStack stack) {
                if(stack.getItem() instanceof DispensedArrow arrow){
                    AbstractArrow projectile = arrow.createArrow(level, stack);
                    projectile.setPos(new Vec3(position.x(), position.y(), position.z()));
                    projectile.pickup = AbstractArrow.Pickup.ALLOWED;
                    return projectile;
                }

                Arrow fallback = new Arrow(level, position.x(), position.y(), position.z(), stack.copyWithCount(1), null);
                fallback.pickup = AbstractArrow.Pickup.ALLOWED;
                return fallback;
            }
        });
    }
}
