package com.idark.valoria.client.ui.screen.book.pages;

import com.idark.valoria.*;
import com.idark.valoria.client.ui.screen.book.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.screens.inventory.*;
import net.minecraft.client.resources.language.*;
import net.minecraft.core.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import net.minecraftforge.api.distmarker.*;
import org.joml.*;
import pro.komaru.tridot.api.*;
import pro.komaru.tridot.client.*;
import pro.komaru.tridot.util.*;

import javax.annotation.*;
import java.lang.Math;
import java.util.*;
import java.util.function.*;

public class GeneralPage extends Page {
    private final List<PageElement> elements = new ArrayList<>();
    private final int wrapWidth = 120;
    private final int pageHeight = 140;
    private float scrollAmount = 0;
    private float targetScroll = 0;
    private int totalHeight = 0;
    private int lastX = 0;
    private int lastY = 0;

    public static final ResourceLocation BACKGROUND = new ResourceLocation(Valoria.ID, "textures/gui/book/codex.png");
    public static final Quaternionf ENTITY_ANGLE = (new Quaternionf()).rotationXYZ(0.43633232F, 0.0F, (float)Math.PI);

    private boolean hasTitle = true;
    private String title;

    public GeneralPage() {}

    public GeneralPage(String textKey) {
        this.addTitle(textKey + ".name");
        this.addText(textKey);
    }

    public GeneralPage hideTitle() {
        this.hasTitle = false;
        return this;
    }

    public GeneralPage withCustomTitle(String title) {
        this.title = title;
        this.hasTitle = true;
        return this;
    }

    public GeneralPage addTitle(String titleKey) {
        elements.add(new PageElement() {
            @Override
            public int render(GuiGraphics gui, int x, int y, int mouseX, int mouseY) {
                if(!hasTitle) return 0;

                Font font = Minecraft.getInstance().font;
                String translated = I18n.get(titleKey);
                drawText(gui, titleKey, x + (wrapWidth - font.width(translated)) / 2, y - 4, false);
                return font.lineHeight + 1;
            }
        });

        return this;
    }

    public GeneralPage addText(String textKey) {
        elements.add(new PageElement() {
            @Override
            public int render(GuiGraphics gui, int x, int y, int mouseX, int mouseY) {
                Font font = Minecraft.getInstance().font;
                String content = I18n.get(textKey);
                drawWrappingText(gui, content, x, y, wrapWidth, false);
                int lineCount = font.split(Component.literal(content), wrapWidth).size();
                return lineCount * (font.lineHeight + 1) + 20;
            }
        });

        return this;
    }

    public GeneralPage addImage(ResourceLocation texture, int width, int height) {
        return addImage(texture, 0, 0, width, height);
    }

    public GeneralPage addImage(ResourceLocation texture, int xOffset, int yOffset, int width, int height) {
        elements.add(new PageElement() {
            @Override
            public int render(GuiGraphics gui, int x, int y, int mouseX, int mouseY) {
                gui.blit(texture, x + xOffset + (xOffset == 0 ? (wrapWidth - width) / 2 : 0), y + yOffset, 0, 0, width, height, width, height);
                return height + yOffset + 5;
            }
        });

        return this;
    }

    public GeneralPage addFloatingItem(ItemStack item, int size) {
        return addItem(item, true, 0, 0, size);
    }

    public GeneralPage addItem(ItemStack item, int size) {
        return addItem(item, false, 0, 0, size);
    }

    public GeneralPage addItem(ItemStack item, boolean floating, int xOffset, int yOffset, int size) {
        elements.add(new PageElement() {
            @Override
            public int render(GuiGraphics gui, int x, int y, int mouseX, int mouseY) {
                Minecraft mc = Minecraft.getInstance();

                if(floating) {
                    gui.pose().pushPose();
                    float ticks = (ClientTick.ticksInGame + mc.getPartialTick()) * 2;
                    float ticksUp = (ClientTick.ticksInGame + mc.getPartialTick()) * 6;
                    ticksUp = (ticksUp) % 360;
                    float scale = size / 16.0F;

                    gui.pose().translate(x + xOffset + 8, y + yOffset + 8, 0);
                    gui.pose().scale(scale, scale, 1.0F);
                    Utils.Render.renderFloatingItemModelIntoGUI(gui, item, -8, -8, ticks, ticksUp);
                    gui.pose().popPose();
                    return size + yOffset + 5;
                } else{
                    Utils.Render.renderItemModelInGui(item, x + xOffset, y + yOffset, size, size, size);
                }

                return size + yOffset + 5;
            }
        });

        return this;
    }

    public GeneralPage addRecipe(ItemStack result) {
        elements.add(new PageElement() {
            private Ingredient[] inputs;
            private boolean hasRecipe = false;

            @Override
            public void init() {
                Minecraft mc = Minecraft.getInstance();
                if (mc.level != null) {
                    RecipeManager manager = mc.level.getRecipeManager();

                    Optional<CraftingRecipe> optional = manager.getAllRecipesFor(RecipeType.CRAFTING).stream()
                    .filter(r -> ItemStack.isSameItem(r.getResultItem(mc.level.registryAccess()), result))
                    .findFirst();

                    if (optional.isPresent()) {
                        CraftingRecipe recipe = optional.get();
                        NonNullList<Ingredient> ingredients = recipe.getIngredients();
                        this.inputs = new Ingredient[9];
                        Arrays.fill(this.inputs, Ingredient.EMPTY);
                        if (recipe instanceof ShapedRecipe shaped) {
                            int width = shaped.getWidth();
                            int height = shaped.getHeight();
                            for (int h = 0; h < height; h++) {
                                for (int w = 0; w < width; w++) {
                                    this.inputs[h * 3 + w] = ingredients.get(h * width + w);
                                }
                            }
                        } else {
                            for (int i = 0; i < ingredients.size(); i++) {
                                this.inputs[i] = ingredients.get(i);
                            }
                        }

                        this.hasRecipe = true;
                    }
                }
            }

            @Override
            public void renderPost(GuiGraphics gui, int x, int y, int mouseX, int mouseY){
                super.renderPost(gui, x, y, mouseX, mouseY);
                if (!hasRecipe) return;
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        int index = i * 3 + j;
                        if (inputs != null && index < inputs.length) {
                            Ingredient ingredient = inputs[index];
                            ItemStack[] matchingStacks = ingredient.getItems();
                            if (matchingStacks.length > 0) {
                                int cycle = (ClientTick.ticksInGame / 40) % matchingStacks.length;
                                BookGui.drawItemTooltip(matchingStacks[cycle], x + 23 + j * 18, y + 2 + i * 18, gui, mouseX, mouseY);
                            }
                        }
                    }
                }

                BookGui.drawItemTooltip(result, x + 89, y + 23 + 46 - 50, gui, mouseX, mouseY);
            }

            @Override
            public int render(GuiGraphics gui, int x, int y, int mouseX, int mouseY) {
                if (!hasRecipe) return 0;
                gui.pose().pushPose();
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        int index = i * 3 + j;
                        gui.blit(BACKGROUND, x + 22 + j * 18, y + 1 + i * 18, 287, 15, 18, 18, 512, 512);
                        if (inputs != null && index < inputs.length) {
                            Ingredient ingredient = inputs[index];
                            ItemStack[] matchingStacks = ingredient.getItems();
                            if (matchingStacks.length > 0) {
                                int cycle = (ClientTick.ticksInGame / 40) % matchingStacks.length;
                                gui.renderItem(matchingStacks[cycle], x + 23 + j * 18, y + 2 + i * 18);
                            }
                        }
                    }
                }

                gui.pose().popPose();

                gui.blit(BACKGROUND, x + 88, y + 22 + 46 - 50, 287, 15, 18, 18, 512, 512);
                gui.renderItem(result, x + 89, y + 23 + 46 - 50);
                gui.blit(BACKGROUND, x + 77, y + 74 - 50, 306, 15, 9, 7, 512, 512); // Arrow
                return 18 * 3;
            }
        });

        return this;
    }

    public GeneralPage addEntity(EntityType<? extends LivingEntity> type, int scale, boolean isDark) {
        return addEntity(type, scale, isDark, entity -> {});
    }

    public <T extends LivingEntity> GeneralPage addEntity(EntityType<T> type, int scale, boolean isDark, Consumer<T> configurator) {
        elements.add(new PageElement() {
            @Nullable private T entity;

            @Override
            public int render(GuiGraphics gui, int x, int y, int mouseX, int mouseY) {
                Minecraft mc = Minecraft.getInstance();
                if (entity == null) {
                    entity = type.create(mc.level);
                }

                if (entity != null) {
                    entity.setYBodyRot(210.0F);
                    entity.setYHeadRot(210.0F);
                    configurator.accept(entity);

                    gui.pose().pushPose();
                    if (isDark) gui.setColor(0, 0, 0, 1);
                    InventoryScreen.renderEntityInInventory(gui, x + wrapWidth / 2, y + (scale * 2), scale, ENTITY_ANGLE, null, entity);
                    if (isDark) gui.setColor(1, 1, 1, 1);
                    return (int)entity.getBoundingBox().getYsize() + 15;
                }

                return 0;
            }
        });

        return this;
    }

    public GeneralPage addSpace(int height) {
        elements.add(new PageElement() {
            @Override
            public int render(GuiGraphics gui, int x, int y, int mouseX, int mouseY) {
                return height;
            }
        });

        return this;
    }

    @Override
    public void init() {
        super.init();
        for (PageElement element : elements) {
            element.init();
        }
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        boolean isHoveringPage = mouseX >= lastX && mouseX <= lastX + wrapWidth + 10 &&
        mouseY >= lastY && mouseY <= lastY + pageHeight + 10;
        if (isHoveringPage && totalHeight > pageHeight) {
            targetScroll = Mth.clamp(targetScroll - (float)delta * 15, 0, totalHeight - pageHeight);
            return true;
        }

        return false;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(GuiGraphics gui, int x, int y, int mouseX, int mouseY) {
        this.lastX = x;
        this.lastY = y;

        scrollAmount = Mth.lerp(0.2f, scrollAmount, targetScroll);
        if (Math.abs(scrollAmount - targetScroll) < 0.1f) scrollAmount = targetScroll;

        int currentY = y + 15 - (int)scrollAmount;
        int initialY = currentY;
        gui.enableScissor(x, y + 10, x + wrapWidth + 10, y + pageHeight + 10);
        int[] elementYCoords = new int[elements.size()];
        if (hasTitle && title != null) {
            drawText(gui, this.title, x + (120 - Minecraft.getInstance().font.width(I18n.get(this.title))) / 2, currentY + 11 - Minecraft.getInstance().font.lineHeight, false);
            currentY += 16;
        }

        for (int i = 0; i < elements.size(); i++) {
            PageElement element = elements.get(i);
            elementYCoords[i] = currentY;

            int heightUsed = element.render(gui, x + 3, currentY, mouseX, mouseY);
            currentY += heightUsed;
        }

        this.totalHeight = currentY - initialY + 15;
        gui.disableScissor();
        for (int i = 0; i < elements.size(); i++) {
            PageElement element = elements.get(i);
            element.renderPost(gui, x + 3, elementYCoords[i], mouseX, mouseY);
        }

        if (totalHeight > pageHeight + 4) {
            int barX = x + wrapWidth + 4;
            int barWidth = 2;
            gui.fill(barX, y, barX + barWidth, y + pageHeight, Col.packColor(100, 0, 0, 0));
            int knobHeight = Math.max(10, (int)((float)pageHeight / totalHeight * pageHeight));
            int knobY = y + (int)((scrollAmount / (totalHeight - pageHeight)) * (pageHeight - knobHeight));
            gui.fill(barX, knobY, barX + barWidth, knobY + knobHeight, Col.packColor(255, 220, 200, 180));
        }
    }

    public abstract static class PageElement {
        public void init() {}
        public void renderPost(GuiGraphics gui, int x, int y, int mouseX, int mouseY) {}
        public abstract int render(GuiGraphics gui, int x, int y, int mouseX, int mouseY);
    }
}
