package com.idark.valoria.registries.item.types.curio.hands;

import com.idark.valoria.core.interfaces.*;
import com.idark.valoria.registries.item.types.builders.*;
import com.idark.valoria.registries.item.types.curio.*;
import net.minecraft.client.player.*;
import net.minecraft.client.resources.*;
import net.minecraft.core.registries.*;
import net.minecraft.resources.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;

public class GlovesItem extends CurioAccessoryItem implements ICurioTexture{
    public GlovesItem(GlovesBuilder builder){
        super(builder);
    }

    private ResourceLocation cachedDefaultTexture = null;
    private ResourceLocation cachedSlimTexture = null;

    @Override
    public ResourceLocation getTexture(ItemStack stack, LivingEntity entity){
        if(builder.texPath == null) return null;

        if (cachedDefaultTexture == null) {
            String basePath = builder.texPath.getPath();
            if(builder.dependsOnStack){
                basePath += BuiltInRegistries.ITEM.getKey(stack.getItem()).getPath();
            }else{
                basePath += builder.texPath.getPath();
            }

            cachedDefaultTexture = ResourceLocation.fromNamespaceAndPath(builder.texPath.getNamespace(), basePath + ".png");
            cachedSlimTexture = ResourceLocation.fromNamespaceAndPath(builder.texPath.getNamespace(), basePath + "_slim.png");
        }

        boolean slim = entity instanceof AbstractClientPlayer player && player.getSkin().model() == PlayerSkin.Model.SLIM;
        return slim ? cachedSlimTexture : cachedDefaultTexture;
    }

    public static class GlovesBuilder extends AbstractCurioBuilder<GlovesItem, GlovesBuilder>{

        public GlovesBuilder(Tier tier, Properties properties){
            super(tier, properties);
        }

        @Override
        public GlovesItem build(){
            return new GlovesItem(this);
        }
    }
}
