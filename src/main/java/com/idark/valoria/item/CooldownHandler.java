package com.idark.valoria.item;

import com.idark.valoria.item.types.KatanaItem;
import com.idark.valoria.item.types.ScytheItem;
import com.idark.valoria.sounds.ModSoundRegistry;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.Item;

public class CooldownHandler {

    /**
     *Some sounds taken from the CalamityMod (Terraria) in a <a href="https://calamitymod.wiki.gg/wiki/Category:Sound_effects">Calamity Mod Wiki.gg</a>
     */
    public static void onCooldownEnd(ServerPlayer player, Item item){
        if (item instanceof KatanaItem || item instanceof ScytheItem){
            player.playNotifySound(ModSoundRegistry.RECHARGE.get(), SoundSource.PLAYERS, item instanceof KatanaItem ? 0.1f : 0.2f,1);
        }
    }
}