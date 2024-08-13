package com.idark.valoria.registries;

import com.idark.valoria.*;
import com.idark.valoria.registries.block.entity.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.entity.BlockEntityType.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.registries.*;

public class BlockEntitiesRegistry{
    public static DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Valoria.ID);

    public static final RegistryObject<BlockEntityType<ModSignBlockEntity>> SIGN_BLOCK_ENTITIES = BLOCK_ENTITIES.register("sign", () -> BlockEntityType.Builder.of(ModSignBlockEntity::new, BlockRegistry.SHADEWOOD_SIGN.get(), BlockRegistry.SHADEWOOD_WALL_SIGN.get(), BlockRegistry.ELDRITCH_SIGN.get(), BlockRegistry.ELDRITCH_WALL_SIGN.get()).build(null));
    public static final RegistryObject<BlockEntityType<ModHangingSignBlockEntity>> HANGING_SIGN_BLOCK_ENTITIES = BLOCK_ENTITIES.register("hanging_sign", () -> BlockEntityType.Builder.of(ModHangingSignBlockEntity::new, BlockRegistry.SHADEWOOD_HANGING_SIGN.get(), BlockRegistry.SHADEWOOD_WALL_HANGING_SIGN.get(), BlockRegistry.ELDRITCH_HANGING_SIGN.get(), BlockRegistry.ELDRITCH_WALL_HANGING_SIGN.get()).build(null));
    public static final RegistryObject<BlockEntityType<PedestalBlockEntity>> PEDESTAL_BLOCK_ENTITY = BLOCK_ENTITIES.register("pedestal_entity", () -> BlockEntityType.Builder.of(PedestalBlockEntity::new, BlockRegistry.ELEGANT_PEDESTAL.get()).build(null));
    public static final RegistryObject<BlockEntityType<CrusherBlockEntity>> CRUSHER_BLOCK_ENTITY = BLOCK_ENTITIES.register("crusher_entity", () -> BlockEntityType.Builder.of(CrusherBlockEntity::new, BlockRegistry.STONE_CRUSHER.get()).build(null));
    public static final RegistryObject<BlockEntityType<CrushableBlockEntity>> CRUSHABLE_BLOCK_ENTITY = BLOCK_ENTITIES.register("crushable_entity", () -> BlockEntityType.Builder.of(CrushableBlockEntity::new, BlockRegistry.SUSPICIOUS_TOMBSTONE.get(), BlockRegistry.SUSPICIOUS_ICE.get()).build(null));
    public static final RegistryObject<BlockEntityType<KegBlockEntity>> KEG_BLOCK_ENTITY = BLOCK_ENTITIES.register("keg_entity", () -> BlockEntityType.Builder.of(KegBlockEntity::new, BlockRegistry.KEG.get()).build(null));
    public static final RegistryObject<BlockEntityType<JewelryBlockEntity>> JEWELRY_BLOCK_ENTITY = BLOCK_ENTITIES.register("jewelry_entity", () -> BlockEntityType.Builder.of(JewelryBlockEntity::new, BlockRegistry.JEWELER_TABLE.get()).build(null));
    public static final RegistryObject<BlockEntityType<ManipulatorBlockEntity>> MANIPULATOR_BLOCK_ENTITY = BLOCK_ENTITIES.register("manipulator_entity", () -> BlockEntityType.Builder.of(ManipulatorBlockEntity::new, BlockRegistry.ELEMENTAL_MANIPULATOR.get()).build(null));
    public static final RegistryObject<BlockEntityType<ModChestBlockEntity>> CHEST_BLOCK_ENTITY = BLOCK_ENTITIES.register("mod_chest", () -> Builder.of(ModChestBlockEntity::new, BlockRegistry.SHADEWOOD_CHEST.get(), BlockRegistry.ELDRITCH_CHEST.get()).build(null));
    public static final RegistryObject<BlockEntityType<ModTrappedChestBlockEntity>> TRAPPED_CHEST_BLOCK_ENTITY = BLOCK_ENTITIES.register("mod_trapped_chest", () -> Builder.of(ModTrappedChestBlockEntity::new, BlockRegistry.TRAPPED_SHADEWOOD_CHEST.get(), BlockRegistry.TRAPPED_ELDRITCH_CHEST.get()).build(null));

    public static void register(IEventBus eventBus){
        BLOCK_ENTITIES.register(eventBus);
    }
}