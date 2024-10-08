package com.idark.valoria.client.event;

import com.idark.valoria.core.event.ServerTickHandler;
import com.idark.valoria.util.ValoriaUtils;
import net.minecraft.client.Minecraft;
import net.minecraftforge.event.TickEvent;

public class ClientTickHandler {

    private ClientTickHandler() {
    }

    public static int ticksInGame = 0;
    public static float partialTicks = 0;

    public static void clientTickEnd(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            if (!Minecraft.getInstance().isPaused()) {
                ticksInGame++;
                partialTicks = 0;
            }

            if (!Minecraft.getInstance().hasSingleplayerServer()) {
                ServerTickHandler.tick++;
                ValoriaUtils.scheduler.handleSyncScheduledTasks(ServerTickHandler.tick);
            }
        }
    }
}