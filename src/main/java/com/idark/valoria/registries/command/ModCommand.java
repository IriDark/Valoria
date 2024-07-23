package com.idark.valoria.registries.command;

import com.idark.valoria.api.unlockable.*;
import com.idark.valoria.core.capability.*;
import com.idark.valoria.core.network.*;
import com.idark.valoria.core.network.packets.*;
import com.idark.valoria.registries.command.core.*;
import com.idark.valoria.util.*;
import com.mojang.brigadier.*;
import com.mojang.brigadier.context.*;
import com.mojang.brigadier.exceptions.*;
import net.minecraft.commands.*;
import net.minecraft.network.chat.*;
import net.minecraft.server.level.*;

import java.util.*;

public class ModCommand{
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher){
        CommandArgument targets = CommandArgument.entities("targets");
        CommandArgument pages = CommandArgument.pages("pages");
        CommandArgument charges = CommandArgument.integer("charges", 0, 2);
        CommandBuilder lexicon = new CommandBuilder("lexicon");
        CommandBuilder builder = new CommandBuilder("valoria").variants(
        lexicon.variants(
        new CommandVariant(CommandPart.create("addAll"), targets).execute((p) -> {
            giveAllPages(p.getSource(), targets.getPlayers(p));
            return 1;
        }),

        new CommandVariant(CommandPart.create("add"), targets, pages).execute((p) -> {
            givePage(p.getSource(), targets.getPlayers(p), pages.getPages(p, "pages"));
            return 1;
        }),

        new CommandVariant(CommandPart.create("removeAll"), targets).execute((p) -> {
            removeAllPages(p.getSource(), targets.getPlayers(p));
            return 1;
        }),

        new CommandVariant(CommandPart.create("remove"), targets, pages).execute((p) -> {
            removePage(p.getSource(), targets.getPlayers(p), pages.getPages(p, "pages"));
            return 1;
        })
        ),

        new CommandVariant(CommandPart.create("setCharge"), targets, charges).execute((p) -> {
            setCharge(p.getSource(), targets.getPlayers(p), charges.getInt(p), p);
            return 1;
        })
        );

        dispatcher.register(lexicon.permission((p) -> p.hasPermission(2)).build());
        dispatcher.register(builder.permission((p) -> p.hasPermission(2)).build());
    }

    private static void giveAllPages(CommandSourceStack command, Collection<? extends ServerPlayer> targetPlayers) throws CommandSyntaxException{
        for(ServerPlayer player : targetPlayers){
            if(targetPlayers.size() == 1){
                command.sendSuccess(() -> Component.translatable("commands.valoria.page.give.single", targetPlayers.iterator().next().getDisplayName()), true);
            }else{
                command.sendSuccess(() -> Component.translatable("commands.valoria.page.give.multiple", targetPlayers.size()), true);
            }

            player.getCapability(IUnlockable.INSTANCE, null).ifPresent(IUnlockable::addAllUnlockable);
            PacketHandler.sendTo(player, new UnlockableUpdatePacket(player));
            PacketHandler.sendTo(player, new PageToastPacket(player, true));
        }
    }

    private static void removeAllPages(CommandSourceStack command, Collection<? extends ServerPlayer> targetPlayers) throws CommandSyntaxException{
        for(ServerPlayer player : targetPlayers){
            if(targetPlayers.size() == 1){
                command.sendSuccess(() -> Component.translatable("commands.valoria.page.remove.single", targetPlayers.iterator().next().getDisplayName()), true);
            }else{
                command.sendSuccess(() -> Component.translatable("commands.valoria.page.remove.multiple", targetPlayers.size()), true);
            }

            player.getCapability(IUnlockable.INSTANCE, null).ifPresent(IUnlockable::removeAllUnlockable);
            PacketHandler.sendTo(player, new UnlockableUpdatePacket(player));
            PacketHandler.sendTo(player, new PageToastPacket(player, false));
        }
    }

    private static void givePage(CommandSourceStack command, Collection<? extends ServerPlayer> targetPlayers, Unlockable pages) throws CommandSyntaxException{
        for(ServerPlayer player : targetPlayers){
            if(targetPlayers.size() == 1){
                command.sendSuccess(() -> Component.translatable("commands.valoria.page.give.single", targetPlayers.iterator().next().getDisplayName()), true);
            }else{
                command.sendSuccess(() -> Component.translatable("commands.valoria.page.give.multiple", targetPlayers.size()), true);
            }

            player.getCapability(IUnlockable.INSTANCE, null).ifPresent((k) -> k.addUnlockable(pages));
            PacketHandler.sendTo(player, new UnlockableUpdatePacket(player));
            PacketHandler.sendTo(player, new PageToastPacket(player, true));
        }
    }

    private static void removePage(CommandSourceStack command, Collection<? extends ServerPlayer> targetPlayers, Unlockable pages) throws CommandSyntaxException{
        for(ServerPlayer player : targetPlayers){
            if(targetPlayers.size() == 1){
                command.sendSuccess(() -> Component.translatable("commands.valoria.page.remove.single", targetPlayers.iterator().next().getDisplayName()), true);
            }else{
                command.sendSuccess(() -> Component.translatable("commands.valoria.page.remove.multiple", targetPlayers.size()), true);
            }

            player.getCapability(IUnlockable.INSTANCE, null).ifPresent((k) -> k.removeUnlockable(pages));
            PacketHandler.sendTo(player, new UnlockableUpdatePacket(player));
            PacketHandler.sendTo(player, new PageToastPacket(player, false));
        }
    }

    private static void setCharge(CommandSourceStack command, Collection<? extends ServerPlayer> targetPlayers, int pCharge, CommandContext p) throws CommandSyntaxException{
        for(ServerPlayer player : targetPlayers){
            NbtUtils.writeIntNbt(player.getOffhandItem(), "charge", pCharge);
            NbtUtils.writeIntNbt(player.getMainHandItem(), "charge", pCharge);
            command.sendSuccess(() -> Component.translatable("commands.valoria.charges.set.add", pCharge).append(" to " + player.getName().getString()), true);
        }
    }
}