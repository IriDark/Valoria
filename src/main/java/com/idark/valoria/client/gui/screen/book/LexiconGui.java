package com.idark.valoria.client.gui.screen.book;

import com.idark.valoria.Valoria;
import com.idark.valoria.client.gui.screen.book.unlockable.UnlockableBookmark;
import com.idark.valoria.util.ColorUtils;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public class LexiconGui extends Screen {
    public static final ResourceLocation BACKGROUND = new ResourceLocation(Valoria.MOD_ID, "textures/gui/book/lexicon.png");
    public ItemStack item;
    public static Chapter currentChapter;
    public static int currentPage = 0;

    public LexiconGui() {
        super(Component.translatable("gui.valoria.main"));
        if (currentChapter == null) {
            currentChapter = LexiconChapters.MAIN_PAGE;
        }
    }

    @Override
    public void tick() {
        Page left = currentChapter.getPage(currentPage), right = currentChapter.getPage(currentPage + 1);
        if (left != null) left.tick();
        if (right != null) right.tick();
    }

    @Override
    public void render(GuiGraphics gui, int mouseX, int mouseY, float partialTicks) {
        renderBackground(gui);
        Minecraft mc = Minecraft.getInstance();
        RenderSystem.setShaderTexture(0, BACKGROUND);
        item = ItemStack.EMPTY;

        this.width = mc.getWindow().getGuiScaledWidth();
        this.height = mc.getWindow().getGuiScaledHeight();
        int guiLeft = (width - 272) / 2, guiTop = (height - 180) / 2;

        // Book
        gui.blit(BACKGROUND, guiLeft, guiTop, 0, 0, 272, 180, 512, 512);

        // START
        gui.blit(BACKGROUND, guiLeft - 19, guiTop + 132, 272, 132, 17, 48, 512, 512);

        if (mouseX >= guiLeft - 14 && mouseX < guiLeft && mouseY >= guiTop + 138 && mouseY < guiTop + 138 + 7) {
            gui.blit(BACKGROUND, guiLeft - 15, guiTop + 137, 279, 112, 9, 9, 512, 512);
            renderTooltip(gui, Component.translatable("gui.valoria.thanks"), guiLeft - 14, guiTop + 138);
        } else {
            gui.blit(BACKGROUND, guiLeft - 14, guiTop + 138, 272, 113, 7, 7, 512, 512);
        }

        if (mouseX >= guiLeft - 12 && mouseX < guiLeft && mouseY >= guiTop + 148 && mouseY < guiTop + 148 + 6) {
            gui.blit(BACKGROUND, guiLeft - 12, guiTop + 148, 272, 121, 3, 6, 512, 512);
        } else {
            gui.blit(BACKGROUND, guiLeft - 12, guiTop + 148, 275, 121, 3, 6, 512, 512);
        }

        if (mouseX >= guiLeft - 12 && mouseX < guiLeft && mouseY >= guiTop + 158 && mouseY < guiTop + 158 + 6) {
            gui.blit(BACKGROUND, guiLeft - 12, guiTop + 158, 272, 121, 3, 6, 512, 512);
        } else {
            gui.blit(BACKGROUND, guiLeft - 12, guiTop + 158, 275, 121, 3, 6, 512, 512);
        }

        if (mouseX >= guiLeft - 12 && mouseX < guiLeft && mouseY >= guiTop + 168 && mouseY < guiTop + 168 + 6) {
            gui.blit(BACKGROUND, guiLeft - 12, guiTop + 168, 272, 121, 3, 6, 512, 512);
        } else {
            gui.blit(BACKGROUND, guiLeft - 12, guiTop + 168, 275, 121, 3, 6, 512, 512);
        }
        // END

        // Bookmarks
        LexiconChapters.LEXICON.render(gui, guiLeft, guiTop, mouseX, mouseY, true);
        LexiconChapters.TREASURES.render(gui, guiLeft, guiTop, mouseX, mouseY, true);
        LexiconChapters.MEDICINE.render(gui, guiLeft, guiTop, mouseX, mouseY, true);
        if (UnlockableBookmark.unlockable != null && UnlockableBookmark.isUnlocked()) {
            LexiconChapters.CRYPT.render(gui, guiLeft, guiTop, mouseX, mouseY, true);
        }

        // Category footer
        gui.blit(BACKGROUND, guiLeft + 48, guiTop + 31, 97, 180, 38, 13, 512, 512);
        gui.blit(BACKGROUND, guiLeft + 186, guiTop + 31, 97, 180, 38, 13, 512, 512);

        Page left = currentChapter.getPage(currentPage), right = currentChapter.getPage(currentPage + 1);
        if (left != null) left.fullRender(gui, guiLeft + 10, guiTop + 8, mouseX, mouseY);
        if (right != null) right.fullRender(gui, guiLeft + 140, guiTop + 8, mouseX, mouseY);
        if (currentChapter.size() >= currentPage + 3) {
            if (mouseX >= guiLeft + 250 && mouseX < guiLeft + 250 + 9 && mouseY >= guiTop + 150 && mouseY < guiTop + 150 + 8) {
                gui.blit(BACKGROUND, guiLeft + 250, guiTop + 150, 272, 104, 9, 8, 512, 512);
                renderTooltip(gui, Component.translatable("gui.valoria.next"), guiLeft + 250, guiTop + 150);
            } else {
                gui.blit(BACKGROUND, guiLeft + 250, guiTop + 150, 272, 88, 9, 8, 512, 512);
            }
        }

        if (currentPage > 0) {
            if (mouseX >= guiLeft + 13 && mouseX < guiLeft + 13 + 9 && mouseY >= guiTop + 150 && mouseY < guiTop + 150 + 8) {
                gui.blit(BACKGROUND, guiLeft + 13, guiTop + 150, 272, 96, 9, 8, 512, 512);
                renderTooltip(gui, Component.translatable("gui.valoria.back"), guiLeft + 13, guiTop + 150);
            } else {
                gui.blit(BACKGROUND, guiLeft + 13, guiTop + 150, 272, 80, 9, 8, 512, 512);
            }
        }
    }

    public void renderTooltip(GuiGraphics gui, MutableComponent component, int x, int y) {
        gui.renderTooltip(Minecraft.getInstance().font, component, x, y);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
            Minecraft mc = Minecraft.getInstance();
            this.width = mc.getWindow().getGuiScaledWidth();
            this.height = mc.getWindow().getGuiScaledHeight();
            int guiLeft = (width - 272) / 2, guiTop = (height - 180) / 2;

            // START
            if (mouseX >= guiLeft - 14 && mouseX < guiLeft && mouseY >= guiTop + 138 && mouseY < guiTop + 138 + 7) {
                mc.player.playNotifySound(SoundEvents.BOOK_PAGE_TURN, SoundSource.NEUTRAL, 1.0f, 1.0f);
            }
            // END

            if (mouseX >= guiLeft + 267 + 2 && mouseX < guiLeft + 267 + 35 && mouseY >= guiTop + 10 + 4 && mouseY < guiTop + 10 + 25) {
                mc.player.playNotifySound(SoundEvents.BOOK_PAGE_TURN, SoundSource.NEUTRAL, 1.0f, 1.0f);
                changeChapter(LexiconChapters.MAIN_PAGE);
            }

            if (currentChapter.size() >= currentPage + 3) {
                if (mouseX >= guiLeft + 250 && mouseX < guiLeft + 250 + 9 && mouseY >= guiTop + 150 && mouseY < guiTop + 150 + 8) {
                    mc.player.playNotifySound(SoundEvents.BOOK_PAGE_TURN, SoundSource.NEUTRAL, 1.0f, 1.0f);
                    currentPage += 2;
                }
            }

            if (currentPage > 0) {
                if (mouseX >= guiLeft + 13 && mouseX < guiLeft + 13 + 9 && mouseY >= guiTop + 150 && mouseY < guiTop + 150 + 8) {
                    mc.player.playNotifySound(SoundEvents.BOOK_PAGE_TURN, SoundSource.NEUTRAL, 1.0f, 1.0f);
                    currentPage -= 2;
                }
            }

            if (mouseX >= guiLeft + 267 + 2 && mouseX < guiLeft + 267 + 35 && mouseY >= guiTop + 38 + 4 && mouseY < guiTop + 38 + 25) {
                mc.player.playNotifySound(SoundEvents.BOOK_PAGE_TURN, SoundSource.NEUTRAL, 1.0f, 1.0f);
                changeChapter(LexiconChapters.TREASURES_PAGE);
            }

            if (mouseX >= guiLeft + 267 + 2 && mouseX < guiLeft + 267 + 35 && mouseY >= guiTop + 66 + 4 && mouseY < guiTop + 66 + 25) {
                mc.player.playNotifySound(SoundEvents.BOOK_PAGE_TURN, SoundSource.NEUTRAL, 1.0f, 1.0f);
                changeChapter(LexiconChapters.MEDICINE_PAGE);
            }

            if (UnlockableBookmark.unlockable != null && UnlockableBookmark.isUnlocked()) {
                if (mouseX >= guiLeft + 267 + 2 && mouseX < guiLeft + 267 + 35 && mouseY >= guiTop + 94 + 4 && mouseY < guiTop + 94 + 25) {
                    mc.player.playNotifySound(SoundEvents.BOOK_PAGE_TURN, SoundSource.NEUTRAL, 1.0f, 1.0f);
                    changeChapter(LexiconChapters.CRYPT_PAGE);
                }
            }
        }

        return false;
    }

    public void changeChapter(Chapter next) {
        currentChapter = next;
        currentPage = 0;
    }

    @OnlyIn(Dist.CLIENT)
    public static void drawText(GuiGraphics gui, String text, int x, int y, boolean Centered) {
        Font font = Minecraft.getInstance().font;
        if (!Centered) {
            gui.drawString(font, I18n.get(text), x, y, ColorUtils.packColor(255, 220, 200, 180), true);
        } else {
            gui.drawCenteredString(font, I18n.get(text), x, y, ColorUtils.packColor(255, 220, 200, 180));
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

    @OnlyIn(Dist.CLIENT)
    public static void drawItemWithTooltip(ItemStack stack, int x, int y, GuiGraphics gui, int mouseX, int mouseY, boolean ShowTooltip) {
        gui.renderItem(stack, x, y);
        if (ShowTooltip && !stack.isEmpty()) {
            if (mouseX >= x && mouseY >= y && mouseX <= x + 16 && mouseY <= y + 16) {
                gui.renderTooltip(Minecraft.getInstance().font, stack, mouseX, mouseY);
            }
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}