package com.idark.valoria.client.render.tile;

import com.idark.valoria.*;
import com.idark.valoria.registries.block.entity.*;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.blockentity.*;
import net.minecraft.client.resources.model.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;
import pro.komaru.tridot.client.*;

public class ManipulatorBlockEntityRenderer implements BlockEntityRenderer<ManipulatorBlockEntity>{
    public static final ModelResourceLocation SPHERE = new ModelResourceLocation(new ResourceLocation(Valoria.ID, "elemental_sphere"), "");

    public ManipulatorBlockEntityRenderer(){
    }

    public static void renderCustomModel(ModelResourceLocation model, ItemDisplayContext displayContext, boolean leftHand, PoseStack poseStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay){
        BakedModel bakedmodel = Minecraft.getInstance().getItemRenderer().getItemModelShaper().getModelManager().getModel(model);
        Minecraft.getInstance().getItemRenderer().render(new ItemStack(Items.DIRT), displayContext, leftHand, poseStack, buffer, combinedLight, combinedOverlay, bakedmodel);
    }

    @Override
    public void render(ManipulatorBlockEntity manipulatorBlockEntity, float partialTicks, PoseStack ms, MultiBufferSource buffers, int light, int overlay){
        ms.pushPose();
        double sinValue = Math.sin((ClientTick.ticksInGame + Minecraft.getInstance().getPartialTick()) * 0.1);
        float y = 0.6f + (float)(sinValue / 20);
        float rot = ClientTick.ticksInGame * 0.5f;

        ms.translate(0.5f, y, 0.5f);
        ms.scale(1.0f, 1.0f, 1.0f);
        ms.mulPose(Axis.YP.rotationDegrees(rot));

        renderCustomModel(SPHERE, ItemDisplayContext.FIXED, false, ms, buffers, light, overlay);
        ms.popPose();
    }
}