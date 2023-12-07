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

    @Override
    public void render(GuiGraphics gui, int mouseX, int mouseY, float partialTicks) {
        renderBackground(gui);
        Minecraft mc = Minecraft.getInstance();
        RenderSystem.setShaderTexture(0, BACKGROUND);

        this.width = mc.getWindow().getGuiScaledWidth();
        this.height = mc.getWindow().getGuiScaledHeight();
        gui.blit(BACKGROUND, 240, 125, 0, 0, 487, 180 * 2, 600, 512);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}