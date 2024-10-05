package com.idark.valoria.registries.item.skins;

import com.idark.valoria.*;
import mod.maxbogomol.fluffy_fur.client.model.item.*;
import mod.maxbogomol.fluffy_fur.client.render.item.*;
import mod.maxbogomol.fluffy_fur.common.itemskin.*;
import mod.maxbogomol.fluffy_fur.registry.common.item.*;
import net.minecraft.client.resources.model.*;
import net.minecraft.resources.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.fml.*;
import net.minecraftforge.fml.common.*;

import java.util.*;

public class SkinsRegistry{
    public static ItemSkin CONTRIBUTORS = new ContributorSkins(Valoria.ID + ":contributors");

    public static void register(){
        ItemSkinsHandler.register(CONTRIBUTORS);
        DistExecutor.unsafeCallWhenOn(Dist.CLIENT, () -> () -> {
            registerModels();
            return new Object();
        });
    }

    @OnlyIn(Dist.CLIENT)
    public static void registerModels(){
        ItemSkinsModels.addSkin(Valoria.ID + ":arcane_gold_bow");
        ItemSkinsModels.addSkin(Valoria.ID + ":arcane_gold_blaze_reap");
    }

    @Mod.EventBusSubscriber(modid = Valoria.ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientRegistryEvents{
        @SubscribeEvent
        public static void modelRegistryItems(ModelEvent.RegisterAdditional event) {
            event.register(LargeItemRenderer.getModelResourceLocation(Valoria.ID, "blaze_reap"));
        }

        @SubscribeEvent
        public static void modelRegistrySkins(ModelEvent.RegisterAdditional event){
            event.register(LargeItemRenderer.getModelResourceLocation(Valoria.ID, "skin/arcane_gold_blaze_reap"));
        }

        @SubscribeEvent
        public static void modelBakeSkins(ModelEvent.ModifyBakingResult event){
            Map<ResourceLocation, BakedModel> map = event.getModels();
            FluffyFurItemSkins.addLargeModel(map, Valoria.ID, "arcane_gold_blaze_reap");
            LargeItemRenderer.bakeModel(map, Valoria.ID, "blaze_reap", new ItemSkinItemOverrides());
        }
    }
}