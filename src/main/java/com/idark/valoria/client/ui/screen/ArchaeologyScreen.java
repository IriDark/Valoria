package com.idark.valoria.client.ui.screen;

import com.idark.valoria.*;
import com.idark.valoria.client.ui.menus.*;
import com.idark.valoria.registries.item.recipe.*;
import com.idark.valoria.util.*;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.screens.inventory.*;
import net.minecraft.client.gui.screens.inventory.tooltip.*;
import net.minecraft.client.resources.language.*;
import net.minecraft.client.resources.sounds.*;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.*;
import net.minecraft.sounds.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraftforge.api.distmarker.*;

import java.awt.*;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class ArchaeologyScreen extends AbstractContainerScreen<ArchaeologyMenu>{
    private final ResourceLocation BG_LOCATION = new ResourceLocation(Valoria.ID, "textures/gui/container/archaeology.png");
    private float scrollOffs;
    /**
     * Is {@code true} if the player clicked on the scroll wheel in the GUI.
     */
    private boolean scrolling;
    /**
     * The index of the first recipe to display.
     * The number of recipes displayed at any time is 12 (4 recipes per row, and 3 rows). If the player scrolled down one
     * row, this value would be 4 (representing the index of the first slot on the second row).
     */
    private int startIndex;
    private boolean displayRecipes;

    public ArchaeologyScreen(ArchaeologyMenu screenContainer, Inventory inv, Component titleIn){
        super(screenContainer, inv, titleIn);
        screenContainer.registerUpdateListener(this::containerChanged);
        --this.titleLabelY;

        this.imageHeight = 165;
    }

    /**
     * Renders the graphical user interface (GUI) element.
     *
     * @param pGuiGraphics the GuiGraphics object used for rendering.
     * @param pMouseX      the x-coordinate of the mouse cursor.
     * @param pMouseY      the y-coordinate of the mouse cursor.
     * @param pPartialTick the partial tick time.
     */
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick){
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        this.renderTooltip(pGuiGraphics, pMouseX, pMouseY);
    }

    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY){
        this.renderBackground(pGuiGraphics);
        int i = this.leftPos;
        int j = this.topPos;
        pGuiGraphics.blit(BG_LOCATION, i, j, 0, 0, this.imageWidth, this.imageHeight);
        int k = (int)(41.0F * this.scrollOffs);
        pGuiGraphics.blit(BG_LOCATION, i + 119, j + 15 + k, 176 + (this.isScrollBarActive() ? 0 : 12), 0, 12, 15);
        int l = this.leftPos + 52;
        int i1 = this.topPos + 14;
        int j1 = this.startIndex + 12;
        this.renderButtons(pGuiGraphics, pMouseX, pMouseY, l, i1, j1);
        this.renderRecipes(pGuiGraphics, l, i1, j1);
    }

    private void renderFloatingItem(GuiGraphics pGuiGraphics, ItemStack pStack, int pX, int pY){
        pGuiGraphics.pose().pushPose();
        pGuiGraphics.pose().translate(0.0F, 0.0F, 232.0F);
        pGuiGraphics.renderItem(pStack, pX, pY);
        var font = net.minecraftforge.client.extensions.common.IClientItemExtensions.of(pStack).getFont(pStack, net.minecraftforge.client.extensions.common.IClientItemExtensions.FontContext.ITEM_COUNT);
        pGuiGraphics.renderItemDecorations(font == null ? this.font : font, pStack, pX, pY, String.valueOf(pStack.getCount()));
        pGuiGraphics.pose().popPose();
    }

    protected void renderTooltip(GuiGraphics pGuiGraphics, int pX, int pY){
        super.renderTooltip(pGuiGraphics, pX, pY);
        if(this.displayRecipes){
            int i = this.leftPos + 52;
            int j = this.topPos + 14;
            int k = this.startIndex + 12;
            List<ArchaeologyRecipe> list = this.menu.getRecipes();
            for(int l = this.startIndex; l < k && l < this.menu.getNumRecipes(); ++l){
                int i1 = l - this.startIndex;
                int j1 = i + i1 % 4 * 16;
                int k1 = j + i1 / 4 * 18 + 2;
                if(pX >= j1 && pX < j1 + 16 && pY >= k1 && pY < k1 + 18){
                    ItemStack stack = this.menu.container.getItem(0).copy();
                    if(this.menu.getCurrentRecipe() != null && !this.menu.getCurrentRecipe().canCraft(this.menu.container)){
                        String component = I18n.get("tooltip.valoria.required_amount");
                        PoseStack ms = pGuiGraphics.pose();
                        TooltipRenderUtil.renderTooltipBackground(pGuiGraphics, pX + 12, pY + 30, Minecraft.getInstance().font.width(component) + 20, 15, 300, Color.BLACK.getRGB(), Pal.darkerGray.rgb(), Pal.darkishGray.rgb(), Pal.lightishGray.darker().rgb());
                        ms.translate(pX, pY, 300);
                        renderFloatingItem(pGuiGraphics, stack.copyWithCount(this.menu.getCurrentRecipe().getIngredientCount()), Minecraft.getInstance().font.width(component) + 12, 30);
                        pGuiGraphics.drawString(this.font, component, 14, 34, Color.WHITE.getRGB());
                        ms.popPose();
                    }

                    pGuiGraphics.renderTooltip(this.font, list.get(l).getResultItem(this.minecraft.level.registryAccess()), pX, pY);
                }
            }
        }
    }

    private void renderButtons(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, int pX, int pY, int pLastVisibleElementIndex){
        for(int i = this.startIndex; i < pLastVisibleElementIndex && i < this.menu.getNumRecipes(); ++i){
            int j = i - this.startIndex;
            int k = pX + j % 4 * 16;
            int l = j / 4;
            int i1 = pY + l * 18 + 2;
            int j1 = 166;
            if(this.menu.getCurrentRecipe() != null && !this.menu.getCurrentRecipe().canCraft(this.menu.container)) pGuiGraphics.setColor(1, 0, 0, 1);
            if(i == this.menu.getSelectedRecipeIndex()){
                j1 += 18;
            }else if(pMouseX >= k && pMouseY >= i1 && pMouseX < k + 16 && pMouseY < i1 + 18){
                j1 += 36;
            }

            pGuiGraphics.blit(BG_LOCATION, k, i1 - 1, 0, j1, 16, 18);
            pGuiGraphics.setColor(1, 1, 1, 1);
        }
    }

    private void renderRecipes(GuiGraphics pGuiGraphics, int pX, int pY, int pStartIndex){
        List<ArchaeologyRecipe> list = this.menu.getRecipes();
        for(int i = this.startIndex; i < pStartIndex && i < this.menu.getNumRecipes(); ++i){
            int j = i - this.startIndex;
            int k = pX + j % 4 * 16;
            int l = j / 4;
            int i1 = pY + l * 18 + 2;
            pGuiGraphics.renderItem(list.get(i).getResultItem(this.minecraft.level.registryAccess()), k, i1);
        }
    }

    /**
     * Called when a mouse button is clicked within the GUI element.
     * <p>
     *
     * @param pMouseX the X coordinate of the mouse.
     * @param pMouseY the Y coordinate of the mouse.
     * @param pButton the button that was clicked.
     * @return {@code true} if the event is consumed, {@code false} otherwise.
     */
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton){
        this.scrolling = false;
        if(this.displayRecipes){
            int i = this.leftPos + 52;
            int j = this.topPos + 14;
            int k = this.startIndex + 12;

            for(int l = this.startIndex; l < k; ++l){
                int i1 = l - this.startIndex;
                double d0 = pMouseX - (double)(i + i1 % 4 * 16);
                double d1 = pMouseY - (double)(j + i1 / 4 * 18);
                if(d0 >= 0.0D && d1 >= 0.0D && d0 < 16.0D && d1 < 18.0D && this.menu.clickMenuButton(this.minecraft.player, l)){
                    Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                    this.minecraft.gameMode.handleInventoryButtonClick((this.menu).containerId, l);
                    return true;
                }
            }

            i = this.leftPos + 119;
            j = this.topPos + 9;
            if(pMouseX >= (double)i && pMouseX < (double)(i + 12) && pMouseY >= (double)j && pMouseY < (double)(j + 54)){
                this.scrolling = true;
            }
        }

        return super.mouseClicked(pMouseX, pMouseY, pButton);
    }

    /**
     * Called when the mouse is dragged within the GUI element.
     * <p>
     *
     * @param pMouseX the X coordinate of the mouse.
     * @param pMouseY the Y coordinate of the mouse.
     * @param pButton the button that is being dragged.
     * @param pDragX  the X distance of the drag.
     * @param pDragY  the Y distance of the drag.
     * @return {@code true} if the event is consumed, {@code false} otherwise.
     */
    public boolean mouseDragged(double pMouseX, double pMouseY, int pButton, double pDragX, double pDragY){
        if(this.scrolling && this.isScrollBarActive()){
            int i = this.topPos + 14;
            int j = i + 54;
            this.scrollOffs = ((float)pMouseY - (float)i - 7.5F) / ((float)(j - i) - 15.0F);
            this.scrollOffs = Mth.clamp(this.scrollOffs, 0.0F, 1.0F);
            this.startIndex = (int)((double)(this.scrollOffs * (float)this.getOffscreenRows()) + 0.5D) * 4;
            return true;
        }else{
            return super.mouseDragged(pMouseX, pMouseY, pButton, pDragX, pDragY);
        }
    }

    /**
     * Called when the mouse wheel is scrolled within the GUI element.
     * <p>
     *
     * @param pMouseX the X coordinate of the mouse.
     * @param pMouseY the Y coordinate of the mouse.
     * @param pDelta  the scrolling delta.
     * @return {@code true} if the event is consumed, {@code false} otherwise.
     */
    public boolean mouseScrolled(double pMouseX, double pMouseY, double pDelta){
        if(this.isScrollBarActive()){
            int i = this.getOffscreenRows();
            float f = (float)pDelta / (float)i;
            this.scrollOffs = Mth.clamp(this.scrollOffs - f, 0.0F, 1.0F);
            this.startIndex = (int)((double)(this.scrollOffs * (float)i) + 0.5D) * 4;
        }

        return true;
    }

    private boolean isScrollBarActive(){
        return this.displayRecipes && this.menu.getNumRecipes() > 12;
    }

    protected int getOffscreenRows(){
        return (this.menu.getNumRecipes() + 4 - 1) / 4 - 3;
    }

    /**
     * Called every time this screen's container is changed (is marked as dirty).
     */
    private void containerChanged(){
        this.displayRecipes = this.menu.hasInputItem();
        if(!this.displayRecipes){
            this.scrollOffs = 0.0F;
            this.startIndex = 0;
        }

    }
}