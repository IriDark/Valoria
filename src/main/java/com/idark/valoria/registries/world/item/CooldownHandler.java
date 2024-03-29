package com.idark.valoria.registries.world.item;

import com.idark.valoria.network.PacketHandler;
import com.idark.valoria.network.packets.CooldownSoundPacket;
import com.idark.valoria.registries.world.item.types.KatanaItem;
import com.idark.valoria.registries.world.item.types.ScytheItem;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;

public class CooldownHandler {

    /**
     * Some sounds taken from the CalamityMod (Terraria) in a <a href="https://calamitymod.wiki.gg/wiki/Category:Sound_effects">Calamity Mod Wiki.gg</a>
     */
    public static void onCooldownEnd(ServerPlayer player, Item item) {
        if (item instanceof KatanaItem || item instanceof ScytheItem) {
            //player.playNotifySound(ModSoundRegistry.RECHARGE.get(), SoundSource.PLAYERS, item instanceof KatanaItem ? 0.1f : 0.2f, 1);
            PacketHandler.sendToTracking(player.level(), player.getOnPos(), new CooldownSoundPacket((float) player.getX(), (float) player.getY() + (player.getBbHeight() / 2), (float) player.getZ()));
        }
    }
}