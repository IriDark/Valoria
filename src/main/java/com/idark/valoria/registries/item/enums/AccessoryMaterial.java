package com.idark.valoria.registries.item.enums;

public enum AccessoryMaterial{
    LEATHER("leather"),
    IRON("iron"),
    GOLD("golden"),
    DIAMOND("diamond"),
    NETHERITE("netherite"),
    NONE("");

    private final String name;

    AccessoryMaterial(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean isInvalid() {
        return this.equals(NONE);
    }
}