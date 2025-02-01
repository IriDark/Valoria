package com.idark.valoria.client.render.tile;

import com.idark.valoria.*;
import com.idark.valoria.registries.block.entity.*;
import com.idark.valoria.registries.block.types.*;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.blockentity.*;
import net.minecraft.client.resources.model.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;
import pro.komaru.tridot.client.event.*;

public class KegBlockEntityRenderer implements BlockEntityRenderer<KegBlockEntity>{

    public static final ModelResourceLocation KEG_BARREL = new ModelResourceLocation(new ResourceLocation(Valoria.ID, "keg_barrel"), "");

    public KegBlockEntityRenderer(){
    }

    public static void renderCustomModel(ModelResourceLocation model, ItemDisplayContext displayContext, boolean leftHand, PoseStack poseStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay){
        BakedModel bakedmodel = Minecraft.getInstance().getItemRenderer().getItemModelShaper().getModelManager().getModel(model);
        Minecraft.getInstance().getItemRenderer().render(new ItemStack(Items.DIRT), displayContext, leftHand, poseStack, buffer, combinedLight, combinedOverlay, bakedmodel);
    }

    @Override
    public void render(KegBlockEntity keg, float partialTicks, PoseStack ms, MultiBufferSource buffers, int light, int overlay){
        if(keg.startCraft && KegBlock.isBrewing(keg.getBlockState())){
            ms.pushPose();
            double sinValue = Math.sin((ClientTickHandler.ticksInGame + Minecraft.getInstance().getPartialTick()) * 0.1);
            float scale = 1.15f + (float)(sinValue / 32);

            ms.translate(0.5f, 0.5f, 0.5f);
            ms.scale(scale, scale, scale);
            ms.mulPose(Axis.YP.rotationDegrees(keg.getBlockRotate()));

            renderCustomModel(KEG_BARREL, ItemDisplayContext.FIXED, false, ms, buffers, light, overlay);
            ms.popPose();
        }
    }
}