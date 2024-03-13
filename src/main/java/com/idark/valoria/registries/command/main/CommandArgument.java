package com.idark.valoria.registries.command.main;

import com.idark.valoria.api.unlockable.Unlockable;
import com.idark.valoria.registries.command.arguments.UnlockableArgumentType;
import com.mojang.brigadier.arguments.*;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.commands.arguments.coordinates.Vec2Argument;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

import java.util.Collection;
import java.util.Collections;

public class CommandArgument extends CommandPart {
    private ArgumentType type;
    private SuggestionProvider suggestionProvider;

    public CommandArgument(String name, ArgumentType type) {
        super(name);
        this.type = type;
    }

    public static CommandArgument blockPos(String name) {
        return new CommandArgument(name, BlockPosArgument.blockPos());
    }

    public static CommandArgument word(String name) {
        return new CommandArgument(name, StringArgumentType.word());
    }

    public static CommandArgument string(String name) {
        return new CommandArgument(name, StringArgumentType.string());
    }

    public static CommandArgument greedyString(String name) {
        return new CommandArgument(name, StringArgumentType.greedyString());
    }

    public static CommandArgument integer(String name) {

        return new CommandArgument(name, IntegerArgumentType.integer());
    }

    public static CommandArgument bool(String name) {
        return new CommandArgument(name, BoolArgumentType.bool());
    }

    public static CommandArgument doubleArg(String name) {
        return new CommandArgument(name, DoubleArgumentType.doubleArg());
    }

    public static CommandArgument integer(String name, int min, int max) {
        return new CommandArgument(name, IntegerArgumentType.integer(min, max));
    }

    public static CommandArgument integer(String name, int min) {
        return new CommandArgument(name, IntegerArgumentType.integer(min));
    }

    public static CommandArgument entities(String name) {
        return new CommandArgument(name, EntityArgument.entities());
    }

    public static CommandArgument create(String name, ArgumentType type) {
        return new CommandArgument(name, type);
    }

    public static CommandArgument players(String name) {
        return new CommandArgument(name, EntityArgument.players());
    }

    public static CommandArgument vector3(String name) {
        return new CommandArgument(name, Vec3Argument.vec3(false));
    }

    public static CommandArgument vector2(String name) {
        return new CommandArgument(name, Vec2Argument.vec2(false));
    }


    public CommandArgument suggestion(SuggestionProvider suggestionProvider) {
        this.suggestionProvider = suggestionProvider;
        return this;
    }

    public int getInt(CommandContext<CommandSourceStack> context) {
        return IntegerArgumentType.getInteger(context, getArgumentName());
    }


    public boolean getBoolean(CommandContext<CommandSourceStack> context) {
        return BoolArgumentType.getBool(context, getArgumentName());
    }


    public double getDouble(CommandContext<CommandSourceStack> context) {
        return DoubleArgumentType.getDouble(context, getArgumentName());
    }

    public String getString(CommandContext<CommandSourceStack> context) {
        return StringArgumentType.getString(context, getArgumentName());
    }

    public Vec3 getVector3(CommandContext<CommandSourceStack> context) {
        return Vec3Argument.getVec3(context, getArgumentName());
    }

    public BlockPos getBlockPos(CommandContext<CommandSourceStack> context) {
        try {
            return BlockPosArgument.getLoadedBlockPos(context, getArgumentName());
        } catch (CommandSyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Collection<? extends Entity> getEntities(CommandContext<CommandSourceStack> context) {
        try {
            return EntityArgument.getEntities(context, getArgumentName());
        } catch (CommandSyntaxException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public Collection<? extends ServerPlayer> getPlayers(CommandContext<CommandSourceStack> context) {
        try {
            return EntityArgument.getPlayers(context, getArgumentName());
        } catch (CommandSyntaxException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }


    @Override
    public ArgumentBuilder build() {
        RequiredArgumentBuilder res = Commands.argument(getArgumentName(), type);
        if (suggestionProvider != null) {
            res = res.suggests(suggestionProvider);
        }
        return res;
    }

//    public LexiconPages getPages(final CommandContext<?> context, final String name) {
//        return context.getArgument(name, LexiconPages.class);
//    }
//
//    public static CommandArgument pages(String pName) {
//        return new CommandArgument(pName, EnumArgument.enumArgument(LexiconPages.class));
//    }

    public Unlockable getPages(final CommandContext<?> context, final String name) {
        return context.getArgument(name, UnlockableArgumentType.getUnlockable(context, name).getClass());
    }

    public static CommandArgument pages(String pName) {
        return new CommandArgument(pName, UnlockableArgumentType.unlockableArgumentType());
    }
}
