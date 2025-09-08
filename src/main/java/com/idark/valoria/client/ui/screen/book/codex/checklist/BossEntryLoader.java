package com.idark.valoria.client.ui.screen.book.codex.checklist;

import com.google.gson.*;
import com.idark.valoria.*;
import com.idark.valoria.client.ui.screen.book.codex.*;
import com.idark.valoria.client.ui.screen.book.codex.checklist.BossEntry.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraft.server.packs.resources.*;
import net.minecraft.util.profiling.*;
import net.minecraft.world.entity.*;
import net.minecraftforge.registries.*;

import java.util.*;

public class BossEntryLoader extends SimpleJsonResourceReloadListener{
    private static final Gson GSON = new GsonBuilder().create();
    public static final BossEntryLoader INSTANCE = new BossEntryLoader();

    public BossEntryLoader(){
        super(GSON, "bosses");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> object, ResourceManager resourceManager, ProfilerFiller profiler){
        Valoria.LOGGER.info("Reloading Bosses info...");
        CodexEntries.bossEntries.clear();
        for(Map.Entry<ResourceLocation, JsonElement> entry : object.entrySet()){
            JsonObject json = entry.getValue().getAsJsonObject();
            BossEntryData data = GSON.fromJson(json, BossEntryData.class);

            EntityType<?> type = ForgeRegistries.ENTITY_TYPES.getValue(new ResourceLocation(data.entity));
            BossEntry boss = new BossEntry((EntityType<? extends LivingEntity>)type);
            if(data.name != null) boss.name = Component.translatable(data.name);
            if(data.description != null) boss.description = Component.translatable(data.description);
            if(data.category != null) boss.category = data.category;
            if(data.item != null) boss.summonItem = ForgeRegistries.ITEMS.getValue(new ResourceLocation(data.item));

            boss.setUnlocked(data.is_unlocked);
            CodexEntries.bossEntries.add(boss);
        }
    }
}