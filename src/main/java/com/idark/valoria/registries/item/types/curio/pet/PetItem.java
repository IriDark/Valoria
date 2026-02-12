package com.idark.valoria.registries.item.types.curio.pet;

import com.idark.valoria.client.render.curio.*;
import com.idark.valoria.registries.item.types.curio.*;
import net.minecraft.client.renderer.*;
import net.minecraft.resources.*;
import net.minecraftforge.client.extensions.common.*;
import software.bernie.geckolib.animatable.*;
import software.bernie.geckolib.core.animatable.instance.*;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.util.*;

import java.util.function.*;

public abstract class PetItem extends ValoriaCurioItem implements GeoItem{
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public PetItem(Properties properties) {
        super(properties);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 0, state -> {
            return state.setAndContinue(RawAnimation.begin().thenLoop("idle"));
        }));
    }

    public abstract ResourceLocation modelPath();

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private PetRenderer renderer;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (this.renderer == null)
                    this.renderer = new PetRenderer(PetItem.this);
                return this.renderer;
            }
        });
    }
}