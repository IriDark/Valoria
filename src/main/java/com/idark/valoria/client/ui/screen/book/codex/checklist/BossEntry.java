package com.idark.valoria.client.ui.screen.book.codex.checklist;

import net.minecraft.network.chat.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;

public class BossEntry{
    public EntityType<? extends LivingEntity> type;
    public Item summonItem;
    public Component name;
    public Component description;
    public String category = "minecraft";
    public boolean isUnlocked = false;

    public BossEntry(EntityType<? extends LivingEntity> type) {
        this.type = type;
    }

    public BossEntry(EntityType<? extends LivingEntity> type, Component name) {
        this.type = type;
        this.name = name;
    }

    public BossEntry(EntityType<? extends LivingEntity> type, Component name, boolean isUnlocked) {
        this.type = type;
        this.name = name;
        this.isUnlocked = isUnlocked;
    }

    public void setUnlocked(boolean unlocked){
        isUnlocked = unlocked;
    }

    public String getCategory() {
        return category;
    }

    public EntityType<?> type() {
        return type;
    }

    public Component description(EntityType<?> entityType) {
        return description != null ? description : Component.translatable(entityType.getDescriptionId() + ".boss_description");
    }

    public Component name(EntityType<?> entityType) {
        return name != null ? name : entityType.getDescription();
    }

    public boolean isUnlocked() {
        return isUnlocked;
    }

    public boolean renderModel(){
        return true;
    }

    static class BossEntryData {
        public String entity;
        public String item;
        public String name;
        public String description;
        public String category = "minecraft";
        public boolean is_unlocked = false;
    }
}