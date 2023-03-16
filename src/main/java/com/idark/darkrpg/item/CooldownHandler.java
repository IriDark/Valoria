package com.idark.darkrpg.item;

import com.idark.darkrpg.util.ModSoundRegistry;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.SoundCategory;

public class CooldownHandler {

    public static void onCooldownEnd(ServerPlayerEntity playerEntity,Item item){
        if (item instanceof KatanaItem){
            playerEntity.playSound(ModSoundRegistry.RECHARGE.get(), SoundCategory.PLAYERS,1,1);
        }
    }
}
