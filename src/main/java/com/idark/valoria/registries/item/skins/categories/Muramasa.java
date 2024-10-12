package com.idark.valoria.registries.item.skins.categories;

import com.idark.valoria.*;
import com.idark.valoria.core.interfaces.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.item.skins.entries.*;
import com.idark.valoria.util.*;
import mod.maxbogomol.fluffy_fur.client.model.item.*;
import mod.maxbogomol.fluffy_fur.common.itemskin.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.item.*;
import net.minecraftforge.api.distmarker.*;

public class Muramasa extends ItemSkin implements IAuthorItemSkin{
    public Muramasa(String id){
        super(id, Pal.sapphire);
    }

    public Component getContributorComponent(ItemStack stack) {
        return stack.getHoverName().copy().withStyle(stack.getRarity().getStyleModifier()).append(Component.literal(" ༶ Auriny ༶").withStyle(Styles.nihility));
    }

    @OnlyIn(Dist.CLIENT)
    public static void registerModels(){
        ItemSkinModels.addSkin(Valoria.ID + ":muramasa");
    }

    @Override
    public void setupSkinEntries() {
        addSkinEntry(new ItemSupplierSkinEntry(() -> ItemsRegistry.murasama.get(), Valoria.ID+":muramasa"));
    }
}