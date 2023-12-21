package com.idark.darkrpg.command;

import com.idark.darkrpg.client.render.gui.book.LexiconGui;
import com.idark.darkrpg.client.render.gui.book.LexiconPages;
import com.idark.darkrpg.command.build.CommandArgument;
import com.idark.darkrpg.command.build.CommandBuilder;
import com.idark.darkrpg.command.build.CommandPart;
import com.idark.darkrpg.command.build.CommandVariant;
import com.idark.darkrpg.toast.ModToast;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import java.util.Collection;

public class ModCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        CommandArgument targets = CommandArgument.entities("targets");
        CommandArgument pages = CommandArgument.pages("pages");
        CommandBuilder builder = new CommandBuilder("darkrpg")
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
            LexiconGui.setOpen(player, pages,true);
            Minecraft.getInstance().getToasts().addToast(new ModToast(true));

            if (targetPlayers.size() == 1) {
                command.sendSuccess(() -> Component.translatable("commands.darkrpg.page.give.single", targetPlayers.iterator().next().getDisplayName()), true);
            } else {
                command.sendSuccess(() -> Component.translatable("commands.darkrpg.page.give.multiple", targetPlayers.size()), true);
            }
        }

        return Command.SINGLE_SUCCESS;
    }

    private static int removePage(CommandSourceStack command, Collection<? extends ServerPlayer> targetPlayers, LexiconPages pages) throws CommandSyntaxException {
        for(ServerPlayer player : targetPlayers) {
            LexiconGui.setOpen(player, pages,false);
            Minecraft.getInstance().getToasts().addToast(new ModToast(false));

            if (targetPlayers.size() == 1) {
                command.sendSuccess(() -> Component.translatable("commands.darkrpg.page.remove.single", targetPlayers.iterator().next().getDisplayName()), true);
            } else {
                command.sendSuccess(() -> Component.translatable("commands.darkrpg.page.remove.multiple", targetPlayers.size()), true);
            }
        }

        return Command.SINGLE_SUCCESS;
    }
}