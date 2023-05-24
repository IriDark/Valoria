package com.idark.darkrpg.client.render.gui;

import com.idark.darkrpg.DarkRPG;
import com.idark.darkrpg.item.types.*;
import com.idark.darkrpg.util.*;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
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

// Currently not work with Legendary Tooltips and other tooltip changing mods. Buggy at this moment and dont work correctly on all devices (Opacity errors)
public class TooltipEventHandler {
	public static final ResourceLocation TRASH = new ResourceLocation(DarkRPG.MOD_ID, "textures/gui/trash.png");
	public static final ResourceLocation DEFAULT = new ResourceLocation(DarkRPG.MOD_ID, "textures/gui/default.png");
	public static final ResourceLocation COMMON = new ResourceLocation(DarkRPG.MOD_ID, "textures/gui/common.png");
	public static final ResourceLocation UNCOMMON = new ResourceLocation(DarkRPG.MOD_ID, "textures/gui/uncommon.png");
	public static final ResourceLocation RARE = new ResourceLocation(DarkRPG.MOD_ID, "textures/gui/rare.png");
	public static final ResourceLocation EPIC = new ResourceLocation(DarkRPG.MOD_ID, "textures/gui/epic.png");
	public static final ResourceLocation MYTHIC = new ResourceLocation(DarkRPG.MOD_ID, "textures/gui/mythic.png");
	public static final ResourceLocation MASTERY = new ResourceLocation(DarkRPG.MOD_ID, "textures/gui/mastery.png");
	public static final ResourceLocation RELIC = new ResourceLocation(DarkRPG.MOD_ID, "textures/gui/relic.png");
	public static final ResourceLocation ETERNAL = new ResourceLocation(DarkRPG.MOD_ID, "textures/gui/eternal.png");
	
	@SubscribeEvent
	public static void onPostTooltipEvent(RenderTooltipEvent.PostText event) {
        ItemStack stack = event.getStack();

        int x = event.getX();
        int y = event.getY();
        int width = event.getWidth();
        int height = event.getHeight();
		Minecraft mc = Minecraft.getInstance();
		MatrixStack matrix = event.getMatrixStack();
		matrix.push();		
		matrix.translate(0, 0, 410);		

		if (stack.getItem() instanceof BlazeReapItem) {
            mc.textureManager.bindTexture(ETERNAL);
            Screen.blit(matrix, x, y + 11, 0, 0, 80, 16, 80, 16);
		}
		matrix.pop();
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        if (!stack.isEmpty()) {
            List<ITextComponent> tooltip = event.getToolTip();
			if (stack.getItem() instanceof BlazeReapItem) {
				tooltip.add(1, new StringTextComponent("                "));
				tooltip.add(1, new StringTextComponent("                "));
			}
		}
	}
}