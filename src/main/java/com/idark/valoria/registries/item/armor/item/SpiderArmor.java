package com.idark.valoria.registries.item.armor.item;

import com.idark.valoria.client.render.armor.*;
import com.idark.valoria.registries.*;
import it.unimi.dsi.fastutil.objects.*;
import net.minecraft.client.model.*;
import net.minecraft.core.*;
import net.minecraft.resources.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.decoration.*;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.client.extensions.common.*;
import org.jetbrains.annotations.*;
import pro.komaru.tridot.client.model.armor.*;
import software.bernie.geckolib.animatable.*;
import software.bernie.geckolib.animatable.instance.*;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.constant.*;
import software.bernie.geckolib.renderer.*;
import software.bernie.geckolib.util.*;

import java.util.*;
import java.util.function.*;

public class SpiderArmor extends HitEffectArmorItem implements GeoItem{
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public SpiderArmor(Holder<ArmorMaterial> material, Type type, Properties settings, float chance, MobEffectInstance... effects){
        super(material, type, settings, chance, effects);
    }

    // prevents log spam
    public ResourceLocation getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, ArmorMaterial.Layer layer, boolean innerModel){
        return ResourceLocation.withDefaultNamespace("textures/models/armor/diamond_layer_1.png"); // PORT NOTE: getArmorTexture returns a ResourceLocation and receives the material layer in 1.21
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer){
        consumer.accept(new IClientItemExtensions(){
            private GeoArmorRenderer<?> renderer;

            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original){
                ArmorModel model = getArmorModel(livingEntity, itemStack, equipmentSlot, original);
                if(model != null) return model;

                if(this.renderer == null) this.renderer = new SpiderArmorRenderer();
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
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, 20, state -> {
            state.setAnimation(DefaultAnimations.IDLE);
            Entity entity = state.getData(DataTickets.ENTITY);
            if (entity instanceof ArmorStand)
                return PlayState.CONTINUE;

            if (!(entity instanceof LivingEntity living))
                return PlayState.STOP;

            Set<Item> wornArmor = new ObjectOpenHashSet<>();
            for (ItemStack stack : living.getArmorSlots()) {
                if (stack.isEmpty())
                    return PlayState.STOP;

                wornArmor.add(stack.getItem());
            }

            boolean isFullSet = wornArmor.containsAll(ObjectArrayList.of(
            ItemsRegistry.spiderBoots.get(),
            ItemsRegistry.spiderLeggings.get(),
            ItemsRegistry.spiderChestplate.get(),
            ItemsRegistry.spiderHelmet.get()));

            return isFullSet ? PlayState.CONTINUE : PlayState.STOP;
        }));
    }
}
