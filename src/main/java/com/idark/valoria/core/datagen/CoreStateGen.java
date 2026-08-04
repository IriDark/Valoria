package com.idark.valoria.core.datagen;

import net.minecraft.data.*;
import net.minecraft.resources.*;
import net.minecraft.world.level.block.*;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.*;
import net.minecraftforge.registries.*;

public abstract class CoreStateGen extends BlockStateProvider {
    public String id;

    public CoreStateGen(PackOutput output, String modid, ExistingFileHelper exFileHelper) {
        super(output, modid, exFileHelper);
        this.id = modid;
    }

    public void registerKit(Block stairs, Block slab, Block texture) {
        stairsBlock((StairBlock) stairs, blockTexture(texture));
        registerSlab(slab, texture);
        blockItem(stairs);
    }

    public void wallBlock(WallBlock block, ResourceLocation texture) {
        String baseName = key(block).getPath().replaceFirst("_wall$", "");
        wallBlock(block, baseName, texture);
        itemModels().getBuilder(key(block).getPath()).parent(models().wallInventory(key(block) + "_inventory", texture));
    }

    public void registerKit(Block block, Block stairs, Block slab, Block wall) {
        blockWithItem(block);
        wallBlock((WallBlock) wall, blockTexture(block));
        registerKit(stairs, slab, block);
    }

    public void registerKit(RegistryObject<Block> block, RegistryObject<Block> stairs, RegistryObject<Block> slab, RegistryObject<Block> wall) {
        registerKit(block.get(), stairs.get(), slab.get(), wall.get());
    }

    public void registerSlab(Block slab, Block texture) {
        slabBlock((SlabBlock) slab, blockTexture(texture), blockTexture(texture));
        blockItem(slab);
    }

    public void registerDoor(RegistryObject<Block> door) {
        registerDoor(door.get());
    }

    public void registerDoor(Block door) {
        doorBlock((DoorBlock) door, sided(door, "_bottom"), sided(door, "_top"));
    }

    public void registerTrapdoor(RegistryObject<Block> trapdoor) {
        trapdoorBlock((TrapDoorBlock) trapdoor.get(), blockTexture(trapdoor.get()), true);
    }

    public void registerFence(RegistryObject<Block> fence, RegistryObject<Block> textureBlock) {
        fenceBlock((FenceBlock) fence.get(), blockTexture(textureBlock.get()));
    }

    public void registerFenceGate(RegistryObject<Block> gate, RegistryObject<Block> textureBlock) {
        fenceGateBlock((FenceGateBlock) gate.get(), blockTexture(textureBlock.get()));
    }

    public void registerButton(RegistryObject<Block> button, RegistryObject<Block> textureBlock) {
        buttonBlock((ButtonBlock) button.get(), blockTexture(textureBlock.get()));
    }

    public void registerPressurePlate(RegistryObject<Block> plate, RegistryObject<Block> textureBlock) {
        pressurePlateBlock((PressurePlateBlock) plate.get(), blockTexture(textureBlock.get()));
    }

    public void registerPillar(RegistryObject<Block> pillar) {
        axisBlock((RotatedPillarBlock) pillar.get(), blockTexture(pillar.get()), sided(pillar.get(), "_top"));
    }

    public ResourceLocation sided(Block block, String side) {
        return new ResourceLocation(id, "block/" + key(block).getPath() + side);
    }

    public void hangingSignBlock(Block signBlock, Block wallSignBlock, ResourceLocation texture) {
        ModelFile sign = models().sign(name(signBlock), texture);
        hangingSignBlock(signBlock, wallSignBlock, sign);
    }

    public void hangingSignBlock(Block signBlock, Block wallSignBlock, ModelFile sign) {
        simpleBlock(signBlock, sign);
        simpleBlock(wallSignBlock, sign);
    }

    public String name(Block block) {
        return key(block).getPath();
    }

    public ResourceLocation key(Block block) {
        return ForgeRegistries.BLOCKS.getKey(block);
    }

    public void plantBlock(Block block) {
        simpleBlock(block, models().cross(name(block), blockTexture(block)).renderType("cutout"));
        simpleItem(block);
    }

    public void plantBlock(Block block, ResourceLocation tex) {
        simpleBlock(block, models().cross(name(block), blockTexture(block)).renderType("cutout"));
        simpleItem(block, tex);
        ModItemModelProvider.entries.add(block.asItem());
    }

    public void simpleItem(Block block, ResourceLocation tex) {
        itemModels().withExistingParent(name(block), "item/generated").texture("layer0", tex);
        ModItemModelProvider.entries.add(block.asItem());
    }

    public void simpleItem(Block block, String id) {
        itemModels().withExistingParent(name(block), "item/generated").texture("layer0", modLoc("item/" + id));
        ModItemModelProvider.entries.add(block.asItem());
    }

    public void simpleItem(Block block) {
        itemModels().withExistingParent(name(block), "item/generated").texture("layer0", modLoc("item/" + name(block)));
        ModItemModelProvider.entries.add(block.asItem());
    }

    public void tintedPlantBlock(Block block) {
        ModelFile model = models().singleTexture(name(block), mcLoc("block/tinted_cross"), "cross", blockTexture(block)).renderType("cutout");
        getVariantBuilder(block).partialState().addModels(new ConfiguredModel(model));
        simpleBlockItem(block, model);
    }

    public void plantBlock(RegistryObject<Block> blockRegistryObject) {
        plantBlock(blockRegistryObject.get());
    }

    public void saplingBlock(Block block) {
        simpleBlock(block, models().cross(name(block), blockTexture(block)).renderType("cutout"));
    }

    public void saplingBlock(RegistryObject<Block> blockRegistryObject) {
        saplingBlock(blockRegistryObject.get());
    }

    public void leavesBlock(Block block) {
        simpleBlockWithItem(block, models().withExistingParent(name(block), mcLoc("minecraft:block/leaves")).texture("all", blockTexture(block)).renderType("cutout"));
    }

    public void leavesBlock(RegistryObject<Block> blockRegistryObject) {
        leavesBlock(blockRegistryObject.get());
    }

    public void randomRotatedBlock(Block block) {
        ModelFile model = cubeAll(block);
        getVariantBuilder(block).partialState().setModels(
                new ConfiguredModel(model, 0, 0, false),
                new ConfiguredModel(model, 0, 90, false),
                new ConfiguredModel(model, 0, 180, false),
                new ConfiguredModel(model, 0, 270, false)
        );

        simpleBlockItem(block, model);
    }

    public void horizontalBlockWithExistingModel(Block block) {
        ModelFile model = models().getExistingFile(new ResourceLocation(id, "block/" + name(block)));
        horizontalBlock(block, model);
    }

    public void existingModelBlock(Block block) {
        ModelFile model = models().getExistingFile(new ResourceLocation(id, "block/" + name(block)));
        simpleBlock(block, model);
    }

    public void blockItem(Block block) {
        simpleBlockItem(block, new ModelFile.UncheckedModelFile(id + ":block/" + key(block).getPath()));
    }

    public void blockWithItem(Block block) {
        simpleBlockWithItem(block, cubeAll(block));
    }

    public void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
}
