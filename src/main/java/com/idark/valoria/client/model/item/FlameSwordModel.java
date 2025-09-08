package com.idark.valoria.client.model.item;

import com.idark.valoria.*;
import com.idark.valoria.registries.item.types.*;
import net.minecraft.resources.*;
import software.bernie.geckolib.loading.object.*;
import software.bernie.geckolib.model.*;

public class FlameSwordModel extends GeoModel<FlameSwordItem>{
    public FlameSwordModel() {
    }

//    public ResourceLocation getAnimationResource(FlameSwordItem animatable) {
//        return new ResourceLocation("valoria", "animations/flame_sword.animation.json");
//    }

    public ResourceLocation getModelResource(FlameSwordItem animatable) {
        return Valoria.loc("geo/flame_sword.geo.json");
    }

    public ResourceLocation getTextureResource(FlameSwordItem animatable) {
        return Valoria.loc("textures/item/flame_sword.png");
    }

    /**
     * Returns the resourcepath for the {@link BakedAnimations} (animation json file) to use for animations based on the provided animatable
     */
    @Override
    public ResourceLocation getAnimationResource(FlameSwordItem animatable){
        return null;
    }
}
