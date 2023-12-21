package com.idark.darkrpg.capability;

import com.idark.darkrpg.client.render.gui.book.LexiconGui;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public class PageCapability implements IPage, INBTSerializable<CompoundTag> {
    private static boolean cryptOpen = LexiconGui.isCryptOpen(Minecraft.getInstance().player);

    @Override
    public boolean isCryptOpen() {
        return cryptOpen;
    }

    @Override
    public void setOpen(boolean open) {
        cryptOpen = open;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag wrapper = new CompoundTag();
        wrapper.putBoolean("crypt_page", isCryptOpen());

        return wrapper;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        if (nbt.contains("crypt_page")) {
            cryptOpen = nbt.getBoolean("crypt_page");
        }
    }
}
