package com.idark.valoria.registries.item.armor.item;

import com.google.common.collect.*;
import com.idark.valoria.client.render.armor.*;
import com.idark.valoria.registries.*;
import it.unimi.dsi.fastutil.objects.*;
import net.minecraft.client.model.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.decoration.*;
import net.minecraft.world.item.*;
import net.minecraftforge.client.extensions.common.*;
import org.jetbrains.annotations.*;
import pro.komaru.tridot.client.model.armor.*;
import pro.komaru.tridot.common.registry.item.armor.*;
import software.bernie.geckolib.animatable.*;
import software.bernie.geckolib.constant.*;
import software.bernie.geckolib.core.animatable.instance.*;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.*;
import software.bernie.geckolib.renderer.*;
import software.bernie.geckolib.util.*;

import java.util.*;
import java.util.function.*;

public class SamuraiArmorItem extends SuitArmorItem implements GeoItem{
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public SamuraiArmorItem(ArmorMaterial material, Type type, Properties properties){
        super(material, type, properties);
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

                if (this.renderer == null) this.renderer = new SamuraiArmorRenderer();
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

            Set<Item> wornArmor = new ObjectOpenHashSet<>();
            for (ItemStack stack : entity.getArmorSlots()) {
                if (stack.isEmpty())
                    return PlayState.STOP;

                wornArmor.add(stack.getItem());
            }

            boolean isFullSet = wornArmor.containsAll(ObjectArrayList.of(
            ItemsRegistry.samuraiBoots.get(),
            ItemsRegistry.samuraiLeggings.get(),
            ItemsRegistry.samuraiChestplate.get(),
            ItemsRegistry.samuraiKabuto.get()));

            return isFullSet ? PlayState.CONTINUE : PlayState.STOP;
        }));
    }

    public float getBonusValue(EquipmentSlot slot){
        return switch(slot){
            case CHEST -> 0.15f;
            case HEAD, FEET -> 0.05f;
            case LEGS -> 0.1f;
            default -> 0;
        };
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot){
        ImmutableMultimap.Builder<Attribute, AttributeModifier> atts = ImmutableMultimap.builder();
        atts.putAll(super.getDefaultAttributeModifiers(slot));
        atts.put(AttributeReg.DASH_DISTANCE.get(), new AttributeModifier(UUID.fromString("58c87772-fa46-4635-8877-72fa464635a6"), "bonus", getBonusValue(slot), AttributeModifier.Operation.ADDITION));
        return slot == type.getSlot() ? atts.build() : super.getDefaultAttributeModifiers(slot);
    }
}