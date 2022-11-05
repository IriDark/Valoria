package com.idark.darkrpg;

import com.idark.darkrpg.item.ModItems;
import net.minecraft.client.gui.ScreenManager;
import com.idark.darkrpg.block.ModBlocks;
import com.idark.darkrpg.entity.ModEntityTypes;
import com.idark.darkrpg.entity.custom.*;
import com.idark.darkrpg.entity.render.*;
import com.idark.darkrpg.entity.model.*;
import com.idark.darkrpg.paintings.ModPaintings;
import com.idark.darkrpg.util.ModItemModelProperties;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.SlotTypePreset;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.DeferredWorkQueue;

@Mod(DarkRPG.MOD_ID)
public class DarkRPG {
    public static final String MOD_ID = "darkrpg";
    
    public DarkRPG() {
		InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE,
                () -> SlotTypePreset.RING.getMessageBuilder().size(2).build());
        InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE,
                () -> SlotTypePreset.BELT.getMessageBuilder().build());
        InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE,
                () -> SlotTypePreset.HANDS.getMessageBuilder().size(2).build());
        InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE,
                () -> SlotTypePreset.NECKLACE.getMessageBuilder().build());
        InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE,
                () -> SlotTypePreset.HEAD.getMessageBuilder().build());
	    InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE,
                () -> SlotTypePreset.CHARM.getMessageBuilder().size(3).build());
		
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
		
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModItems.register(eventBus);
		ModBlocks.register(eventBus);
        ModEntityTypes.register(eventBus);
		ModPaintings.register(eventBus);
		
		MinecraftForge.EVENT_BUS.register(this);
	}
	 private void doClientStuff(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
	    RenderTypeLookup.setRenderLayer(ModBlocks.CATTAIL.get(), RenderType.getCutout());		
		RenderTypeLookup.setRenderLayer(ModBlocks.ELEMENTAL_MANIPULATOR.get(), RenderType.getCutout());
	   	RenderTypeLookup.setRenderLayer(ModBlocks.SPIDER_EGG.get(), RenderType.getCutout());
	    RenderTypeLookup.setRenderLayer(ModBlocks.PEDESTAL.get(), RenderType.getCutout());
	    RenderTypeLookup.setRenderLayer(ModBlocks.VASE_SMALL.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.VASE_SMALL_1.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.VASE_BIG.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.VASE_BIG_1.get(), RenderType.getCutout());
	    RenderTypeLookup.setRenderLayer(ModBlocks.BRONZE_GLASS.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.BRONZE_LAMP_3.get(), RenderType.getCutout());

  });
     RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.SWAMP_WANDERER.get(), SwampWandererRenderer::new);
	 RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.MANNEQUIN.get(), MannequinRenderer::new);
	 
     ModItemModelProperties.makeBow(ModItems.NATURE_BOW.get());
}
private void setup(final FMLCommonSetupEvent event) {
	    DeferredWorkQueue.runLater(() -> {
            GlobalEntityTypeAttributes.put(ModEntityTypes.SWAMP_WANDERER.get(), SwampWandererEntity.setCustomAttributes().create());
			GlobalEntityTypeAttributes.put(ModEntityTypes.MANNEQUIN.get(), MannequinEntity.setCustomAttributes().create());
  });
}
				private void processIMC(final InterModProcessEvent event) {
					// some example code to receive and process InterModComms from other mods
                }
}
