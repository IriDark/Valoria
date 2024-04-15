package com.idark.valoria.core.event;

import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ServerTickHandler {

    public static int tick = 0;

    @SubscribeEvent
    public static void serverTick(TickEvent.ServerTickEvent serverTickEvent) {
        if (serverTickEvent.phase == TickEvent.Phase.END) {
            tick++;
        }
    }
}
