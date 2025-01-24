package com.idark.valoria.client.ui.screen;

import com.idark.valoria.Valoria;
import com.idark.valoria.client.ui.menus.KegMenu;
import com.idark.valoria.registries.block.entity.KegBlockEntity;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class KegScreen extends AbstractContainerScreen<KegMenu>{
    private final ResourceLocation GUI = new ResourceLocation(Valoria.ID, "textures/gui/container/keg_brewery.png");

    public KegScreen(KegMenu screenContainer, Inventory inv, Component titleIn){
        super(screenContainer, inv, titleIn);
        this.imageHeight = 165;
        this.inventoryLabelY = this.inventoryLabelY + 46;
    }

    @Override
    public void render(GuiGraphics gui, int mouseX, int mouseY, float partialTicks){
        this.renderBackground(gui);
        super.render(gui, mouseX, mouseY, partialTicks);
        this.renderTooltip(gui, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY){
        pGuiGraphics.drawString(this.font, this.title, this.titleLabelX + 50, this.titleLabelY - 2, 4210752, false);
        pGuiGraphics.drawString(this.font, this.playerInventoryTitle, this.inventoryLabelX, this.inventoryLabelY - 46, 4210752, false);
    }

    @Override
    protected void renderBg(GuiGraphics gui, float partialTicks, int x, int y){
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        int i = this.leftPos;
        int j = this.topPos;
        gui.blit(GUI, i, j, 0, 0, this.imageWidth, this.imageHeight);
        if(menu.blockEntity instanceof KegBlockEntity keg){
            if(keg.progress > 0 && !keg.itemHandler.getStackInSlot(0).isEmpty()){
                int height = 21;
                height /= ((double)keg.progressMax / (double)keg.progress);
                gui.blit(GUI, i + 76, j + 52 - height, 176, 21 - height, 22, height, 256, 256);
            }
        }
    }
}