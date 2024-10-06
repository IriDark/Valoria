package com.idark.valoria.registries.item.skins;

import com.idark.valoria.*;
import com.idark.valoria.registries.item.types.ranged.*;
import com.idark.valoria.registries.item.types.ranged.bows.*;
import com.idark.valoria.util.*;
import mod.maxbogomol.fluffy_fur.common.itemskin.*;

public class ArcaneGoldSkins extends ItemSkin{
    public ArcaneGoldSkins(String id){
        super(id, Pal.arcaneGold);
    }

    @Override
    public void setupSkinEntries() {
        addSkinEntry(new ItemClassSkinEntry(ConfigurableBowItem.class, Valoria.ID+":arcane_wood_bow"));
        addSkinEntry(new ItemClassSkinEntry(BlazeReapItem.class, Valoria.ID+":arcane_gold_blaze_reap"));
    }
}
