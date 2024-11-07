package com.idark.valoria.registries.item.skins.categories;

import com.idark.valoria.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.item.skins.entries.*;
import com.idark.valoria.util.*;
import mod.maxbogomol.fluffy_fur.client.model.item.*;
import mod.maxbogomol.fluffy_fur.common.itemskin.*;
import net.minecraftforge.api.distmarker.*;

public class Midnight extends ItemSkin{
    public Midnight(String id){
        super(id, Pal.majestyPurple);
    }

    @OnlyIn(Dist.CLIENT)
    public static void registerModels(){
        ItemSkinModels.addSkin(Valoria.ID + ":midnight_quantum_reaper");
    }

    @Override
    public void setupSkinEntries(){
        addSkinEntry(new ItemSupplierSkinEntry(() -> ItemsRegistry.quantumReaper.get(), Valoria.ID + ":midnight_quantum_reaper"));
    }
}