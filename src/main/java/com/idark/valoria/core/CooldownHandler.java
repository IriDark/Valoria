package com.idark.valoria.core;

import com.idark.valoria.core.interfaces.*;
import com.idark.valoria.core.network.*;
import com.idark.valoria.core.network.packets.*;
import net.minecraft.server.level.*;
import net.minecraft.world.item.*;

public class CooldownHandler {
    public static void onCooldownEnd(ServerPlayer player, Item item) {
        if (item instanceof CooldownNotifyItem notifyItem) {
            PacketHandler.sendTo(player, new CooldownSoundPacket(notifyItem.getSoundEvent(), player.getX(), player.getY() + (player.getBbHeight() / 2), player.getZ()));
        }
    }
}