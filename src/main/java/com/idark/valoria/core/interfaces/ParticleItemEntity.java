package com.idark.valoria.core.interfaces;

import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;

public interface ParticleItemEntity {
    void spawnParticles(Level level, ItemEntity entity);
}