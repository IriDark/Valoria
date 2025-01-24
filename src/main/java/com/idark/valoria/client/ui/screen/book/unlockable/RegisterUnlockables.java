package com.idark.valoria.client.ui.screen.book.unlockable;

import com.idark.valoria.Valoria;
import com.idark.valoria.api.unlockable.Unlockables;

public class RegisterUnlockables{

    public static ChapterUnlockable CRYPT = new ChapterUnlockable(Valoria.ID + ":crypt");

    public static void init(){
        Unlockables.register(CRYPT);
    }
}