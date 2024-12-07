package com.idark.valoria.client.render.tile;

import com.idark.valoria.registries.*;
import com.idark.valoria.registries.block.entity.*;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.blockentity.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.core.*;
import net.minecraft.util.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraftforge.api.distmarker.*;

@OnlyIn(Dist.CLIENT)
public class CrushableBlockRenderer implements BlockEntityRenderer<CrushableBlockEntity> {

    public CrushableBlockRenderer() {
    }

    public void render(CrushableBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        if (pBlockEntity.getLevel() != null) {
            int i = pBlockEntity.getBlockState().getValue(BlockStateProperties.DUSTED);
            boolean isIce = pBlockEntity.getBlockState().is(BlockRegistry.suspiciousIce.get());
            if (i > 0 || isIce) {
                Direction direction = pBlockEntity.getHitDirection();
                ItemStack itemstack = pBlockEntity.getItem();
                if (direction != null) {
                    if (!itemstack.isEmpty()) {
                        pPoseStack.pushPose();
                        pPoseStack.translate(0.0F, 0.5F, 0.0F);
                        float[] afloat = this.translations(direction, i);
                        pPoseStack.translate(afloat[0], afloat[1], afloat[2]);
                        pPoseStack.mulPose(Axis.YP.rotationDegrees(75.0F));
                        boolean flag = direction == Direction.EAST || direction == Direction.WEST;
                        pPoseStack.mulPose(Axis.YP.rotationDegrees((float) ((flag ? 90 : 0) + 11)));
                        pPoseStack.scale(0.5F, 0.5F, 0.5F);
                        int j = LevelRenderer.getLightColor(pBlockEntity.getLevel(), pBlockEntity.getBlockState(), pBlockEntity.getBlockPos().relative(direction));
                        Minecraft.getInstance().getItemRenderer().renderStatic(itemstack, ItemDisplayContext.FIXED, j, OverlayTexture.NO_OVERLAY, pPoseStack, pBuffer, pBlockEntity.getLevel(), 0);
                        pPoseStack.popPose();
                    }
                } else {
                    RandomSource random = pBlockEntity.getLevel().random;
                    random.setSeed(pBlockEntity.getBlockPos().asLong());
                    if (!itemstack.isEmpty()) {
                        pPoseStack.pushPose();

                        double xOffset = random.nextDouble() * 0.22 - 0.1;
                        double yOffset = random.nextDouble() * 0.22 - 0.1;
                        double zOffset = random.nextDouble() * 0.22 - 0.1;

                        pPoseStack.translate(0.5 + xOffset, 0.6 + yOffset, 0.5 + zOffset);

                        double yawDegrees = (random.nextDouble() * 0.7 + 0.1) * 180.0;
                        if (yawDegrees < 0) yawDegrees += 360.0;
                        pPoseStack.mulPose(Axis.YP.rotationDegrees((float) yawDegrees));

                        double pitchDegrees = (random.nextDouble() * 0.7 + 0.1) * 180.0 - 90.0;
                        if (pitchDegrees < 0) pitchDegrees += 360.0;
                        pPoseStack.mulPose(Axis.XP.rotationDegrees((float) pitchDegrees));

                        pPoseStack.scale(0.8f, 0.8f, 0.8f);
                        Minecraft.getInstance().getItemRenderer().renderStatic(itemstack, ItemDisplayContext.FIXED, pPackedLight, pPackedOverlay, pPoseStack, pBuffer, Minecraft.getInstance().level, 0);

                        pPoseStack.popPose();
                    }
                }
            }
        }
    }

    private float[] translations(Direction pDirection, int pDustedLevel) {
        float[] afloat = new float[]{0.5F, 0.0F, 0.5F};
        float f = (float) pDustedLevel / 10.0F * 0.85F;
        switch (pDirection) {
            case EAST:
                afloat[0] = 0.73F + f;
                break;
            case WEST:
                afloat[0] = 0.25F - f;
                break;
            case UP:
                afloat[1] = 0.25F + f;
                break;
            case DOWN:
                afloat[1] = -0.23F - f;
                break;
            case NORTH:
                afloat[2] = 0.25F - f;
                break;
            case SOUTH:
                afloat[2] = 0.73F + f;
        }

        return afloat;
    }
}