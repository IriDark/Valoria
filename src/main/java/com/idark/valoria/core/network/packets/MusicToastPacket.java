package com.idark.valoria.core.network.packets;

import com.idark.valoria.*;
import com.idark.valoria.client.ui.toast.*;
import com.idark.valoria.core.config.*;
import net.minecraft.client.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.network.protocol.common.custom.*;
import net.minecraft.sounds.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.level.*;
import net.neoforged.api.distmarker.*;
import net.neoforged.neoforge.network.handling.*;

import java.util.*;

public class MusicToastPacket implements CustomPacketPayload{
    public static final CustomPacketPayload.Type<MusicToastPacket> TYPE = new CustomPacketPayload.Type<>(Valoria.loc("music_toast_packet"));
    public static final StreamCodec<RegistryFriendlyByteBuf, MusicToastPacket> STREAM_CODEC = StreamCodec.of((buf, msg) -> MusicToastPacket.encode(msg, buf), MusicToastPacket::decode);

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type(){
        return TYPE;
    }
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

    public MusicToastPacket(Player entity, SoundEvent event){
        this.uuid = entity.getUUID();
        this.author = event.getLocation().toLanguageKey() + ".author";
        this.music = event.getLocation().toLanguageKey() + ".name";
    }

    public static void encode(MusicToastPacket object, FriendlyByteBuf buffer){
        buffer.writeUUID(object.uuid);
        buffer.writeUtf(object.music);
        buffer.writeUtf(object.author);
    }

    public static MusicToastPacket decode(FriendlyByteBuf buffer){
        return new MusicToastPacket(buffer.readUUID(), buffer.readUtf(), buffer.readUtf());
    }

    public static void handle(MusicToastPacket packet, IPayloadContext ctx){
        ctx.enqueueWork(() -> {

            Level world = Valoria.proxy.getLevel();
            Player player = world.getPlayerByUUID(packet.uuid);
            if(player != null){
                toast(packet);
            }
        });
    }

    @OnlyIn(Dist.CLIENT)
    public static void toast(MusicToastPacket packet){
        if (ClientConfig.SHOW_TOASTS.get()){
            Minecraft.getInstance().getToasts().addToast(new MusicToast(packet.music, packet.author));
        }
    }
}