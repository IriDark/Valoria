package com.idark.valoria.client.render;

import com.idark.valoria.*;
import com.idark.valoria.registries.item.ability.*;
import com.idark.valoria.registries.item.ability.AbilityHelper.*;
import com.mojang.blaze3d.systems.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.resources.*;
import net.minecraft.util.*;
import net.minecraft.world.item.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.client.gui.overlay.*;

import java.util.*;

public class AbilityOverlayHandler{
    public final static ResourceLocation bg = Valoria.loc("textures/gui/tooltips/ability_slot.png");

    @OnlyIn(Dist.CLIENT)
    public static void onDrawScreenPost(RenderGuiOverlayEvent.Post event){
        if (event.getOverlay() != VanillaGuiOverlay.HOTBAR.type()) {
            return;
        }

        Minecraft mc = Minecraft.getInstance();
        GuiGraphics gui = event.getGuiGraphics();
        ItemStack stack = Items.AIR.getDefaultInstance();
        if(!mc.player.getMainHandItem().isEmpty()){
            stack = mc.player.getMainHandItem();
        }else if(!mc.player.getOffhandItem().isEmpty()){
            stack = mc.player.getOffhandItem();
        }

        List<ActiveAbility> abilities = AbilityHelper.getActiveAbilities(stack);
        if (abilities.isEmpty()) return;

        gui.pose().pushPose();
        render(mc, gui, stack, abilities);
        gui.pose().popPose();
    }

    private static void render(Minecraft mc, GuiGraphics gui, ItemStack stack, List<ActiveAbility> abilities){
        long currentTime = mc.level.getGameTime();
        int x = gui.guiWidth() / 2 + 175;
        int y = gui.guiHeight() / 2 + 200;
        
        for (int i = 0; i < abilities.size(); i++){
            var element = abilities.get(i);
            int drawX = x + i * 34 + (32 / 2);

            gui.blit(bg, drawX, y, 0, 0, 32, 32, 64, 64);
            gui.blit(element.ability().icon, drawX + 8, y + 6, 0, 0, 18, 18, 18, 18);

            String name = element.type().name;
            gui.pose().pushPose();
            gui.pose().translate(drawX + (32 / 2f), y + 34, 0);
            gui.pose().scale(0.65f, 0.65f, 1.0f);
            gui.drawCenteredString(mc.font, name, 0, 0, CommonColors.WHITE);
            gui.pose().popPose();
            
            long endTime = AbilityHelper.getCooldown(mc.player, element.ability());
            int maxTicks = AbilityHelper.getMaxCooldown(mc.player, element.ability());
            if (endTime > 0 && currentTime < endTime && maxTicks > 0) {
                float progress = (float) (endTime - currentTime) / maxTicks;
                int cdHeight = (int) (progress * 32);
                int offset = 32 - cdHeight;

                RenderSystem.enableBlend();
                gui.setColor(255, 255, 255, 0.75f);
                gui.blit(bg, drawX, y + offset, 0, offset, 32, cdHeight, 64, 64);
                gui.setColor(1, 1, 1, 1);
                RenderSystem.disableBlend();
            }
            
            int usages = AbilityHelper.getUsages(mc.player, element.ability());
            if (usages > 0 && element.ability().maxUsages > 1) {
                gui.pose().pushPose();
                gui.pose().translate(drawX + 32, y + 32, 0);
                gui.pose().scale(0.65f, 0.65f, 1.0f);
                gui.drawCenteredString(mc.font, usages + "/" + element.ability().maxUsages, 0, 0, CommonColors.WHITE);
                gui.pose().popPose();
            }
        }
    }
}