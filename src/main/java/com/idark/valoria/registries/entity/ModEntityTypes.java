package com.idark.valoria.registries.entity;

import com.idark.valoria.Valoria;
import com.idark.valoria.registries.entity.decoration.CustomBoatEntity;
import com.idark.valoria.registries.entity.decoration.CustomChestBoatEntity;
import com.idark.valoria.registries.entity.decoration.MannequinEntity;
import com.idark.valoria.registries.entity.living.*;
import com.idark.valoria.registries.entity.projectile.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntityTypes {
    public static DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Valoria.ID);

    public static final RegistryObject<EntityType<SwampWandererEntity>> SWAMP_WANDERER = ENTITY_TYPES.register("swamp_wanderer",
            () -> EntityType.Builder.of(SwampWandererEntity::new, MobCategory.MONSTER)
                    .sized(0.6f, 1.95f)
                    .build(new ResourceLocation(Valoria.ID, "swamp_wanderer").toString()));

    public static final RegistryObject<EntityType<GoblinEntity>> GOBLIN = ENTITY_TYPES.register("goblin",
            () -> EntityType.Builder.of(GoblinEntity::new, MobCategory.CREATURE)
                    .sized(0.6f, 1.25f)
                    .build(new ResourceLocation(Valoria.ID, "goblin").toString()));

    public static final RegistryObject<EntityType<DraugrEntity>> DRAUGR = ENTITY_TYPES.register("draugr",
            () -> EntityType.Builder.of(DraugrEntity::new, MobCategory.CREATURE)
                    .sized(0.6f, 2.0f)
                    .build(new ResourceLocation(Valoria.ID, "draugr").toString()));

    public static final RegistryObject<EntityType<NecromancerEntity>> NECROMANCER = ENTITY_TYPES.register("necromancer",
            () -> EntityType.Builder.of(NecromancerEntity::new, MobCategory.CREATURE)
                    .sized(0.6f, 2.0f)
                    .build(new ResourceLocation(Valoria.ID, "necromancer").toString()));

    public static final RegistryObject<EntityType<UndeadEntity>> UNDEAD = ENTITY_TYPES.register("undead",
            () -> EntityType.Builder.of(UndeadEntity::new, MobCategory.MONSTER)
                    .sized(0.4f, 0.8f)
                    .build(new ResourceLocation(Valoria.ID, "undead").toString()));

    public static final RegistryObject<EntityType<NecromancerFangs>> NECROMANCER_FANGS = ENTITY_TYPES.register("necromancer_fangs",
            () -> EntityType.Builder.<NecromancerFangs>of(NecromancerFangs::new, MobCategory.MISC)
                    .sized(1, 1f)
                    .build(new ResourceLocation(Valoria.ID, "necromancer_fangs").toString()));

    public static final RegistryObject<EntityType<MannequinEntity>> MANNEQUIN = ENTITY_TYPES.register("mannequin",
            () -> EntityType.Builder.of(MannequinEntity::new, MobCategory.MISC)
                    .sized(0.6f, 1.95f)
                    .build(new ResourceLocation(Valoria.ID, "mannequin").toString()));

    public static final RegistryObject<EntityType<KunaiEntity>> KUNAI = ENTITY_TYPES.register("kunai",
            () -> EntityType.Builder.<KunaiEntity>of(KunaiEntity::new, MobCategory.MISC)
                    .sized(0.35f, 0.35f)
                    .build(new ResourceLocation(Valoria.ID, "kunai").toString()));

    public static final RegistryObject<EntityType<PhantomArrow>> PHANTOM_ARROW = ENTITY_TYPES.register("phantom_arrow",
            () -> EntityType.Builder.<PhantomArrow>of(PhantomArrow::new, MobCategory.MISC)
                    .sized(0.35f, 0.35f)
                    .build(new ResourceLocation(Valoria.ID, "phantom_arrow").toString()));
    public static final RegistryObject<EntityType<WickedArrow>> WICKED_ARROW = ENTITY_TYPES.register("wicked_arrow",
            () -> EntityType.Builder.<WickedArrow>of(WickedArrow::new, MobCategory.MISC)
                    .sized(0.35f, 0.35f)
                    .build(new ResourceLocation(Valoria.ID, "wicked_arrow").toString()));
    public static final RegistryObject<EntityType<SoulArrow>> SOUL_ARROW = ENTITY_TYPES.register("soul_arrow",
            () -> EntityType.Builder.<SoulArrow>of(SoulArrow::new, MobCategory.MISC)
                    .sized(0.35f, 0.35f)
                    .build(new ResourceLocation(Valoria.ID, "soul_arrow").toString()));

    public static final RegistryObject<EntityType<InfernalArrow>> INFERNAL_ARROW = ENTITY_TYPES.register("infernal_arrow",
            () -> EntityType.Builder.<InfernalArrow>of(InfernalArrow::new, MobCategory.MISC)
                    .sized(0.35f, 0.35f)
                    .build(new ResourceLocation(Valoria.ID, "infernal_arrow").toString()));

    public static final RegistryObject<EntityType<MeatBlockEntity>> MEAT = ENTITY_TYPES.register("meat",
            () -> EntityType.Builder.<MeatBlockEntity>of(MeatBlockEntity::new, MobCategory.MISC)
                    .sized(1, 1f)
                    .build(new ResourceLocation(Valoria.ID, "meat").toString()));

    public static final RegistryObject<EntityType<SpectralBladeEntity>> SPECTRAL_BLADE = ENTITY_TYPES.register("spectral_blade",
            () -> EntityType.Builder.<SpectralBladeEntity>of(SpectralBladeEntity::new, MobCategory.MISC)
                    .sized(0.35f, 0.35f)
                    .build(new ResourceLocation(Valoria.ID, "spectral_blade").toString()));

    public static final RegistryObject<EntityType<PoisonedKunaiEntity>> POISONED_KUNAI = ENTITY_TYPES.register("poisoned_kunai",
            () -> EntityType.Builder.<PoisonedKunaiEntity>of(PoisonedKunaiEntity::new, MobCategory.MISC)
                    .sized(0.35f, 0.35f)
                    .build(new ResourceLocation(Valoria.ID, "poisoned_kunai").toString()));

    public static final RegistryObject<EntityType<CustomBoatEntity>> BOAT = ENTITY_TYPES.register("boat",
            () -> EntityType.Builder.<CustomBoatEntity>of(CustomBoatEntity::new, MobCategory.MISC)
                    .sized(1.375f, 0.5625f)
                    .build(new ResourceLocation(Valoria.ID, "shadewood_boat").toString()));
    public static final RegistryObject<EntityType<CustomChestBoatEntity>> CHEST_BOAT = ENTITY_TYPES.register("chest_boat",
            () -> EntityType.Builder.<CustomChestBoatEntity>of(CustomChestBoatEntity::new, MobCategory.MISC)
                    .sized(1.375f, 0.5625f)
                    .build(new ResourceLocation(Valoria.ID, "shadewood_chest_boat").toString()));


    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}