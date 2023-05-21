package com.idark.darkrpg.item.curio.levels;

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
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.potion.*;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.world.World;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.SlotTypePreset;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.common.ForgeMod;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.UUID;
import java.util.Objects;
import java.util.List;

public class CurioIronAmber extends Item implements ICurioItem {
	private static final ResourceLocation TEXTURE = new ResourceLocation(DarkRPG.MOD_ID, "textures/entity/iron_necklace_amber.png");
	private final boolean ring;	
	public CurioIronAmber(boolean isRing, Item.Properties properties) {
        super(properties);
		this.ring = isRing;	
	}
	
	@Nonnull
	@Override
	public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
    return new ICurio.SoundInfo(SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 1.0f, 1.0f);
	}

    @Override
    public boolean canRightClickEquip(ItemStack stack) {
        return true;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> atts = LinkedHashMultimap.create();
        atts.put(Attributes.ARMOR, new AttributeModifier(uuid, "bonus", 1, AttributeModifier.Operation.ADDITION));
		return atts;
		}
		
    @Override
    public void curioTick(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        PlayerEntity player = (PlayerEntity) livingEntity;

        if(!player.world.isRemote()) {
            boolean hasPlayerFireResistance =
                    !Objects.equals(player.getActivePotionEffect(Effects.HASTE), null);

            if(!hasPlayerFireResistance) {
                player.addPotionEffect(new EffectInstance(Effects.HASTE, 200));

                if(random.nextFloat() > 0.6f) {
                    stack.damageItem(1, player, p -> CuriosApi.getCuriosHelper().onBrokenCurio(
                            SlotTypePreset.NECKLACE.getIdentifier(), index, p));
                }
            }
        }

        ICurioItem.super.curioTick(identifier, index, livingEntity, stack);
    }
	
	@Override
    public void render(String identifier, int index, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int light, LivingEntity livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, ItemStack stack) {
		if(ring == false) {
			NecklaceModel model = new NecklaceModel();
			ICurio.RenderHelper.translateIfSneaking(matrixStack, livingEntity);
			ICurio.RenderHelper.rotateIfSneaking(matrixStack, livingEntity);
			IVertexBuilder vertexBuilder = ItemRenderer.getBuffer(renderTypeBuffer, model.getRenderType(TEXTURE), false, stack.hasEffect());
			model.render(matrixStack, vertexBuilder, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
		} else {
			return;
		}
	}

    @Override
    public boolean canRender(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        return true;
    }	
	
	@Override
	public void addInformation(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flags) {
	super.addInformation(stack, world, tooltip, flags);
	tooltip.add(new TranslationTextComponent("tooltip.darkrpg.amber").mergeStyle(TextFormatting.GRAY));
	}
}