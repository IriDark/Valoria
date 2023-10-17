package com.idark.darkrpg;

import com.google.common.collect.ImmutableMap;
import com.idark.darkrpg.block.*;
import com.idark.darkrpg.block.types.*;
import com.idark.darkrpg.client.event.*;
import com.idark.darkrpg.client.render.DashOverlayRender;
import com.idark.darkrpg.client.render.gui.*;
import com.idark.darkrpg.client.render.model.item.Item2DRenderer;
import com.idark.darkrpg.client.render.model.tileentity.*;
import com.idark.darkrpg.config.*;
import com.idark.darkrpg.effect.ModEffects;
import com.idark.darkrpg.entity.ModEntityTypes;
import com.idark.darkrpg.entity.custom.*;
import com.idark.darkrpg.entity.renderer.*;
import com.idark.darkrpg.item.ModItems;
import com.idark.darkrpg.paintings.ModPaintings;
import com.idark.darkrpg.tileentity.*;
import com.idark.darkrpg.util.*;
import com.idark.darkrpg.util.particle.*;
import com.idark.darkrpg.enchant.*;
import com.idark.darkrpg.world.*;
import net.minecraft.block.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.client.renderer.tileentity.SignTileEntityRenderer;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.SlotTypePreset;

import javax.annotation.Nonnull;

	    @Mod(DarkRPG.MOD_ID)
	    public class DarkRPG {
	    public static final String MOD_ID = "darkrpg";
	    
	    public DarkRPG() {
	    InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE,
                () -> SlotTypePreset.RING.getMessageBuilder().build());
	    InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE,
                () -> SlotTypePreset.BELT.getMessageBuilder().build());
	    InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE,
                () -> SlotTypePreset.HANDS.getMessageBuilder().build());
	    InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE,
                () -> SlotTypePreset.NECKLACE.getMessageBuilder().build());
	    InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE,
                () -> SlotTypePreset.HEAD.getMessageBuilder().build());
		InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE,
                () -> SlotTypePreset.CHARM.getMessageBuilder().build());
	    
	    FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
	    FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
	    
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
        forgeBus.addListener(ClientTickHandler::clientTickEnd);
        forgeBus.addListener(WorldRenderHandler::onRenderWorldLast);	
		forgeBus.addListener(DashOverlayRender::tick);
		forgeBus.addListener(DashOverlayRender::onDrawScreenPost);
		forgeBus.addListener(MagmaBarRender::onDrawScreenPost);		
		forgeBus.addListener(TooltipEventHandler::onPostTooltipEvent);
		forgeBus.addListener(TooltipEventHandler::onTooltip);
		
	    MinecraftForge.EVENT_BUS.register(this);
	}
	
	    private void doClientStuff(final FMLClientSetupEvent event) {
	    event.enqueueWork(() -> {
		RenderTypeLookup.setRenderLayer(ModBlocks.FALSEFLOWER.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.FALSEFLOWER_SMALL.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.SOULFLOWER.get(), RenderType.cutout());
	    RenderTypeLookup.setRenderLayer(ModBlocks.VOID_ROOTS.get(), RenderType.cutout());
	    RenderTypeLookup.setRenderLayer(ModBlocks.GAIB_ROOTS.get(), RenderType.cutout());
	    RenderTypeLookup.setRenderLayer(ModBlocks.KARUSAKAN_ROOTS.get(), RenderType.cutout());
	    RenderTypeLookup.setRenderLayer(ModBlocks.DRIED_PLANT.get(), RenderType.cutout());
	    RenderTypeLookup.setRenderLayer(ModBlocks.DRIED_ROOTS.get(), RenderType.cutout());
	    RenderTypeLookup.setRenderLayer(ModBlocks.ALOE_SMALL.get(), RenderType.cutout());
	    RenderTypeLookup.setRenderLayer(ModBlocks.ALOE.get(), RenderType.cutout());
	    RenderTypeLookup.setRenderLayer(ModBlocks.CATTAIL.get(), RenderType.cutout());
	    RenderTypeLookup.setRenderLayer(ModBlocks.SOULROOT.get(), RenderType.cutout());	    
	    RenderTypeLookup.setRenderLayer(ModBlocks.CRIMSON_SOULROOT.get(), RenderType.cutout());	    
	    RenderTypeLookup.setRenderLayer(ModBlocks.DOUBLE_SOULROOT.get(), RenderType.cutout());	    
	    RenderTypeLookup.setRenderLayer(ModBlocks.MAGMAROOT.get(), RenderType.cutout());	    
	    RenderTypeLookup.setRenderLayer(ModBlocks.DOUBLE_MAGMAROOT.get(), RenderType.cutout());	    
	    RenderTypeLookup.setRenderLayer(ModBlocks.GOLDY.get(), RenderType.cutout());	    
	    RenderTypeLookup.setRenderLayer(ModBlocks.DOUBLE_GOLDY.get(), RenderType.cutout());	    
	    RenderTypeLookup.setRenderLayer(ModBlocks.BLOODROOT.get(), RenderType.cutout());	    
	    RenderTypeLookup.setRenderLayer(ModBlocks.RAJUSH.get(), RenderType.cutout());	    
	    RenderTypeLookup.setRenderLayer(ModBlocks.ELEMENTAL_MANIPULATOR.get(), RenderType.cutout());
	    RenderTypeLookup.setRenderLayer(ModBlocks.SPIDER_EGG.get(), RenderType.cutout());
	    RenderTypeLookup.setRenderLayer(ModBlocks.SKULLY_PEDESTAL.get(), RenderType.cutout());
	    RenderTypeLookup.setRenderLayer(ModBlocks.ELEGANT_PEDESTAL.get(), RenderType.cutout());
	    RenderTypeLookup.setRenderLayer(ModBlocks.POT_SMALL.get(), RenderType.cutout());
	    RenderTypeLookup.setRenderLayer(ModBlocks.POT_SMALL_HANDLESS.get(), RenderType.cutout());
	    RenderTypeLookup.setRenderLayer(ModBlocks.POT_LONG.get(), RenderType.cutout());
	    RenderTypeLookup.setRenderLayer(ModBlocks.POT_LONG_HANDLESS.get(), RenderType.cutout());
	    RenderTypeLookup.setRenderLayer(ModBlocks.POT_LONG_MOSSY.get(), RenderType.cutout());
	    RenderTypeLookup.setRenderLayer(ModBlocks.POT_LONG_MOSSY_HANDLESS.get(), RenderType.cutout());
	    RenderTypeLookup.setRenderLayer(ModBlocks.BRONZE_GLASS.get(), RenderType.cutout());
	    RenderTypeLookup.setRenderLayer(ModBlocks.BRONZE_LAMP.get(), RenderType.cutout());
	    RenderTypeLookup.setRenderLayer(ModBlocks.DECORATED_BRONZE_LAMP.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.SHADEWOOD_PRESSURE_PLATE.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.SHADEWOOD_BUTTON.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.SHADEWOOD_DOOR.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.SHADEWOOD_TRAPDOOR.get(), RenderType.cutout());
	    RenderTypeLookup.setRenderLayer(ModBlocks.BRONZE_DOOR.get(), RenderType.cutout());
	    RenderTypeLookup.setRenderLayer(ModBlocks.BRONZE_TRAPDOOR.get(), RenderType.cutout());
	    RenderTypeLookup.setRenderLayer(ModBlocks.BRONZE_TRAPDOOR2.get(), RenderType.cutout());
	    RenderTypeLookup.setRenderLayer(ModBlocks.AMBER_CRYSTAL.get(), RenderType.cutout());
	    RenderTypeLookup.setRenderLayer(ModBlocks.AMETHYST_CRYSTAL.get(), RenderType.cutout());
	    RenderTypeLookup.setRenderLayer(ModBlocks.RUBY_CRYSTAL.get(), RenderType.cutout());
	    RenderTypeLookup.setRenderLayer(ModBlocks.SAPPHIRE_CRYSTAL.get(), RenderType.cutout());
	    RenderTypeLookup.setRenderLayer(ModBlocks.VOID_CRYSTAL.get(), RenderType.cutout());
	    RenderTypeLookup.setRenderLayer(ModBlocks.SPIKES.get(), RenderType.cutout());
	    RenderTypeLookup.setRenderLayer(ModBlocks.SHADEWOOD_LEAVES.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.SHADEWOOD_SAPLING.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.POTTED_SHADEWOOD_SAPLING.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.POTTED_FALSEFLOWER_SMALL.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.POTTED_FALSEFLOWER.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.POTTED_DRIED_ROOTS.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.POTTED_DRIED_PLANT.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.POTTED_SOULFLOWER.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.POTTED_SOULROOT.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.POTTED_VOID_ROOTS.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.POTTED_SOULROOT.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.POTTED_CRIMSON_SOULROOT.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.POTTED_RAJUSH.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.POTTED_MAGMAROOT.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.POTTED_GOLDY.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.POTTED_BLOODROOT.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.POTTED_ALOE_SMALL.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.QUICKSAND.get(), RenderType.cutout());
		
        ClientRegistry.bindTileEntityRenderer(ModTileEntities.CRUSHER_TILE_ENTITY.get(), CrusherTileEntityRenderer::new);
        ClientRegistry.bindTileEntityRenderer(ModTileEntities.PEDESTAL_TILE_ENTITY.get(), PedestalTileEntityRenderer::new);
		ClientRegistry.bindTileEntityRenderer(ModTileEntities.SIGN_TILE_ENTITIES.get(), SignTileEntityRenderer::new);
	    Atlases.addWoodType(ModWoodTypes.SHADEWOOD);
		});
	    
	    EntitySpawnPlacementRegistry.register(ModEntityTypes.SWAMP_WANDERER.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND,
	    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::checkAnyLightMonsterSpawnRules);
	    EntitySpawnPlacementRegistry.register(ModEntityTypes.GOBLIN.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND,
	    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::checkAnyLightMonsterSpawnRules);
	    
	    RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.GOBLIN.get(), GoblinRenderer::new);
	    RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.SWAMP_WANDERER.get(), SwampWandererRenderer::new);
	    RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.MANNEQUIN.get(), MannequinRenderer::new);
	    RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.KUNAI.get(), KunaiRenderer::new);
	    RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.POISONED_KUNAI.get(), PoisonedKunaiRenderer::new);
	    
	    ModItemModelProperties.makeBow(ModItems.SAMURAI_LONG_BOW.get());
	    ModItemModelProperties.makeBow(ModItems.NATURE_BOW.get());		
	    ModItemModelProperties.makeBow(ModItems.AQUARIUS_BOW.get());
	    ModItemModelProperties.makeBow(ModItems.BOW_OF_DARKNESS.get());
	    ModItemModelProperties.makeBow(ModItems.PHANTASM_BOW.get());
		ModItemModelProperties.makeSize(ModItems.SOUL_COLLECTOR.get());
	    }
		private void setup(final FMLCommonSetupEvent event) {
        WorldGen.init();

        event.enqueueWork(() -> {
		AxeItem.STRIPABLES = new ImmutableMap.Builder<Block, Block>().putAll(AxeItem.STRIPABLES)
		.put(ModBlocks.SHADELOG.get(), ModBlocks.STRIPPED_SHADELOG.get())
		.put(ModBlocks.SHADEWOOD.get(), ModBlocks.STRIPPED_SHADEWOOD.get()).build();
       
		WoodType.register(ModWoodTypes.SHADEWOOD);
	    });
        DeferredWorkQueue.runLater(() -> {
		GlobalEntityTypeAttributes.put(ModEntityTypes.SWAMP_WANDERER.get(), SwampWandererEntity.setCustomAttributes().build());
        GlobalEntityTypeAttributes.put(ModEntityTypes.MANNEQUIN.get(), MannequinEntity.setCustomAttributes().build());
        GlobalEntityTypeAttributes.put(ModEntityTypes.GOBLIN.get(), GoblinEntity.setCustomAttributes().build());
        });
        }
	    private void processIMC(final InterModProcessEvent event) {
	    // some example code to receive and process InterModComms from other mods
	    }

		@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
		public static class RegistryEvents {

		@SubscribeEvent
		public static void onModelRegistryEvent(ModelRegistryEvent event) {
			if (ClientConfig.IN_HAND_MODELS_32X.get()) {
				for (String item : Item2DRenderer.HAND_MODEL_ITEMS) {
					ModelLoader.addSpecialModel(new ModelResourceLocation(MOD_ID+":" + item + "_in_hand", "inventory"));
				}
			}
		}

		@SubscribeEvent
		public static void onModelBakeEvent(ModelBakeEvent event) {
		if (ClientConfig.IN_HAND_MODELS_32X.get()) {
			Item2DRenderer.onModelBakeEvent(event);
			}
		}
		
		@OnlyIn(Dist.CLIENT)
		@SubscribeEvent
		public static void registerFactories(ParticleFactoryRegisterEvent event) {
			Minecraft.getInstance().particleEngine.register(ModParticles.SPARKLE_PARTICLE.get(), SparkleParticleType.Factory::new);
			Minecraft.getInstance().particleEngine.register(ModParticles.PHANTOM_SLASH.get(), SlashParticleType.Factory::new);
			Minecraft.getInstance().particleEngine.register(ModParticles.TRANSFORM_PARTICLE.get(), SparkleParticleType.Factory::new);
			Minecraft.getInstance().particleEngine.register(ModParticles.GEODE_PARTICLE.get(), SparkleParticleType.Factory::new);
		}	
		
		@SubscribeEvent
		public static void registerModifierSerializers(@Nonnull final RegistryEvent.Register<GlobalLootModifierSerializer<?>>event) {
			event.getRegistry().registerAll(
			new LootStructureAdditionModifier.Serializer().setRegistryName
			(new ResourceLocation(DarkRPG.MOD_ID,"miners_bag")),
			new LootStructureAdditionModifier.Serializer().setRegistryName
			(new ResourceLocation(DarkRPG.MOD_ID,"gems_bag"))
			);
		}
	}
}