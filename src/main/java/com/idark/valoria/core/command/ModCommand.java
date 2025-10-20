package com.idark.valoria.core.command;

import com.idark.valoria.api.unlockable.*;
import com.idark.valoria.api.unlockable.types.*;
import com.idark.valoria.core.capability.*;
import com.idark.valoria.core.command.parts.*;
import com.idark.valoria.core.network.*;
import com.idark.valoria.core.network.packets.*;
import com.idark.valoria.registries.*;
import com.mojang.brigadier.*;
import com.mojang.brigadier.context.*;
import com.mojang.brigadier.exceptions.*;
import net.minecraft.commands.*;
import net.minecraft.nbt.*;
import net.minecraft.network.chat.*;
import net.minecraft.server.level.*;
import net.minecraft.world.item.*;
import pro.komaru.tridot.api.command.*;

import java.util.*;

public class ModCommand{
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher){
        CommandArgument targets = CommandArgument.entities("targets");
        CommandArguments pages = CommandArguments.pages("pages");
        CommandArgument nihility = CommandArgument.integer("nihility");
        CommandArgument charges = CommandArgument.integer("charges", 0, 2);
        CommandBuilder codex = new CommandBuilder("codex").permission((p) -> p.hasPermission(2));
        CommandBuilder builder = new CommandBuilder("valoria").variants(
        codex.variants(
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
                }),

                new CommandVariant(CommandPart.create("addNihility"), targets, nihility).execute((p) -> {
                    addNihilityLevel(p.getSource(), targets.getPlayers(p), nihility.getInt(p));
                    return 1;
                }),
        
                new CommandVariant(CommandPart.create("decreaseNihility"), targets, nihility).execute((p) -> {
                    decreaseNihilityLevel(p.getSource(), targets.getPlayers(p), nihility.getInt(p));
                    return 1;
                }),

                new CommandVariant(CommandPart.create("setNihility"), targets, nihility).execute((p) -> {
                    setNihilityLevel(p.getSource(), targets.getPlayers(p), nihility.getInt(p));
                    return 1;
                })
        );

        dispatcher.register(builder.permission((p) -> p.hasPermission(2)).build());
    }

    private static void giveAllPages(CommandSourceStack command, Collection<? extends ServerPlayer> targetPlayers) throws CommandSyntaxException{
        for(ServerPlayer player : targetPlayers){
            if(targetPlayers.size() == 1){
                command.sendSuccess(() -> Component.translatable("commands.valoria.page.give.single", targetPlayers.iterator().next().getDisplayName()), true);
            }else{
                command.sendSuccess(() -> Component.translatable("commands.valoria.page.give.multiple", targetPlayers.size()), true);
            }

            UnlockUtils.addAll(player);
            PacketHandler.sendTo(player, new PageToastPacket(player, ItemsRegistry.page.get(), true));
        }
    }

    private static void removeAllPages(CommandSourceStack command, Collection<? extends ServerPlayer> targetPlayers) throws CommandSyntaxException{
        for(ServerPlayer player : targetPlayers){
            if(targetPlayers.size() == 1){
                command.sendSuccess(() -> Component.translatable("commands.valoria.page.remove.single", targetPlayers.iterator().next().getDisplayName()), true);
            }else{
                command.sendSuccess(() -> Component.translatable("commands.valoria.page.remove.multiple", targetPlayers.size()), true);
            }

            UnlockUtils.removeAll(player);
            PacketHandler.sendTo(player, new PageToastPacket(player, ItemsRegistry.page.get(), false));
        }
    }

    private static void givePage(CommandSourceStack command, Collection<? extends ServerPlayer> targetPlayers, Unlockable pages) throws CommandSyntaxException{
        for(ServerPlayer player : targetPlayers){
            if(targetPlayers.size() == 1){
                command.sendSuccess(() -> Component.translatable("commands.valoria.page.give.single", targetPlayers.iterator().next().getDisplayName()), true);
            }else{
                command.sendSuccess(() -> Component.translatable("commands.valoria.page.give.multiple", targetPlayers.size()), true);
            }

            UnlockUtils.add(player, pages);
            PacketHandler.sendTo(player, new UnlockableUpdatePacket(player));
            PacketHandler.sendTo(player, new PageToastPacket(player, ItemsRegistry.page.get(), true));
        }
    }

    private static void removePage(CommandSourceStack command, Collection<? extends ServerPlayer> targetPlayers, Unlockable pages) throws CommandSyntaxException{
        for(ServerPlayer player : targetPlayers){
            if(targetPlayers.size() == 1){
                command.sendSuccess(() -> Component.translatable("commands.valoria.page.remove.single", targetPlayers.iterator().next().getDisplayName()), true);
            }else{
                command.sendSuccess(() -> Component.translatable("commands.valoria.page.remove.multiple", targetPlayers.size()), true);
            }

            UnlockUtils.remove(player, pages);
            PacketHandler.sendTo(player, new UnlockableUpdatePacket(player));
            PacketHandler.sendTo(player, new PageToastPacket(player, ItemsRegistry.page.get(), false));
        }
    }

    public static void decreaseNihilityLevel(CommandSourceStack command, Collection<? extends ServerPlayer> targetPlayers, int amount){
        for(ServerPlayer player : targetPlayers){
            if(targetPlayers.size() == 1){
                command.sendSuccess(() -> Component.translatable("commands.valoria.nihility.decrease.single", amount, targetPlayers.iterator().next().getDisplayName()), true);
            }else{
                command.sendSuccess(() -> Component.translatable("commands.valoria.nihility.decrease.multiple", amount, targetPlayers.size()), true);
            }

            player.getCapability(INihilityLevel.INSTANCE).ifPresent(nihilityLevel -> nihilityLevel.decrease(player, amount));
        }
    }

    public static void addNihilityLevel(CommandSourceStack command, Collection<? extends ServerPlayer> targetPlayers, int amount){
        for(ServerPlayer player : targetPlayers){
            if(targetPlayers.size() == 1){
                command.sendSuccess(() -> Component.translatable("commands.valoria.nihility.add.single", amount, targetPlayers.iterator().next().getDisplayName()), true);
            }else{
                command.sendSuccess(() -> Component.translatable("commands.valoria.nihility.add.multiple", amount, targetPlayers.size()), true);
            }

            player.getCapability(INihilityLevel.INSTANCE).ifPresent(nihilityLevel -> nihilityLevel.modifyAmount(player, amount));
        }
    }

    public static void setNihilityLevel(CommandSourceStack command, Collection<? extends ServerPlayer> targetPlayers, int amount){
        for(ServerPlayer player : targetPlayers){
            if(targetPlayers.size() == 1){
                command.sendSuccess(() -> Component.translatable("commands.valoria.nihility.set.single", amount, targetPlayers.iterator().next().getDisplayName()), true);
            }else{
                command.sendSuccess(() -> Component.translatable("commands.valoria.nihility.set.multiple", amount, targetPlayers.size()), true);
            }

            player.getCapability(INihilityLevel.INSTANCE).ifPresent(nihilityLevel -> nihilityLevel.setAmountFromServer(player, amount));
        }
    }

    public static void setCharges(ItemStack stack, int value){
        CompoundTag nbt = stack.getOrCreateTag();
        nbt.putInt("charge", value);
    }

    private static void setCharge(CommandSourceStack command, Collection<? extends ServerPlayer> targetPlayers, int pCharge, CommandContext p) throws CommandSyntaxException{
        for(ServerPlayer player : targetPlayers){
            setCharges(player.getOffhandItem(), pCharge);
            setCharges(player.getMainHandItem(), pCharge);
            command.sendSuccess(() -> Component.translatable("commands.valoria.charges.set.add", pCharge).append(" to " + player.getName().getString()), true);
        }
    }
}