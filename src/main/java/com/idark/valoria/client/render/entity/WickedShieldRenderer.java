package com.idark.valoria.client.render.entity;

import com.idark.valoria.*;
import com.idark.valoria.client.model.entity.*;
import com.idark.valoria.registries.entity.living.minions.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.resources.*;
import net.minecraftforge.api.distmarker.*;

@OnlyIn(Dist.CLIENT)
public class WickedShieldRenderer extends MobRenderer<WickedShield, WickedShieldModel<WickedShield>>{
    protected static final ResourceLocation TEXTURE = new ResourceLocation(Valoria.ID, "textures/item/wicked_shield.png");

    public WickedShieldRenderer(EntityRendererProvider.Context context){
        super(context, new WickedShieldModel<>(WickedShieldModel.createBodyLayer().bakeRoot()), 0.0F);
    }

    @Override
    public ResourceLocation getTextureLocation(WickedShield pEntity){
        return TEXTURE;
    }
}