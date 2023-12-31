package com.idark.valoria.capability;

import com.idark.valoria.client.render.gui.book.LexiconGui;
import com.idark.valoria.client.render.gui.book.LexiconPages;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public class PageCapability implements IPage, INBTSerializable<CompoundTag> {
    private static boolean isMainOpen = LexiconGui.isOpen(Minecraft.getInstance().player, LexiconPages.MAIN);
    private static boolean isGemsOpen = LexiconGui.isOpen(Minecraft.getInstance().player, LexiconPages.GEMS);
    private static boolean isMedicineOpen = LexiconGui.isOpen(Minecraft.getInstance().player, LexiconPages.MEDICINE);
    private static boolean isCryptOpen = LexiconGui.isOpen(Minecraft.getInstance().player, LexiconPages.MEDICINE);

    @Override
    public boolean isOpen(LexiconPages pages) {
        return switch (pages) {
            case MAIN -> isMainOpen;
            case GEMS, GEMS_ABOUT -> isGemsOpen;
            case MEDICINE -> isMedicineOpen;
            case THANKS -> false;
            case CRYPT -> isCryptOpen;
        };
    }

    @Override
    public void makeOpen(LexiconPages pages, boolean open) {
        switch (pages) {
            case MAIN -> isMainOpen = open;
            case GEMS, GEMS_ABOUT -> isGemsOpen = open;
            case MEDICINE -> isMedicineOpen = open;
            //case THANKS -> open;
            case CRYPT -> isCryptOpen = open;
        }
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag wrapper = new CompoundTag();
        wrapper.putBoolean("main", isOpen(LexiconPages.MAIN));
        wrapper.putBoolean("gems", isOpen(LexiconPages.GEMS));
        wrapper.putBoolean("medicine", isOpen(LexiconPages.MEDICINE));
        wrapper.putBoolean("crypt", isOpen(LexiconPages.CRYPT));


        return wrapper;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        if (nbt.contains("main")) {
            isMainOpen = nbt.getBoolean("main");
        }

        if (nbt.contains("gems")) {
            isGemsOpen = nbt.getBoolean("gems");
        }

        if (nbt.contains("medicine")) {
            isMedicineOpen = nbt.getBoolean("medicine");
        }

        if (nbt.contains("crypt")) {
            isCryptOpen = nbt.getBoolean("crypt");
        }
    }
}