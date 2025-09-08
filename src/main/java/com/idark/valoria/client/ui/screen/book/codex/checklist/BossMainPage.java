package com.idark.valoria.client.ui.screen.book.codex.checklist;

import com.idark.valoria.client.ui.screen.book.*;
import com.idark.valoria.client.ui.screen.book.pages.*;
import com.idark.valoria.util.*;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.util.*;
import net.minecraft.world.item.*;
import net.minecraftforge.api.distmarker.*;
import org.jetbrains.annotations.*;
import pro.komaru.tridot.util.*;
import pro.komaru.tridot.util.phys.*;
import pro.komaru.tridot.util.struct.data.*;

import java.util.*;

import static com.idark.valoria.Valoria.loc;

public class BossMainPage extends TextPage{
    Seq<BossEntry> entries;

    public int checkboxSize = 11;
    public int yMargin = 15;
    public int namespaceOffset = 11;
    public int insideWidth = 300;
    public int insideHeight = 128;

    public int guiX;
    public int guiY;
    public int checkboxX;

    public float scrollOffset = 0;
    public final float scrollStep = 20;
    private boolean isDraggingScrollbar = false;
    private double dragStartMouseY;
    private float dragStartScrollOffset;
    private int scrollbarX;
    private int scrollbarY;
    private int scrollbarHeight;

    public BossMainPage(String textKey, Seq<BossEntry> entrySeq) {
        super(textKey);
        this.title = "valoria.book.boss_checklist.title";
        this.entries = entrySeq;
    }

    public void scissorsOn(GuiGraphics gui, PoseStack pose, int x, int y, int w, int h) {
        AbsRect r = AbsRect.xywhDef((float)x, (float)y, (float)w, (float)h).pose(pose);
        gui.enableScissor((int)r.x, (int)r.y, (int)r.x2, (int)r.y2);
    }

    public void scissorsOff(GuiGraphics gui) {
        gui.disableScissor();
    }

    @Override
    public void mouseScrolled(double mouseX, double mouseY, double delta) {
        scrollOffset -= (float)(delta * scrollStep);
        float maxOffset = Math.max(0, entries.size * yMargin - insideHeight);
        scrollOffset = Mth.clamp(scrollOffset, 0, maxOffset);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(GuiGraphics gui, int x, int y, int mouseX, int mouseY) {
        this.guiX = x;
        this.guiY = y;
        renderScroll(gui, x, y, mouseY);

        int yOffset = 25 - (int) scrollOffset;
        renderEntries(gui, x, y, mouseX, mouseY, yOffset, yMargin);
    }

    public void renderScroll(GuiGraphics gui, int x, int y, int mouseY){
        int contentHeight = entries.size * yMargin;
        int maxOffset = Math.max(0, contentHeight - insideHeight);
        if (isDraggingScrollbar) {
            double dragDeltaY = mouseY - dragStartMouseY;
            float scrollRatio = (float) maxOffset / (insideHeight - scrollbarHeight);
            scrollOffset = Mth.clamp(dragStartScrollOffset + (float) (dragDeltaY * scrollRatio), 0, maxOffset);
        }

        scrollOffset = Mth.clamp(scrollOffset, 0, maxOffset);
        scrollbarX = x + 112 + 11;
        int scrollBarYOffset = 10;
        if (contentHeight > insideHeight) {
            float ratio = (float) insideHeight / (float) contentHeight;
            scrollbarHeight = (int) (ratio * insideHeight);
            scrollbarHeight = Math.max(20, scrollbarHeight);
            float scrollbarTrackHeight = insideHeight - scrollbarHeight;
            scrollbarY = y + (int) ((scrollOffset / (float) maxOffset) * scrollbarTrackHeight);

            gui.fill(scrollbarX, scrollBarYOffset + y, scrollbarX + 4, scrollBarYOffset + y + insideHeight, Col.fromHex("413838").a(0.35f).rgb());
            gui.fill(scrollbarX, scrollBarYOffset + scrollbarY, scrollbarX + 4, scrollBarYOffset + scrollbarY + scrollbarHeight, Col.fromHex("413838").brighter().a(0.345f).rgb());
        } else {
            scrollbarHeight = 0;
        }
    }

    public void renderEntries(GuiGraphics gui, int x, int y, int mouseX, int mouseY, int yOffset, int yMargin){
        checkboxX = x + 5;
        entries.sort(Comparator.comparing(BossEntry::getCategory).thenComparing(e -> e.name(e.type()).getString()));
        scissorsOn(gui, gui.pose(), x+insideWidth/2, y +150/2, insideWidth, insideHeight); // perfectly fits needed area
        int currentY = y + yOffset - 10;
        String lastCategory = null;
        for(BossEntry entry : entries){
            if(!entry.getCategory().equals(lastCategory)){
                gui.drawString(Minecraft.getInstance().font, entry.getCategory().toUpperCase(), checkboxX, currentY, -1, true);
                currentY += namespaceOffset;
                lastCategory = entry.getCategory();
            }

            if(currentY + yMargin > y + 21 && currentY < y + 21 + insideHeight){
                gui.blit(loc("textures/gui/book/frame.png"), checkboxX, currentY, entry.isUnlocked() ? 400 : 411, 0, checkboxSize, checkboxSize, 512, 512);
                if(isHover(mouseX, mouseY, checkboxX + 15, currentY, 100, 11)){
                    EntityPos result = getEntityPos(entry);
                    renderEntity(entry.isUnlocked(), gui, entry.type, x + result.entityDrawX(),y + result.entityDrawY(), (int)result.scale(), Minecraft.getInstance(), 13);

                    gui.renderTooltip(Minecraft.getInstance().font, entry.name(entry.type()), mouseX, mouseY);
                    gui.drawString(Minecraft.getInstance().font, entry.name(entry.type()), checkboxX + 15, currentY + checkboxSize / 2 - Minecraft.getInstance().font.lineHeight / 2, Pal.ecru.copy().brighter().rgb(), true);
                }else{
                    gui.drawString(Minecraft.getInstance().font, entry.name(entry.type()), checkboxX + 15, currentY + checkboxSize / 2 - Minecraft.getInstance().font.lineHeight / 2, Pal.ecru.rgb(), true);
                }
            }

            currentY += yMargin;
        }

        scissorsOff(gui);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            if (scrollbarHeight > 0 && isHover(mouseX, mouseY, scrollbarX, guiY, 4, insideHeight)) {
                isDraggingScrollbar = true;
                dragStartMouseY = mouseY;
                dragStartScrollOffset = scrollOffset;
                if (!isHover(mouseX, mouseY, scrollbarX, scrollbarY, 4, scrollbarHeight)) {
                    float clickRatio = (float) (mouseY - guiY) / (float) insideHeight;
                    int maxOffset = Math.max(0, entries.size * yMargin - insideHeight);
                    scrollOffset = Mth.clamp(clickRatio * maxOffset, 0, maxOffset);
                }
                return true;
            }

            int currentY = guiY + 21 - (int) scrollOffset - 10;
            entries.sort(Comparator.comparing(BossEntry::getCategory).thenComparing(e -> e.name(e.type()).getString()));
            String lastCategory = null;
            for (BossEntry entry : entries) {
                if(!entry.getCategory().equals(lastCategory)){
                    currentY += namespaceOffset;
                    lastCategory = entry.getCategory();
                }

                if (currentY + yMargin > guiY && currentY < guiY + insideHeight) {
                    if (isHover(mouseX, mouseY, checkboxX, currentY, checkboxSize, checkboxSize)) {
                        entry.setUnlocked(!entry.isUnlocked());
                        return true;
                    }

                    if(isHover(mouseX, mouseY, checkboxX + 15, currentY, 100, 11)) {
                        Item item = Items.AIR;
                        if(entry.summonItem != null) item = entry.summonItem;
                        EntityPos result = getEntityPos(entry);
                        changeChapter(new Chapter("", new BossPage(entry.description(entry.type).getString()).withCraftEntry(new ItemStack(item)).setEntityData(result.entityDrawX(), result.entityDrawY(), (int)result.scale(), entry.isUnlocked()).withEntity(entry.type).hideTitle()));
                    }
                }

                currentY += yMargin;
            }
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    private static @NotNull EntityPos getEntityPos(BossEntry entry){
        float width = entry.type.getWidth();
        float height = entry.type.getHeight();
        float maxDimension = Math.max(width, height);
        float scale = maxDimension > 0 ? 120.0f / maxDimension : 1.0f;

        int entityDrawX = 215 - (int)((width * scale) / 2f);
        int entityDrawY = 60 + (int)((height * scale) / 2f);
        return new EntityPos(scale, entityDrawX, entityDrawY);
    }

    private record EntityPos(float scale, int entityDrawX, int entityDrawY){
    }

    public void changeChapter(Chapter chapter) {
        Minecraft.getInstance().setScreen(new BookGui(chapter, false));
    }

    public boolean isHover(double mouseX, double mouseY, int x, int y, int width, int height) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (button == 0) isDraggingScrollbar = false;
        return super.mouseReleased(mouseX, mouseY, button);
    }
}