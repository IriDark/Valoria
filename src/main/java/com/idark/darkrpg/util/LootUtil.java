package com.idark.darkrpg.util;

import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.List;
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

	@Nonnull
	public static List<ItemStack> createLoot(ResourceLocation table, LootParams params) {
		LootTable loot = getTable(params.getLevel(), table);
		if (loot == LootTable.EMPTY)
			return Lists.newArrayList();

		return loot.getRandomItems(params);
	}

	public static LootParams getGiftParameters(ServerLevel level, Vec3 pos, Entity entity) {
		return getGiftParameters(level, pos, 0, entity);
	}

	public static LootParams getGiftParameters(ServerLevel level, Vec3 pos, float luck, Entity entity) {
		return new LootParams.Builder(level).withParameter(LootContextParams.THIS_ENTITY, entity).withParameter(LootContextParams.ORIGIN, pos).withLuck(luck).create(LootContextParamSets.GIFT);
	}
}