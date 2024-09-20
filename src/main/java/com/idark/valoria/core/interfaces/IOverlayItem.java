package com.idark.valoria.core.interfaces;

import com.mojang.blaze3d.systems.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.nbt.*;
import net.minecraft.resources.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.client.event.*;
import org.lwjgl.opengl.*;

public interface IOverlayItem{
    ResourceLocation getTexture();

    @OnlyIn(Dist.CLIENT)
    void render(CompoundTag tag, GuiGraphics gui, int offsetX, int offsetY);

    default boolean toRender() {
       return !Minecraft.getInstance().player.isSpectator();
    }

    default int mainHandOffset() {
        return 0;
    }

    default int offhandOffset() {
        return 25;
    }

    @OnlyIn(Dist.CLIENT)
    static void onDrawScreenPost(RenderGuiOverlayEvent.Post event){
        Minecraft mc = Minecraft.getInstance();
        GuiGraphics gui = event.getGuiGraphics();
        boolean mainFlag = !mc.player.getMainHandItem().isEmpty() && mc.player.getMainHandItem().getItem() instanceof IOverlayItem;
        if(mainFlag && mc.player.getMainHandItem().getItem() instanceof IOverlayItem item){
            if(item.toRender()){
                gui.pose().pushPose();
                gui.pose().translate(0, 0, -200);
                RenderSystem.enableBlend();
                RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                mc.textureManager.bindForSetup(item.getTexture());
                item.render(mc.player.getMainHandItem().getOrCreateTag(), gui, item.mainHandOffset(), 0);

                RenderSystem.disableBlend();
                gui.pose().popPose();
            }
        }

        if(!mc.player.getOffhandItem().isEmpty() && mc.player.getOffhandItem().getItem() instanceof IOverlayItem item){
            if(item.toRender()){
                gui.pose().pushPose();
                gui.pose().translate(0, 0, -200);
                RenderSystem.enableBlend();
                RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                mc.textureManager.bindForSetup(item.getTexture());
                item.render(mc.player.getOffhandItem().getOrCreateTag(), gui, mainFlag ? item.offhandOffset() : item.mainHandOffset(), 0);

                RenderSystem.disableBlend();
                gui.pose().popPose();
            }
        }
    }
}
