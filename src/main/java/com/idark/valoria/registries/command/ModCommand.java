package com.idark.valoria.registries.command;

import com.idark.valoria.api.unlockable.Unlockable;
import com.idark.valoria.core.capability.IUnlockable;
import com.idark.valoria.core.network.PacketHandler;
import com.idark.valoria.core.network.packets.PageToastPacket;
import com.idark.valoria.core.network.packets.UnlockableUpdatePacket;
import com.idark.valoria.registries.command.core.CommandArgument;
import com.idark.valoria.registries.command.core.CommandBuilder;
import com.idark.valoria.registries.command.core.CommandPart;
import com.idark.valoria.registries.command.core.CommandVariant;
import com.idark.valoria.registries.item.types.MagmaSwordItem;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import java.util.Collection;

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
            MagmaSwordItem.setCharges(player.getOffhandItem(), pCharge);
            MagmaSwordItem.setCharges(player.getMainHandItem(), pCharge);
            command.sendSuccess(() -> Component.translatable("commands.valoria.charges.set.add", pCharge).append(" to " + player.getName().getString()), true);
        }
    }
}