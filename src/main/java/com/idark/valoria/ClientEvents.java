package com.idark.valoria;


import com.idark.valoria.core.config.*;
import com.idark.valoria.util.*;
import net.minecraft.*;
import net.minecraft.network.chat.*;
import net.minecraft.network.chat.ClickEvent.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.fml.*;
import net.minecraftforge.fml.config.*;
import net.minecraftforge.fml.config.ModConfig.*;
import pro.komaru.tridot.client.gfx.text.*;

import java.io.*;

public class ClientEvents{

    @SubscribeEvent
    public static void onClientJoin(ClientPlayerNetworkEvent.LoggingIn event){
        var modInfo = ModList.get().getModFileById(Valoria.ID).getMods().get(0);
        var result = VersionChecker.getResult(modInfo);
        if(ClientConfig.SHOW_UPDATES.get()){
            if(result.status().shouldDraw()){
                var newVersion = result.target().toString();
                Component message = Component.literal("\uD83E\uDEB7 Valoria: ").withStyle(style -> DotStyle.of().color(Pal.verySoftPink)).append(Component.translatable("tooltip.valoria.update_available", newVersion).withStyle(ChatFormatting.WHITE));
                var actions = Component.translatable("mco.backup.button.download").withStyle(style -> style.withUnderlined(true).withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.curseforge.com/minecraft/mc-mods/valoria"))).append(Component.literal(" | ").append(Component.translatable("tooltip.valoria.hide").withStyle(style -> style.withUnderlined(true).withClickEvent(new ClickEvent(Action.OPEN_FILE, new File(ConfigTracker.INSTANCE.getConfigFileName(Valoria.ID, Type.CLIENT)).getAbsolutePath())))));
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