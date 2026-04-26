package com.idark.valoria.client.ui.screen.book.codex;

import com.idark.valoria.*;
import com.idark.valoria.api.events.CodexEvent.*;
import com.idark.valoria.api.unlockable.types.*;
import com.idark.valoria.client.ui.screen.book.*;
import com.idark.valoria.registries.*;
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

import java.util.*;
import java.util.function.*;

import static com.idark.valoria.Valoria.loc;

@OnlyIn(Dist.CLIENT)
public class Codex extends DotScreen{
    public static Codex screen;
    public CodexEntry focusedOn;

    public int backgroundWidth = 1024, backgroundHeight = 1024;
    public int frameWidth = 276;
    public int frameHeight = 180;
    public int insideWidth = 262;
    public int insideHeight = 164;

    public float zoom = 1.0f;
    public float targetZoom = 1.0f;
    public final float minZoom = 0.5f, maxZoom = 2.0f;
    public float sidebarScroll = 0;
    public float targetSidebarScroll = 0;
    private boolean isDraggingSidebar = false;

    public float xModifier = 0.75f, yModifier = 0.75f;
    public float xOffset, yOffset;
    public float targetXOffset, targetYOffset;

    public int entriesLayer = 104;
    public float animTime = 10;
    public Player player;

    public ResourceLocation FRAME = loc("textures/gui/book/frame.png");

    public Codex(){
        super();
        assetsId = Valoria.ID;
    }

    public static Codex getInstance() {
        if (screen == null) {
            CodexEntries.initChapters(); // init so recipes can render
            screen = new Codex();
        }

        return screen;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        if (mouseX < guiLeft() - 100) {
            float maxScroll = Math.max(0, (CodexEntries.sidebarEntries.size * 18) - 450);
            targetSidebarScroll = (float) Mth.clamp(targetSidebarScroll - (delta * 20), 0, maxScroll);
            return true;
        }

        float oldZoom = this.targetZoom;
        float zoomSpeed = 0.1f;
        this.targetZoom = Mth.clamp(this.targetZoom + (float)delta * zoomSpeed, minZoom, maxZoom);
        if (oldZoom != this.targetZoom) {
            float mouseRelX = (float) (mouseX - getCenterX());
            float mouseRelY = (float) (mouseY - getCenterY());
            float multiplier = (1f / oldZoom - 1f / this.targetZoom);
            this.targetXOffset += mouseRelX * multiplier / xModifier;
            this.targetYOffset += mouseRelY * multiplier / yModifier;
        }

        return true;
    }

    public void sound(Supplier<SoundEvent> soundEvent, float volume, float pitch) {
        Minecraft.getInstance().player.playSound(soundEvent.get(), volume, pitch);
    }

    private float openedAtTick = -1;
    public void open(Player player) {
        this.player = player;
        CodexEntries.init();
        final Codex codex = getInstance();
        codex.openedAtTick = codex.tick + codex.mc().getPartialTick();
        Minecraft.getInstance().setScreen(codex);
        codex.sound(() -> SoundEvents.BOOK_PAGE_TURN, 1.0f, 1.0f);

        CodexEntry root = CodexEntries.entries.find(e -> e.getChapter() == CodexEntries.PAGES_CHAPTER);
        if (root != null) {
            focusOn(root);
        } else {
            xOffset = 0; yOffset = 0; zoom = 1;
        }
    }

    public float time(){
        return (tick + mc().getPartialTick()) - openedAtTick;
    }

    @Override
    public void render(GuiGraphics gui, int mouseX, int mouseY, float partialTicks){
        super.render(gui, mouseX, mouseY, partialTicks);
        this.xOffset = Mth.lerp(0.15f, this.xOffset, this.targetXOffset);
        this.yOffset = Mth.lerp(0.15f, this.yOffset, this.targetYOffset);
        this.zoom = Mth.lerp(0.15f, this.zoom, this.targetZoom);
        this.sidebarScroll = Mth.lerp(0.15f, this.sidebarScroll, this.targetSidebarScroll);

        float t = time();
        Interp interp = Interp.pow2Out;
        float progress = interp.apply(Mathf.clamp(t/animTime));

        color(Mathf.clamp(progress/0.9f));
        renderBackground(gui);
        color(progress);

        push();

        float y = Mth.lerp(progress, height, 0);
        move(0, y);
        float scaleProgress = Mth.clamp(progress, 0, 1);
        float s = Mth.lerp(scaleProgress, 0, 1.0f);
        scale(s, s, cx(), cy());
        render(gui, mouseX, mouseY);
        pop();
    }

    public void render(GuiGraphics gui, int mouseX, int mouseY){
        push();
        layer(408);
        int scaleWidth = 203;
        int scaleX = guiLeft() + 14;
        int scaleY = guiTop() + frameHeight - 30;
        String zoomText = String.format(Locale.US, "%.1fx", this.targetZoom);
        var font = Minecraft.getInstance().font;
        int textX = scaleX + scaleWidth + 10;
        int textY = scaleY + 3;
        gui.drawString(font, "\uD83D\uDD0D " + zoomText, textX, textY, 0xFFFFFF, false);
        pop();

        push();
        layer(600); // shadows
        gui.blit(FRAME, guiLeft(), guiTop(), 0, 0, frameWidth, frameHeight, 512, 512);
        layer(0);
        pop();

        push();
            renderBackground(gui, "textures/gui/book/background.png", mouseX, mouseY);
            layer(601); // home button
            if(isHover(mouseX, mouseY, (int)(this.cx() - 10), guiTop() + this.frameHeight - 15, 20, 20)){
                gui.blit(FRAME, (int)(cx() - 5 - 1), guiTop() + frameHeight - 10 - 1, 10, 191, 12, 12, 512, 512);
            }else{
                gui.blit(FRAME, (int)(cx() - 5), guiTop() + frameHeight - 10, 0, 192, 10, 10, 512, 512);
            }

        pop();

        push();
        gui.fill(guiLeft() - 500, 0, guiLeft() - 125, screen.getRectangle().height(), Col.packColor(75, 0, 0, 0));

        int listStartX = guiLeft() - 335;
        int listStartY = 10;
        int listHeight = 450;
        int listWidth = 200;

        // Sidebar scrollbar logic
        int totalContentHeight = CodexEntries.sidebarEntries.size * 18;
        if (totalContentHeight > listHeight) {
            int barX = listStartX + listWidth;
            int barY = listStartY + 1;
            int barWidth = 2;
            gui.fill(barX, barY, barX + barWidth, barY + listHeight, Col.packColor(75, 0, 0, 0));
            int knobHeight = Math.max(10, (int)(((float)listHeight / totalContentHeight) * 25));
            int knobY = listStartY + (int)((sidebarScroll / (totalContentHeight - listHeight)) * (listHeight - knobHeight));

            gui.fill(barX, knobY, barX + barWidth, knobY + knobHeight, Col.packColor(255, 220, 200, 180));
        }

        gui.enableScissor(listStartX, listStartY, listStartX + 180, listStartY + listHeight);
        int currentY = listStartY - (int)sidebarScroll;
        for (SidebarEntry sEntry : CodexEntries.sidebarEntries) {
            sEntry.render(this, listStartX, currentY, gui, mouseX, mouseY);
            currentY += SidebarEntry.height + 3;
        }

        gui.disableScissor();

        float percentage = CodexEntries.sidebarEntries.size > 0 ? ((float)CodexEntries.openedEntries.size / CodexEntries.sidebarEntries.size) * 100f : 0;
        String progressText = String.format(java.util.Locale.US, "%.0f%%", percentage);
        gui.drawCenteredString(Minecraft.getInstance().font, progressText, listStartX + SidebarEntry.width / 2 - 5, listStartY + listHeight + 5, 0xFFFFFF);
        pop();

        for(CodexEntry entry : CodexEntries.entries) {
            entry.renderTooltipPost(this, gui, getuOffset(), getvOffset(), guiLeft() + 8, guiTop() + 10);
        }
    }

    public void focusOn(CodexEntry entry) {
        this.focusedOn = entry;
        this.targetZoom = 1.0f;
        this.targetXOffset = (12f - entry.x) / xModifier;
        this.targetYOffset = (12f - entry.y) / yModifier;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button){
        double unzoomedX = getUnzoomedMouseX(mouseX);
        double unzoomedY = getUnzoomedMouseY(mouseY);
        if(button == GLFW.GLFW_MOUSE_BUTTON_LEFT){
            for(CodexEntry entry : CodexEntries.entries){
                entry.onClick(this, unzoomedX, unzoomedY);
            }

            for(SidebarEntry entry : CodexEntries.openedEntries){
                entry.onClick(this, mouseX, mouseY);
            }

            if(isHover(mouseX, mouseY, (int)(this.cx() - 10), guiTop() + this.frameHeight - 15, 20, 20)){
                CodexEntry root = CodexEntries.entries.find(e -> e.getChapter() == CodexEntries.PAGES_CHAPTER);
                if (root != null) {
                    focusOn(root);
                } else {
                    xOffset = 0; yOffset = 0; zoom = 1;
                }

                sound(SoundsRegistry.UI_CODEX_CLICK, 0.5f, 1f);
                return true;
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
        Minecraft.getInstance().setScreen(new BookGui(chapter, false));
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if(button == GLFW.GLFW_MOUSE_BUTTON_LEFT){
            float maxXOffset = (backgroundWidth) / (2f * xModifier);
            float maxYOffset = (backgroundHeight) / (2f * yModifier);
            xOffset = Mth.clamp(xOffset + (float)-dragX, -maxXOffset, maxXOffset);
            yOffset = Mth.clamp(yOffset + (float)-dragY, -maxYOffset, maxYOffset);
            targetXOffset = Mth.clamp(targetXOffset + (float)-dragX, -maxXOffset, maxXOffset);
            targetYOffset = Mth.clamp(targetYOffset + (float)-dragY, -maxYOffset, maxYOffset);
        }

        if(focusedOn != null){
            double realMouseX = (mouseX - getCenterX()) * zoom + getCenterX();
            double realMouseY = (mouseY - getCenterY()) * zoom + getCenterY();
            boolean isInsideBook = isOnScreen(realMouseX, realMouseY);
            if(!isInsideBook){
                focusedOn = null;
            }
        }

        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    public void renderBackground(GuiGraphics gui, String texture, float mouseX, float mouseY) {
        float uOffset = getuOffset();
        float vOffset = getvOffset();
        scissorsOn((int)getCenterX(), (int)getCenterY(), insideWidth, insideHeight);
        gui.pose().pushPose();

        float cX = getCenterX();
        float cY = getCenterY();
        gui.pose().translate(cX, cY, 0);
        gui.pose().scale(zoom, zoom, 1f);
        gui.pose().translate(-cX, -cY, 0);

        int bgX = insideLeft() - (int)uOffset;
        int bgY = insideTop() - (int)vOffset;
        gui.blit(loc(texture), bgX, bgY, 0, 0, backgroundWidth, backgroundHeight, backgroundWidth, backgroundHeight);

        float unzoomedX = (float) getUnzoomedMouseX(mouseX);
        float unzoomedY = (float) getUnzoomedMouseY(mouseY);

        push();
        layer(entriesLayer);
        renderEntries(gui, uOffset, vOffset, unzoomedX, unzoomedY);
        pop();

        gui.pose().popPose();
        scissorsOff();
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
        for (CodexEntry entry : CodexEntries.entries){
            if(entry.isHidden()) return;
            entry.node.children.each(c -> {
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

                layer(entriesLayer - 105);
                gui.fill((int)(-x - 1), (int)-y - 1 + 11, (int)-x + 2, (int)-y + 2 + 11, color);
                gui.vLine((int)-x, (int)-y, (int)-y2, color);
                gui.hLine((int)-x, (int)-chx, (int)-y2, color);
                gui.vLine((int)-chx, (int)-y2, (int)-chy, color);
                pop();

                entry.render(this, gui, uOffset, vOffset, insideLeft(), insideTop(), mouseX, mouseY);
                c.entry.render(this, gui, uOffset, vOffset, insideLeft(), insideTop(), mouseX, mouseY);
            });
        }
    }

    public float getCenterX() {
        return insideLeft() + insideWidth / 2f;
    }

    public float getCenterY() {
        return insideTop() + insideHeight / 2f;
    }

    public double getUnzoomedMouseX(double mouseX) {
        return (mouseX - getCenterX()) / zoom + getCenterX();
    }

    public double getUnzoomedMouseY(double mouseY) {
        return (mouseY - getCenterY()) / zoom + getCenterY();
    }

    public float getuOffset(){
        float minU = (insideWidth / (2f * zoom)) - (insideWidth / 2f);
        float maxU = backgroundWidth - (insideWidth / 2f) - (insideWidth / (2f * zoom));
        float rawOffset = (backgroundWidth / 2f - insideWidth) + xOffset * xModifier;

        if (minU > maxU) return (backgroundWidth - insideWidth) / 2f;
        return Mth.clamp(rawOffset, minU, maxU);
    }

    public float getvOffset(){
        float minV = (insideHeight / (2f * zoom)) - (insideHeight / 2f);
        float maxV = backgroundHeight - (insideHeight / 2f) - (insideHeight / (2f * zoom));

        float rawOffset = (backgroundHeight / 2f - insideHeight) + yOffset * yModifier;

        if (minV > maxV) return (backgroundHeight - insideHeight) / 2f;
        return Mth.clamp(rawOffset, minV, maxV);
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
