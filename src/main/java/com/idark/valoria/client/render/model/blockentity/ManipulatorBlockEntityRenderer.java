package com.idark.valoria.client.render.model.blockentity;

import com.idark.valoria.Valoria;
import com.idark.valoria.client.event.ClientTickHandler;
import com.idark.valoria.registries.world.block.entity.ManipulatorBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.Random;

public class ManipulatorBlockEntityRenderer implements BlockEntityRenderer<ManipulatorBlockEntity> {
    public static final ModelResourceLocation SPHERE = new ModelResourceLocation(new ResourceLocation(Valoria.MOD_ID, "elemental_sphere"), "");

    public ManipulatorBlockEntityRenderer() {
    }

    @Override
    public void render(ManipulatorBlockEntity manipulatorBlockEntity, float partialTicks, PoseStack ms, MultiBufferSource buffers, int light, int overlay) {
        Random random = new Random();

        ms.pushPose();
        double sinValue = Math.sin((ClientTickHandler.ticksInGame + Minecraft.getInstance().getPartialTick()) * 0.1);
        float y = 0.6f + (float) (sinValue / 20);
        float rot = random.nextFloat() + (float) (sinValue * 180 + random.nextFloat());

        ms.translate(0.5f, y, 0.5f);
        ms.scale(1.0f, 1.0f, 1.0f);
        ms.mulPose(Axis.YP.rotationDegrees(rot));

        renderCustomModel(SPHERE, ItemDisplayContext.FIXED, false, ms, buffers, light, overlay);
        ms.popPose();
    }

    public static void renderCustomModel(ModelResourceLocation model, ItemDisplayContext displayContext, boolean leftHand, PoseStack poseStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        BakedModel bakedmodel = Minecraft.getInstance().getItemRenderer().getItemModelShaper().getModelManager().getModel(model);
        Minecraft.getInstance().getItemRenderer().render(new ItemStack(Items.DIRT), displayContext, leftHand, poseStack, buffer, combinedLight, combinedOverlay, bakedmodel);
    }
}