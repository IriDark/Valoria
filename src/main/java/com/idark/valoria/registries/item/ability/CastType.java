package com.idark.valoria.registries.item.ability;

public enum CastType {
    RIGHT_CLICK("RMB"),
    SHIFT_RIGHT_CLICK("SHIFT + RMB"),
    LEFT_CLICK("LMB"),
    SHIFT_LEFT_CLICK("SHIFT + LMB"),
    NOT_SPECIFIED("");

    public final String name;
    CastType(String name) {
        this.name = name;
    }

    public static CastType fromEvent(int event) {
        return values()[event % values().length]; 
    }
}