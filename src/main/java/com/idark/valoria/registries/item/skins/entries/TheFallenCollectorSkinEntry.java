package com.idark.valoria.registries.item.skins.entries;

import com.idark.valoria.*;
import com.idark.valoria.registries.item.armor.item.*;
import mod.maxbogomol.fluffy_fur.client.model.armor.*;
import mod.maxbogomol.fluffy_fur.common.itemskin.*;
import net.minecraft.client.model.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import net.minecraftforge.api.distmarker.*;

public class TheFallenCollectorSkinEntry extends ArmorClassSkinEntry{
    public TheFallenCollectorSkinEntry(Class item, String skin){
        super(item, skin);
    }

    public TheFallenCollectorSkinEntry addArmorSkin(EquipmentSlot armorSlot, String skin){
        skins.put(armorSlot, skin);
        return this;
    }

    @Override
    public boolean canApplyOnItem(ItemStack itemStack){
        if(item.isInstance(itemStack.getItem())){
            if(itemStack.getItem() instanceof SkinableArmorItem armor){
                return skins.containsKey(armor.getEquipmentSlot());
            }
        }

        return false;
    }

    @OnlyIn(Dist.CLIENT)
    public String getItemModelName(ItemStack stack){
        if(stack.getItem() instanceof SkinableArmorItem armor){
            return skins.get(armor.getEquipmentSlot());
        }

        return null;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public ArmorModel getArmorModel(LivingEntity entity, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel _default){
        return ValoriaClient.THE_FALLEN_COLLECTOR_ARMOR;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type){
        return skin + ".png";
    }
}