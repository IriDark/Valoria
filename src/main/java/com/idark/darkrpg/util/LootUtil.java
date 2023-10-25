package com.idark.darkrpg.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.*;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.List;
public final class LootUtil {
	public static LootTable getTable(ServerLevel world, ResourceLocation table) {
		return world.getServer().getLootData().getLootTable(table);
	}

	public static void givePlayerMultipleItems(Player pl, Collection<ItemStack> stacks) {
		for (ItemStack stack : stacks) {
			pl.drop(stack, false);
		}
	}

	//@Nonnull
	//public static List<ItemStack> generateLoot(ServerLevel world, ResourceLocation table, LootContext context) {
	//	LootTable lootTable = getTable(world, table);
	//
	//	if (lootTable == LootTable.EMPTY)
	//		return Lists.<ItemStack>newArrayList();
	//
	//	return lootTable.getRandomItems(context);
	//}

	public static LootContext getGiftContext(ServerLevel world, Vec3 position, Entity targetEntity) {
		return getGiftContext(world, position, targetEntity);
	}

	//public static LootContext getGiftContext(ServerLevel world, Vec3 position, float luck, Entity targetEntity) {
	//	return new LootContext.Builder(world).withRandom(world.getRandom()).withParameter(LootContextParams.THIS_ENTITY, targetEntity).withParameter(LootContextParams.ORIGIN, position).withLuck(luck).create(LootContextParams.TOOL);
	//}
}