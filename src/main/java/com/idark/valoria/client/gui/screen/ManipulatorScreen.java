package com.idark.valoria.client.gui.screen;

import com.idark.valoria.*;
import com.idark.valoria.registries.block.entity.*;
import com.idark.valoria.registries.menus.*;
import com.mojang.blaze3d.systems.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.screens.inventory.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraftforge.api.distmarker.*;

@OnlyIn(Dist.CLIENT)
public class ManipulatorScreen extends AbstractContainerScreen<ManipulatorMenu>{
    private final ResourceLocation GUI = new ResourceLocation(Valoria.ID, "textures/gui/container/manipulator.png");

    public ManipulatorScreen(ManipulatorMenu screenContainer, Inventory inv, Component titleIn){
        super(screenContainer, inv, titleIn);
        this.imageHeight = 165;
        this.inventoryLabelY = this.inventoryLabelY + 46;
    }

    @Override
    public void render(GuiGraphics gui, int mouseX, int mouseY, float partialTicks){
        this.renderBackground(gui);
        super.render(gui, mouseX, mouseY, partialTicks);
        this.renderTooltip(gui, mouseX, mouseY);
    }

    @Override
    public void renderTooltip(GuiGraphics pGuiGraphics, int pX, int pY){
        if(this.menu.getCarried().isEmpty() && this.hoveredSlot != null && this.hoveredSlot.hasItem()){
            ItemStack itemstack = this.hoveredSlot.getItem();
            pGuiGraphics.renderTooltip(this.font, this.getTooltipFromContainerItem(itemstack), itemstack.getTooltipImage(), itemstack, pX, pY);
        }

        if(this.menu.tileEntity instanceof ManipulatorBlockEntity tile){
            int i = this.leftPos;
            int j = this.topPos;
            if(pX >= i + 16 && pX < i + 16 + 8 && pY >= j + 25 && pY < j + 25 + 8){
                renderTooltip(pGuiGraphics, Component.translatable("tooltip.valoria.core_charges")
                .append(": " + tile.infernal_core + "/8"), i - 28, j + 22);
            }
            if(pX >= i + 49 && pX < i + 49 + 8 && pY >= j + 25 && pY < j + 25 + 8){
                renderTooltip(pGuiGraphics, Component.translatable("tooltip.valoria.core_charges")
                .append(": " + tile.nature_core + "/8"), i + 6, j + 22);
            }
            if(pX >= i + 33 && pX < i + 33 + 8 && pY >= j + 8 && pY < j + 8 + 8){
                renderTooltip(pGuiGraphics, Component.translatable("tooltip.valoria.core_charges")
                .append(": " + tile.aquarius_core + "/8"), i - 11, j + 5);
            }
            if(pX >= i + 33 && pX < i + 33 + 8 && pY >= j + 42 && pY < j + 42 + 8){
                renderTooltip(pGuiGraphics, Component.translatable("tooltip.valoria.core_charges")
                .append(": " + tile.void_core + "/8"), i - 11, j + 39);
            }
        }
    }

    public void renderTooltip(GuiGraphics gui, MutableComponent component, int x, int y){
        gui.renderTooltip(Minecraft.getInstance().font, component, x, y);
    }

    @Override
    protected void renderLabels(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY){
        pGuiGraphics.drawString(this.font, this.title, this.titleLabelX + 42, this.titleLabelY, 4210752, false);
        pGuiGraphics.drawString(this.font, this.playerInventoryTitle, this.inventoryLabelX, this.inventoryLabelY - 46, 4210752, false);
    }

    @Override
    protected void renderBg(GuiGraphics gui, float partialTicks, int x, int y){
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        int i = this.leftPos;
        int j = this.topPos;
        gui.blit(GUI, i, j, 0, 0, this.imageWidth, this.imageHeight);
        if(menu.tileEntity instanceof ManipulatorBlockEntity elemental){
            if(elemental.itemHandler.getStackInSlot(1).isEmpty()) {
                gui.blit(GUI, i + 76, j + 53, 208, 0, 16, 16);
            }

            if(elemental.infernal_core != 0){
                gui.blit(GUI, i + 16, j + 25, 181, 27, 5, 5);
            }

            if(elemental.nature_core != 0){
                gui.blit(GUI, i + 49, j + 25, 186, 27, 5, 5);
            }

            if(elemental.aquarius_core != 0){
                gui.blit(GUI, i + 33, j + 8, 176, 27, 5, 5);
            }

            if(elemental.void_core != 0){
                gui.blit(GUI, i + 33, j + 42, 191, 27, 5, 5);
            }

            if(elemental.progress > 0){
                int width = 24;
                width /= ((double)elemental.progressMax / (double)elemental.progress);
                gui.blit(GUI, i + 101, j + 53, 176, 0, width, 17, 256, 256);
            }
        }
    }
}