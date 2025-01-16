package com.idark.valoria.registries.item.skins;

import com.idark.valoria.*;
import com.idark.valoria.client.model.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.item.armor.item.*;
import com.idark.valoria.registries.item.skins.categories.*;
import com.idark.valoria.registries.item.skins.entries.*;
import com.idark.valoria.registries.item.types.*;
import com.idark.valoria.registries.item.types.ranged.*;
import com.idark.valoria.registries.item.types.ranged.bows.*;
import com.idark.valoria.util.*;
import mod.maxbogomol.fluffy_fur.client.model.item.*;
import mod.maxbogomol.fluffy_fur.client.render.item.*;
import mod.maxbogomol.fluffy_fur.common.itemskin.*;
import mod.maxbogomol.fluffy_fur.registry.client.*;
import mod.maxbogomol.fluffy_fur.registry.common.item.*;
import net.minecraft.client.resources.model.*;
import net.minecraft.resources.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.fml.*;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.registries.*;

import java.util.*;

public class SkinsRegistry{
    public static ItemSkin THE_FALLEN_COLLECTOR, ARCANE_GOLD, CYBERPUNK, MIDNIGHT, MURAMASA, FISH;
    public static void init() {
        THE_FALLEN_COLLECTOR = new SkinBuilder(Valoria.ID, "the_fallen_collector").setColor(Pal.seaGreen).setContributor("Kerdo").withStyle(Styles.nature)
            .addEntry(new TheFallenCollectorSkinEntry(ArmorItem.class, Valoria.ID+":textures/models/armor/skin/the_fallen_collector")
            .addArmorSkin(EquipmentSlot.HEAD,Valoria.ID+":the_fallen_collector_crown")
            .addArmorSkin(EquipmentSlot.CHEST,Valoria.ID+":the_fallen_collector_coat"))
            .addEntry(new ItemClassSkinEntry(KatanaItem.class, Valoria.ID+":brand"))
            .build();
        ARCANE_GOLD = new SkinBuilder(Valoria.ID, "arcane_gold").setColor(Pal.arcaneGold).setContributor("MaxBogomol").withStyle(Styles.arcaneGold)
            .addEntry(new ItemClassSkinEntry(ConfigurableBowItem.class, Valoria.ID+":arcane_wood_bow"))
            .addEntry(new ItemClassSkinEntry(BlazeReapItem.class, Valoria.ID+":arcane_gold_blaze_reap"))
            .build();
        CYBERPUNK = new SkinBuilder(Valoria.ID, "cyberpunk").setColor(Pal.majestyPurple).setContributor("Auriny").withStyle(Styles.nihility)
            .addEntry(new ItemSupplierSkinEntry(() -> ItemsRegistry.quantumReaper.get(), Valoria.ID+":cyberpunk_quantum_reaper"))
            .build();
        MIDNIGHT = new SkinBuilder(Valoria.ID, "midnight").setColor(Pal.majestyPurple)
            .addEntry(new ItemSupplierSkinEntry(() -> ItemsRegistry.quantumReaper.get(), Valoria.ID+":midnight_quantum_reaper"))
            .build();
        MURAMASA = new SkinBuilder(Valoria.ID, "muramasa").setColor(Pal.majestyPurple).setContributor("Auriny").withStyle(Styles.nihility)
            .addEntry(new ItemSupplierSkinEntry(() -> ItemsRegistry.murasama.get(), Valoria.ID+":muramasa"))
            .build();
        FISH = new SkinBuilder(Valoria.ID, "swordfish").setColor(Pal.crystalBlue).setContributor("Skoow").withStyle(Styles.aquarius)
            .addEntry(new ItemSupplierSkinEntry(() -> ItemsRegistry.murasama.get(), Valoria.ID+":swordfish"))
            .build();
    }

    public static void register(){
        init();
        ItemSkinHandler.register(ARCANE_GOLD);
        ItemSkinHandler.register(THE_FALLEN_COLLECTOR);
        ItemSkinHandler.register(CYBERPUNK);
        ItemSkinHandler.register(MIDNIGHT);
        ItemSkinHandler.register(MURAMASA);
        ItemSkinHandler.register(FISH);
        DistExecutor.unsafeCallWhenOn(Dist.CLIENT, () -> () -> {
            registerModels();
            return new Object();
        });
    }

    @OnlyIn(Dist.CLIENT)
    public static void registerModels(){
        ItemSkinModels.addBowSkin(Valoria.ID + ":arcane_wood_bow");
        ItemSkinModels.addSkin(Valoria.ID + ":arcane_gold_blaze_reap");
        ItemSkinModels.addSkin(Valoria.ID + ":the_fallen_collector_crown");
        ItemSkinModels.addSkin(Valoria.ID + ":the_fallen_collector_coat");
        ItemSkinModels.addSkin(Valoria.ID + ":brand");
        ItemSkinModels.addSkin(Valoria.ID + ":cyberpunk_quantum_reaper");
        ItemSkinModels.addSkin(Valoria.ID + ":midnight_quantum_reaper");
        ItemSkinModels.addSkin(Valoria.ID + ":swordfish");
        ItemSkinModels.addSkin(Valoria.ID + ":muramasa");
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
            event.register(LargeItemRenderer.getModelResourceLocation(Valoria.ID, "skin/swordfish"));
        }

        @SubscribeEvent
        public static void modelBakeSkins(ModelEvent.ModifyBakingResult event){
            Map<ResourceLocation, BakedModel> map = event.getModels();
            bakeArmor(map);
            bakeKatana(map);
            for(RegistryObject<Item> item : ItemsRegistry.ITEMS.getEntries()){
                if(item.get() instanceof ConfigurableBowItem){
                    FluffyFurModels.addBowItemModel(map, item.getId(), new ModItemModelProperties());
                }
            }

            FluffyFurItemSkins.addSkinModel(map, ItemsRegistry.quantumReaper.getId());
            FluffyFurItemSkins.addLargeModel(map, Valoria.ID, "muramasa");
            FluffyFurItemSkins.addLargeModel(map, Valoria.ID, "brand");
            FluffyFurItemSkins.addLargeModel(map, Valoria.ID, "arcane_gold_blaze_reap");
            FluffyFurItemSkins.addLargeModel(map, Valoria.ID, "swordfish");
            LargeItemRenderer.bakeModel(map, Valoria.ID, "blaze_reap", new ItemSkinItemOverrides());
        }
    }

    @OnlyIn(Dist.CLIENT)
    private static void registerKatana(ModelEvent.RegisterAdditional event){
        for(RegistryObject<Item> item : ItemsRegistry.ITEMS.getEntries()){
            if(item.get() instanceof KatanaItem && ((KatanaItem) item.get()).builder.hasLargeModel){
                event.register(LargeItemRenderer.getModelResourceLocation(Valoria.ID, item.getId().getPath()));
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    private static void bakeKatana(Map<ResourceLocation, BakedModel> map){
        for(RegistryObject<Item> item : ItemsRegistry.ITEMS.getEntries()){
            if(item.get() instanceof KatanaItem katana){
                if(katana.builder.hasLargeModel){
                    LargeItemRenderer.bakeModel(map, Valoria.ID, item.getId().getPath(), new ItemSkinItemOverrides());
                } else {
                    FluffyFurItemSkins.addSkinModel(map, item.getId());
                }
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    private static void bakeArmor(Map<ResourceLocation, BakedModel> map){
        for(RegistryObject<Item> item : ItemsRegistry.ITEMS.getEntries()){
            if(item.get() instanceof SkinableArmorItem){
                FluffyFurItemSkins.addSkinModel(map, item.getId());
            }
        }
    }
}