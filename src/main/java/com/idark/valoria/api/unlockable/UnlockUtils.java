package com.idark.valoria.api.unlockable;

import com.idark.valoria.capability.IUnlockable;
import com.idark.valoria.client.toast.ModToast;
import com.idark.valoria.network.PacketHandler;
import com.idark.valoria.network.UnlockableUpdatePacket;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import java.util.concurrent.atomic.AtomicBoolean;

public class UnlockUtils {

    public static boolean isUnlockable(Entity entity, Unlockable unlockable) {
        if (!(entity instanceof Player)) return false;
        AtomicBoolean isKnow = new AtomicBoolean(false);
        entity.getCapability(IUnlockable.INSTANCE, null).ifPresent((k) -> {
            isKnow.set(k.isUnlockable(unlockable));
        });
        return isKnow.get();
    }

    public static void addUnlockable(Entity entity, Unlockable unlockable) {
        if (!(entity instanceof Player)) return;
        entity.getCapability(IUnlockable.INSTANCE, null).ifPresent((k) -> {
            if (k.isUnlockable(unlockable)) return;
            k.addUnlockable(unlockable);

            unlockable.award((Player) entity);

            PacketHandler.sendTo((Player) entity, new UnlockableUpdatePacket((Player) entity));
            if (unlockable.hasToast()) {
                PacketHandler.sendTo((Player) entity, new ModToast(true));
            }
        });
    }

    public static void removeUnlockable(Entity entity, Unlockable unlockable) {
        if (!(entity instanceof Player)) return;
        entity.getCapability(IUnlockable.INSTANCE, null).ifPresent((k) -> {
            if (!k.isUnlockable(unlockable)) return;
            k.removeUnlockable(unlockable);

            PacketHandler.sendTo((Player) entity, new UnlockableUpdatePacket((Player) entity));
        });
    }

    public static void addAllUnlockable(Entity entity) {
        if (!(entity instanceof Player)) return;
        entity.getCapability(IUnlockable.INSTANCE, null).ifPresent((k) -> {
            k.addAllUnlockable();

            for (Unlockable unlockable : Unlockables.getUnlockables()) {
                if (unlockable.hasAllAward()) {
                    unlockable.award((Player) entity);
                }
            }

            PacketHandler.sendTo((Player) entity, new UnlockableUpdatePacket((Player) entity));
            PacketHandler.sendTo((Player) entity, new ModToast(true));
        });
    }

    public static void removeAllUnlockable(Entity entity) {
        if (!(entity instanceof Player)) return;
        entity.getCapability(IUnlockable.INSTANCE, null).ifPresent((k) -> {
            k.removeAllUnlockable();

            PacketHandler.sendTo((Player) entity, new UnlockableUpdatePacket((Player) entity));
        });
    }

    public static int getUnlockablePoints(Entity entity) {
        int points = 0;

        for (Unlockable Unlockable : Unlockables.getUnlockables()) {
            if (isUnlockable(entity, Unlockable)) {
                points = points + Unlockable.getPoints();
            }
        }

        return points;
    }

    public static int getAllUnlockablePoints() {
        int points = 0;

        for (Unlockable Unlockable : Unlockables.getUnlockables()) {
            points = points + Unlockable.getPoints();
        }

        return points;
    }
}