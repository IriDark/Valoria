package com.idark.darkrpg.capability;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;


public interface IPage {

    Capability<IPage> INSTANCE = CapabilityManager.get(new CapabilityToken<>() {});

    boolean isCryptOpen();
    void setOpen(boolean open);
}
