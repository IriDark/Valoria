package com.idark.valoria.command;

import com.idark.valoria.capability.IPage;
import com.idark.valoria.client.render.gui.book.LexiconGui;
import com.idark.valoria.client.render.gui.book.LexiconPages;
import com.idark.valoria.command.build.CommandArgument;
import com.idark.valoria.command.build.CommandBuilder;
import com.idark.valoria.command.build.CommandPart;
import com.idark.valoria.command.build.CommandVariant;
import com.idark.valoria.network.PacketHandler;
import com.idark.valoria.network.PageUpdatePacket;
import com.idark.valoria.toast.ModToast;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.Collection;

public class ModCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        CommandArgument targets = CommandArgument.entities("targets");
        CommandArgument pages = CommandArgument.pages("pages");
        CommandBuilder builder = new CommandBuilder("valoria")
                .variants(
                        new CommandVariant(CommandPart.create("addPage"), targets, pages).execute((p) -> {
                            givePage(p.getSource(), targets.getPlayers(p), pages.getPages(p, "pages"));
                            return 1;
                        }),

                        new CommandVariant(CommandPart.create("removePage"), targets, pages).execute((p) -> {
                            removePage(p.getSource(), targets.getPlayers(p), pages.getPages(p, "pages"));
                            return 1;
                        })
                );

        dispatcher.register(builder.permission((p)->p.hasPermission(2)).build());
    }

    private static int givePage(CommandSourceStack command, Collection<? extends ServerPlayer> targetPlayers, LexiconPages pages) throws CommandSyntaxException {
        for(ServerPlayer player : targetPlayers) {
            LexiconGui.makeOpen(player, pages,true);
            Minecraft.getInstance().getToasts().addToast(new ModToast(true));

            if (targetPlayers.size() == 1) {
                command.sendSuccess(() -> Component.translatable("commands.valoria.page.give.single", targetPlayers.iterator().next().getDisplayName()), true);
            } else {
                command.sendSuccess(() -> Component.translatable("commands.valoria.page.give.multiple", targetPlayers.size()), true);
            }

            player.getCapability(IPage.INSTANCE, null).ifPresent((k) -> {
                k.makeOpen(pages, true);
            });

            PacketHandler.sendTo(player, new PageUpdatePacket(player));
        }

        return Command.SINGLE_SUCCESS;
    }

    private static int removePage(CommandSourceStack command, Collection<? extends ServerPlayer> targetPlayers, LexiconPages pages) throws CommandSyntaxException {
        for(ServerPlayer player : targetPlayers) {
            LexiconGui.makeOpen(player, pages,false);
            Minecraft.getInstance().getToasts().addToast(new ModToast(false));

            if (targetPlayers.size() == 1) {
                command.sendSuccess(() -> Component.translatable("commands.valoria.page.remove.single", targetPlayers.iterator().next().getDisplayName()), true);
            } else {
                command.sendSuccess(() -> Component.translatable("commands.valoria.page.remove.multiple", targetPlayers.size()), true);
            }

            player.getCapability(IPage.INSTANCE, null).ifPresent((k) -> {
                k.makeOpen(pages, false);
            });

            PacketHandler.sendTo(player, new PageUpdatePacket(player));
        }

        return Command.SINGLE_SUCCESS;
    }
}