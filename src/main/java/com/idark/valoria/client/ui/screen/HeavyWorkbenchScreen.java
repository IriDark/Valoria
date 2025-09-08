package com.idark.valoria.client.ui.screen;

import com.idark.valoria.*;
import com.idark.valoria.client.ui.menus.*;
import com.idark.valoria.registries.item.component.*;
import com.idark.valoria.registries.item.recipe.*;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.screens.inventory.*;
import net.minecraft.core.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraft.sounds.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.inventory.tooltip.*;
import net.minecraft.world.item.*;
import pro.komaru.tridot.util.phys.*;
import pro.komaru.tridot.util.struct.data.*;

import java.util.*;

public class HeavyWorkbenchScreen extends AbstractContainerScreen<HeavyWorkbenchMenu> {
    private static final ResourceLocation TEXTURE = Valoria.loc("textures/gui/container/heavy_workbench.png");
    private final Seq<BlueprintData> renderedRecipes = Seq.with();
    private BlueprintData hoveredRecipe = null;

    private float scrollOffset = 0;
    private long lastClickTime = 0;

    private final int blueprintAreaLeft = 7;
    private final int blueprintAreaTop = 8;
    private final int blueprintAreaWidth = 74;
    private final int blueprintAreaHeight = 31;
    private final int visibleRows = 4;
    private final int visibleCols = 2;

    private final int scrollbarHeight = 54;
    private final int recipeAreaWidth = 174;
    private final int recipeAreaHeight = 60;

    public HeavyWorkbenchScreen(HeavyWorkbenchMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        this.imageHeight = 166;
        this.imageWidth = 176;
    }

    @Override
    protected void init() {
        super.init();
        updateCraftableRecipes();
    }

    private void updateCraftableRecipes() {
        this.renderedRecipes.clear();
        List<BlueprintData> nonCraftableRecipes = new ArrayList<>();
        for (var recipeHolder : this.menu.getAllRecipes()) {
            BlueprintData data = new BlueprintData(recipeHolder);
            if (this.menu.canCraft(recipeHolder)) {
                this.renderedRecipes.add(data); // craftables at top
            } else {
                nonCraftableRecipes.add(data);
            }
        }

        this.renderedRecipes.addAll(nonCraftableRecipes);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        updateCraftableRecipes();
        this.hoveredRecipe = getRecipeAt(mouseX, mouseY);

        this.renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY){
        pGuiGraphics.drawString(this.font, this.playerInventoryTitle, this.inventoryLabelX, this.inventoryLabelY, 4210752, false);
    }

    @Override
    protected void renderTooltip(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        super.renderTooltip(guiGraphics, mouseX, mouseY);
        if (this.hoveredRecipe != null){
            if(hoveredRecipe.isVisible()){
                var recipe = hoveredRecipe.recipe;
                List<Component> tooltip = new ArrayList<>();
                tooltip.add(Component.translatable("tooltip.valoria.materials_needed", recipe.getResultItem(RegistryAccess.EMPTY).getHoverName()));
                Optional<TooltipComponent> comp = Optional.empty();

                if(!recipe.getInputs().isEmpty()) comp = Optional.of(new ClientMaterialListClientComponent(recipe.getInputs()));
                guiGraphics.renderTooltip(this.font, tooltip, comp, mouseX, mouseY);
            }
        }
    }

    @Override
    protected void renderBg(GuiGraphics gui, float partialTick, int mouseX, int mouseY){
        int left = this.getGuiLeft();
        int top = this.getGuiTop();
        int scrollbarTop = top + 8;
        int scrollbarLeft = left + 163;
        gui.blit(TEXTURE, left, top, 0, 0, this.imageWidth, this.imageHeight);
        gui.blit(TEXTURE, scrollbarLeft, scrollbarTop, 176, 0, 7, scrollbarHeight);

        int totalRows = (int) Math.ceil((double) this.renderedRecipes.size / visibleCols);
        int maxScroll = Math.max(0, totalRows - visibleRows);
        int handleY = (int) (this.scrollOffset * (scrollbarHeight - 10));
        gui.blit(TEXTURE, scrollbarLeft, scrollbarTop + handleY, 183, 0, 7, 15);

        int firstIndex = (int)(this.scrollOffset * maxScroll) * visibleCols;
        int recipeAreaX = left + 88;
        int recipeAreaY = top + 33;

        scissorsOn(gui, gui.pose(), recipeAreaX, recipeAreaY, recipeAreaWidth, recipeAreaHeight);
        renderGrid(gui, firstIndex, left, top - handleY);
        scissorsOff(gui);
    }

    private boolean isVisibleInScissor(GuiGraphics gui, int x, int y, int w, int h, int scissorX, int scissorY, int scissorW, int scissorH) {
        AbsRect s = AbsRect.xywhDef((float)scissorX, (float)scissorY, (float)scissorW, (float)scissorH).pose(gui.pose());
        AbsRect r = AbsRect.xywhDef((float)x, (float)y, (float)w, (float)h).pose(gui.pose());
        return r.x < s.x2 && r.x2 > s.x && r.y < s.y2 && r.y2 > s.y;
    }

    public boolean isVisible(GuiGraphics gui, int x, int y) {
        int left = this.getGuiLeft();
        int top = this.getGuiTop();
        int recipeAreaX = left + 88;
        int recipeAreaY = top + 33;
        return isVisibleInScissor(gui, x, y, blueprintAreaWidth, blueprintAreaHeight, recipeAreaX, recipeAreaY, recipeAreaWidth, recipeAreaHeight - 10);
    }

    private void renderGrid(GuiGraphics gui, int firstIndex, int left, int top){
        int gapX = 4;
        int gapY = 2;
        for(int row = 0; row < visibleRows; row++){
            for(int col = 0; col < visibleCols; col++){
                int recipeIndex = firstIndex + row * visibleCols + col;
                if(recipeIndex < this.renderedRecipes.size){
                    BlueprintData blueprintData = this.renderedRecipes.get(recipeIndex);
                    WorkbenchRecipe recipe = blueprintData.recipe;
                    int x = left + blueprintAreaLeft + col * (blueprintAreaWidth + gapX);
                    int y = top + blueprintAreaTop + row * (blueprintAreaHeight + gapY);
                    if(isVisible(gui, x, y)){
                        blueprintData.setVisible(true);
                        if(this.hoveredRecipe != null && recipe.equals(this.hoveredRecipe.recipe)){
                            gui.blit(TEXTURE, x - 1, y - 1, 0,  197, blueprintAreaWidth + 2, blueprintAreaHeight + 2);
                        }

                        gui.blit(TEXTURE, x, y, this.menu.canCraft(recipe) ? 0 : 74, 166, blueprintAreaWidth, blueprintAreaHeight);
                        ItemStack result = recipe.getResultItem(RegistryAccess.EMPTY);
                        gui.renderFakeItem(result, x + 29, y + 6);
                    }else{
                        blueprintData.setVisible(false);
                    }
                }
            }
        }
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        int totalRows = (int) Math.ceil((double) this.renderedRecipes.size / visibleCols);
        int maxScroll = Math.max(0, totalRows - 2);

        if (maxScroll > 0) {
            float rowHeight = 33.0F;
            float pixelsPerScroll = 33.0F;
            float totalScrollablePixels = (totalRows - 2) * rowHeight;

            float scrollDelta = (float) delta * pixelsPerScroll / totalScrollablePixels;

            this.scrollOffset = Mth.clamp(this.scrollOffset - scrollDelta, 0.0F, 1.0F);
            return true;
        }

        return false;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            if (System.currentTimeMillis() - this.lastClickTime < 450) {
                var clickedRecipe = getRecipeAt((int) mouseX, (int) mouseY);
                if (clickedRecipe != null && clickedRecipe.isVisible()) {
                    minecraft.player.playSound(SoundEvents.BOOK_PAGE_TURN);
                    // TODO: Implement packet to send crafting request to server
                    return true;
                }
            }

            this.lastClickTime = System.currentTimeMillis();
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    public void scissorsOn(GuiGraphics gui, PoseStack pose, int x, int y, int w, int h) {
        AbsRect r = AbsRect.xywhDef((float)x, (float)y, (float)w, (float)h).pose(pose);
        gui.enableScissor((int)r.x, (int)r.y, (int)r.x2, (int)r.y2);
    }

    public void scissorsOff(GuiGraphics gui) {
        gui.disableScissor();
    }

    private BlueprintData getRecipeAt(double mouseX, double mouseY) {
        int totalRows = (int) Math.ceil((double) this.renderedRecipes.size / visibleCols);
        int maxScroll = Math.max(0, totalRows - visibleRows);
        int firstIndex = (int)(this.scrollOffset * maxScroll) * visibleCols;

        int left = this.getGuiLeft();
        int top = this.getGuiTop();
        int handleY = (int) (this.scrollOffset * (scrollbarHeight - 10));

        int mouseXRelative = (int) mouseX - left;
        int mouseYRelative = (int) mouseY - top;

        int gapX = 4;
        int gapY = 2;

        int cellWidth = blueprintAreaWidth;
        int cellHeight = blueprintAreaHeight;
        for (int row = 0; row < visibleRows; row++) {
            for (int col = 0; col < visibleCols; col++) {
                int xPos = blueprintAreaLeft + col * (cellWidth + gapX);
                int yPos = blueprintAreaTop + row * (cellHeight + gapY) - handleY;
                if (mouseXRelative >= xPos && mouseXRelative < xPos + cellWidth && mouseYRelative >= yPos && mouseYRelative < yPos + cellHeight) {
                    int recipeIndex = firstIndex + row * visibleCols + col;
                    if (recipeIndex < this.renderedRecipes.size) {
                        return this.renderedRecipes.get(recipeIndex);
                    }
                }
            }
        }

        return null;
    }

    public static class BlueprintData {
        private final WorkbenchRecipe recipe;
        private boolean visible = true;
        public BlueprintData(WorkbenchRecipe recipe) {
            this.recipe = recipe;
        }

        public boolean isVisible(){
            return this.visible;
        }

        public void setVisible(boolean visible) {
            this.visible = visible;
        }
    }
}