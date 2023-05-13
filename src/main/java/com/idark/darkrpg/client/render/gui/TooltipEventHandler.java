package com.idark.darkrpg.client.render.gui;

import com.idark.darkrpg.DarkRPG;
import com.idark.darkrpg.item.types.*;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.lwjgl.opengl.GL11;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.ArrayList;
import java.util.List;

public class TooltipEventHandler {

    private TooltipEventHandler() {}
    
	public static void onPostTooltipEvent(RenderTooltipEvent.PostText event) {
        ItemStack stack = event.getStack();

        int x = event.getX();
        int y = event.getY();
        int width = event.getWidth();
        int height = event.getHeight();
        MatrixStack ms = event.getMatrixStack();

        Minecraft mc = Minecraft.getInstance();

        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        ms.translate(0, 0, 410.0);
            
		if (stack.getItem() instanceof BlazeReapItem) {
            mc.textureManager.bindTexture(new ResourceLocation(DarkRPG.MOD_ID + ":textures/gui/eternal.png"));
            AbstractGui.blit(ms, x, y - 50, 0, 0, 80, 16, 80, 16);
		}
	}
	
	@SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        if (!stack.isEmpty()) {
            List<ITextComponent> tooltip = event.getToolTip();
			if (stack.getItem() instanceof BlazeReapItem) {
            tooltip.add(new StringTextComponent("                "));
            tooltip.add(new StringTextComponent("                "));
			}
		}
	}
}