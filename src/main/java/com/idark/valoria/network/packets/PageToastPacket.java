package com.idark.valoria.network.packets;

import com.idark.valoria.Valoria;
import com.idark.valoria.client.gui.toast.PageToast;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class PageToastPacket {
    private final UUID uuid;
    private final boolean unlock;

    public PageToastPacket(UUID uuid, boolean pUnlock) {
        this.uuid = uuid;
        unlock = pUnlock;
    }

    public PageToastPacket(Player entity, boolean pUnlock) {
        this.uuid = entity.getUUID();
        unlock = pUnlock;
    }

    public static void encode(PageToastPacket object, FriendlyByteBuf buffer) {
        buffer.writeUUID(object.uuid);
        buffer.writeBoolean(object.unlock);
    }

    public static PageToastPacket decode(FriendlyByteBuf buffer) {
        return new PageToastPacket(buffer.readUUID(), buffer.readBoolean());
    }

    public static void handle(PageToastPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            assert ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT;

            Level world = Valoria.proxy.getWorld();
            Player player = world.getPlayerByUUID(packet.uuid);
            if (player != null) {
                toast(packet);
            }
        });

        ctx.get().setPacketHandled(true);
    }

    @OnlyIn(Dist.CLIENT)
    public static void toast(PageToastPacket packet) {
        Minecraft.getInstance().getToasts().addToast(new PageToast(packet.unlock));
    }
}