package com.idark.valoria.registries.item.types;

import net.minecraft.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.inventory.tooltip.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.*;
import pro.komaru.tridot.common.registry.item.*;
import pro.komaru.tridot.common.registry.item.components.*;
import pro.komaru.tridot.util.struct.data.*;

import java.util.function.*;

public class BossDescriptionItem extends Item implements TooltipComponentItem{
    Supplier<EntityType<?>> typeSupplier;
    Block block;
    public BossDescriptionItem(Supplier<EntityType<?>> type, Block block, Properties pProperties){
        super(pProperties);
        this.typeSupplier = type;
        this.block = block;
    }

    @Override
    public Seq<TooltipComponent> getTooltips(ItemStack pStack){
        return Seq.with(
            new ObjectComponent(Component.translatable("tooltip.valoria.used_on", ComponentUtils.wrapInSquareBrackets(block.getName())).withStyle(ChatFormatting.GREEN), block.asItem(), 0, 4),
            new TextComponent(Component.translatable("tooltip.valoria.boss_summonable", typeSupplier.get().getDescription()).withStyle(ChatFormatting.GRAY))
        );
    }
}
