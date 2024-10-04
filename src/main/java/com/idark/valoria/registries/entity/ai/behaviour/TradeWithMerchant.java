package com.idark.valoria.registries.entity.ai.behaviour;

import com.google.common.collect.*;
import com.idark.valoria.registries.entity.living.*;
import net.minecraft.server.level.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.behavior.*;
import net.minecraft.world.entity.ai.memory.*;
import net.minecraft.world.entity.npc.*;
import net.minecraft.world.item.*;

import java.util.*;

public class TradeWithMerchant extends Behavior<AbstractHauntedMerchant> {
    private Set<Item> trades = ImmutableSet.of();

    public TradeWithMerchant() {
        super(ImmutableMap.of(MemoryModuleType.INTERACTION_TARGET, MemoryStatus.VALUE_PRESENT, MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES, MemoryStatus.VALUE_PRESENT));
    }

    protected boolean checkExtraStartConditions(ServerLevel pLevel, AbstractHauntedMerchant pOwner) {
        return BehaviorUtils.targetIsValid(pOwner.getBrain(), MemoryModuleType.INTERACTION_TARGET, EntityType.VILLAGER);
    }

    protected boolean canStillUse(ServerLevel pLevel, AbstractHauntedMerchant pEntity, long pGameTime) {
        return this.checkExtraStartConditions(pLevel, pEntity);
    }

    protected void start(ServerLevel pLevel, AbstractHauntedMerchant pEntity, long pGameTime) {
        Villager villager = (Villager) pEntity.getBrain().getMemory(MemoryModuleType.INTERACTION_TARGET).get();
        BehaviorUtils.lockGazeAndWalkToEachOther(pEntity, villager, 0.5F);
    }

    protected void tick(ServerLevel pLevel, AbstractHauntedMerchant pOwner, long pGameTime) {
        AbstractHauntedMerchant villager = (AbstractHauntedMerchant) pOwner.getBrain().getMemory(MemoryModuleType.INTERACTION_TARGET).get();
        if (!(pOwner.distanceToSqr(villager) > 5.0D)) {
            BehaviorUtils.lockGazeAndWalkToEachOther(pOwner, villager, 0.5F);
            pOwner.gossip(pLevel, villager, pGameTime);
            if (!this.trades.isEmpty() && pOwner.getInventory().hasAnyOf(this.trades)) {
                throwHalfStack(pOwner, this.trades, villager);
            }
        }
    }

    protected void stop(ServerLevel pLevel, AbstractHauntedMerchant pEntity, long pGameTime) {
        pEntity.getBrain().eraseMemory(MemoryModuleType.INTERACTION_TARGET);
    }

    private static void throwHalfStack(AbstractHauntedMerchant pVillager, Set<Item> pStack, LivingEntity pEntity) {
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