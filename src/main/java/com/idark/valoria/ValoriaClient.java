package com.idark.valoria;

import com.idark.valoria.block.ModBlocks;
import com.idark.valoria.block.types.ModWoodTypes;
import com.idark.valoria.client.render.curio.model.BeltModel;
import com.idark.valoria.client.render.curio.model.HandsModel;
import com.idark.valoria.client.render.curio.model.HandsModelDefault;
import com.idark.valoria.client.render.curio.model.NecklaceModel;
import com.idark.valoria.client.render.model.item.Item2DRenderer;
import com.idark.valoria.client.render.model.blockentity.KegBlockEntityRenderer;
import com.idark.valoria.client.render.model.blockentity.CrushableBlockRenderer;
import com.idark.valoria.client.render.model.blockentity.CrusherBlockEntityRenderer;
import com.idark.valoria.client.render.model.blockentity.PedestalBlockEntityRenderer;
import com.idark.valoria.config.ClientConfig;
import com.idark.valoria.entity.ModEntityTypes;
import com.idark.valoria.entity.renderer.*;
import com.idark.valoria.item.ModItems;
import com.idark.valoria.block.blockentity.ModBlockEntities;
import com.idark.valoria.util.ModItemModelProperties;
import com.idark.valoria.util.particle.ModParticles;
import com.idark.valoria.util.particle.SlashParticleType;
import com.idark.valoria.util.particle.SparkleParticleType;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.io.IOException;

public class ValoriaClient {
	public static ShaderInstance GLOWING_PARTICLE_SHADER, SPRITE_PARTICLE_SHADER;
	public static ModelLayerLocation NECKLACE_LAYER = new ModelLayerLocation(new ResourceLocation(Valoria.MOD_ID, "necklace"), "main");
	public static ModelLayerLocation HANDS_LAYER = new ModelLayerLocation(new ResourceLocation(Valoria.MOD_ID, "hands"), "main");
	public static ModelLayerLocation HANDS_LAYER_SLIM = new ModelLayerLocation(new ResourceLocation(Valoria.MOD_ID, "hands_slim"), "main");
	public static ModelLayerLocation BELT_LAYER = new ModelLayerLocation(new ResourceLocation(Valoria.MOD_ID, "belt"), "main");
	public static ModelResourceLocation KEG_MODEL = new ModelResourceLocation(Valoria.MOD_ID, "keg_barrel", "");

	public static ShaderInstance getGlowingParticleShader() {
		return GLOWING_PARTICLE_SHADER;
	}

	public static ShaderInstance getSpriteParticleShader() {
		return SPRITE_PARTICLE_SHADER;
	}

	@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
	public static class RegistryEvents {

		@SubscribeEvent
		public static void doClientStuff(FMLClientSetupEvent event) {
			event.enqueueWork(() -> {
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.FALSEFLOWER.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.FALSEFLOWER_SMALL.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.SOULFLOWER.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.VOID_ROOTS.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.GAIB_ROOTS.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.KARUSAKAN_ROOTS.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.DRIED_PLANT.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.DRIED_ROOTS.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.ALOE_SMALL.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.ALOE.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.CATTAIL.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.SOULROOT.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.CRIMSON_SOULROOT.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.DOUBLE_SOULROOT.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.MAGMAROOT.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.DOUBLE_MAGMAROOT.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.GOLDY.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.DOUBLE_GOLDY.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.BLOODROOT.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.RAJUSH.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.ELEMENTAL_MANIPULATOR.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.SPIDER_EGG.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.SKULLY_PEDESTAL.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.ELEGANT_PEDESTAL.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.POT_SMALL.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.POT_SMALL_HANDLES.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.POT_LONG.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.POT_LONG_HANDLES.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.POT_LONG_MOSSY.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.POT_LONG_MOSSY_HANDLES.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.BRONZE_GLASS.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.BRONZE_LAMP.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.BRONZE_TRAPDOOR_GLASS.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.SHADEWOOD_PRESSURE_PLATE.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.SHADEWOOD_BUTTON.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.SHADEWOOD_DOOR.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.SHADEWOOD_TRAPDOOR.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.BRONZE_TRAPDOOR.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.AMBER_CRYSTAL.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.AMETHYST_CRYSTAL.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.RUBY_CRYSTAL.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.SAPPHIRE_CRYSTAL.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.VOID_CRYSTAL.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.SPIKES.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.SHADEWOOD_LEAVES.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.SHADEWOOD_SAPLING.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.POTTED_SHADEWOOD_SAPLING.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.POTTED_FALSEFLOWER_SMALL.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.POTTED_FALSEFLOWER.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.POTTED_DRIED_ROOTS.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.POTTED_DRIED_PLANT.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.POTTED_SOULFLOWER.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.POTTED_SOULROOT.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.POTTED_VOID_ROOTS.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.POTTED_SOULROOT.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.POTTED_CRIMSON_SOULROOT.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.POTTED_RAJUSH.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.POTTED_MAGMAROOT.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.POTTED_GOLDY.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.POTTED_BLOODROOT.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.POTTED_ALOE_SMALL.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.QUICKSAND.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.SUSPICIOUS_ICE.get(), RenderType.translucent());

				BlockEntityRenderers.register(ModBlockEntities.KEG_BLOCK_ENTITY.get(), (trd) -> new KegBlockEntityRenderer());
				BlockEntityRenderers.register(ModBlockEntities.CRUSHABLE_BLOCK_ENTITY.get(), (trd) -> new CrushableBlockRenderer());
				BlockEntityRenderers.register(ModBlockEntities.CRUSHER_BLOCK_ENTITY.get(), (trd) -> new CrusherBlockEntityRenderer());
				BlockEntityRenderers.register(ModBlockEntities.PEDESTAL_BLOCK_ENTITY.get(), (trd) -> new PedestalBlockEntityRenderer());
				BlockEntityRenderers.register(ModBlockEntities.SIGN_BLOCK_ENTITIES.get(), SignRenderer::new);
				BlockEntityRenderers.register(ModBlockEntities.HANGING_SIGN_BLOCK_ENTITIES.get(), HangingSignRenderer::new);
				Sheets.addWoodType(ModWoodTypes.SHADEWOOD);
			});

			EntityRenderers.register(ModEntityTypes.NECROMANCER.get(), NecromancerRenderer::new);
			EntityRenderers.register(ModEntityTypes.DRAUGR.get(), DraugrRenderer::new);
			EntityRenderers.register(ModEntityTypes.GOBLIN.get(), GoblinRenderer::new);
			//EntityRenderers.register(ModEntityTypes.SWAMP_WANDERER.get(), SwampWandererRenderer::new);
			EntityRenderers.register(ModEntityTypes.MANNEQUIN.get(), MannequinRenderer::new);
			EntityRenderers.register(ModEntityTypes.KUNAI.get(), KunaiRenderer::new);
			EntityRenderers.register(ModEntityTypes.SPECTRAL_BLADE.get(), SpectralBladeRenderer::new);
			EntityRenderers.register(ModEntityTypes.POISONED_KUNAI.get(), PoisonedKunaiRenderer::new);
			EntityRenderers.register(ModEntityTypes.MEAT.get(), MeatBlockRenderer::new);

			ModItemModelProperties.makeBow(ModItems.SAMURAI_LONG_BOW.get());
			ModItemModelProperties.makeBow(ModItems.NATURE_BOW.get());
			ModItemModelProperties.makeBow(ModItems.AQUARIUS_BOW.get());
			ModItemModelProperties.makeBow(ModItems.BOW_OF_DARKNESS.get());
			ModItemModelProperties.makeBow(ModItems.PHANTASM_BOW.get());
			ModItemModelProperties.makeSize(ModItems.SOUL_COLLECTOR.get());
			ModItemModelProperties.makeCooldown(ModItems.SPECTRAL_BLADE.get());
		}

		@SubscribeEvent
		public static void onModelRegistryEvent(ModelEvent.RegisterAdditional event) {
			if (ClientConfig.IN_HAND_MODELS_32X.get()) {
				for (String item : Item2DRenderer.HAND_MODEL_ITEMS) {
					event.register(new ModelResourceLocation(new ResourceLocation(Valoria.MOD_ID, item + "_in_hand"), "inventory"));
				}
			}

			event.register(KEG_MODEL);
		}

		@SubscribeEvent
		public static void onModelBakeEvent(ModelEvent.ModifyBakingResult event) {
			if (ClientConfig.IN_HAND_MODELS_32X.get()) {
				Item2DRenderer.onModelBakeEvent(event);
			}
		}

		@SubscribeEvent
		public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
			event.registerLayerDefinition(ValoriaClient.NECKLACE_LAYER, NecklaceModel::createBodyLayer);
			event.registerLayerDefinition(ValoriaClient.BELT_LAYER, BeltModel::createBodyLayer);
			event.registerLayerDefinition(ValoriaClient.HANDS_LAYER, HandsModelDefault::createBodyLayer);
			event.registerLayerDefinition(ValoriaClient.HANDS_LAYER_SLIM, HandsModel::createBodyLayer);
		}

		@OnlyIn(Dist.CLIENT)
		@SubscribeEvent
		public static void registerFactories(RegisterParticleProvidersEvent event) {
			Minecraft.getInstance().particleEngine.register(ModParticles.SPARKLE_PARTICLE.get(), SparkleParticleType.Factory::new);
			Minecraft.getInstance().particleEngine.register(ModParticles.PHANTOM_SLASH.get(), SlashParticleType.Factory::new);
			Minecraft.getInstance().particleEngine.register(ModParticles.TRANSFORM_PARTICLE.get(), SparkleParticleType.Factory::new);
			Minecraft.getInstance().particleEngine.register(ModParticles.GEODE_PARTICLE.get(), SparkleParticleType.Factory::new);
		}

		@OnlyIn(Dist.CLIENT)
		@SubscribeEvent
		public static void shaderRegistry(RegisterShadersEvent event) throws IOException {
			event.registerShader(new ShaderInstance(event.getResourceProvider(), new ResourceLocation("valoria:glowing_particle"), DefaultVertexFormat.PARTICLE),
					shader -> {
						GLOWING_PARTICLE_SHADER = shader;
					});
			event.registerShader(new ShaderInstance(event.getResourceProvider(), new ResourceLocation("valoria:sprite_particle"), DefaultVertexFormat.PARTICLE),
					shader -> {
						SPRITE_PARTICLE_SHADER = shader;
					});
		}
	}
}