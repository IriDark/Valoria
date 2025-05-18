package com.idark.valoria.registries.item.armor.item;

import com.idark.valoria.client.*;
import com.idark.valoria.client.model.armor.*;
import net.minecraft.client.*;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import net.minecraftforge.client.extensions.common.*;
import org.jetbrains.annotations.*;
import pro.komaru.tridot.common.registry.item.armor.*;
import pro.komaru.tridot.common.registry.item.skins.*;

import java.util.function.*;

public class PhantasmArmorItem extends EffectArmorItem{
	public PhantasmArmorItem(Type type, ArmorMaterial material, Properties properties) {
		super(material, type, properties);
	}

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(ArmorRender.INSTANCE);
    }

    private static final class ArmorRender implements IClientItemExtensions {
        private static final ArmorRender INSTANCE = new ArmorRender();

        @Override
        public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel<?> model) {
            EntityModelSet models = Minecraft.getInstance().getEntityModels();
            if(stack.getItem() instanceof SkinableArmorItem skinableArmorItem){
                ItemSkin skin = ItemSkin.itemSkin(stack);
                if(skin != null) return skinableArmorItem.getArmorModel(living, stack, slot, model);
            }

            ModelPart root = models.bakeLayer(slot == EquipmentSlot.LEGS ? ValoriaLayers.PHANTASM_ARMOR_INNER : ValoriaLayers.PHANTASM_ARMOR_OUTER);
            return new BaseArmorModel(root);
        }
    }
}