package com.idark.valoria.client.ui.screen.book.lexicon;

import com.idark.valoria.api.unlockable.*;
import com.idark.valoria.client.ui.screen.book.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.item.*;
import net.minecraftforge.api.distmarker.*;

import javax.annotation.*;

import static com.idark.valoria.Valoria.loc;

@OnlyIn(Dist.CLIENT)
public class CodexEntry{
    public final Item item;
    public final Chapter chapter;
    public final MutableComponent translate;
    public Unlockable unlockable;
    public int x;
    public int y;
    public ChapterNode node;

    public CodexEntry(int x, int y, Item item, @Nullable MutableComponent title, Chapter chapter, ChapterNode node, Unlockable pUnlockablePage) {
        this.x = x;
        this.y = y;
        this.item = item;
        this.translate = title;
        this.chapter = chapter;
        this.node = node;
        this.node.entry = this;
        this.unlockable = pUnlockablePage;

        this.y += Codex.getInstance().insideHeight/2 - 66;
    }

    public CodexEntry(int x, int y, Item item, @Nullable MutableComponent title, Chapter chapter, ChapterNode node) {
        this(x,y,item,title,chapter,node,null);
    }

    public void render(Codex codex, GuiGraphics gui, float uOffset, float vOffset, int guiLeft, int guiTop, float mouseX, float mouseY){
        int x = (codex.backgroundWidth - codex.insideWidth) / 2 - (this.x - guiLeft) - (int)uOffset;
        int y = (codex.backgroundHeight - codex.insideHeight) / 2 - (this.y - guiTop) - (int)vOffset;
        int entryWidth = 22;
        int entryHeight = 22;
        if (isUnlocked()) {
            if(isHover(mouseX, mouseY, x, y) && codex.isOnScreen(mouseX, mouseY)) {
                gui.blit(loc("textures/gui/book/entry.png"), x, y, 22, 0, entryWidth, entryHeight, 64, 32);

                int textWidth = Minecraft.getInstance().font.width(translate);
                int tooltipX = x + entryWidth - (textWidth / 2) - 22;
                int tooltipY = y + entryHeight + 18;
                renderTooltip(gui, translate, tooltipX, tooltipY);
            } else {
                gui.blit(loc("textures/gui/book/entry.png"), x, y, 0, 0, entryWidth, entryHeight, 64, 32);
            }

            gui.pose().pushPose();
            gui.pose().translate(0,0,codex.entries - 254);
            gui.renderItem(item.getDefaultInstance(), x + 3, y + 3);
            gui.pose().popPose();
        }
    }

    public Chapter getChapter(){
        return chapter;
    }

    public boolean isHover(double mouseX, double mouseY, int x, int y) {
        return mouseX >= x && mouseX <= x + 24 && mouseY >= y && mouseY <= y + 24;
    }

    public void renderTooltip(GuiGraphics gui, MutableComponent component, int x, int y){
        gui.renderTooltip(Minecraft.getInstance().font, component, x, y);
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isUnlocked(){
        if(unlockable == null){
            return true;
        }else{
            return (UnlockUtils.isUnlocked(Minecraft.getInstance().player, unlockable));
        }
    }
}
