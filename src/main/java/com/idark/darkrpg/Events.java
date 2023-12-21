package com.idark.darkrpg;

import com.idark.darkrpg.capability.PageProvider;
import com.idark.darkrpg.command.ModCommand;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class Events {
    @SubscribeEvent
    public void attachEntityCaps(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) event.addCapability(new ResourceLocation(DarkRPG.MOD_ID, "crypt_page"), new PageProvider());
    }
}