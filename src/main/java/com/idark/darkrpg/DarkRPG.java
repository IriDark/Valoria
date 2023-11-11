package com.idark.darkrpg;

import com.google.common.collect.ImmutableMap;
import com.idark.darkrpg.block.ModBlocks;
import com.idark.darkrpg.block.types.ModWoodTypes;
import com.idark.darkrpg.client.event.ClientTickHandler;
import com.idark.darkrpg.client.render.DashOverlayRender;
import com.idark.darkrpg.client.render.gui.MagmaBarRender;
import com.idark.darkrpg.client.render.gui.TooltipEventHandler;
import com.idark.darkrpg.client.render.model.item.Item2DRenderer;
import com.idark.darkrpg.client.render.model.tileentity.CrusherTileEntityRenderer;
import com.idark.darkrpg.client.render.model.tileentity.PedestalTileEntityRenderer;
import com.idark.darkrpg.config.ClientConfig;
import com.idark.darkrpg.config.Config;
import com.idark.darkrpg.effect.ModEffects;
import com.idark.darkrpg.enchant.ModEnchantments;
import com.idark.darkrpg.entity.ModEntityTypes;
import com.idark.darkrpg.entity.renderer.*;
import com.idark.darkrpg.item.ModItemGroup;
import com.idark.darkrpg.item.ModItems;
import com.idark.darkrpg.paintings.ModPaintings;
import com.idark.darkrpg.tileentity.ModTileEntities;
import com.idark.darkrpg.util.ModItemModelProperties;
import com.idark.darkrpg.util.ModSoundRegistry;
import com.idark.darkrpg.util.WorldRenderHandler;
import com.idark.darkrpg.util.particle.ModParticles;
import com.idark.darkrpg.util.particle.SlashParticleType;
import com.idark.darkrpg.util.particle.SparkleParticleType;
import com.idark.darkrpg.world.WorldGen;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.SlotTypePreset;

import java.io.IOException;

@Mod(DarkRPG.MOD_ID)
	    public class DarkRPG {
	    public static final String MOD_ID = "darkrpg";

	public DarkRPG() {
	    InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.RING.getMessageBuilder().build());
	    InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.BELT.getMessageBuilder().build());
	    InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.HANDS.getMessageBuilder().build());
	    InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.NECKLACE.getMessageBuilder().build());
	    InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.HEAD.getMessageBuilder().build());
		InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.CHARM.getMessageBuilder().build());
	    
	    FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
	    
	    IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
		ModSoundRegistry.SOUNDS.register(eventBus);
		ModEffects.register(eventBus);
	    ModEnchantments.register(eventBus);		
		ModPaintings.register(eventBus);
	    ModItems.register(eventBus);
		ModBlocks.register(eventBus);
		ModTileEntities.register(eventBus);
		ModEntityTypes.register(eventBus);
        ModParticles.register(eventBus);

		IEventBus forgeBus = MinecraftForge.EVENT_BUS;

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ClientConfig.SPEC);

        forgeBus.register(new WorldGen());

		DistExecutor.unsafeCallWhenOn(Dist.CLIENT, () -> () -> {
			forgeBus.addListener(ClientTickHandler::clientTickEnd);
			forgeBus.addListener(WorldRenderHandler::onRenderWorldLast);
			forgeBus.addListener(DashOverlayRender::tick);
			forgeBus.addListener(DashOverlayRender::onDrawScreenPost);
			forgeBus.addListener(MagmaBarRender::onDrawScreenPost);
			forgeBus.addListener(TooltipEventHandler::onPostTooltipEvent);
			//forgeBus.addListener(TooltipEventHandler::onTooltip);
			return new Object();
		});

		ModItemGroup.register(eventBus);
		eventBus.addListener(ModItemGroup::addCreative);
		
	    MinecraftForge.EVENT_BUS.register(this);
	}
		private void setup(final FMLCommonSetupEvent event) {
        //WorldGen.init();

        event.enqueueWork(() -> {
		AxeItem.STRIPPABLES = new ImmutableMap.Builder<Block, Block>().putAll(AxeItem.STRIPPABLES)
		.put(ModBlocks.SHADELOG.get(), ModBlocks.STRIPPED_SHADELOG.get())
		.put(ModBlocks.SHADEWOOD.get(), ModBlocks.STRIPPED_SHADEWOOD.get()).build();
       
		WoodType.register(ModWoodTypes.SHADEWOOD);
	    });
        //DeferredWorkQueue.runLater(() -> {
		//GlobalEntityTypeAttributes.put(ModEntityTypes.SWAMP_WANDERER.get(), SwampWandererEntity.setCustomAttributes().build());
        //GlobalEntityTypeAttributes.put(ModEntityTypes.MANNEQUIN.get(), MannequinEntity.setCustomAttributes().build());
        //GlobalEntityTypeAttributes.put(ModEntityTypes.GOBLIN.get(), GoblinEntity.setCustomAttributes().build());
        //});
        }
	    private void processIMC(final InterModProcessEvent event) {
	    // some example code to receive and process InterModComms from other mods
	    }

		
		/*@SubscribeEvent
		public static void registerModifierSerializers(@Nonnull final RegistryEvent.Register<GlobalLootModifierSerializer<?>>event) {
			event.getRegistry().registerAll(
			new LootStructureAdditionModifier.Serializer().setRegistryName
			(new ResourceLocation(DarkRPG.MOD_ID,"miners_bag")),
			new LootStructureAdditionModifier.Serializer().setRegistryName
			(new ResourceLocation(DarkRPG.MOD_ID,"gems_bag"))
			);
		}*/

}