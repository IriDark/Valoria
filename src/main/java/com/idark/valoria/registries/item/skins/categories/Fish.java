package com.idark.valoria.registries.item.skins.categories;

import com.idark.valoria.*;
import com.idark.valoria.core.interfaces.*;
import com.idark.valoria.registries.item.types.*;
import com.idark.valoria.util.*;
import mod.maxbogomol.fluffy_fur.client.model.item.*;
import mod.maxbogomol.fluffy_fur.common.itemskin.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.item.*;
import net.minecraftforge.api.distmarker.*;

public class Fish extends ItemSkin implements AuthoredItemSkin{
    public Fish(String id){
        super(id, Pal.crystalBlue);
    }

    public Component getContributorComponent(ItemStack stack) {
        return stack.getHoverName().copy().withStyle(stack.getRarity().getStyleModifier()).append(Component.literal(" ༶ Skoow ༶").withStyle(Styles.aquarius));
    }

    @OnlyIn(Dist.CLIENT)
    public static void registerModels(){
        ItemSkinModels.addSkin(Valoria.ID + ":swordfish");
    }

    @Override
    public void setupSkinEntries(){
        addSkinEntry(new ItemClassSkinEntry(KatanaItem.class, Valoria.ID + ":swordfish"));
    }
}