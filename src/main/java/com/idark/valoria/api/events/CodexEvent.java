package com.idark.valoria.api.events;

import com.idark.valoria.api.unlockable.types.*;
import com.idark.valoria.client.ui.screen.book.codex.*;
import net.minecraft.world.entity.player.*;
import net.minecraftforge.eventbus.api.*;

public class CodexEvent extends Event{

    @Cancelable
    public static class OnInit extends CodexEvent{
        public ChapterNode root;

        public OnInit(ChapterNode root) {
            this.root = root;
        }
    }

    @Cancelable
    public static class EntryAdded extends CodexEvent{
        public CodexEntry entry;

        public EntryAdded(CodexEntry entry) {
            this.entry = entry;
        }
    }

    @Cancelable
    public static class OnPageUnlocked extends CodexEvent{
        public Unlockable unlockable;

        public OnPageUnlocked(Unlockable unlockable) {
            this.unlockable = unlockable;
        }
    }

    /**
     * Called when reward is being claimed, cancel to remove Valoria behaviour
     */
    @Cancelable
    public static class OnRewardClaim extends CodexEvent{
        public Player player;
        public Unlockable unlockable;

        public OnRewardClaim(Player player, Unlockable unlockable) {
            this.player = player;
            this.unlockable = unlockable;
        }
    }
}