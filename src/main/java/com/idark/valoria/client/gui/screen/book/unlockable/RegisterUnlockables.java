package com.idark.valoria.client.gui.screen.book.unlockable;

import com.idark.valoria.Valoria;
import com.idark.valoria.api.unlockable.Unlockables;

public class RegisterUnlockables {

    public static PageUnlockable CRYPT = new PageUnlockable(Valoria.MOD_ID + ":crypt");

    public static void init() {
        Unlockables.register(CRYPT);
    }
}