package com.idark.valoria.registries.item.skins.entries;

import com.idark.valoria.*;
import net.minecraft.client.model.*;
import net.minecraft.resources.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import net.minecraftforge.api.distmarker.*;
import pro.komaru.tridot.client.model.armor.*;
import pro.komaru.tridot.common.registry.item.armor.*;
import pro.komaru.tridot.common.registry.item.skins.entries.*;

public class TheFallenCollectorSkinEntry extends ArmorExtendingSkinEntry{
    String skin;
    public TheFallenCollectorSkinEntry(Class<? extends Item> item, ResourceLocation modelLoc){
        super(item, modelLoc);
        this.skin=modelLoc.toString();
    }

    public TheFallenCollectorSkinEntry addArmorSkin(EquipmentSlot armorSlot, ResourceLocation modelLoc){
        skins.put(armorSlot, modelLoc.toString());
        return this;
    }

    @Override
    public boolean appliesOn(ItemStack stack){
        if(item.isInstance(stack.getItem())){
            if(stack.getItem() instanceof SkinableArmorItem armor){
                return skins.containsKey(armor.getEquipmentSlot());
            }
        }

        return false;
    }


    @OnlyIn(Dist.CLIENT)
    public String itemModel(ItemStack stack){
        if(stack.getItem() instanceof SkinableArmorItem armor){
            return skins.get(armor.getEquipmentSlot());
        }

        return null;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public ArmorModel armorModel(LivingEntity entity, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel _default){
        return ValoriaClient.THE_FALLEN_COLLECTOR_ARMOR;
    }

    @Override
    public String armorTexture(Entity entity, ItemStack stack, EquipmentSlot slot, String type){
        return skin + ".png";
    }
}