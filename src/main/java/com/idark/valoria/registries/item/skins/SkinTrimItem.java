package com.idark.valoria.registries.item.skins;

import com.idark.valoria.*;
import mod.maxbogomol.fluffy_fur.common.itemskin.*;
import net.minecraft.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraftforge.api.distmarker.*;

import java.util.*;

public class SkinTrimItem extends Item{
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
