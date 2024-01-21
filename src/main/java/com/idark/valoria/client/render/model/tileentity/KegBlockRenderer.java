package com.idark.valoria.client.render.model.tileentity;

import com.idark.valoria.Valoria;
import com.idark.valoria.block.types.KegBlock;
import com.idark.valoria.client.event.ClientTickHandler;
import com.idark.valoria.tileentity.KegBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.HashSet;
import java.util.Set;

public class KegBlockRenderer implements BlockEntityRenderer<KegBlockEntity> {

    public static final ModelResourceLocation KEG_BARREL = new ModelResourceLocation(new ResourceLocation(Valoria.MOD_ID, "keg_barrel"), "");

    private static final Set<BlockPos> brewingPositions = new HashSet<>();

    public static void setBrewingForBlock(BlockPos pos, boolean isBrewing) {
        if (isBrewing) {
            brewingPositions.add(pos);
        } else {
            brewingPositions.remove(pos);
        }
    }

    public KegBlockRenderer() {}

    @Override
    public void render(KegBlockEntity keg, float partialTicks, PoseStack ms, MultiBufferSource buffers, int light, int overlay) {
        BlockPos kegPos = keg.getBlockPos();
        if (brewingPositions.contains(kegPos) && KegBlock.isBrewing(keg.getBlockState())) {
            ms.pushPose();
            double sinValue = Math.sin((ClientTickHandler.ticksInGame + Minecraft.getInstance().getPartialTick()) * 0.1);
            float scale = 1.15f + (float) (sinValue / 32);

            ms.translate(0.5f, 0.5f, 0.5f);
            ms.scale(scale, scale, scale);
            ms.mulPose(Axis.YP.rotationDegrees(keg.getBlockRotate()));

            renderCustomModel(KEG_BARREL, ItemDisplayContext.FIXED, false, ms, buffers, light, overlay);
            ms.popPose();
        }
    }

    public static void renderCustomModel(ModelResourceLocation model, ItemDisplayContext displayContext, boolean leftHand, PoseStack poseStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        BakedModel bakedmodel = Minecraft.getInstance().getItemRenderer().getItemModelShaper().getModelManager().getModel(model);
        Minecraft.getInstance().getItemRenderer().render(new ItemStack(Items.DIRT), displayContext, leftHand, poseStack, buffer, combinedLight, combinedOverlay, bakedmodel);
    }
}