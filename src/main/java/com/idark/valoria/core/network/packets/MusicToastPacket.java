package com.idark.valoria.core.network.packets;

import com.idark.valoria.Valoria;
import com.idark.valoria.client.ui.toast.MusicToast;
import com.idark.valoria.client.ui.toast.PageToast;
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

public class MusicToastPacket {
    private final UUID uuid;
    public String music;
    public String author;

    public MusicToastPacket(UUID uuid, String music, String author){
        this.uuid = uuid;
        this.author = author;
        this.music = music;
    }

    public MusicToastPacket(Player entity, String music, String author){
        this.uuid = entity.getUUID();
        this.author = author;
        this.music = music;
    }

    public static void encode(MusicToastPacket object, FriendlyByteBuf buffer){
        buffer.writeUUID(object.uuid);
        buffer.writeUtf(object.music);
        buffer.writeUtf(object.author);
    }

    public static MusicToastPacket decode(FriendlyByteBuf buffer){
        return new MusicToastPacket(buffer.readUUID(), buffer.readUtf(), buffer.readUtf());
    }

    public static void handle(MusicToastPacket packet, Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(() -> {
            assert ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT;

            Level world = Valoria.proxy.getLevel();
            Player player = world.getPlayerByUUID(packet.uuid);
            if(player != null){
                toast(packet);
            }
        });

        ctx.get().setPacketHandled(true);
    }

    @OnlyIn(Dist.CLIENT)
    public static void toast(MusicToastPacket packet){
        Minecraft.getInstance().getToasts().addToast(new MusicToast(packet.music, packet.author));
    }
}