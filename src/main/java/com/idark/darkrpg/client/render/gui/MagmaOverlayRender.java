package com.idark.darkrpg.client.render.gui;

import com.idark.darkrpg.DarkRPG;
import com.idark.darkrpg.item.types.MagmaSwordItem;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.TickEvent;

public class MagmaOverlayRender {

    private MagmaOverlayRender() {}

    public static int chargeLevel = 0;
	private static final ResourceLocation BAR = new ResourceLocation(DarkRPG.MOD_ID + ":textures/gui/magma_charge.png");

    public static void onDrawScreenPost(RenderGameOverlayEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        ItemStack main = mc.player.getHeldItemMainhand();
        ItemStack offhand = mc.player.getHeldItemOffhand();
        MatrixStack ms = event.getMatrixStack();
        if (event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
            PlayerEntity player = mc.player;
            ItemStack stack = main;	
            RenderSystem.enableBlend();
            RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            boolean renderBar = false;
			
            if (!main.isEmpty() && main.getItem() instanceof MagmaSwordItem) {
                renderBar = true;
            } else {
                if (!offhand.isEmpty() && offhand.getItem() instanceof MagmaSwordItem) {
                    renderBar = true;
                    stack = offhand;
                }
            }
			
            if (renderBar == true) {
                if (!player.isSpectator()) {
                    MagmaSwordItem magma = (MagmaSwordItem) stack.getItem();
                    mc.textureManager.bindTexture(BAR);
                    AbstractGui.blit(ms, 3, 2, 0, 0, 8, 17, 64, 136);
					if (chargeLevel == 1) {
						AbstractGui.blit(ms, 12, 2, 0, 0, 2, 5, 16, 40);
					} else if (chargeLevel == 2) {
						AbstractGui.blit(ms, 12, 2, 0, 0, 2, 5, 16, 40);
						AbstractGui.blit(ms, 15, 0, 0, 0, 10, 19, 80, 152);
					}						
				}
				
			RenderSystem.disableBlend();		
			}
		}
	}
}