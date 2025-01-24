package com.idark.valoria.client.ui.screen;

import com.idark.valoria.Valoria;
import com.idark.valoria.client.ui.menus.ManipulatorMenu;
import com.idark.valoria.registries.block.entity.ManipulatorBlockEntity;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SmithingTemplateItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Optional;

@OnlyIn(Dist.CLIENT)
public class ManipulatorScreen extends AbstractContainerScreen<ManipulatorMenu>{
    private final ResourceLocation GUI = new ResourceLocation(Valoria.ID, "textures/gui/container/manipulator.png");
    private static final Component MISSING_TEMPLATE_TOOLTIP = Component.translatable("container.upgrade.missing_template_tooltip");

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
        }
    }
}