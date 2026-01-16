package com.idark.valoria.core.capability;

import net.minecraft.core.*;
import net.minecraft.nbt.*;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.*;

public class MagmaLevelCap implements ICapabilityProvider, INBTSerializable<CompoundTag>{
    MagmaLevelProvider impl = new MagmaLevelProvider();

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side){
        return cap == IMagmaLevel.INSTANCE ? (LazyOptional<T>) LazyOptional.of(() -> impl) : LazyOptional.empty();
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
