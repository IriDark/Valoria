package com.idark.valoria.client.render.item;

import com.idark.valoria.util.*;
import com.mojang.blaze3d.systems.*;
import net.minecraft.client.gui.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.client.*;

public class NihilityDecorator implements IItemDecorator{
    private static final ResourceLocation OVERLAY = ResourceLocation.fromNamespaceAndPath("valoria", "textures/item/rot.png");

    @Override
    public boolean render(GuiGraphics guiGraphics, Font font, ItemStack stack, int x, int y) {
        if (ValoriaUtils.hasRot(stack)) {
            int stage = ValoriaUtils.getRot(stack);
            float alpha = Math.min(1.0f, stage / 100.0f);
            int height = (int) (16 * alpha);
            int offset = 16 - height;

            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();

            guiGraphics.pose().pushPose();
            guiGraphics.pose().translate(0, 0, 200);
            guiGraphics.blit(OVERLAY, x, y + offset, 0, offset, 16, height, 16, 16);

            guiGraphics.pose().popPose();
            return true;
        }

        return false;
    }
}