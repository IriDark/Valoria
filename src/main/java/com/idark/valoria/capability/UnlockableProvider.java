package com.idark.valoria.capability;

import com.idark.valoria.api.unlockable.Unlockable;
import com.idark.valoria.api.unlockable.Unlockables;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.HashSet;
import java.util.Set;

public class UnlockableProvider implements IUnlockable, INBTSerializable<CompoundTag> {

    Set<Unlockable> unlockables = new HashSet<>();

    @Override
    public boolean isUnlockable(Unlockable unlockable) {
        return unlockables.contains(unlockable);
    }

    @Override
    public void addUnlockable(Unlockable unlockable) {
        unlockables.add(unlockable);
    }

    @Override
    public void removeUnlockable(Unlockable unlockable) {
        unlockables.remove(unlockable);
    }

    @Override
    public void addAllUnlockable() {
        unlockables.clear();
        unlockables.addAll(Unlockables.getUnlockables());
    }

    @Override
    public void removeAllUnlockable() {
        unlockables.clear();
    }

    @Override
    public Set<Unlockable> getUnlockables() {
        return unlockables;
    }
    
    @Override
    public CompoundTag serializeNBT() {
        ListTag unlockables = new ListTag ();
        for (Unlockable unlockable : getUnlockables()) {
            unlockables.add(StringTag.valueOf(unlockable.getId()));
        }

        CompoundTag wrapper = new CompoundTag();
        wrapper.put("unlockables", unlockables);
        return wrapper;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        removeAllUnlockable();
        if ((nbt).contains("unlockables")) {
            ListTag unlockables = nbt.getList("unlockables", Tag.TAG_STRING);
            for (int i = 0; i < unlockables.size(); i++) {
                Unlockable unlockable = Unlockables.getUnlockable(unlockables.getString(i));
                if (unlockable != null) addUnlockable(unlockable);
            }
        }
    }
}
