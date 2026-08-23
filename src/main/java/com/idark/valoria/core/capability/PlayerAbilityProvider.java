package com.idark.valoria.core.capability;

import net.minecraft.core.*;
import net.minecraft.nbt.*;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.*;
import org.jetbrains.annotations.*;

public class PlayerAbilityProvider implements ICapabilitySerializable<CompoundTag> {
    public static final Capability<PlayerAbilityTracker> PLAYER_ABILITIES = CapabilityManager.get(new CapabilityToken<>(){});

    private PlayerAbilityTracker abilities = null;
    private final LazyOptional<PlayerAbilityTracker> optional = LazyOptional.of(this::createAbilities);

    private PlayerAbilityTracker createAbilities() {
        if (this.abilities == null) {
            this.abilities = new PlayerAbilityTracker();
        }

        return this.abilities;
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == PLAYER_ABILITIES) {
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        return createAbilities().serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createAbilities().deserializeNBT(nbt);
    }
}
