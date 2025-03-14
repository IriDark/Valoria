package com.idark.valoria.registries.item.skins;

import com.idark.valoria.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.item.armor.item.*;
import com.idark.valoria.registries.item.skins.entries.*;
import com.idark.valoria.registries.item.types.*;
import com.idark.valoria.registries.item.types.ranged.*;
import com.idark.valoria.util.*;
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
import pro.komaru.tridot.client.model.*;
import pro.komaru.tridot.client.model.item.*;
import pro.komaru.tridot.client.model.render.item.*;
import pro.komaru.tridot.common.registry.item.*;
import pro.komaru.tridot.common.registry.item.builders.*;
import pro.komaru.tridot.common.registry.item.skins.*;
import pro.komaru.tridot.common.registry.item.types.*;

import java.util.*;

public class SkinsRegistry{
    public static ItemSkin THE_FALLEN_COLLECTOR, ARCANE_GOLD, CYBERPUNK, MIDNIGHT, MURAMASA, FISH, STAR_DIVIDER;

    public static void init(){
        THE_FALLEN_COLLECTOR = new SkinBuilder(Valoria.ID, "the_fallen_collector").color(Pal.seaGreen).contributor("Kerdo").style(Styles.nature)
                .add(new TheFallenCollectorSkinEntry(ArmorItem.class, Valoria.ID + ":textures/models/armor/skin/the_fallen_collector")
                        .addArmorSkin(EquipmentSlot.HEAD, Valoria.ID + ":the_fallen_collector_crown")
                        .addArmorSkin(EquipmentSlot.CHEST, Valoria.ID + ":the_fallen_collector_coat"))
                .add(new ItemExtendingSkinEntry(KatanaItem.class, Valoria.ID + ":brand"))
                .build();
        ARCANE_GOLD = new SkinBuilder(Valoria.ID, "arcane_gold").color(Pal.arcaneGold).contributor("MaxBogomol").style(Styles.arcaneGold)
                .add(new ItemExtendingSkinEntry(ConfigurableBowItem.class, Valoria.ID + ":arcane_wood_bow"))
                .add(new ItemExtendingSkinEntry(BlazeReapItem.class, Valoria.ID + ":arcane_gold_blaze_reap"))
                .build();
        CYBERPUNK = new SkinBuilder(Valoria.ID, "cyberpunk").color(Pal.majestyPurple).contributor("Auriny").style(Styles.nihility)
                .add(new ItemSupplierSkinEntry(() -> ItemsRegistry.quantumReaper.get(), Valoria.ID + ":cyberpunk_quantum_reaper"))
                .build();
        MIDNIGHT = new SkinBuilder(Valoria.ID, "midnight").color(Pal.majestyPurple)
                .add(new ItemSupplierSkinEntry(() -> ItemsRegistry.quantumReaper.get(), Valoria.ID + ":midnight_quantum_reaper"))
                .build();
        MURAMASA = new SkinBuilder(Valoria.ID, "muramasa").color(Pal.majestyPurple).contributor("Auriny").style(Styles.nihility)
                .add(new ItemSupplierSkinEntry(() -> ItemsRegistry.murasama.get(), Valoria.ID + ":muramasa"))
                .build();
        FISH = new SkinBuilder(Valoria.ID, "swordfish").color(Pal.crystalBlue).contributor("Skoow").style(Styles.aquarius)
                .add(new ItemSupplierSkinEntry(() -> ItemsRegistry.murasama.get(), Valoria.ID + ":swordfish"))
                .build();
        STAR_DIVIDER = new SkinBuilder(Valoria.ID, "star_divider").color((Pal.verySoftPink)).contributor("Rainach").style(Styles.nihility)
            .add(new ItemSupplierSkinEntry(() -> ItemsRegistry.quantumReaper.get(), Valoria.ID + ":star_divider"))
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
        ItemSkinHandler.register(STAR_DIVIDER);
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
        ItemSkinModels.addSkin(Valoria.ID + ":star_divider");
        ItemSkinModels.addSkin(Valoria.ID + ":muramasa");
    }

    @Mod.EventBusSubscriber(modid = Valoria.ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientRegistryEvents{
        @SubscribeEvent
        public static void modelRegistrySkins(ModelEvent.RegisterAdditional event){
            registerKatana(event);
            for(RegistryObject<Item> item : ItemsRegistry.ITEMS.getEntries()){
                if(item.get() instanceof ConfigurableBowItem){
                    TridotModels.addBowItemModel(event, Valoria.ID, item.getId().getPath());
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
                    TridotModels.addBowItemModel(map, item.getId());
                }
            }

            TridotItemSkins.addSkinModel(map, ItemsRegistry.quantumReaper.getId());
            TridotItemSkins.addLargeModel(map, Valoria.ID, "muramasa");
            TridotItemSkins.addLargeModel(map, Valoria.ID, "brand");
            TridotItemSkins.addLargeModel(map, Valoria.ID, "arcane_gold_blaze_reap");
            TridotItemSkins.addLargeModel(map, Valoria.ID, "swordfish");
            LargeItemRenderer.bakeModel(map, Valoria.ID, "blaze_reap", new ItemSkinItemOverrides());
        }
    }

    @OnlyIn(Dist.CLIENT)
    private static void registerKatana(ModelEvent.RegisterAdditional event){
        for(RegistryObject<Item> item : ItemsRegistry.ITEMS.getEntries()){
            if(item.get() instanceof KatanaItem && ((KatanaItem)item.get()).builder.hasLargeModel){
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
                }else{
                    TridotItemSkins.addSkinModel(map, item.getId());
                }
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    private static void bakeArmor(Map<ResourceLocation, BakedModel> map){
        for(RegistryObject<Item> item : ItemsRegistry.ITEMS.getEntries()){
            if(item.get() instanceof SkinableArmorItem){
                TridotItemSkins.addSkinModel(map, item.getId());
            }
        }
    }
}