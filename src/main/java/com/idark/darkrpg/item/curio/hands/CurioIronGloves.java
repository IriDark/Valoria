package com.idark.darkrpg.item.curio.hands;

import com.idark.darkrpg.DarkRPG;
import com.idark.darkrpg.item.ModItems;
import com.idark.darkrpg.item.ModItemGroup;
import com.idark.darkrpg.item.curio.*;
import com.idark.darkrpg.client.render.curio.model.*;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import top.theillusivec4.curios.api.*;
import top.theillusivec4.curios.api.type.capability.*;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.common.ForgeMod;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.UUID;
import java.util.List;

public class CurioIronGloves extends RPGCurioItem {
	
   private static final ResourceLocation GLOVES_TEXTURE = new ResourceLocation(DarkRPG.MOD_ID, "textures/entity/iron_gloves.png");

   public CurioIronGloves(Properties properties) {
        super(properties);
	}
	
    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext,
                                                                        UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> atts = LinkedHashMultimap.create();
        atts.put(Attributes.ARMOR, new AttributeModifier(uuid, "bonus", 1.5, AttributeModifier.Operation.ADDITION));
        return atts;
		}
		
    @Override
    public void curioTick(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        PlayerEntity player = (PlayerEntity) livingEntity;

        if(random.nextFloat() > 1.0f) {
        stack.damageItem(1, player, p -> CuriosApi.getCuriosHelper().onBrokenCurio(
        SlotTypePreset.HANDS.getIdentifier(), index, p));
	    }
	}
	
    @Override
    public void render(String identifier, int index, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int light, LivingEntity livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, ItemStack stack) {
        super.render(identifier, index, matrixStack, renderTypeBuffer, light, livingEntity, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, stack);
        HandsModel model = new HandsModel();
        model.setLivingAnimations(livingEntity, limbSwing, limbSwingAmount, partialTicks);
        model.setRotationAngles(livingEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        ICurio.RenderHelper.followBodyRotations(livingEntity, model);
        IVertexBuilder vertexBuilder = ItemRenderer.getBuffer(renderTypeBuffer, model.getRenderType(GLOVES_TEXTURE), false, stack.hasEffect());
        model.render(matrixStack, vertexBuilder, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }
    @Override
    public boolean canRender(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        return true;
    }
}
