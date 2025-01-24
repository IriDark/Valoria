package com.idark.valoria.core;

import com.idark.valoria.core.interfaces.CooldownNotifyItem;
import com.idark.valoria.core.network.PacketHandler;
import com.idark.valoria.core.network.packets.CooldownSoundPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;

public class CooldownHandler{
    public static void onCooldownEnd(ServerPlayer player, Item item){
        if(item instanceof CooldownNotifyItem notifyItem){
            PacketHandler.sendTo(player, new CooldownSoundPacket(notifyItem.getSoundEvent(), player.getX(), player.getY() + (player.getBbHeight() / 2), player.getZ()));
        }
    }
}