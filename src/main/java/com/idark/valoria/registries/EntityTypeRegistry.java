package com.idark.valoria.registries;

import com.idark.valoria.*;
import com.idark.valoria.registries.entity.decoration.*;
import com.idark.valoria.registries.entity.living.*;
import com.idark.valoria.registries.entity.projectile.*;
import net.minecraft.resources.*;
import net.minecraft.world.entity.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.registries.*;

public class EntityTypeRegistry{
    public static DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Valoria.ID);

    public static final RegistryObject<EntityType<SwampWandererEntity>> SWAMP_WANDERER = ENTITY_TYPES.register("swamp_wanderer",
    () -> EntityType.Builder.of(SwampWandererEntity::new, MobCategory.MONSTER)
    .sized(0.6f, 1.95f)
    .clientTrackingRange(8)
    .build(new ResourceLocation(Valoria.ID, "swamp_wanderer").toString()));

    public static final RegistryObject<EntityType<Goblin>> GOBLIN = ENTITY_TYPES.register("goblin",
    () -> EntityType.Builder.of(Goblin::new, MobCategory.CREATURE)
    .sized(0.6f, 1.25f)
    .clientTrackingRange(8)
    .build(new ResourceLocation(Valoria.ID, "goblin").toString()));

    public static final RegistryObject<EntityType<Succubus>> SUCCUBUS = ENTITY_TYPES.register("succubus",
    () -> EntityType.Builder.of(Succubus::new, MobCategory.MONSTER)
    .sized(0.6f, 2.0f)
    .clientTrackingRange(8)
    .build(new ResourceLocation(Valoria.ID, "succubus").toString()));

    public static final RegistryObject<EntityType<DraugrEntity>> DRAUGR = ENTITY_TYPES.register("draugr",
    () -> EntityType.Builder.of(DraugrEntity::new, MobCategory.CREATURE)
    .sized(0.6f, 2.0f)
    .clientTrackingRange(8)
    .build(new ResourceLocation(Valoria.ID, "draugr").toString()));

    public static final RegistryObject<EntityType<ShadewoodSpider>> SHADEWOOD_SPIDER = ENTITY_TYPES.register("shadewood_spider",
    () -> EntityType.Builder.of(ShadewoodSpider::new, MobCategory.MONSTER)
    .sized(1.4f, 0.9f)
    .clientTrackingRange(8)
    .build(new ResourceLocation(Valoria.ID, "shadewood_spider").toString()));

    public static final RegistryObject<EntityType<NecromancerEntity>> NECROMANCER = ENTITY_TYPES.register("necromancer",
    () -> EntityType.Builder.of(NecromancerEntity::new, MobCategory.CREATURE)
    .sized(0.6f, 2.0f)
    .clientTrackingRange(8)
    .build(new ResourceLocation(Valoria.ID, "necromancer").toString()));

    public static final RegistryObject<EntityType<UndeadEntity>> UNDEAD = ENTITY_TYPES.register("undead",
    () -> EntityType.Builder.of(UndeadEntity::new, MobCategory.MONSTER)
    .sized(0.4f, 0.8f)
    .clientTrackingRange(8)
    .build(new ResourceLocation(Valoria.ID, "undead").toString()));

    public static final RegistryObject<EntityType<NecromancerFangs>> NECROMANCER_FANGS = ENTITY_TYPES.register("necromancer_fangs",
    () -> EntityType.Builder.<NecromancerFangs>of(NecromancerFangs::new, MobCategory.MISC)
    .sized(1, 1f)
    .clientTrackingRange(6)
    .updateInterval(2)
    .build(new ResourceLocation(Valoria.ID, "necromancer_fangs").toString()));

    public static final RegistryObject<EntityType<MannequinEntity>> MANNEQUIN = ENTITY_TYPES.register("mannequin",
    () -> EntityType.Builder.of(MannequinEntity::new, MobCategory.MISC)
    .sized(0.6f, 1.95f)
    .clientTrackingRange(4)
    .updateInterval(4) // funny
    .build(new ResourceLocation(Valoria.ID, "mannequin").toString()));

    public static final RegistryObject<EntityType<ThrowableBomb>> THROWABLE_BOMB = ENTITY_TYPES.register("throwable_bomb",
    () -> EntityType.Builder.<ThrowableBomb>of(ThrowableBomb::new, MobCategory.MISC)
    .sized(0.45f, 0.45f)
    .clientTrackingRange(4)
    .updateInterval(20)
    .build(new ResourceLocation(Valoria.ID, "throwable_bomb").toString()));

    public static final RegistryObject<EntityType<KunaiEntity>> KUNAI = ENTITY_TYPES.register("kunai",
    () -> EntityType.Builder.<KunaiEntity>of(KunaiEntity::new, MobCategory.MISC)
    .sized(0.35f, 0.35f)
    .clientTrackingRange(4)
    .updateInterval(20)
    .build(new ResourceLocation(Valoria.ID, "kunai").toString()));

    public static final RegistryObject<EntityType<ThrownSpearEntity>> SPEAR = ENTITY_TYPES.register("spear",
    () -> EntityType.Builder.<ThrownSpearEntity>of(ThrownSpearEntity::new, MobCategory.MISC)
    .sized(0.5f, 0.35f)
    .clientTrackingRange(4)
    .updateInterval(20)
    .build(new ResourceLocation(Valoria.ID, "spear").toString()));

    public static final RegistryObject<EntityType<PhantomArrow>> PHANTOM_ARROW = ENTITY_TYPES.register("phantom_arrow",
    () -> EntityType.Builder.<PhantomArrow>of(PhantomArrow::new, MobCategory.MISC)
    .sized(0.35f, 0.35f)
    .clientTrackingRange(4)
    .updateInterval(20)
    .build(new ResourceLocation(Valoria.ID, "phantom_arrow").toString()));

    public static final RegistryObject<EntityType<WickedArrow>> WICKED_ARROW = ENTITY_TYPES.register("wicked_arrow",
    () -> EntityType.Builder.<WickedArrow>of(WickedArrow::new, MobCategory.MISC)
    .sized(0.35f, 0.35f)
    .clientTrackingRange(4)
    .updateInterval(20)
    .build(new ResourceLocation(Valoria.ID, "wicked_arrow").toString()));

    public static final RegistryObject<EntityType<SoulArrow>> SOUL_ARROW = ENTITY_TYPES.register("soul_arrow",
    () -> EntityType.Builder.<SoulArrow>of(SoulArrow::new, MobCategory.MISC)
    .sized(0.35f, 0.35f)
    .clientTrackingRange(4)
    .updateInterval(20)
    .build(new ResourceLocation(Valoria.ID, "soul_arrow").toString()));

    public static final RegistryObject<EntityType<InfernalArrow>> INFERNAL_ARROW = ENTITY_TYPES.register("infernal_arrow",
    () -> EntityType.Builder.<InfernalArrow>of(InfernalArrow::new, MobCategory.MISC)
    .sized(0.35f, 0.35f)
    .clientTrackingRange(4)
    .updateInterval(20)
    .build(new ResourceLocation(Valoria.ID, "infernal_arrow").toString()));

    public static final RegistryObject<EntityType<MeatBlockEntity>> MEAT = ENTITY_TYPES.register("meat",
    () -> EntityType.Builder.<MeatBlockEntity>of(MeatBlockEntity::new, MobCategory.MISC)
    .sized(1, 1f)
    .clientTrackingRange(4)
    .updateInterval(20)
    .build(new ResourceLocation(Valoria.ID, "meat").toString()));

    public static final RegistryObject<EntityType<SpectralBladeEntity>> SPECTRAL_BLADE = ENTITY_TYPES.register("spectral_blade",
    () -> EntityType.Builder.<SpectralBladeEntity>of(SpectralBladeEntity::new, MobCategory.MISC)
    .sized(0.35f, 0.35f)
    .clientTrackingRange(4)
    .updateInterval(20)
    .build(new ResourceLocation(Valoria.ID, "spectral_blade").toString()));

    public static final RegistryObject<EntityType<CustomBoatEntity>> BOAT = ENTITY_TYPES.register("boat",
    () -> EntityType.Builder.<CustomBoatEntity>of(CustomBoatEntity::new, MobCategory.MISC)
    .sized(1.375f, 0.5625f)
    .build(new ResourceLocation(Valoria.ID, "valoria_boat").toString()));

    public static final RegistryObject<EntityType<CustomChestBoatEntity>> CHEST_BOAT = ENTITY_TYPES.register("chest_boat",
    () -> EntityType.Builder.<CustomChestBoatEntity>of(CustomChestBoatEntity::new, MobCategory.MISC)
    .sized(1.375f, 0.5625f)
    .build(new ResourceLocation(Valoria.ID, "valoria_chest_boat").toString()));


    public static void register(IEventBus eventBus){
        ENTITY_TYPES.register(eventBus);
    }
}