package com.idark.valoria.registries.item.types;

import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;

public interface IParticleItem {
    void addParticles(Level level, ItemEntity entity);
}