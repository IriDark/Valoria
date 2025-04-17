package com.idark.valoria.client.ui.screen.book.unlockable;

import com.idark.valoria.*;
import com.idark.valoria.api.unlockable.*;
import net.minecraft.world.item.*;

import static com.idark.valoria.Valoria.loc;

public class RegisterUnlockables{

    public static Unlockable CRYPT = new Unlockable(Valoria.ID + ":crypt");
    public static Unlockable TEST = new Unlockable(Valoria.ID + ":reward_test").addAward(new ItemStack(Items.DIAMOND, 10), new ItemStack(Items.NETHERITE_INGOT, 2)).addAward(loc("loot_tables/items/crusher"));

    public static void init(){
        Unlockables.register(CRYPT);
        Unlockables.register(TEST);
    }
}