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

public class Epic extends ItemSkin implements AuthoredItemSkin {
    public Epic(String id){
        super(id, Pal.majestyPurple);
    }

    public Component getContributorComponent(ItemStack stack) {
        if (stack.is(ItemsRegistry.quantumReaper.get())) return stack.getHoverName().copy().withStyle(stack.getRarity().getStyleModifier()).append(Component.literal(" ༶ Auriny ༶").withStyle(Styles.nihility));
        return stack.getHoverName();
    }

    @OnlyIn(Dist.CLIENT)
    public static void registerModels(){
        ItemSkinModels.addSkin(Valoria.ID + ":cyberpunk_quantum_reaper");
        ItemSkinModels.addSkin(Valoria.ID + ":midnight_quantum_reaper");
    }

    @Override
    public boolean canApplyOnItem(ItemStack itemStack){
        return super.canApplyOnItem(itemStack);
    }

    @Override
    public void setupSkinEntries() {
        addSkinEntry(new ItemSupplierSkinEntry(() -> ItemsRegistry.quantumReaper.get(), Valoria.ID+":cyberpunk_quantum_reaper"));
        addSkinEntry(new ItemSupplierSkinEntry(() -> ItemsRegistry.quantumReaper.get(), Valoria.ID+":midnight_quantum_reaper"));
    }
}