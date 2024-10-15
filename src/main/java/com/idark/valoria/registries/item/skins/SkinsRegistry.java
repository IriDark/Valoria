package com.idark.valoria.registries.item.skins;

import com.idark.valoria.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.item.skins.categories.*;
import com.idark.valoria.registries.item.types.*;
import com.idark.valoria.registries.item.types.ranged.bows.*;
import mod.maxbogomol.fluffy_fur.client.model.item.*;
import mod.maxbogomol.fluffy_fur.client.render.item.*;
import mod.maxbogomol.fluffy_fur.common.itemskin.*;
import mod.maxbogomol.fluffy_fur.registry.client.*;
import mod.maxbogomol.fluffy_fur.registry.common.item.*;
import net.minecraft.client.resources.model.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.fml.*;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.registries.*;

import java.util.*;

public class SkinsRegistry{
    public static ItemSkin THE_FALLEN_COLLECTOR = new TheFallenCollector(Valoria.ID + ":the_fallen_collector");
    public static ItemSkin ARCANE_GOLD = new ArcaneGold(Valoria.ID + ":arcane_gold");
    public static ItemSkin CYBERPUNK = new Cyberpunk(Valoria.ID + ":cyberpunk");
    public static ItemSkin MURAMASA = new Muramasa(Valoria.ID + ":muramasa");
    public static void register(){
        ItemSkinHandler.register(ARCANE_GOLD);
        ItemSkinHandler.register(THE_FALLEN_COLLECTOR);
        ItemSkinHandler.register(CYBERPUNK);
        ItemSkinHandler.register(MURAMASA);
        DistExecutor.unsafeCallWhenOn(Dist.CLIENT, () -> () -> {
            registerModels();
            return new Object();
        });
    }

    @OnlyIn(Dist.CLIENT)
    public static void registerModels(){
        ArcaneGold.registerModels();
        TheFallenCollector.registerModels();
        Cyberpunk.registerModels();
        Muramasa.registerModels();
    }

    @Mod.EventBusSubscriber(modid = Valoria.ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientRegistryEvents{
        @SubscribeEvent
        public static void modelRegistrySkins(ModelEvent.RegisterAdditional event) {
            registerKatana(event);
            for(RegistryObject<Item> item : ItemsRegistry.ITEMS.getEntries()){
                if(item.get() instanceof ConfigurableBowItem){
                    FluffyFurModels.addBowItemModel(event, Valoria.ID, item.getId().getPath());
                }
            }

            event.register(LargeItemRenderer.getModelResourceLocation(Valoria.ID, "blaze_reap"));
            event.register(LargeItemRenderer.getModelResourceLocation(Valoria.ID, "skin/arcane_gold_blaze_reap"));
            event.register(LargeItemRenderer.getModelResourceLocation(Valoria.ID, "skin/muramasa"));
            event.register(LargeItemRenderer.getModelResourceLocation(Valoria.ID, "skin/brand"));
        }

        @SubscribeEvent
        public static void modelBakeSkins(ModelEvent.ModifyBakingResult event){
            Map<ResourceLocation, BakedModel> map = event.getModels();
            bakeArmor(map);
            bakeKatana(map);
            for(RegistryObject<Item> item : ItemsRegistry.ITEMS.getEntries()){
                if(item.get() instanceof ConfigurableBowItem){
                    FluffyFurModels.addBowItemModel(map, item.getId(), new BowSkinItemOverrides());
                }
            }

            FluffyFurItemSkins.addSkinModel(map, ItemsRegistry.quantumReaper.getId());
            FluffyFurItemSkins.addLargeModel(map, Valoria.ID, "muramasa");
            FluffyFurItemSkins.addLargeModel(map, Valoria.ID, "brand");
            FluffyFurItemSkins.addLargeModel(map, Valoria.ID, "arcane_gold_blaze_reap");
            LargeItemRenderer.bakeModel(map, Valoria.ID, "blaze_reap", new ItemSkinItemOverrides());
        }
    }

    private static void registerKatana(ModelEvent.RegisterAdditional event){
        for(RegistryObject<Item> item : ItemsRegistry.ITEMS.getEntries()){
            if(item.get() instanceof KatanaItem && ((KatanaItem) item.get()).hasLargeModel){
                event.register(LargeItemRenderer.getModelResourceLocation(Valoria.ID, item.getId().getPath()));
            }
        }
    }

    private static void bakeKatana(Map<ResourceLocation, BakedModel> map){
        for(RegistryObject<Item> item : ItemsRegistry.ITEMS.getEntries()){
            if(item.get() instanceof KatanaItem && ((KatanaItem) item.get()).hasLargeModel){
                LargeItemRenderer.bakeModel(map, Valoria.ID, item.getId().getPath(), new ItemSkinItemOverrides());
            }
        }
    }

    private static void bakeArmor(Map<ResourceLocation, BakedModel> map){
        for(RegistryObject<Item> item : ItemsRegistry.ITEMS.getEntries()){
            if(item.get() instanceof SkinableArmorItem){
                FluffyFurItemSkins.addSkinModel(map, item.getId());
            }
        }
    }
}