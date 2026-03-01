package com.idark.valoria.registries.item.ability;

import net.minecraft.nbt.*;
import net.minecraft.resources.*;
import net.minecraft.server.level.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import org.jetbrains.annotations.*;

import javax.annotation.Nullable;
import java.util.*;

public class AbilityHelper {
    private static final String ABILITIES_TAG = "valoria_abilities";
    private static final String COOLDOWNS_TAG = "ability_cooldowns";

    public static void setAbility(ItemStack stack, AbilityComponent ability) {
        setAbility(stack, CastType.NOT_SPECIFIED, ability);
    }

    public static void setAbility(ItemStack stack, CastType type, AbilityComponent ability) {
        CompoundTag tag = stack.getOrCreateTag();
        ListTag abilitiesList = tag.contains(ABILITIES_TAG, Tag.TAG_LIST) ? tag.getList(ABILITIES_TAG, Tag.TAG_COMPOUND) : new ListTag();

        CompoundTag abilityEntry = ability.saveToNBT();
        abilityEntry.putString("cast_type", type.name());
        abilitiesList.add(abilityEntry);

        tag.put(ABILITIES_TAG, abilitiesList);
    }

    public static void updateAbilityState(ItemStack stack, CastType type, AbilityComponent ability) {
        if (!stack.hasTag() || !stack.getTag().contains(ABILITIES_TAG, Tag.TAG_LIST)) return;
        ListTag list = stack.getTag().getList(ABILITIES_TAG, Tag.TAG_COMPOUND);

        for (int i = 0; i < list.size(); i++) {
            CompoundTag entry = list.getCompound(i);
            if (entry.getString("cast_type").equals(type.name()) && entry.getString("id").equals(ability.type.id.toString())) {
                CompoundTag newTag = ability.saveToNBT();
                newTag.putString("cast_type", type.name());
                list.set(i, newTag);
                break;
            }
        }
    }

    @Nullable
    public static AbilityComponent getAbility(ItemStack stack, CastType type) {
        if (!stack.hasTag() || !stack.getTag().contains(ABILITIES_TAG, Tag.TAG_LIST)) return null;

        ListTag abilitiesList = stack.getTag().getList(ABILITIES_TAG, Tag.TAG_COMPOUND);
        for (int i = 0; i < abilitiesList.size(); i++) {
            CompoundTag entry = abilitiesList.getCompound(i);
            if (entry.getString("cast_type").equals(type.name())) {
                AbilityType<?> abilityType = AbilityRegistry.getType(new ResourceLocation(entry.getString("id")));
                if (abilityType != null) {
                    AbilityComponent ability = abilityType.createInstance();
                    ability.loadFromNBT(entry);
                    return ability;
                }
            }
        }

        return null;
    }

    public record ActiveAbility(CastType type, AbilityComponent ability) {}
    public static List<ActiveAbility> getActiveAbilities(ItemStack stack) {
        List<ActiveAbility> list = new ArrayList<>();
        if (!stack.hasTag() || !stack.getTag().contains(ABILITIES_TAG, Tag.TAG_LIST)) return list;

        ListTag abilitiesList = stack.getTag().getList(ABILITIES_TAG, Tag.TAG_COMPOUND);
        for (int i = 0; i < abilitiesList.size(); i++) {
            CompoundTag entry = abilitiesList.getCompound(i);
            AbilityType<?> abilityType = AbilityRegistry.getType(new ResourceLocation(entry.getString("id")));
            if (abilityType != null) {
                AbilityComponent ability = abilityType.createInstance();
                ability.loadFromNBT(entry);

                CastType type;
                try { type = CastType.valueOf(entry.getString("cast_type")); }
                catch (IllegalArgumentException e) { type = CastType.NOT_SPECIFIED; }

                list.add(new ActiveAbility(type, ability));
            }
        }

        return list;
    }

    public static void tryCast(ServerPlayer player, ItemStack stack, CastType type) {
        AbilityComponent ability = getAbility(stack, type);
        if (ability != null && ability.canCast(player, stack)) {
            int cd = ability.onCastStart(player, player.level(), stack);
            stack.hurtAndBreak(ability.durabilityUsage, player, (p) -> p.broadcastBreakEvent(player.getUsedItemHand()));
            ability.usages++;
            if (cd > 0 && ability.usages >= ability.maxUsages) {
                setCooldown(player.level(), stack, ability, cd);
                ability.usages = 0;
                player.getCooldowns().addCooldown(stack.getItem(), ability.itemCooldown);
            }

            updateAbilityState(stack, type, ability);
        }
    }

    private static @NotNull CompoundTag getOrCreateTag(CompoundTag tag, String tagName) {
        return tag.contains(tagName) ? tag.getCompound(tagName) : new CompoundTag();
    }

    public static boolean isOnCooldown(Level level, ItemStack stack, AbilityComponent ability) {
        CompoundTag tag = stack.getOrCreateTag();
        CompoundTag cdTag = getOrCreateTag(tag, COOLDOWNS_TAG);
        String abilityId = ability.type.id.toString();
        if (cdTag.contains(abilityId)) {
            long endTime = cdTag.getLong(abilityId);
            return level.getGameTime() < endTime;
        }

        return false;
    }

    public static long getCooldown(ItemStack stack, AbilityComponent ability) {
        CompoundTag tag = stack.getOrCreateTag();
        CompoundTag cdTag = getOrCreateTag(tag, COOLDOWNS_TAG);
        String abilityId = ability.type.id.toString();
        if (cdTag.contains(abilityId)) {
            return cdTag.getLong(abilityId);
        }

        return 0L;
    }

    public static void setCooldown(Level level, ItemStack stack, AbilityComponent ability, int ticks) {
        CompoundTag tag = stack.getOrCreateTag();
        CompoundTag cdTag = getOrCreateTag(tag, COOLDOWNS_TAG);

        String id = ability.type.id.toString();
        cdTag.putLong(id, level.getGameTime() + ticks);
        cdTag.putInt(id + "_max", ticks);
        tag.put(COOLDOWNS_TAG, cdTag);
    }
}