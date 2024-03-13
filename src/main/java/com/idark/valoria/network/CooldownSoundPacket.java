package com.idark.valoria.network;

import com.idark.valoria.ValoriaClient;
import com.idark.valoria.registries.sounds.CooldownSoundInstance;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class CooldownSoundPacket {
    private final float posX;
    private final float posY;
    private final float posZ;

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

    @OnlyIn(Dist.CLIENT)
    public static void playSound() {
        if (ValoriaClient.COOLDOWN_SOUND.player == null) {
            ValoriaClient.COOLDOWN_SOUND = new CooldownSoundInstance(Minecraft.getInstance().player);
        }

        if (!Minecraft.getInstance().getSoundManager().isActive(ValoriaClient.COOLDOWN_SOUND)) {
            Minecraft.getInstance().getSoundManager().play(ValoriaClient.COOLDOWN_SOUND);
        }
    }

    public static void handle(CooldownSoundPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            assert ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT;
            playSound();
        });

        ctx.get().setPacketHandled(true);
    }
}