package com.idark.darkrpg.item;

import com.idark.darkrpg.item.types.KatanaItem;
import com.idark.darkrpg.item.types.ScytheItem;
import com.idark.darkrpg.util.ModSoundRegistry;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.Item;

public class CooldownHandler {

    // Some sounds taken from the CalamityMod (Terraria) in a https://calamitymod.fandom.com/wiki/Category:Sound_effects
    public static void onCooldownEnd(ServerPlayer player, Item item){
        if (item instanceof KatanaItem || item instanceof ScytheItem){
            player.playNotifySound(ModSoundRegistry.RECHARGE.get(), SoundSource.PLAYERS,1,1);
        }
    }
}