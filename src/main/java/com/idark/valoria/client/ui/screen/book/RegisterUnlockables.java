package com.idark.valoria.client.ui.screen.book;

import com.idark.valoria.*;
import com.idark.valoria.api.unlockable.*;
import com.idark.valoria.api.unlockable.types.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.level.*;
import net.minecraft.world.item.*;

import static com.idark.valoria.Valoria.loc;

public class RegisterUnlockables{

    public static Unlockable
    pick, heavyWorkbench,
    crushables, stoneCrusher,

    undead, kingCrab, harmonyEntities,
    necromancerGrimoire, necromancer, harmonyCrown, dryador, suspiciousGem, wickedCrystal,
    crypt, fortress, valoriaPortal, valoriaVisit,

    cobalt, blackGold,
    natureCore, aquariusCore, infernalCore, voidCore // elemental

    ;

    public static void init() {
        crypt = register(new Unlockable(Valoria.ID + ":crypt"));
        kingCrab = register(new EntityUnlockable(Valoria.ID + ":king_crab", ItemsRegistry.crabClaw.get(), EntityTypeRegistry.KING_CRAB.get()));
        harmonyEntities = register(new EntityTagUnlockable(Valoria.ID + ":harmony_entities", ItemsRegistry.harmonyHeart.get(), TagsRegistry.HARMONY_CREATURES));
        fortress = register(new Unlockable(ItemsRegistry.wickedAmethyst.get(), Valoria.ID + ":fortress", false));
        pick = register(new ItemUnlockable(Valoria.ID + ":pick", ItemsRegistry.pick.get()).addAward(loc("items/crusher")));
        heavyWorkbench = register(new ItemUnlockable(Valoria.ID + ":heavy_workbench", BlockRegistry.heavyWorkbench.get().asItem()));
        stoneCrusher = register(new ItemUnlockable(Valoria.ID + ":stone_crusher", BlockRegistry.stoneCrusher.get().asItem()));
        crushables = register(new TagUnlockable(Valoria.ID + ":crushables", TagsRegistry.CRUSHABLE));
        undead = register(new Unlockable(Valoria.ID + ":undead"));
        valoriaPortal = register(new ItemUnlockable(Valoria.ID + ":valoria_portal", false, ItemsRegistry.valoriaPortalFrameShard.get()));
        valoriaVisit = register(new DimensionUnlockable(Valoria.ID + ":valoria_visit", false, ItemsRegistry.nihilityShard.get(), LevelGen.VALORIA_KEY));
        necromancerGrimoire = register(new ItemUnlockable(Valoria.ID + ":necromancer_grimoire", false, ItemsRegistry.necromancerGrimoire.get()));
        necromancer = register(new Unlockable(Valoria.ID + ":necromancer", false));
        harmonyCrown = register(new ItemUnlockable(Valoria.ID + ":harmony_crown", false, ItemsRegistry.harmonyCrown.get()));
        dryador = register(new Unlockable(ItemsRegistry.harmonyCrown.get(), Valoria.ID + ":dryador", false));
        suspiciousGem = register(new ItemUnlockable(Valoria.ID + ":suspicious_gem", false, ItemsRegistry.suspiciousGem.get()));
        wickedCrystal = register(new Unlockable(ItemsRegistry.suspiciousGem.get(), Valoria.ID + ":wicked_crystal", false));
        cobalt = register(new ItemUnlockable(Valoria.ID + ":cobalt", Items.DIAMOND_PICKAXE));
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