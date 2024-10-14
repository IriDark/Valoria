package com.idark.valoria.registries.item.skins.categories;

import com.idark.valoria.*;
import com.idark.valoria.core.interfaces.*;
import com.idark.valoria.registries.item.types.*;
import com.idark.valoria.util.*;
import mod.maxbogomol.fluffy_fur.client.model.armor.*;
import mod.maxbogomol.fluffy_fur.client.model.item.*;
import mod.maxbogomol.fluffy_fur.common.itemskin.*;
import net.minecraft.client.model.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import net.minecraftforge.api.distmarker.*;

public class TheFallenCollector extends ItemSkin implements AuthoredItemSkin {
    public TheFallenCollector(String id){
        super(id, Pal.seaGreen);
    }

    public Component getContributorComponent(ItemStack stack) {
        return stack.getHoverName().copy().withStyle(stack.getRarity().getStyleModifier()).append(Component.literal(" ༶ Kerdo ༶").withStyle(Styles.nature));
    }

    @OnlyIn(Dist.CLIENT)
    public static void registerModels(){
        ItemSkinModels.addSkin(Valoria.ID + ":the_fallen_collector_crown");
        ItemSkinModels.addSkin(Valoria.ID + ":the_fallen_collector_coat");
        ItemSkinModels.addSkin(Valoria.ID + ":brand");
    }

    @Override
    public void setupSkinEntries() {
        addSkinEntry(new TheFallenCollectorSkinEntry(ArmorItem.class,
        Valoria.ID+":textures/models/armor/skin/the_fallen_collector")
        .addArmorSkin(EquipmentSlot.HEAD, Valoria.ID + ":the_fallen_collector_crown")
        .addArmorSkin(EquipmentSlot.CHEST, Valoria.ID + ":the_fallen_collector_coat"));

        addSkinEntry(new ItemClassSkinEntry(KatanaItem.class, Valoria.ID+":brand"));
    }

    public static class TheFallenCollectorSkinEntry extends ArmorClassSkinEntry {
        public TheFallenCollectorSkinEntry(Class item, String skin) {
            super(item, skin);
        }

        public TheFallenCollectorSkinEntry addArmorSkin(EquipmentSlot armorSlot, String skin) {
            skins.put(armorSlot, skin);
            return this;
        }

        @Override
        public boolean canApplyOnItem(ItemStack itemStack) {
            if (item.isInstance(itemStack.getItem())) {
                if (itemStack.getItem() instanceof SkinableArmorItem armor) {
                    return skins.containsKey(armor.getEquipmentSlot());
                }
            }

            return false;
        }

        @OnlyIn(Dist.CLIENT)
        public String getItemModelName(ItemStack stack) {
            if (stack.getItem() instanceof SkinableArmorItem armor) {
                return skins.get(armor.getEquipmentSlot());
            }

            return null;
        }

        @Override
        @OnlyIn(Dist.CLIENT)
        public ArmorModel getArmorModel(LivingEntity entity, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel _default) {
            return ValoriaClient.THE_FALLEN_COLLECTOR_ARMOR;
        }

        @Override
        @OnlyIn(Dist.CLIENT)
        public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
            return skin + ".png";
        }
    }

}
