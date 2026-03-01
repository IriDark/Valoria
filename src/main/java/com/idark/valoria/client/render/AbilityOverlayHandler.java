package com.idark.valoria.client.render;

import com.idark.valoria.*;
import com.idark.valoria.registries.item.ability.*;
import com.idark.valoria.registries.item.ability.AbilityHelper.*;
import com.mojang.blaze3d.systems.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.nbt.*;
import net.minecraft.resources.*;
import net.minecraft.util.*;
import net.minecraft.world.item.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.client.gui.overlay.*;

import java.util.*;

public class AbilityOverlayHandler{
    private static ItemStack cachedStack = ItemStack.EMPTY;
    private static Tag cachedAbilitiesTag = null;;
    private static List<ActiveAbility> cachedAbilities = new ArrayList<>();

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

        if(!stack.hasTag() || !stack.getTag().contains("valoria_abilities")) return;
        CompoundTag currentNBT = stack.getTag();
        Tag currentAbilitiesTag = currentNBT.get("valoria_abilities");
        if (stack != cachedStack || !Objects.equals(currentAbilitiesTag, cachedAbilitiesTag)) {
            cachedStack = stack;
            cachedAbilitiesTag = currentAbilitiesTag != null ? currentAbilitiesTag.copy() : null;
            cachedAbilities = AbilityHelper.getActiveAbilities(stack);
        }

        gui.pose().pushPose();
        render(mc, gui, currentNBT);
        gui.pose().popPose();
    }

    private static void render(Minecraft mc, GuiGraphics gui, CompoundTag currentNBT){
        long currentTime = mc.level.getGameTime();
        int x = gui.guiWidth() / 2 + 175;
        int y = gui.guiHeight() / 2 + 200;
        List<ActiveAbility> abilities = cachedAbilities;
        for (int i = 0; i < abilities.size(); i++){
            var element = abilities.get(i);
            int drawX = x + i * 34 + (32 / 2);

            gui.blit(bg, drawX, y, 0, 0, 32, 32, 64, 64);
            gui.blit(element.ability().icon, drawX + 8, y + 6, 0, 0, 18, 18, 18, 18);

            String name = element.type().name;
            gui.pose().pushPose();
            gui.pose().translate(drawX + (32 / 2), y + 34, 0);
            gui.pose().scale(0.65f, 0.65f, 1.0f);
            gui.drawCenteredString(mc.font, name, 0, 0, CommonColors.WHITE);
            gui.pose().popPose();
            if (currentNBT.contains("ability_cooldowns")) {
                CompoundTag cdTag = currentNBT.getCompound("ability_cooldowns");
                String id = element.ability().type.id.toString();
                if (cdTag.contains(id)) {
                    long endTime = cdTag.getLong(id);
                    int maxTicks = cdTag.getInt(id + "_max");
                    if (currentTime < endTime) {
                        float progress = (float) (endTime - currentTime) / maxTicks;
                        int cdHeight = (int) (progress * 32);
                        int offset = 32 - cdHeight;

                        RenderSystem.enableBlend();
                        gui.setColor(255, 255, 255, 0.75f);
                        gui.blit(bg, drawX, y + offset, 0, offset, 32, cdHeight, 64, 64);
                        gui.setColor(1, 1, 1, 1);
                        RenderSystem.disableBlend();
                    }
                }
            }
        }
    }
}