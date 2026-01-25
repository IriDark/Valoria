package com.idark.valoria.core.interfaces;

import net.minecraft.core.*;
import net.minecraft.tags.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;

public interface IEffectiveWeaponEntity{
    TagKey<Item> getEffective();
    default float scaleFactor() {
        return 2f;
    }

    default void spawnHitParticles(Level level, BlockPos blockPos) {
    }
}
