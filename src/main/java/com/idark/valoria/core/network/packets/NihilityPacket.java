package com.idark.valoria.core.network.packets;

import com.idark.valoria.core.capability.*;
import net.minecraft.client.*;
import net.minecraft.client.player.*;
import net.minecraft.network.*;
import net.minecraft.world.entity.*;
import net.minecraftforge.network.NetworkEvent.*;

import javax.annotation.*;
import java.util.function.*;

public class NihilityPacket {
    private final float max;
    private final float nihilityLevel;
    public NihilityPacket(float max, float amount){
        this.max = max;
        this.nihilityLevel = amount;
    }

    public NihilityPacket(INihilityLevel nihility, @Nullable LivingEntity entity) {
        this.max = nihility.getMaxAmount(entity);
        this.nihilityLevel = nihility.getAmount();
    }

    public static void encode(NihilityPacket object, FriendlyByteBuf buffer){
        buffer.writeFloat(object.max);
        buffer.writeFloat(object.nihilityLevel);
    }

    public static NihilityPacket decode(FriendlyByteBuf buffer){
        return new NihilityPacket(buffer.readFloat(), buffer.readFloat());
    }

    public void handle(Supplier<Context> ctx) {
        ctx.get().enqueueWork(() -> {
            LocalPlayer player = Minecraft.getInstance().player;
            if(player == null) return;
            player.getCapability(NihilityLevelProvider.INSTANCE).ifPresent(nihility -> {
                nihility.setMaxAmount(this.max);
                nihility.setAmount(this.nihilityLevel);
            });
        });

        ctx.get().setPacketHandled(true);
    }
}