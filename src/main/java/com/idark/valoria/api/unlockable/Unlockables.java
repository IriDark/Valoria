package com.idark.valoria.api.unlockable;

import com.idark.valoria.api.unlockable.types.*;

import java.util.*;

public class Unlockables{
    public static Map<String, Unlockable> unlockableMap = new HashMap<String, Unlockable>();
    public static ArrayList<Unlockable> unlockable = new ArrayList<Unlockable>();

    public static void addUnlockable(String id, Unlockable unlockable){
        unlockableMap.put(id, unlockable);
        Unlockables.unlockable.add(unlockable);
    }

    public static Unlockable getUnlockable(int id){
        return unlockableMap.get(id);
    }

    public static Unlockable getUnlockable(String id){
        return unlockableMap.get(id);
    }

    public static void register(Unlockable unlockable){
        unlockableMap.put(unlockable.getId(), unlockable);
        Unlockables.unlockable.add(unlockable);
    }

    public static int size(){
        return unlockableMap.size();
    }

    public static ArrayList<Unlockable> get(){
        return unlockable;
    }
}