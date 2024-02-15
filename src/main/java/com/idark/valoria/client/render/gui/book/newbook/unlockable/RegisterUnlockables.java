package com.idark.valoria.client.render.gui.book.newbook.unlockable;

import com.idark.valoria.Valoria;
import com.idark.valoria.api.unlockable.Unlockables;
import com.idark.valoria.item.ModItems;

public class RegisterUnlockables {

    public static ItemUnlockable ALOE = new ItemUnlockable(Valoria.MOD_ID+":aloe", false, 5, ModItems.ALOE.get());

    public static void init() {
        Unlockables.register(ALOE);
    }
}