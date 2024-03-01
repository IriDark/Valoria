package com.idark.valoria.network;

import com.idark.valoria.registries.sounds.CooldownSoundInstance;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class CooldownSoundPacket {
    private final float posX;
    private final float posY;
    private final float posZ;
    public static final CooldownSoundInstance COOLDOWN_SOUND = new CooldownSoundInstance(Minecraft.getInstance().player);

    public CooldownSoundPacket(float posX, float posY, float posZ) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
    }

    public static CooldownSoundPacket decode(FriendlyByteBuf buf) {
        return new CooldownSoundPacket(buf.readFloat(), buf.readFloat(), buf.readFloat());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeFloat(posX);
        buf.writeFloat(posY);
        buf.writeFloat(posZ);
    }

    public static void handle(CooldownSoundPacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isClient()) {
            ctx.get().enqueueWork(() -> {
                if (!Minecraft.getInstance().getSoundManager().isActive(COOLDOWN_SOUND)) {
                    Minecraft.getInstance().getSoundManager().play(COOLDOWN_SOUND);
                }

                ctx.get().setPacketHandled(true);
            });
        }
    }
}
