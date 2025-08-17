package com.idark.valoria.core.capability;

import com.idark.valoria.core.network.*;
import com.idark.valoria.core.network.packets.*;
import com.idark.valoria.registries.*;
import net.minecraft.nbt.*;
import net.minecraft.server.level.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraftforge.common.util.*;

import javax.annotation.*;

public class NihilityLevelProvider implements INihilityLevel, INBTSerializable<CompoundTag>{
    public static float clientMax, clientAmount;
    public float nihilityAmount = 0;

    public void modifyAmount(@Nullable LivingEntity entity, float amount) {
        setAmount(entity, this.nihilityAmount + amount);
    }

    public void decrease(Player player, float amount) {
        modifyAmount(player, -amount);
    }

    @Override
    public void setAmount(@Nullable LivingEntity entity, float amount) {
        float max = getMaxAmount(entity, false);
        if (amount > max) {
            this.nihilityAmount = max;
        } else {
            this.nihilityAmount = Math.max(amount, 0F);
        }

        sendDataToClient(entity);
    }

    @Override
    public float getAmount(boolean clientSide) {
        return this.nihilityAmount;
    }

    public float getMaxAmount(@Nullable LivingEntity entity, boolean clientSide) {
        if (clientSide) return clientMax;
        if (entity != null && entity.getAttribute(AttributeReg.MAX_NIHILITY_LEVEL.get()) != null) {
            return (float) entity.getAttributeValue(AttributeReg.MAX_NIHILITY_LEVEL.get());
        }

        return 100f;
    }

    private void sendDataToClient(@Nullable LivingEntity entity) {
        if (entity instanceof ServerPlayer player) {
            PacketHandler.sendToTracking(player.level(), player.blockPosition(), new NihilityPacket(this, player));
        }
    }

    @Override
    public CompoundTag serializeNBT(){
        CompoundTag wrapper = new CompoundTag();
        wrapper.putFloat("nihilityLevel", this.nihilityAmount);
        return wrapper;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt){
        this.nihilityAmount = nbt.getFloat("nihilityLevel");
    }
}
