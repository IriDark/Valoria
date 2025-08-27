package com.idark.valoria.registries.item.armor.item;

import com.idark.valoria.client.*;
import com.idark.valoria.client.model.armor.*;
import net.minecraft.*;
import net.minecraft.client.*;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.client.extensions.common.*;
import org.jetbrains.annotations.*;
import pro.komaru.tridot.common.registry.item.armor.*;
import pro.komaru.tridot.common.registry.item.skins.*;

import java.util.*;
import java.util.function.*;

public class CrimtaneArmor extends SuitArmorItem{
    public CrimtaneArmor(Type type, ArmorMaterial material, Properties settings){
        super(material, type, settings);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> list, TooltipFlag flags){
        super.appendHoverText(stack, world, list, flags);
        if (stack.getItem() instanceof ArmorItem armor) {
            var effects = AbstractArmorRegistry.HIT_EFFECTS.get(armor.getMaterial());
            if (effects != null) {
                var component = Component.translatable("tooltip.tridot.applies_to_target").withStyle(ChatFormatting.GRAY);
                for (int i = 0; i < effects.size(); i++) {
                    MobEffect effect = effects.get(i).instance().get().getEffect();
                    var effectName = effect.getDisplayName().getString();
                    component.append(Component.literal(effectName).withStyle(stack.getRarity().getStyleModifier()));
                    if (i < effects.size() - 1) {
                        component.append(Component.literal(", ").withStyle(ChatFormatting.GRAY));
                    }
                }

                list.add(component);
            }
        }
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(CrimtaneArmor.ArmorRender.INSTANCE);
    }

    private static final class ArmorRender implements IClientItemExtensions {
        private static final CrimtaneArmor.ArmorRender INSTANCE = new CrimtaneArmor.ArmorRender();

        @Override
        public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel<?> model) {
            EntityModelSet models = Minecraft.getInstance().getEntityModels();
            if(stack.getItem() instanceof SkinableArmorItem skinableArmorItem){
                ItemSkin skin = ItemSkin.itemSkin(stack);
                if(skin != null) return skinableArmorItem.getArmorModel(living, stack, slot, model);
            }

            ModelPart root = models.bakeLayer(slot == EquipmentSlot.LEGS ? ValoriaLayers.INFERNAL_ARMOR_INNER : ValoriaLayers.INFERNAL_ARMOR_OUTER);
            return new BaseArmorModel(root);
        }
    }
}