package com.idark.valoria.core.capability;

import com.idark.valoria.core.network.*;
import com.idark.valoria.core.network.packets.*;
import com.idark.valoria.registries.*;
import net.minecraft.core.*;
import net.minecraft.nbt.*;
import net.minecraft.server.level.*;
import net.minecraft.world.entity.*;
import net.neoforged.neoforge.common.util.*;

import javax.annotation.*;

public class NihilityLevelProvider implements INihilityLevel, INBTSerializable<CompoundTag>{
    public float nihilityAmount = 0;
    private float maxNihilityAmount = 100f;

    public void modifyAmount(@Nullable LivingEntity entity, float amount) {
        setAmountFromServer(entity, this.nihilityAmount + amount);
    }

    public void decrease(LivingEntity player, float amount) {
        modifyAmount(player, -amount);
    }

    @Override
    public void setAmount(float amount) {
        this.nihilityAmount = Math.max(Math.min(amount, this.maxNihilityAmount), 0F);
    }

    public void setAmountFromServer(@Nullable LivingEntity entity, float amount) {
        float max = getMaxAmount(entity);
        if (amount > max) {
            this.nihilityAmount = max;
        } else this.nihilityAmount = Math.max(amount, 0F);

        sendDataToClient(entity);
    }

    @Override
    public float getAmount() {
        return this.nihilityAmount;
    }

    @Override
    public void setMaxAmount(float max) {
        this.maxNihilityAmount = max;
    }

    public float getMaxAmount(@Nullable LivingEntity entity) {
        if (entity != null && entity.getAttribute(AttributeReg.MAX_NIHILITY) != null) {
            return (float) entity.getAttributeValue(AttributeReg.MAX_NIHILITY);
        }

        return this.maxNihilityAmount;
    }

    private void sendDataToClient(@Nullable LivingEntity entity) {
        if (!(entity instanceof ServerPlayer player)) return;
        PacketHandler.sendTo(player, new NihilityPacket(this, player));
    }

    @Override
    public CompoundTag serializeNBT(HolderLookup.Provider provider){
        CompoundTag wrapper = new CompoundTag();
        wrapper.putFloat("nihility_level", this.nihilityAmount);
        return wrapper;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag nbt){
        this.nihilityAmount = nbt.getFloat("nihility_level");
    }

    @Override
    public void copyFrom(INihilityLevel source) {
        this.nihilityAmount = source.getAmount();
    }
}
