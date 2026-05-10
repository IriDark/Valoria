package com.idark.valoria.core;

import com.google.gson.*;
import net.minecraft.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraft.server.level.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.storage.loot.*;
import net.minecraft.world.level.storage.loot.parameters.*;
import pro.komaru.tridot.api.*;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class PatreonManager {
    public static final ConcurrentHashMap<UUID, String> PATRONS = new ConcurrentHashMap<>();

    public static void fetchPatrons() {
        CompletableFuture.runAsync(() -> {
            try {
                URL url = new URL("https://raw.githubusercontent.com/IriDark/Valoria/refs/heads/dev/patrons.json");
                InputStreamReader reader = new InputStreamReader(url.openStream());
                JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
                PATRONS.clear();
                for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                    UUID playerUUID = UUID.fromString(entry.getKey());
                    var rId = entry.getValue().getAsString();
                    PATRONS.put(playerUUID, rId);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static int getPatronCount() {
        return PATRONS.size();
    }

    public static boolean isPatron(UUID uuid) {
        return PATRONS.containsKey(uuid);
    }

    public static boolean isPatron(Player player) {
        return isPatron(player.getUUID());
    }

    public static void rewardPlayer(ServerPlayer player) {
        if (PATRONS.containsKey(player.getUUID())) {
            String rewardLootTable = PATRONS.get(player.getUUID());
            LootParams params = new LootParams.Builder(player.serverLevel()).create(LootContextParamSets.EMPTY);
            List<ItemStack> generatedLoot = Utils.Items.createLoot(new ResourceLocation(rewardLootTable), params);
            if (!generatedLoot.isEmpty()) {
                Utils.Items.giveLoot(player, generatedLoot);
                player.sendSystemMessage(Component.literal("Thank you for supporting Valoria! Here is your reward.").withStyle(ChatFormatting.GOLD));
            }
        }
    }
}