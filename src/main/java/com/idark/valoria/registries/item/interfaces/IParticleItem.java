package com.idark.valoria.registries.item.interfaces;

import net.minecraft.world.entity.item.*;
import net.minecraft.world.level.*;

public interface IParticleItem{
    void addParticles(Level level, ItemEntity entity);
}