package com.idark.valoria.client.gui.screen.book.unlockable;

import com.idark.valoria.api.unlockable.UnlockUtils;
import com.idark.valoria.api.unlockable.Unlockable;
import com.idark.valoria.client.gui.screen.book.Chapter;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class UnlockableEntry {
    public Chapter chapter;
    public ItemStack icon;
    public Unlockable unlockable;

    public UnlockableEntry(Chapter chapter, ItemStack icon) {
        this.chapter = chapter;
        this.icon = icon;
    }

    public UnlockableEntry(Chapter chapter, ItemStack icon, Unlockable unlockable) {
        this.chapter = chapter;
        this.icon = icon;
        this.unlockable = unlockable;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isUnlocked() {
        if (unlockable == null) {
            return true;
        } else {
            return (UnlockUtils.isUnlocked(Minecraft.getInstance().player, unlockable));
        }
    }
}