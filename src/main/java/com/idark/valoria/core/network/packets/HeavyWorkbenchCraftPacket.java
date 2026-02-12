package com.idark.valoria.core.network.packets;

import com.idark.valoria.client.ui.menus.*;
import com.idark.valoria.core.network.*;
import com.idark.valoria.registries.item.recipe.*;
import net.minecraft.network.*;
import net.minecraft.resources.*;
import net.minecraft.server.level.*;

public class HeavyWorkbenchCraftPacket extends RateLimitedPacket{
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

    public void execute(ServerPlayer player){
        if (player.containerMenu instanceof HeavyWorkbenchMenu heavyMenu) {
            heavyMenu.tryCraftRecipe(player, this.recipeId);
        }
    }
}
