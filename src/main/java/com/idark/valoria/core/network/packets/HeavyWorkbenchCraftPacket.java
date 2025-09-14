package com.idark.valoria.core.network.packets;

import com.idark.valoria.client.ui.menus.*;
import com.idark.valoria.registries.item.recipe.*;
import net.minecraft.network.*;
import net.minecraft.resources.*;
import net.minecraft.server.level.*;
import net.minecraftforge.network.NetworkEvent.*;

import java.util.function.*;

public class HeavyWorkbenchCraftPacket{
    public ResourceLocation recipeId;
    public HeavyWorkbenchCraftPacket(WorkbenchRecipe recipe){
        this.recipeId = recipe.getId();
    }

    public HeavyWorkbenchCraftPacket(ResourceLocation recipe){
        this.recipeId = recipe;
    }

    public static void encode(HeavyWorkbenchCraftPacket object, FriendlyByteBuf buffer){
        buffer.writeResourceLocation(object.recipeId);
    }

    public static HeavyWorkbenchCraftPacket decode(FriendlyByteBuf buffer){
        return new HeavyWorkbenchCraftPacket(buffer.readResourceLocation());
    }

    public static void handle(HeavyWorkbenchCraftPacket packet, Supplier<Context> ctx){
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if(player == null) return;
            if (player.containerMenu instanceof HeavyWorkbenchMenu heavyMenu) {
                heavyMenu.tryCraftRecipe(player, packet.recipeId);
            }
        });

        ctx.get().setPacketHandled(true);
    }
}
