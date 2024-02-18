package com.idark.valoria.command;

import com.idark.valoria.api.unlockable.Unlockable;
import com.idark.valoria.capability.IUnlockable;
import com.idark.valoria.command.build.CommandArgument;
import com.idark.valoria.command.build.CommandBuilder;
import com.idark.valoria.command.build.CommandPart;
import com.idark.valoria.command.build.CommandVariant;
import com.idark.valoria.item.types.MagmaSwordItem;
import com.idark.valoria.client.toast.ModToast;
import com.idark.valoria.network.PacketHandler;
import com.idark.valoria.network.UnlockableUpdatePacket;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Collection;

public class ModCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        CommandArgument targets = CommandArgument.entities("targets");
        CommandArgument pages = CommandArgument.pages("pages");
        CommandArgument charges = CommandArgument.integer("charges", 0, 2);
        CommandBuilder builder = new CommandBuilder("valoria")
                .variants(
                        new CommandVariant(CommandPart.create("addPage"), targets, pages).execute((p) -> {
                            givePage(p.getSource(), targets.getPlayers(p), pages.getPages(p, "pages"));
                            return 1;
                        }),

                        new CommandVariant(CommandPart.create("removePage"), targets, pages).execute((p) -> {
                            removePage(p.getSource(), targets.getPlayers(p), pages.getPages(p, "pages"));
                            return 1;
                        }),

                        new CommandVariant(CommandPart.create("setCharge"), targets, charges).execute((p) -> {
                            setCharge(p.getSource(), targets.getPlayers(p), charges.getInt(p), p);
                            return 1;
                        })
                        );

        dispatcher.register(builder.permission((p)->p.hasPermission(2)).build());
    }

    private static int givePage(CommandSourceStack command, Collection<? extends ServerPlayer> targetPlayers, Unlockable pages) throws CommandSyntaxException {
        for(ServerPlayer player : targetPlayers) {
            useModToast(true);
            if (targetPlayers.size() == 1) {
                command.sendSuccess(() -> Component.translatable("commands.valoria.page.give.single", targetPlayers.iterator().next().getDisplayName()), true);
            } else {
                command.sendSuccess(() -> Component.translatable("commands.valoria.page.give.multiple", targetPlayers.size()), true);
            }

            player.getCapability(IUnlockable.INSTANCE, null).ifPresent((k) -> {
                k.addUnlockable(pages);
            });

            PacketHandler.sendTo(player, new UnlockableUpdatePacket(player));
        }

        return Command.SINGLE_SUCCESS;
    }

    private static int setCharge(CommandSourceStack command, Collection<? extends ServerPlayer> targetPlayers, int pCharge, CommandContext p) throws CommandSyntaxException {
        for(ServerPlayer player : targetPlayers) {
            MagmaSwordItem.setCharge(player.getOffhandItem(), pCharge);
            MagmaSwordItem.setCharge(player.getMainHandItem(), pCharge);

            command.sendSuccess(() -> Component.translatable("commands.valoria.charges.set.add", pCharge).append(" to " + player.getName().getString()), true);
        }

        return Command.SINGLE_SUCCESS;
    }

    private static int removePage(CommandSourceStack command, Collection<? extends ServerPlayer> targetPlayers, Unlockable pages) throws CommandSyntaxException {
        for(ServerPlayer player : targetPlayers) {
            useModToast(false);
            if (targetPlayers.size() == 1) {
                command.sendSuccess(() -> Component.translatable("commands.valoria.page.remove.single", targetPlayers.iterator().next().getDisplayName()), true);
            } else {
                command.sendSuccess(() -> Component.translatable("commands.valoria.page.remove.multiple", targetPlayers.size()), true);
            }

            player.getCapability(IUnlockable.INSTANCE, null).ifPresent((k) -> {
                k.removeUnlockable(pages);
            });

            PacketHandler.sendTo(player, new UnlockableUpdatePacket(player));
        }

        return Command.SINGLE_SUCCESS;
    }

    @OnlyIn(Dist.CLIENT)
    public static void useModToast(boolean pUnlock) {
        Minecraft.getInstance().getToasts().addToast(new ModToast(pUnlock));
    }
}