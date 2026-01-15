package com.idark.valoria.core.compat.jei.jer;

import com.idark.valoria.*;
import com.idark.valoria.registries.*;
import jeresources.api.*;
import jeresources.compatibility.api.*;
import net.minecraft.client.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.resources.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.client.event.*;

@JERPlugin
public class JerCompat {

    @OnlyIn(Dist.CLIENT)
    public static void onClientPlayerLogin(ClientPlayerNetworkEvent.LoggingIn event) {
        Minecraft mc = Minecraft.getInstance();
        ClientLevel clientLevel = mc.level;
        if (clientLevel != null) {
            IMobRegistry mobRegistry = JERAPI.getInstance().getMobRegistry();
            if (mobRegistry != null) {
                mobRegistry.register(EntityTypeRegistry.WICKED_CRYSTAL.get().create(clientLevel), new ResourceLocation(Valoria.ID, "items/wicked_crystal_treasure_bag"));
                mobRegistry.register(EntityTypeRegistry.DRYADOR.get().create(clientLevel), new ResourceLocation(Valoria.ID, "items/dryador_treasure_bag"));
                mobRegistry.register(EntityTypeRegistry.NECROMANCER.get().create(clientLevel), new ResourceLocation(Valoria.ID, "items/necromancer_treasure_bag"));
            }
        }
    }

    public static void init(){
        IJERAPI jerApi = JERAPI.getInstance();
        IDungeonRegistry dungeonRegistry = jerApi.getDungeonRegistry();
//        IWorldGenRegistry worldGenRegistry = JERAPI.getInstance().getWorldGenRegistry();
//        if(worldGenRegistry != null) oreRegistry(worldGenRegistry);
        if(dungeonRegistry != null) dungeonRegistry(dungeonRegistry);
    }

//    private static void oreRegistry(IWorldGenRegistry worldGenRegistry){
//        worldGenRegistry.register(new ItemStack(BlockRegistry.sapphireOre.get()), new ItemStack(BlockRegistry.deepslateSapphireOre.get()), new DistributionSquare(3, 4, -64, 16), new LootDrop(new ItemStack(ItemsRegistry.sapphireGem.get()), 2, 5, Conditional.affectedByFortune));
//        worldGenRegistry.register(new ItemStack(BlockRegistry.pearliumOre.get()), new DistributionSquare(7, 14, -64, 42), new LootDrop(new ItemStack(ItemsRegistry.pearliumIngot.get()), 2, 5, Conditional.affectedByFortune));
//        worldGenRegistry.register(new ItemStack(BlockRegistry.cobaltOre.get()), new ItemStack(BlockRegistry.deepslateCobaltOre.get()), new DistributionSquare(4, 8, -64, 6), new LootDrop(new ItemStack(ItemsRegistry.rawCobalt.get()), 1, 1, Conditional.affectedByFortune));
//        worldGenRegistry.register(new ItemStack(BlockRegistry.amberOre.get()), new ItemStack(BlockRegistry.deepslateAmberOre.get()), new DistributionSquare(3, 4, -64, 32), new LootDrop(new ItemStack(ItemsRegistry.amberGem.get()), 2, 5, Conditional.affectedByFortune));
//        worldGenRegistry.register(new ItemStack(BlockRegistry.rubyOre.get()), new ItemStack(BlockRegistry.deepslateRubyOre.get()), new DistributionSquare(2, 4, -64, 8), new LootDrop(new ItemStack(ItemsRegistry.rubyGem.get()), 2, 5, Conditional.affectedByFortune));
//
//        // Valoria
//        worldGenRegistry.register(new ItemStack(BlockRegistry.jadeOre.get()), new ItemStack(BlockRegistry.picriteJadeOre.get()), new DistributionSquare(6, 3, -64, 6), new Restriction(new DimensionRestriction(LevelGen.VALORIA_KEY)), new LootDrop(new ItemStack(ItemsRegistry.jade.get()), 2, 5, Conditional.affectedByFortune));
//        worldGenRegistry.register(new ItemStack(BlockRegistry.pyratiteOre.get()), new DistributionSquare(6, 5, -64, 32), new Restriction(new DimensionRestriction(LevelGen.VALORIA_KEY)), new LootDrop(new ItemStack(ItemsRegistry.pyratite.get()), 1, 1, Conditional.affectedByFortune));
//        worldGenRegistry.register(new ItemStack(BlockRegistry.wickedAmethystOre.get()), new DistributionSquare(8, 14, -64, 42), new Restriction(new DimensionRestriction(LevelGen.VALORIA_KEY)), new LootDrop(new ItemStack(ItemsRegistry.wickedAmethyst.get()), 1, 3, Conditional.affectedByFortune));
//        worldGenRegistry.register(new ItemStack(BlockRegistry.dormantCrystals.get()), new DistributionSquare(6, 8, -64, 42), new Restriction(new DimensionRestriction(LevelGen.VALORIA_KEY)), new LootDrop(new ItemStack(ItemsRegistry.unchargedShard.get()), 1, 3, Conditional.affectedByFortune));
//    }

    private static void dungeonRegistry(IDungeonRegistry dungeonRegistry){
        dungeonRegistry.registerChest("Fortress", new ResourceLocation(Valoria.ID, "chests/fortress"));
        dungeonRegistry.registerChest("Fortress Good", new ResourceLocation(Valoria.ID, "chests/fortress_good"));
        dungeonRegistry.registerChest("Fortress Normal", new ResourceLocation(Valoria.ID, "chests/fortress_normal"));

        dungeonRegistry.registerChest("Crypt", new ResourceLocation(Valoria.ID, "chests/crypt"));
        dungeonRegistry.registerChest("Crypt Sarcophagus", new ResourceLocation(Valoria.ID, "items/sarcophagus"));
        dungeonRegistry.registerChest("Necromancer Crypt", new ResourceLocation(Valoria.ID, "chests/necromancer_crypt"));
        dungeonRegistry.registerChest("Crystallized Deep Ruins", new ResourceLocation(Valoria.ID, "chests/crystallized_deep_ruins"));
        dungeonRegistry.registerChest("Fractured Skull", new ResourceLocation(Valoria.ID, "chests/fractured_skull"));
        dungeonRegistry.registerChest("Monstrosity", new ResourceLocation(Valoria.ID, "chests/monstrosity"));
    }
}