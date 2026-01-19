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
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.components.*;
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
import org.jetbrains.annotations.*;
import pro.komaru.tridot.util.*;
import pro.komaru.tridot.util.phys.*;
import pro.komaru.tridot.util.struct.data.*;

import java.util.*;

public class AlchemyStationScreen extends AbstractContainerScreen<AlchemyStationMenu>{
    private static final ResourceLocation TEXTURE = Valoria.loc("textures/gui/container/heavy_workbench.png");
    private static final Component SEARCH_HINT = Component.translatable("gui.recipebook.search_hint").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY);
    private final Seq<BlueprintData> renderedRecipes = Seq.with();
    private final int recipesOffsetX = 6;
    private final int recipesOffsetY = 18;
    private final int recipeSlotWidth = 20;
    private final int recipeSlotHeight = 19;
    private final int scrollbarHeight = 84;
    private final int visible = 7;
    private final int recipeAreaWidth = 175;
    private final int recipeAreaHeight = 85;
    private final int gridXOffset = 2;
    private final int gridYOffset = 2;
    private final int maxLevel = 4;
    private final int slotSize = recipeSlotWidth + gridXOffset;
    private final int recipeRowHeight = this.recipeSlotHeight + 1 + this.gridYOffset;
    private BlueprintData hoveredRecipe = null;
    private BlueprintData clickedBlueprintO;
    private AlchemyCategories chosen = AlchemyCategories.ALL;
    private String lastSearch = "";
    private int errorVariant = 0;
    private EditBox searchBox;
    private final ContainerListener listener = new ContainerListener(){
        public void slotChanged(AbstractContainerMenu menu, int index, ItemStack stack){
            updateCraftableRecipes();
        }

        public void dataChanged(AbstractContainerMenu menu, int index, int value){
        }
    };
    private boolean scrolling = false;
    private float scrollDistance = 0.0F;

    public AlchemyStationScreen(AlchemyStationMenu menu, Inventory inventory, Component title){
        super(menu, inventory, title);
        this.imageHeight = 196;
        this.imageWidth = 176;
        this.menu.addSlotListener(this.listener);
        if(inventory.isEmpty()) updateCraftableRecipes(); // add recipes even if slots has no update
    }

    public void init(){
        super.init();
        String s = getSearchValue();
        this.searchBox = new EditBox(this.minecraft.font, getGuiLeft() + 4, getGuiTop() - 20, recipeAreaWidth - 4, 9 + 3, Component.translatable("itemGroup.search"));
        this.searchBox.setMaxLength(50);
        this.searchBox.setVisible(true);
        this.searchBox.setTextColor(16777215);
        this.searchBox.setValue(s);
        this.searchBox.setHint(SEARCH_HINT);
        this.addWidget(this.searchBox);
    }

    private @NotNull String getSearchValue(){
        return this.searchBox != null ? this.searchBox.getValue() : "";
    }

    @Override
    public boolean charTyped(char pCodePoint, int pModifiers){
        if(this.searchBox != null && this.searchBox.isFocused()){
            String s = getSearchValue();
            if(this.searchBox.charTyped(pCodePoint, pModifiers)){
                if(!Objects.equals(s, this.searchBox.getValue())){
                    playSound(SoundEvents.BAMBOO_HIT, 0.25f, 1);
                    this.updateCraftableRecipes();
                }

                return true;
            }
        }

        return super.charTyped(pCodePoint, pModifiers);
    }

    @Override
    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers){
        String s = this.searchBox.getValue();
        if(this.searchBox.keyPressed(pKeyCode, pScanCode, pModifiers)){
            if(!Objects.equals(s, this.searchBox.getValue())){
                playSound(SoundEvents.BAMBOO_HIT, 0.25f, 1);
                this.updateCraftableRecipes();
            }

            return true;
        }else{
            return (this.searchBox != null && this.searchBox.isFocused() && this.searchBox.isVisible() && pKeyCode != 256) || super.keyPressed(pKeyCode, pScanCode, pModifiers);
        }
    }

    @Override
    public void removed(){
        super.removed();
        this.menu.removeSlotListener(this.listener);
    }

    public float getScrollPercent(){
        return scrollDistance / slotSize;
    }

    private String getRecipeName(AlchemyRecipe recipe){
        ItemStack resultStack = recipe.getResultItem(this.minecraft.level.registryAccess());
        if(resultStack.isEmpty()){
            return "";
        }

        return resultStack.getHoverName().getString();
    }

    public void updateCraftableRecipes(){
        Seq<BlueprintData> craftableRecipes = Seq.with();
        Seq<BlueprintData> nonCraftableRecipes = Seq.with();
        for(var recipeHolder : this.menu.getAllRecipes()){
            String s = getSearchValue();
            BlueprintData data = new BlueprintData(recipeHolder);
            if(s.isEmpty()){
                if(recipeHolder.getCategory().equals(chosen.category) || chosen.equals(AlchemyCategories.ALL)){
                    addRecipes(recipeHolder, craftableRecipes, data, nonCraftableRecipes);
                }
            }else{
                String recipeName = getRecipeName(recipeHolder).toLowerCase();
                boolean searchMatch = recipeName.contains(s.toLowerCase());
                if(searchMatch){
                    if(recipeHolder.getCategory().equals(chosen.category) || chosen.equals(AlchemyCategories.ALL)) addRecipes(recipeHolder, craftableRecipes, data, nonCraftableRecipes);
                }
            }
        }

        craftableRecipes.sort(Comparator.comparing((b) -> b.recipe.getId()));
        nonCraftableRecipes.sort(Comparator.comparing((b) -> b.recipe.getId()));
        renderedRecipes.clear();
        renderedRecipes.addAll(craftableRecipes).addAll(nonCraftableRecipes);
    }

    private void addRecipes(AlchemyRecipe recipe, Seq<BlueprintData> craftableRecipes, BlueprintData data, Seq<BlueprintData> nonCraftableRecipes){
        if(this.menu.checkAndSetAvailability(recipe) && menu.getBlockLevel() >= recipe.getLevel()){
            craftableRecipes.add(data);
        }else{
            data.setShouldUpgrade(menu.getBlockLevel() < recipe.getLevel());
            nonCraftableRecipes.add(data);
        }
    }

    @Override
    protected void containerTick(){
        super.containerTick();
        this.searchBox.tick();
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick){
        this.hoveredRecipe = getBlueprintAt(mouseX, mouseY);
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY){
        pGuiGraphics.drawString(this.font, this.title.copy().append(": ").append(Component.translatable("menu.valoria.category." + chosen.category)), this.titleLabelX, this.titleLabelY + 2, CommonColors.GRAY, false);
        pGuiGraphics.drawString(this.font, this.playerInventoryTitle, this.inventoryLabelX, this.inventoryLabelY + 30, 4210752, false);
    }

    @Override
    protected void renderTooltip(GuiGraphics guiGraphics, int mouseX, int mouseY){
        super.renderTooltip(guiGraphics, mouseX, mouseY);
        if(this.hoveredRecipe != null){
            if(hoveredRecipe.isVisible()){
                var recipe = hoveredRecipe.recipe;
                List<Component> tooltip = new ArrayList<>();
                Optional<TooltipComponent> comp = Optional.empty();

                ItemStack result = recipe.getResultItem(RegistryAccess.EMPTY);
                tooltip.add(result.getHoverName().copy().withStyle(result.getDisplayName().getStyle()));
                tooltip.add(Component.empty());
                tooltip.add(Component.translatable("tooltip.tridot.shift_for_details", Component.translatable("key.keyboard.left.shift").getString()).withStyle(ChatFormatting.GRAY));
                if(hoveredRecipe.shouldUpgrade()){
                    tooltip.add(Component.empty());
                    tooltip.add(Component.translatable("tooltip.valoria.alchemy_station.upgrade", this.menu.getBlockLevel() + "/" + hoveredRecipe.recipe.getLevel()).withStyle(ChatFormatting.BOLD).withStyle(ChatFormatting.RED));
                }

                if(!recipe.getInputs().isEmpty()){
                    this.menu.checkAndSetAvailability(recipe);
                    comp = Optional.of(new MaterialListComponent(recipe.getInputs()));
                }else{
                    Valoria.LOGGER.warn("Recipe - {}, has no Inputs", hoveredRecipe.recipe.getId());
                }

                if(!Screen.hasShiftDown()){
                    guiGraphics.renderTooltip(this.font, tooltip, comp, mouseX, mouseY);
                }else{
                    guiGraphics.renderTooltip(this.font, result.getTooltipLines(null, TooltipFlag.NORMAL), Optional.empty(), result, mouseX, mouseY);
                }
            }
        }

        if(canUpgrade()){
            int x = width / 2;
            int y = height / 2;
            if(isHover(mouseX, mouseY, x - 45, y - 27, 80, 20)){
                var optRecipe = menu.getUpgrade(getUpgradeLoc());
                if(optRecipe.isPresent()){
                    List<Component> tooltip = new ArrayList<>();
                    tooltip.add(Component.translatable("menu.valoria.upgrade").withStyle(ChatFormatting.YELLOW));
                    Optional<TooltipComponent> comp = Optional.of(new MaterialListComponent(optRecipe.get().getInputs()));

                    this.menu.checkAndSetAvailability(optRecipe.get());
                    guiGraphics.renderTooltip(this.font, tooltip, comp, mouseX, mouseY);
                }
            }
        }

        for(AlchemyCategories category : AlchemyCategories.values()){
            int x = getGuiLeft() - 19;
            int y = (getGuiTop() + 6) + category.ordinal() * slotSize;
            if(isHover(mouseX, mouseY, x, y, slotSize, 20)){
                List<Component> tooltip = new ArrayList<>();
                tooltip.add(Component.translatable("menu.valoria.category." + category.category));
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
        renderScroll(gui, scrollbarLeft, scrollbarTop, mouseX, mouseY);
        this.searchBox.render(gui, mouseX, mouseY, partialTick);

        int recipeAreaX = getRecipeAreaX(left);
        int recipeAreaY = getRecipeAreaY(top);
        scissorsOn(gui, gui.pose(), recipeAreaX, recipeAreaY + 25, this.recipeAreaWidth, this.recipeAreaHeight);
        renderGrid(gui, left, top);
        scissorsOff(gui);

        String s = getSearchValue();
        int x = width / 2;
        int y = height / 2;
        if(renderedRecipes.isEmpty()){
            if(!lastSearch.equals(s)){
                errorVariant = Tmp.rnd.nextInt(0, 11);
                lastSearch = s;
            }

            gui.drawCenteredString(Minecraft.getInstance().font, Component.translatable("tooltip.valoria.not_found." + errorVariant), x - 5, y - 50, CommonColors.WHITE);
        }

        if(canUpgrade()){
            if(isHover(mouseX, mouseY, x - 45, y - 27, 80, 20)){
                gui.blit(TEXTURE, x - 46, y - 28, 59, 216, 82, 22);
            }

            gui.blit(TEXTURE, x - 45, y - 27, 60, 196, 80, 20);
            gui.drawCenteredString(Minecraft.getInstance().font, Component.translatable("menu.valoria.upgrade"), x - 5, y - 22, CommonColors.WHITE);
        }

        for(AlchemyCategories category : AlchemyCategories.values()){
            int catX = left - 19;
            int catY = (top + 6) + category.ordinal() * slotSize;
            boolean flag = isHover(mouseX, mouseY, catX, catY, slotSize, 20);
            if(!flag && chosen != category){
                gui.blit(TEXTURE, catX, catY, 234, 0, slotSize, 20);
                gui.renderFakeItem(category.item.getDefaultInstance(), catX + 3, catY + 2);
            }else{
                gui.blit(TEXTURE, catX - 6, catY, 228, 20, 28, 20);
                gui.renderFakeItem(category.item.getDefaultInstance(), catX - 3, catY + 2);
            }
        }
    }

    public boolean insideScrollbar(int scrollbarLeft, int scrollbarTop, int mouseX, int mouseY){
        int maxScroll = this.getMaxScroll();
        float scrollPercentage = maxScroll > 0 ? getScrollPercent() / maxScroll : 0.0F;
        int scrollbarThumbY = (int)(scrollPercentage * (scrollbarHeight - 10));
        return isHover(mouseX, mouseY, scrollbarLeft, scrollbarTop + scrollbarThumbY, 7, 15);
    }

    public void renderScroll(GuiGraphics gui, int scrollbarLeft, int scrollbarTop, int mouseX, int mouseY){
        gui.blit(TEXTURE, scrollbarLeft, scrollbarTop, 176, 0, 7, scrollbarHeight);
        int maxScroll = this.getMaxScroll();
        float scrollPercentage = maxScroll > 0 ? getScrollPercent() / maxScroll : 0.0F;
        int scrollbarThumbY = (int)(scrollPercentage * (scrollbarHeight - 10));
        if(insideScrollbar(scrollbarLeft, scrollbarTop, mouseX, mouseY)){
            gui.blit(TEXTURE, scrollbarLeft, scrollbarTop + scrollbarThumbY, 183 + 7, 0, 7, 15);
        }else{
            gui.blit(TEXTURE, scrollbarLeft, scrollbarTop + scrollbarThumbY, 183, 0, 7, 15);
        }
    }

    public boolean isHover(double mouseX, double mouseY, int x, int y, int width, int height){
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }

    public boolean isVisible(GuiGraphics gui, int x, int y){
        return ValoriaUtils.isVisibleInScissor(gui, x, y, recipeSlotWidth, recipeSlotHeight, getRecipeAreaX(this.getGuiLeft()), getRecipeAreaY(this.getGuiTop()), recipeAreaWidth, recipeAreaHeight);
    }

    private void renderGrid(GuiGraphics gui, int left, int top){
        for(int i = 0; i < this.renderedRecipes.size; i++){
            int row = i / this.visible;
            int col = i % this.visible;

            BlueprintData blueprintData = this.renderedRecipes.get(i);
            AlchemyRecipe recipe = blueprintData.recipe;
            int x = left + this.recipesOffsetX + col * (this.recipeSlotWidth + this.gridXOffset);
            int y = top + this.recipesOffsetY + row * this.recipeRowHeight - (int)this.scrollDistance;
            if(isVisible(gui, x, y)){
                blueprintData.setVisible(true);
                if(this.hoveredRecipe != null && recipe.equals(this.hoveredRecipe.recipe)){
                    gui.blit(TEXTURE, x - 1, y - 1, 0, 215, this.recipeSlotWidth + 2, this.recipeSlotHeight + 2);
                }

                boolean isClicked = clickedBlueprintO == blueprintData;
                gui.blit(TEXTURE, x, y, isClicked ? 0 : this.menu.checkAndSetAvailability(recipe) ? 20 : 40, 196, this.recipeSlotWidth, this.recipeSlotHeight);
                ItemStack result = recipe.getResultItem(RegistryAccess.EMPTY);
                gui.renderFakeItem(result, x + 2, y + 2);
            }else{
                blueprintData.setVisible(false);
            }
        }
    }

    public boolean mouseReleased(double pMouseX, double pMouseY, int pButton){
        if(pButton == 0) this.scrolling = false;
        return super.mouseReleased(pMouseX, pMouseY, pButton);
    }

    public boolean mouseDragged(double pMouseX, double pMouseY, int pButton, double pDragX, double pDragY){
        if(this.scrolling){
            final int scrollStepHeight = slotSize;
            int maxScrollSteps = this.getMaxScroll();
            double maxScrollPixels = maxScrollSteps * scrollStepHeight;
            this.scrollDistance += (float)pDragY;
            this.scrollDistance = (float)Math.max(0.0, this.scrollDistance);
            this.scrollDistance = (float)Math.min(this.scrollDistance, maxScrollPixels);
            return true;
        }else{
            return super.mouseDragged(pMouseX, pMouseY, pButton, pDragX, pDragY);
        }
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta){
        int maxScroll = this.getMaxScroll();
        if(getScrollPercent() < maxScroll && delta == -1){
            this.scrollDistance += slotSize;
        }else if(scrollDistance > 0 && delta == 1){
            this.scrollDistance -= slotSize;
        }

        if(getScrollPercent() > maxScroll){
            scrollDistance = maxScroll * slotSize;
        }else if(scrollDistance < 0){
            scrollDistance = 0;
        }

        return super.mouseScrolled(mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button){
        BlueprintData clickedBlueprint = hoveredRecipe;
        int left = this.getGuiLeft();
        int top = this.getGuiTop();
        if(button == 0){
            if(clickedBlueprint != null && clickedBlueprint.isVisible()){
                if(clickedBlueprintO == clickedBlueprint && this.menu.checkAndSetAvailability(clickedBlueprint.recipe)){
                    playSound(SoundsRegistry.UI_ALCHEMY_BREW.get());
                    PacketHandler.sendToServer(new AlchemyCraftPacket(this.hoveredRecipe.recipe));
                    return true;
                }else{
                    clickedBlueprintO = hoveredRecipe;
                    playSound(SoundsRegistry.UI_CLICK.get(), 0.15f, 2);
                }
            }

            if(canUpgrade()){
                int x = width / 2;
                int y = height / 2;
                if(isHover(mouseX, mouseY, x - 45, y - 27, 80, 20)){
                    PacketHandler.sendToServer(new AlchemyUpgradeTryPacket(getUpgradeLoc()));
                    playSound(SoundsRegistry.UI_CLICK.get(), 0.15f, 2);
                }
            }

            int scrollbarTop = top + 8;
            int scrollbarLeft = left + 163;
            if(this.insideScrollbar(scrollbarLeft, scrollbarTop, (int)mouseX, (int)mouseY)){
                this.scrolling = true;
            }

            for(AlchemyCategories category : AlchemyCategories.values()){
                int x = left - 19;
                int y = (top + 6) + category.ordinal() * slotSize;
                if(isHover(mouseX, mouseY, x, y, 24, 20)){
                    this.chosen = category;
                    scrollDistance = 0;
                    playSound(SoundsRegistry.UI_CLICK.get(), 0.25f, 1.65f);
                    updateCraftableRecipes();
                }
            }
        }

        if(this.searchBox != null && this.searchBox.isActive()) this.searchBox.setFocused(false);
        return super.mouseClicked(mouseX, mouseY, button);
    }

    private @NotNull ResourceLocation getUpgradeLoc(){
        return Valoria.loc("alchemy/upgrade/alchemy_upgrade_" + menu.getBlockLevel());
    }

    private boolean canUpgrade(){
        return menu.getBlockLevel() < maxLevel;
    }

    public void scissorsOn(GuiGraphics gui, PoseStack pose, int x, int y, int w, int h){
        AbsRect r = AbsRect.xywhDef((float)x, (float)y, (float)w, (float)h).pose(pose);
        gui.enableScissor((int)r.x, (int)r.y + 2, (int)r.x2, (int)r.y2 - 8);
    }

    public void scissorsOff(GuiGraphics gui){
        gui.disableScissor();
    }

    private BlueprintData getBlueprintAt(double mouseX, double mouseY){
        int left = this.getGuiLeft();
        int top = this.getGuiTop();
        int mouseXRelative = (int)mouseX - left;
        int mouseYRelative = (int)mouseY - top;

        int viewportX = this.recipesOffsetX;
        int viewportY = this.recipesOffsetY;
        int viewportWidth = this.visible * (this.recipeSlotWidth + this.gridXOffset);
        if(mouseXRelative < viewportX || mouseXRelative >= viewportX + viewportWidth || mouseYRelative < viewportY || mouseYRelative >= viewportY + this.recipeAreaHeight){
            return null;
        }

        for(int i = 0; i < this.renderedRecipes.size; i++){
            int row = i / this.visible;
            int col = i % this.visible;

            int itemX = this.recipesOffsetX + col * (this.recipeSlotWidth + this.gridXOffset);
            int itemY = this.recipesOffsetY + row * this.recipeRowHeight - (int)this.scrollDistance;
            if(mouseXRelative >= itemX && mouseXRelative < itemX + this.recipeSlotWidth && mouseYRelative >= itemY && mouseYRelative < itemY + this.recipeSlotHeight){
                return this.renderedRecipes.get(i);
            }
        }

        return null;
    }

    public int getMaxScroll(){
        int total = (int)Math.ceil((double)this.renderedRecipes.size / this.visible);
        return Math.max(0, total - 1);
    }

    public int getRecipeAreaY(int top){
        return top + 33;
    }

    public int getRecipeAreaX(int left){
        return left + 88;
    }

    public void playSound(SoundEvent soundEvent){
        minecraft.player.playSound(soundEvent);
    }

    public void playSound(SoundEvent soundEvent, float vol, float pitch){
        minecraft.player.playSound(soundEvent, vol, pitch);
    }

    public static class BlueprintData{
        private final AlchemyRecipe recipe;
        private boolean visible = true;
        private boolean shouldUpgrade = false;

        public BlueprintData(AlchemyRecipe recipe){
            this.recipe = recipe;
        }

        public boolean shouldUpgrade(){
            return this.shouldUpgrade;
        }

        public boolean isVisible(){
            return this.visible;
        }

        public void setVisible(boolean visible){
            this.visible = visible;
        }

        public void setShouldUpgrade(boolean shouldUpgrade){
            this.shouldUpgrade = shouldUpgrade;
        }
    }

}