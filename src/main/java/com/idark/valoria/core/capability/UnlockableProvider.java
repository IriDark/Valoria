package com.idark.valoria.core.capability;

import com.idark.valoria.api.unlockable.*;
import com.idark.valoria.api.unlockable.types.*;
import net.minecraft.nbt.*;
import net.minecraftforge.common.util.*;

import java.util.*;

public class UnlockableProvider implements IUnlockable, INBTSerializable<CompoundTag>{
    Set<Unlockable> unlockables = new HashSet<>();
    Set<Unlockable> claimed = new HashSet<>();
    Set<Unlockable> viewed = new HashSet<>();

    @Override
    public boolean isViewed(Unlockable unlockable){
        return viewed.contains(unlockable);
    }

    @Override
    public void markViewed(Unlockable unlockable){
        viewed.add(unlockable);
    }

    @Override
    public void removeViewed(Unlockable unlockable){
        viewed.remove(unlockable);
    }

    @Override
    public void viewAll(){
        viewed.clear();
        viewed.addAll(Unlockables.get());
    }

    @Override
    public void resetViewed(){
        viewed.clear();
    }

    @Override
    public Set<Unlockable> getViewed(){
        return viewed;
    }

    @Override
    public boolean isUnlocked(Unlockable unlockable){
        return unlockables.contains(unlockable);
    }

    @Override
    public boolean isClaimed(Unlockable unlockable){
        return claimed.contains(unlockable);
    }

    @Override
    public void claim(Unlockable unlockable){
        claimed.add(unlockable);
    }

    @Override
    public void clearClaimed(){
        claimed.clear();
    }

    @Override
    public void addUnlockable(Unlockable unlockable){
        unlockables.add(unlockable);
    }

    @Override
    public void removeUnlockable(Unlockable unlockable){
        unlockables.remove(unlockable);
    }

    @Override
    public void addAllUnlockable(){
        unlockables.clear();
        unlockables.addAll(Unlockables.get());
    }

    @Override
    public void removeAllUnlockable(){
        unlockables.clear();
    }

    @Override
    public Set<Unlockable> getClaimed(){
        return claimed;
    }

    @Override
    public Set<Unlockable> getUnlockables(){
        return unlockables;
    }

    @Override
    public CompoundTag serializeNBT(){
        ListTag unlocked = new ListTag();
        ListTag claimed = new ListTag();
        ListTag viewed = new ListTag();
        for(Unlockable un0 : getUnlockables()){
            if(un0 != null) unlocked.add(StringTag.valueOf(un0.getId()));
        }

        for(Unlockable un1 : getClaimed()){
            if(un1 != null) claimed.add(StringTag.valueOf(un1.getId()));
        }

        for(Unlockable un2 : getViewed()){
            if(un2 != null) viewed.add(StringTag.valueOf(un2.getId()));
        }

        CompoundTag wrapper = new CompoundTag();
        wrapper.put("unlocked", unlocked);
        wrapper.put("claimed", claimed);
        wrapper.put("viewed", viewed);
        return wrapper;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt){
        removeAllUnlockable();
        if((nbt).contains("unlocked")){
            ListTag unlockables = nbt.getList("unlocked", Tag.TAG_STRING);
            for(int i = 0; i < unlockables.size(); i++){
                Unlockable unlockable = Unlockables.getUnlockable(unlockables.getString(i));
                if(unlockable != null) addUnlockable(unlockable);
            }
        }

        clearClaimed();
        if((nbt).contains("claimed")){
            ListTag claimed = nbt.getList("claimed", Tag.TAG_STRING);
            for(int i = 0; i < claimed.size(); i++){
                Unlockable unlockable = Unlockables.getUnlockable(claimed.getString(i));
                if(unlockable != null) claim(unlockable);
            }
        }

        resetViewed();
        if((nbt).contains("viewed")){
            ListTag viewed = nbt.getList("viewed", Tag.TAG_STRING);
            for(int i = 0; i < viewed.size(); i++){
                Unlockable unlockable = Unlockables.getUnlockable(viewed.getString(i));
                if(unlockable != null) markViewed(unlockable);
            }
        }
    }
}
