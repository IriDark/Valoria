package com.idark.valoria.api.events;

import com.idark.valoria.api.unlockable.types.*;
import com.idark.valoria.client.ui.screen.book.codex.*;
import net.minecraft.world.entity.player.*;
import net.neoforged.bus.api.*;

public class CodexEvent extends Event{

    public static class OnInit extends CodexEvent implements ICancellableEvent{
        public ChapterNode root;

        public OnInit(ChapterNode root) {
            this.root = root;
        }
    }

    public static class EntryAdded extends CodexEvent implements ICancellableEvent{
        public CodexEntry entry;

        public EntryAdded(CodexEntry entry) {
            this.entry = entry;
        }
    }

    public static class OnPageUnlocked extends CodexEvent implements ICancellableEvent{
        public Unlockable unlockable;

        public OnPageUnlocked(Unlockable unlockable) {
            this.unlockable = unlockable;
        }
    }

    /**
     * Called when reward is being claimed, cancel to remove Valoria behaviour
     */
    public static class OnRewardClaim extends CodexEvent implements ICancellableEvent{
        public Player player;
        public Unlockable unlockable;

        public OnRewardClaim(Player player, Unlockable unlockable) {
            this.player = player;
            this.unlockable = unlockable;
        }
    }
}