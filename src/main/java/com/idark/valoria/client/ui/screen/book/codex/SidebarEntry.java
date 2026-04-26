package com.idark.valoria.client.ui.screen.book.codex;

import com.idark.valoria.registries.*;
import com.idark.valoria.util.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.network.chat.*;
import net.minecraft.util.*;
import pro.komaru.tridot.util.*;

import static com.idark.valoria.Valoria.loc;

public class SidebarEntry{
    public int x;
    public int y;
    public int depth;

    public static int width = 195;
    public static int height = 15;

    public CodexEntry entry;
    public boolean isHidden;

    public SidebarEntry(CodexEntry entry, int depth){
        this.entry = entry;
        this.depth = depth;
    }

    public void render(Codex codex, int renderX, int renderY, GuiGraphics gui, float mouseX, float mouseY) {
        if (isHidden) return;
        this.x = renderX;
        this.y = renderY;
        int indent = this.depth * 6;

        boolean isHovered = mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
        int bgColor = isHovered ? Pal.darkestGray.pack() : CommonColors.BLACK;
        int sideColor = (isHovered || codex.focusedOn != null && codex.focusedOn .equals(entry)) ? CommonColors.WHITE : entry.isViewed() ? CommonColors.GRAY : Col.fromHex("c84545").pack();
        gui.fill(x + 2, y, x + width, y + height, bgColor);

        gui.fill(x, y, x + 2, y + height, sideColor);
        if(entry.isUnlocked()){
            gui.renderFakeItem(entry.node.item.getDefaultInstance(), x + 3 + indent, y - 1);
            gui.drawString(Minecraft.getInstance().font, entry.translate.getString(), x + 25 + indent, y + 4, 0xFFFFFF);
        } else {
            gui.drawString(Minecraft.getInstance().font, "\uD83D\uDD12 " + Component.translatable("commands.valoria.page.unknown").getString(), x + 8 + indent, y + 4, 0xFFFFFF);
        }

        if(entry.isUnlocked() && !entry.isViewed()) {
            gui.pose().pushPose();
            gui.pose().translate(0, 0, 300);
            int iconX = isHovered ? 51 : 42;
            int iconY = isHovered ? 192 : 193;
            int textureSize = isHovered ? 11 : 9;
            gui.blit(loc("textures/gui/book/frame.png"), x + width - (isHovered ? 7 : 6), y - (isHovered ? 3 : 2), iconX, iconY, textureSize, textureSize, 512, 512);
            gui.pose().popPose();
        }

    }

    public void onClick(Codex codex, double mouseX, double mouseY){
        boolean isHovered = mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
        if(!isHidden){
            if(isHovered){
                codex.sound(SoundsRegistry.UI_CODEX_CLICK, 0.5f, 1f);
                codex.focusOn(entry);
            }
        }
    }

    public void renderTooltip(GuiGraphics gui, int x, int y){
        gui.renderTooltip(Minecraft.getInstance().font, Component.translatable(entry.getChapter().titleKey), x, y);
    }
}
