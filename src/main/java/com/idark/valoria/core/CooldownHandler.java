package com.idark.valoria.core;

import com.idark.valoria.core.interfaces.ICooldownItem;
import com.idark.valoria.core.network.PacketHandler;
import com.idark.valoria.core.network.packets.CooldownSoundPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;

public class CooldownHandler {

    /**
     * Some sounds taken from the CalamityMod (Terraria) in a <a href="https://calamitymod.wiki.gg/wiki/Category:Sound_effects">Calamity Mod Wiki.gg</a>
     */
    public static void onCooldownEnd(ServerPlayer player, Item item) {
        if (item instanceof ICooldownItem) {
            PacketHandler.sendTo(player, new CooldownSoundPacket(player.getX(), player.getY() + (player.getBbHeight() / 2), player.getZ()));
        }
    }
}