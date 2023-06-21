package com.idark.darkrpg.client.render.gui;

import com.idark.darkrpg.DarkRPG;
import com.idark.darkrpg.config.ClientConfig;
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
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.*;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.TickEvent;

public class MagmaBarRender {

    private MagmaBarRender() {}
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
			
			CompoundNBT compoundnbt = stack.getTag();	
            if (renderBar == true) {
                if (!player.isSpectator()) {
					Integer barType = ClientConfig.MAGMA_CHARGE_BAR_TYPE.get();						
					Integer xCord = ClientConfig.MAGMA_CHARGE_BAR_X.get();	
					Integer yCord = ClientConfig.MAGMA_CHARGE_BAR_Y.get();	
					Integer xDebug = ClientConfig.DEBUG_X.get();	
					Integer yDebug = ClientConfig.DEBUG_Y.get();						
                    mc.textureManager.bindTexture(BAR);
					if (barType == 1) {
						AbstractGui.blit(ms, xCord, yCord, 0, 0, 16, 34, 64, 64);
						if (compoundnbt.getInt("charge") == 1) {
							AbstractGui.blit(ms, xCord + 8, yCord + 18, 0, 34, 4, 25, 64, 64);
						} else if (compoundnbt.getInt("charge") == 2) {
							AbstractGui.blit(ms, xCord + 8, yCord + 18, 0, 34, 4, 25, 64, 64);
							AbstractGui.blit(ms, xCord + 8, yCord + 6, 0, 34, 4, 25, 64, 64);
							AbstractGui.blit(ms, xCord - 2, yCord - 2, 16, 0, 20, 38, 64, 64);
						}
					//TODO BarType 2
					}
					/*/ else if (barType == 2) {
						AbstractGui.blit(ms, xCord + xDebug, yCord + yDebug, 10, 21, 22, 22, 64, 64);
						if (compoundnbt.getInt("charge") == 1) {
							AbstractGui.blit(ms, xCord + xDebug, yCord + yDebug, 21, 10, 22, 22, 64, 64);
						} else if (compoundnbt.getInt("charge") == 2) {
							AbstractGui.blit(ms, xCord + xDebug, yCord + yDebug, 21, 21, 22, 22, 64, 64);
						}
					} /*/
				}
				
			RenderSystem.disableBlend();		
			}
		}
	}
}