package com.idark.valoria.client.ui.screen.book.unlockable;

import com.idark.valoria.*;
import com.idark.valoria.api.unlockable.*;
import com.idark.valoria.api.unlockable.types.*;
import com.idark.valoria.registries.*;

import static com.idark.valoria.Valoria.loc;

public class RegisterUnlockables{

    public static Unlockable
    pick, tinkererWorkbench,

    undead,
    necromancerGrimoire, necromancer, harmonyCrown, dryador, suspiciousGem, wickedCrystal,
    crypt, fortress,

    blackGold,
    natureCore, aquariusCore, infernalCore, voidCore // elemental

    ;

    public static void init() {
        crypt = register(new Unlockable(Valoria.ID + ":crypt"));
        fortress = register(new Unlockable(ItemsRegistry.wickedAmethyst.get(), Valoria.ID + ":fortress", false));
        pick = register(new ItemUnlockable(Valoria.ID + ":pick", ItemsRegistry.pick.get()).addAward(loc("items/crusher")));
        tinkererWorkbench = register(new ItemUnlockable(Valoria.ID + ":tinkerer_workbench", BlockRegistry.tinkererWorkbench.get().asItem()));
        undead = register(new Unlockable(Valoria.ID + ":undead"));
        necromancerGrimoire = register(new ItemUnlockable(Valoria.ID + ":necromancer_grimoire", false, ItemsRegistry.necromancerGrimoire.get()));
        necromancer = register(new Unlockable(Valoria.ID + ":necromancer", false));
        harmonyCrown = register(new ItemUnlockable(Valoria.ID + ":harmony_crown", false, ItemsRegistry.harmonyCrown.get()));
        dryador = register(new Unlockable(ItemsRegistry.harmonyCrown.get(), Valoria.ID + ":dryador", false));
        suspiciousGem = register(new ItemUnlockable(Valoria.ID + ":suspicious_gem", false, ItemsRegistry.suspiciousGem.get()));
        wickedCrystal = register(new Unlockable(ItemsRegistry.suspiciousGem.get(), Valoria.ID + ":wicked_crystal", false));
        blackGold = register(new ItemUnlockable(Valoria.ID + ":black_gold", ItemsRegistry.blackGold.get()));
        natureCore = register(new ItemUnlockable(Valoria.ID + ":nature_core", false, ItemsRegistry.natureCore.get()));
        aquariusCore = register(new ItemUnlockable(Valoria.ID + ":aquarius_core", false, ItemsRegistry.aquariusCore.get()));
        infernalCore = register(new ItemUnlockable(Valoria.ID + ":infernal_core", false, ItemsRegistry.infernalCore.get()));
        voidCore = register(new ItemUnlockable(Valoria.ID + ":void_core", false, ItemsRegistry.voidCore.get()));
    }

    public static Unlockable register(Unlockable unlockable){
        Unlockables.register(unlockable);
        return unlockable;
    }
}