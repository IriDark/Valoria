package com.idark.valoria.client.render.gui.book.unlockable;

import com.idark.valoria.api.unlockable.Unlockable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class ItemUnlockable extends Unlockable {
    @Deprecated
    public final Item item;

    public ItemUnlockable(String id, Item item) {
        super(id);
        this.item = item;
    }

    public boolean canReceived(List<ItemStack> items) {
        for (ItemStack stack : items) {
            if (stack.getItem().equals(item)) {
                return true;
            }
        }
        return false;
    }

    public Item getItem() {
        return item;
    }

    @OnlyIn(Dist.CLIENT)
    public ItemStack getIcon() {
        return item.getDefaultInstance();
    }
}