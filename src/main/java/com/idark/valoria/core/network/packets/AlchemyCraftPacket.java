package com.idark.valoria.core.network.packets;

import com.idark.valoria.*;
import com.idark.valoria.client.ui.menus.*;
import com.idark.valoria.core.network.*;
import com.idark.valoria.registries.item.recipe.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.network.protocol.common.custom.*;
import net.minecraft.resources.*;
import net.minecraft.server.level.*;

public class AlchemyCraftPacket extends RateLimitedPacket{
    public static final CustomPacketPayload.Type<AlchemyCraftPacket> TYPE = new CustomPacketPayload.Type<>(Valoria.loc("alchemy_craft_packet"));
    public static final StreamCodec<RegistryFriendlyByteBuf, AlchemyCraftPacket> STREAM_CODEC = StreamCodec.of((buf, msg) -> AlchemyCraftPacket.encode(msg, buf), AlchemyCraftPacket::decode);

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type(){
        return TYPE;
    }
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

    public void execute(ServerPlayer player){
        if (player.containerMenu instanceof AlchemyStationMenu heavyMenu) {
            heavyMenu.tryCraftRecipe(player, this.recipeId);
        }
    }
}
