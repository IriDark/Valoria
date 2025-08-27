package com.idark.valoria.registries.item.armor;

import com.idark.valoria.client.render.armor.*;
import com.idark.valoria.registries.*;
import mod.azure.azurelib.rewrite.animation.cache.*;
import mod.azure.azurelib.rewrite.render.armor.*;

public class ArmorSetup{
    public static void clientSetup(){
        AzArmorRendererRegistry.register(PhantasmArmorRenderer::new, ItemsRegistry.phantasmHelmet.get(), ItemsRegistry.phantasmChestplate.get(), ItemsRegistry.phantasmLeggings.get(), ItemsRegistry.phantasmBoots.get());
        AzArmorRendererRegistry.register(SamuraiArmorRenderer::new, ItemsRegistry.samuraiKabuto.get(), ItemsRegistry.samuraiChestplate.get(), ItemsRegistry.samuraiLeggings.get(), ItemsRegistry.samuraiBoots.get());
    }

    public static void setup(){
        AzIdentityRegistry.register(ItemsRegistry.samuraiKabuto.get(), ItemsRegistry.samuraiChestplate.get(), ItemsRegistry.samuraiLeggings.get(), ItemsRegistry.samuraiBoots.get());
    }
}