package com.idark.valoria.core.compat.jei.jer;

import com.idark.valoria.*;
import com.idark.valoria.registries.*;
import jeresources.api.*;
import jeresources.compatibility.api.*;
import net.minecraft.client.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.core.registries.*;
import net.minecraft.resources.*;
import net.minecraft.world.level.storage.loot.*;
import net.neoforged.api.distmarker.*;
import net.neoforged.neoforge.client.event.*;

@JERPlugin
public class JerCompat {

    @OnlyIn(Dist.CLIENT)
    public static void onClientPlayerLogin(ClientPlayerNetworkEvent.LoggingIn event) {
        Minecraft mc = Minecraft.getInstance();
        ClientLevel clientLevel = mc.level;
        if (clientLevel != null) {
            IMobRegistry mobRegistry = JERAPI.getInstance().getMobRegistry();
            if (mobRegistry != null) {
                mobRegistry.register(EntityTypeRegistry.WICKED_CRYSTAL.get().create(clientLevel), lootTable("items/wicked_crystal_treasure_bag"));
                mobRegistry.register(EntityTypeRegistry.DRYADOR.get().create(clientLevel), lootTable("items/dryador_treasure_bag"));
                mobRegistry.register(EntityTypeRegistry.NECROMANCER.get().create(clientLevel), lootTable("items/necromancer_treasure_bag"));
                mobRegistry.register(EntityTypeRegistry.FIRRON.get().create(clientLevel), lootTable("items/firron_treasure_bag"));
            }
        }
    }

    public static void init(){
        IJERAPI jerApi = JERAPI.getInstance();
        IDungeonRegistry dungeonRegistry = jerApi.getDungeonRegistry();
        if(dungeonRegistry != null) dungeonRegistry(dungeonRegistry);
    }

    private static void dungeonRegistry(IDungeonRegistry dungeonRegistry){
        dungeonRegistry.registerChest("Fortress", lootTable("chests/fortress"));
        dungeonRegistry.registerChest("Fortress Good", lootTable("chests/fortress_good"));
        dungeonRegistry.registerChest("Fortress Normal", lootTable("chests/fortress_normal"));

        dungeonRegistry.registerChest("Crypt", lootTable("chests/crypt"));
        dungeonRegistry.registerChest("Crypt Sarcophagus", lootTable("items/sarcophagus"));
        dungeonRegistry.registerChest("Necromancer Crypt", lootTable("chests/necromancer_crypt"));
        dungeonRegistry.registerChest("Crystallized Deep Ruins", lootTable("chests/crystallized_deep_ruins"));
        dungeonRegistry.registerChest("Fractured Skull", lootTable("chests/fractured_skull"));
        dungeonRegistry.registerChest("Monstrosity", lootTable("chests/monstrosity"));
    }

    private static ResourceKey<LootTable> lootTable(String path){
        return ResourceKey.create(Registries.LOOT_TABLE, Valoria.loc(path));
    }
}
