package com.idark.darkrpg.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

public class PageProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    PageCapability page = new PageCapability();

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        if (cap == IPage.INSTANCE) return (LazyOptional<T>) LazyOptional.of(() -> page);
        else return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        return page.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        page.deserializeNBT(nbt);
    }
}
