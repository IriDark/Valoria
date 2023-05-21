package com.idark.darkrpg.item.curio;

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
import top.theillusivec4.curios.api.*;
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

public class CurioItemProperty extends RPGCurioItem implements ICurioItem {
	private static final ResourceLocation AMBER = new ResourceLocation(DarkRPG.MOD_ID, "textures/entity/necklace/amber.png");
	private static final ResourceLocation DIAMOND = new ResourceLocation(DarkRPG.MOD_ID, "textures/entity/necklace/diamond.png");
	private static final ResourceLocation EMERALD = new ResourceLocation(DarkRPG.MOD_ID, "textures/entity/necklace/emerald.png");
	private static final ResourceLocation RUBY = new ResourceLocation(DarkRPG.MOD_ID, "textures/entity/necklace/ruby.png");
	private static final ResourceLocation SAPPHIRE = new ResourceLocation(DarkRPG.MOD_ID, "textures/entity/necklace/sapphire.png");
	private static final ResourceLocation ARMOR = new ResourceLocation(DarkRPG.MOD_ID, "textures/entity/necklace/shield.png");
	private static final ResourceLocation HEALTH = new ResourceLocation(DarkRPG.MOD_ID, "textures/entity/necklace/health.png");
	private static final ResourceLocation WEALTH = new ResourceLocation(DarkRPG.MOD_ID, "textures/entity/necklace/wealth.png");
	private static final ResourceLocation IRON = new ResourceLocation(DarkRPG.MOD_ID, "textures/entity/necklace/iron_necklace.png");
	private static final ResourceLocation GOLD = new ResourceLocation(DarkRPG.MOD_ID, "textures/entity/necklace/golden_necklace.png");
	private static final ResourceLocation NETHERITE = new ResourceLocation(DarkRPG.MOD_ID, "textures/entity/necklace/netherite_necklace.png");
	private static final ResourceLocation YADAYN = new ResourceLocation(DarkRPG.MOD_ID, "textures/entity/necklace/empty.png");

	public static AccessoryType type;
	public static AccessoryGem gem;
	public static AccessoryMaterial material;

	public CurioItemProperty(AccessoryType type, AccessoryGem gem, AccessoryMaterial material, Item.Properties properties) {
        super(properties);
		this.type = type;
		this.gem = gem;
		this.material = material;
	}

	public AccessoryType getAccessoryType() {
		return this.type;
	}
	
	public AccessoryGem getAccessoryGem() {
		return this.gem;
	}
	
	public AccessoryMaterial getAccessoryMaterial() {
		return this.material;
	}

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
		Multimap<Attribute, AttributeModifier> atts = LinkedHashMultimap.create();
		switch(gem) {
			case AMBER:
				atts.put(Attributes.ARMOR, new AttributeModifier(uuid, "bonus", 1, AttributeModifier.Operation.ADDITION));
				break;
			case DIAMOND:
				atts.put(Attributes.ARMOR, new AttributeModifier(uuid, "bonus", 4, AttributeModifier.Operation.ADDITION));
				break;
			case EMERALD:
				atts.put(Attributes.LUCK, new AttributeModifier(uuid, "bonus", 1, AttributeModifier.Operation.ADDITION));
				break;
			case RUBY:
				atts.put(Attributes.MAX_HEALTH, new AttributeModifier(uuid, "bonus", 1, AttributeModifier.Operation.ADDITION));
				break;
			case SAPPHIRE:
				atts.put(ForgeMod.SWIM_SPEED.get(), new AttributeModifier(uuid, "bonus",0.1, AttributeModifier.Operation.ADDITION ));
				break;
			
			case HEALTH:
				atts.put(Attributes.MAX_HEALTH, new AttributeModifier(uuid, "bonus", 2.5, AttributeModifier.Operation.ADDITION));
				break;
			case ARMOR:
				atts.put(Attributes.ARMOR, new AttributeModifier(uuid, "bonus", 2.5, AttributeModifier.Operation.ADDITION));
				break;
			case WEALTH:
				atts.put(Attributes.LUCK, new AttributeModifier(uuid, "bonus", 3, AttributeModifier.Operation.ADDITION));
				break;
		}
			
		return atts;
	}
		
    @Override
    public void curioTick(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
		if(gem == AccessoryGem.AMBER) {
        PlayerEntity player = (PlayerEntity) livingEntity;

        if(!player.world.isRemote()) {
            boolean hasPlayerEffect = !Objects.equals(player.getActivePotionEffect(Effects.HASTE), null);

            if(!hasPlayerEffect) {
                player.addPotionEffect(new EffectInstance(Effects.HASTE, 200));
				}
            }
        }

        super.curioTick(identifier, index, livingEntity, stack);
    }
	
	@Override
    public boolean canRender(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        return true;
    }

	@Override
    public void render(String identifier, int index, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int light, LivingEntity livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, ItemStack stack) {
		NecklaceModel model = new NecklaceModel();
		IVertexBuilder vertexBuilder= ItemRenderer.getBuffer(renderTypeBuffer, model.getRenderType(YADAYN), false, stack.hasEffect());;

		switch(material) {
			case IRON:
				vertexBuilder = ItemRenderer.getBuffer(renderTypeBuffer, model.getRenderType(IRON), false, stack.hasEffect());
				break;
			case GOLD:
				vertexBuilder = ItemRenderer.getBuffer(renderTypeBuffer, model.getRenderType(GOLD), false, stack.hasEffect());
				break;
			case NETHERITE:
				vertexBuilder = ItemRenderer.getBuffer(renderTypeBuffer, model.getRenderType(NETHERITE), false, stack.hasEffect());
				break;
		}

		switch(type) {
			case RING:
				break;
			case CHARM:
				break;
			case NECKLACE:
				ICurio.RenderHelper.translateIfSneaking(matrixStack, livingEntity);
				ICurio.RenderHelper.rotateIfSneaking(matrixStack, livingEntity);
				model.render(matrixStack, vertexBuilder, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
				break;
		}

		switch(gem) {
			case AMBER:
				vertexBuilder = ItemRenderer.getBuffer(renderTypeBuffer, model.getRenderType(AMBER), false, stack.hasEffect());
				break;
			case DIAMOND:
				vertexBuilder = ItemRenderer.getBuffer(renderTypeBuffer, model.getRenderType(DIAMOND), false, stack.hasEffect());
				break;
			case EMERALD:
				vertexBuilder = ItemRenderer.getBuffer(renderTypeBuffer, model.getRenderType(EMERALD), false, stack.hasEffect());
				break;
			case RUBY:
				vertexBuilder = ItemRenderer.getBuffer(renderTypeBuffer, model.getRenderType(RUBY), false, stack.hasEffect());
				break;
			case SAPPHIRE:
				vertexBuilder = ItemRenderer.getBuffer(renderTypeBuffer, model.getRenderType(SAPPHIRE), false, stack.hasEffect());
				break;

			case ARMOR:
				vertexBuilder = ItemRenderer.getBuffer(renderTypeBuffer, model.getRenderType(ARMOR), false, stack.hasEffect());
				break;
			case HEALTH:
				vertexBuilder = ItemRenderer.getBuffer(renderTypeBuffer, model.getRenderType(HEALTH), false, stack.hasEffect());
				break;
			case WEALTH:
				vertexBuilder = ItemRenderer.getBuffer(renderTypeBuffer, model.getRenderType(WEALTH), false, stack.hasEffect());
				break;
		}

		switch(type) {
			case RING:
				break;
			case CHARM:
				break;
			case NECKLACE:
				ICurio.RenderHelper.translateIfSneaking(matrixStack, livingEntity);
				ICurio.RenderHelper.rotateIfSneaking(matrixStack, livingEntity);
				model.render(matrixStack, vertexBuilder, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
				model.render(matrixStack, vertexBuilder, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
				break;
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flags) {
		super.addInformation(stack, world, tooltip, flags);	
		if (gem == AccessoryGem.AMBER) {	
			tooltip.add(new TranslationTextComponent("tooltip.darkrpg.amber").mergeStyle(TextFormatting.GRAY));
		} else if (material == AccessoryMaterial.GOLD) {
			tooltip.add(new TranslationTextComponent("tooltip.darkrpg.golden").mergeStyle(TextFormatting.GRAY));
		}
		
		tooltip.add(new TranslationTextComponent("tooltip.darkrpg.rmb").mergeStyle(TextFormatting.GREEN));
	}
}
