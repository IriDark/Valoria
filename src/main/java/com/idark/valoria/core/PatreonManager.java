package com.idark.valoria.core;

import com.google.gson.*;

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
}