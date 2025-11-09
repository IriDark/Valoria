package com.idark.valoria.core.network.packets;

import com.idark.valoria.client.ui.menus.*;
import com.idark.valoria.registries.item.recipe.*;
import net.minecraft.network.*;
import net.minecraft.resources.*;
import net.minecraft.server.level.*;
import net.minecraftforge.network.NetworkEvent.*;

import java.util.function.*;

public class AlchemyCraftPacket{
    public ResourceLocation recipeId;
    public AlchemyCraftPacket(AlchemyRecipe recipe){
        this.recipeId = recipe.getId();
    }

    public AlchemyCraftPacket(ResourceLocation recipe){
        this.recipeId = recipe;
    }

    public static void encode(AlchemyCraftPacket object, FriendlyByteBuf buffer){
        buffer.writeResourceLocation(object.recipeId);
    }

    public static AlchemyCraftPacket decode(FriendlyByteBuf buffer){
        return new AlchemyCraftPacket(buffer.readResourceLocation());
    }

    public static void handle(AlchemyCraftPacket packet, Supplier<Context> ctx){
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if(player == null) return;
            if (player.containerMenu instanceof AlchemyStationMenu heavyMenu) {
                heavyMenu.tryCraftRecipe(player, packet.recipeId);
            }
        });

        ctx.get().setPacketHandled(true);
    }
}
