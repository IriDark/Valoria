package com.idark.valoria.client.render.model.item;

import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.resources.model.*;
import net.minecraft.core.*;
import net.minecraft.resources.*;
import net.minecraft.util.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.state.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.client.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.fml.event.lifecycle.*;
import org.jetbrains.annotations.*;

import javax.annotation.Nullable;
import java.util.*;

@OnlyIn(Dist.CLIENT)
public class Item2DRenderer{

    /**
     * Adds the item ID which will be searched. must be "ModId:item_ID"
     * @see com.idark.valoria.Valoria#clientSetup(FMLClientSetupEvent) Valoria Client Setup
     */
    public static List<String> handModelItems = new ArrayList<>();

    @SubscribeEvent
    public static void onModelBakeEvent(ModelEvent.ModifyBakingResult event){
        Map<ResourceLocation, BakedModel> map = event.getModels();
        for(String itemId : handModelItems){
            ResourceLocation modelInventory = new ModelResourceLocation(new ResourceLocation(itemId), "inventory");
            ResourceLocation modelHand = new ModelResourceLocation(new ResourceLocation(itemId + "_in_hand"), "inventory");
            BakedModel bakedModelDefault = map.get(modelInventory);
            BakedModel bakedModelHand = map.get(modelHand);
            if(bakedModelDefault == null){
                System.out.println("[onModelBakeEvent] No model found for inventory: " + modelInventory);
                continue;
            }

            if(bakedModelHand == null){
                System.out.println("[onModelBakeEvent] No model found for hand: " + modelHand);
                continue;
            }

            BakedModel modelWrapper = new BakedModel(){
                @Override
                public List<BakedQuad> getQuads(@Nullable BlockState pState, @Nullable Direction pDirection, RandomSource pRandom){
                    return bakedModelDefault.getQuads(pState, pDirection, pRandom);
                }

                @Override
                public boolean useAmbientOcclusion(){
                    return bakedModelDefault.useAmbientOcclusion();
                }

                @Override
                public boolean isGui3d(){
                    return bakedModelDefault.isGui3d();
                }

                @Override
                public boolean usesBlockLight(){
                    return bakedModelDefault.usesBlockLight();
                }

                @Override
                public boolean isCustomRenderer(){
                    return bakedModelDefault.isCustomRenderer();
                }

                @Override
                public TextureAtlasSprite getParticleIcon(){
                    return bakedModelDefault.getParticleIcon();
                }

                @Override
                public ItemOverrides getOverrides(){
                    return bakedModelDefault.getOverrides();
                }

                @Override
                public @NotNull BakedModel applyTransform(ItemDisplayContext transformType, PoseStack poseStack, boolean applyLeftHandTransform){
                    BakedModel modelToUse = bakedModelDefault;
                    if(transformType != ItemDisplayContext.GUI && transformType != ItemDisplayContext.GROUND && transformType != ItemDisplayContext.FIXED){
                        modelToUse = bakedModelHand;
                    }

                    return ForgeHooksClient.handleCameraTransforms(poseStack, modelToUse, transformType, applyLeftHandTransform);
                }
            };

            map.put(modelInventory, modelWrapper);
        }
    }
}