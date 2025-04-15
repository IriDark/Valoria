package com.idark.valoria.client.ui.screen.book.lexicon;

import com.idark.valoria.*;
import com.idark.valoria.client.ui.screen.book.*;
import com.mojang.blaze3d.systems.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.resources.*;
import net.minecraft.sounds.*;
import net.minecraft.util.*;
import net.minecraftforge.api.distmarker.*;
import org.lwjgl.glfw.*;
import pro.komaru.tridot.api.render.*;

import java.util.function.*;

import static com.idark.valoria.Valoria.loc;

// most likely will be renamed to something else?
@OnlyIn(Dist.CLIENT)
public class Codex extends DotScreen{
    public static Codex screen;
    public int backgroundWidth = 1024;
    public int backgroundHeight = 1024;
    public int frameWidth = 276;
    public int frameHeight = 180;
    public int insideWidth = 262;
    public int insideHeight = 165;
    public float xModifier = 0.75f, yModifier = 0.75f;
    public float xOffset;
    public float yOffset;

    public ResourceLocation FRAME = loc("textures/gui/book/frame.png");
    public Codex(){
        super();
        assetsId = Valoria.ID;
    }

    public void sound(Supplier<SoundEvent> soundEvent, float volume, float pitch) {
        Minecraft.getInstance().player.playNotifySound(soundEvent.get(), SoundSource.PLAYERS, volume, pitch);
    }

    public static Codex getInstance() {
        if (screen == null) screen = new Codex();
        return screen;
    }

    public static void open() {
        CodexEntries.init();
        final Codex codex = getInstance();
        Minecraft.getInstance().setScreen(codex);
        codex.sound(() -> SoundEvents.BOOK_PAGE_TURN, 1.0f, 1.0f);
    }

    @Override
    public void render(GuiGraphics gui, int mouseX, int mouseY, float partialTicks){
        super.render(gui, mouseX, mouseY, partialTicks);
        renderBackground(gui);
        gui.blit(FRAME, guiLeft(), guiTop(), 0, 0, frameWidth, frameHeight, 512, 512);
        renderBackground(gui, "textures/gui/book/background.png", mouseX, mouseY);
        if(isHover(mouseX, mouseY, (int)(this.cx()), guiTop() + this.frameHeight - 8, 10, 10)) {
            push();
            scale(1.5f, 1.5f, cx(), cy());
            layer(700);
            move((int)(this.cx() - 2), guiTop() + frameHeight - 6 - 32);
            gui.blit(FRAME, 0, 0, 10, 191, 12, 12, 512, 512);
            pop();
        } else {
            gui.blit(FRAME, (int)(cx()), guiTop() + frameHeight - 8, 0, 192, 10, 10, 512, 512);
        }

        //drawDebug(mouseX, mouseY);
    }

    public void drawDebug(double mouseX, double mouseY) {
        push();
        localPose.translate(0,0,600);
        localG.fill((int)mouseX, (int)mouseY, (int)(mouseX - 8), (int)(mouseY - 8), CommonColors.WHITE);

        float uOffset = Mth.clamp((backgroundWidth / 2f - insideWidth) + xOffset * xModifier, 0, backgroundWidth - insideWidth);
        float vOffset = Mth.clamp((backgroundHeight / 2f - insideHeight) + yOffset * yModifier, 0, backgroundHeight - insideHeight);
        for(CodexEntry entry : CodexEntries.entries){
            int x = (backgroundWidth - insideWidth) / 2 - (entry.x - guiLeft()) - (int)uOffset;
            int y = (backgroundHeight - insideHeight) / 2 - (entry.y - guiTop()) - (int)vOffset;
            localG.fill(x + 11, y + 11, x + 25, y + 25 , CommonColors.WHITE); // buttons
        }

        pop();
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button){
        float uOffset = Mth.clamp((backgroundWidth / 2f - insideWidth) + xOffset * xModifier, 0, backgroundWidth - insideWidth);
        float vOffset = Mth.clamp((backgroundHeight / 2f - insideHeight) + yOffset * yModifier, 0, backgroundHeight - insideHeight);
        if(button == GLFW.GLFW_MOUSE_BUTTON_LEFT){
            for(CodexEntry entry : CodexEntries.entries){
                int x = (backgroundWidth - insideWidth) / 2 - (entry.x - guiLeft()) - (int)uOffset;
                int y = (backgroundHeight - insideHeight) / 2 - (entry.y - guiTop()) - (int)vOffset;
                if(isOnScreen(mouseX, mouseY)){
                    if(entry.isUnlocked() && entry.isHover(mouseX, mouseY, x, y)){
                        sound(() -> SoundEvents.BOOK_PAGE_TURN, 1, 1);
                        changeChapter(entry.getChapter());
                    }
                }

                if(isHover(mouseX, mouseY, (int)(this.cx()), guiTop() + this.frameHeight - 8, 10, 10)) {
                    xOffset = 0;
                    yOffset = 0;
                    sound(() -> SoundEvents.LEVER_CLICK, 1, 1);
                }
            }
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    public boolean isOnScreen(double mouseX, double mouseY) {
        return mouseX >= insideLeft() && mouseY >= insideTop() && mouseX <= (insideLeft() + insideWidth) && mouseY <= (insideTop() + insideHeight);
    }

    public boolean isHover(double mouseX, double mouseY, int x, int y, int width, int height) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }

    public void changeChapter(Chapter chapter) {
        Minecraft.getInstance().setScreen(new LexiconGui(chapter));
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        float maxXOffset = (backgroundWidth) / (2f * xModifier);
        float maxYOffset = (backgroundHeight) / (2f * yModifier);
        xOffset = Mth.clamp(xOffset + (float)dragX, -maxXOffset, maxXOffset);
        yOffset = Mth.clamp(yOffset + (float)dragY, -maxYOffset, maxYOffset);
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    public void renderBackground(GuiGraphics gui, String texture, float mouseX, float mouseY) {
        float uOffset = Mth.clamp((backgroundWidth / 2f - insideWidth) + xOffset * xModifier, 0, backgroundWidth);
        float vOffset = Mth.clamp((backgroundHeight / 2f - insideHeight ) + yOffset * yModifier, 0, backgroundHeight);
        gui.blit(loc(texture), insideLeft(), insideTop(), (int)uOffset, (int)vOffset, insideWidth, insideHeight, backgroundWidth, backgroundHeight);
        renderEntries(gui, uOffset, vOffset, mouseX, mouseY);
        renderCorners(gui, insideLeft(), insideTop());
    }

    public void renderCorners(GuiGraphics gui, int insideLeft, int insideTop){
        RenderSystem.enableBlend();
        gui.pose().pushPose();
        gui.pose().translate(0, 0, 600);
        gui.blit(loc("textures/gui/book/frame_blur.png"), insideLeft, insideTop, 0, 0, insideWidth, insideHeight, insideWidth, insideHeight);
        gui.pose().popPose();
        RenderSystem.disableBlend();
    }

    public void renderEntries(GuiGraphics gui, float uOffset, float vOffset, float mouseX, float mouseY) {
        scissorsOn(insideLeft(), insideTop(), insideWidth, insideHeight);
        for (CodexEntry entry : CodexEntries.entries) {
            if (entry.isUnlocked()) {
                entry.render(this, gui, uOffset, vOffset, insideLeft(), insideTop(), mouseX, mouseY);
            }
        }

        scissorsOff();
    }

    public int insideLeft() {
        return (width - insideWidth) / 2;
    }

    public int insideTop() {
        return (height - insideHeight) / 2;
    }

    public int guiLeft() {
        return (width - frameWidth) / 2;
    }

    public int guiTop() {
        return (height - frameHeight) / 2;
    }
}
