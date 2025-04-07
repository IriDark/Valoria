package com.idark.valoria.core.compat.jei.jer;

import com.idark.valoria.*;
import com.idark.valoria.registries.*;
import jeresources.api.*;
import jeresources.compatibility.api.*;
import net.minecraft.client.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.resources.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.client.event.*;

@JERPlugin
public class JerCompat{

    @OnlyIn(Dist.CLIENT)
    public static void onClientPlayerLogin(ClientPlayerNetworkEvent.LoggingIn event) {
        Minecraft mc = Minecraft.getInstance();
        ClientLevel clientLevel = mc.level;
        if (clientLevel != null) {
            IMobRegistry mobRegistry = JERAPI.getInstance().getMobRegistry();
            if (mobRegistry != null) {
                mobRegistry.register(EntityTypeRegistry.WICKED_CRYSTAL.get().create(clientLevel), new ResourceLocation(Valoria.ID, "items/wicked_crystal_treasure_bag"));
                mobRegistry.register(EntityTypeRegistry.DRYADOR.get().create(clientLevel), new ResourceLocation(Valoria.ID, "items/dryador_treasure_bag"));
                mobRegistry.register(EntityTypeRegistry.NECROMANCER.get().create(clientLevel), new ResourceLocation(Valoria.ID, "items/necromancer_treasure_bag"));
            }
        }
    }

    public static void init(){
        IJERAPI jerApi = JERAPI.getInstance();
        IDungeonRegistry dungeonRegistry = jerApi.getDungeonRegistry();
        if(dungeonRegistry != null){
            dungeonRegistry.registerChest("Fortress", new ResourceLocation(Valoria.ID, "chests/fortress"));
            dungeonRegistry.registerChest("Fortress Good", new ResourceLocation(Valoria.ID, "chests/fortress_good"));
            dungeonRegistry.registerChest("Fortress Normal", new ResourceLocation(Valoria.ID, "chests/fortress_normal"));

            dungeonRegistry.registerChest("Crypt", new ResourceLocation(Valoria.ID, "chests/crypt"));
            dungeonRegistry.registerChest("Necromancer Crypt", new ResourceLocation(Valoria.ID, "chests/necromancer_crypt"));
            dungeonRegistry.registerChest("Crystallized Deep Ruins", new ResourceLocation(Valoria.ID, "chests/crystallized_deep_ruins"));
            dungeonRegistry.registerChest("Fractured Skull", new ResourceLocation(Valoria.ID, "chests/fractured_skull"));
            dungeonRegistry.registerChest("Monstrosity", new ResourceLocation(Valoria.ID, "chests/monstrosity"));
        }
    }
}