package com.idark.valoria.core.capability;

import com.idark.valoria.api.unlockable.types.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;

import javax.annotation.*;
import java.util.*;

public interface IUnlockable{
    static Optional<IUnlockable> of(@Nullable Entity entity){
        return entity instanceof Player player ? Optional.of(player.getData(ValoriaAttachments.UNLOCKABLES)) : Optional.empty();
    }

    boolean isViewed(Unlockable unlockable);

    void markViewed(Unlockable unlockable);

    void removeViewed(Unlockable unlockable);

    void viewAll();

    void resetViewed();

    Set<Unlockable> getViewed();

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

    void copyFrom(IUnlockable source);
}
