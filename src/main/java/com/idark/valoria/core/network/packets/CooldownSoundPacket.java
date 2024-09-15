package com.idark.valoria.core.network.packets;

import com.idark.valoria.ValoriaClient;
import com.idark.valoria.client.sounds.CooldownSoundInstance;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

//todo somehow redo this
public class CooldownSoundPacket {
    private final double posX;
    private final double posY;
    private final double posZ;

    public CooldownSoundPacket(double posX, double posY, double posZ) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
    }

    public static CooldownSoundPacket decode(FriendlyByteBuf buf) {
        return new CooldownSoundPacket(buf.readDouble(), buf.readDouble(), buf.readDouble());
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

    public void encode(FriendlyByteBuf buf) {
        buf.writeDouble(posX);
        buf.writeDouble(posY);
        buf.writeDouble(posZ);
    }
}