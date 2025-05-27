package com.idark.valoria.client.ui.screen;

import com.idark.valoria.*;
import com.idark.valoria.client.ui.menus.*;
import com.idark.valoria.client.ui.menus.slots.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.block.entity.*;
import com.mojang.blaze3d.systems.*;
import net.minecraft.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.screens.inventory.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;

import java.util.*;

public class KegScreen extends AbstractContainerScreen<KegMenu>{
    private final ResourceLocation GUI = new ResourceLocation(Valoria.ID, "textures/gui/container/keg_brewery.png");

    public KegScreen(KegMenu screenContainer, Inventory inv, Component titleIn){
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
    protected void renderLabels(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY){
        pGuiGraphics.drawString(this.font, this.title, this.titleLabelX + 50, this.titleLabelY - 2, 4210752, false);
        pGuiGraphics.drawString(this.font, this.playerInventoryTitle, this.inventoryLabelX, this.inventoryLabelY - 46, 4210752, false);
    }

    @Override
    protected void renderTooltip(GuiGraphics pGuiGraphics, int pX, int pY){
        if(this.hoveredSlot instanceof KegResultSlot) {
            ItemStack stack = hoveredSlot.getItem();
            if(stack.isEmpty()) return;
            Component component = Component.translatable("tooltip.valoria.collected", hoveredSlot.getItem().is(TagsRegistry.CUP_DRINKS) ? ItemsRegistry.cup.get() : ItemsRegistry.bottle.get()).withStyle(ChatFormatting.GRAY);

            var tooltip = this.getTooltipFromContainerItem(stack);
            tooltip.add(Component.empty());
            tooltip.add(component);
            List<Component> tooltipLines = new ArrayList<>(tooltip);
            pGuiGraphics.renderTooltip(this.font, tooltipLines, stack.getTooltipImage(), stack, pX, pY);
        } else {
            super.renderTooltip(pGuiGraphics, pX, pY);
        }
    }

    @Override
    protected void renderBg(GuiGraphics gui, float partialTicks, int x, int y){
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        int i = this.leftPos;
        int j = this.topPos;
        gui.blit(GUI, i, j, 0, 0, this.imageWidth, this.imageHeight);
        if(menu.blockEntity instanceof KegBlockEntity keg){
            if(keg.progress > 0 && !keg.itemHandler.getStackInSlot(0).isEmpty()){
                int height = 21;
                height /= ((double)keg.progressMax / (double)keg.progress);
                gui.blit(GUI, i + 77, j + 52 - height, 176, 21 - height, 22, height, 256, 256);
            }
        }
    }
}