package com.idark.darkrpg.util;

import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
public final class LootUtil {
	public static LootTable getTable(ServerLevel world, ResourceLocation table) {
		return world.getServer().getLootData().getLootTable(table);
	}

	/**
	* Drops loot when using item
	*/
	public static void DropLoot(Player player, Collection<ItemStack> stacks) {
		for (ItemStack stack : stacks) {
			player.drop(stack, false);
		}
	}

	/**
	* Spawns loot on a block position when using item
	*/
	public static void SpawnLoot(Level world, BlockPos blockpos, Collection<ItemStack> stacks) {
		for (ItemStack stack : stacks) {
			if (!world.isClientSide()) {
				world.addFreshEntity(new ItemEntity(world, blockpos.getX() + 0.5F, blockpos.getY() + 0.5F, blockpos.getZ() + 0.5F, stack));
			}
		}
	}

	/**
	* AOA sources
	*/
	@Nonnull
	public static List<ItemStack> generateLoot(ResourceLocation table, LootParams context) {
		LootTable lootTable = getTable(context.getLevel(), table);
		if (lootTable == LootTable.EMPTY)
			return Lists.newArrayList();

		return lootTable.getRandomItems(context);
	}

	public static LootContext createContext(ServerLevel level, Function<LootParams.Builder, LootParams> params) {
		return new LootContext.Builder(params.apply(new LootParams.Builder(level))).create(null);
	}

	public static LootParams getGiftParameters(ServerLevel world, Vec3 position, Entity targetEntity) {
		return getGiftParameters(world, position, 0, targetEntity);
	}

	public static LootParams getGiftParameters(ServerLevel level, Vec3 position, float luck, Entity targetEntity) {
		return new LootParams.Builder(level).withParameter(LootContextParams.THIS_ENTITY, targetEntity).withParameter(LootContextParams.ORIGIN, position).withLuck(luck).create(LootContextParamSets.GIFT);
	}
}