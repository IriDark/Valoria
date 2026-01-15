package com.idark.valoria.client.render.item;

import com.mojang.blaze3d.systems.*;
import net.minecraft.client.gui.*;
import net.minecraft.nbt.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;
import net.minecraftforge.client.*;

public class NihilityDecorator implements IItemDecorator{
    private static final ResourceLocation OVERLAY = new ResourceLocation("valoria", "textures/item/rot.png");

    @Override
    public boolean render(GuiGraphics guiGraphics, Font font, ItemStack stack, int x, int y) {
        CompoundTag tag = stack.getTag();
        if (tag != null && tag.contains("ValoriaRot")) {
            int stage = tag.getInt("ValoriaRot");
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