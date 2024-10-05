package com.idark.valoria.registries.item.skins;

import com.idark.valoria.*;
import com.idark.valoria.registries.item.types.ranged.*;
import com.idark.valoria.registries.item.types.ranged.bows.*;
import mod.maxbogomol.fluffy_fur.common.itemskin.*;

public class ContributorSkins extends ItemSkin{
    public ContributorSkins(String id){
        super(id);
    }

    @Override
    public void setupSkinEntries() {
        addSkinEntry(new ItemClassSkinEntry(ConfigurableBowItem.class, Valoria.ID+":arcane_gold_bow"));
        addSkinEntry(new ItemClassSkinEntry(BlazeReapItem.class, Valoria.ID+":arcane_gold_blaze_reap"));
    }
}
