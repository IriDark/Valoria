package com.idark.valoria.core;

import com.idark.valoria.core.network.*;
import com.idark.valoria.core.network.packets.*;
import com.idark.valoria.registries.item.interfaces.*;
import net.minecraft.server.level.*;
import net.minecraft.world.item.*;

public class CooldownHandler{

    /**
     * Some sounds taken from the CalamityMod (Terraria) in a <a href="https://calamitymod.wiki.gg/wiki/Category:Sound_effects">Calamity Mod Wiki.gg</a>
     */
    public static void onCooldownEnd(ServerPlayer player, Item item){
        if(item instanceof ICooldownItem){
            PacketHandler.sendTo(player, new CooldownSoundPacket((float)player.getX(), (float)player.getY() + (player.getBbHeight() / 2), (float)player.getZ()));
        }
    }
}