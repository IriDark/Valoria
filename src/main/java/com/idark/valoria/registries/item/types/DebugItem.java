package com.idark.valoria.registries.item.types;

import com.idark.valoria.registries.*;
import com.idark.valoria.registries.entity.projectile.*;
import com.idark.valoria.util.*;
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
        if(!worldIn.isClientSide()) {
            acorn(worldIn, playerIn);
        }

        return InteractionResultHolder.consume(itemstack);
    }

    private static void acorn(Level worldIn, Player playerIn){
        AcornProjectile spell = new AcornProjectile(playerIn, worldIn);
        Vec3 vector3d = playerIn.getViewVector(1.0F);
        spell.shoot(vector3d.x(), vector3d.y(), vector3d.z(), 1F, 3);
        worldIn.addFreshEntity(spell);
    }

    private static void spell(Level worldIn, Player playerIn){
        SpellProjectile spell = new SpellProjectile(worldIn, playerIn, 6);
        Vec3 vector3d = playerIn.getViewVector(1.0F);
        spell.setColor(Pal.nature);
        spell.shoot(vector3d.x(), vector3d.y(), vector3d.z(), 1F, 3);
        worldIn.addFreshEntity(spell);
    }
}