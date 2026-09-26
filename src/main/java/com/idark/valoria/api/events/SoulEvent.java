package com.idark.valoria.api.events;

import net.minecraft.world.item.*;
import net.neoforged.bus.api.*;

public class SoulEvent extends Event{

    public static class Added extends SoulEvent implements ICancellableEvent{
        public ItemStack stack;
        public int count;

        public Added(ItemStack stack, int count) {
            this.stack = stack;
            this.count = count;
        }

        public void addCount(int count) {
            this.count += count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
}