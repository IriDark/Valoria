package com.idark.valoria.registries.world.block.entity;

import com.idark.valoria.Valoria;
import com.idark.valoria.registries.world.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {

    public static DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Valoria.MOD_ID);

    public static final RegistryObject<BlockEntityType<ModSignBlockEntity>> SIGN_BLOCK_ENTITIES = BLOCK_ENTITIES.register("sign", () -> BlockEntityType.Builder.of(ModSignBlockEntity::new, ModBlocks.SHADEWOOD_SIGN.get(), ModBlocks.SHADEWOOD_WALL_SIGN.get()).build(null));
    public static final RegistryObject<BlockEntityType<ModHangingSignBlockEntity>> HANGING_SIGN_BLOCK_ENTITIES = BLOCK_ENTITIES.register("hanging_sign", () -> BlockEntityType.Builder.of(ModHangingSignBlockEntity::new, ModBlocks.SHADEWOOD_HANGING_SIGN.get(), ModBlocks.SHADEWOOD_WALL_HANGING_SIGN.get()).build(null));
    public static final RegistryObject<BlockEntityType<PedestalBlockEntity>> PEDESTAL_BLOCK_ENTITY = BLOCK_ENTITIES.register("pedestal_entity", () -> BlockEntityType.Builder.of(PedestalBlockEntity::new, ModBlocks.ELEGANT_PEDESTAL.get()).build(null));
    public static final RegistryObject<BlockEntityType<CrusherBlockEntity>> CRUSHER_BLOCK_ENTITY = BLOCK_ENTITIES.register("crusher_entity", () -> BlockEntityType.Builder.of(CrusherBlockEntity::new, ModBlocks.STONE_CRUSHER.get()).build(null));
    public static final RegistryObject<BlockEntityType<CrushableBlockEntity>> CRUSHABLE_BLOCK_ENTITY = BLOCK_ENTITIES.register("crushable_entity", () -> BlockEntityType.Builder.of(CrushableBlockEntity::new, ModBlocks.SUSPICIOUS_TOMBSTONE.get(), ModBlocks.SUSPICIOUS_ICE.get()).build(null));
    public static final RegistryObject<BlockEntityType<KegBlockEntity>> KEG_BLOCK_ENTITY = BLOCK_ENTITIES.register("keg_entity", () -> BlockEntityType.Builder.of(KegBlockEntity::new, ModBlocks.KEG.get()).build(null));
    public static final RegistryObject<BlockEntityType<JewelryBlockEntity>> JEWELRY_BLOCK_ENTITY = BLOCK_ENTITIES.register("jewelry_entity", () -> BlockEntityType.Builder.of(JewelryBlockEntity::new, ModBlocks.JEWELER_TABLE.get()).build(null));
    public static final RegistryObject<BlockEntityType<ManipulatorBlockEntity>> MANIPULATOR_BLOCK_ENTITY = BLOCK_ENTITIES.register("manipulator_entity", () -> BlockEntityType.Builder.of(ManipulatorBlockEntity::new, ModBlocks.ELEMENTAL_MANIPULATOR.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}