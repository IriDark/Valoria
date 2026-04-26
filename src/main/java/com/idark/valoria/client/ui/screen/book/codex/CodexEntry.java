package com.idark.valoria.client.ui.screen.book.codex;

import com.idark.valoria.api.unlockable.*;
import com.idark.valoria.client.ui.screen.book.*;
import com.idark.valoria.core.config.*;
import com.idark.valoria.core.network.*;
import com.idark.valoria.core.network.packets.*;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.network.chat.*;
import net.minecraft.sounds.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraftforge.api.distmarker.*;

import java.util.*;

import static com.idark.valoria.Valoria.loc;

@OnlyIn(Dist.CLIENT)
public class CodexEntry{
    public int x;
    public int y;
    public ChapterNode node;
    public boolean isHidden;
    public boolean isRendered;
    public MutableComponent translate;
    public MutableComponent unknownTranslate;

    public CodexEntry(ChapterNode node, int x, int y){
        this.node = node;
        this.node.entry = this;
        this.translate = Component.translatable(node.chapter.titleKey);
        this.unknownTranslate = Component.translatable(node.chapter.unknownKey);
        this.x = x;
        this.y = y;

        this.y += Codex.getInstance().insideHeight/2 - 66;
    }

    private float hoverProgress = 0;

    public void hide() {
        isHidden = true;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public boolean isHoveredThisFrame = false;
    public void render(Codex codex, GuiGraphics gui, float uOffset, float vOffset, int guiLeft, int guiTop, float mouseX, float mouseY){
        this.isRendered = true;
        int x = (codex.backgroundWidth - codex.insideWidth) / 2 - (this.x - guiLeft) - (int)uOffset;
        int y = (codex.backgroundHeight - codex.insideHeight) / 2 - (this.y - guiTop) - (int)vOffset;
        double realMouseX = (mouseX - codex.getCenterX()) * codex.zoom + codex.getCenterX();
        double realMouseY = (mouseY - codex.getCenterY()) * codex.zoom + codex.getCenterY();

        var loc = loc("textures/gui/book/frame.png");
        boolean isInsideBook = codex.isOnScreen(realMouseX, realMouseY);
        boolean isHoveringNode = codex.isHover(mouseX, mouseY, x, y, 20, 20);
        this.isHoveredThisFrame = isInsideBook && isHoveringNode;

        hoverProgress = Mth.lerp(0.15f, hoverProgress, isHoveredThisFrame ? 1f : 0f);

        int entryWidth = 22;
        int entryHeight = 22;

        gui.pose().pushPose();
        float scale = 1.0f + (hoverProgress * 0.25f);
        gui.pose().translate(x + 11, y + 11, 0);
        gui.pose().scale(scale, scale, 1.0f);
        gui.pose().translate(-11, -11, 0);

        int stx = isUnlocked() ? node.style.x : Style.CLOSED.x;
        int sty = isUnlocked() ? node.style.y : Style.CLOSED.y;
        int sthx = isUnlocked() ? node.style.hoverX : Style.CLOSED.hoverX;
        int sthy = isUnlocked() ? node.style.hoverY : Style.CLOSED.hoverY;

        if(isHoveredThisFrame || hoverProgress > 0.1f){
            gui.blit(loc, 0, 0, sthx, sthy, entryWidth, entryHeight, 512, 512);
        }else{
            gui.blit(loc, 0, 0, stx, sty, entryWidth, entryHeight, 512, 512);
        }

        int pX = isHoveredThisFrame ? 13 : 14;
        int pY = isHoveredThisFrame ? -4 : -3;
        if(isUnlocked()){
            gui.pose().pushPose();
            gui.pose().translate(0, 0, codex.entriesLayer - 100);
            gui.renderFakeItem(node.item.getDefaultInstance(), 3, 3);
            gui.pose().popPose();
            if(!isViewed()) {
                gui.pose().pushPose();
                gui.pose().translate(0, 0, 300);
                int iconX = isHoveredThisFrame ? 51 : 42;
                int iconY = isHoveredThisFrame ? 192 : 193;
                int textureSize = isHoveredThisFrame ? 11 : 9;
                gui.blit(loc, pX, pY, iconX, iconY, textureSize, textureSize, 512, 512);
                gui.pose().popPose();
            }
        } else {
            if(!node.hints.isEmpty()) {
                gui.pose().pushPose();
                gui.pose().translate(0, 0, 300);
                int iconX = isHoveredThisFrame ? 31 : 22;
                int iconY = isHoveredThisFrame ? 192 : 193;
                int textureSize = isHoveredThisFrame ? 11 : 9;
                gui.blit(loc, pX, pY, iconX, iconY, textureSize, textureSize, 512, 512);
                gui.pose().popPose();
            }
        }
        gui.pose().popPose();
    }

    public void renderTooltipPost(Codex codex, GuiGraphics gui, float uOffset, float vOffset, int guiLeft, int guiTop) {
        if (!this.isHoveredThisFrame || this.isHidden) return;
        int x = (codex.backgroundWidth - codex.insideWidth) / 2 - (this.x - guiLeft) - (int)uOffset;
        int y = (codex.backgroundHeight - codex.insideHeight) / 2 - (this.y - guiTop) - (int)vOffset;

        float screenX = (x - codex.getCenterX()) * codex.zoom + codex.getCenterX();
        float screenY = (y - codex.getCenterY()) * codex.zoom + codex.getCenterY();
        float scaledIconSize = 22 * codex.zoom;

        MutableComponent transl = isUnlocked() ? translate : unknownTranslate;
        List<Component> tooltipLines = getTooltipLines(transl);

        int maxLineWidth = 0;
        for (Component comp : tooltipLines) {
            maxLineWidth = Math.max(maxLineWidth, Minecraft.getInstance().font.width(comp));
        }

        int tooltipX = (int) (screenX + (scaledIconSize / 2f) - (maxLineWidth / 2f));
        int tooltipY = (int) (screenY + scaledIconSize);

        gui.pose().pushPose();
        gui.pose().translate(-11, 18, 800);
        renderTooltip(gui, tooltipLines, tooltipX, tooltipY);
        gui.pose().popPose();
    }

    private List<Component> getTooltipLines(MutableComponent title) {
        List<Component> lines = new ArrayList<>();
        lines.add(title);

        Player player = Minecraft.getInstance().player;
        boolean flag = player != null && node.unlockable != null && node.unlockable.hasAwards() && !UnlockUtils.isClaimed(player, node.unlockable);

        if (isUnlocked()) {
            lines.addAll(node.description);
            if (flag) {
                lines.add(Component.literal("Rewards available").withStyle(ChatFormatting.GOLD));
            }
        } else {
            lines.addAll(node.hints);
            if (flag) {
                lines.add(Component.translatable("codex.valoria.rewards").withStyle(ChatFormatting.GOLD));
            }

            if (node.unlockable != null && node.unlockable.randomObtainable) {
                lines.add(Component.empty());
                lines.add(Component.translatable("codex.valoria.random_obtainable").withStyle(ChatFormatting.DARK_GRAY));
            }
        }

        return lines;
    }

    public void onClick(Codex codex, double virtualMouseX, double virtualMouseY){
        double realMouseX = (virtualMouseX - codex.getCenterX()) * codex.zoom + codex.getCenterX();
        double realMouseY = (virtualMouseY - codex.getCenterY()) * codex.zoom + codex.getCenterY();
        boolean isInsideBook = codex.isOnScreen(realMouseX, realMouseY);
        if(isInsideBook && !isHidden && isRendered){
            if(isHoveredThisFrame){
                if(isUnlocked() && !this.getChapter().pages.isEmpty()){
                    var unlockable = node.unlockable;
                    if(unlockable != null && unlockable.hasAwards() && !UnlockUtils.isClaimed(codex.player, unlockable) && !Codex.onClaim(this, unlockable)){
                        PacketHandler.sendToServer(new UnlockCodexPacket(unlockable.getId()));
                    }

                    if(unlockable != null) PacketHandler.sendToServer(new ReadCodexPacket(unlockable.getId()));

                    codex.sound(() -> SoundEvents.BOOK_PAGE_TURN, 1, 1);
                    codex.changeChapter(getChapter());
                } else {
                    codex.sound(() -> SoundEvents.CHAIN_HIT, 1, 1);
                }
            }
        }
    }

    public Chapter getChapter(){
        return node.chapter;
    }

    public void renderTooltip(GuiGraphics gui, List<Component> lines, int x, int y){
        PoseStack pose = gui.pose();
        Player player = Minecraft.getInstance().player;
        boolean flag = player != null && node.unlockable != null && node.unlockable.hasAwards() && !UnlockUtils.isClaimed(player, node.unlockable);
        if(flag) {
            boolean loot = node.unlockable.getLoot() != null;
            boolean items = node.unlockable.getItems() != null;
            if(loot || items){
                pose.pushPose();
                pose.translate(0, 0, 300);
                int itemX = x + 18;
                int itemY = y + (lines.size() * 10) - 10;
                if(loot){
                    gui.renderItem(new ItemStack(Items.BUNDLE), itemX, itemY);
                    itemX += 18;
                }

                if(items){
                    lines.add(Component.empty());
                    lines.add(Component.empty());
                    for(ItemStack stack : node.unlockable.getItems()){
                        gui.renderItem(stack, itemX, itemY);
                        gui.renderItemDecorations(Minecraft.getInstance().font, stack, itemX, itemY);
                        itemX += 18;
                    }
                }

                pose.popPose();
            }
        }

        gui.renderComponentTooltip(Minecraft.getInstance().font, lines, x, y);
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isViewed(){
        if(node.unlockable == null){
            return true;
        }else{
            return (UnlockUtils.isViewed(Minecraft.getInstance().player, node.unlockable));
        }
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isUnlocked(){
        if(node.unlockable == null){
            return true;
        }else{
            return !ServerConfig.ENABLE_CODEX_PROGRESSION.get() || (UnlockUtils.isUnlocked(Minecraft.getInstance().player, node.unlockable));
        }
    }
}
