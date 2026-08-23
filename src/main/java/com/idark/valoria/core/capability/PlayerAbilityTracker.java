package com.idark.valoria.core.capability;

import net.minecraft.nbt.*;
import net.minecraftforge.common.util.*;

import java.util.*;
import java.util.concurrent.*;

public class PlayerAbilityTracker implements INBTSerializable<CompoundTag> {
    private final Map<String, Integer> usages = new ConcurrentHashMap<>();
    private final Map<String, Long> cooldowns = new ConcurrentHashMap<>();
    private final Map<String, Integer> maxCooldowns = new ConcurrentHashMap<>();

    public int getUsages(String abilityId) {
        return usages.getOrDefault(abilityId, 0);
    }

    public void setUsages(String abilityId, int count) {
        usages.put(abilityId, count);
    }

    public long getCooldown(String abilityId) {
        return cooldowns.getOrDefault(abilityId, 0L);
    }

    public int getMaxCooldown(String abilityId) {
        return maxCooldowns.getOrDefault(abilityId, 1);
    }

    public void setCooldown(String abilityId, long endTime, int maxTicks) {
        cooldowns.put(abilityId, endTime);
        maxCooldowns.put(abilityId, maxTicks);
    }

    public void copyFrom(PlayerAbilityTracker source) {
        this.usages.clear();
        this.cooldowns.clear();
        this.maxCooldowns.clear();
        this.usages.putAll(source.usages);
        this.cooldowns.putAll(source.cooldowns);
        this.maxCooldowns.putAll(source.maxCooldowns);
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        CompoundTag usagesTag = new CompoundTag();
        for (Map.Entry<String, Integer> entry : usages.entrySet()) {
            usagesTag.putInt(entry.getKey(), entry.getValue());
        }

        tag.put("Usages", usagesTag);
        CompoundTag cdTag = new CompoundTag();
        for (Map.Entry<String, Long> entry : cooldowns.entrySet()) {
            cdTag.putLong(entry.getKey(), entry.getValue());
            cdTag.putInt(entry.getKey() + "_max", maxCooldowns.getOrDefault(entry.getKey(), 1));
        }

        tag.put("Cooldowns", cdTag);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        usages.clear();
        cooldowns.clear();
        maxCooldowns.clear();
        
        if (nbt.contains("Usages", Tag.TAG_COMPOUND)) {
            CompoundTag usagesTag = nbt.getCompound("Usages");
            for (String key : usagesTag.getAllKeys()) {
                usages.put(key, usagesTag.getInt(key));
            }
        }
        
        if (nbt.contains("Cooldowns", Tag.TAG_COMPOUND)) {
            CompoundTag cdTag = nbt.getCompound("Cooldowns");
            for (String key : cdTag.getAllKeys()) {
                if (!key.endsWith("_max")) {
                    cooldowns.put(key, cdTag.getLong(key));
                    maxCooldowns.put(key, cdTag.getInt(key + "_max"));
                }
            }
        }
    }
}
