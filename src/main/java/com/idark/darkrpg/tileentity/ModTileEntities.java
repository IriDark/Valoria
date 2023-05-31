package com.idark.darkrpg.tileentity;

import com.idark.darkrpg.DarkRPG;
import com.idark.darkrpg.block.ModBlocks;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTileEntities {

    public static DeferredRegister<TileEntityType<?>> TILE_ENTITIES =
        DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, DarkRPG.MOD_ID);

    public static final RegistryObject<TileEntityType<ModSignTileEntity>> SIGN_TILE_ENTITIES = TILE_ENTITIES.register("shadewood_sign", () -> TileEntityType.Builder.create(ModSignTileEntity::new, ModBlocks.SHADEWOOD_SIGN.get(), ModBlocks.SHADEWOOD_WALL_SIGN.get()).build(null));
    public static final RegistryObject<TileEntityType<PedestalTileEntity>> PEDESTAL_TILE_ENTITY = TILE_ENTITIES.register("pedestal_entity", () -> TileEntityType.Builder.create(PedestalTileEntity::new, ModBlocks.SKULLY_PEDESTAL.get(), ModBlocks.ELEGANT_PEDESTAL.get()).build(null));
    public static final RegistryObject<TileEntityType<CrusherTileEntity>> CRUSHER_TILE_ENTITY = TILE_ENTITIES.register("crusher_entity", () -> TileEntityType.Builder.create(CrusherTileEntity::new, ModBlocks.STONE_CRUSHER.get()).build(null));

    public static void register(IEventBus eventBus) {
        TILE_ENTITIES.register(eventBus);
    }
}