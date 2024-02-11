package com.idark.valoria.client.compat.jade;

import com.idark.valoria.block.blockentity.CrusherBlockEntity;
import com.idark.valoria.block.blockentity.JewelryBlockEntity;
import com.idark.valoria.block.blockentity.KegBlockEntity;
import com.idark.valoria.block.types.CrusherBlock;
import com.idark.valoria.block.types.JewelerBlock;
import com.idark.valoria.block.types.KegBlock;
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

    @Override
    public void register(IWailaCommonRegistration registration) {
        registration.registerBlockDataProvider(JewelryProvider.INSTANCE, JewelryBlockEntity.class);
        registration.registerBlockDataProvider(CrusherProvider.INSTANCE, CrusherBlockEntity.class);
        registration.registerBlockDataProvider(KegProvider.INSTANCE, KegBlockEntity.class);
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerBlockComponent(JewelryProvider.INSTANCE, JewelerBlock.class);
        registration.registerBlockComponent(CrusherProvider.INSTANCE, CrusherBlock.class);
        registration.registerBlockComponent(KegProvider.INSTANCE, KegBlock.class);
    }
}
