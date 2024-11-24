package com.idark.valoria.client.render.tile;

// todo move to lib
//public class ModChestRender extends ChestRenderer<ModChestBlockEntity> {
//
//    public static final ResourceLocation CHEST_SHEET = new ResourceLocation("textures/atlas/chest.png");
//
//    public ModChestRender(BlockEntityRendererProvider.Context pContext) {
//        super(pContext);
//    }
//
//    private static Material chestMaterial(String pChestName) {
//        return new Material(CHEST_SHEET, new ResourceLocation(Valoria.ID, "entity/chest/" + pChestName));
//    }
//
//    @Override
//    public @NotNull Material getMaterial(@NotNull ModChestBlockEntity tile, @NotNull ChestType type) {
//        Block block = tile.getBlockState().getBlock();
//        if (type.name().equals("SINGLE")) {
//            return chestMaterial(ForgeRegistries.BLOCKS.getKey(block).getPath());
//        } else {
//            return chestMaterial(ForgeRegistries.BLOCKS.getKey(block).getPath() + "_" + type.name().toLowerCase());
//        }
//    }
//}