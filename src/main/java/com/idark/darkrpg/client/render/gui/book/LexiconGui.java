package com.idark.darkrpg.client.render.gui.book;

import com.idark.darkrpg.DarkRPG;
import com.idark.darkrpg.item.ModItems;
import com.idark.darkrpg.util.ColorUtils;
import com.idark.darkrpg.util.RenderUtils;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.Tags;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public class LexiconGui extends Screen {
    public static final ResourceLocation BACKGROUND = new ResourceLocation(DarkRPG.MOD_ID, "textures/gui/book/lexicon.png");
    public static final ResourceLocation TREASURES = new ResourceLocation(DarkRPG.MOD_ID, "textures/gui/book/book_image.png");
    public ItemStack item;
    public LexiconPages pages;

    public LexiconGui(LexiconPages pages) {
        super(Component.translatable("gui.darkrpg.main"));
        this.pages = pages;
    }

    @Override
    public void render(GuiGraphics gui, int mouseX, int mouseY, float partialTicks) {
        renderBackground(gui);
        Minecraft mc = Minecraft.getInstance();
        RenderSystem.setShaderTexture(0, BACKGROUND);
        item = ItemStack.EMPTY;

        this.width = mc.getWindow().getGuiScaledWidth();
        this.height = mc.getWindow().getGuiScaledHeight();
        int guiLeft = (width - 544) / 2, guiTop = (height - 360) / 2;

        // Book
        gui.blit(BACKGROUND, guiLeft, guiTop, 0, 0, 544, 360, 1024, 1024);

        if (mouseX >= guiLeft + 534 && mouseX < guiLeft + 534 + 334 && mouseY >= guiTop + 18 && mouseY < guiTop + 18 + 50) {
            gui.blit(BACKGROUND, guiLeft + 534, guiTop + 18, 0, 436, 334, 100, 1024, 1024);
            drawItem(new ItemStack(ModItems.LEXICON.get()), guiLeft + 545, guiTop + 28);
            renderTooltip(gui, Component.translatable("gui.darkrpg.knowledge"), guiLeft + 575, guiTop + 50);
        } else {
            gui.blit(BACKGROUND, guiLeft + 534, guiTop + 18, 0, 386, 334, 50, 1024, 1024);
            drawItem(new ItemStack(ModItems.LEXICON.get()), guiLeft + 538, guiTop + 28);
        }

        if (mouseX >= guiLeft + 534 && mouseX < guiLeft + 534 + 334 && mouseY >= guiTop + 72 && mouseY < guiTop + 72 + 50) {
            gui.blit(BACKGROUND, guiLeft + 534, guiTop + 72, 0, 436, 334, 100, 1024, 1024);
            renderTooltip(gui, Component.translatable("gui.darkrpg.test"), guiLeft + 575, guiTop + 132);
            drawItem(new ItemStack(ModItems.AMETHYST_GEM.get()), guiLeft + 545, guiTop + 82);
        } else {
            gui.blit(BACKGROUND, guiLeft + 534, guiTop + 72, 0, 386, 334, 50, 1024, 1024);
            drawItem(new ItemStack(ModItems.AMETHYST_GEM.get()), guiLeft + 538, guiTop + 82);
        }

        if (mouseX >= guiLeft + 534 && mouseX < guiLeft + 534 + 334 && mouseY >= guiTop + 126 && mouseY < guiTop + 126 + 50) {
            gui.blit(BACKGROUND, guiLeft + 534, guiTop + 126, 0, 436, 334, 100, 1024, 1024);
            renderTooltip(gui, Component.translatable("gui.darkrpg.test"), guiLeft + 575, guiTop + 186);
            drawItem(new ItemStack(ModItems.GEM_BAG.get()), guiLeft + 545, guiTop + 136);
        } else {
            gui.blit(BACKGROUND, guiLeft + 534, guiTop + 126, 0, 386, 334, 50, 1024, 1024);
            drawItem(new ItemStack(ModItems.GEM_BAG.get()), guiLeft + 538, guiTop + 136);
        }

        if (mouseX >= guiLeft + 534 && mouseX < guiLeft + 534 + 334 && mouseY >= guiTop + 180 && mouseY < guiTop + 180 + 50) {
            gui.blit(BACKGROUND, guiLeft + 534, guiTop + 180, 0, 436, 334, 100, 1024, 1024);
            renderTooltip(gui, Component.translatable("gui.darkrpg.test"), guiLeft + 575, guiTop + 240);
            drawItem(new ItemStack(ModItems.GEM_BAG.get()), guiLeft + 545, guiTop + 190);
        } else {
            gui.blit(BACKGROUND, guiLeft + 534, guiTop + 180, 0, 386, 334, 50, 1024, 1024);
            drawItem(new ItemStack(ModItems.GEM_BAG.get()), guiLeft + 538, guiTop + 190);
        }

        // Category footer
        gui.blit(BACKGROUND, guiLeft + 370, guiTop + 45, 192, 360, 264, 26, 1024, 1024);
        gui.blit(BACKGROUND, guiLeft + 94, guiTop + 45, 192, 360, 264, 26, 1024, 1024);
        switch (pages) {
            case MAIN:
                // Search bar
                gui.blit(BACKGROUND, guiLeft + 40, guiTop + 37, 0, 360, 192, 26, 1024, 1024);

                // Search bar footer
                gui.blit(BACKGROUND, guiLeft + 94, guiTop + 62, 192, 360, 264, 26, 1024, 1024);
                drawWrappingText(gui, "gui.darkrpg.knowledge", guiLeft + 355, guiTop + 37, 240);
                drawWrappingText(gui, "gui.darkrpg.knowledge.access", guiLeft + 282, guiTop + 68, 240);

                // Craft
                CraftEntry slot1 = new CraftEntry(Items.PAPER, 345, 100);
                CraftEntry slot2 = new CraftEntry(Items.BOOK, 381, 100);
                CraftEntry slot3 = new CraftEntry(Items.AIR, 345, 136);
                CraftEntry slot4 = new CraftEntry(Items.AIR, 381, 136);
                CraftEntry result = new CraftEntry(ModItems.LEXICON.get(), 452, 119);

                slot1.render(gui, guiLeft, guiTop, mouseX, mouseY, true);
                slot2.render(gui, guiLeft, guiTop, mouseX, mouseY, true);
                slot3.render(gui, guiLeft, guiTop, mouseX, mouseY, false);
                slot4.render(gui, guiLeft, guiTop, mouseX, mouseY, false);
                result.render(gui, guiLeft, guiTop, mouseX, mouseY, true);
                result.resultArrow(gui, guiLeft, guiTop, 425, 127, mouseX, mouseY, false);
                break;
            case TEST:
                drawWrappingText(gui, "gui.darkrpg.treasures.name", guiLeft + 105, guiTop + 37, 240);
                drawWrappingText(gui, "gui.darkrpg.treasures", guiLeft + 26, guiTop + 68, 240);
                gui.blit(TREASURES, guiLeft + 35, guiTop + 110, 0, 0, 1697 / 8, 786 / 7, 1697 / 8, 786 / 7);
                drawWrappingText(gui, "gui.darkrpg.treasure.gems", guiLeft + 26, guiTop + 225, 238);
                break;
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
            Minecraft mc = Minecraft.getInstance();
            this.width = mc.getWindow().getGuiScaledWidth();
            this.height = mc.getWindow().getGuiScaledHeight();
            int guiLeft = (width - 544) / 2, guiTop = (height - 360) / 2;

            if (mouseX >= guiLeft + 534 && mouseX < guiLeft + 534 + 334 && mouseY >= guiTop + 18 && mouseY < guiTop + 18 + 50) {
                mc.player.playNotifySound(SoundEvents.BOOK_PAGE_TURN, SoundSource.NEUTRAL, 1.0f, 1.0f);
                mc.setScreen(new LexiconGui(LexiconPages.MAIN));
            }

            if (mouseX >= guiLeft + 534 && mouseX < guiLeft + 534 + 334 && mouseY >= guiTop + 72 && mouseY < guiTop + 72 + 50) {
                mc.player.playNotifySound(SoundEvents.BOOK_PAGE_TURN, SoundSource.NEUTRAL, 1.0f, 1.0f);
                mc.setScreen(new LexiconGui(LexiconPages.TEST));
            }

            if (mouseX >= guiLeft + 534 && mouseX < guiLeft + 534 + 334 && mouseY >= guiTop + 126 && mouseY < guiTop + 126 + 50) {
                mc.player.playNotifySound(SoundEvents.BOOK_PAGE_TURN, SoundSource.NEUTRAL, 1.0f, 1.0f);
            }

            if (mouseX >= guiLeft + 534 && mouseX < guiLeft + 534 + 334 && mouseY >= guiTop + 180 && mouseY < guiTop + 180 + 50) {
                mc.player.playNotifySound(SoundEvents.BOOK_PAGE_TURN, SoundSource.NEUTRAL, 1.0f, 1.0f);
            }
        }

        return false;
    }

    public void renderTooltip(GuiGraphics gui, MutableComponent component, int x, int y) {
        gui.renderTooltip(Minecraft.getInstance().font, component, x, y);
    }

    @OnlyIn(Dist.CLIENT)
    public static void drawText(GuiGraphics gui, String text, int x, int y) {
        Font font = Minecraft.getInstance().font;
        gui.drawString(font,  I18n.get(text), x, y, ColorUtils.packColor(255, 220, 200, 180), true);
    }

    @OnlyIn(Dist.CLIENT)
    public static void drawWrappingText(GuiGraphics gui, String text, int x, int y, int wrap) {
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
            }
            else line += s + " ";
        }
        if (!line.isEmpty()) lines.add(line);
        for (int i = 0; i < lines.size(); i ++) {
            drawText(gui, lines.get(i), x, y + i * (font.lineHeight + 1));
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static void drawItem(ItemStack stack, int x, int y) {
        RenderUtils.renderItemModelInGui(stack, x, y, 32, 32, 32);
    }

    @OnlyIn(Dist.CLIENT)
    public static void drawItemWithTooltip(ItemStack stack, int size, int x, int y, GuiGraphics gui, int mouseX, int mouseY, boolean ShowTooltip) {
        RenderUtils.renderItemModelInGui(stack, x, y, size, size, size);
        if (ShowTooltip) {
            gui.renderItemDecorations(Minecraft.getInstance().font, stack, x, y, null);
            if (mouseX >= x && mouseY >= y && mouseX <= x + size && mouseY <= y + size) {
                gui.renderTooltip(Minecraft.getInstance().font, stack, mouseX, mouseY);
            }
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}