package com.idark.valoria.tileentity;

import com.idark.valoria.Valoria;
import com.idark.valoria.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModTileEntities {

    public static DeferredRegister<BlockEntityType<?>> TILE_ENTITIES =
        DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Valoria.MOD_ID);

    public static final RegistryObject<BlockEntityType<ModSignTileEntity>> SIGN_TILE_ENTITIES = TILE_ENTITIES.register("sign", () -> BlockEntityType.Builder.of(ModSignTileEntity::new, ModBlocks.SHADEWOOD_SIGN.get(), ModBlocks.SHADEWOOD_WALL_SIGN.get()).build(null));
    public static final RegistryObject<BlockEntityType<ModHangingSignTileEntity>> HANGING_SIGN_TILE_ENTITIES = TILE_ENTITIES.register("hanging_sign", () -> BlockEntityType.Builder.of(ModHangingSignTileEntity::new, ModBlocks.SHADEWOOD_HANGING_SIGN.get(), ModBlocks.SHADEWOOD_WALL_HANGING_SIGN.get()).build(null));
    public static final RegistryObject<BlockEntityType<PedestalTileEntity>> PEDESTAL_TILE_ENTITY = TILE_ENTITIES.register("pedestal_entity", () -> BlockEntityType.Builder.of(PedestalTileEntity::new, ModBlocks.SKULLY_PEDESTAL.get(), ModBlocks.ELEGANT_PEDESTAL.get()).build(null));
    public static final RegistryObject<BlockEntityType<CrusherTileEntity>> CRUSHER_TILE_ENTITY = TILE_ENTITIES.register("crusher_entity", () -> BlockEntityType.Builder.of(CrusherTileEntity::new, ModBlocks.STONE_CRUSHER.get()).build(null));

    public static void register(IEventBus eventBus) {
        TILE_ENTITIES.register(eventBus);
    }
}