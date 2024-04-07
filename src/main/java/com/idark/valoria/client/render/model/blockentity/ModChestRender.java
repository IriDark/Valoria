package com.idark.valoria.client.render.model.blockentity;

import com.idark.valoria.Valoria;
import com.idark.valoria.registries.block.entity.ModChestBlockEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.ChestRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

public class ModChestRender extends ChestRenderer<ModChestBlockEntity> {

    public static final ResourceLocation CHEST_SHEET = new ResourceLocation("textures/atlas/chest.png");

    public ModChestRender(BlockEntityRendererProvider.Context pContext) {
        super(pContext);
    }

    private static Material chestMaterial(String pChestName) {
        return new Material(CHEST_SHEET, new ResourceLocation(Valoria.MOD_ID,"entity/chest/" + pChestName));
    }

    @Override
    public @NotNull Material getMaterial(@NotNull ModChestBlockEntity tile, @NotNull ChestType type) {
        Block block = tile.getBlockState().getBlock();
        if (type.name().equals("SINGLE")) {
            return chestMaterial(ForgeRegistries.BLOCKS.getKey(block).getPath());
        } else {
            return chestMaterial(ForgeRegistries.BLOCKS.getKey(block).getPath() + "_" + type.name().toLowerCase());
        }
    }
}
