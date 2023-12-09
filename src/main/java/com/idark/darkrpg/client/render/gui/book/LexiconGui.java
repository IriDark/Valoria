package com.idark.darkrpg.client.render.gui.book;

import com.idark.darkrpg.DarkRPG;
import com.idark.darkrpg.item.ModItems;
import com.idark.darkrpg.util.ColorUtils;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.ChatFormatting;
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
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public class LexiconGui extends Screen {
    public static final ResourceLocation BACKGROUND = new ResourceLocation(DarkRPG.MOD_ID, "textures/gui/book/lexicon.png");
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
        int guiLeft = (width - 272) / 2, guiTop = (height - 180) / 2;

        int BookmarkHeight = 25;
        int BookmarkWidth = 35;
        // Book
        gui.blit(BACKGROUND, guiLeft, guiTop, 0, 0, 272, 180, 512, 512);
        gui.blit(BACKGROUND, guiLeft - 19, guiTop + 132, 272, 132, 17, 48, 512, 512);

        if (mouseX >= guiLeft - 14 && mouseX < guiLeft - 14 + BookmarkWidth && mouseY >= guiTop + 138 && mouseY < guiTop + 138 + 7) {
            gui.blit(BACKGROUND, guiLeft - 15, guiTop + 137, 279, 112, 9, 9, 512, 512);
            renderTooltip(gui, Component.translatable("gui.darkrpg.thanks"), guiLeft - 14, guiTop + 138);
        } else {
            gui.blit(BACKGROUND, guiLeft - 14, guiTop + 138, 272, 113, 7, 7, 512, 512);
        }

        if (mouseX >= guiLeft - 12 && mouseX < guiLeft - 12 + BookmarkWidth && mouseY >= guiTop + 148 && mouseY < guiTop + 148 + 6) {
            gui.blit(BACKGROUND, guiLeft - 12, guiTop + 148, 272, 121, 3, 6, 512, 512);
        } else {
            gui.blit(BACKGROUND, guiLeft - 12, guiTop + 148, 275, 121, 3, 6, 512, 512);
        }

        if (mouseX >= guiLeft - 12 && mouseX < guiLeft - 12 + BookmarkWidth && mouseY >= guiTop + 158 && mouseY < guiTop + 158 + 6) {
            gui.blit(BACKGROUND, guiLeft - 12, guiTop + 158, 272, 121, 3, 6, 512, 512);
        } else {
            gui.blit(BACKGROUND, guiLeft - 12, guiTop + 158, 275, 121, 3, 6, 512, 512);
        }

        if (mouseX >= guiLeft - 12 && mouseX < guiLeft - 12 + BookmarkWidth && mouseY >= guiTop + 168 && mouseY < guiTop + 168 + 6) {
            gui.blit(BACKGROUND, guiLeft - 12, guiTop + 168, 272, 121, 3, 6, 512, 512);
        } else {
            gui.blit(BACKGROUND, guiLeft - 12, guiTop + 168, 275, 121, 3, 6, 512, 512);
        }

        // Bookmarks
        boolean b = mouseX >= guiLeft + 267 && mouseX < guiLeft + 267 + BookmarkWidth && mouseY >= guiTop + 10 && mouseY < guiTop + 10 + BookmarkHeight;
        if (b) {
            gui.blit(BACKGROUND, guiLeft + 267, guiTop + 10, 0, 218, BookmarkWidth, BookmarkHeight, 512, 512);
            gui.renderItem(new ItemStack(ModItems.LEXICON.get()), guiLeft + 272, guiTop + 14);
            renderTooltip(gui, Component.translatable("gui.darkrpg.main"), guiLeft + 282, guiTop + 31);
        } else {
            gui.blit(BACKGROUND, guiLeft + 267, guiTop + 10, 0, 193, BookmarkWidth, BookmarkHeight, 512, 512);
            gui.renderItem(new ItemStack(ModItems.LEXICON.get()), guiLeft + 269, guiTop + 14);
        }

        boolean b1 = mouseX >= guiLeft + 267 && mouseX < guiLeft + 267 + BookmarkWidth && mouseY >= guiTop + 38 && mouseY < guiTop + 38 + 25;
        if (b1) {
            gui.blit(BACKGROUND, guiLeft + 267, guiTop + 38, 0, 218, BookmarkWidth, BookmarkHeight, 512, 512);
            renderTooltip(gui, Component.translatable("gui.darkrpg.jewelry"), guiLeft + 282, guiTop + 59);
            gui.renderItem(new ItemStack(ModItems.AMETHYST_GEM.get()), guiLeft + 272, guiTop + 42);
        } else {
            gui.blit(BACKGROUND, guiLeft + 267, guiTop + 38, 0, 193, BookmarkWidth, BookmarkHeight, 512, 512);
            gui.renderItem(new ItemStack(ModItems.AMETHYST_GEM.get()), guiLeft + 269, guiTop + 42);
        }

        boolean b2 = mouseX >= guiLeft + 267 && mouseX < guiLeft + 267 + BookmarkWidth && mouseY >= guiTop + 66 && mouseY < guiTop + 66 + BookmarkHeight;
        if (b2) {
            gui.blit(BACKGROUND, guiLeft + 267, guiTop + 66, 0, 218, BookmarkWidth, BookmarkHeight, 512, 512);
            renderTooltip(gui, Component.translatable("gui.darkrpg.medicine"), guiLeft + 282, guiTop + 87);
            gui.renderItem(new ItemStack(ModItems.ALOE_BANDAGE.get()), guiLeft + 272, guiTop + 70);
        } else {
            gui.blit(BACKGROUND, guiLeft + 267, guiTop + 66, 0, 193, BookmarkWidth, BookmarkHeight, 512, 512);
            gui.renderItem(new ItemStack(ModItems.ALOE_BANDAGE.get()), guiLeft + 269, guiTop + 70);
        }

        if (mouseX >= guiLeft + 267 && mouseX < guiLeft + 267 + BookmarkWidth && mouseY >= guiTop + 94 && mouseY < guiTop + 94 + BookmarkHeight) {
            gui.blit(BACKGROUND, guiLeft + 267, guiTop + 94, 0, 218, BookmarkWidth, BookmarkHeight, 512, 512);
            gui.blit(BACKGROUND, guiLeft + 278, guiTop + 102, 277, 70, 5, 10, 512, 512);
            renderTooltip(gui, Component.translatable("gui.darkrpg.soon"), guiLeft + 282, guiTop + 115);
            //gui.renderItem(new ItemStack(ModItems.GEM_BAG.get()), guiLeft + 272, guiTop + 98);
        } else {
            gui.blit(BACKGROUND, guiLeft + 267, guiTop + 94, 0, 193, BookmarkWidth, BookmarkHeight, 512, 512);
            gui.blit(BACKGROUND, guiLeft + 275, guiTop + 102, 272, 70, 5, 10, 512, 512);
            //gui.renderItem(new ItemStack(ModItems.GEM_BAG.get()), guiLeft + 269, guiTop + 98);
        }

        // Category footer
        gui.blit(BACKGROUND, guiLeft + 48, guiTop + 31, 97, 180, 38, 13, 512, 512);
        gui.blit(BACKGROUND, guiLeft + 186, guiTop + 31, 97, 180, 38, 13, 512, 512);
        switch (pages) {
            case MAIN:
                if (b) {
                    gui.blit(BACKGROUND, guiLeft + 267, guiTop + 10, 0, 218, BookmarkWidth, BookmarkHeight, 512, 512);
                } else {
                    gui.blit(BACKGROUND, guiLeft + 267, guiTop + 10, 0, 218 + BookmarkHeight, BookmarkWidth, BookmarkHeight, 512, 512);
                }

                // Search bar
                gui.blit(BACKGROUND, guiLeft + 20, guiTop + 18, 0, 180, 96, 13, 512, 512);
                drawWrappingText(gui, "gui.darkrpg.search", guiLeft + 15, guiTop + 42, 115, false);

                // Search bar footer
                gui.blit(BACKGROUND, guiLeft + 48, guiTop + 31, 97, 180, 38, 13, 512, 512);
                drawWrappingText(gui, "gui.darkrpg.knowledge", guiLeft + 203, guiTop + 21, 240, true);
                drawWrappingText(gui, "gui.darkrpg.knowledge.desc", guiLeft + 148, guiTop + 45, 136, false);

                // Craft
                CraftEntry slot1 = new CraftEntry(new ItemStack(Items.PAPER), 175, 88);
                CraftEntry slot2 = new CraftEntry(new ItemStack(Items.AIR), 175, 88 + 18);
                CraftEntry slot3 = new CraftEntry(new ItemStack(Items.BOOK), 175 + 18, 88);
                CraftEntry slot4 = new CraftEntry(new ItemStack(Items.AIR), 175 + 18, 88 + 18);
                CraftEntry result = new CraftEntry(new ItemStack(ModItems.LEXICON.get()), 175 + 48, 88 + 8);

                slot1.render(gui, guiLeft, guiTop, mouseX, mouseY, true);
                slot2.render(gui, guiLeft, guiTop, mouseX, mouseY, false);
                slot3.render(gui, guiLeft, guiTop, mouseX, mouseY, true);
                slot4.render(gui, guiLeft, guiTop, mouseX, mouseY, false);
                result.render(gui, guiLeft, guiTop, mouseX, mouseY, true);
                result.resultArrow(gui, guiLeft, guiTop, 175 + 37, 88 + 14, mouseX, mouseY, false);
                break;
            case GEMS:
                if (b1) {
                    gui.blit(BACKGROUND, guiLeft + 267, guiTop + 38, 0, 218, BookmarkWidth, BookmarkHeight, 512, 512);
                } else {
                    gui.blit(BACKGROUND, guiLeft + 267, guiTop + 38, 0, 218 + BookmarkHeight, BookmarkWidth, BookmarkHeight, 512, 512);
                }


                drawWrappingText(gui, "gui.darkrpg.treasures.name", guiLeft + 70, guiTop + 21, 120, true);
                drawWrappingText(gui, "gui.darkrpg.treasures", guiLeft + 15, guiTop + 42, 120, false);
                drawWrappingText(gui, "gui.darkrpg.treasures.gems.name", guiLeft + 208, guiTop + 21, 120, true);
                if (mouseX >= guiLeft + 250 && mouseX < guiLeft + 250 + 9 && mouseY >= guiTop + 150 && mouseY < guiTop + 150 + 8) {
                    gui.blit(BACKGROUND, guiLeft + 250, guiTop + 150, 272, 104, 9, 8, 512, 512);
                    renderTooltip(gui, Component.translatable("gui.darkrpg.next"), guiLeft + 250, guiTop + 150);
                } else {
                    gui.blit(BACKGROUND, guiLeft + 250, guiTop + 150, 272, 88, 9, 8, 512, 512);
                }

                drawWrappingText(gui, "gui.darkrpg.treasure.gems", guiLeft + 148, guiTop + 42, 120, false);
                break;
            case GEMS_ABOUT:
                if (b1) {
                    gui.blit(BACKGROUND, guiLeft + 267, guiTop + 38, 0, 218, BookmarkWidth, BookmarkHeight, 512, 512);
                } else {
                    gui.blit(BACKGROUND, guiLeft + 267, guiTop + 38, 0, 218 + BookmarkHeight, BookmarkWidth, BookmarkHeight, 512, 512);
                }

                if (mouseX >= guiLeft + 13 && mouseX < guiLeft + 13 + 9 && mouseY >= guiTop + 150 && mouseY < guiTop + 150 + 8) {
                    gui.blit(BACKGROUND, guiLeft + 13, guiTop + 150, 272, 96, 9, 8, 512, 512);
                    renderTooltip(gui, Component.translatable("gui.darkrpg.back"), guiLeft + 13, guiTop + 150);
                } else {
                    gui.blit(BACKGROUND, guiLeft + 13, guiTop + 150, 272, 80, 9, 8, 512, 512);
                }

                drawWrappingText(gui, "gui.darkrpg.about", guiLeft + 70, guiTop + 21, 120, true);
                drawWrappingText(gui, "gui.darkrpg.treasure.gems.about", guiLeft + 15, guiTop + 42, 117, false);
                drawWrappingText(gui, "gui.darkrpg.geodes", guiLeft + 208, guiTop + 21, 120, true);
                drawWrappingText(gui, "gui.darkrpg.geodes.desc", guiLeft + 148, guiTop + 42, 117, false);
                break;
            case THANKS:
                drawWrappingText(gui, "gui.darkrpg.about", guiLeft + 70, guiTop + 21, 120, true);
                drawWrappingText(gui, "gui.darkrpg.thanks.desc_2", guiLeft + 15, guiTop + 42, 117, false);
                drawWrappingText(gui, "gui.darkrpg.thanks", guiLeft + 208, guiTop + 21, 120, true);
                drawWrappingText(gui, "gui.darkrpg.thanks.desc", guiLeft + 148, guiTop + 42, 117, false);
                gui.blit(BACKGROUND, guiLeft + 185, guiTop  + 91, 279, 112, 9, 9, 512, 512);
                if (mouseX >= guiLeft + 185 && mouseX < guiLeft + 185 + 9 && mouseY >= guiTop + 91 && mouseY < guiTop + 91 + 9) {
                    renderTooltip(gui, Component.literal("It's Cat, Feimos, WOSAJ, AstemirDev, Auriny, Skoow, GraFik"), guiLeft + 50, guiTop + 120);
                }

                break;
            case MEDICINE:
                if (b2) {
                    gui.blit(BACKGROUND, guiLeft + 267, guiTop + 66, 0, 218, BookmarkWidth, BookmarkHeight, 512, 512);
                } else {
                    gui.blit(BACKGROUND, guiLeft + 267, guiTop + 66, 0, 218 + BookmarkHeight, BookmarkWidth, BookmarkHeight, 512, 512);
                }

                drawWrappingText(gui, "gui.darkrpg.medicine", guiLeft + 70, guiTop + 21, 120, true);
                drawWrappingText(gui, "gui.darkrpg.aloe", guiLeft + 15, guiTop + 42, 117, false);
                drawWrappingText(gui, "gui.darkrpg.recipes", guiLeft + 208, guiTop + 21, 120, true);
                CraftEntry medicine_slot1 = new CraftEntry(new ItemStack(ModItems.ALOE_PIECE.get()), 175, 48);
                CraftEntry medicine_slot2 = new CraftEntry(new ItemStack(Items.STRING), 175, 48 + 18);
                CraftEntry medicine_slot3 = new CraftEntry(new ItemStack(Items.STRING), 175 + 18, 48);
                CraftEntry medicine_slot4 = new CraftEntry(new ItemStack(ModItems.ALOE_PIECE.get()), 175 + 18, 48 + 18);
                CraftEntry medicine_result = new CraftEntry(new ItemStack(ModItems.ALOE_BANDAGE.get()), 175 + 48, 48 + 8);

                medicine_slot1.render(gui, guiLeft, guiTop, mouseX, mouseY, true);
                medicine_slot2.render(gui, guiLeft, guiTop, mouseX, mouseY, true);
                medicine_slot3.render(gui, guiLeft, guiTop, mouseX, mouseY, true);
                medicine_slot4.render(gui, guiLeft, guiTop, mouseX, mouseY, true);
                medicine_result.render(gui, guiLeft, guiTop, mouseX, mouseY, true);
                medicine_result.resultArrow(gui, guiLeft, guiTop, 175 + 37, 48 + 14, mouseX, mouseY, false);
                gui.renderItemDecorations(font, new ItemStack(ModItems.ALOE_BANDAGE.get()), guiLeft + 175 + 49, guiTop + 48 + 8, "2");

                CraftEntry medicine_upgraded_slot1 = new CraftEntry(new ItemStack(Items.REDSTONE), 175, 98);
                CraftEntry medicine_upgraded_slot2 = new CraftEntry(new ItemStack(ModItems.ALOE_BANDAGE.get()), 175, 98 + 18);
                CraftEntry medicine_upgraded_slot3 = new CraftEntry(new ItemStack(ModItems.ALOE_BANDAGE.get()), 175 + 18, 98);
                CraftEntry medicine_upgraded_slot4 = new CraftEntry(new ItemStack(Items.REDSTONE), 175 + 18, 98 + 18);
                CraftEntry medicine_upgraded_result = new CraftEntry(new ItemStack(ModItems.ALOE_BANDAGE_UPGRADED.get()), 175 + 48, 98 + 8);
                gui.renderItemDecorations(font, new ItemStack(ModItems.ALOE_BANDAGE.get()), guiLeft + 175 + 49, guiTop + 98 + 8, "2");

                medicine_upgraded_slot1.render(gui, guiLeft, guiTop, mouseX, mouseY, true);
                medicine_upgraded_slot2.render(gui, guiLeft, guiTop, mouseX, mouseY, true);
                medicine_upgraded_slot3.render(gui, guiLeft, guiTop, mouseX, mouseY, true);
                medicine_upgraded_slot4.render(gui, guiLeft, guiTop, mouseX, mouseY, true);
                medicine_upgraded_result.render(gui, guiLeft, guiTop, mouseX, mouseY, true);
                medicine_upgraded_result.resultArrow(gui, guiLeft, guiTop, 175 + 37, 98 + 14, mouseX, mouseY, false);
                break;
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
            Minecraft mc = Minecraft.getInstance();
            this.width = mc.getWindow().getGuiScaledWidth();
            this.height = mc.getWindow().getGuiScaledHeight();
            int guiLeft = (width - 272) / 2, guiTop = (height - 180) / 2;

            if (mouseX >= guiLeft - 14 && mouseX < guiLeft - 14 + 35 && mouseY >= guiTop + 138 && mouseY < guiTop + 138 + 7) {
                mc.player.playNotifySound(SoundEvents.BOOK_PAGE_TURN, SoundSource.NEUTRAL, 1.0f, 1.0f);
                mc.setScreen(new LexiconGui(LexiconPages.THANKS));
            }

            if (mouseX >= guiLeft + 267 && mouseX < guiLeft + 267 + 35 && mouseY >= guiTop + 10 && mouseY < guiTop + 10 + 25) {
                mc.player.playNotifySound(SoundEvents.BOOK_PAGE_TURN, SoundSource.NEUTRAL, 1.0f, 1.0f);
                mc.setScreen(new LexiconGui(LexiconPages.MAIN));
            }

            if (mouseX >= guiLeft + 267 && mouseX < guiLeft + 267 + 35 && mouseY >= guiTop + 38 && mouseY < guiTop + 38 + 25) {
                mc.player.playNotifySound(SoundEvents.BOOK_PAGE_TURN, SoundSource.NEUTRAL, 1.0f, 1.0f);
                mc.setScreen(new LexiconGui(LexiconPages.GEMS));
            }

            if (mouseX >= guiLeft + 267 && mouseX < guiLeft + 267 + 35 && mouseY >= guiTop + 66 && mouseY < guiTop + 66 + 25) {
                mc.player.playNotifySound(SoundEvents.BOOK_PAGE_TURN, SoundSource.NEUTRAL, 1.0f, 1.0f);
                mc.setScreen(new LexiconGui(LexiconPages.MEDICINE));
            }

            if (mouseX >= guiLeft + 267 && mouseX < guiLeft + 267 + 35 && mouseY >= guiTop + 94 && mouseY < guiTop + 94 + 25) {
                mc.player.playNotifySound(SoundEvents.UI_LOOM_TAKE_RESULT, SoundSource.NEUTRAL, 1.0f, 1.0f);
            }

            switch (pages) {
                case MAIN, THANKS, MEDICINE:
                    break;
                case GEMS:
                    if (mouseX >= guiLeft + 250 && mouseX < guiLeft + 250 + 9 && mouseY >= guiTop + 150 && mouseY < guiTop + 150 + 8) {
                        mc.player.playNotifySound(SoundEvents.BOOK_PAGE_TURN, SoundSource.NEUTRAL, 1.0f, 1.0f);
                        mc.setScreen(new LexiconGui(LexiconPages.GEMS_ABOUT));
                    }

                    break;
                case GEMS_ABOUT:
                    if (mouseX >= guiLeft + 13 && mouseX < guiLeft + 13 + 9 && mouseY >= guiTop + 150 && mouseY < guiTop + 150 + 8) {
                        mc.player.playNotifySound(SoundEvents.BOOK_PAGE_TURN, SoundSource.NEUTRAL, 1.0f, 1.0f);
                        mc.setScreen(new LexiconGui(LexiconPages.GEMS));
                    }

                    break;
            }
        }

        return false;
    }

    public void renderTooltip(GuiGraphics gui, MutableComponent component, int x, int y) {
        gui.renderTooltip(Minecraft.getInstance().font, component, x, y);
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
            }
            else line += s + " ";
        }
        if (!line.isEmpty()) lines.add(line);
        for (int i = 0; i < lines.size(); i ++) {
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