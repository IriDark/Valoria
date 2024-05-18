package com.idark.valoria.registries.item.types;

import com.idark.valoria.client.render.model.item.ItemAnims;
import com.idark.valoria.client.render.model.item.animation.SpinAttackAnimation;
import com.idark.valoria.util.ValoriaUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

/**
 * Used to debug some particle packets and other things
 */
public class DebugItem extends Item implements ICustomAnimationItem, ISpinAttackItem {
    public static SpinAttackAnimation animation = new SpinAttackAnimation();
    public DebugItem(Properties pProperties) {
        super(pProperties);
    }

    public int getUseDuration(@NotNull ItemStack stack) {
        return 72000;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public ItemAnims getAnimation(ItemStack stack) {
        return animation;
    }

    public void onUseTick(Level pLevel, LivingEntity pLivingEntity, ItemStack pStack, int pRemainingUseDuration) {
        ValoriaUtils.spinAttack(pLevel, (Player) pLivingEntity, 0.5f);
    }

    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        playerIn.startUsingItem(handIn);
        Minecraft.getInstance().gameRenderer.shutdownEffect();
        Minecraft.getInstance().gameRenderer.loadEffect(new ResourceLocation("shaders/post/spider.json"));

        return InteractionResultHolder.consume(itemstack);
    }
}