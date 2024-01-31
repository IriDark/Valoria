package com.idark.valoria.client.screen;

import com.idark.valoria.Valoria;
import com.idark.valoria.block.blockentity.JewelryBlockEntity;
import com.idark.valoria.client.container.JewelryMenu;
import com.idark.valoria.recipe.JewelryRecipe;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class JewelryScreen extends AbstractContainerScreen<JewelryMenu> {
    private final ResourceLocation GUI = new ResourceLocation(Valoria.MOD_ID, "textures/gui/container/jewelry.png");

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

    }

    @Override
    protected void renderBg(GuiGraphics gui, float partialTicks, int x, int y) {
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        this.minecraft.getTextureManager().bindForSetup(GUI);
        int i = this.leftPos;
        int j = this.topPos;
        gui.blit(GUI, i, j, 0, 0, this.imageWidth, this.imageHeight);

        if (menu.tileEntity instanceof JewelryBlockEntity jewelry) {
            if (jewelry.progress > 0) {
                width = 24;
                width /= ((double) jewelry.progressMax / (double) jewelry.progress);
                gui.blit(GUI, i + 100, j + 47, 176, 0, width, 17, 256, 256);
            }
        }
    }
}