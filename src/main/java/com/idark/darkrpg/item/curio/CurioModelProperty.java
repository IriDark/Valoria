package com.idark.darkrpg.item.curio;

import com.idark.darkrpg.DarkRPG;
import com.idark.darkrpg.client.render.curio.model.NecklaceModel;
import com.idark.darkrpg.util.RenderUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class CurioModelProperty implements ICurioRenderer {

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

    public AccessoryType type;
    public AccessoryGem gem;
    public AccessoryMaterial material;

    public CurioModelProperty(AccessoryType type, AccessoryGem gem, AccessoryMaterial material) {
        super();
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
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext,
                                                                          PoseStack matrixStack, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource renderTypeBuffer,
                                                                          int light, float limbSwing, float limbSwingAmount, float partialTicks,
                                                                          float ageInTicks, float netHeadYaw, float headPitch) {

        NecklaceModel model = new NecklaceModel(Minecraft.getInstance().getEntityModels().bakeLayer(NecklaceModel.NECKLACE));
	    //HandsModel hands = new HandsModel();
        //BeltModel belt = new BeltModel();
		VertexConsumer vertexBuilder = ItemRenderer.getFoilBuffer(renderTypeBuffer, model.renderType(EMPTY), false, stack.hasFoil());;
        LivingEntity entity = slotContext.entity();
		switch(material) {
			//case LEATHER:
			//if (type == AccessoryType.GLOVES) {
			//	vertexBuilder = ItemRenderer.getFoilBuffer(renderTypeBuffer, hands.renderType(GLOVES_LEATHER), false, stack.hasFoil());
			//	break;
			//} else if (type == AccessoryType.BELT) {
			//	vertexBuilder = ItemRenderer.getFoilBuffer(renderTypeBuffer, belt.renderType(BELT_TEXTURE), false, stack.hasFoil());
			//	break;
			//}

			case IRON:
			if (type == AccessoryType.NECKLACE) {
				vertexBuilder = ItemRenderer.getFoilBuffer(renderTypeBuffer, model.renderType(IRON), false, stack.hasFoil());
				break;
			//} else if (type == AccessoryType.GLOVES) {
			//	vertexBuilder = ItemRenderer.getFoilBuffer(renderTypeBuffer, hands.renderType(GLOVES_IRON), false, stack.hasFoil());
			//	break;
			}

			case GOLD:
			if (type == AccessoryType.NECKLACE) {
				vertexBuilder = ItemRenderer.getFoilBuffer(renderTypeBuffer, model.renderType(GOLD), false, stack.hasFoil());
				break;
			//} else if (type == AccessoryType.GLOVES) {
			//	vertexBuilder = ItemRenderer.getFoilBuffer(renderTypeBuffer, hands.renderType(GLOVES_GOLD), false, stack.hasFoil());
			//	break;
			}

			//case DIAMOND:
			//	vertexBuilder = ItemRenderer.getFoilBuffer(renderTypeBuffer, hands.renderType(GLOVES_DIAMOND), false, stack.hasFoil());
			//	break;
			case NETHERITE:
			if (type == AccessoryType.NECKLACE) {
				vertexBuilder = ItemRenderer.getFoilBuffer(renderTypeBuffer, model.renderType(NETHERITE), false, stack.hasFoil());
				break;
			//} else if (type == AccessoryType.GLOVES) {
			//	vertexBuilder = ItemRenderer.getFoilBuffer(renderTypeBuffer, hands.renderType(GLOVES_NETHERITE), false, stack.hasFoil());
			//	break;
			}
		}


		switch(type) {
            /*
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
				*/
			case NECKLACE:
                matrixStack.pushPose();
                ICurioRenderer.followBodyRotations(entity, model);
                ICurioRenderer.translateIfSneaking(matrixStack, entity);
                ICurioRenderer.rotateIfSneaking(matrixStack, entity);

                Vec3 rotate = RenderUtils.followBodyRotation(entity);
                model.model.yRot = (float) rotate.y();

                model.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
                model.renderToBuffer(matrixStack, vertexBuilder, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
                matrixStack.popPose();
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
			//case BELT:
			//    belt.prepareMobModel(livingEntity, limbSwing, limbSwingAmount, partialTicks);
			//	ICurio.RenderHelper.followBodyRotations(livingEntity, hands);
			//	belt.setupAnim(livingEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
			//	belt.renderToBuffer(matrixStack, vertexBuilder, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
			//	break;
			//case GLOVES:
			//    hands.prepareMobModel(livingEntity, limbSwing, limbSwingAmount, partialTicks);
			//	ICurio.RenderHelper.followBodyRotations(livingEntity, hands);
			//	hands.setupAnim(livingEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
			//	hands.renderToBuffer(matrixStack, vertexBuilder, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
			//	break;
			//case RING:
			//	break;
			//case CHARM:
			//	break;
			case NECKLACE:
                matrixStack.pushPose();
                ICurioRenderer.followBodyRotations(entity, model);
                ICurioRenderer.translateIfSneaking(matrixStack, entity);
                ICurioRenderer.rotateIfSneaking(matrixStack, entity);

                Vec3 rotate = RenderUtils.followBodyRotation(entity);
                model.model.yRot = (float) rotate.y();

                model.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
                model.renderToBuffer(matrixStack, vertexBuilder, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
                matrixStack.popPose();
				break;
		}
	}
}
