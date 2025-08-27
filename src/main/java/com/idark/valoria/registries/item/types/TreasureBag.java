package com.idark.valoria.registries.item.types;

import com.idark.valoria.*;
import com.idark.valoria.registries.*;
import net.minecraft.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraft.sounds.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import pro.komaru.tridot.client.gfx.text.*;
import pro.komaru.tridot.common.registry.item.types.*;

import java.util.*;

public class TreasureBag extends LootItem{
    public TreasureBag(ResourceLocation loot, Properties properties){
        super(loot, properties);
    }

    @Override
    public SoundEvent getOpenSound(){
        return SoundsRegistry.BAG_OPEN.get();
    }

    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> list, TooltipFlag flags){
        super.appendHoverText(stack, world, list, flags);
        list.add(1, Component.translatable("tooltip.valoria.treasure").withStyle(ChatFormatting.GRAY));
        list.add(2, Component.empty());
        list.add(3, Component.translatable("tooltip.valoria.rmb").withStyle(DotStyle.of().font(Valoria.FONT)));
    }
}