package com.idark.valoria.client.ui.screen;

import com.idark.valoria.Valoria;
import com.idark.valoria.client.ui.menus.JewelryMenu;
import com.idark.valoria.registries.block.entity.JewelryBlockEntity;
import com.idark.valoria.registries.item.skins.SkinTrimItem;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CyclingSlotBackground;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;
import java.util.Optional;

@OnlyIn(Dist.CLIENT)
public class JewelryScreen extends AbstractContainerScreen<JewelryMenu>{
    private final ResourceLocation GUI = new ResourceLocation(Valoria.ID, "textures/gui/container/jewelry.png");
    private static final ResourceLocation EMPTY_SLOT_SMITHING_TEMPLATE_ARMOR_TRIM = new ResourceLocation("item/empty_slot_smithing_template_armor_trim");
    private static final ResourceLocation EMPTY_SLOT_DIAMOND = new ResourceLocation("item/empty_slot_diamond");
    private static final List<ResourceLocation> SLOT_ICONS = List.of(EMPTY_SLOT_SMITHING_TEMPLATE_ARMOR_TRIM, EMPTY_SLOT_DIAMOND);
    private static final ResourceLocation EMPTY_SLOT_RING = new ResourceLocation(Valoria.ID, "item/base_empty_slot_ring");
    private static final ResourceLocation EMPTY_SLOT_NECKLACE = new ResourceLocation(Valoria.ID, "item/base_empty_slot_necklace");
    private static final List<ResourceLocation> DEFAULT_ICON = List.of(EMPTY_SLOT_RING, EMPTY_SLOT_NECKLACE);
    private final CyclingSlotBackground templateIcon = new CyclingSlotBackground(1);
    private final CyclingSlotBackground baseIcon = new CyclingSlotBackground(0);

    public JewelryScreen(JewelryMenu screenContainer, Inventory inv, Component titleIn){
        super(screenContainer, inv, titleIn);
        this.imageHeight = 165;
        this.inventoryLabelY = this.inventoryLabelY + 46;
    }

    public void containerTick(){
        super.containerTick();
        Optional<SkinTrimItem> optional = this.getTrimItem();
        this.templateIcon.tick(SLOT_ICONS);
        this.baseIcon.tick(optional.map(SkinTrimItem::createBaseEmptyIcons).orElse(DEFAULT_ICON));
    }

    private Optional<SkinTrimItem> getTrimItem(){
        ItemStack itemstack = this.menu.getSlot(1).getItem();
        if(!itemstack.isEmpty()){
            Item item = itemstack.getItem();
            if(item instanceof SkinTrimItem trim){
                return Optional.of(trim);
            }
        }

        return Optional.empty();
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
        if(menu.blockEntity instanceof JewelryBlockEntity jewelry){
            this.templateIcon.render(this.menu, gui, partialTicks, this.leftPos, this.topPos);
            this.baseIcon.render(this.menu, gui, partialTicks, this.leftPos, this.topPos);
            if(jewelry.progress > 0 && !jewelry.itemHandler.getStackInSlot(1).isEmpty() && !jewelry.itemHandler.getStackInSlot(0).isEmpty()){
                int width = 24;
                width /= ((double)jewelry.progressMax / (double)jewelry.progress);
                gui.blit(GUI, i + 101, j + 47, 176, 0, width, 17, 256, 256);
            }
        }
    }
}