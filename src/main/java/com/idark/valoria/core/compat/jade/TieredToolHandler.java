package com.idark.valoria.core.compat.jade;

import net.minecraft.core.*;
import net.minecraft.tags.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import snownee.jade.addon.harvest.*;

import java.util.*;
import java.util.function.*;

public class TieredToolHandler implements ToolHandler{
    private final String name;
    private final TagKey<Block> tag;
    private final Tier tier;
    private final Supplier<List<ItemStack>> items;

    public TieredToolHandler(String name, TagKey<Block> tag, Tier tier, Supplier<List<ItemStack>> items) {
        this.name = name;
        this.tag = tag;
        this.tier = tier;
        this.items = items;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ItemStack test(BlockState state, Level level, BlockPos pos) {
        if (state.is(tag)) {
            for (ItemStack item : items.get()) {
                if (item.getItem() instanceof TieredItem t && t.getTier() == tier) {
                    if(item.isCorrectToolForDrops(state)) {
                        return item;
                    }
                }
            }
        }

        return ItemStack.EMPTY;
    }

    @Override
    public List<ItemStack> getTools(){
        return items.get();
    }
}