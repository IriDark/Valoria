package com.idark.valoria.api.unlockable;

import com.idark.valoria.*;
import com.idark.valoria.api.unlockable.types.*;
import com.idark.valoria.core.capability.*;
import com.idark.valoria.core.network.*;
import com.idark.valoria.core.network.packets.*;
import net.minecraft.server.level.*;
import net.minecraft.world.entity.player.*;
import pro.komaru.tridot.util.*;

import javax.annotation.*;
import java.util.*;
import java.util.concurrent.atomic.*;

public class UnlockUtils{

    public static boolean isClaimed(Player player, Unlockable unlockable){
        AtomicBoolean claimed = new AtomicBoolean(false);
        player.getCapability(IUnlockable.INSTANCE, null).ifPresent((k) -> claimed.set(k.isClaimed(unlockable)));
        return claimed.get();
    }

    public static boolean isUnlocked(Player player, Unlockable unlockable){
        AtomicBoolean isKnow = new AtomicBoolean(false);
        player.getCapability(IUnlockable.INSTANCE, null).ifPresent((k) -> isKnow.set(k.isUnlocked(unlockable)));
        return isKnow.get();
    }

    public static Set<Unlockable> getUnlocked(Player player) {
        AtomicReference<Set<Unlockable>> set = new AtomicReference<>();
        player.getCapability(IUnlockable.INSTANCE, null).ifPresent((k) -> set.set(k.getUnlockables()));
        return set.get();
    }

    public static void claim(Player player, Unlockable unlockable){
        player.getCapability(IUnlockable.INSTANCE, null).ifPresent((k) -> {
            if(k.isClaimed(unlockable)) return;
            k.claim(unlockable);
        });
    }

    @Nullable
    public static Unlockable getRandom(Player player){
        ArrayList<Unlockable> all = new ArrayList<>(Unlockables.get());
        Set<Unlockable> unlocked = UnlockUtils.getUnlocked(player);
        if (unlocked != null) all.removeAll(unlocked);
        all.removeIf(unlockable -> !unlockable.canObtainByRandom());
        if (all.isEmpty()) return null;

        return all.get(Tmp.rnd.random(all.size() - 1));
    }

    @Nullable
    public static Unlockable getRandom(){
        var list = Unlockables.get();
        return list.isEmpty() ? null : list.get(Tmp.rnd.random(list.size() - 1));
    }

    public static void addRandom(ServerPlayer entity){
        var map = Unlockables.get().stream().findAny();
        if(map.isPresent()){
            Unlockable unlockable = map.get();
            entity.getCapability(IUnlockable.INSTANCE, null).ifPresent((k) -> {
                if(k.isUnlocked(unlockable)) return;
                k.addUnlockable(unlockable);
                PacketHandler.sendTo((Player)entity, new UnlockableUpdatePacket(entity));
                PacketHandler.sendTo((Player)entity, new PageToastPacket(entity, true));
            });
        }
    }

    public static void add(ServerPlayer entity, Unlockable unlockable){
        entity.getCapability(IUnlockable.INSTANCE, null).ifPresent((k) -> {
            if(k.isUnlocked(unlockable)) return;
            k.addUnlockable(unlockable);
            PacketHandler.sendTo((Player)entity, new UnlockableUpdatePacket(entity));
            PacketHandler.sendTo((Player)entity, new PageToastPacket(entity, true));
        });

        Valoria.LOGGER.debug("{} unlocked page {}", entity.getDisplayName().getString(), unlockable.id);
    }

    public static void remove(ServerPlayer entity, Unlockable unlockable){
        entity.getCapability(IUnlockable.INSTANCE, null).ifPresent((k) -> {
            if(!k.isUnlocked(unlockable)) return;
            k.removeUnlockable(unlockable);
            PacketHandler.sendTo((Player)entity, new UnlockableUpdatePacket(entity));
            PacketHandler.sendTo((Player)entity, new PageToastPacket(entity, false));
        });
    }

    public static void addAll(ServerPlayer entity){
        entity.getCapability(IUnlockable.INSTANCE, null).ifPresent((k) -> {
            k.addAllUnlockable();
            PacketHandler.sendTo((Player)entity, new UnlockableUpdatePacket(entity));
        });
    }

    public static void removeAll(ServerPlayer entity){
        entity.getCapability(IUnlockable.INSTANCE, null).ifPresent((k) -> {
            k.removeAllUnlockable();
            k.clearClaimed();
            PacketHandler.sendTo((Player)entity, new UnlockableUpdatePacket(entity));
        });
    }
}