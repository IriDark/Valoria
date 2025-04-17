package com.idark.valoria.client.ui.screen.book.lexicon;

import com.idark.valoria.api.unlockable.*;
import com.idark.valoria.client.ui.screen.book.*;
import com.idark.valoria.core.network.*;
import com.idark.valoria.core.network.packets.*;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.network.chat.*;
import net.minecraft.sounds.*;
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
    public MutableComponent translate;

    public CodexEntry(ChapterNode node, int x, int y) {
        this.node = node;
        this.node.entry = this;
        this.translate = Component.translatable(node.chapter.titleKey);
        this.x = x;
        this.y = y;

        this.y += Codex.getInstance().insideHeight/2 - 66;
    }

    public void hide() {
        isHidden = true;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public void render(Codex codex, GuiGraphics gui, float uOffset, float vOffset, int guiLeft, int guiTop, float mouseX, float mouseY){
        int x = (codex.backgroundWidth - codex.insideWidth) / 2 - (this.x - guiLeft) - (int)uOffset;
        int y = (codex.backgroundHeight - codex.insideHeight) / 2 - (this.y - guiTop) - (int)vOffset;
        int entryWidth = 22;
        int entryHeight = 22;
        var loc = loc("textures/gui/book/frame.png");

        int stx = isUnlocked() ? node.style.x : Style.CLOSED.x;
        int sty = isUnlocked() ? node.style.y : Style.CLOSED.y;
        int sthx = isUnlocked() ? node.style.hoverX : Style.CLOSED.hoverX;
        int sthy = isUnlocked() ? node.style.hoverY : Style.CLOSED.hoverY;
        if(isHover(mouseX, mouseY, x - 12, y - 8) && codex.isOnScreen(mouseX, mouseY)){
            gui.blit(loc, x, y, sthx, sthy, entryWidth, entryHeight, 512, 512);

            var transl = isUnlocked() ? translate : Component.translatable("commands.valoria.page.unknown");
            int textWidth = Minecraft.getInstance().font.width(transl);
            int tooltipX = x + entryWidth - (textWidth / 2) - 22;
            int tooltipY = y + entryHeight + 18;
            renderTooltip(gui, transl, tooltipX, tooltipY);
        }else{
            gui.blit(loc, x, y, stx, sty, entryWidth, entryHeight, 512, 512);
        }

        if(isUnlocked()){
            gui.pose().pushPose();
            gui.pose().translate(0, 0, codex.entries - 250);
            gui.renderItem(node.item.getDefaultInstance(), x + 3, y + 3);
            gui.pose().popPose();
        }
    }

    public void onClick(Codex codex, double mouseX, double mouseY, int uOffset, int vOffset){
        int x = (codex.backgroundWidth - codex.insideWidth) / 2 - (this.x - codex.guiLeft()) - uOffset;
        int y = (codex.backgroundHeight - codex.insideHeight) / 2 - (this.y - codex.guiTop()) - vOffset;
        if(codex.isOnScreen(mouseX, mouseY)){
            if(isHover(mouseX, mouseY, x - 6, y)){
                if(isUnlocked()){
                    var unlockable = node.unlockable;
                    if(unlockable != null && unlockable.hasAwards() && !UnlockUtils.isClaimed(codex.player, unlockable) && !Codex.onClaim(this, unlockable)){
                        PacketHandler.sendToServer(new UnlockCodexPacket(unlockable.getId()));
                    } else {
                        codex.sound(() -> SoundEvents.BOOK_PAGE_TURN, 1, 1);
                        codex.changeChapter(getChapter());
                    }

                } else {
                    codex.sound(() -> SoundEvents.CHAIN_HIT, 1, 1);
                }
            }
        }

        if(codex.isHover(mouseX, mouseY, (int)(codex.cx()-10), codex.guiTop() + codex.frameHeight - 15, 20, 20)) {
            codex.xOffset = 0;
            codex.yOffset = 0;
            codex.sound(() -> SoundEvents.LEVER_CLICK, 1, 1);
        }
    }

    public Chapter getChapter(){
        return node.chapter;
    }

    public boolean isHover(double mouseX, double mouseY, int x, int y) {
        return mouseX >= x + 8 && mouseX <= x + 8 + 28 && mouseY >= y + 8 && mouseY <= y + 8 + 28;
    }

    public void renderTooltip(GuiGraphics gui, MutableComponent component, int x, int y){
        PoseStack pose = gui.pose();
        List<Component> lines = new ArrayList<>();
        lines.add(component);

        Player player = Minecraft.getInstance().player;
        boolean flag = player != null && node.unlockable != null && node.unlockable.hasAwards() && !UnlockUtils.isClaimed(player, node.unlockable);
        if(isUnlocked()){
            lines.addAll(node.description);
            if(flag) {
                lines.add(Component.literal("Rewards available").withStyle(ChatFormatting.GOLD));
            }

        } else {
            lines.addAll(node.hints);
            if(flag){
                lines.add(Component.translatable("codex.valoria.rewards").withStyle(ChatFormatting.GOLD));
            }
        }

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
    public boolean isUnlocked(){
        if(node.unlockable == null){
            return true;
        }else{
            return (UnlockUtils.isUnlocked(Minecraft.getInstance().player, node.unlockable));
        }
    }
}
