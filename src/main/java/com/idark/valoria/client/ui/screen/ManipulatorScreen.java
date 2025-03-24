package com.idark.valoria.client.ui.screen;

import com.idark.valoria.*;
import com.idark.valoria.client.ui.menus.*;
import com.idark.valoria.registries.block.entity.*;
import com.mojang.blaze3d.systems.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.screens.inventory.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraftforge.api.distmarker.*;

import java.util.*;

@OnlyIn(Dist.CLIENT)
public class ManipulatorScreen extends AbstractContainerScreen<ManipulatorMenu>{
    private final ResourceLocation GUI = new ResourceLocation(Valoria.ID, "textures/gui/container/manipulator.png");
    private static final Component MISSING_TEMPLATE_TOOLTIP = Component.translatable("container.upgrade.missing_template_tooltip");

    public ManipulatorScreen(ManipulatorMenu screenContainer, Inventory inv, Component titleIn){
        super(screenContainer, inv, titleIn);
        this.imageHeight = 166;
    }

    @Override
    public void render(GuiGraphics gui, int mouseX, int mouseY, float partialTicks){
        this.renderBackground(gui);
        super.render(gui, mouseX, mouseY, partialTicks);
        this.renderTooltip(gui, mouseX, mouseY);
        this.renderOnboardingTooltips(gui, mouseX, mouseY);
    }

    @Override
    public void renderTooltip(GuiGraphics pGuiGraphics, int pX, int pY){
        if(this.menu.getCarried().isEmpty() && this.hoveredSlot != null && this.hoveredSlot.hasItem()){
            ItemStack itemstack = this.hoveredSlot.getItem();
            pGuiGraphics.renderTooltip(this.font, this.getTooltipFromContainerItem(itemstack), itemstack.getTooltipImage(), itemstack, pX, pY);
        }

        if(this.menu.blockEntity instanceof ManipulatorBlockEntity tile){
            int i = this.leftPos;
            int j = this.topPos;
            if(pX >= i + 48 && pX < i + 48 + 8 && pY >= j + 31 && pY < j + 31 + 8){
                renderTooltip(pGuiGraphics, Component.translatable("tooltip.valoria.core_charges")
                .append(": " + tile.nature_core + "/8"), i + 8, j + 22);
            }
            if(pX >= i + 62 && pX < i + 62 + 8 && pY >= j + 31 && pY < j + 31 + 8){
                renderTooltip(pGuiGraphics, Component.translatable("tooltip.valoria.core_charges")
                .append(": " + tile.aquarius_core + "/8"), i + 18, j + 22);
            }
            if(pX >= i + 101 && pX < i + 101 + 8 && pY >= j + 31 && pY < j + 31 + 8){
                renderTooltip(pGuiGraphics, Component.translatable("tooltip.valoria.core_charges")
                .append(": " + tile.infernal_core + "/8"), i + 58, j + 22);
            }
            if(pX >= i + 115 && pX < i + 115 + 8 && pY >= j + 31 && pY < j + 31 + 22){
                renderTooltip(pGuiGraphics, Component.translatable("tooltip.valoria.core_charges")
                .append(": " + tile.void_core + "/8"), i + 72, j + 22);
            }
        }
    }

    public void renderTooltip(GuiGraphics gui, MutableComponent component, int x, int y){
        gui.renderTooltip(Minecraft.getInstance().font, component, x, y);
    }

    @Override
    protected void renderLabels(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY){
        pGuiGraphics.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, 4210752, false);
        pGuiGraphics.drawString(this.font, this.playerInventoryTitle, this.inventoryLabelX, this.inventoryLabelY, 4210752, false);
    }

    private void renderOnboardingTooltips(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY){
        Optional<Component> optional = Optional.empty();
        if(this.hoveredSlot != null){
            ItemStack itemstack = this.getMenu().getSlot(1).getItem();
            ItemStack itemstack1 = this.hoveredSlot.getItem();
            if(itemstack.isEmpty()){
                if(this.hoveredSlot.index == 1){
                    optional = Optional.of(MISSING_TEMPLATE_TOOLTIP);
                }
            }else{
                Item item = itemstack.getItem();
                if(item instanceof SmithingTemplateItem smithingtemplateitem){
                    if(itemstack1.isEmpty()){
                        if(this.hoveredSlot.index == 1){
                            optional = Optional.of(smithingtemplateitem.getBaseSlotDescription());
                        }else if(this.hoveredSlot.index == 2){
                            optional = Optional.of(smithingtemplateitem.getAdditionSlotDescription());
                        }
                    }
                }
            }
        }

        optional.ifPresent((p_280863_) -> {
            pGuiGraphics.renderTooltip(this.font, this.font.split(p_280863_, 115), pMouseX, pMouseY);
        });
    }

    @Override
    protected void renderBg(GuiGraphics gui, float partialTicks, int x, int y){
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        int i = this.leftPos;
        int j = this.topPos;
        gui.blit(GUI, i, j, 0, 0, this.imageWidth, this.imageHeight);
        if(menu.blockEntity instanceof ManipulatorBlockEntity elemental){
            int width = 24;
            width /= ((double)elemental.progressMax / (double)elemental.progress);
            gui.blit(GUI, i + 101, j + 53, 176, 0, width, 17, 256, 256);
            if(elemental.itemHandler.getStackInSlot(1).isEmpty()){
                gui.blit(GUI, i + 76, j + 53, 208, 0, 16, 16);
            }

            if(elemental.nature_core != 0){
                gui.blit(GUI, i + 48, j + 31, 186, 27, 5, 5);
            }

            if(elemental.aquarius_core != 0){
                gui.blit(GUI, i + 62, j + 31, 176, 27, 5, 5);
            }

            if(elemental.infernal_core != 0){
                gui.blit(GUI, i + 101, j + 31, 181, 27, 5, 5);
            }

            if(elemental.void_core != 0){
                gui.blit(GUI, i + 115, j + 31, 191, 27, 5, 5);
            }
        }
    }
}