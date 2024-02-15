package com.idark.valoria.network;

import com.idark.valoria.Valoria;
import com.idark.valoria.capability.IUnlockable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class UnlockableUpdatePacket {
    UUID uuid;
    CompoundTag tag;

    public UnlockableUpdatePacket(UUID uuid, CompoundTag tag) {
        this.uuid = uuid;
        this.tag = tag;
    }

    public UnlockableUpdatePacket(Player entity) {
        this.uuid = entity.getUUID();
        entity.getCapability(IUnlockable.INSTANCE, null).ifPresent((k) -> {
            if (k instanceof INBTSerializable) {
                this.tag = ((INBTSerializable<CompoundTag>) k).serializeNBT();
            }
        });
    }

    public static void encode(UnlockableUpdatePacket object, FriendlyByteBuf buffer) {
        buffer.writeUUID(object.uuid);
        buffer.writeNbt(object.tag);
    }

    public static UnlockableUpdatePacket decode(FriendlyByteBuf buffer) {
        return new UnlockableUpdatePacket(buffer.readUUID(), buffer.readNbt());
    }

    public static void handle(UnlockableUpdatePacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            assert ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT;

            Level world = Valoria.proxy.getWorld();
            Player player = world.getPlayerByUUID(packet.uuid);
            if (player != null) {
                player.getCapability(IUnlockable.INSTANCE, null).ifPresent((k) -> {
                    if (k instanceof INBTSerializable) {
                        ((INBTSerializable<CompoundTag>) k).deserializeNBT(packet.tag);
                    }
                });
            }
        });
        ctx.get().setPacketHandled(true);
    }
}