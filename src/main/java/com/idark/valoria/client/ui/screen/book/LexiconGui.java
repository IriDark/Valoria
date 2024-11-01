package com.idark.valoria.client.ui.screen.book;

import com.idark.valoria.*;
import com.idark.valoria.client.ui.screen.book.pages.*;
import com.mojang.blaze3d.systems.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.screens.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraft.sounds.*;
import net.minecraft.world.item.*;
import net.minecraftforge.api.distmarker.*;
import org.lwjgl.glfw.*;

@OnlyIn(Dist.CLIENT)
public class LexiconGui extends Screen {
    public static final ResourceLocation BACKGROUND = new ResourceLocation(Valoria.ID, "textures/gui/book/lexicon.png");
    public ItemStack item;
    public static Chapter currentChapter;
    public static int currentPage = 0;

    public LexiconGui() {
        super(Component.translatable("lexicon.valoria.main"));
        if (currentChapter == null) {
            currentChapter = LexiconChapters.MAIN_CHAPTER;
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
        gui.blit(BACKGROUND, guiLeft, guiTop, 0, 0, 272, 180, 512, 512);
        for (Bookmark bookmark : LexiconChapters.categories) {
            if (bookmark.isUnlocked()) {
                bookmark.render(gui, guiLeft, guiTop, mouseX, mouseY);
            }
        }

        Page left = currentChapter.getPage(currentPage), right = currentChapter.getPage(currentPage + 1);
        if (left != null) left.fullRender(gui, guiLeft + 10, guiTop + 8, mouseX, mouseY);
        if (right != null) right.fullRender(gui, guiLeft + 145, guiTop + 8, mouseX, mouseY);
        if (left instanceof TextPage) gui.blit(BACKGROUND, guiLeft + 48, guiTop + 31, 97, 180, 38, 13, 512, 512);
        if (right instanceof TextPage) gui.blit(BACKGROUND, guiLeft + 186, guiTop + 31, 97, 180, 38, 13, 512, 512);
        if (currentChapter.size() >= currentPage + 3) {
            if (mouseX >= guiLeft + 250 && mouseX < guiLeft + 250 + 9 && mouseY >= guiTop + 150 && mouseY < guiTop + 150 + 8) {
                gui.blit(BACKGROUND, guiLeft + 250, guiTop + 150, 272, 104, 9, 8, 512, 512);
                renderTooltip(gui, Component.translatable("lexicon.valoria.next"), guiLeft + 250, guiTop + 150);
            } else {
                gui.blit(BACKGROUND, guiLeft + 250, guiTop + 150, 272, 88, 9, 8, 512, 512);
            }
        }

        if (currentPage > 0) {
            if (mouseX >= guiLeft + 13 && mouseX < guiLeft + 13 + 9 && mouseY >= guiTop + 150 && mouseY < guiTop + 150 + 8) {
                gui.blit(BACKGROUND, guiLeft + 13, guiTop + 150, 272, 96, 9, 8, 512, 512);
                renderTooltip(gui, Component.translatable("lexicon.valoria.back"), guiLeft + 13, guiTop + 150);
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

            for (Bookmark bookmark : LexiconChapters.categories) {
                if (bookmark.isUnlocked() && bookmark.onClick(mouseX, mouseY, guiLeft, guiTop)) {
                    mc.player.playNotifySound(SoundEvents.BOOK_PAGE_TURN, SoundSource.NEUTRAL, 1.0f, 1.0f);
                    changeChapter(bookmark.getChapter());
                }
            }
        }

        return false;
    }

    public void changeChapter(Chapter next) {
        currentChapter = next;
        currentPage = 0;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}