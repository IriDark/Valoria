package com.idark.valoria.registries.entity.ai.behaviour;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.idark.valoria.registries.entity.living.HauntedMerchant;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Set;

public class TradeWithMerchant extends Behavior<HauntedMerchant> {
    private Set<Item> trades = ImmutableSet.of();

    public TradeWithMerchant() {
        super(ImmutableMap.of(MemoryModuleType.INTERACTION_TARGET, MemoryStatus.VALUE_PRESENT, MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES, MemoryStatus.VALUE_PRESENT));
    }

    protected boolean checkExtraStartConditions(ServerLevel pLevel, HauntedMerchant pOwner) {
        return BehaviorUtils.targetIsValid(pOwner.getBrain(), MemoryModuleType.INTERACTION_TARGET, EntityType.VILLAGER);
    }

    protected boolean canStillUse(ServerLevel pLevel, HauntedMerchant pEntity, long pGameTime) {
        return this.checkExtraStartConditions(pLevel, pEntity);
    }

    protected void start(ServerLevel pLevel, HauntedMerchant pEntity, long pGameTime) {
        Villager villager = (Villager) pEntity.getBrain().getMemory(MemoryModuleType.INTERACTION_TARGET).get();
        BehaviorUtils.lockGazeAndWalkToEachOther(pEntity, villager, 0.5F);
    }

    protected void tick(ServerLevel pLevel, HauntedMerchant pOwner, long pGameTime) {
        HauntedMerchant villager = (HauntedMerchant) pOwner.getBrain().getMemory(MemoryModuleType.INTERACTION_TARGET).get();
        if (!(pOwner.distanceToSqr(villager) > 5.0D)) {
            BehaviorUtils.lockGazeAndWalkToEachOther(pOwner, villager, 0.5F);
            pOwner.gossip(pLevel, villager, pGameTime);
            if (!this.trades.isEmpty() && pOwner.getInventory().hasAnyOf(this.trades)) {
                throwHalfStack(pOwner, this.trades, villager);
            }
        }
    }

    protected void stop(ServerLevel pLevel, HauntedMerchant pEntity, long pGameTime) {
        pEntity.getBrain().eraseMemory(MemoryModuleType.INTERACTION_TARGET);
    }

    private static void throwHalfStack(HauntedMerchant pVillager, Set<Item> pStack, LivingEntity pEntity) {
        SimpleContainer simplecontainer = pVillager.getInventory();
        ItemStack itemstack = ItemStack.EMPTY;
        int i = 0;

        while (i < simplecontainer.getContainerSize()) {
            ItemStack itemstack1;
            Item item;
            int j;
            label28:
            {
                itemstack1 = simplecontainer.getItem(i);
                if (!itemstack1.isEmpty()) {
                    item = itemstack1.getItem();
                    if (pStack.contains(item)) {
                        if (itemstack1.getCount() > itemstack1.getMaxStackSize() / 2) {
                            j = itemstack1.getCount() / 2;
                            break label28;
                        }

                        if (itemstack1.getCount() > 24) {
                            j = itemstack1.getCount() - 24;
                            break label28;
                        }
                    }
                }

                ++i;
                continue;
            }

            itemstack1.shrink(j);
            itemstack = new ItemStack(item, j);
            break;
        }

        if (!itemstack.isEmpty()) {
            BehaviorUtils.throwItem(pVillager, itemstack, pEntity.position());
        }

    }
}