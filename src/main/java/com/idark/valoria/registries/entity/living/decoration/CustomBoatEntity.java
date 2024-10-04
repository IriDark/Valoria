package com.idark.valoria.registries.entity.living.decoration;

import com.idark.valoria.registries.*;
import net.minecraft.nbt.*;
import net.minecraft.network.protocol.*;
import net.minecraft.network.protocol.game.*;
import net.minecraft.network.syncher.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.vehicle.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraftforge.network.*;
import org.jetbrains.annotations.*;

public class CustomBoatEntity extends Boat {
    private static final EntityDataAccessor<Integer> BOAT_TYPE = SynchedEntityData.defineId(CustomBoatEntity.class, EntityDataSerializers.INT);

    public CustomBoatEntity(EntityType<? extends Boat> type, Level level) {
        super(type, level);
        this.blocksBuilding = true;
    }

    public CustomBoatEntity(Level level, double x, double y, double z) {
        this(EntityTypeRegistry.BOAT.get(), level);
        this.setPos(x, y, z);
        this.xo = x;
        this.yo = y;
        this.zo = z;
    }

    public CustomBoatEntity.Type getCustomBoatEntityType() {
        return CustomBoatEntity.Type.byId(this.getEntityData().get(BOAT_TYPE));
    }

    public void setCustomBoatEntityType(CustomBoatEntity.Type boatType) {
        this.getEntityData().set(BOAT_TYPE, boatType.ordinal());
    }

    @Override
    public @NotNull Item getDropItem() {
        return switch (this.getCustomBoatEntityType()) {
            case SHADEWOOD -> ItemsRegistry.shadewoodBoat.get();
            case ELDRITCH -> ItemsRegistry.eldritchBoat.get();
        };
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(BOAT_TYPE, Type.SHADEWOOD.ordinal());
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        tag.putString("Type", this.getCustomBoatEntityType().getName());
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        if (tag.contains("Type", 8)) {
            this.setCustomBoatEntityType(CustomBoatEntity.Type.getTypeFromString(tag.getString("Type")));
        }
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public enum Type {
        SHADEWOOD(BlockRegistry.SHADEWOOD_PLANKS.get(), "shadewood"),
        ELDRITCH(BlockRegistry.ELDRITCH_PLANKS.get(), "eldritch");

        private final String name;
        private final Block block;

        Type(Block block, String name) {
            this.name = name;
            this.block = block;
        }

        public static CustomBoatEntity.Type byId(int id) {
            CustomBoatEntity.Type[] types = values();
            if (id < 0 || id >= types.length) {
                id = 0;
            }

            return types[id];
        }

        public static CustomBoatEntity.Type getTypeFromString(String nameIn) {
            CustomBoatEntity.Type[] types = values();

            for (Type type : types) {
                if (type.getName().equals(nameIn)) {
                    return type;
                }
            }

            return types[0];
        }

        public String getName() {
            return this.name;
        }

        public Block asPlank() {
            return this.block;
        }

        public String toString() {
            return this.name;
        }
    }
}