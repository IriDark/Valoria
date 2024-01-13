package com.idark.valoria.capability;

import com.idark.valoria.client.render.gui.book.LexiconGui;
import com.idark.valoria.client.render.gui.book.LexiconPages;
import com.idark.valoria.network.PacketHandler;
import com.idark.valoria.network.PageUpdatePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.util.INBTSerializable;

public class PageCapability implements IPage, INBTSerializable<CompoundTag> {
    boolean isCryptOpen = LexiconGui.pageIsOpen(Minecraft.getInstance().player, LexiconPages.CRYPT);
    boolean isGemsOpen = LexiconGui.pageIsOpen(Minecraft.getInstance().player, LexiconPages.GEMS);
    boolean isMainOpen = LexiconGui.pageIsOpen(Minecraft.getInstance().player, LexiconPages.MAIN);
    boolean isMedicineOpen = LexiconGui.pageIsOpen(Minecraft.getInstance().player, LexiconPages.MEDICINE);

    // TODO: FIX THIS SHIT :(
    public static void makeOpen(Entity entity, LexiconPages pages, boolean open) {
        if (!(entity instanceof ServerPlayer player)) return;
        player.getCapability(IPage.INSTANCE, null).ifPresent((k) -> k.makeOpen(pages, open));
        PacketHandler.sendTo(player, new PageUpdatePacket(player));
    }

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
            case MAIN -> {
                makeOpen(Minecraft.getInstance().player, LexiconPages.MAIN, open);
                isMainOpen = open;
            }
            case GEMS, GEMS_ABOUT -> {
                makeOpen(Minecraft.getInstance().player, LexiconPages.GEMS, open);
                isGemsOpen = open;
            }

            case MEDICINE -> {
                makeOpen(Minecraft.getInstance().player, LexiconPages.MEDICINE, open);
                isMedicineOpen = open;
            }
            //case THANKS -> open;
            case CRYPT -> {
                makeOpen(Minecraft.getInstance().player, LexiconPages.CRYPT, open);
                isCryptOpen = open;
            }
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