package com.idark.valoria.registries.item.types.curio.hands;

import com.idark.valoria.registries.item.types.builders.*;
import com.idark.valoria.registries.item.types.curio.*;
import net.minecraft.client.player.*;
import net.minecraft.resources.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import net.minecraftforge.registries.*;

public class GlovesItem extends CurioAccessoryItem implements ICurioTexture{
    public GlovesItem(GlovesBuilder builder){
        super(builder);
    }

    @Override
    public ResourceLocation getTexture(ItemStack stack, LivingEntity entity){
        if(builder.texPath == null) return null;

        boolean flag = entity instanceof AbstractClientPlayer player && !player.getModelName().equals("default");
        if(builder.dependsOnStack){
            return new ResourceLocation(builder.texPath.getNamespace(), builder.texPath.getPath() + ForgeRegistries.ITEMS.getKey(stack.getItem()).getPath() + (flag ? "_slim" : "") + ".png");
        }

        return new ResourceLocation(builder.texPath.getNamespace(), builder.texPath.getPath() +builder.texPath + (flag ? "_slim" : "") + ".png");
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
