package com.idark.darkrpg.capability;

import com.idark.darkrpg.client.render.gui.book.LexiconPages;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;


public interface IPage {

    Capability<IPage> INSTANCE = CapabilityManager.get(new CapabilityToken<>() {});

    boolean isOpen(LexiconPages pages);
    void makeOpen(LexiconPages pages, boolean open);
}
