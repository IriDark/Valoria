package com.idark.valoria;


import com.idark.valoria.api.unlockable.*;
import com.idark.valoria.client.ui.screen.book.*;
import com.idark.valoria.client.ui.screen.book.codex.*;
import com.idark.valoria.core.config.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.util.*;
import com.mojang.blaze3d.platform.*;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.screens.inventory.*;
import net.minecraft.network.chat.*;
import net.minecraft.network.chat.ClickEvent.*;
import net.minecraft.sounds.*;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.fml.*;
import net.minecraftforge.fml.config.*;
import net.minecraftforge.fml.config.ModConfig.*;
import org.lwjgl.glfw.*;
import pro.komaru.tridot.client.gfx.text.*;

import java.io.*;

public class ClientEvents{

    @SubscribeEvent
    public static void onMouseClick(ScreenEvent.MouseButtonPressed event) {
        Minecraft mc = Minecraft.getInstance();
        if(event.getScreen() instanceof EffectRenderingInventoryScreen<?> inventoryScreen){
            if(event.getButton() == GLFW.GLFW_MOUSE_BUTTON_RIGHT){
                if(!InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), GLFW.GLFW_KEY_LEFT_CONTROL)) return;

                Slot hovered = inventoryScreen.getSlotUnderMouse();
                if(hovered == null) return;
                var unlockable = Unlockables.getUnlockableByItem(hovered.getItem().getItem());
                if(unlockable.isPresent()){
                    var node = CodexEntries.getNode(unlockable.get());

                    mc.player.playNotifySound(SoundEvents.BOOK_PAGE_TURN, SoundSource.NEUTRAL, 1.0f, 1.0f);
                    if(node == null) return;

                    mc.setScreen(new BookGui(node.chapter, true));
                    event.setCanceled(true);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onTooltipRender(RenderTooltipEvent.Pre event){
        ItemStack stack = event.getItemStack();
        if(Unlockables.getUnlockableByItem(stack.getItem()).isPresent()){
            GuiGraphics gfx = event.getGraphics();
            PoseStack pose = gfx.pose();

            pose.pushPose();
            pose.translate(0, 0, 500);

            int tooltipHeight = event.getComponents().size() * event.getFont().lineHeight;
            int iconX = event.getX();
            int iconY = event.getY() + tooltipHeight - 18;
            gfx.renderItem(ItemsRegistry.codex.get().getDefaultInstance(), iconX, iconY);
            pose.popPose();
        }
    }

    @SubscribeEvent
    public static void onClientJoin(ClientPlayerNetworkEvent.LoggingIn event){
        var modInfo = ModList.get().getModFileById(Valoria.ID).getMods().get(0);
        var result = VersionChecker.getResult(modInfo);
        if(ClientConfig.SHOW_UPDATES.get()){
            if(result.status().shouldDraw()){
                var newVersion = result.target().toString();
                Component message = Component.literal("\uD83E\uDEB7 Valoria: ").withStyle(style -> DotStyle.of().color(Pal.verySoftPink)).append(Component.translatable("tooltip.valoria.update_available", newVersion).withStyle(ChatFormatting.WHITE));
                var actions = Component.translatable("tooltip.valoria.download").withStyle(style -> style.withUnderlined(true).withFont(Valoria.FONT).withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.curseforge.com/minecraft/mc-mods/valoria"))).append(Component.literal(" | ").append(Component.translatable("tooltip.valoria.hide").withStyle(style -> style.withUnderlined(true).withFont(Valoria.FONT).withClickEvent(new ClickEvent(Action.OPEN_FILE, new File(ConfigTracker.INSTANCE.getConfigFileName(Valoria.ID, Type.CLIENT)).getAbsolutePath())))));
                var separator = Component.literal("<->-<->-<->-<->-<->-<->-<->-<->-<->-<->-<->-<->-<->-<->").withStyle(style -> DotStyle.of().color(Pal.verySoftPink.copy().darker()));

                event.getPlayer().displayClientMessage(separator, false);

                event.getPlayer().displayClientMessage(message, false);
                event.getPlayer().displayClientMessage(Component.empty(), false);
                event.getPlayer().displayClientMessage(Component.literal("             ").append(actions), false);

                event.getPlayer().displayClientMessage(separator, false);
            }
        }
    }
}