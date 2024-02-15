package com.idark.valoria.capability;

import com.idark.valoria.api.unlockable.Unlockable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

import java.util.Set;

public interface IUnlockable {
    Capability<IUnlockable> INSTANCE = CapabilityManager.get(new CapabilityToken<>() {});

    boolean isUnlockable(Unlockable unlockable);
    void addUnlockable(Unlockable unlockable);
    void removeUnlockable(Unlockable unlockable);
    void addAllUnlockable();
    void removeAllUnlockable();
    Set<Unlockable> getUnlockables();
}
