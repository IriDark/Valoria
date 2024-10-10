package com.idark.valoria.registries.item.skins;

import com.idark.valoria.Valoria;
import mod.maxbogomol.fluffy_fur.common.itemskin.ItemSkin;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class SkinTrimItem extends Item {
    public ItemSkin skin;
    public SkinTrimItem(ItemSkin skin, Item.Properties properties) {
        super(properties);
        this.skin = skin;
    }

    public ItemSkin getSkin() {
        return skin;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flags) {
        tooltip.add(getSkin().getSkinComponent());
    }

    public String getDescriptionId() {
        return Util.makeDescriptionId("item", new ResourceLocation(Valoria.ID, "skin_trim"));
    }
}
