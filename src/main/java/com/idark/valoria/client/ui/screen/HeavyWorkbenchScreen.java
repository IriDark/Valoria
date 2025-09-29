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
import net.minecraft.network.chat.Component;
import net.minecraft.resources.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.inventory.*;
import net.minecraft.world.inventory.tooltip.*;
import net.minecraft.world.item.*;
import pro.komaru.tridot.util.phys.*;
import pro.komaru.tridot.util.struct.data.*;

import java.awt.*;
import java.util.*;
import java.util.List;

public class HeavyWorkbenchScreen extends AbstractContainerScreen<HeavyWorkbenchMenu> {
    private static final ResourceLocation TEXTURE = Valoria.loc("textures/gui/container/heavy_workbench.png");
    private final Seq<BlueprintData> renderedRecipes = Seq.with();
    private BlueprintData hoveredRecipe = null;
    private BlueprintData clickedBlueprintO;
    private long lastClickTime = 0;

    private final int blueprintAreaLeft = 10;
    private final int blueprintAreaTop = 6;
    private final int blueprintAreaWidth = 44;
    private final int blueprintAreaHeight = 29;
    private final int scrollbarHeight = 84;

    private float scrollDistance = 0.0F;
    private final int visible = 3;

    private final int recipeAreaWidth = 174;
    private final int recipeAreaHeight = 94;
    private final int gridXOffset = 6;
    private final int gridYOffset = 2;
    private final int recipeRowHeight = this.blueprintAreaHeight + this.gridYOffset;

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
            if (this.menu.checkAndSetAvailability(recipeHolder)) {
                craftableRecipes.add(data); // craftables at top
            } else {
                nonCraftableRecipes.add(data);
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
                if(!recipe.getInputs().isEmpty()) comp = Optional.of(new ClientMaterialListClientComponent(recipe.getInputs()));
                if(!Screen.hasShiftDown()){
                    guiGraphics.renderTooltip(this.font, tooltip, comp, mouseX, mouseY);
                } else {
                    guiGraphics.renderTooltip(this.font, result.getTooltipLines(null, TooltipFlag.NORMAL), Optional.empty(), result, mouseX, mouseY);
                }
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
        scissorsOn(gui, gui.pose(), recipeAreaX, recipeAreaY+19, this.recipeAreaWidth, this.recipeAreaHeight);
        renderGrid(gui, left, top);
        scissorsOff(gui);

        gui.pose().pushPose();
        gui.pose().translate(0, 0, 180);
        if(scrollDistance > 0) gui.fillGradient(left + 3, top + 3, left + 173, top + 25, new Color(0, 0, 0, 0.45f).getRGB(), new Color(1, 0, 0, 0).getRGB());
        if(scrollDistance < maxScroll) gui.fillGradient(left + 3, top + 80, left + 173, top + 98, new Color(1, 0, 0, 0).getRGB(), new Color(0, 0, 0, 0.45f).getRGB());
        gui.pose().popPose();
    }

    public boolean isVisible(GuiGraphics gui, int x, int y) {
        return ValoriaUtils.isVisibleInScissor(gui, x, y, blueprintAreaWidth, blueprintAreaHeight, getRecipeAreaX(this.getGuiLeft()), getRecipeAreaY(this.getGuiTop()), recipeAreaWidth, recipeAreaHeight - 10);
    }

    private void renderGrid(GuiGraphics gui, int left, int top){
        for(int i = 0; i < this.renderedRecipes.size; i++){
            int row = i / this.visible;
            int col = i % this.visible;

            BlueprintData blueprintData = this.renderedRecipes.get(i);
            WorkbenchRecipe recipe = blueprintData.recipe;
            int x = left + this.blueprintAreaLeft + col * (this.blueprintAreaWidth + this.gridXOffset);
            int y = top + this.blueprintAreaTop + row * this.recipeRowHeight - (int)this.scrollDistance;
            if(isVisible(gui, x, y)){
                blueprintData.setVisible(true);
                if(this.hoveredRecipe != null && recipe.equals(this.hoveredRecipe.recipe)){
                    gui.blit(TEXTURE, x - 1, y - 1, 0,  196 + this.blueprintAreaHeight, this.blueprintAreaWidth + 2, this.blueprintAreaHeight + 2);
                }

                gui.blit(TEXTURE, x, y, this.menu.checkAndSetAvailability(recipe) ? 0 : blueprintAreaWidth, 196, this.blueprintAreaWidth, this.blueprintAreaHeight);
                ItemStack result = recipe.getResultItem(RegistryAccess.EMPTY);
                gui.renderFakeItem(result, x + 14, y + 6);
            } else {
                blueprintData.setVisible(false);
            }
        }
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        int maxScroll = this.getMaxScroll();
        if (maxScroll > 0) {
            this.scrollDistance = Mth.clamp(this.scrollDistance - (float)delta * this.recipeRowHeight, 0.0F, maxScroll);
            return true;
        }
        return super.mouseScrolled(mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        BlueprintData clickedBlueprint = hoveredRecipe;
        if (button == 0) {
            if (clickedBlueprint != null && clickedBlueprint.isVisible()) {
                if (clickedBlueprintO == clickedBlueprint && this.menu.checkAndSetAvailability(clickedBlueprint.recipe) && System.currentTimeMillis() - this.lastClickTime < 450) {
                    minecraft.player.playSound(SoundsRegistry.CRYSTAL_ACID.get());
                    PacketHandler.sendToServer(new HeavyWorkbenchCraftPacket(this.hoveredRecipe.recipe));

                    this.lastClickTime = 0;
                    return true;
                } else {
                    clickedBlueprintO = hoveredRecipe;
                    this.lastClickTime = System.currentTimeMillis();
                    minecraft.player.playSound(SoundsRegistry.UI_CLICK.get());
                }
            }

            return true;
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

    private BlueprintData getBlueprintAt(double mouseX, double mouseY) {
        int left = this.getGuiLeft();
        int top = this.getGuiTop();
        int mouseXRelative = (int) mouseX - left;
        int mouseYRelative = (int) mouseY - top;

        int viewportX = this.blueprintAreaLeft;
        int viewportY = this.blueprintAreaTop;
        int viewportWidth = this.visible * (this.blueprintAreaWidth + this.gridXOffset);
        if (mouseXRelative < viewportX || mouseXRelative >= viewportX + viewportWidth || mouseYRelative < viewportY || mouseYRelative >= viewportY + this.recipeAreaHeight) {
            return null;
        }

        for (int i = 0; i < this.renderedRecipes.size; i++) {
            int row = i / this.visible;
            int col = i % this.visible;

            int itemX = this.blueprintAreaLeft + col * (this.blueprintAreaWidth + this.gridXOffset);
            int itemY = this.blueprintAreaTop + row * this.recipeRowHeight - (int)this.scrollDistance;
            if (mouseXRelative >= itemX && mouseXRelative < itemX + this.blueprintAreaWidth && mouseYRelative >= itemY && mouseYRelative < itemY + this.blueprintAreaHeight) {
                return this.renderedRecipes.get(i);
            }
        }

        return null;
    }

    public int getMaxScroll() {
        int totalContentHeight = (int) Math.ceil((double) this.renderedRecipes.size / this.visible) * this.recipeRowHeight;
        return Math.max(0, totalContentHeight - this.recipeAreaHeight);
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