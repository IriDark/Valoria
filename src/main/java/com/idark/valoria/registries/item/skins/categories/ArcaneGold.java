package com.idark.valoria.registries.item.skins.categories;

import com.idark.valoria.*;
import com.idark.valoria.core.interfaces.*;
import com.idark.valoria.registries.item.types.ranged.*;
import com.idark.valoria.registries.item.types.ranged.bows.*;
import com.idark.valoria.util.*;
import mod.maxbogomol.fluffy_fur.client.model.item.*;
import mod.maxbogomol.fluffy_fur.common.itemskin.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.item.*;
import net.minecraftforge.api.distmarker.*;

public class ArcaneGold extends ItemSkin implements IAuthorItemSkin{
    public ArcaneGold(String id){
        super(id, Pal.arcaneGold);
    }

    public Component getContributorComponent(ItemStack stack) {
        return stack.getHoverName().copy().withStyle(stack.getRarity().getStyleModifier()).append(Component.literal(" ༶ MaxBogomol ༶").withStyle(Styles.arcaneGold));
    }

    @OnlyIn(Dist.CLIENT)
    public static void registerModels(){
        ItemSkinModels.addBowSkin(Valoria.ID + ":arcane_wood_bow");
        ItemSkinModels.addSkin(Valoria.ID + ":arcane_gold_blaze_reap");
    }

    @Override
    public void setupSkinEntries() {
        addSkinEntry(new ItemClassSkinEntry(ConfigurableBowItem.class, Valoria.ID+":arcane_wood_bow"));
        addSkinEntry(new ItemClassSkinEntry(BlazeReapItem.class, Valoria.ID+":arcane_gold_blaze_reap"));
    }
}
