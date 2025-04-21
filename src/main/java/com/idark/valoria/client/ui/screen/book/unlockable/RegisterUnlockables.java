package com.idark.valoria.client.ui.screen.book.unlockable;

import com.idark.valoria.*;
import com.idark.valoria.api.unlockable.*;
import com.idark.valoria.api.unlockable.types.*;
import net.minecraft.world.item.*;

import static com.idark.valoria.Valoria.loc;

public class RegisterUnlockables{

    public static Unlockable
    crypt, test;

    public static void init() {
        crypt = register(new Unlockable(Valoria.ID + ":crypt"));
        test = register(new ItemUnlockable(Valoria.ID + ":reward_test", Items.DIAMOND)
                .addAward(new ItemStack(Items.DIAMOND, 10), new ItemStack(Items.NETHERITE_INGOT, 2))
                .addAward(loc("loot_tables/items/crusher")));
    }

    public static Unlockable register(Unlockable unlockable){
        Unlockables.register(unlockable);
        return unlockable;
    }
}