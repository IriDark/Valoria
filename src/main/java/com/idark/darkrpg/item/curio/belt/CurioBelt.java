package com.idark.darkrpg.item.curio.belt;

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
import net.minecraft.item.*;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.world.World;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.SlotTypePreset;
import top.theillusivec4.curios.api.type.util.ICuriosHelper;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.UUID;
import java.util.Objects;
import java.util.Random;
import java.util.List;


public class CurioBelt extends RPGCurioItem {
	
   private static final ResourceLocation BELT_TEXTURE = new ResourceLocation(DarkRPG.MOD_ID, "textures/entity/leather_belt.png");

    public CurioBelt(Properties properties) {
        super(properties);
	}
	
	@Override
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
    Multimap<Attribute, AttributeModifier> atts = LinkedHashMultimap.create();
    atts.put(Attributes.ARMOR, new AttributeModifier(uuid, "bonus", 0.5, AttributeModifier.Operation.ADDITION));
    CuriosApi.getCuriosHelper().addSlotModifier(atts, "charm", uuid, 2.0, AttributeModifier.Operation.ADDITION);
    return atts;
	}
	
	@Override
	public void addInformation(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flags) {
	super.addInformation(stack, world, tooltip, flags);
	tooltip.add(new TranslationTextComponent("tooltip.darkrpg.belt").mergeStyle(TextFormatting.GRAY));
	}
	
	@Override
    public void render(String identifier, int index, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int light, LivingEntity livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, ItemStack stack) {
	BeltModel model = new BeltModel();
	ICurio.RenderHelper.translateIfSneaking(matrixStack, livingEntity);
    ICurio.RenderHelper.rotateIfSneaking(matrixStack, livingEntity);
    IVertexBuilder vertexBuilder = ItemRenderer.getBuffer(renderTypeBuffer, model.getRenderType(BELT_TEXTURE), false, stack.hasEffect());
    model.render(matrixStack, vertexBuilder, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
	}

    @Override
    public boolean canRender(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
    return true;
    }
}