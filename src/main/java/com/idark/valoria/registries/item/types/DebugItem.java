package com.idark.valoria.registries.item.types;

import com.idark.valoria.registries.*;
import com.idark.valoria.registries.entity.projectile.*;
import net.minecraft.server.level.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.gameevent.*;
import net.minecraft.world.phys.*;
import org.jetbrains.annotations.*;

/**
 * Used to debug some particle packets and other things
 */
public class DebugItem extends Item{

    public DebugItem(Properties pProperties){
        super(pProperties);
    }

    public int getUseDuration(@NotNull ItemStack stack){
        return 72000;
    }

    private void spawn(ServerLevel level, Player entity){
        LaserEntity laser = new LaserEntity(level, entity);
        laser.setDamage(6);
        Vec3 vector3d = entity.getViewVector(1.0F);
        entity.gameEvent(GameEvent.ITEM_INTERACT_START);
        entity.playSound(SoundsRegistry.MAGIC_SHOOT.get());
        laser.shoot(vector3d.x(), vector3d.y(), vector3d.z(), 1F, 3);
        level.addFreshEntity(laser);
    }

    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn){
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        playerIn.startUsingItem(handIn);
        if(worldIn instanceof ServerLevel level) {
            spawn(level, playerIn);
        }
        return InteractionResultHolder.consume(itemstack);
    }
}