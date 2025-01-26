package com.idark.valoria.core.compat;

import com.idark.valoria.core.compat.jei.jer.JerCompat;
import net.minecraftforge.fml.ModList;

public class ModCompats{
    public static void init() {
        if(ModList.get().isLoaded("jeresources")){
            JerCompat.init();
        }
    }
}
