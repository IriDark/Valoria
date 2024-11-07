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

public class Cyberpunk extends ItemSkin implements AuthoredItemSkin {
    public Cyberpunk(String id){
        super(id, Pal.majestyPurple);
    }

    public Component getContributorComponent(ItemStack stack) {
        return stack.getHoverName().copy().withStyle(stack.getRarity().getStyleModifier()).append(Component.literal(" ༶ Auriny ༶").withStyle(Styles.nihility));
    }

    @OnlyIn(Dist.CLIENT)
    public static void registerModels(){
        ItemSkinModels.addSkin(Valoria.ID + ":cyberpunk_quantum_reaper");
    }

    @Override
    public void setupSkinEntries() {
        addSkinEntry(new ItemSupplierSkinEntry(() -> ItemsRegistry.quantumReaper.get(), Valoria.ID+":cyberpunk_quantum_reaper"));
    }
}