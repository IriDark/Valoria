package com.idark.valoria.registries;

import com.idark.valoria.*;
import com.idark.valoria.registries.block.entity.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.entity.BlockEntityType.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.registries.*;

public class BlockEntitiesRegistry{
    public static DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Valoria.ID);
    public static final RegistryObject<BlockEntityType<ModSignBlockEntity>> SIGN_BLOCK_ENTITIES = BLOCK_ENTITIES.register("sign", () -> Builder.of(ModSignBlockEntity::new, BlockRegistry.shadewoodSign.get(), BlockRegistry.shadewoodWallSign.get(), BlockRegistry.eldritchSign.get(), BlockRegistry.eldritchWallSign.get()).build(null));
    public static final RegistryObject<BlockEntityType<ModHangingSignBlockEntity>> HANGING_SIGN_BLOCK_ENTITIES = BLOCK_ENTITIES.register("hanging_sign", () -> Builder.of(ModHangingSignBlockEntity::new, BlockRegistry.shadewoodHangingSign.get(), BlockRegistry.shadewoodWallHangingSign.get(), BlockRegistry.eldritchHangingSign.get(), BlockRegistry.eldritchWallHangingSign.get()).build(null));
    public static final RegistryObject<BlockEntityType<PedestalBlockEntity>> PEDESTAL_BLOCK_ENTITY = BLOCK_ENTITIES.register("pedestal_entity", () -> Builder.of(PedestalBlockEntity::new, BlockRegistry.elegantPedestal.get()).build(null));
    public static final RegistryObject<BlockEntityType<CrypticAltarBlockEntity>> CRYPTIC_ALTAR = BLOCK_ENTITIES.register("cryptic_altar", () -> Builder.of(CrypticAltarBlockEntity::new, BlockRegistry.crypticAltar.get()).build(null));
    public static final RegistryObject<BlockEntityType<WickedAltarBlockEntity>> WICKED_ALTAR = BLOCK_ENTITIES.register("wicked_altar", () -> Builder.of(WickedAltarBlockEntity::new, BlockRegistry.wickedAltar.get()).build(null));
    public static final RegistryObject<BlockEntityType<CrusherBlockEntity>> CRUSHER_BLOCK_ENTITY = BLOCK_ENTITIES.register("crusher_entity", () -> Builder.of(CrusherBlockEntity::new, BlockRegistry.stoneCrusher.get()).build(null));
    public static final RegistryObject<BlockEntityType<CrushableBlockEntity>> CRUSHABLE_BLOCK_ENTITY = BLOCK_ENTITIES.register("crushable_entity", () -> Builder.of(CrushableBlockEntity::new, BlockRegistry.suspiciousTombstone.get(), BlockRegistry.suspiciousIce.get()).build(null));
    public static final RegistryObject<BlockEntityType<KegBlockEntity>> KEG_BLOCK_ENTITY = BLOCK_ENTITIES.register("keg_entity", () -> Builder.of(KegBlockEntity::new, BlockRegistry.keg.get()).build(null));
    public static final RegistryObject<BlockEntityType<JewelryBlockEntity>> JEWELRY_BLOCK_ENTITY = BLOCK_ENTITIES.register("jewelry_entity", () -> Builder.of(JewelryBlockEntity::new, BlockRegistry.jewelerTable.get()).build(null));
    public static final RegistryObject<BlockEntityType<ManipulatorBlockEntity>> MANIPULATOR_BLOCK_ENTITY = BLOCK_ENTITIES.register("manipulator_entity", () -> Builder.of(ManipulatorBlockEntity::new, BlockRegistry.elementalManipulator.get()).build(null));
    public static final RegistryObject<BlockEntityType<ValoriaPortalBlockEntity>> VALORIA_PORTAL_BLOCK_ENTITY = BLOCK_ENTITIES.register("valoria_portal", () -> Builder.of(ValoriaPortalBlockEntity::new, BlockRegistry.valoriaPortal.get()).build(null));
    public static final RegistryObject<BlockEntityType<FleshCystBlockEntity>> FLESH_CYST = BLOCK_ENTITIES.register("flesh_cyst", () -> Builder.of(FleshCystBlockEntity::new, BlockRegistry.fleshCyst.get()).build(null));
    public static final RegistryObject<BlockEntityType<KilnBlockEntity>> KILN = BLOCK_ENTITIES.register("kiln", () -> Builder.of(KilnBlockEntity::new, BlockRegistry.kiln.get()).build(null));

    public static void register(IEventBus eventBus){
        BLOCK_ENTITIES.register(eventBus);
    }
}