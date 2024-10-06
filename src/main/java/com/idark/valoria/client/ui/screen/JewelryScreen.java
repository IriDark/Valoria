package com.idark.valoria.client.ui.screen;

import com.idark.valoria.*;
import com.idark.valoria.client.ui.menus.*;
import com.idark.valoria.registries.block.entity.*;
import com.mojang.blaze3d.systems.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.screens.inventory.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraft.world.entity.player.*;
import net.minecraftforge.api.distmarker.*;

@OnlyIn(Dist.CLIENT)
public class JewelryScreen extends AbstractContainerScreen<JewelryMenu> {
    private final ResourceLocation GUI = new ResourceLocation(Valoria.ID, "textures/gui/container/jewelry.png");
    public JewelryScreen(JewelryMenu screenContainer, Inventory inv, Component titleIn) {
        super(screenContainer, inv, titleIn);
        this.imageHeight = 165;
        this.inventoryLabelY = this.inventoryLabelY + 46;
    }

    @Override
    public void render(GuiGraphics gui, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(gui);
        super.render(gui, mouseX, mouseY, partialTicks);
        this.renderTooltip(gui, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY) {
        pGuiGraphics.drawString(this.font, this.title, this.titleLabelX + 50, this.titleLabelY - 2, 4210752, false);
        pGuiGraphics.drawString(this.font, this.playerInventoryTitle, this.inventoryLabelX, this.inventoryLabelY - 46, 4210752, false);
    }

    @Override
    protected void renderBg(GuiGraphics gui, float partialTicks, int x, int y) {
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        int i = this.leftPos;
        int j = this.topPos;
        gui.blit(GUI, i, j, 0, 0, this.imageWidth, this.imageHeight);
        if (menu.blockEntity instanceof JewelryBlockEntity jewelry) {
            if (jewelry.progress > 0 && !jewelry.itemHandler.getStackInSlot(1).isEmpty() && !jewelry.itemHandler.getStackInSlot(0).isEmpty()) {
                int width = 24;
                width /= ((double) jewelry.progressMax / (double) jewelry.progress);
                gui.blit(GUI, i + 101, j + 47, 176, 0, width, 17, 256, 256);
            }
        }
    }
}