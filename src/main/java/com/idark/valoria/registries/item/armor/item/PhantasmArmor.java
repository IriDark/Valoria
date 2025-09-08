package com.idark.valoria.registries.item.armor.item;

import com.idark.valoria.client.render.armor.*;
import net.minecraft.client.model.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import net.minecraftforge.client.extensions.common.*;
import org.jetbrains.annotations.*;
import pro.komaru.tridot.client.model.armor.*;
import pro.komaru.tridot.common.registry.item.armor.*;
import software.bernie.geckolib.animatable.*;
import software.bernie.geckolib.core.animatable.instance.*;
import software.bernie.geckolib.core.animation.AnimatableManager.*;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.renderer.*;
import software.bernie.geckolib.util.*;

import java.util.function.*;

public class PhantasmArmor extends EffectArmorItem implements GeoItem{
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public PhantasmArmor(ArmorMaterial material, Type type, Properties settings){
        super(material, type, settings);
    }

    // prevents log spam
    public @Nullable String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type){
        return "minecraft:textures/models/armor/diamond_layer_1.png";
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private GeoArmorRenderer<?> renderer;

            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                ArmorModel model = getArmorModel(livingEntity, itemStack, equipmentSlot, original);
                if(model != null) return model;

                if (this.renderer == null) this.renderer = new PhantasmArmorRenderer();
                this.renderer.prepForRender(livingEntity, itemStack, equipmentSlot, original);
                return this.renderer;
            }
        });
    }

    /**
     * Register your {@link AnimationController AnimationControllers} and their respective animations and conditions.
     * Override this method in your animatable object and add your controllers via {@link ControllerRegistrar#add ControllerRegistrar.add}.
     * You may add as many controllers as wanted.
     * <br><br>
     * Each controller can only play <u>one</u> animation at a time, and so animations that you intend to play concurrently should be handled in independent controllers.
     * Note having multiple animations playing via multiple controllers can override parts of one animation with another if both animations use the same bones or child bones.
     * @param controllers The object to register your controller instances to
     */
    @Override
    public void registerControllers(ControllerRegistrar controllers){

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
}
