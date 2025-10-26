package com.idark.valoria.core.capability;

import net.minecraft.core.*;
import net.minecraft.nbt.*;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.*;

public class NihilityLevelCap implements ICapabilityProvider, INBTSerializable<CompoundTag>{
    NihilityLevelProvider impl = new NihilityLevelProvider();

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side){
        return cap == INihilityLevel.INSTANCE ? (LazyOptional<T>) LazyOptional.of(() -> impl) : LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT(){
        return impl.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt){
        impl.deserializeNBT(nbt);
    }
}
