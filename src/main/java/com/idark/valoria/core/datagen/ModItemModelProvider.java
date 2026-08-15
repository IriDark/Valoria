package com.idark.valoria.core.datagen;

import com.idark.valoria.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.item.types.*;
import net.minecraft.data.*;
import net.minecraft.resources.*;
import net.minecraft.server.packs.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.*;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.client.model.generators.loaders.*;
import net.minecraftforge.common.data.*;
import net.minecraftforge.registries.*;

import java.util.*;
import java.util.function.*;

public class ModItemModelProvider extends ItemModelProvider {
    public static List<Item> entries = new ArrayList<>();

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Valoria.ID, existingFileHelper);
    }

    protected void add(Item pItem, Function<Item, ItemModelBuilder> pFactory) {
        pFactory.apply(pItem);
        entries.add(pItem);
    }

    protected void skip(Item pItem) {
        entries.add(pItem);
    }

    @Override
    protected void registerModels() {
        largeHandheldRotated(ItemsRegistry.bloodHound.get(), ItemsRegistry.clawhook.get(), ItemsRegistry.meatCutter.get());
        largeHandheld(ItemsRegistry.bronzeSword.get(), ItemsRegistry.voidEdge.get(), ItemsRegistry.cobaltSword.get(), ItemsRegistry.ent.get(), ItemsRegistry.coralReef.get(), ItemsRegistry.phantom.get(), ItemsRegistry.infernalSword.get(), ItemsRegistry.etherealSword.get());
        largeHandheld(ItemsRegistry.ironKatana.get(), ItemsRegistry.goldenKatana.get(), ItemsRegistry.diamondKatana.get(), ItemsRegistry.netheriteKatana.get(), ItemsRegistry.jadeKatana.get(), ItemsRegistry.holidayKatana.get(), ItemsRegistry.samuraiKatana.get(), ItemsRegistry.murasama.get());
        largeHandheld(ItemsRegistry.ironScythe.get(), ItemsRegistry.goldenScythe.get(), ItemsRegistry.diamondScythe.get(), ItemsRegistry.netheriteScythe.get(), ItemsRegistry.lunarScythe.get(), ItemsRegistry.jadeScythe.get(), ItemsRegistry.aquariusScythe.get(), ItemsRegistry.reaperScythe.get(), ItemsRegistry.natureScythe.get(), ItemsRegistry.crimtaneScythe.get(), ItemsRegistry.voidScythe.get(), ItemsRegistry.infernalScythe.get(), ItemsRegistry.beast.get());
        spear(ItemsRegistry.woodenSpear.get(), ItemsRegistry.stoneSpear.get(), ItemsRegistry.ironSpear.get(), ItemsRegistry.goldenSpear.get(), ItemsRegistry.diamondSpear.get(), ItemsRegistry.netheriteSpear.get(), ItemsRegistry.jadeSpear.get(), ItemsRegistry.lunarSpear.get(), ItemsRegistry.aquariusSpear.get(), ItemsRegistry.etherealSpear.get(), ItemsRegistry.natureSpear.get(), ItemsRegistry.pyratiteSpear.get(), ItemsRegistry.voidSpear.get(), ItemsRegistry.infernalSpear.get());
        separateTransforms(ItemsRegistry.glaive.get(), modLoc("item/spear"), mcLoc("item/handheld"), modLoc("item/glaive_large"), true);

        bow(ItemsRegistry.natureBow.get(), ItemsRegistry.aquariusBow.get(), ItemsRegistry.infernalBow.get(), ItemsRegistry.voidBow.get(), ItemsRegistry.phantasmBow.get(), ItemsRegistry.jadeBow.get());
        bow(1.2F, 0.88F, ItemsRegistry.lunarBow.get(), ItemsRegistry.samuraiLongBow.get());
        crossbow(ItemsRegistry.natureCrossbow.get(), ItemsRegistry.aquariusCrossbow.get(), ItemsRegistry.infernalCrossbow.get(), ItemsRegistry.voidCrossbow.get(), ItemsRegistry.phantasmCrossbow.get(), ItemsRegistry.jadeCrossbow.get());

        this.skip(ItemsRegistry.spectralBlade.get());
        this.skip(ItemsRegistry.spectralBladeThrown.get());

        this.skip(ItemsRegistry.draugrShield.get());
        this.skip(ItemsRegistry.bronzeShield.get());
        this.skip(ItemsRegistry.natureShield.get());
        this.skip(ItemsRegistry.aquariusShield.get());
        this.skip(ItemsRegistry.infernalShield.get());
        this.skip(ItemsRegistry.voidShield.get());
        this.skip(ItemsRegistry.spiderShield.get());
        this.skip(ItemsRegistry.crimtaneShield.get());
        this.skip(ItemsRegistry.pyratiteShield.get());
        this.skip(ItemsRegistry.wickedShield.get());
        this.skip(ItemsRegistry.phantasmShield.get());
        this.skip(ItemsRegistry.ironEyeNecklace.get());
        this.skip(ItemsRegistry.goldenEyeNecklace.get());
        this.skip(ItemsRegistry.netheriteEyeNecklace.get());

        this.skip(ItemsRegistry.jewelryBag.get());
        this.skip(BlockRegistry.umbralKeypad.get().asItem());
        this.skip(BlockRegistry.jewelerTable.get().asItem());
        this.skip(BlockRegistry.stoneCrusher.get().asItem());
        this.skip(BlockRegistry.crypticAltar.get().asItem());
        this.skip(BlockRegistry.wickedAltar.get().asItem());
        this.skip(BlockRegistry.keg.get().asItem());
        this.skip(BlockRegistry.kiln.get().asItem());
        this.skip(BlockRegistry.heavyWorkbench.get().asItem());
        this.skip(BlockRegistry.shadeTrappedChest.get().asItem());
        this.skip(BlockRegistry.shadeChest.get().asItem());
        this.skip(BlockRegistry.eldritchTrappedChest.get().asItem());
        this.skip(BlockRegistry.eldritchChest.get().asItem());
        this.skip(BlockRegistry.valoriaPortal.get().asItem());
        this.skip(BlockRegistry.valoriaPortalFrame.get().asItem());
        this.skip(BlockRegistry.firronTrophy.get().asItem());
        this.skip(BlockRegistry.bossTrophy.get().asItem());
        this.skip(BlockRegistry.necromancerTrophy.get().asItem());
        this.skip(BlockRegistry.wickedCrystalTrophy.get().asItem());
        this.skip(BlockRegistry.dryadorTrophy.get().asItem());
        this.skip(BlockRegistry.sarcophagus.get().asItem());
        this.skip(BlockRegistry.alchemyStationTier1.get().asItem());
        this.skip(BlockRegistry.alchemyStationTier2.get().asItem());
        this.skip(BlockRegistry.alchemyStationTier3.get().asItem());
        this.skip(BlockRegistry.alchemyStationTier4.get().asItem());
        this.skip(BlockRegistry.voidTaint.get().asItem());
        this.skip(BlockRegistry.bloodVein.get().asItem());
        this.skip(BlockRegistry.potSmall.get().asItem());
        this.skip(BlockRegistry.potSmallHandles.get().asItem());
        this.skip(BlockRegistry.potLong.get().asItem());
        this.skip(BlockRegistry.potLongHandles.get().asItem());
        this.skip(BlockRegistry.potDesert.get().asItem());
        this.skip(BlockRegistry.potDesertHandles.get().asItem());
        this.skip(BlockRegistry.potLongMossy.get().asItem());
        this.skip(BlockRegistry.potLongMossyHandles.get().asItem());
        this.skip(BlockRegistry.cryptPot.get().asItem());
        this.skip(BlockRegistry.decoratedCryptPot.get().asItem());
        this.skip(BlockRegistry.shadeBlossom.get().asItem());
        this.skip(BlockRegistry.shadeBranch.get().asItem());
        this.skip(BlockRegistry.shadeSapling.get().asItem());
        this.skip(BlockRegistry.eldritchSapling.get().asItem());
        this.skip(BlockRegistry.dreadwoodSapling.get().asItem());
        this.skip(ItemsRegistry.summonBook.get().asItem());
        this.skip(ItemsRegistry.crystal.get().asItem());
        this.skip(ItemsRegistry.mannequin.get().asItem());
        this.skip(ItemsRegistry.flameSword.get().asItem());
        this.skip(BlockRegistry.bronzeGlassPane.get().asItem());
        this.skip(BlockRegistry.abyssalGlowfern.get().asItem());
        this.skip(BlockRegistry.aloe.get().asItem());
        this.skip(BlockRegistry.bloodVein.get().asItem());
        this.skip(BlockRegistry.caveRoot.get().asItem());
        this.skip(BlockRegistry.glowVioletSprout.get().asItem());
        this.skip(BlockRegistry.soulFlower.get().asItem());
        this.skip(BlockRegistry.suspiciousIce.get().asItem());
        this.skip(BlockRegistry.suspiciousTombstone.get().asItem());

        // Automatic Item Model generation for Block Items in BlockRegistry
        for (RegistryObject<Block> entry : BlockRegistry.BLOCK.getEntries()){
            Block block = entry.get();
            String name = entry.getId().getPath();
            if(!entries.contains(block.asItem())){
                if(block instanceof DoorBlock){
                    this.add(block.asItem(), item -> simpleItem(name, "item/"));
                }else if(block instanceof WallBlock){
                    this.add(block.asItem(), item -> wallInventory(name, modLoc("block/" + name.replace("_wall", ""))));
                }else if(block instanceof ChestBlock){
                    this.skip(block.asItem());
                }else if(block instanceof FenceBlock){
                    String baseName = getBaseBlockName(name, "_fence");
                    this.add(block.asItem(), item -> fenceInventory(name, modLoc("block/" + baseName)));
                }else if(block instanceof ButtonBlock){
                    String baseName = getBaseBlockName(name, "_button");
                    this.add(block.asItem(), item -> buttonInventory(name, modLoc("block/" + baseName)));
                }else if(block instanceof TrapDoorBlock){
                    this.add(block.asItem(), item -> getBuilder(name).parent(new ModelFile.UncheckedModelFile(modLoc("block/" + name + "_bottom").toString())));
                }else{
                    this.add(block.asItem(), item -> getBuilder(name).parent(new ModelFile.UncheckedModelFile(modLoc("block/" + name).toString())));
                }
            }
        }

        // Automatic Item Model generation for Items in ItemsRegistry
        for (RegistryObject<Item> entry : ItemsRegistry.ITEMS.getEntries()) {
            Item item = entry.get();
            String name = entry.getId().getPath();
            if(!this.entries.contains(item)){
                if(!(item instanceof TexturedSpawnEggItem) && item instanceof SpawnEggItem || name.contains("spawn_egg")){
                    this.add(item, gen -> withExistingParent(name, new ResourceLocation("item/template_spawn_egg")));
                } else if (!hasItemTexture(name)) {
                    Valoria.LOGGER.warn("Skipping item model for {} because item/{}.png is missing", entry.getId(), name);
                    this.skip(item);
                }else if(item instanceof TieredItem || item instanceof SwordItem || item instanceof DiggerItem){
                    this.add(item, gen -> handheldItem(name));
                }else{
                    this.add(item, gen -> simpleItem(name, "item/"));
                }
            }
        }
    }

    private String getBaseBlockName(String name, String suffix) {
        if (name.startsWith("shade_") || name.startsWith("eldritch_") || name.startsWith("dread_")) {
            if (suffix.equals("_fence") || suffix.equals("_fence_gate") || suffix.equals("_button") || suffix.equals("_pressure_plate")) {
                return name.substring(0, name.indexOf('_')) + "_planks";
            }
        }
        return name.replace(suffix, "");
    }

    private ItemModelBuilder simpleItem(String name, String path) {
        return withExistingParent(name, new ResourceLocation("item/generated"))
                .texture("layer0", new ResourceLocation(Valoria.ID, path + name));
    }

    private ItemModelBuilder handheldItem(String name) {
        return withExistingParent(name, new ResourceLocation("item/handheld"))
                .texture("layer0", new ResourceLocation(Valoria.ID, "item/" + name));
    }

    private void largeHandheldRotated(Item... items) {
        for (Item item : items) {
            separateTransforms(item, mcLoc("item/handheld"), mcLoc("item/handheld"), modLoc("item/handheld_large_rotate"), false);
        }
    }

    private void largeHandheld(Item... items) {
        for (Item item : items) {
            separateTransforms(item, mcLoc("item/handheld"), mcLoc("item/handheld"), modLoc("item/handheld_large"), false);
        }
    }

    private void spear(Item... items) {
        for (Item item : items) {
            separateTransforms(item, modLoc("item/spear"), modLoc("item/spear"), modLoc("item/spear_large"), true);
        }
    }

    private void separateTransforms(Item item, ResourceLocation baseParent, ResourceLocation iconParent, ResourceLocation inHandParent, boolean includeNone) {
        String name = ForgeRegistries.ITEMS.getKey(item).getPath();
        if (!hasItemTexture(name) || !hasItemTexture(name + "_in_hand")) {
            this.skip(item);
            return;
        }
        ItemModelBuilder base = withExistingParent(name, baseParent);
        ItemModelBuilder icon = withExistingParent(name + "_icon", iconParent).texture("layer0", modLoc("item/" + name));
        ItemModelBuilder inHand = withExistingParent(name + "_in_hand", inHandParent).texture("layer0", modLoc("item/" + name + "_in_hand"));
        SeparateTransformsModelBuilder<ItemModelBuilder> transforms = base.customLoader(SeparateTransformsModelBuilder::begin)
                .base(inHand)
                .perspective(ItemDisplayContext.GUI, icon)
                .perspective(ItemDisplayContext.GROUND, icon)
                .perspective(ItemDisplayContext.FIXED, icon);
        if (includeNone) {
            transforms.perspective(ItemDisplayContext.NONE, inHand);
        }
        transforms.end();
        this.skip(item);
    }

    private void bow(Item... items) {
        bow(0.9F, 0.68F, items);
    }

    private void bow(float thirdPersonYScale, float firstPersonYScale, Item... items) {
        for (Item item : items) {
            String name = ForgeRegistries.ITEMS.getKey(item).getPath();
            if (!hasItemTextures(name, name + "_pulling_0", name + "_pulling_1", name + "_pulling_2")) {
                this.skip(item);
                continue;
            }
            ItemModelBuilder model = withExistingParent(name, mcLoc("item/generated")).texture("layer0", modLoc("item/" + name));
            applyBowTransforms(model, thirdPersonYScale, firstPersonYScale);
            model.override().predicate(mcLoc("pulling"), 1.0F).model(modelVariant(name, "pulling_0", model)).end()
                    .override().predicate(mcLoc("pulling"), 1.0F).predicate(mcLoc("pull"), 0.65F).model(modelVariant(name, "pulling_1", model)).end()
                    .override().predicate(mcLoc("pulling"), 1.0F).predicate(mcLoc("pull"), 0.9F).model(modelVariant(name, "pulling_2", model)).end();
            this.skip(item);
        }
    }

    private void crossbow(Item... items) {
        for (Item item : items) {
            String name = ForgeRegistries.ITEMS.getKey(item).getPath();
            if (!hasItemTextures(name + "_standby", name + "_pulling_0", name + "_pulling_1", name + "_pulling_2", name + "_arrow", name + "_firework")) {
                this.skip(item);
                continue;
            }
            ItemModelBuilder model = withExistingParent(name, mcLoc("item/generated")).texture("layer0", modLoc("item/" + name + "_standby"));
            applyCrossbowTransforms(model);
            model.override().predicate(mcLoc("pulling"), 1.0F).model(crossbowStage(name, "pulling_0", model)).end()
                    .override().predicate(mcLoc("pulling"), 1.0F).predicate(mcLoc("pull"), 0.58F).model(crossbowStage(name, "pulling_1", model)).end()
                    .override().predicate(mcLoc("pulling"), 1.0F).predicate(mcLoc("pull"), 1.0F).model(crossbowStage(name, "pulling_2", model)).end()
                    .override().predicate(mcLoc("charged"), 1.0F).model(crossbowStage(name, "arrow", model)).end()
                    .override().predicate(mcLoc("charged"), 1.0F).predicate(mcLoc("firework"), 1.0F).model(crossbowStage(name, "firework", model)).end();
            this.skip(item);
        }
    }

    private ItemModelBuilder crossbowStage(String name, String stage, ItemModelBuilder parent) {
        return modelVariant(name, stage, parent);
    }

    private ItemModelBuilder modelVariant(String name, String stage, ItemModelBuilder parent) {
        return getBuilder(name + "_" + stage).parent(parent).texture("layer0", modLoc("item/" + name + "_" + stage));
    }

    private void applyBowTransforms(ItemModelBuilder model, float thirdPersonYScale, float firstPersonYScale) {
        model.transforms()
                .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND).rotation(-80, 260, -40).translation(-1, -2, 2.5F).scale(0.9F, thirdPersonYScale, 0.9F).end()
                .transform(ItemDisplayContext.THIRD_PERSON_LEFT_HAND).rotation(-80, 280, 40).translation(-1, -2, 2.5F).scale(0.9F, thirdPersonYScale, 0.9F).end()
                .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND).rotation(0, -90, 25).translation(1.13F, 3.2F, 1.13F).scale(0.68F, firstPersonYScale, 0.68F).end()
                .transform(ItemDisplayContext.FIRST_PERSON_LEFT_HAND).rotation(0, 90, -25).translation(1.13F, 3.2F, 1.13F).scale(0.68F, firstPersonYScale, 0.68F).end()
                .end();
    }

    private void applyCrossbowTransforms(ItemModelBuilder model) {
        model.transforms()
                .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND).rotation(-90, 0, -60).translation(2, 0.1F, -3).scale(0.9F).end()
                .transform(ItemDisplayContext.THIRD_PERSON_LEFT_HAND).rotation(-90, 0, 30).translation(2, 0.1F, -3).scale(0.9F).end()
                .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND).rotation(-90, 0, -55).translation(1.13F, 3.2F, 1.13F).scale(0.68F).end()
                .transform(ItemDisplayContext.FIRST_PERSON_LEFT_HAND).rotation(-90, 0, 35).translation(1.13F, 3.2F, 1.13F).scale(0.68F).end()
                .end();
    }

    private boolean hasExistingItemModel(ResourceLocation id) {
        return existingFileHelper.exists(id, PackType.CLIENT_RESOURCES, ".json", "models/item");
    }

    private boolean hasItemTexture(String name) {
        return existingFileHelper.exists(modLoc("item/" + name), PackType.CLIENT_RESOURCES, ".png", "textures");
    }

    private boolean hasItemTextures(String... names) {
        return Arrays.stream(names).allMatch(this::hasItemTexture);
    }

    @Override
    public ItemModelBuilder wallInventory(String name, ResourceLocation texture) {
        return withExistingParent(name, mcLoc("block/wall_inventory"))
                .texture("wall", texture);
    }

    public ItemModelBuilder fenceInventory(String name, ResourceLocation texture) {
        return withExistingParent(name, mcLoc("block/fence_inventory"))
                .texture("texture", texture);
    }

    public ItemModelBuilder buttonInventory(String name, ResourceLocation texture) {
        return withExistingParent(name, mcLoc("block/button_inventory"))
                .texture("texture", texture);
    }
}
