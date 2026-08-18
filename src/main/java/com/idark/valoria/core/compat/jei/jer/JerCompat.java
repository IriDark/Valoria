package com.idark.valoria.core.compat.jei.jer;

import com.idark.valoria.*;
import com.idark.valoria.registries.*;
import jeresources.api.*;
import jeresources.compatibility.api.*;
import net.minecraft.client.*;
import net.minecraft.client.multiplayer.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.client.event.*;

@JERPlugin
public class JerCompat {

    @OnlyIn(Dist.CLIENT)
    public static void onClientPlayerLogin(ClientPlayerNetworkEvent.LoggingIn event) {
        Minecraft mc = Minecraft.getInstance();
        ClientLevel clientLevel = mc.level;
        if (clientLevel != null) {
            IMobRegistry mobRegistry = JERAPI.getInstance().getMobRegistry();
            if (mobRegistry != null) {
                mobRegistry.register(EntityTypeRegistry.WICKED_CRYSTAL.get().create(clientLevel), Valoria.loc("items/wicked_crystal_treasure_bag"));
                mobRegistry.register(EntityTypeRegistry.DRYADOR.get().create(clientLevel), Valoria.loc("items/dryador_treasure_bag"));
                mobRegistry.register(EntityTypeRegistry.NECROMANCER.get().create(clientLevel), Valoria.loc("items/necromancer_treasure_bag"));
                mobRegistry.register(EntityTypeRegistry.FIRRON.get().create(clientLevel), Valoria.loc("items/firron_treasure_bag"));
            }
        }
    }

    public static void init(){
        IJERAPI jerApi = JERAPI.getInstance();
        IDungeonRegistry dungeonRegistry = jerApi.getDungeonRegistry();
        if(dungeonRegistry != null) dungeonRegistry(dungeonRegistry);
    }

    private static void dungeonRegistry(IDungeonRegistry dungeonRegistry){
        dungeonRegistry.registerChest("Fortress", Valoria.loc("chests/fortress"));
        dungeonRegistry.registerChest("Fortress Good", Valoria.loc("chests/fortress_good"));
        dungeonRegistry.registerChest("Fortress Normal", Valoria.loc("chests/fortress_normal"));

        dungeonRegistry.registerChest("Crypt", Valoria.loc("chests/crypt"));
        dungeonRegistry.registerChest("Crypt Sarcophagus", Valoria.loc("items/sarcophagus"));
        dungeonRegistry.registerChest("Necromancer Crypt", Valoria.loc("chests/necromancer_crypt"));
        dungeonRegistry.registerChest("Crystallized Deep Ruins", Valoria.loc("chests/crystallized_deep_ruins"));
        dungeonRegistry.registerChest("Fractured Skull", Valoria.loc("chests/fractured_skull"));
        dungeonRegistry.registerChest("Monstrosity", Valoria.loc("chests/monstrosity"));
    }
}