package com.idark.valoria.registries.item.armor.item;

import com.idark.valoria.client.render.armor.*;
import net.minecraft.client.model.*;
import net.minecraft.core.*;
import net.minecraft.resources.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.client.extensions.common.*;
import org.jetbrains.annotations.*;
import pro.komaru.tridot.common.registry.item.armor.*;
import software.bernie.geckolib.animatable.*;
import software.bernie.geckolib.animatable.instance.*;
import software.bernie.geckolib.animation.AnimatableManager.*;
import software.bernie.geckolib.renderer.*;
import software.bernie.geckolib.util.*;

import java.util.function.*;

public class FallenCollectorArmorItem extends PercentageArmorItem implements GeoItem{
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public FallenCollectorArmorItem(Holder<ArmorMaterial> material, Type type, Properties properties){
        super(material, type, properties);
    }

    // prevents log spam
    public ResourceLocation getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, ArmorMaterial.Layer layer, boolean innerModel){
        return ResourceLocation.withDefaultNamespace("textures/models/armor/diamond_layer_1.png"); // PORT NOTE: getArmorTexture returns a ResourceLocation and receives the material layer in 1.21
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private GeoArmorRenderer<?> renderer;

            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                if (this.renderer == null) this.renderer = new FallenCollectorArmorRenderer();
                this.renderer.prepForRender(livingEntity, itemStack, equipmentSlot, original);
                return this.renderer;
            }
        });
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache(){
        return cache;
    }

    @Override
    public void registerControllers(ControllerRegistrar controllers) {
    }
}