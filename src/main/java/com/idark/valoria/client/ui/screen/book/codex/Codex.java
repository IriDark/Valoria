package com.idark.valoria.client.ui.screen.book.codex;

import com.idark.valoria.*;
import com.idark.valoria.client.ui.screen.book.*;
import com.idark.valoria.core.config.*;
import com.idark.valoria.core.network.*;
import com.idark.valoria.core.network.packets.*;
import com.idark.valoria.registries.*;
import com.mojang.blaze3d.systems.*;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.*;
import it.unimi.dsi.fastutil.booleans.*;
import net.minecraft.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.screens.*;
import net.minecraft.client.renderer.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraft.sounds.*;
import net.minecraft.util.*;
import net.minecraftforge.api.distmarker.*;
import org.jetbrains.annotations.*;
import org.joml.*;
import org.lwjgl.glfw.*;
import pro.komaru.tridot.api.render.*;
import pro.komaru.tridot.util.*;
import pro.komaru.tridot.util.math.*;

import java.lang.Math;
import java.util.*;
import java.util.function.*;

import static com.idark.valoria.Valoria.loc;

@OnlyIn(Dist.CLIENT)
public class Codex extends DotScreen{
    public static Codex screen;
    public CodexEntry focusedOn;
    private static final Component SEARCH_HINT = Component.translatable("gui.recipebook.search_hint").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY);

    public int backgroundWidth = 512, backgroundHeight = 512;
    public int frameWidth = 276;
    public int frameHeight = 180;
    public int insideWidth = 262;
    public int insideHeight = 164;

    public int listHeight() {
        return height - 60;
    }

    public int listWidth = 200;

    public float zoom = 1.0f;
    public float targetZoom = 1.0f;
    public final float minZoom = 0.5f, maxZoom = 2.0f;
    public float sidebarScroll = 0;
    public float targetSidebarScroll = 0;

    public float xModifier = 0.75f, yModifier = 0.75f;
    public float xOffset, yOffset;
    public float targetXOffset, targetYOffset;

    public int entriesLayer = 104;
    public float animTime = 10;

    public EditBox searchBar;
    public ResourceLocation FRAME = loc("textures/gui/book/frame.png");

    public Codex(){
        super();
        assetsId = Valoria.ID;
    }

    @Override
    protected void init() {
        super.init();
        String s = getSearchValue();
        searchBar = new EditBox(Minecraft.getInstance().font, listStartX() + 5, listStartY() + 5, 190, 12, Component.translatable("itemGroup.search"));
        searchBar.setMaxLength(50);
        searchBar.setBordered(true);
        searchBar.setVisible(true);
        searchBar.setValue(s);
        searchBar.setTextColor(0xFFFFFF);
        searchBar.setHint(SEARCH_HINT);
        this.addWidget(searchBar);

        if (!isSidebarDisabled()){
            boolean isAdmin = this.minecraft.player != null && this.minecraft.player.hasPermissions(2);
            int patreonX = isAdmin ? (this.width / 2 + 5) : (this.width / 2 - 100);
            if (isAdmin) {
                this.addRenderableWidget(new ImageButton(this.width / 2 - 205, 20, 200, 40, 0, 0, 40, Valoria.loc("textures/gui/progression.png"), 200, 80, (button) -> {
                    BooleanConsumer callback = (confirmed) -> {
                        if (confirmed) {
                            PacketHandler.sendToServer(new ProgressionDisableCodexPacket());
                        }
                        Minecraft.getInstance().setScreen(this);
                    };

                    ConfirmScreen warning = new ConfirmScreen(
                    callback,
                    Component.translatable("codex.screen.valoria.codex_progression.title").withStyle(ChatFormatting.RED),
                    Component.translatable("codex.screen.valoria_progression.description_" + (ServerConfig.ENABLE_CODEX_PROGRESSION.get() ? "disable" : "enable"))
                    );

                    Minecraft.getInstance().setScreen(warning);
                }));
            }

            this.addRenderableWidget(new ImageButton(patreonX, 20, 200, 40, 0, 0, 40, Valoria.loc("textures/gui/patreon.png"), 200, 80, (button) -> {
                String url = "https://www.patreon.com/c/IriDark";
                ConfirmLinkScreen confirmLinkScreen = new ConfirmLinkScreen((confirmed) -> {
                    if (confirmed) {
                        Util.getPlatform().openUri(url);
                    }
                    this.minecraft.setScreen(this);
                }, url, true);

                this.minecraft.setScreen(confirmLinkScreen);
            }));
        }
    }

    /**
       @return the last instance of Codex to prevent opening animation when closing a book GUI
    **/
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
            float maxScroll = Math.max(0, (CodexEntries.sidebarEntries.size * 18) - listHeight());
            targetSidebarScroll = (float) Mth.clamp(targetSidebarScroll - (delta * 20), 0, maxScroll);
            return true;
        }

        float zoomSpeed = 0.1f;
        this.targetZoom = Mth.clamp(this.targetZoom + (float)delta * zoomSpeed, minZoom, maxZoom);
        return true;
    }

    public void sound(Supplier<SoundEvent> soundEvent, float volume, float pitch) {
        Minecraft.getInstance().player.playSound(soundEvent.get(), volume, pitch);
    }

    private float openedAtTick = -1;
    public void open() {
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
        this.update(gui);
        float t = time();
        Interp interp = Interp.pow2Out;
        float progress = interp.apply(Mathf.clamp(t/animTime));

        color(Mathf.clamp(progress/0.9f));
        color(progress);
        renderBackground(gui);
        push();

        super.render(gui, mouseX, mouseY, partialTicks);
        this.xOffset = Mth.lerp(0.15f, this.xOffset, this.targetXOffset);
        this.yOffset = Mth.lerp(0.15f, this.yOffset, this.targetYOffset);
        this.zoom = Mth.lerp(0.15f, this.zoom, this.targetZoom);
        this.sidebarScroll = Mth.lerp(0.15f, this.sidebarScroll, this.targetSidebarScroll);


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
        String zoomText = "\uD83D\uDD0D " + String.format(Locale.US, "%.1fx", this.targetZoom);
        var font = Minecraft.getInstance().font;
        int textX = scaleX + scaleWidth + 10;
        int textY = scaleY + 3;

        pop();

        push();
        layer(600); // shadows
        gui.blit(FRAME, guiLeft(), guiTop(), 0, 0, frameWidth, frameHeight, 512, 512);
        layer(0);
        pop();

        push();
            renderBackground(gui, mouseX, mouseY);
            layer(601); // home button
            if(isHover(mouseX, mouseY, (int)(this.cx() - 10), guiTop() + this.frameHeight - 15, 20, 20)){
                gui.blit(FRAME, (int)(cx() - 5 - 1), guiTop() + frameHeight - 10 - 1, 10, 191, 12, 12, 512, 512);
            }else{
                gui.blit(FRAME, (int)(cx() - 5), guiTop() + frameHeight - 10, 0, 192, 10, 10, 512, 512);
            }

            gui.fill(textX - 3, textY - 3, textX + font.width(zoomText) + 3, textY + font.lineHeight + 3, Col.packColor(150, 0, 0, 0));
            gui.drawString(font, zoomText, textX, textY, 0xFFFFFF, false);
        pop();

        for(CodexEntry entry : CodexEntries.entries) {
            entry.renderTooltipPost(this, gui, getuOffset(), getvOffset(), guiLeft() + 8, guiTop() + 10);
            if(Minecraft.getInstance().options.renderDebug) {
                entry.renderDebug(gui, mouseX - 160, mouseY + 15);
            }
        }


        renderSidebar(gui, mouseX, mouseY);
    }

    private void renderSidebar(GuiGraphics gui, int mouseX, int mouseY){
        push();
        if (isSidebarDisabled()) {
            return;
        }

        gui.fill(0, 0, listWidth + 25, screen.getRectangle().height(), Col.packColor(75, 0, 0, 0));

        // Sidebar scrollbar logic
        int totalContentHeight = CodexEntries.sidebarEntries.size * 18;
        if (totalContentHeight > listHeight()) {
            int barX = listStartX() + listWidth;
            int barY = listStartY() + 1;
            int barWidth = 2;
            gui.fill(barX, barY + 20, barX + barWidth, barY + listHeight(), Col.packColor(75, 0, 0, 0));
            int knobHeight = Math.max(10, (int)(((float)listHeight() / totalContentHeight) * 25));
            int knobY = listStartY() + 20 + (int)((sidebarScroll / (totalContentHeight - listHeight())) * (listHeight() - knobHeight - 20));

            gui.fill(barX, knobY, barX + barWidth, knobY + knobHeight, Col.packColor(255, 125, 125, 125));
        }

        gui.enableScissor(listStartX(), listStartY(), listStartX() + 200, listStartY() + listHeight());
        int currentY = listStartY() - (int)sidebarScroll + 20;
        for (SidebarEntry sEntry : CodexEntries.sidebarEntries) {
            sEntry.render(this, listStartX(), currentY, gui, mouseX, mouseY);
            currentY += sEntry.height + 3;
        }

        gui.disableScissor();

        float percentage = CodexEntries.openedEntries.size > 0 ? ((float)CodexEntries.openedEntries.size / CodexEntries.entries.size) * 100f : 0;
        String progressText = String.format(Locale.US, "%.0f%%", percentage);
        gui.drawCenteredString(Minecraft.getInstance().font, progressText + " | " + CodexEntries.openedEntries.size + "/" + CodexEntries.entries.size, (listStartX() + 15 / 2) + 85, listStartY() + listHeight() + 15, 0xFFFFFF);
        pop();

        searchBar.setX((listStartX() + 15 / 2) - 5);
        searchBar.setY(listStartY() + listHeight() + 25 + searchBar.getHeight());
        searchBar.render(gui, mouseX, mouseY, Minecraft.getInstance().getPartialTick());
        for(SidebarEntry entry : CodexEntries.sidebarEntries) {
            entry.renderTooltip(this, gui, mouseX, mouseY);
        }
    }

    public void focusOn(CodexEntry entry) {
        this.focusedOn = entry;
        this.targetZoom = 1.0f;
        this.targetXOffset = (12f - entry.x) / xModifier;
        this.targetYOffset = (12f - entry.y) / yModifier;
    }

    @Override
    public void tick(){
        super.tick();
        if (isSidebarDisabled()) {
            return;
        }

        this.searchBar.tick();
    }

    private @NotNull String getSearchValue(){
        return this.searchBar != null ? this.searchBar.getValue() : "";
    }

    @Override
    public boolean charTyped(char pCodePoint, int pModifiers){
        if(this.searchBar != null && this.searchBar.isFocused()){
            if(this.searchBar.charTyped(pCodePoint, pModifiers)){
                sound(() -> SoundEvents.BAMBOO_HIT, 0.25f, 1);
                CodexEntries.rebuildSidebar(getSearchValue());
                return true;
            }
        }

        return super.charTyped(pCodePoint, pModifiers);
    }

    @Override
    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers){
        if(this.searchBar.keyPressed(pKeyCode, pScanCode, pModifiers)){
            sound(() -> SoundEvents.BAMBOO_HIT, 0.25f, 1);
            CodexEntries.rebuildSidebar(getSearchValue());
            return true;
        }else{
            return (this.searchBar != null && this.searchBar.isFocused() && this.searchBar.isVisible() && pKeyCode != 256) || super.keyPressed(pKeyCode, pScanCode, pModifiers);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button){
        double unzoomedX = getUnzoomedMouseX(mouseX);
        double unzoomedY = getUnzoomedMouseY(mouseY);
        searchBar.setFocused(false);
        if (!isSidebarDisabled() && searchBar.mouseClicked(mouseX, mouseY, button)) {
            searchBar.setFocused(true);
            searchBar.onClick(mouseX, mouseY);
            return true;
        }

        if(button == GLFW.GLFW_MOUSE_BUTTON_LEFT){
            for(CodexEntry entry : CodexEntries.entries){
                entry.onClick(this, unzoomedX, unzoomedY);
            }

            if(!isSidebarDisabled()){
                for(SidebarEntry entry : CodexEntries.sidebarEntries){
                    entry.onClick(this, mouseX, mouseY);
                }
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

    private boolean isSidebarDisabled(){
        return listWidth + 25 >= this.guiLeft();
    }

    public boolean isOnSidebar(double mouseX, double mouseY) {
        return mouseX >= listStartX() && mouseY >= listStartY() && mouseX <= (listStartX() + 200) && mouseY <= (listStartY() + listHeight());
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
            float baseSens = ClientConfig.CODEX_SENSITIVITY.get();
            float accelerationFactor = 0.035f;
            float maxMultiplier = 5.0f;
            double mouseSpeed = Math.sqrt(dragX * dragX + dragY * dragY);
            float multiplier = (float) (baseSens + (mouseSpeed * accelerationFactor));

            multiplier = Math.min(multiplier, maxMultiplier);

            xOffset += (float) (-dragX * multiplier);
            yOffset += (float) (-dragY * multiplier);
            targetXOffset += (float) (-dragX * multiplier);
            targetYOffset += (float) (-dragY * multiplier);
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

    public void renderBackground(GuiGraphics gui, float mouseX, float mouseY) {
        float uOffset = getuOffset();
        float vOffset = getvOffset();
        scissorsOn((int)getCenterX(), (int)getCenterY(), insideWidth, insideHeight);
        float yaw = xOffset * 0.05f;
        float pitch = yOffset * 0.05f;
        renderSkybox(pitch, yaw);

        gui.pose().pushPose();

        float cX = getCenterX();
        float cY = getCenterY();
        gui.pose().translate(cX, cY, 0);
        gui.pose().scale(zoom, zoom, 1f);
        gui.pose().translate(-cX, -cY, 0);

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

    private void renderSkybox(float pitch, float yaw) {
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferbuilder = tesselator.getBuilder();

        float aspect = (float)insideWidth / (float)insideHeight;
        Matrix4f matrix4f = (new Matrix4f()).setPerspective(1.5F, aspect, 0.05F, 10.0F);

        RenderSystem.backupProjectionMatrix();
        RenderSystem.setProjectionMatrix(matrix4f, VertexSorting.DISTANCE_TO_ORIGIN);

        PoseStack posestack = RenderSystem.getModelViewStack();
        posestack.pushPose();
        posestack.setIdentity();
        posestack.mulPose(Axis.XP.rotationDegrees(pitch));
        posestack.mulPose(Axis.YP.rotationDegrees(yaw));
        RenderSystem.applyModelViewMatrix();

        RenderSystem.disableCull();
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);

        for(int i = 0; i < 6; ++i) {
            int r = 255, g = 255, b = 255;
            RenderSystem.setShaderTexture(0, loc("textures/gui/book/skybox/panorama_" + i + ".png"));
            bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
            if (i == 0) {
                bufferbuilder.vertex(-1.0D, -1.0D, 1.0D).uv(0.0F, 0.0F).color(r, g, b, 255).endVertex();
                bufferbuilder.vertex(-1.0D, 1.0D, 1.0D).uv(0.0F, 1.0F).color(r, g, b, 255).endVertex();
                bufferbuilder.vertex(1.0D, 1.0D, 1.0D).uv(1.0F, 1.0F).color(r, g, b, 255).endVertex();
                bufferbuilder.vertex(1.0D, -1.0D, 1.0D).uv(1.0F, 0.0F).color(r, g, b, 255).endVertex();
            } else if (i == 1) {
                bufferbuilder.vertex(1.0D, -1.0D, 1.0D).uv(0.0F, 0.0F).color(r, g, b, 255).endVertex();
                bufferbuilder.vertex(1.0D, 1.0D, 1.0D).uv(0.0F, 1.0F).color(r, g, b, 255).endVertex();
                bufferbuilder.vertex(1.0D, 1.0D, -1.0D).uv(1.0F, 1.0F).color(r, g, b, 255).endVertex();
                bufferbuilder.vertex(1.0D, -1.0D, -1.0D).uv(1.0F, 0.0F).color(r, g, b, 255).endVertex();
            } else if (i == 2) {
                bufferbuilder.vertex(1.0D, -1.0D, -1.0D).uv(0.0F, 0.0F).color(r, g, b, 255).endVertex();
                bufferbuilder.vertex(1.0D, 1.0D, -1.0D).uv(0.0F, 1.0F).color(r, g, b, 255).endVertex();
                bufferbuilder.vertex(-1.0D, 1.0D, -1.0D).uv(1.0F, 1.0F).color(r, g, b, 255).endVertex();
                bufferbuilder.vertex(-1.0D, -1.0D, -1.0D).uv(1.0F, 0.0F).color(r, g, b, 255).endVertex();
            } else if (i == 3) {
                bufferbuilder.vertex(-1.0D, -1.0D, -1.0D).uv(0.0F, 0.0F).color(r, g, b, 255).endVertex();
                bufferbuilder.vertex(-1.0D, 1.0D, -1.0D).uv(0.0F, 1.0F).color(r, g, b, 255).endVertex();
                bufferbuilder.vertex(-1.0D, 1.0D, 1.0D).uv(1.0F, 1.0F).color(r, g, b, 255).endVertex();
                bufferbuilder.vertex(-1.0D, -1.0D, 1.0D).uv(1.0F, 0.0F).color(r, g, b, 255).endVertex();
            } else if (i == 4) {
                bufferbuilder.vertex(-1.0D, 1.0D, 1.0D).uv(0.0F, 0.0F).color(r, g, b, 255).endVertex();
                bufferbuilder.vertex(-1.0D, 1.0D, -1.0D).uv(0.0F, 1.0F).color(r, g, b, 255).endVertex();
                bufferbuilder.vertex(1.0D, 1.0D, -1.0D).uv(1.0F, 1.0F).color(r, g, b, 255).endVertex();
                bufferbuilder.vertex(1.0D, 1.0D, 1.0D).uv(1.0F, 0.0F).color(r, g, b, 255).endVertex();
            } else {
                bufferbuilder.vertex(-1.0D, -1.0D, -1.0D).uv(0.0F, 0.0F).color(r, g, b, 255).endVertex();
                bufferbuilder.vertex(-1.0D, -1.0D, 1.0D).uv(0.0F, 1.0F).color(r, g, b, 255).endVertex();
                bufferbuilder.vertex(1.0D, -1.0D, 1.0D).uv(1.0F, 1.0F).color(r, g, b, 255).endVertex();
                bufferbuilder.vertex(1.0D, -1.0D, -1.0D).uv(1.0F, 0.0F).color(r, g, b, 255).endVertex();
            }

            BufferUploader.drawWithShader(bufferbuilder.end());
        }

        RenderSystem.enableCull();
        RenderSystem.enableDepthTest();
        RenderSystem.depthMask(true);
        RenderSystem.disableBlend();

        posestack.popPose();
        RenderSystem.applyModelViewMatrix();
        RenderSystem.restoreProjectionMatrix();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
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
            entry.render(this, gui, uOffset, vOffset, insideLeft(), insideTop(), mouseX, mouseY);
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
        return (backgroundWidth / 2f - insideWidth) + xOffset * xModifier;
    }

    public float getvOffset(){
        return (backgroundHeight / 2f - insideHeight) + yOffset * yModifier;
    }

    public int listStartX() {
        return Mth.clamp((int)(this.width * 0.02f), 5, 10);
    }

    public int listStartY() {
        return -10;
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
