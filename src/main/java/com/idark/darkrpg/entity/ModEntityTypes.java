package com.idark.darkrpg.entity;

import com.idark.darkrpg.DarkRPG;
import com.idark.darkrpg.entity.custom.*;
import com.idark.darkrpg.entity.projectile.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntityTypes {
    public static DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, DarkRPG.MOD_ID);

	/*
    public static final RegistryObject<EntityType<SwampWandererEntity>> SWAMP_WANDERER = ENTITY_TYPES.register("swamp_wanderer",
        () -> EntityType.Builder.of(SwampWandererEntity::new, MobCategory.MONSTER)
        .sized(1f, 2.3f) // Hitbox Size
        .build(new ResourceLocation(DarkRPG.MOD_ID, "swamp_wanderer").toString()));
 	*/

	public static final RegistryObject<EntityType<GoblinEntity>> GOBLIN = ENTITY_TYPES.register("goblin",
        () -> EntityType.Builder.of(GoblinEntity::new, MobCategory.CREATURE)
        .sized(0.5f, 1.3f)
        .build(new ResourceLocation(DarkRPG.MOD_ID, "goblin").toString()));

	public static final RegistryObject<EntityType<MannequinEntity>> MANNEQUIN = ENTITY_TYPES.register("mannequin",
		() -> EntityType.Builder.of(MannequinEntity::new, MobCategory.CREATURE)
		.sized(1f, 2f)
		.build(new ResourceLocation(DarkRPG.MOD_ID, "mannequin").toString()));

	public static final RegistryObject<EntityType<KunaiEntity>> KUNAI = ENTITY_TYPES.register("kunai",
        () -> EntityType.Builder.<KunaiEntity>of(KunaiEntity::new, MobCategory.MISC)
        .sized(0.35f, 0.35f)
        .build(new ResourceLocation(DarkRPG.MOD_ID, "kunai").toString()));

	public static final RegistryObject<EntityType<SpectralBladeEntity>> SPECTRAL_BLADE = ENTITY_TYPES.register("spectral_blade",
		() -> EntityType.Builder.<SpectralBladeEntity>of(SpectralBladeEntity::new, MobCategory.MISC)
		.sized(0.35f, 0.35f)
		.build(new ResourceLocation(DarkRPG.MOD_ID, "spectral_blade").toString()));

	public static final RegistryObject<EntityType<PoisonedKunaiEntity>> POISONED_KUNAI = ENTITY_TYPES.register("poisoned_kunai",
        () -> EntityType.Builder.<PoisonedKunaiEntity>of(PoisonedKunaiEntity::new, MobCategory.MISC)
        .sized(0.35f, 0.35f)
        .build(new ResourceLocation(DarkRPG.MOD_ID, "poisoned_kunai").toString()));
	
	public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}