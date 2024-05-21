package com.idark.valoria.core.network.packets;

import com.idark.valoria.*;
import com.idark.valoria.client.gui.toast.*;
import net.minecraft.client.*;
import net.minecraft.network.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.level.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.network.*;

import java.util.*;
import java.util.function.*;

public class PageToastPacket{
    private final UUID uuid;
    private final boolean unlock;

    public PageToastPacket(UUID uuid, boolean pUnlock){
        this.uuid = uuid;
        unlock = pUnlock;
    }

    public PageToastPacket(Player entity, boolean pUnlock){
        this.uuid = entity.getUUID();
        unlock = pUnlock;
    }

    public static void encode(PageToastPacket object, FriendlyByteBuf buffer){
        buffer.writeUUID(object.uuid);
        buffer.writeBoolean(object.unlock);
    }

    public static PageToastPacket decode(FriendlyByteBuf buffer){
        return new PageToastPacket(buffer.readUUID(), buffer.readBoolean());
    }

    public static void handle(PageToastPacket packet, Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(() -> {
            assert ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT;

            Level world = Valoria.proxy.getWorld();
            Player player = world.getPlayerByUUID(packet.uuid);
            if(player != null){
                toast(packet);
            }
        });

        ctx.get().setPacketHandled(true);
    }

    @OnlyIn(Dist.CLIENT)
    public static void toast(PageToastPacket packet){
        Minecraft.getInstance().getToasts().addToast(new PageToast(packet.unlock));
    }
}