package com.idark.valoria.client.ui.screen.book.codex;

import com.idark.valoria.registries.*;
import com.idark.valoria.util.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.network.chat.*;
import net.minecraft.util.*;
import pro.komaru.tridot.util.*;

import java.util.*;

import static com.idark.valoria.Valoria.loc;

public class SidebarEntry{
    public int x;
    public int y;
    public int depth;

    public int width = 190;
    public int height = 15;

    public CodexEntry entry;
    public boolean isHidden;
    public boolean isHoveredThisFrame = false;

    private final static int packedSide = Col.fromHex("c84545").pack();
    private final static int packedBackground = Pal.darkestGray.pack();

    public SidebarEntry(CodexEntry entry, int depth){
        this.entry = entry;
        this.depth = depth;
    }

    public void render(Codex codex, int renderX, int renderY, GuiGraphics gui, float mouseX, float mouseY) {
        if (isHidden) return;
        boolean flag = entry.isUnlocked() && entry.isViewed();
        this.x = renderX;
        this.y = renderY;
        int indent = this.depth * 8;

        this.isHoveredThisFrame = mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
        int baseShade = Math.max(0x06, 0x22 - depth * 0x07);
        int bgShade = isHoveredThisFrame ? Math.min(0xFF, baseShade + 0x14) : baseShade;
        int bgColor = (0xFF << 24) | (bgShade << 16) | (bgShade << 8) | Math.min(0xFF, bgShade + 3);
        int sideColor = (isHoveredThisFrame || codex.focusedOn != null && codex.focusedOn.equals(entry)) ? CommonColors.WHITE : flag ? CommonColors.GRAY : packedSide;
        gui.fill(x + 2, y, x + width, y + height, bgColor);
        gui.fill(x, y, x + 2, y + height, sideColor);
        if (!entry.node.children.isEmpty()) {
            String arrow = entry.node.isCollapsed ? "▶" : "▼";
            gui.drawString(Minecraft.getInstance().font, arrow, x + width - 15, y + 4, CommonColors.WHITE);
        }

        if(entry.isUnlocked()){
            gui.drawString(Minecraft.getInstance().font, "◆", x + 8 + indent, y + 4, 0xFFFFFF);
            gui.drawString(Minecraft.getInstance().font, entry.translate.getString(), x + 25 + indent, y + 4, CommonColors.GRAY);
        } else {
            gui.drawString(Minecraft.getInstance().font, "◇", x + 8 + indent, y + 4, 0xFFFFFF);
            gui.drawString(Minecraft.getInstance().font, Component.translatable("commands.valoria.page.unknown").getString(), x + 25 + indent, y + 4, CommonColors.GRAY);
        }

        if(entry.isUnlocked() && !entry.isViewed()) {
            gui.pose().pushPose();
            gui.pose().translate(0, 0, 300);
            int iconX = isHoveredThisFrame ? 51 : 42;
            int iconY = isHoveredThisFrame ? 192 : 193;
            int textureSize = isHoveredThisFrame ? 11 : 9;
            gui.blit(loc("textures/gui/book/frame.png"), x + width - (isHoveredThisFrame ? 7 : 6), y - (isHoveredThisFrame ? 3 : 2), iconX, iconY, textureSize, textureSize, 512, 512);
            gui.pose().popPose();
        }
    }

    public void onClick(Codex codex, double mouseX, double mouseY){
        boolean isHovered = mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
        if(!isHidden && codex.isOnSidebar(mouseX, mouseY)){
            if(isHovered){
                // If clicked on the right side (arrow area)
                if (mouseX >= x + width - 25 && !entry.node.children.isEmpty()) {
                    entry.node.isCollapsed = !entry.node.isCollapsed;
                    CodexEntries.rebuildSidebar();
                    codex.sound(SoundsRegistry.UI_CODEX_CLICK, 0.4f, 1.2f);
                } else {
                    codex.sound(SoundsRegistry.UI_CODEX_CLICK, 0.5f, 1f);
                    codex.focusOn(entry);
                }
            }
        }
    }

    public void renderTooltip(Codex codex, GuiGraphics gui, int x, int y){
        if (!this.isHoveredThisFrame || this.isHidden || !codex.isOnSidebar(x, y)) return;
        float scaledIconSize = 22 * codex.zoom;
        MutableComponent transl = entry.isUnlocked() ? entry.translate : entry.unknownTranslate;
        List<Component> tooltipLines = entry.getTooltipLines(transl);

        int maxLineWidth = 0;
        for (Component comp : tooltipLines) {
            maxLineWidth = Math.max(maxLineWidth, Minecraft.getInstance().font.width(comp));
        }

        int tooltipX = (int) (x - 15 + (scaledIconSize / 2f) - (maxLineWidth / 2f));
        int tooltipY = (int) ((y + 5) + scaledIconSize);
        entry.renderTooltip(gui, tooltipLines, tooltipX, tooltipY);
    }
}
