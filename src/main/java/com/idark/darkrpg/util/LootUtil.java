package com.idark.darkrpg.util;

public final class LootUtil {
	/*public static LootTable getTable(ServerLevel world, ResourceLocation table) {
		return world.getServer().getLootData().getLootTable(table);
	}

	public static void givePlayerMultipleItems(Player pl, Collection<ItemStack> stacks) {
		for (ItemStack stack : stacks) {
			pl.drop(stack, false);
		}
	}

	@Nonnull
	public static List<ItemStack> generateLoot(ServerLevel world, ResourceLocation table, LootContext context) {
		LootTable lootTable = getTable(world, table);

		if (lootTable == LootTable.EMPTY)
			return Lists.<ItemStack>newArrayList();

		return lootTable.getRandomItems(context);
	}

	public static LootContext getGiftContext(ServerLevel world, Vec3 position, Entity targetEntity) {
		return getGiftContext(world, position, 0, targetEntity);
	}

	public static LootContext getGiftContext(ServerLevel world, Vec3 position, float luck, Entity targetEntity) {
		return new LootContext.Builder(world).withRandom(world.getRandom()).withParameter(LootParameters.THIS_ENTITY, targetEntity).withParameter(LootParameters.ORIGIN, position).withLuck(luck).create(LootParameterSets.GIFT);
	}*/
}