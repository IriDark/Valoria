package com.idark.valoria.core.network.packets;

import com.idark.valoria.client.ui.menus.*;
import com.idark.valoria.core.network.*;
import com.idark.valoria.registries.item.recipe.*;
import net.minecraft.network.*;
import net.minecraft.resources.*;
import net.minecraft.server.level.*;

public class AlchemyUpgradeTryPacket extends RateLimitedPacket{
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

    public void execute(ServerPlayer player){
        if(player.containerMenu instanceof AlchemyStationMenu heavyMenu){
            heavyMenu.tryUpgrade(player, this.recipeId);
        }
    }
}
