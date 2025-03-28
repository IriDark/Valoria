package com.idark.valoria.api.unlockable;

import com.idark.valoria.core.capability.IUnlockable;
import com.idark.valoria.core.network.PacketHandler;
import com.idark.valoria.core.network.packets.PageToastPacket;
import com.idark.valoria.core.network.packets.UnlockableUpdatePacket;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import java.util.concurrent.atomic.AtomicBoolean;

public class UnlockUtils{

    public static boolean isUnlocked(Entity entity, Unlockable unlockable){
        if(!(entity instanceof Player)) return false;
        AtomicBoolean isKnow = new AtomicBoolean(false);
        entity.getCapability(IUnlockable.INSTANCE, null).ifPresent((k) -> isKnow.set(k.isUnlockable(unlockable)));
        return isKnow.get();
    }

    public static void addUnlockable(Entity entity, Unlockable unlockable){
        if(!(entity instanceof Player)) return;
        entity.getCapability(IUnlockable.INSTANCE, null).ifPresent((k) -> {
            if(k.isUnlockable(unlockable)) return;
            k.addUnlockable(unlockable);
            unlockable.award((Player)entity);
            PacketHandler.sendTo((Player)entity, new UnlockableUpdatePacket((Player)entity));
            PacketHandler.sendTo((Player)entity, new PageToastPacket((Player)entity, true));
        });
    }

    public static void removeUnlockable(Entity entity, Unlockable unlockable){
        if(!(entity instanceof Player)) return;
        entity.getCapability(IUnlockable.INSTANCE, null).ifPresent((k) -> {
            if(!k.isUnlockable(unlockable)) return;
            k.removeUnlockable(unlockable);
            PacketHandler.sendTo((Player)entity, new UnlockableUpdatePacket((Player)entity));
            PacketHandler.sendTo((Player)entity, new PageToastPacket((Player)entity, false));
        });
    }

    public static void addAllUnlockables(Entity entity){
        if(!(entity instanceof Player)) return;
        entity.getCapability(IUnlockable.INSTANCE, null).ifPresent((k) -> {
            k.addAllUnlockable();
            for(Unlockable unlockable : Unlockables.getUnlockables()){
                if(unlockable.hasAllAward()){
                    unlockable.award((Player)entity);
                }
            }

            PacketHandler.sendTo((Player)entity, new UnlockableUpdatePacket((Player)entity));
            PacketHandler.sendTo((Player)entity, new PageToastPacket((Player)entity, true));
        });
    }

    public static void removeAllUnlockables(Entity entity){
        if(!(entity instanceof Player)) return;
        entity.getCapability(IUnlockable.INSTANCE, null).ifPresent((k) -> {
            k.removeAllUnlockable();
            PacketHandler.sendTo((Player)entity, new UnlockableUpdatePacket((Player)entity));
            PacketHandler.sendTo((Player)entity, new PageToastPacket((Player)entity, false));
        });
    }
}