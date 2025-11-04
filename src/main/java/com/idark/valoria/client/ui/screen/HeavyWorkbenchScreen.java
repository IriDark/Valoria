package com.idark.valoria.client.ui.screen;

import com.idark.valoria.*;
import com.idark.valoria.client.ui.menus.*;
import com.idark.valoria.core.network.*;
import com.idark.valoria.core.network.packets.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.item.component.*;
import com.idark.valoria.registries.item.recipe.*;
import com.idark.valoria.util.*;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.screens.*;
import net.minecraft.client.gui.screens.inventory.*;
import net.minecraft.core.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraft.sounds.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.inventory.*;
import net.minecraft.world.inventory.tooltip.*;
import net.minecraft.world.item.*;
import pro.komaru.tridot.util.phys.*;
import pro.komaru.tridot.util.struct.data.*;

import java.util.*;

public class HeavyWorkbenchScreen extends AbstractContainerScreen<HeavyWorkbenchMenu> {
    private static final ResourceLocation TEXTURE = Valoria.loc("textures/gui/container/heavy_workbench.png");
    private final Seq<BlueprintData> renderedRecipes = Seq.with();
    private BlueprintData hoveredRecipe = null;
    private BlueprintData clickedBlueprintO;
    private Categories chosen = Categories.ALL;

    private final int recipesOffsetX = 6;
    private final int recipesOffsetY = 18;
    private final int recipeSlotWidth = 20;
    private final int recipeSlotHeight = 19;
    private final int scrollbarHeight = 84;

    private float scrollDistance = 0.0F;
    private final int visible = 7;

    private final int recipeAreaWidth = 175;
    private final int recipeAreaHeight = 85;
    private final int gridXOffset = 2;
    private final int gridYOffset = 2;
    private final int recipeRowHeight = this.recipeSlotHeight + 1 + this.gridYOffset;

    private final ContainerListener listener = new ContainerListener() {
        public void slotChanged(AbstractContainerMenu menu, int index, ItemStack stack) {
            updateCraftableRecipes();
        }

        public void dataChanged(AbstractContainerMenu menu, int index, int value) {
        }
    };

    public HeavyWorkbenchScreen(HeavyWorkbenchMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        this.imageHeight = 196;
        this.imageWidth = 176;
        this.menu.addSlotListener(this.listener);
    }

    @Override
    public void removed(){
        super.removed();
        this.menu.removeSlotListener(this.listener);
    }

    public void updateCraftableRecipes() {
        Seq<BlueprintData> craftableRecipes = Seq.with();
        Seq<BlueprintData> nonCraftableRecipes = Seq.with();
        for (var recipeHolder : this.menu.getAllRecipes()) {
            BlueprintData data = new BlueprintData(recipeHolder);
            if(recipeHolder.getGroup().equals(chosen.category) || chosen.equals(Categories.ALL)){
                if(this.menu.checkAndSetAvailability(recipeHolder)){
                    craftableRecipes.add(data); // craftables at top
                }else{
                    nonCraftableRecipes.add(data);
                }
            }
        }

        craftableRecipes.sort(Comparator.comparing((b) -> b.recipe.getId()));
        nonCraftableRecipes.sort(Comparator.comparing((b) -> b.recipe.getId()));
        renderedRecipes.clear();
        renderedRecipes.addAll(craftableRecipes).addAll(nonCraftableRecipes);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.hoveredRecipe = getBlueprintAt(mouseX, mouseY);
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY){
        pGuiGraphics.drawString(this.font, this.title.copy().append(": ").append(Component.translatable("menu.valoria.heavy_workbench.category." + chosen.category)), this.titleLabelX, this.titleLabelY+2, CommonColors.GRAY, false);
        pGuiGraphics.drawString(this.font, this.playerInventoryTitle, this.inventoryLabelX, this.inventoryLabelY+30, 4210752, false);
    }

    @Override
    protected void renderTooltip(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        super.renderTooltip(guiGraphics, mouseX, mouseY);
        if (this.hoveredRecipe != null){
            if(hoveredRecipe.isVisible()){
                var recipe = hoveredRecipe.recipe;
                List<Component> tooltip = new ArrayList<>();
                Optional<TooltipComponent> comp = Optional.empty();

                ItemStack result = recipe.getResultItem(RegistryAccess.EMPTY);
                tooltip.add(result.getHoverName().copy().withStyle(result.getDisplayName().getStyle()));
                tooltip.add(Component.empty());
                tooltip.add(Component.translatable("tooltip.tridot.shift_for_details", Component.translatable("key.keyboard.left.shift").getString()).withStyle(ChatFormatting.GRAY));
                if(!recipe.getInputs().isEmpty()) {
                    comp = Optional.of(new MaterialListComponent(recipe.getInputs()));
                }  else {
                    Valoria.LOGGER.warn("Recipe - {}, has no Inputs", hoveredRecipe.recipe.getId());
                }

                if(!Screen.hasShiftDown()){
                    guiGraphics.renderTooltip(this.font, tooltip, comp, mouseX, mouseY);
                } else {
                    guiGraphics.renderTooltip(this.font, result.getTooltipLines(null, TooltipFlag.NORMAL), Optional.empty(), result, mouseX, mouseY);
                }
            }
        }

        for(Categories category : Categories.values()){
            int x = getGuiLeft() - 19;
            int y = (getGuiTop() + 6) + category.ordinal() * 22;
            if(isHover(mouseX, mouseY, x, y, 22, 20)){
                List<Component> tooltip = new ArrayList<>();
                tooltip.add(Component.translatable("menu.valoria.heavy_workbench.category." + category.category));
                guiGraphics.renderTooltip(this.font, tooltip, Optional.empty(), mouseX, mouseY);
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
        int maxScroll = this.getMaxScroll();
        float scrollPercentage = maxScroll > 0 ? this.scrollDistance / maxScroll : 0.0F;
        int scrollbarThumbY = (int)(scrollPercentage * (scrollbarHeight - 10));
        gui.blit(TEXTURE, scrollbarLeft, scrollbarTop + scrollbarThumbY, 183, 0, 7, 15);

        int recipeAreaX = getRecipeAreaX(left);
        int recipeAreaY = getRecipeAreaY(top);
        scissorsOn(gui, gui.pose(), recipeAreaX, recipeAreaY+25, this.recipeAreaWidth, this.recipeAreaHeight);
        renderGrid(gui, left, top);
        scissorsOff(gui);

        for(Categories category : Categories.values()){
            int x = left - 19;
            int y = (top + 6) + category.ordinal() * 22;
            boolean flag = isHover(mouseX, mouseY, x, y, 22, 20);
            if(!flag && chosen != category){
                gui.blit(TEXTURE, x, y, 234, 0, 22, 20);
                gui.renderFakeItem(category.item.getDefaultInstance(), x + 3, y + 2);
            } else {
                gui.blit(TEXTURE, x - 6, y, 228, 20, 28, 20);
                gui.renderFakeItem(category.item.getDefaultInstance(), x - 3, y + 2);
            }
        }
    }

    public boolean isHover(double mouseX, double mouseY, int x, int y, int width, int height) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }

    public boolean isVisible(GuiGraphics gui, int x, int y) {
        return ValoriaUtils.isVisibleInScissor(gui, x, y, recipeSlotWidth, recipeSlotHeight, getRecipeAreaX(this.getGuiLeft()), getRecipeAreaY(this.getGuiTop()), recipeAreaWidth, recipeAreaHeight);
    }

    private void renderGrid(GuiGraphics gui, int left, int top){
        for(int i = 0; i < this.renderedRecipes.size; i++){
            int row = i / this.visible;
            int col = i % this.visible;

            BlueprintData blueprintData = this.renderedRecipes.get(i);
            WorkbenchRecipe recipe = blueprintData.recipe;
            int x = left + this.recipesOffsetX + col * (this.recipeSlotWidth + this.gridXOffset);
            int y = top + this.recipesOffsetY + row * this.recipeRowHeight - (int)this.scrollDistance;
            if(isVisible(gui, x, y)){
                blueprintData.setVisible(true);
                if(this.hoveredRecipe != null && recipe.equals(this.hoveredRecipe.recipe)){
                    gui.blit(TEXTURE, x - 1, y - 1, 0,  215, this.recipeSlotWidth + 2, this.recipeSlotHeight + 2);
                }

                boolean isClicked = clickedBlueprintO == blueprintData;
                gui.blit(TEXTURE, x, y, isClicked ? 0 : this.menu.checkAndSetAvailability(recipe) ? 20 : 40, 196, this.recipeSlotWidth, this.recipeSlotHeight);
                ItemStack result = recipe.getResultItem(RegistryAccess.EMPTY);
                gui.renderFakeItem(result, x + 2, y + 2);
            } else {
                blueprintData.setVisible(false);
            }
        }
    }


    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        int maxScroll = this.getMaxScroll();
        if(scrollDistance < maxScroll && delta == -1) {
            this.scrollDistance += 22;
        } else if(scrollDistance > 0 && delta == 1) {
            this.scrollDistance -= 22;
        }

        return super.mouseScrolled(mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        BlueprintData clickedBlueprint = hoveredRecipe;
        if (button == 0) {
            if (clickedBlueprint != null && clickedBlueprint.isVisible()) {
                if (clickedBlueprintO == clickedBlueprint && this.menu.checkAndSetAvailability(clickedBlueprint.recipe)) {
                    minecraft.player.playSound(SoundEvents.SMITHING_TABLE_USE);
                    PacketHandler.sendToServer(new HeavyWorkbenchCraftPacket(this.hoveredRecipe.recipe));
                    return true;
                } else {
                    clickedBlueprintO = hoveredRecipe;
                    minecraft.player.playSound(SoundsRegistry.UI_CLICK.get());
                }
            }

            for(Categories category : Categories.values()){
                int x = getGuiLeft() - 19;
                int y = (getGuiTop() + 6) + category.ordinal() * 22;
                if(isHover(mouseX, mouseY, x, y, 24, 20)){
                    this.chosen = category;
                    minecraft.player.playSound(SoundsRegistry.UI_CLICK.get());
                    updateCraftableRecipes();
                }
            }

            return true;
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    public void scissorsOn(GuiGraphics gui, PoseStack pose, int x, int y, int w, int h) {
        AbsRect r = AbsRect.xywhDef((float)x, (float)y, (float)w, (float)h).pose(pose);
        gui.enableScissor((int)r.x, (int)r.y, (int)r.x2, (int)r.y2 - 3);
    }

    public void scissorsOff(GuiGraphics gui) {
        gui.disableScissor();
    }

    private BlueprintData getBlueprintAt(double mouseX, double mouseY) {
        int left = this.getGuiLeft();
        int top = this.getGuiTop();
        int mouseXRelative = (int) mouseX - left;
        int mouseYRelative = (int) mouseY - top;

        int viewportX = this.recipesOffsetX;
        int viewportY = this.recipesOffsetY;
        int viewportWidth = this.visible * (this.recipeSlotWidth + this.gridXOffset);
        if (mouseXRelative < viewportX || mouseXRelative >= viewportX + viewportWidth || mouseYRelative < viewportY || mouseYRelative >= viewportY + this.recipeAreaHeight) {
            return null;
        }

        for (int i = 0; i < this.renderedRecipes.size; i++) {
            int row = i / this.visible;
            int col = i % this.visible;

            int itemX = this.recipesOffsetX + col * (this.recipeSlotWidth + this.gridXOffset);
            int itemY = this.recipesOffsetY + row * this.recipeRowHeight - (int)this.scrollDistance;
            if (mouseXRelative >= itemX && mouseXRelative < itemX + this.recipeSlotWidth && mouseYRelative >= itemY && mouseYRelative < itemY + this.recipeSlotHeight) {
                return this.renderedRecipes.get(i);
            }
        }

        return null;
    }

    public int getMaxScroll() {
        int totalContentHeight = (int) Math.ceil((double) this.renderedRecipes.size / this.visible) * this.recipeRowHeight;
        return Math.max(0, totalContentHeight - this.recipeAreaHeight + 2);
    }

    public int getRecipeAreaY(int top){
        return top + 33;
    }

    public int getRecipeAreaX(int left){
        return left + 88;
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