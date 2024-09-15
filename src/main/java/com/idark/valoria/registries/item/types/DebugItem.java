package com.idark.valoria.registries.item.types;

import com.idark.valoria.core.network.PacketHandler;
import com.idark.valoria.core.network.packets.CircleShapedParticlePacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

/**
 * Used to debug some particle packets and other things
 */
public class DebugItem extends Item {

    public DebugItem(Properties pProperties) {
        super(pProperties);
    }

    public int getUseDuration(@NotNull ItemStack stack) {
        return 72000;
    }

    public void onUseTick(Level pLevel, LivingEntity pLivingEntity, ItemStack pStack, int pRemainingUseDuration) {
        if (pLevel instanceof ServerLevel serverLevel)
            PacketHandler.sendToTracking(serverLevel, pLivingEntity.getOnPos(), new CircleShapedParticlePacket(pLivingEntity.getX(), pLivingEntity.getY(), pLivingEntity.getZ(), pLivingEntity.getRotationVector().y, 255, 123, 66));
    }

    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        playerIn.startUsingItem(handIn);
        return InteractionResultHolder.consume(itemstack);
    }
}