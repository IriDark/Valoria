package com.idark.valoria.client.render.tile;

import com.idark.valoria.registries.block.entity.*;
import com.idark.valoria.registries.block.types.*;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.blockentity.*;
import net.minecraft.core.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.state.*;

public class HeavyAnvilBlockEntityRenderer implements BlockEntityRenderer<HeavyAnvilBlockEntity> {
    
    public HeavyAnvilBlockEntityRenderer() {
    }

    @Override
    public void render(HeavyAnvilBlockEntity anvil, float partialTicks, PoseStack ms, MultiBufferSource buffers, int light, int overlay) {
        ItemStack stack = anvil.itemHandler.getStackInSlot(0);
        if (stack.isEmpty()) return;

        BlockState state = anvil.getBlockState();
        if (!state.hasProperty(HeavyAnvil.FACING)) return;

        Direction facing = state.getValue(HeavyAnvil.FACING);
        
        ms.pushPose();
        double x = 0.5 - facing.getStepX() * 0.5;
        double z = 0.5 - facing.getStepZ() * 0.5;

        ms.translate(x, 0.95F, z);
        float rot = -facing.toYRot();
        ms.mulPose(Axis.YP.rotationDegrees(rot + 90));
        ms.mulPose(Axis.XP.rotationDegrees(90.0F));
        
        ms.scale(0.5F, 0.5F, 0.5F);

        int lightAbove = LevelRenderer.getLightColor(anvil.getLevel(), anvil.getBlockPos().above());
        Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemDisplayContext.FIXED, lightAbove, overlay, ms, buffers, anvil.getLevel(), 0);
        
        ms.popPose();
    }
}
