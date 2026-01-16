package com.idark.valoria.core.network.packets;

import com.idark.valoria.core.capability.*;
import net.minecraft.client.*;
import net.minecraft.client.player.*;
import net.minecraft.network.*;
import net.minecraft.world.entity.*;
import net.minecraftforge.network.NetworkEvent.*;

import javax.annotation.*;
import java.util.function.*;

public class MagmaPacket{
    private final float max;
    private final float magmaLevel;

    public MagmaPacket(float max, float amount){
        this.max = max;
        this.magmaLevel = amount;
    }

    public MagmaPacket(IMagmaLevel magma, @Nullable LivingEntity entity) {
        this.max = magma.getMaxAmount(entity);
        this.magmaLevel = magma.getAmount();
    }

    public static void encode(MagmaPacket object, FriendlyByteBuf buffer){
        buffer.writeFloat(object.max);
        buffer.writeFloat(object.magmaLevel);
    }

    public static MagmaPacket decode(FriendlyByteBuf buffer){
        return new MagmaPacket(buffer.readFloat(), buffer.readFloat());
    }

    public void handle(Supplier<Context> ctx) {
        ctx.get().enqueueWork(() -> {
            LocalPlayer player = Minecraft.getInstance().player;
            if(player == null) return;
            player.getCapability(MagmaLevelProvider.INSTANCE).ifPresent(m -> {
                m.setMaxAmount(this.max);
                m.setAmount(this.magmaLevel);
            });
        });

        ctx.get().setPacketHandled(true);
    }
}