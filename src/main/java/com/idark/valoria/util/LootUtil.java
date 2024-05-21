package com.idark.valoria.util;

import com.google.common.collect.*;
import com.idark.valoria.*;
import com.mojang.serialization.*;
import net.minecraft.core.*;
import net.minecraft.resources.*;
import net.minecraft.server.level.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.storage.loot.*;
import net.minecraft.world.level.storage.loot.parameters.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.common.loot.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.registries.*;

import javax.annotation.*;
import java.util.*;

public final class LootUtil{

    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIER_SERIALIZERS = DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, Valoria.ID);
    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> ADD_ITEM = LOOT_MODIFIER_SERIALIZERS.register("add_item", AddItemModifier.CODEC);

    public static LootTable getTable(ServerLevel pServer, ResourceLocation pLoot){
        return pServer.getServer().getLootData().getLootTable(pLoot);
    }

    /**
     * Drops loot when using item
     */
    public static void DropLoot(Player pPlayer, Collection<ItemStack> pItemStacks){
        for(ItemStack stack : pItemStacks){
            pPlayer.drop(stack, false);
        }
    }

    /**
     * Spawns loot on a block position when using item
     */
    public static void SpawnLoot(Level pLevel, BlockPos pPos, Collection<ItemStack> pItemStacks){
        if(!pLevel.isClientSide()){
            for(ItemStack stack : pItemStacks){
                pLevel.addFreshEntity(new ItemEntity(pLevel, pPos.getX() + 0.5F, pPos.getY() + 0.5F, pPos.getZ() + 0.5F, stack));
            }
        }
    }

    @Nonnull
    public static List<ItemStack> createLoot(ResourceLocation pLoot, LootParams pParams){
        LootTable loot = getTable(pParams.getLevel(), pLoot);
        if(loot == LootTable.EMPTY)
            return Lists.newArrayList();

        return loot.getRandomItems(pParams);
    }

    public static LootParams getGiftParameters(ServerLevel pLevel, Vec3 pPos, Entity pEntity){
        return getGiftParameters(pLevel, pPos, 0, pEntity);
    }

    public static LootParams getGiftParameters(ServerLevel pLevel, Vec3 pPos, float pLuckValue, Entity pEntity){
        return new LootParams.Builder(pLevel).withParameter(LootContextParams.THIS_ENTITY, pEntity).withParameter(LootContextParams.ORIGIN, pPos).withLuck(pLuckValue).create(LootContextParamSets.GIFT);
    }

    public static void register(IEventBus eventBus){
        LOOT_MODIFIER_SERIALIZERS.register(eventBus);
    }
}