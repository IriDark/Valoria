package com.idark.valoria.registries.entity.npc;

import com.google.common.collect.ImmutableSet;
import com.idark.valoria.Valoria;
import com.idark.valoria.registries.MiscRegistry;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;
import java.util.function.Predicate;

public class VillagerProfessionRegistry{
    public static final DeferredRegister<VillagerProfession> PROFESSION = DeferredRegister.create(ForgeRegistries.VILLAGER_PROFESSIONS, Valoria.ID);
    public static final RegistryObject<VillagerProfession> JEWELER = PROFESSION.register("jeweler", () -> register("jeweler", MiscRegistry.JEWELER.getKey(), SoundEvents.VILLAGER_WORK_TOOLSMITH));

    private static VillagerProfession register(String pName, ResourceKey<PoiType> pJobSite, @Nullable SoundEvent pWorkSound){
        return register(pName, (p_219668_) -> p_219668_.is(pJobSite), (p_219640_) -> p_219640_.is(pJobSite), pWorkSound);
    }

    private static VillagerProfession register(String pName, Predicate<Holder<PoiType>> pHeldJobSite, Predicate<Holder<PoiType>> pAcquirableJobSites, @Nullable SoundEvent pWorkSound){
        return register(pName, pHeldJobSite, pAcquirableJobSites, ImmutableSet.of(), ImmutableSet.of(), pWorkSound);
    }

    private static VillagerProfession register(String pName, ResourceKey<PoiType> pJobSite, ImmutableSet<Item> pRequestedItems, ImmutableSet<Block> pSecondaryPoi, @Nullable SoundEvent pWorkSound){
        return register(pName, (p_238234_) -> {
            return p_238234_.is(pJobSite);
        }, (p_238237_) -> {
            return p_238237_.is(pJobSite);
        }, pRequestedItems, pSecondaryPoi, pWorkSound);
    }

    private static VillagerProfession register(String pName, Predicate<Holder<PoiType>> pHeldJobSite, Predicate<Holder<PoiType>> pAcquirableJobSites, ImmutableSet<Item> pRequestedItems, ImmutableSet<Block> pSecondaryPoi, @Nullable SoundEvent pWorkSound){
        return new VillagerProfession(pName, pHeldJobSite, pAcquirableJobSites, pRequestedItems, pSecondaryPoi, pWorkSound);
    }

    public static void register(IEventBus eventBus){
        PROFESSION.register(eventBus);
    }
}
