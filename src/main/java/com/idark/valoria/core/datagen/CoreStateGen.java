package com.idark.valoria.core.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public abstract class CoreStateGen extends BlockStateProvider{
    public String id;

    public CoreStateGen(PackOutput output, String modid, ExistingFileHelper exFileHelper){
        super(output, modid, exFileHelper);
        this.id = modid;
    }

    public void registerKit(Block stairs, Block slab, Block texture){
        stairsBlock((StairBlock)stairs, blockTexture(texture));
        registerSlab(slab, texture);
        blockItem(stairs);
    }

    public void wallBlock(WallBlock block, ResourceLocation texture){
        wallBlock(block, key(block).toString(), texture);
        itemModels().getBuilder(key(block).getPath()).parent(models().wallInventory(key(block) + "_inventory", texture));
    }

    public void registerKit(Block block, Block stairs, Block slab, Block wall){
        blockWithItem(block);
        wallBlock((WallBlock)wall, blockTexture(block));
        registerKit(stairs, slab, block);
    }

    public void registerSlab(Block slab, Block texture){
        slabBlock((SlabBlock)slab, blockTexture(texture), blockTexture(texture));
        blockItem(slab);
    }

    public void registerDoor(Block door){
        doorBlock((DoorBlock)door, sided(door, "_bottom"), sided(door, "_top"));
    }

    public ResourceLocation sided(Block block, String side){
        return new ResourceLocation(id, ":block/" + key(block).getPath() + side);
    }

    public void saplingBlock(RegistryObject<Block> blockRegistryObject){
        simpleBlock(blockRegistryObject.get(), models().cross(key(blockRegistryObject.get()).getPath(), blockTexture(blockRegistryObject.get())).renderType("cutout"));
    }

    public void hangingSignBlock(Block signBlock, Block wallSignBlock, ResourceLocation texture){
        ModelFile sign = models().sign(name(signBlock), texture);
        hangingSignBlock(signBlock, wallSignBlock, sign);
    }

    public void hangingSignBlock(Block signBlock, Block wallSignBlock, ModelFile sign){
        simpleBlock(signBlock, sign);
        simpleBlock(wallSignBlock, sign);
    }

    public String name(Block block){
        return key(block).getPath();
    }

    public ResourceLocation key(Block block){
        return ForgeRegistries.BLOCKS.getKey(block);
    }

    public void leavesBlock(RegistryObject<Block> blockRegistryObject){
        simpleBlockWithItem(blockRegistryObject.get(), models().singleTexture(key(blockRegistryObject.get()).getPath(), new ResourceLocation("minecraft:block/leaves"), "all", blockTexture(blockRegistryObject.get())).renderType("cutout"));
    }

    public void blockItem(Block block){
        simpleBlockItem(block, new ModelFile.UncheckedModelFile(id + ":block/" + key(block).getPath()));
    }

    public void blockWithItem(Block block){
        simpleBlockWithItem(block, cubeAll(block));
    }

    public void blockWithItem(RegistryObject<Block> blockRegistryObject){
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
}
