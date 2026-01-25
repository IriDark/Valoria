package com.idark.valoria.registries.entity.living.elemental;

import com.idark.valoria.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.entity.ai.attacks.*;
import net.minecraft.nbt.*;
import net.minecraft.network.syncher.*;
import net.minecraft.resources.*;
import net.minecraft.util.*;
import net.minecraft.util.ByIdMap.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.pathfinder.*;
import org.jetbrains.annotations.*;

import java.util.function.*;

public class NatureGolem extends AbstractElementalGolem{
    private static final EntityDataAccessor<Integer> TYPE = SynchedEntityData.defineId(NatureGolem.class, EntityDataSerializers.INT);

    public NatureGolem(EntityType<? extends AbstractElementalGolem> type, Level pLevel){
        super(type, pLevel);
        this.xpReward = 5;
        this.getNavigation().setCanFloat(false);
        this.setPathfindingMalus(BlockPathTypes.LAVA, 8.0F);
        this.setPathfindingMalus(BlockPathTypes.DAMAGE_OTHER, 8.0F);
        this.setPathfindingMalus(BlockPathTypes.POWDER_SNOW, 8.0F);

        this.selector.addAttack(new GolemMeleeAttack(this, 1, 2, 0, 10, 20));
        this.selector.addAttack(new GolemMeleeSlapAttack(this, 1, 2, 0, 10, 40));
        this.selector.addAttack(new GolemStompAttack(this, 4, 2, 0, 20, 60));
        this.selector.addAttack(new GolemGroundPunchAttack(this, 1, 4, 0, 20, 120));
    }

    public NatureGolem(Level pLevel){
        this(EntityTypeRegistry.NATURE_GOLEM.get(), pLevel);
    }

    @Override
    public @Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag){
        if (pDataTag == null || !pDataTag.contains("Variant")) {
            this.setVariant(Variant.byId(this.random.nextInt(Variant.values().length)));
        }

        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    public void setVariant(Variant variant) {
        this.entityData.set(TYPE, variant.getId());
    }

    public Variant getVariant() {
        return Variant.byId(this.entityData.get(TYPE));
    }

    public enum Variant implements StringRepresentable{
        DEFAULT(0, "default", new ResourceLocation(Valoria.ID, "textures/entity/nature_golem.png")),
        MOSSY_0(1, "mossy_0", new ResourceLocation(Valoria.ID, "textures/entity/nature_golem_moss_0.png")),
        MOSSY_1(2, "mossy_1", new ResourceLocation(Valoria.ID, "textures/entity/nature_golem_moss_1.png"));

        private static final IntFunction<Variant> BY_ID = ByIdMap.continuous(Variant::getId, values(), OutOfBoundsStrategy.ZERO);
        public final ResourceLocation texture;
        public final String name;
        public final int id;

        Variant(int id, String name, ResourceLocation texture){
            this.id = id;
            this.name = name;
            this.texture = texture;
        }

        public ResourceLocation texture() {
            return texture;
        }

        @Override
        public String getSerializedName() { return name; }

        public String getString(){
            return name;
        }

        public int getId(){
            return id;
        }

        public static Variant byId(int pId){
            return BY_ID.apply(pId);
        }
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(TYPE, 0);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound){
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("Variant", this.getVariant().getId());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound){
        super.readAdditionalSaveData(pCompound);
        this.setVariant(Variant.byId(pCompound.getInt("Variant")));
    }
}
