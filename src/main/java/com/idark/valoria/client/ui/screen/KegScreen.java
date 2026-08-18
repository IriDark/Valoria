package com.idark.valoria.client.ui.screen;

import com.idark.valoria.*;
import com.idark.valoria.client.ui.menus.*;
import com.idark.valoria.registries.block.entity.*;
import com.mojang.blaze3d.systems.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.screens.inventory.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraft.world.entity.player.*;

import java.util.*;

public class KegScreen extends AbstractContainerScreen<KegMenu>{
    private final ResourceLocation GUI = Valoria.loc("textures/gui/container/keg_brewery.png");
    private static final ResourceLocation EMPTY_SLOT_GLASS_BOTTLE = Valoria.loc("item/base_empty_slot_glass_bottle");
    private static final ResourceLocation EMPTY_SLOT_BOTTLE = Valoria.loc("item/base_empty_slot_bottle");
    private static final ResourceLocation EMPTY_SLOT_CUP = Valoria.loc("item/base_empty_slot_cup");
    private static final List<ResourceLocation> SLOT_ICONS = List.of(EMPTY_SLOT_GLASS_BOTTLE, EMPTY_SLOT_BOTTLE, EMPTY_SLOT_CUP);
    private final CyclingSlotBackground templateIcon = new CyclingSlotBackground(1);

    public KegScreen(KegMenu screenContainer, Inventory inv, Component titleIn){
        super(screenContainer, inv, titleIn);
        this.imageHeight = 165;
        this.inventoryLabelY = this.inventoryLabelY + 46;
    }

    public void containerTick(){
        super.containerTick();
        this.templateIcon.tick(SLOT_ICONS);
    }

    @Override
    public void render(GuiGraphics gui, int mouseX, int mouseY, float partialTicks){
        this.renderBackground(gui);
        super.render(gui, mouseX, mouseY, partialTicks);
        this.renderTooltip(gui, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY){
        pGuiGraphics.drawString(this.font, this.title, this.titleLabelX + 50, this.titleLabelY - 2, 4210752, false);
        pGuiGraphics.drawString(this.font, this.playerInventoryTitle, this.inventoryLabelX, this.inventoryLabelY - 46, 4210752, false);
    }

    @Override
    protected void renderBg(GuiGraphics gui, float partialTicks, int x, int y){
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        int i = this.leftPos;
        int j = this.topPos;
        gui.blit(GUI, i, j, 0, 0, this.imageWidth, this.imageHeight);
        this.templateIcon.render(this.menu, gui, partialTicks, i + 30, j - 50);
        if(menu.blockEntity instanceof KegBlockEntity keg){
            if(keg.progress > 0 && !keg.itemHandler.getStackInSlot(0).isEmpty()){
                int height = 21;
                height /= ((double)keg.progressMax / (double)keg.progress);
                gui.blit(GUI, i + 89, j + 52 - height, 176, 21 - height, 22, height, 256, 256);
            }
        }
    }
}