package com.idark.valoria;

import com.idark.valoria.client.color.*;
import com.idark.valoria.client.model.*;
import com.idark.valoria.client.model.curio.*;
import com.idark.valoria.client.particle.*;
import com.idark.valoria.client.render.entity.*;
import com.idark.valoria.client.render.tile.*;
import com.idark.valoria.client.sounds.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.block.types.*;
import com.idark.valoria.registries.entity.living.decoration.*;
import com.idark.valoria.util.*;
import com.mojang.blaze3d.platform.*;
import com.mojang.blaze3d.vertex.*;
import mod.maxbogomol.fluffy_fur.*;
import mod.maxbogomol.fluffy_fur.client.gui.screen.*;
import mod.maxbogomol.fluffy_fur.client.tooltip.*;
import net.minecraft.client.*;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.blockentity.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.resources.model.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.client.settings.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.event.lifecycle.*;
import org.lwjgl.glfw.*;

import java.io.*;

import static com.idark.valoria.Valoria.*;
import static mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes.*;

public class ValoriaClient {
    private static final String CATEGORY_KEY = "key.category.valoria.general";
    public static final KeyMapping BAG_MENU_KEY = new KeyMapping("key.valoria.bag_menu", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, CATEGORY_KEY);
    public static final KeyMapping JEWELRY_BONUSES_KEY = new KeyMapping("key.valoria.jewelry", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, CATEGORY_KEY);
    public static ModelLayerLocation NECKLACE_LAYER = new ModelLayerLocation(new ResourceLocation(Valoria.ID, "necklace"), "main");
    public static ModelLayerLocation HANDS_LAYER = new ModelLayerLocation(new ResourceLocation(Valoria.ID, "hands"), "main");
    public static ModelLayerLocation HANDS_LAYER_SLIM = new ModelLayerLocation(new ResourceLocation(Valoria.ID, "hands_slim"), "main");
    public static ModelLayerLocation BELT_LAYER = new ModelLayerLocation(new ResourceLocation(Valoria.ID, "belt"), "main");
    public static ModelLayerLocation BAG_LAYER = new ModelLayerLocation(new ResourceLocation(Valoria.ID, "jewelry_bag"), "main");
    public static ModelResourceLocation KEG_MODEL = new ModelResourceLocation(Valoria.ID, "keg_barrel", "");
    public static ModelResourceLocation SPHERE = new ModelResourceLocation(Valoria.ID, "elemental_sphere", "");
    public static ShaderInstance VALORIA_PORTAL;
    public static ShaderInstance getValoriaPortal() {
        return VALORIA_PORTAL;
    }
    public static final RenderStateShard.ShaderStateShard VALORIA_PORTAL_SHADER = new RenderStateShard.ShaderStateShard(ValoriaClient::getValoriaPortal);
    public static final RenderType VALORIA_PORTAL_RENDER_TYPE = RenderType.create(Valoria.ID + ":valoria_portal", DefaultVertexFormat.POSITION, VertexFormat.Mode.QUADS, 256, false, false, RenderType.CompositeState.builder().setShaderState(VALORIA_PORTAL_SHADER).setWriteMaskState(COLOR_WRITE).setTransparencyState(NORMAL_TRANSPARENCY).setTextureState(RenderStateShard.MultiTextureStateShard.builder().add(ValoriaPortalRenderer.BACKGROUND_LOC, false, false).add(ValoriaPortalRenderer.LAYER_LOC, false, false).build()).createCompositeState(false));
    public static RenderType valoriaPortal() {
        return VALORIA_PORTAL_RENDER_TYPE;
    }

    public static CooldownSoundInstance COOLDOWN_SOUND = new CooldownSoundInstance(null);
    public static FluffyFurMod MOD_INSTANCE;
    public static FluffyFurPanorama ECOTONE_PANORAMA;
    public static void setupMenu() {
        MOD_INSTANCE = new FluffyFurMod(Valoria.ID, "Valoria", "0.6.2b").setDev("Iri ♡").setItem(new ItemStack(BlockRegistry.SHADE_BLOSSOM.get()))
        .setNameColor(Pal.verySoftPink).setVersionColor(Pal.cyan)
        .setDescription(Component.translatable("mod_description.valoria"))
        .addGithubLink("https://github.com/IriDark/Valoria")
        .addCurseForgeLink("https://www.curseforge.com/minecraft/mc-mods/valoria")
        .addModrinthLink("https://modrinth.com/mod/valoria")
        .addDiscordLink("https://discord.gg/wWdXpwuPmK");

        ECOTONE_PANORAMA = new FluffyFurPanorama(Valoria.ID + ":ecotone", Component.translatable("panorama.valoria.ecotone"))
        .setMod(MOD_INSTANCE).setItem(new ItemStack(BlockRegistry.SHADE_BLOSSOM.get())).setSort(0)
        .setTexture(new ResourceLocation(Valoria.ID, "textures/gui/title/background/panorama"))
        .setLogo(new ResourceLocation(Valoria.ID, "textures/gui/title/valoria_logo.png"));

        FluffyFurClient.registerMod(MOD_INSTANCE);
        FluffyFurClient.registerPanorama(ECOTONE_PANORAMA);
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class RegistryEvents {

        @SubscribeEvent
        public static void registerAttributeModifiers(FMLClientSetupEvent event){
            TooltipModifierHandler.register(new AttributeTooltipModifier(){
                public boolean isToolBase(AttributeModifier modifier, Player player, TooltipFlag flag){
                    return modifier.getId().equals(BASE_ENTITY_REACH_UUID);
                }
            });

            TooltipModifierHandler.register(new AttributeTooltipModifier(){
                public boolean isToolBase(AttributeModifier modifier, Player player, TooltipFlag flag){
                    return modifier.getId().equals(BASE_PROJECTILE_DAMAGE_UUID);
                }
            });

            TooltipModifierHandler.register(new AttributeTooltipModifier(){
                public boolean isToolBase(AttributeModifier modifier, Player player, TooltipFlag flag){
                    return modifier.getId().equals(BASE_DASH_DISTANCE_UUID);
                }
            });

            TooltipModifierHandler.register(new AttributeTooltipModifier(){
                public boolean isToolBase(AttributeModifier modifier, Player player, TooltipFlag flag){
                    return modifier.getId().equals(BASE_ATTACK_RADIUS_UUID);
                }
            });

            TooltipModifierHandler.register(new AttributeTooltipModifier(){
                public boolean isToolBase(AttributeModifier modifier, Player player, TooltipFlag flag){
                    return modifier.getId().equals(BASE_NECROMANCY_COUNT_UUID);
                }
            });

            TooltipModifierHandler.register(new AttributeTooltipModifier(){
                public boolean isToolBase(AttributeModifier modifier, Player player, TooltipFlag flag){
                    return modifier.getId().equals(BASE_NECROMANCY_LIFETIME_UUID);
                }
            });
        }

        @SubscribeEvent
        public static void ColorMappingBlocks(RegisterColorHandlersEvent.Block event) {
            event.register((state, world, pos, tintIndex) -> ModBlockColors.getInstance().getGrassColor(state, world, pos, tintIndex), ModBlockColors.MODDED_GRASS);
            event.register((state, world, pos, tintIndex) -> ModBlockColors.getInstance().getFoliageColor(state, world, pos, tintIndex), ModBlockColors.MODDED_FOLIAGE);
        }

        @SubscribeEvent
        public static void ColorMappingItems(RegisterColorHandlersEvent.Item event) {
            event.register((stack, tintIndex) -> tintIndex > 0 ? -1 : 12487423, BlockRegistry.ELDRITCH_SAPLING.get(), BlockRegistry.ELDRITCH_LEAVES.get());
            event.register((stack, tintIndex) -> tintIndex > 0 ? -1 : 9100543, BlockRegistry.SHADEWOOD_BRANCH.get(), BlockRegistry.SHADEWOOD_SAPLING.get(), BlockRegistry.SHADEWOOD_LEAVES.get());
            event.register((stack, tintIndex) -> 11301619, BlockRegistry.VOID_GRASS.get(), BlockRegistry.VOID_TAINT.get(), BlockRegistry.VOID_ROOTS.get());
            event.register((p_92708_, p_92709_) -> p_92709_ > 0 ? -1 : ((DyeableLeatherItem) p_92708_.getItem()).getColor(p_92708_), ItemsRegistry.leatherGloves.get());
            event.register((p_92708_, p_92709_) -> p_92709_ > 0 ? -1 : ((DyeableLeatherItem) p_92708_.getItem()).getColor(p_92708_), ItemsRegistry.jewelryBag.get());
        }

        @SubscribeEvent
        public static void doClientStuff(FMLClientSetupEvent event) {
            event.enqueueWork(() -> {
                BlockEntityRenderers.register(BlockEntitiesRegistry.MANIPULATOR_BLOCK_ENTITY.get(), (trd) -> new ManipulatorBlockEntityRenderer());
                BlockEntityRenderers.register(BlockEntitiesRegistry.JEWELRY_BLOCK_ENTITY.get(), (trd) -> new JewelryBlockEntityRender());
                BlockEntityRenderers.register(BlockEntitiesRegistry.KEG_BLOCK_ENTITY.get(), (trd) -> new KegBlockEntityRenderer());
                BlockEntityRenderers.register(BlockEntitiesRegistry.CRUSHABLE_BLOCK_ENTITY.get(), (trd) -> new CrushableBlockRenderer());
                BlockEntityRenderers.register(BlockEntitiesRegistry.CRUSHER_BLOCK_ENTITY.get(), (trd) -> new CrusherBlockEntityRenderer());
                BlockEntityRenderers.register(BlockEntitiesRegistry.PEDESTAL_BLOCK_ENTITY.get(), (trd) -> new PedestalBlockEntityRenderer());
                BlockEntityRenderers.register(BlockEntitiesRegistry.SIGN_BLOCK_ENTITIES.get(), SignRenderer::new);
                BlockEntityRenderers.register(BlockEntitiesRegistry.HANGING_SIGN_BLOCK_ENTITIES.get(), HangingSignRenderer::new);
                BlockEntityRenderers.register(BlockEntitiesRegistry.VALORIA_PORTAL_BLOCK_ENTITY.get(), ValoriaPortalRenderer::new);
                Sheets.addWoodType(ModWoodTypes.ELDRITCH);
                Sheets.addWoodType(ModWoodTypes.SHADEWOOD);
            });

            EntityRenderers.register(EntityTypeRegistry.BOAT.get(), m -> new CustomBoatRenderer(m, false));
            EntityRenderers.register(EntityTypeRegistry.CHEST_BOAT.get(), m -> new CustomBoatRenderer(m, true));

            EntityRenderers.register(EntityTypeRegistry.HAUNTED_MERCHANT.get(), HauntedMerchantRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.NECROMANCER.get(), NecromancerRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.DRAUGR.get(), DraugrRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.GOBLIN.get(), GoblinRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.SWAMP_WANDERER.get(), SwampWandererRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.MANNEQUIN.get(), MannequinRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.KUNAI.get(), KunaiRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.THROWABLE_BOMB.get(), ThrownItemRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.SPECTRAL_BLADE.get(), SpectralBladeRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.MEAT.get(), MeatBlockRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.DEVOURER.get(), DevourerRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.UNDEAD.get(), UndeadRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.PHANTOM_ARROW.get(), AbstractValoriaArrowRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.WICKED_ARROW.get(), AbstractValoriaArrowRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.SOUL_ARROW.get(), AbstractValoriaArrowRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.INFERNAL_ARROW.get(), AbstractValoriaArrowRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.SPEAR.get(), ThrownSpearRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.SHADEWOOD_SPIDER.get(), ShadewoodSpiderRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.SUCCUBUS.get(), SuccubusRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.TROLL.get(), TrollRenderer::new);

            ModItemModelProperties.makeBow(ItemsRegistry.samuraiLongBow.get());
            ModItemModelProperties.makeBow(ItemsRegistry.natureBow.get());
            ModItemModelProperties.makeBow(ItemsRegistry.aquariusBow.get());
            ModItemModelProperties.makeBow(ItemsRegistry.infernalBow.get());
            ModItemModelProperties.makeBow(ItemsRegistry.voidBow.get());
            ModItemModelProperties.makeBow(ItemsRegistry.phantasmBow.get());
            ModItemModelProperties.makeSize(ItemsRegistry.soulCollector.get());
            ModItemModelProperties.makeCooldown(ItemsRegistry.spectralBlade.get());
        }

        @SubscribeEvent
        public static void onModelRegistryEvent(ModelEvent.RegisterAdditional event) {
            event.register(KEG_MODEL);
            event.register(SPHERE);
        }

        @SubscribeEvent
        public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
            event.registerLayerDefinition(ValoriaClient.NECKLACE_LAYER, NecklaceModel::createBodyLayer);
            event.registerLayerDefinition(ValoriaClient.BELT_LAYER, BeltModel::createBodyLayer);
            event.registerLayerDefinition(ValoriaClient.BAG_LAYER, JewelryBagModel::createBodyLayer);
            event.registerLayerDefinition(ValoriaClient.HANDS_LAYER, HandsModel::createBodyLayer);
            event.registerLayerDefinition(ValoriaClient.HANDS_LAYER_SLIM, HandsModelSlim::createBodyLayer);
            for (CustomBoatEntity.Type boatType : CustomBoatEntity.Type.values()) {
                event.registerLayerDefinition(CustomBoatRenderer.createBoatModelName(boatType), BoatModel::createBodyModel);
                event.registerLayerDefinition(CustomBoatRenderer.createChestBoatModelName(boatType), ChestBoatModel::createBodyModel);
            }
        }

        @SubscribeEvent
        public static void registerKeyBindings(RegisterKeyMappingsEvent event) {
            event.register(ValoriaClient.BAG_MENU_KEY);
            event.register(ValoriaClient.JEWELRY_BONUSES_KEY);
        }

        @OnlyIn(Dist.CLIENT)
        @SubscribeEvent
        public static void registerFactories(RegisterParticleProvidersEvent event) {
            ParticleRegistry.registerParticleFactory(event);
        }

        @SubscribeEvent
        public static void registerRenderTypes(FMLClientSetupEvent event) {
            addTranslucentRenderType(VALORIA_PORTAL_RENDER_TYPE);
        }

        @OnlyIn(Dist.CLIENT)
        @SubscribeEvent
        public static void shaderRegistry(RegisterShadersEvent event) throws IOException{
            event.registerShader(new ShaderInstance(event.getResourceProvider(), new ResourceLocation(Valoria.ID, "valoria_portal"), DefaultVertexFormat.POSITION), shader -> VALORIA_PORTAL = shader);
        }
    }
}