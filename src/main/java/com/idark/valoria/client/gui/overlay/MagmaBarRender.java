package com.idark.valoria.client.gui.overlay;

import com.idark.valoria.*;
import com.idark.valoria.core.config.*;
import com.idark.valoria.registries.item.types.*;
import com.mojang.blaze3d.systems.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.nbt.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.client.event.*;
import org.lwjgl.opengl.*;

@OnlyIn(Dist.CLIENT)
public class MagmaBarRender{

    private MagmaBarRender(){
    }

    private static final ResourceLocation BAR = new ResourceLocation(Valoria.ID + ":textures/gui/overlay/magma_charge.png");

    public static void onDrawScreenPost(RenderGuiOverlayEvent.Post event){
        Minecraft mc = Minecraft.getInstance();
        ItemStack main = mc.player.getMainHandItem();
        ItemStack offhand = mc.player.getOffhandItem();
        GuiGraphics gui = event.getGuiGraphics();
        ItemStack stack = main;

        gui.pose().pushPose();
        gui.pose().translate(0, 0, -200);
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        int barType = ClientConfig.MAGMA_CHARGE_BAR_TYPE.get();
        boolean renderBar = false;

        if(barType < 3 && !main.isEmpty() && main.getItem() instanceof MagmaSwordItem){
            renderBar = true;
        }else{
            if(barType < 3 && !offhand.isEmpty() && offhand.getItem() instanceof MagmaSwordItem){
                renderBar = true;
                stack = offhand;
            }
        }

        CompoundTag tag = stack.getTag();
        if(renderBar){
            if(!mc.player.isSpectator()){
                int xCord = ClientConfig.MAGMA_CHARGE_BAR_X.get();
                int yCord = ClientConfig.MAGMA_CHARGE_BAR_Y.get();

                mc.textureManager.bindForSetup(BAR);
                if(barType == 1){
                    gui.blit(BAR, xCord, yCord, 0, 0, 16, 34, 64, 64);
                    if(tag.getInt("charge") == 1){
                        gui.blit(BAR, xCord + 8, yCord + 18, 0, 34, 4, 25, 64, 64);
                    }else if(tag.getInt("charge") == 2){
                        gui.blit(BAR, xCord + 8, yCord + 18, 0, 34, 4, 25, 64, 64);
                        gui.blit(BAR, xCord + 8, yCord + 6, 0, 34, 4, 25, 64, 64);
                        gui.blit(BAR, xCord - 2, yCord - 2, 16, 0, 20, 38, 64, 64);
                    }
                }else if(barType == 2){
                    gui.blit(BAR, xCord, yCord, 20, 42, 22, 22, 64, 64);
                    if(tag.getInt("charge") > 0){
                        gui.blit(BAR, xCord, yCord, 42, tag.getInt("charge") == 1 ? 20 : 42, 22, 22, 64, 64);
                    }
                }
            }
        }

        RenderSystem.disableBlend();
        gui.pose().popPose();
    }
}