package com.idark.valoria.core;

import com.google.gson.*;
import com.idark.valoria.*;
import net.minecraft.*;
import net.minecraft.nbt.*;
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
                URL url = new URL("https://raw.githubusercontent.com/IriDark/Valoria/refs/heads/main/patrons.json");
                try (InputStreamReader reader = new InputStreamReader(url.openStream())) {
                    JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
                    Map<UUID, String> tempMap = new HashMap<>();
                    for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                        UUID playerUUID = UUID.fromString(entry.getKey());
                        String rId = entry.getValue().getAsString();
                        tempMap.put(playerUUID, rId);
                    }

                    PATRONS.clear();
                    PATRONS.putAll(tempMap);
                }
            } catch (Exception e) {
                Valoria.LOGGER.error("Failed to load Patreon rewards list", e);
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

    public static void rewardPlayer(ServerPlayer player){
        CompoundTag persistentData = player.getPersistentData();
        CompoundTag persistedNbt = persistentData.getCompound(Player.PERSISTED_NBT_TAG);
        String rewardLootTable = PATRONS.get(player.getUUID());
        if(rewardLootTable != null && !persistedNbt.getBoolean("ValoriaPatronRewardClaimed")){
            LootParams params = new LootParams.Builder(player.serverLevel()).create(LootContextParamSets.EMPTY);
            List<ItemStack> generatedLoot = Utils.Items.createLoot(new ResourceLocation(rewardLootTable), params);
            if(!generatedLoot.isEmpty()){
                Utils.Items.giveLoot(player, generatedLoot);
                player.sendSystemMessage(Component.literal("Thank you for supporting Valoria! Here is your reward.").withStyle(ChatFormatting.GOLD));
            }

            persistedNbt.putBoolean("ValoriaPatronRewardClaimed", true);
            persistentData.put(Player.PERSISTED_NBT_TAG, persistedNbt);
        }
    }
}