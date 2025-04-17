package com.idark.valoria.core.capability;

import com.idark.valoria.api.unlockable.*;
import net.minecraftforge.common.capabilities.*;

import java.util.*;

public interface IUnlockable{
    Capability<IUnlockable> INSTANCE = CapabilityManager.get(new CapabilityToken<>(){
    });

    boolean isUnlocked(Unlockable unlockable);

    void addUnlockable(Unlockable unlockable);

    void removeUnlockable(Unlockable unlockable);

    void addAllUnlockable();

    void removeAllUnlockable();

    Set<Unlockable> getUnlockables();

    boolean isClaimed(Unlockable unlockable);

    void claim(Unlockable unlockable);

    void clearClaimed();

    Set<Unlockable> getClaimed();
}
