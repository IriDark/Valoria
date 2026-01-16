package com.idark.valoria.core.capability;

import com.idark.valoria.core.network.*;
import com.idark.valoria.core.network.packets.*;
import net.minecraft.nbt.*;
import net.minecraft.server.level.*;
import net.minecraft.world.entity.*;
import net.minecraftforge.common.util.*;

import javax.annotation.*;

public class MagmaLevelProvider implements IMagmaLevel, INBTSerializable<CompoundTag>{
    public float magmaAmount = 0;
    private float maxMagmaAmount = 0;

    public void modifyAmount(@Nullable LivingEntity entity, float amount) {
        setAmountFromServer(entity, this.magmaAmount + amount);
    }

    public void modifyMaxAmount(@Nullable LivingEntity entity, float amount) {
        setMaxAmountFromServer(entity, this.maxMagmaAmount + amount);
    }

    public void decrease(LivingEntity player, float amount) {
        modifyAmount(player, -amount);
    }

    @Override
    public void setAmount(float amount) {
        this.magmaAmount = Math.max(Math.min(amount, this.maxMagmaAmount), 0F);
    }

    public void setAmountFromServer(@Nullable LivingEntity entity, float amount) {
        float max = getMaxAmount(entity);
        if (amount > max) {
            this.magmaAmount = max;
        } else this.magmaAmount = Math.max(amount, 0F);

        sendDataToClient(entity);
    }

    public void setMaxAmountFromServer(@Nullable LivingEntity entity, float amount) {
        this.maxMagmaAmount = Math.max(amount, 0F);
        sendDataToClient(entity);
    }

    @Override
    public float getAmount() {
        return this.magmaAmount;
    }

    @Override
    public void addMaxAmount(LivingEntity player, float amount) {
        modifyMaxAmount(player, amount);
    }

    @Override
    public void decreaseMaxAmount(LivingEntity player, float amount) {
        modifyMaxAmount(player, -amount);
    }

    @Override
    public void setMaxAmount(float max) {
        this.maxMagmaAmount = max;
    }

    public float getMaxAmount(@Nullable LivingEntity entity) {
        return this.maxMagmaAmount;
    }

    private void sendDataToClient(@Nullable LivingEntity entity) {
        if (entity == null || !(entity instanceof ServerPlayer player)) return;
        PacketHandler.sendTo(player, new MagmaPacket(this, player));
    }

    @Override
    public CompoundTag serializeNBT(){
        CompoundTag wrapper = new CompoundTag();
        wrapper.putFloat("magma_level", this.magmaAmount);
        return wrapper;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt){
        this.magmaAmount = nbt.getFloat("magma_level");
    }
}
