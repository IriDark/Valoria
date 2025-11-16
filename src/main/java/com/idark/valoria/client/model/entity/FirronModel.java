package com.idark.valoria.client.model.entity;

import com.idark.valoria.*;
import com.idark.valoria.registries.entity.living.boss.firron.*;
import net.minecraft.resources.*;
import net.minecraft.util.*;
import software.bernie.geckolib.constant.*;
import software.bernie.geckolib.core.animatable.model.*;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.model.*;
import software.bernie.geckolib.model.data.*;

public class FirronModel extends GeoModel<Firron>{

    @Override
    public ResourceLocation getModelResource(Firron animatable){
        return Valoria.loc("geo/entity/firron.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Firron animatable){
        return Valoria.loc("textures/entity/firron.png");
    }

    @Override
    public ResourceLocation getAnimationResource(Firron animatable){
        return Valoria.loc("animations/entity/firron.animation.json");
    }

    @Override
    public void setCustomAnimations(Firron animatable, long instanceId, AnimationState<Firron> animationState){
        CoreGeoBone head = getAnimationProcessor().getBone("head");
        if(head != null){
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
        }

        CoreGeoBone portal = getAnimationProcessor().getBone("portal");
        if(portal != null){
            portal.setHidden(animatable.tickCount > 140);
        }
    }
}