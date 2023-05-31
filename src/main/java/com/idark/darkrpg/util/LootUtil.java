package com.idark.darkrpg.util;

import com.google.common.collect.Lists;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nonnull;
import java.util.*;

public final class LootUtil {
	public static LootTable getTable(ServerWorld world, ResourceLocation table) {
		return world.getServer().getLootTableManager().getLootTableFromLocation(table);
	}

	public static void givePlayerMultipleItems(PlayerEntity pl, Collection<ItemStack> stacks) {
		for (ItemStack stack : stacks) {
			pl.dropItem(stack, false);
		}
	}

	@Nonnull
	public static List<ItemStack> generateLoot(ServerWorld world, ResourceLocation table, LootContext context) {
		LootTable lootTable = getTable(world, table);

		if (lootTable == LootTable.EMPTY_LOOT_TABLE)
			return Lists.<ItemStack>newArrayList();

		return lootTable.generate(context);
	}

	public static LootContext getGiftContext(ServerWorld world, Vector3d position, Entity targetEntity) {
		return getGiftContext(world, position, 0, targetEntity);
	}

	public static LootContext getGiftContext(ServerWorld world, Vector3d position, float luck, Entity targetEntity) {
		return new LootContext.Builder(world).withRandom(world.getRandom()).withParameter(LootParameters.THIS_ENTITY, targetEntity).withParameter(LootParameters.ORIGIN, position).withLuck(luck).build(LootParameterSets.GIFT);
	}
}