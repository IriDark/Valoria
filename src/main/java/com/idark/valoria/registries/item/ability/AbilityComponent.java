package com.idark.valoria.registries.item.ability;

import com.idark.valoria.*;
import net.minecraft.nbt.*;
import net.minecraft.resources.*;
import net.minecraft.server.level.*;
import net.minecraft.world.inventory.tooltip.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import pro.komaru.tridot.util.struct.data.*;

public abstract class AbilityComponent{
    public final AbilityType<?> type;
    public ResourceLocation icon = Valoria.loc("textures/gui/tooltips/unknown.png");
    public int itemCooldown = 0;
    public int usages = 0;
    public int maxUsages = 1;
    public int durabilityUsage = 1;
    public boolean cancelVanillaBehaviour = false;

    public AbilityComponent(AbilityType<?> type) {
        this.type = type;
    }

    public CompoundTag saveToNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putString("id", type.id.toString());
        tag.putInt("usages", this.usages);
        tag.putInt("maxUsages", this.maxUsages);
        tag.putInt("itemCooldown", this.itemCooldown);
        tag.putInt("durabilityUsage", this.durabilityUsage);
        tag.putBoolean("cancelVanilla", this.cancelVanillaBehaviour);
        if (icon != null) tag.putString("icon", icon.toString());

        writeCustomNBT(tag);
        return tag;
    }

    public void loadFromNBT(CompoundTag tag) {
        this.usages = tag.getInt("usages");
        this.maxUsages = tag.contains("maxUsages") ? tag.getInt("maxUsages") : 1;
        this.itemCooldown = tag.getInt("itemCooldown");
        this.durabilityUsage = tag.contains("durabilityUsage") ? tag.getInt("durabilityUsage") : 1;
        this.cancelVanillaBehaviour = tag.getBoolean("cancelVanilla");
        if (tag.contains("icon")) this.icon = new ResourceLocation(tag.getString("icon"));

        readCustomNBT(tag);
    }

    protected void writeCustomNBT(CompoundTag tag) {}
    protected void readCustomNBT(CompoundTag tag) {}

    public Seq<TooltipComponent> getTooltips(ItemStack pStack, CastType castType){
        return Seq.with();
    }

    public int onCastStart(ServerPlayer player, Level level, ItemStack stack) {
        return this.execute(player, level, stack);
    }

    /**
     *
     * @return Ability cooldown in ticks
     */
    public abstract int execute(ServerPlayer player, Level level, ItemStack stack);

    public boolean canCast(ServerPlayer player, ItemStack stack) {
        Level level = player.level();
        boolean noCooldown = !AbilityHelper.isOnCooldown(level, stack, this);
        boolean noItemCooldown = !player.getCooldowns().isOnCooldown(stack.getItem());
        return noCooldown && noItemCooldown;
    }
}