package com.idark.valoria.core.compat.jade;

import com.idark.valoria.registries.*;
import com.idark.valoria.registries.block.entity.*;
import com.idark.valoria.registries.block.types.*;
import net.minecraft.resources.*;
import net.minecraft.tags.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.*;
import snownee.jade.addon.harvest.*;
import snownee.jade.api.*;

@WailaPlugin
public class ModPlugin implements IWailaPlugin{
    public static final ResourceLocation JEWELRY = new ResourceLocation("valoria:jewelry");
    public static final ResourceLocation CRUSHER = new ResourceLocation("valoria:crusher");
    public static final ResourceLocation KEG = new ResourceLocation("valoria:keg");
    public static final ResourceLocation KEY = new ResourceLocation("valoria:key");
    public static final ResourceLocation MANIPULATOR = new ResourceLocation("valoria:manipulator");

    @Override
    public void register(IWailaCommonRegistration registration){
        registration.registerBlockDataProvider(JewelryProvider.INSTANCE, JewelryBlockEntity.class);
        registration.registerBlockDataProvider(CrusherProvider.INSTANCE, CrusherBlockEntity.class);
        registration.registerBlockDataProvider(KegProvider.INSTANCE, KegBlockEntity.class);
        registration.registerBlockDataProvider(ManipulatorProvider.INSTANCE, ManipulatorBlockEntity.class);
    }

    @Override
    public void registerClient(IWailaClientRegistration registration){
        registration.registerBlockComponent(JewelryProvider.INSTANCE, JewelerBlock.class);
        registration.registerBlockComponent(CrusherProvider.INSTANCE, CrusherBlock.class);
        registration.registerBlockComponent(KegProvider.INSTANCE, KegBlock.class);
        registration.registerBlockComponent(KeyBlockProvider.INSTANCE, UmbralKeyPadBlock.class);
        registration.registerBlockComponent(ManipulatorProvider.INSTANCE, ManipulatorBlock.class);

        registerTier("bronze", TagsRegistry.NEEDS_BRONZE_TOOL, ItemTierRegistry.BRONZE);
        registerTier("pearlium", TagsRegistry.NEEDS_PEARLIUM_TOOL, ItemTierRegistry.PEARLIUM);
        registerTier("cobalt", TagsRegistry.NEEDS_COBALT_TOOL, ItemTierRegistry.COBALT);
        registerTier("ethereal", TagsRegistry.NEEDS_ETHEREAL_TOOL, ItemTierRegistry.ETHEREAL);
        registerTier("nature", TagsRegistry.NEEDS_NATURE_TOOL, ItemTierRegistry.NATURE);
        registerTier("aquarius", TagsRegistry.NEEDS_DEPTH_TOOL, ItemTierRegistry.AQUARIUS);
        registerTier("infernal", TagsRegistry.NEEDS_INFERNAL_TOOL, ItemTierRegistry.INFERNAL);
        registerTier("jade", TagsRegistry.NEEDS_JADE_TOOL, ItemTierRegistry.JADE);
        registerTier("void", TagsRegistry.NEEDS_VOID_TOOL, ItemTierRegistry.NIHILITY);
    }

    public void registerTier(String name, TagKey<Block> tag, Tier tier) {
        HarvestToolProvider.registerHandler(new TieredToolHandler(name, tag, tier, () -> ItemTierRegistry.getTieredItems(tier)));
    }
}
