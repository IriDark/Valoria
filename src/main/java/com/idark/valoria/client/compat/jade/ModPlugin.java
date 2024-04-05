package com.idark.valoria.client.compat.jade;

import com.idark.valoria.registries.block.entity.types.CrusherBlockEntity;
import com.idark.valoria.registries.block.entity.types.JewelryBlockEntity;
import com.idark.valoria.registries.block.entity.types.KegBlockEntity;
import com.idark.valoria.registries.block.entity.types.ManipulatorBlockEntity;
import com.idark.valoria.registries.block.types.*;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class ModPlugin implements IWailaPlugin {
    public static final ResourceLocation JEWELRY = new ResourceLocation("valoria:jewelry");
    public static final ResourceLocation CRUSHER = new ResourceLocation("valoria:crusher");
    public static final ResourceLocation KEG = new ResourceLocation("valoria:keg");
    public static final ResourceLocation KEY = new ResourceLocation("valoria:key");
    public static final ResourceLocation MANIPULATOR = new ResourceLocation("valoria:manipulator");

    @Override
    public void register(IWailaCommonRegistration registration) {
        registration.registerBlockDataProvider(JewelryProvider.INSTANCE, JewelryBlockEntity.class);
        registration.registerBlockDataProvider(CrusherProvider.INSTANCE, CrusherBlockEntity.class);
        registration.registerBlockDataProvider(KegProvider.INSTANCE, KegBlockEntity.class);
        registration.registerBlockDataProvider(ManipulatorProvider.INSTANCE, ManipulatorBlockEntity.class);
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerBlockComponent(JewelryProvider.INSTANCE, JewelerBlock.class);
        registration.registerBlockComponent(CrusherProvider.INSTANCE, CrusherBlock.class);
        registration.registerBlockComponent(KegProvider.INSTANCE, KegBlock.class);
        registration.registerBlockComponent(KeyBlockProvider.INSTANCE, KeyPadBlock.class);
        registration.registerBlockComponent(ManipulatorProvider.INSTANCE, ManipulatorBlock.class);
    }
}
