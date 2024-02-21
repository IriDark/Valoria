package com.idark.valoria.api.unlockable;

import com.idark.valoria.capability.IUnlockable;
import com.idark.valoria.client.gui.toast.ModToast;
import com.idark.valoria.network.PacketHandler;
import com.idark.valoria.network.UnlockableUpdatePacket;
import net.minecraft.client.Minecraft;
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
                Minecraft.getInstance().getToasts().addToast(new ModToast(true));
            }
        });
    }

    public static void removeUnlockable(Entity entity, Unlockable unlockable) {
        if (!(entity instanceof Player)) return;
        entity.getCapability(IUnlockable.INSTANCE, null).ifPresent((k) -> {
            if (!k.isUnlockable(unlockable)) return;
            k.removeUnlockable(unlockable);

            PacketHandler.sendTo((Player) entity, new UnlockableUpdatePacket((Player) entity));
            Minecraft.getInstance().getToasts().addToast(new ModToast(false));

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
            Minecraft.getInstance().getToasts().addToast(new ModToast(true));
        });
    }

    public static void removeAllUnlockable(Entity entity) {
        if (!(entity instanceof Player)) return;
        entity.getCapability(IUnlockable.INSTANCE, null).ifPresent((k) -> {
            k.removeAllUnlockable();

            PacketHandler.sendTo((Player) entity, new UnlockableUpdatePacket((Player) entity));
            Minecraft.getInstance().getToasts().addToast(new ModToast(false));
        });
    }
}