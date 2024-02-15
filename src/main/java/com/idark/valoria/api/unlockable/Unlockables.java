package com.idark.valoria.api.unlockable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Unlockables {
    public static Map<String, Unlockable> unlockableMap = new HashMap<String, Unlockable>();
    public static ArrayList<Unlockable> unlockables = new ArrayList<Unlockable>();

    public static void addKnowledge(String id, Unlockable spell) {
        unlockableMap.put(id, spell);
        unlockables.add(spell);
    }

    public static Unlockable getUnlockable(int id) {
        return unlockableMap.get(id);
    }

    public static Unlockable getUnlockable(String id) {
        return unlockableMap.get(id);
    }

    public static void register(Unlockable spell) {
        unlockableMap.put(spell.getId(), spell);
        unlockables.add(spell);
    }

    public static int size() {
        return unlockableMap.size();
    }

    public static ArrayList<Unlockable> getUnlockables() {
        return unlockables;
    }
}