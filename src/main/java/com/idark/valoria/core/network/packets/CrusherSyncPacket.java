package com.idark.valoria.core.network.packets;

import com.idark.valoria.core.compat.jei.categories.*;
import com.idark.valoria.registries.item.recipe.*;
import net.minecraft.network.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;
import net.minecraftforge.network.NetworkEvent.*;

import java.util.*;
import java.util.function.*;

public class CrusherSyncPacket {
    public ResourceLocation recipeId;
    public List<ItemStack> items;
    public CrusherSyncPacket(ResourceLocation recipeId, List<ItemStack> items){
        this.recipeId = recipeId;
        this.items = items;
    }

    public CrusherSyncPacket(CrusherRecipe recipe, List<ItemStack> items){
        this(recipe.getId(), items);
    }

    public static void encode(CrusherSyncPacket object, FriendlyByteBuf buffer){
        buffer.writeResourceLocation(object.recipeId);
        buffer.writeCollection(object.items, FriendlyByteBuf::writeItem);
    }

    public static CrusherSyncPacket decode(FriendlyByteBuf buffer){
        return new CrusherSyncPacket(buffer.readResourceLocation(), buffer.readCollection(ArrayList::new, FriendlyByteBuf::readItem));
    }

    public void handle(Supplier<Context> ctx) {
        ctx.get().enqueueWork(() -> {
            CrusherRecipeCategory.DROPS.put(recipeId, items);
        });

        ctx.get().setPacketHandled(true);
    }
}
