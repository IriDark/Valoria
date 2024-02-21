package com.idark.valoria.client.gui.screen;

import com.idark.valoria.Valoria;
import com.idark.valoria.registries.world.block.entity.ManipulatorBlockEntity;
import com.idark.valoria.client.gui.menu.ManipulatorMenu;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class ManipulatorScreen extends AbstractContainerScreen<ManipulatorMenu> {
    private final ResourceLocation GUI = new ResourceLocation(Valoria.MOD_ID, "textures/gui/container/manipulator.png");

    public ManipulatorScreen(ManipulatorMenu screenContainer, Inventory inv, Component titleIn) {
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
        pGuiGraphics.drawString(this.font, this.title, this.titleLabelX + 42, this.titleLabelY, 4210752, false);
        pGuiGraphics.drawString(this.font, this.playerInventoryTitle, this.inventoryLabelX, this.inventoryLabelY - 46, 4210752, false);
    }

    @Override
    protected void renderBg(GuiGraphics gui, float partialTicks, int x, int y) {
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        int i = this.leftPos;
        int j = this.topPos;
        gui.blit(GUI, i, j, 0, 0, this.imageWidth, this.imageHeight);

        gui.blit(GUI, i + 16, j + 25, 181, 27, 5, 5);
        gui.blit(GUI, i + 49, j + 25, 186, 27, 5, 5);
        gui.blit(GUI, i + 33, j + 8, 176, 27, 5, 5);
        gui.blit(GUI, i + 33, j + 42, 191, 27, 5, 5);
        if (menu.tileEntity instanceof ManipulatorBlockEntity elemental) {
            if (elemental.progress > 0) {
                int width = 24;
                width /= ((double) elemental.progressMax / (double) elemental.progress);
                gui.blit(GUI, i + 100, j + 53, 176, 0, width, 17, 256, 256);
            }
        }
    }
}