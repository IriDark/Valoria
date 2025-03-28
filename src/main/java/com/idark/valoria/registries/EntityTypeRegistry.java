package com.idark.valoria.registries;

import com.idark.valoria.*;
import com.idark.valoria.registries.entity.living.*;
import com.idark.valoria.registries.entity.living.boss.*;
import com.idark.valoria.registries.entity.living.boss.dryador.*;
import com.idark.valoria.registries.entity.living.decoration.*;
import com.idark.valoria.registries.entity.living.elemental.*;
import com.idark.valoria.registries.entity.living.minions.*;
import com.idark.valoria.registries.entity.projectile.*;
import net.minecraft.resources.*;
import net.minecraft.world.entity.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.registries.*;
import pro.komaru.tridot.common.registry.entity.misc.*;

public class EntityTypeRegistry{
    public static DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Valoria.ID);
    public static final RegistryObject<EntityType<ScourgeEntity>> SCOURGE = register("scourge", EntityType.Builder.of(ScourgeEntity::new, MobCategory.MONSTER).sized(0.6f, 1.95f).clientTrackingRange(8));
    public static final RegistryObject<EntityType<SwampWandererEntity>> SWAMP_WANDERER = register("swamp_wanderer", EntityType.Builder.of(SwampWandererEntity::new, MobCategory.MONSTER).sized(0.6f, 1.95f).clientTrackingRange(8));
    public static final RegistryObject<EntityType<Goblin>> GOBLIN = register("goblin", EntityType.Builder.of(Goblin::new, MobCategory.CREATURE).sized(0.6f, 1.25f).clientTrackingRange(8));
    public static final RegistryObject<EntityType<Devil>> DEVIL = register("devil", EntityType.Builder.of(Devil::new, MobCategory.MONSTER).sized(0.6f, 2.0f).clientTrackingRange(8));
    public static final RegistryObject<EntityType<Troll>> TROLL = register("troll", EntityType.Builder.of(Troll::new, MobCategory.MONSTER).sized(0.6f, 2.0f).clientTrackingRange(8));
    public static final RegistryObject<EntityType<Troll>> CORRUPTED_TROLL = register("corrupted_troll", EntityType.Builder.of(Troll::new, MobCategory.MONSTER).sized(0.6f, 2.0f).clientTrackingRange(8));
    public static final RegistryObject<EntityType<HauntedMerchant>> HAUNTED_MERCHANT = register("haunted_merchant", EntityType.Builder.of(HauntedMerchant::new, MobCategory.MISC).sized(0.75f, 2.35f).clientTrackingRange(8));
    public static final RegistryObject<EntityType<DraugrEntity>> DRAUGR = register("draugr", EntityType.Builder.of(DraugrEntity::new, MobCategory.MONSTER).sized(0.6f, 2.0f).clientTrackingRange(8));
    public static final RegistryObject<EntityType<ShadewoodSpider>> SHADEWOOD_SPIDER = register("shadewood_spider", EntityType.Builder.of(ShadewoodSpider::new, MobCategory.MONSTER).sized(1.4f, 0.9f).clientTrackingRange(8));
    public static final RegistryObject<EntityType<MannequinEntity>> MANNEQUIN = register("mannequin", EntityType.Builder.of(MannequinEntity::new, MobCategory.MISC).sized(0.6f, 1.95f).clientTrackingRange(4).updateInterval(4));
    public static final RegistryObject<EntityType<SorcererEntity>> SORCERER = register("sorcerer", EntityType.Builder.<SorcererEntity>of(SorcererEntity::new, MobCategory.MONSTER).sized(0.6f, 2.0f).clientTrackingRange(8));
    public static final RegistryObject<EntityType<Ent>> ENT = register("ent", EntityType.Builder.of(Ent::new, MobCategory.MONSTER).sized(0.8f, 2.0f).clientTrackingRange(8));
    public static final RegistryObject<EntityType<MaggotEntity>> MAGGOT = register("maggot", EntityType.Builder.<MaggotEntity>of(MaggotEntity::new, MobCategory.MONSTER).sized(0.4F, 0.3F).clientTrackingRange(8));

    // Minions
    public static final RegistryObject<EntityType<UndeadEntity>> UNDEAD = register("undead", EntityType.Builder.of(UndeadEntity::new, MobCategory.MONSTER).sized(0.4f, 0.8f).clientTrackingRange(8));
    public static final RegistryObject<EntityType<FleshSentinel>> FLESH_SENTINEL = register("flesh_sentinel", EntityType.Builder.of(FleshSentinel::new, MobCategory.MONSTER).sized(0.6f, 0.6f).clientTrackingRange(8));
    public static final RegistryObject<EntityType<WickedShield>> WICKED_SHIELD = register("wicked_shield", EntityType.Builder.<WickedShield>of(WickedShield::new, MobCategory.MISC).sized(1f, 1.75f).clientTrackingRange(8));
    public static final RegistryObject<EntityType<CrystalEntity>> CRYSTAL = register("crystal", EntityType.Builder.of(CrystalEntity::new, MobCategory.MONSTER).sized(1f, 1f).clientTrackingRange(8));

    // Projectiles
    public static final RegistryObject<EntityType<WickedArrow>> WICKED_ARROW = register("wicked_arrow", EntityType.Builder.<WickedArrow>of(WickedArrow::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20));
    public static final RegistryObject<EntityType<SoulArrow>> SOUL_ARROW = register("soul_arrow", EntityType.Builder.<SoulArrow>of(SoulArrow::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20));
    public static final RegistryObject<EntityType<PhantomArrow>> PHANTOM_ARROW = register("phantom_arrow", EntityType.Builder.<PhantomArrow>of(PhantomArrow::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20));
    public static final RegistryObject<EntityType<InfernalArrow>> INFERNAL_ARROW = register("infernal_arrow", EntityType.Builder.of(InfernalArrow::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20));
    public static final RegistryObject<EntityType<ThrowableBomb>> THROWABLE_BOMB = register("throwable_bomb", EntityType.Builder.<ThrowableBomb>of(ThrowableBomb::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20));
    public static final RegistryObject<EntityType<MeatBlockEntity>> MEAT = register("meat", EntityType.Builder.<MeatBlockEntity>of(MeatBlockEntity::new, MobCategory.MISC).sized(1, 1f).clientTrackingRange(4).updateInterval(20));
    public static final RegistryObject<EntityType<SpectralBladeEntity>> SPECTRAL_BLADE = register("spectral_blade", EntityType.Builder.<SpectralBladeEntity>of(SpectralBladeEntity::new, MobCategory.MISC).sized(0.35f, 0.35f).clientTrackingRange(4).updateInterval(20));
    public static final RegistryObject<EntityType<ThrownSpearEntity>> SPEAR = register("spear", EntityType.Builder.<ThrownSpearEntity>of(ThrownSpearEntity::new, MobCategory.MISC).sized(0.5f, 0.35f).clientTrackingRange(4).updateInterval(20));
    public static final RegistryObject<EntityType<KunaiEntity>> KUNAI = register("kunai", EntityType.Builder.<KunaiEntity>of(KunaiEntity::new, MobCategory.MISC).sized(0.35f, 0.35f).clientTrackingRange(4).updateInterval(20));
    public static final RegistryObject<EntityType<SpellProjectile>> SPELL = register("spell", EntityType.Builder.<SpellProjectile>of(SpellProjectile::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20));
    public static final RegistryObject<EntityType<CrystalShard>> CRYSTAL_SHARD = register("crystal_shard", EntityType.Builder.<CrystalShard>of(CrystalShard::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20));
    public static final RegistryObject<EntityType<PyratiteShard>> PYRATITE_SHARD = register("pyratitel_shard", EntityType.Builder.<PyratiteShard>of(PyratiteShard::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20));

    public static final RegistryObject<EntityType<LaserEntity>> LASER = register("laser", EntityType.Builder.<LaserEntity>of(LaserEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20));

    public static final RegistryObject<EntityType<Devourer>> DEVOURER = register("devourer", EntityType.Builder.<Devourer>of(Devourer::new, MobCategory.MISC).sized(1, 1f).clientTrackingRange(6).updateInterval(2));
    public static final RegistryObject<EntityType<CrystalSpikes>> CRYSTAL_SPIKES = register("crystal_spikes", EntityType.Builder.<CrystalSpikes>of(CrystalSpikes::new, MobCategory.MISC).sized(1, 1f).clientTrackingRange(6).updateInterval(2));

    // Boss
    public static final RegistryObject<EntityType<NecromancerEntity>> NECROMANCER = register("necromancer", EntityType.Builder.of(NecromancerEntity::new, MobCategory.MONSTER).sized(0.6f, 2.0f).clientTrackingRange(8));
    public static final RegistryObject<EntityType<WickedCrystal>> WICKED_CRYSTAL = register("wicked_crystal", EntityType.Builder.of(WickedCrystal::new, MobCategory.MONSTER).sized(1.4f, 5.0f).clientTrackingRange(8));
    public static final RegistryObject<EntityType<DryadorEntity>> DRYADOR = register("dryador", EntityType.Builder.of(DryadorEntity::new, MobCategory.MONSTER).sized(1.2f, 3.5f).clientTrackingRange(8));

    public static final RegistryObject<EntityType<CustomBoatEntity>> SHADEWOOD_BOAT = register("shadewood_boat", EntityType.Builder.<CustomBoatEntity>of((t, l) -> (new CustomBoatEntity(t, l, ItemsRegistry.shadewoodBoat, false)), MobCategory.MISC).sized(1.375f, 0.5625f));
    public static final RegistryObject<EntityType<CustomChestBoatEntity>> SHADEWOOD_CHEST_BOAT = register("shadewood_chest_boat", EntityType.Builder.<CustomChestBoatEntity>of((t, l) -> (new CustomChestBoatEntity(t, l, ItemsRegistry.shadewoodChestBoat, false)), MobCategory.MISC).sized(1.375f, 0.5625f));
    public static final RegistryObject<EntityType<CustomBoatEntity>> ELDRITCH_BOAT = register("eldritch_boat", EntityType.Builder.<CustomBoatEntity>of((t, l) -> (new CustomBoatEntity(t, l, ItemsRegistry.eldritchBoat, false)), MobCategory.MISC).sized(1.375f, 0.5625f));
    public static final RegistryObject<EntityType<CustomChestBoatEntity>> ELDRITCH_CHEST_BOAT = register("eldritch_chest_boat", EntityType.Builder.<CustomChestBoatEntity>of((t, l) -> (new CustomChestBoatEntity(t, l, ItemsRegistry.eldritchChestBoat, false)), MobCategory.MISC).sized(1.375f, 0.5625f));

    public static <T extends Entity> RegistryObject<EntityType<T>> register(String name, EntityType.Builder<T> builder){
        return ENTITY_TYPES.register(name, () -> builder.build(new ResourceLocation(Valoria.ID, name).toString()));
    }

    public static void register(IEventBus eventBus){
        ENTITY_TYPES.register(eventBus);
    }
}