package com.idark.valoria.core.network.packets;

import com.idark.valoria.client.ui.menus.*;
import com.idark.valoria.registries.item.recipe.*;
import net.minecraft.network.*;
import net.minecraft.resources.*;
import net.minecraft.server.level.*;
import net.minecraftforge.network.NetworkEvent.*;

import java.util.function.*;

public class AlchemyUpgradeTryPacket{
    public ResourceLocation recipeId;
    public AlchemyUpgradeTryPacket(AlchemyUpgradeRecipe recipe){
        this.recipeId = recipe.getId();
    }

    public AlchemyUpgradeTryPacket(ResourceLocation recipe){
        this.recipeId = recipe;
    }

    public static void encode(AlchemyUpgradeTryPacket object, FriendlyByteBuf buffer){
        buffer.writeResourceLocation(object.recipeId);
    }

    public static AlchemyUpgradeTryPacket decode(FriendlyByteBuf buffer){
        return new AlchemyUpgradeTryPacket(buffer.readResourceLocation());
    }

    public static void handle(AlchemyUpgradeTryPacket packet, Supplier<Context> ctx){
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if(player == null) return;
            if (player.containerMenu instanceof AlchemyStationMenu heavyMenu) {
                heavyMenu.tryUpgrade(player, packet.recipeId);
            }
        });

        ctx.get().setPacketHandled(true);
    }
}
