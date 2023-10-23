package com.idark.darkrpg.item.curio;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.idark.darkrpg.DarkRPG;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class CurioItemProperty extends Item implements ICurioItem {

	/**
	 * 	Textures
 	 */
	private static final ResourceLocation AMBER = new ResourceLocation(DarkRPG.MOD_ID, "textures/entity/necklace/amber.png");
	private static final ResourceLocation DIAMOND = new ResourceLocation(DarkRPG.MOD_ID, "textures/entity/necklace/diamond.png");
	private static final ResourceLocation EMERALD = new ResourceLocation(DarkRPG.MOD_ID, "textures/entity/necklace/emerald.png");
	private static final ResourceLocation RUBY = new ResourceLocation(DarkRPG.MOD_ID, "textures/entity/necklace/ruby.png");
	private static final ResourceLocation SAPPHIRE = new ResourceLocation(DarkRPG.MOD_ID, "textures/entity/necklace/sapphire.png");
	private static final ResourceLocation ARMOR = new ResourceLocation(DarkRPG.MOD_ID, "textures/entity/necklace/armor.png");
	private static final ResourceLocation HEALTH = new ResourceLocation(DarkRPG.MOD_ID, "textures/entity/necklace/health.png");
	private static final ResourceLocation WEALTH = new ResourceLocation(DarkRPG.MOD_ID, "textures/entity/necklace/wealth.png");
	private static final ResourceLocation IRON = new ResourceLocation(DarkRPG.MOD_ID, "textures/entity/necklace/iron_necklace.png");
	private static final ResourceLocation GOLD = new ResourceLocation(DarkRPG.MOD_ID, "textures/entity/necklace/golden_necklace.png");
	private static final ResourceLocation NETHERITE = new ResourceLocation(DarkRPG.MOD_ID, "textures/entity/necklace/netherite_necklace.png");
	private static final ResourceLocation EMPTY = new ResourceLocation(DarkRPG.MOD_ID, "textures/entity/necklace/empty.png");

	private static final ResourceLocation GLOVES_LEATHER = new ResourceLocation(DarkRPG.MOD_ID, "textures/entity/leather_gloves.png");
	private static final ResourceLocation GLOVES_IRON = new ResourceLocation(DarkRPG.MOD_ID, "textures/entity/iron_gloves.png");
	private static final ResourceLocation GLOVES_GOLD = new ResourceLocation(DarkRPG.MOD_ID, "textures/entity/golden_gloves.png");
    private static final ResourceLocation GLOVES_DIAMOND = new ResourceLocation(DarkRPG.MOD_ID, "textures/entity/diamond_gloves.png");
	private static final ResourceLocation GLOVES_NETHERITE = new ResourceLocation(DarkRPG.MOD_ID, "textures/entity/netherite_gloves.png");
	
	private static final ResourceLocation BELT_TEXTURE = new ResourceLocation(DarkRPG.MOD_ID, "textures/entity/leather_belt.png");

	/**
	* Enums
	*/
	public AccessoryType type;
	public AccessoryGem gem;
	public AccessoryMaterial material;

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

	@Nonnull
	@Override
	public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
    return new ICurio.SoundInfo(SoundEvents.ARMOR_EQUIP_GOLD, 1.0f, 1.0f);
	}

    @Override
    public boolean canRightClickEquip(ItemStack stack) {
        return true;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
		Multimap<Attribute, AttributeModifier> atts = LinkedHashMultimap.create();
		// Reciving Gem Type & gives atts
		switch(gem) {
			case NONE:
				atts.put(Attributes.ARMOR, new AttributeModifier(uuid, "bonus", 0.5, AttributeModifier.Operation.ADDITION));
				break;
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
				atts.put(ForgeMod.SWIM_SPEED.get(), new AttributeModifier(uuid, "bonus", 0.1, AttributeModifier.Operation.ADDITION ));
				break;
			
			case HEALTH:
				if (material == AccessoryMaterial.IRON) {
					atts.put(Attributes.MAX_HEALTH, new AttributeModifier(uuid, "bonus", 1, AttributeModifier.Operation.ADDITION));
					break;
				} else {
					atts.put(Attributes.MAX_HEALTH, new AttributeModifier(uuid, "bonus", 2.5, AttributeModifier.Operation.ADDITION));
					break;
				}
			case ARMOR:
				if (material == AccessoryMaterial.IRON) {
					atts.put(Attributes.ARMOR, new AttributeModifier(uuid, "bonus", 3, AttributeModifier.Operation.ADDITION));
					break;
				} else {
					atts.put(Attributes.ARMOR, new AttributeModifier(uuid, "bonus", 6, AttributeModifier.Operation.ADDITION));
					atts.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(uuid, "bonus", 0.5, AttributeModifier.Operation.ADDITION));
					break;
				}
				
			case TOUGH:
				atts.put(Attributes.ARMOR, new AttributeModifier(uuid, "bonus", 1.5, AttributeModifier.Operation.ADDITION));
				atts.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(uuid, "bonus", 1, AttributeModifier.Operation.ADDITION));
				break;
			case TANK:
				atts.put(Attributes.ARMOR, new AttributeModifier(uuid, "bonus", 5, AttributeModifier.Operation.ADDITION));
				atts.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(uuid, "bonus", 1.5, AttributeModifier.Operation.ADDITION));
				break;
			case WEALTH:
				if (material == AccessoryMaterial.IRON) {
					atts.put(Attributes.LUCK, new AttributeModifier(uuid, "bonus", 1.5, AttributeModifier.Operation.ADDITION));
					break;
				} else {
					atts.put(Attributes.LUCK, new AttributeModifier(uuid, "bonus", 3, AttributeModifier.Operation.ADDITION));
					break;
				}
				
			case BELT:
			    atts.put(Attributes.ARMOR, new AttributeModifier(uuid, "bonus", 0.5, AttributeModifier.Operation.ADDITION));
				CuriosApi.getCuriosHelper().addSlotModifier(atts, "charm", uuid, 2.0, AttributeModifier.Operation.ADDITION);
				break;
		}
			
		return atts;
	}
		
    @Override
    public void curioTick(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        Player player = (Player) livingEntity;
		/*
 		 Receiving gem type and then giving player effect`s
 		*/
		if(gem == AccessoryGem.AMBER) {
			if(!player.level().isClientSide()) {
				boolean hasPlayerEffect = !Objects.equals(player.getEffect(MobEffects.DIG_SPEED), null);
            if(!hasPlayerEffect) {
                player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 200));
				}
            }
		}
		
        ICurioItem.super.curioTick(identifier, index, livingEntity, stack);
    }
	
	/*@Override
    public boolean canRender(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        return true;
    }

	@Override
    public void render(String identifier, int index, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int light, LivingEntity livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, ItemStack stack) {
		NecklaceModel model = new NecklaceModel();
	    HandsModel hands = new HandsModel();
        BeltModel belt = new BeltModel();
		IVertexBuilder vertexBuilder = ItemRenderer.getFoilBuffer(renderTypeBuffer, model.renderType(EMPTY), false, stack.hasFoil());;

		/*
		  	Receiving model properties
 		 */ /*
		switch(material) {
			case LEATHER:
			if (type == AccessoryType.GLOVES) {
				vertexBuilder = ItemRenderer.getFoilBuffer(renderTypeBuffer, hands.renderType(GLOVES_LEATHER), false, stack.hasFoil());
				break;
			} else if (type == AccessoryType.BELT) {
				vertexBuilder = ItemRenderer.getFoilBuffer(renderTypeBuffer, belt.renderType(BELT_TEXTURE), false, stack.hasFoil());
				break;
			}
			
			case IRON:
			if (type == AccessoryType.NECKLACE) {
				vertexBuilder = ItemRenderer.getFoilBuffer(renderTypeBuffer, model.renderType(IRON), false, stack.hasFoil());
				break;
			} else if (type == AccessoryType.GLOVES) {
				vertexBuilder = ItemRenderer.getFoilBuffer(renderTypeBuffer, hands.renderType(GLOVES_IRON), false, stack.hasFoil());
				break;
			}
			
			case GOLD:
			if (type == AccessoryType.NECKLACE) {
				vertexBuilder = ItemRenderer.getFoilBuffer(renderTypeBuffer, model.renderType(GOLD), false, stack.hasFoil());
				break;
			} else if (type == AccessoryType.GLOVES) {
				vertexBuilder = ItemRenderer.getFoilBuffer(renderTypeBuffer, hands.renderType(GLOVES_GOLD), false, stack.hasFoil());
				break;
			}
			
			case DIAMOND:
				vertexBuilder = ItemRenderer.getFoilBuffer(renderTypeBuffer, hands.renderType(GLOVES_DIAMOND), false, stack.hasFoil());			
				break;			
			case NETHERITE:
			if (type == AccessoryType.NECKLACE) {
				vertexBuilder = ItemRenderer.getFoilBuffer(renderTypeBuffer, model.renderType(NETHERITE), false, stack.hasFoil());
				break;
			} else if (type == AccessoryType.GLOVES) {
				vertexBuilder = ItemRenderer.getFoilBuffer(renderTypeBuffer, hands.renderType(GLOVES_NETHERITE), false, stack.hasFoil());			
				break;
			}
		}

		switch(type) {
			case BELT:
			    belt.prepareMobModel(livingEntity, limbSwing, limbSwingAmount, partialTicks);
				ICurio.RenderHelper.followBodyRotations(livingEntity, belt);
				belt.setupAnim(livingEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
				belt.renderToBuffer(matrixStack, vertexBuilder, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
				break;
			case GLOVES:
			    hands.prepareMobModel(livingEntity, limbSwing, limbSwingAmount, partialTicks);
				ICurio.RenderHelper.followBodyRotations(livingEntity, hands);
				hands.setupAnim(livingEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
				hands.renderToBuffer(matrixStack, vertexBuilder, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);			
				break;
			case RING:
				break;
			case CHARM:
				break;
			case NECKLACE:
			    model.prepareMobModel(livingEntity, limbSwing, limbSwingAmount, partialTicks);
				ICurio.RenderHelper.followBodyRotations(livingEntity, model);
				model.setupAnim(livingEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
				model.renderToBuffer(matrixStack, vertexBuilder, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
				break;
		}
	
	if (type == AccessoryType.NECKLACE) {
		switch(gem) {
			case NONE:
				return;
			case AMBER:
				vertexBuilder = ItemRenderer.getFoilBuffer(renderTypeBuffer, model.renderType(AMBER), false, stack.hasFoil());
				break;
			case DIAMOND:
				vertexBuilder = ItemRenderer.getFoilBuffer(renderTypeBuffer, model.renderType(DIAMOND), false, stack.hasFoil());
				break;
			case EMERALD:
				vertexBuilder = ItemRenderer.getFoilBuffer(renderTypeBuffer, model.renderType(EMERALD), false, stack.hasFoil());
				break;
			case RUBY:
				vertexBuilder = ItemRenderer.getFoilBuffer(renderTypeBuffer, model.renderType(RUBY), false, stack.hasFoil());
				break;
			case SAPPHIRE:
				vertexBuilder = ItemRenderer.getFoilBuffer(renderTypeBuffer, model.renderType(SAPPHIRE), false, stack.hasFoil());
				break;

			case ARMOR:
				vertexBuilder = ItemRenderer.getFoilBuffer(renderTypeBuffer, model.renderType(ARMOR), false, stack.hasFoil());
				break;
			case HEALTH:
				vertexBuilder = ItemRenderer.getFoilBuffer(renderTypeBuffer, model.renderType(HEALTH), false, stack.hasFoil());
				break;
			case WEALTH:
				vertexBuilder = ItemRenderer.getFoilBuffer(renderTypeBuffer, model.renderType(WEALTH), false, stack.hasFoil());
				break;
			}
		}

		switch(type) {
			case BELT:
			    belt.prepareMobModel(livingEntity, limbSwing, limbSwingAmount, partialTicks);
				ICurio.RenderHelper.followBodyRotations(livingEntity, hands);
				belt.setupAnim(livingEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
				belt.renderToBuffer(matrixStack, vertexBuilder, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
				break;
			case GLOVES:
			    hands.prepareMobModel(livingEntity, limbSwing, limbSwingAmount, partialTicks);
				ICurio.RenderHelper.followBodyRotations(livingEntity, hands);
				hands.setupAnim(livingEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
				hands.renderToBuffer(matrixStack, vertexBuilder, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);			
				break;
			case RING:
				break;
			case CHARM:
				break;
			case NECKLACE:
			    model.prepareMobModel(livingEntity, limbSwing, limbSwingAmount, partialTicks);
				ICurio.RenderHelper.followBodyRotations(livingEntity, hands);
				model.setupAnim(livingEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
				model.renderToBuffer(matrixStack, vertexBuilder, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
				break;
		}
	}*/
	
	@Override
	public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags) {
		super.appendHoverText(stack, world, tooltip, flags);	
		if (gem == AccessoryGem.AMBER) {	
			tooltip.add(Component.translatable("tooltip.darkrpg.amber").withStyle(ChatFormatting.GRAY));
		} else if (material == AccessoryMaterial.GOLD) {
			tooltip.add(Component.translatable("tooltip.darkrpg.golden").withStyle(ChatFormatting.GRAY));
		} else if (type == AccessoryType.BELT) {
			tooltip.add(Component.translatable("tooltip.darkrpg.belt").withStyle(ChatFormatting.GRAY));
		}
		
		tooltip.add(Component.translatable("tooltip.darkrpg.rmb_equip").withStyle(ChatFormatting.GREEN));
	}
}