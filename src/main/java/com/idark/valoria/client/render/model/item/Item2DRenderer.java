package com.idark.valoria.client.render.model.item;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class Item2DRenderer {

    public static List<Item> handModelItems = new ArrayList<>();

    @SubscribeEvent
    public static void onModelBakeEvent(ModelEvent.ModifyBakingResult event) {
        Map<ResourceLocation, BakedModel> map = event.getModels();
        for (Item item : handModelItems) {
            ResourceLocation itemId = ForgeRegistries.ITEMS.getKey(item.getDefaultInstance().getItem());
            if (itemId == null) {
                System.err.println("[onModelBakeEvent] Failed to get item ID for item: " + item.getName(item.getDefaultInstance()));
                continue;
            }

            String itemIdString = itemId.toString();
            ResourceLocation modelInventory = new ModelResourceLocation(new ResourceLocation(itemIdString), "inventory");
            ResourceLocation modelHand = new ModelResourceLocation(new ResourceLocation(itemIdString + "_in_hand"), "inventory");

            BakedModel bakedModelDefault = map.get(modelInventory);
            BakedModel bakedModelHand = map.get(modelHand);

            if (bakedModelDefault == null) {
                System.out.println("[onModelBakeEvent] No model found for inventory: " + modelInventory);
                continue;
            }

            if (bakedModelHand == null) {
                System.out.println("[onModelBakeEvent] No model found for hand: " + modelHand);
                continue;
            }

            if (Minecraft.getInstance().player != null && bakedModelHand == null || bakedModelDefault == null) {
                Minecraft.getInstance().player.displayClientMessage(Component.literal("Minecraft Tried to crash but Valoria prevented it by skipping some of model renderer some of them may be 16x").withStyle(ChatFormatting.GRAY), true);
            }

            BakedModel modelWrapper = new BakedModel() {
                @Override
                public List<BakedQuad> getQuads(@Nullable BlockState pState, @Nullable Direction pDirection, RandomSource pRandom) {
                    return bakedModelDefault.getQuads(pState, pDirection, pRandom);
                }

                @Override
                public boolean useAmbientOcclusion() {
                    return bakedModelDefault.useAmbientOcclusion();
                }

                @Override
                public boolean isGui3d() {
                    return bakedModelDefault.isGui3d();
                }

                @Override
                public boolean usesBlockLight() {
                    return bakedModelDefault.usesBlockLight();
                }

                @Override
                public boolean isCustomRenderer() {
                    return bakedModelDefault.isCustomRenderer();
                }

                @Override
                public TextureAtlasSprite getParticleIcon() {
                    return bakedModelDefault.getParticleIcon();
                }

                @Override
                public ItemOverrides getOverrides() {
                    return bakedModelDefault.getOverrides();
                }

                @Override
                public @NotNull BakedModel applyTransform(ItemDisplayContext transformType, PoseStack poseStack, boolean applyLeftHandTransform) {
                    BakedModel modelToUse = bakedModelDefault;
                    if (transformType != ItemDisplayContext.GUI && transformType != ItemDisplayContext.GROUND && transformType != ItemDisplayContext.FIXED) {
                        modelToUse = bakedModelHand;
                    }

                    return ForgeHooksClient.handleCameraTransforms(poseStack, modelToUse, transformType, applyLeftHandTransform);
                }
            };

            map.put(modelInventory, modelWrapper);
        }
    }
}