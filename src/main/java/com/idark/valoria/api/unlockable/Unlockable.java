package com.idark.valoria.api.unlockable;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class Unlockable {
    public String id;

    public Unlockable(String id) {
        this.id = id;
    }

    public boolean canReceived() {
        return false;
    }

    public String getId() {
        return id;
    }

    public boolean hasToast() {
        return true;
    }

    public boolean hasAllAward() {
        return true;
    }

    public void award(Player player) {

    }

    @OnlyIn(Dist.CLIENT)
    public ItemStack getIcon() {
        return ItemStack.EMPTY;
    }
}