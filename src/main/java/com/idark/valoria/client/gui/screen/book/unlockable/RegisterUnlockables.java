package com.idark.valoria.client.gui.screen.book.unlockable;

import com.idark.valoria.*;
import com.idark.valoria.api.unlockable.*;

public class RegisterUnlockables{

    public static ChapterUnlockable CRYPT = new ChapterUnlockable(Valoria.ID + ":crypt");

    public static void init(){
        Unlockables.register(CRYPT);
    }
}