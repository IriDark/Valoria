package com.idark.valoria.registries.item.types;

import com.idark.valoria.util.ValoriaUtils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.joml.Vector3d;

import java.util.ArrayList;
import java.util.List;

public class DebugItem extends Item {
    public DebugItem(Properties pProperties) {
        super(pProperties);
    }

    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        List<LivingEntity> hitEntities = new ArrayList<>();
        Vector3d pos = new Vector3d(playerIn.getX(), playerIn.getY() + 0.3f, playerIn.getZ());
        if (worldIn instanceof ServerLevel level) {
            ValoriaUtils.spawnParticlesInRadius(level, itemstack, ParticleTypes.LARGE_SMOKE, pos, 0, playerIn.getRotationVector().y, 1);
            ValoriaUtils.spawnParticlesInRadius(level, itemstack, ParticleTypes.LARGE_SMOKE, pos, 0, playerIn.getRotationVector().y, 4);
            ValoriaUtils.radiusHit(level, itemstack, playerIn, ParticleTypes.FLAME, hitEntities, pos, 0, playerIn.getRotationVector().y, 4);
        }

        return InteractionResultHolder.consume(itemstack);
    }
}