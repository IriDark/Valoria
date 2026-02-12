package com.idark.valoria.core.network.packets;

import com.idark.valoria.client.ui.menus.*;
import com.idark.valoria.core.network.*;
import com.idark.valoria.registries.item.recipe.*;
import net.minecraft.network.*;
import net.minecraft.resources.*;
import net.minecraft.server.level.*;

public class AlchemyCraftPacket extends RateLimitedPacket{
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
