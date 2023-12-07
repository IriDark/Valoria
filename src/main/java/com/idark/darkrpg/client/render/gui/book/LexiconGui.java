package com.idark.darkrpg.client.render.gui.book;

import com.idark.darkrpg.DarkRPG;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class LexiconGui extends Screen {
    public static final ResourceLocation BACKGROUND = new ResourceLocation(DarkRPG.MOD_ID, "textures/gui/book/lexicon.png");
    public LexiconGui() {
        super(Component.translatable("gui.darkrpg.name"));
    }
    public static int xSize = 272;
    public static int ySize = 180;

    @Override
    public void render(GuiGraphics gui, int mouseX, int mouseY, float partialTicks) {
        renderBackground(gui);
        Minecraft mc = Minecraft.getInstance();
        RenderSystem.setShaderTexture(0, BACKGROUND);

        this.width = mc.getWindow().getGuiScaledWidth();
        this.height = mc.getWindow().getGuiScaledHeight();
        int guiLeft = (width - xSize) / 2, guiTop = (height - ySize) / 2;
        gui.blit(BACKGROUND, guiLeft, guiTop, 0, 0, xSize, ySize, 512, 512);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}