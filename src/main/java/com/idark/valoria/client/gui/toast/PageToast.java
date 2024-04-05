package com.idark.valoria.client.gui.toast;

import com.idark.valoria.Valoria;
import com.idark.valoria.registries.ItemsRegistry;
import com.idark.valoria.util.ValoriaUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastComponent;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class PageToast implements Toast {

    public static PageToast instance;
    public ResourceLocation TEXTURE = new ResourceLocation(Valoria.MOD_ID, "textures/gui/toast.png");
    public boolean pUnlock;

    @Override
    public Visibility render(GuiGraphics pGuiGraphics, ToastComponent pToastComponent, long pTimeSinceLastVisible) {
        pGuiGraphics.blit(TEXTURE, 0, 0, 0, 0, this.width(), this.height(), 256, 32);

        pGuiGraphics.renderItem(ItemsRegistry.CRYPT.get().getDefaultInstance(), 8, 8);
        pGuiGraphics.renderItem(ItemsRegistry.LEXICON.get().getDefaultInstance(), 155, 8);

        if (pUnlock) {
            drawWrappingText(pGuiGraphics, "gui.valoria.new_page", 90, 8, 120, true);
        } else {
            drawWrappingText(pGuiGraphics, "gui.valoria.delete_page", 92, 12, 120, true);
        }

        return (double) pTimeSinceLastVisible >= 5000.0D * pToastComponent.getNotificationDisplayTimeMultiplier() ? Visibility.HIDE : Visibility.SHOW;
    }

    public PageToast(boolean pUnlock) {
        this.pUnlock = pUnlock;
    }

    @Override
    public int width() {
        return 180;
    }

    @OnlyIn(Dist.CLIENT)
    public static void drawText(GuiGraphics gui, String text, int x, int y, boolean Centered) {
        Font font = Minecraft.getInstance().font;
        if (!Centered) {
            gui.drawString(font, I18n.get(text), x, y, ValoriaUtils.color.packColor(255, 220, 200, 180), true);
        } else {
            gui.drawCenteredString(font, I18n.get(text), x, y, ValoriaUtils.color.packColor(255, 220, 200, 180));
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static void drawWrappingText(GuiGraphics gui, String text, int x, int y, int wrap, boolean Centered) {
        Font font = Minecraft.getInstance().font;
        List<String> lines = new ArrayList<>();
        String[] words = I18n.get(text).split(" ");
        String line = "";
        for (String s : words) {
            if (s.equals("\n")) {
                lines.add(line);
                line = "";
            } else if (font.width(line) + font.width(s) > wrap) {
                lines.add(line);
                line = s + " ";
            } else line += s + " ";
        }
        if (!line.isEmpty()) lines.add(line);
        for (int i = 0; i < lines.size(); i++) {
            drawText(gui, lines.get(i), x, y + i * (font.lineHeight + 1), Centered);
        }
    }
}
