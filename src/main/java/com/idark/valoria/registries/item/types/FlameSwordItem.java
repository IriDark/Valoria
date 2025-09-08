package com.idark.valoria.registries.item.types;

import com.idark.valoria.client.render.item.*;
import net.minecraft.client.renderer.*;
import net.minecraft.world.item.*;
import net.minecraftforge.client.extensions.common.*;
import software.bernie.geckolib.animatable.*;
import software.bernie.geckolib.core.animatable.instance.*;
import software.bernie.geckolib.core.animation.AnimatableManager.*;
import software.bernie.geckolib.util.*;

import java.util.function.*;

public class FlameSwordItem extends SwordItem implements GeoItem{
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public FlameSwordItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties){
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    @Override
    public boolean isPerspectiveAware(){
        return true;
    }

    @Override
    public void registerControllers(ControllerRegistrar controllers){
//        controllers.add(new AnimationController<>(this, state -> {
//            ItemDisplayContext transformType = state.getData(DataTickets.ITEM_RENDER_PERSPECTIVE);
//            return null;
//        }));
    }

    /**
     * Each instance of a {@code GeoAnimatable} must return an instance of an {@link AnimatableInstanceCache}, which handles instance-specific animation info.
     * Generally speaking, you should create your cache using {@code GeckoLibUtil#createCache} and store it in your animatable instance, returning that cached instance when called.
     * @return A cached instance of an {@code AnimatableInstanceCache}
     */
    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache(){
        return cache;
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private FlameSwordRenderer renderer = null;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer(){
                if (this.renderer == null)
                    this.renderer = new FlameSwordRenderer();

                return renderer;
            }
        });
    }
}
