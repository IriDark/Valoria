package com.idark.valoria.core.compat.jei.jer;

import com.idark.valoria.Valoria;
import com.idark.valoria.registries.EntityTypeRegistry;
import jeresources.api.IDungeonRegistry;
import jeresources.api.IJERAPI;
import jeresources.api.IMobRegistry;
import jeresources.api.JERPlugin;
import jeresources.compatibility.api.JERAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;

@JERPlugin
public class JerCompat{

    public static void onClientPlayerLogin(ClientPlayerNetworkEvent.LoggingIn event) {
        Minecraft mc = Minecraft.getInstance();
        ClientLevel clientLevel = mc.level;
        if (clientLevel != null) {
            IMobRegistry mobRegistry = JERAPI.getInstance().getMobRegistry();
            if (mobRegistry != null) {
                mobRegistry.register(EntityTypeRegistry.NECROMANCER.get().create(clientLevel), new ResourceLocation(Valoria.ID, "items/necromancer_treasure_bag"));
            }
        }
    }

    public static void init(){
        IJERAPI jerApi = JERAPI.getInstance();
        IDungeonRegistry dungeonRegistry = jerApi.getDungeonRegistry();
        if(dungeonRegistry != null){
            dungeonRegistry.registerChest("Crypt", new ResourceLocation(Valoria.ID, "chests/crypt"));
            dungeonRegistry.registerChest("Necromancer Crypt", new ResourceLocation(Valoria.ID, "chests/necromancer_crypt"));
            dungeonRegistry.registerChest("Crystallized Deep Ruins", new ResourceLocation(Valoria.ID, "chests/crystallized_deep_ruins"));
            dungeonRegistry.registerChest("Fractured Skull", new ResourceLocation(Valoria.ID, "chests/fractured_skull"));
            dungeonRegistry.registerChest("Monstrosity", new ResourceLocation(Valoria.ID, "chests/monstrosity"));
        }
    }
}