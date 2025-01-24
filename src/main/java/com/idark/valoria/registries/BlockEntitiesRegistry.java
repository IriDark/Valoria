package com.idark.valoria.registries;

import com.idark.valoria.Valoria;
import com.idark.valoria.registries.block.entity.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityType.Builder;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockEntitiesRegistry{
    public static DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Valoria.ID);
    public static final RegistryObject<BlockEntityType<ModSignBlockEntity>> SIGN_BLOCK_ENTITIES = BLOCK_ENTITIES.register("sign", () -> BlockEntityType.Builder.of(ModSignBlockEntity::new, BlockRegistry.shadewoodSign.get(), BlockRegistry.shadewoodWallSign.get(), BlockRegistry.eldritchSign.get(), BlockRegistry.eldritchWallSign.get()).build(null));
    public static final RegistryObject<BlockEntityType<ModHangingSignBlockEntity>> HANGING_SIGN_BLOCK_ENTITIES = BLOCK_ENTITIES.register("hanging_sign", () -> BlockEntityType.Builder.of(ModHangingSignBlockEntity::new, BlockRegistry.shadewoodHangingSign.get(), BlockRegistry.shadewoodWallHangingSign.get(), BlockRegistry.eldritchHangingSign.get(), BlockRegistry.eldritchWallHangingSign.get()).build(null));
    public static final RegistryObject<BlockEntityType<PedestalBlockEntity>> PEDESTAL_BLOCK_ENTITY = BLOCK_ENTITIES.register("pedestal_entity", () -> BlockEntityType.Builder.of(PedestalBlockEntity::new, BlockRegistry.elegantPedestal.get()).build(null));
    public static final RegistryObject<BlockEntityType<CrypticAltarBlockEntity>> CRYPTIC_ALTAR = BLOCK_ENTITIES.register("cryptic_altar", () -> BlockEntityType.Builder.of(CrypticAltarBlockEntity::new, BlockRegistry.crypticAltar.get()).build(null));
    public static final RegistryObject<BlockEntityType<CrusherBlockEntity>> CRUSHER_BLOCK_ENTITY = BLOCK_ENTITIES.register("crusher_entity", () -> BlockEntityType.Builder.of(CrusherBlockEntity::new, BlockRegistry.stoneCrusher.get()).build(null));
    public static final RegistryObject<BlockEntityType<CrushableBlockEntity>> CRUSHABLE_BLOCK_ENTITY = BLOCK_ENTITIES.register("crushable_entity", () -> BlockEntityType.Builder.of(CrushableBlockEntity::new, BlockRegistry.suspiciousTombstone.get(), BlockRegistry.suspiciousIce.get()).build(null));
    public static final RegistryObject<BlockEntityType<KegBlockEntity>> KEG_BLOCK_ENTITY = BLOCK_ENTITIES.register("keg_entity", () -> BlockEntityType.Builder.of(KegBlockEntity::new, BlockRegistry.keg.get()).build(null));
    public static final RegistryObject<BlockEntityType<JewelryBlockEntity>> JEWELRY_BLOCK_ENTITY = BLOCK_ENTITIES.register("jewelry_entity", () -> BlockEntityType.Builder.of(JewelryBlockEntity::new, BlockRegistry.jewelerTable.get()).build(null));
    public static final RegistryObject<BlockEntityType<ManipulatorBlockEntity>> MANIPULATOR_BLOCK_ENTITY = BLOCK_ENTITIES.register("manipulator_entity", () -> BlockEntityType.Builder.of(ManipulatorBlockEntity::new, BlockRegistry.elementalManipulator.get()).build(null));
    //    public static final RegistryObject<BlockEntityType<ModChestBlockEntity>> CHEST_BLOCK_ENTITY = BLOCK_ENTITIES.register("mod_chest", () -> Builder.of(ModChestBlockEntity::new, BlockRegistry.SHADEWOOD_CHEST.get(), BlockRegistry.ELDRITCH_CHEST.get()).build(null));
//    public static final RegistryObject<BlockEntityType<ModTrappedChestBlockEntity>> TRAPPED_CHEST_BLOCK_ENTITY = BLOCK_ENTITIES.register("mod_trapped_chest", () -> Builder.of(ModTrappedChestBlockEntity::new, BlockRegistry.TRAPPED_SHADEWOOD_CHEST.get(), BlockRegistry.TRAPPED_ELDRITCH_CHEST.get()).build(null));
    public static final RegistryObject<BlockEntityType<ValoriaPortalBlockEntity>> VALORIA_PORTAL_BLOCK_ENTITY = BLOCK_ENTITIES.register("valoria_portal", () -> Builder.of(ValoriaPortalBlockEntity::new, BlockRegistry.valoriaPortal.get()).build(null));
    public static final RegistryObject<BlockEntityType<FleshCystBlockEntity>> FLESH_CYST = BLOCK_ENTITIES.register("flesh_cyst", () -> Builder.of(FleshCystBlockEntity::new, BlockRegistry.fleshCyst.get()).build(null));

    public static void register(IEventBus eventBus){
        BLOCK_ENTITIES.register(eventBus);
    }
}