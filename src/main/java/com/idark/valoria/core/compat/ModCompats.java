package com.idark.valoria.core.compat;

import com.idark.valoria.core.compat.jei.jer.*;
import net.neoforged.fml.*;

public class ModCompats{
    public static void init() {
        if(ModList.get().isLoaded("jeresources")){
            JerCompat.init();
        }
    }
}
