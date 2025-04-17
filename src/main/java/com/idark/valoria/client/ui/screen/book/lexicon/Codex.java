package com.idark.valoria.client.ui.screen.book.lexicon;

import com.idark.valoria.*;
import com.idark.valoria.api.events.CodexEvent.*;
import com.idark.valoria.api.unlockable.*;
import com.idark.valoria.client.ui.screen.book.*;
import com.mojang.blaze3d.systems.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.resources.*;
import net.minecraft.sounds.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.player.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.common.*;
import org.lwjgl.glfw.*;
import pro.komaru.tridot.api.render.*;
import pro.komaru.tridot.util.*;
import pro.komaru.tridot.util.math.*;

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
    public int insideHeight = 164;
    public float xModifier = 0.75f, yModifier = 0.75f;
    public float xOffset;
    public float yOffset;
    public int entries = 104;
    public float animTime = 10;
    public Player player;

    public ResourceLocation FRAME = loc("textures/gui/book/frame.png");
    public Codex(){
        super();
        assetsId = Valoria.ID;
    }

    public static Codex getInstance() {
        if (screen == null) screen = new Codex();
        return screen;
    }

    public void sound(Supplier<SoundEvent> soundEvent, float volume, float pitch) {
        Minecraft.getInstance().player.playNotifySound(soundEvent.get(), SoundSource.PLAYERS, volume, pitch);
    }

    private float openedAtTick = -1;
    public void open(Player player) {
        this.player = player;
        CodexEntries.init();
        final Codex codex = getInstance();
        codex.openedAtTick = codex.tick + codex.mc().getPartialTick();
        Minecraft.getInstance().setScreen(codex);
        codex.sound(() -> SoundEvents.BOOK_PAGE_TURN, 1.0f, 1.0f);
    }

    public float time(){
        return (tick + mc().getPartialTick()) - openedAtTick;
    }

    @Override
    public void render(GuiGraphics gui, int mouseX, int mouseY, float partialTicks){
        super.render(gui, mouseX, mouseY, partialTicks);
        float t = time();
        Interp interp = Interp.fade;
        float progress = interp.apply(Mathf.clamp(t/animTime));

        color(Mathf.clamp(progress/0.9f));
        renderBackground(gui);
        color(progress);

        float yOffset;
        yOffset = 0;

        push();
        move(0,yOffset);
        scale(0.9f+progress*0.1f,0.9f+progress*0.1f,cx(),cy());
        render(gui, mouseX, mouseY);

        //drawDebug(mouseX, mouseY);
        pop();
    }

    public void render(GuiGraphics gui, int mouseX, int mouseY){
        push();
        layer(300 * 2);
        gui.blit(FRAME, guiLeft(), guiTop(), 0, 0, frameWidth, frameHeight, 512, 512);
        layer(0);
        pop();

        push();
        renderBackground(gui, "textures/gui/book/background.png", mouseX, mouseY);
        layer(601);
        if(isHover(mouseX, mouseY, (int)(this.cx()-10), guiTop() + this.frameHeight - 15, 20, 20)) {
            gui.blit(FRAME, (int)(cx()-4-1), guiTop() + frameHeight - 8 -1, 10, 191, 12, 12, 512, 512);
        } else {
            gui.blit(FRAME, (int)(cx()-4), guiTop() + frameHeight - 8, 0, 192, 10, 10, 512, 512);
        }

        pop();
    }

    public void drawDebug(double mouseX, double mouseY) {
        push();
        localPose.translate(0,0,600);
        localG.fill((int)mouseX, (int)mouseY, (int)(mouseX - 8), (int)(mouseY - 8), CommonColors.WHITE);

        float uOffset = getuOffset();
        float vOffset = getvOffset();
        for(CodexEntry entry : CodexEntries.entries){
            int x = (backgroundWidth - insideWidth) / 2 - (entry.x - guiLeft()) - (int)uOffset;
            int y = (backgroundHeight - insideHeight) / 2 - (entry.y - guiTop()) - (int)vOffset;
            localG.fill(x + 8, y + 8, x + 28, y + 28 , CommonColors.WHITE); // buttons
        }

        localG.fill((int)(this.cx()-7), guiTop() + this.frameHeight - 15, (int)(this.cx()-6) + 15, guiTop() + this.frameHeight + 5, CommonColors.WHITE); // buttons
        pop();
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button){
        float uOffset = getuOffset();
        float vOffset = getvOffset();
        if(button == GLFW.GLFW_MOUSE_BUTTON_LEFT){
            for(CodexEntry entry : CodexEntries.entries){
                entry.onClick(this, mouseX, mouseY, (int)uOffset, (int)vOffset);
            }
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    public static boolean onClaim(CodexEntry entry, Unlockable unlockable) {
        return MinecraftForge.EVENT_BUS.post(new OnRewardClaim(entry, unlockable));
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
        if(button == GLFW.GLFW_MOUSE_BUTTON_LEFT){
            float maxXOffset = (backgroundWidth) / (2f * xModifier);
            float maxYOffset = (backgroundHeight) / (2f * yModifier);
            xOffset = Mth.clamp(xOffset + (float)dragX, -maxXOffset, maxXOffset);
            yOffset = Mth.clamp(yOffset + (float)dragY, -maxYOffset, maxYOffset);
        }

        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    public void renderBackground(GuiGraphics gui, String texture, float mouseX, float mouseY) {
        float uOffset = getuOffset();
        float vOffset = getvOffset();
        gui.blit(loc(texture), insideLeft(), insideTop(), (int)uOffset, (int)vOffset, insideWidth, insideHeight, backgroundWidth, backgroundHeight);

        push();
        layer(entries);
        renderEntries(gui, uOffset, vOffset, mouseX, mouseY);
        pop();

        renderCorners(gui, insideLeft(), insideTop());
    }

    public void renderCorners(GuiGraphics gui, int insideLeft, int insideTop){
        push();
        RenderSystem.enableBlend();
        layer(300);
        gui.blit(loc("textures/gui/book/frame_blur.png"), insideLeft, insideTop, 0, 0, insideWidth, insideHeight, insideWidth, insideHeight);
        RenderSystem.disableBlend();
        pop();
    }

    public void renderEntries(GuiGraphics gui, float uOffset, float vOffset, float mouseX, float mouseY) {
        scissorsOn(insideLeft(), insideTop(), insideWidth, insideHeight);
        for (CodexEntry entry : CodexEntries.entries){
            if(entry.isHidden()) return;
            entry.node.children.each(c -> {
                if(c.entry.isHidden() || !entry.isUnlocked()) return;
                float chx = c.entry.x;
                float chy = c.entry.y;

                float x = entry.x;
                float y = entry.y;

                float deltaY = chy - y;

                float y2 = y + deltaY * 0.5f;
                var color = c.entry.isUnlocked() ? Col.intArgb(Col.fromHex("7A5577")) : Col.intArgb(Col.gray);

                push();
                move((backgroundWidth - insideWidth) / 2f, (backgroundHeight - insideHeight) / 2f);
                move(guiLeft(), guiTop());
                move(-uOffset, -vOffset);
                move(22 - 4f, 22 - 4f);

                layer(entries - 105);
                gui.fill((int)(-x - 1), (int)-y - 1 + 11, (int)-x + 2, (int)-y + 2 + 11, color);
                gui.vLine((int)-x, (int)-y, (int)-y2, color);
                gui.hLine((int)-x, (int)-chx, (int)-y2, color);
                gui.vLine((int)-chx, (int)-y2, (int)-chy, color);
                pop();

                entry.render(this, gui, uOffset, vOffset, insideLeft(), insideTop(), mouseX, mouseY);
                c.entry.render(this, gui, uOffset, vOffset, insideLeft(), insideTop(), mouseX, mouseY);
            });
        }

        scissorsOff();
    }

    public float getvOffset(){
        return Mth.clamp((backgroundHeight / 2f - insideHeight) + yOffset * yModifier, 0, backgroundHeight - insideHeight);
    }

    public float getuOffset(){
        return Mth.clamp((backgroundWidth / 2f - insideWidth) + xOffset * xModifier, 0, backgroundWidth - insideWidth);
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
