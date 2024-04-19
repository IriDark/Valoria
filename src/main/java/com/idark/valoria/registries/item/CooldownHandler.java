package com.idark.valoria.registries.item;

import com.idark.valoria.core.network.PacketHandler;
import com.idark.valoria.core.network.packets.CooldownSoundPacket;
import com.idark.valoria.registries.item.types.KatanaItem;
import com.idark.valoria.registries.item.types.ScytheItem;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;

public class CooldownHandler {

    /**
     * Some sounds taken from the CalamityMod (Terraria) in a <a href="https://calamitymod.wiki.gg/wiki/Category:Sound_effects">Calamity Mod Wiki.gg</a>
     */
    public static void onCooldownEnd(ServerPlayer player, Item item) {
        if (item instanceof KatanaItem || item instanceof ScytheItem) {
            PacketHandler.sendToTracking(player.level(), player.getOnPos(), new CooldownSoundPacket((float) player.getX(), (float) player.getY() + (player.getBbHeight() / 2), (float) player.getZ()));
        }
    }
}