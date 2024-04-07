package com.idark.valoria.registries.item.types;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

public class CoreBuilder extends Item {

    private final int givenCores;
    private final int[] color;
    private final String coreName;

    /**
     * @param pGivenCores Max value: 8
     * @param pColor (R, G, B)
     * @param pCoreID Core name
     */
    public CoreBuilder(Properties pProperties, int pGivenCores, int[] pColor, String pCoreID) {
        super(pProperties);
        givenCores = pGivenCores;
        color = pColor;
        coreName = pCoreID;
    }

    /**
     * @param pGivenCores Max value: 8
     * @param pColor (R, G, B)
     */
    public CoreBuilder(Properties pProperties, int pGivenCores, int[] pColor, RegistryObject<Item> item) {
        super(pProperties);
        givenCores = pGivenCores;
        color = pColor;
        coreName = item.getId().getPath();
    }

    public String getCoreName() {
        return coreName;
    }

    public int[] getCoreColor() {
        return color;
    }

    public int getGivenCores() {
        return givenCores;
    }
}
