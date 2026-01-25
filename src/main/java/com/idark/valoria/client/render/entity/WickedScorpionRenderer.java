package com.idark.valoria.client.render.entity;

import com.idark.valoria.*;
import com.idark.valoria.client.model.entity.*;
import com.idark.valoria.registries.entity.living.elemental.*;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.resources.*;
import pro.komaru.tridot.client.model.render.entity.*;

public class WickedScorpionRenderer extends MobRenderer<WickedScorpion, WickedScorpionModel<WickedScorpion>>{
    protected static final ResourceLocation TEXTURE = new ResourceLocation(Valoria.ID, "textures/entity/wicked_scorpion.png");

    public WickedScorpionRenderer(EntityRendererProvider.Context context){
        super(context, new WickedScorpionModel<>(WickedScorpionModel.createBodyLayer().bakeRoot()), 0.75F);
        this.addLayer(new LuminescentLayer.Builder<>(this)
                .setTexture(new ResourceLocation(Valoria.ID, "textures/entity/wicked_scorpion_eyes.png"))
                .build());
    }

    @Override
    public void render(WickedScorpion pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight){
        if(pEntity.isBaby()){
            pMatrixStack.scale(0.7f, 0.7f, 0.7f);
        }

        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(WickedScorpion pEntity){
        return TEXTURE;
    }
}