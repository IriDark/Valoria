package com.idark.valoria.registries.item.types;

import com.idark.valoria.client.render.model.item.*;
import com.idark.valoria.client.render.model.item.animation.*;
import com.idark.valoria.registries.item.interfaces.*;
import com.idark.valoria.util.*;
import net.minecraft.client.*;
import net.minecraft.resources.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraftforge.api.distmarker.*;
import org.jetbrains.annotations.*;

/**
 * Used to debug some particle packets and other things
 */
public class DebugItem extends Item implements ICustomAnimationItem, ISpinAttackItem{
    public static SpinAttackAnimation animation = new SpinAttackAnimation();

    public DebugItem(Properties pProperties){
        super(pProperties);
    }

    public int getUseDuration(@NotNull ItemStack stack){
        return 72000;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public ItemAnims getAnimation(ItemStack stack){
        return animation;
    }

    public void onUseTick(Level pLevel, LivingEntity pLivingEntity, ItemStack pStack, int pRemainingUseDuration){
        ValoriaUtils.spinAttack(pLevel, (Player)pLivingEntity, 0.5f);
    }

    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn){
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        playerIn.startUsingItem(handIn);
        Minecraft.getInstance().gameRenderer.shutdownEffect();
        Minecraft.getInstance().gameRenderer.loadEffect(new ResourceLocation("shaders/post/spider.json"));

        return InteractionResultHolder.consume(itemstack);
    }
}