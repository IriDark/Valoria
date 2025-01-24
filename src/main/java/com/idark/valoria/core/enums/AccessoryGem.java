package com.idark.valoria.core.enums;

public enum AccessoryGem{
    NONE, BELT, TANK, TOUGH, AMBER, DIAMOND, EMERALD, RUBY, SAPPHIRE, ARMOR, HEALTH, WEALTH;

    public String getGemName(){
        return switch(this){
            case AMBER -> "amber";
            case DIAMOND -> "diamond";
            case EMERALD -> "emerald";
            case RUBY -> "ruby";
            case SAPPHIRE -> "sapphire";
            case ARMOR -> "armor";
            case HEALTH -> "health";
            case WEALTH -> "wealth";
            default -> null;
        };
    }

    public boolean isTextureApplicable(){
        return this != NONE && this != BELT && this != TANK && this != TOUGH;
    }
}