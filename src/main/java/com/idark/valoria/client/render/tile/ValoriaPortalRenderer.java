package com.idark.valoria.client.render.tile;


import com.idark.valoria.Valoria;
import com.idark.valoria.client.shaders.ShaderRegistry;
import com.idark.valoria.registries.block.entity.ValoriaPortalBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Matrix4f;

import static mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes.getDelayedRender;

@OnlyIn(Dist.CLIENT)
public class ValoriaPortalRenderer<T extends ValoriaPortalBlockEntity> implements BlockEntityRenderer<T>{
    public static final ResourceLocation BACKGROUND_LOC = new ResourceLocation(Valoria.ID, "textures/environment/valoria_portal.png");
    public static final ResourceLocation LAYER_LOC = new ResourceLocation(Valoria.ID, "textures/environment/valoria_portal_layer2.png");

    public ValoriaPortalRenderer(BlockEntityRendererProvider.Context pContext){
    }

    public void render(T pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay){
        Matrix4f matrix4f = pPoseStack.last().pose();
        this.renderCube(pBlockEntity, matrix4f, getDelayedRender().getBuffer(renderType()));
    }

    private void renderCube(T pBlockEntity, Matrix4f pPose, VertexConsumer pConsumer){
        float f = this.getOffsetDown();
        float f1 = this.getOffsetUp();
        this.renderFace(pBlockEntity, pPose, pConsumer, 0.0F, 1.0F, f, f, 0.0F, 0.0F, 1.0F, 1.0F, Direction.DOWN);
        this.renderFace(pBlockEntity, pPose, pConsumer, 0.0F, 1.0F, f1, f1, 1.0F, 1.0F, 0.0F, 0.0F, Direction.UP);
    }

    private void renderFace(T pBlockEntity, Matrix4f pPose, VertexConsumer pConsumer, float pX0, float pX1, float pY0, float pY1, float pZ0, float pZ1, float pZ2, float pZ3, Direction pDirection){
        if(pBlockEntity.shouldRenderFace(pDirection)){
            pConsumer.vertex(pPose, pX0, pY0, pZ0).endVertex();
            pConsumer.vertex(pPose, pX1, pY0, pZ1).endVertex();
            pConsumer.vertex(pPose, pX1, pY1, pZ2).endVertex();
            pConsumer.vertex(pPose, pX0, pY1, pZ3).endVertex();
        }
    }

    protected float getOffsetUp(){
        return 0.55F;
    }

    protected float getOffsetDown(){
        return 0.435F;
    }

    protected RenderType renderType(){
        return ShaderRegistry.valoriaPortal();
    }
}