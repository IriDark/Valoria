package com.idark.valoria.core.command.parts;

import com.idark.valoria.api.unlockable.*;
import com.idark.valoria.core.command.arguments.*;
import com.mojang.brigadier.arguments.*;
import com.mojang.brigadier.context.*;
import pro.komaru.tridot.api.command.*;

public class CommandArguments extends CommandArgument{

    public CommandArguments(String name, ArgumentType type){
        super(name, type);
    }

    public static CommandArguments pages(String pName){
        return new CommandArguments(pName, UnlockableArgumentType.unlockableArgumentType());
    }

    public Unlockable getPages(final CommandContext<?> context, final String name){
        return context.getArgument(name, UnlockableArgumentType.getUnlockable(context, name).getClass());
    }
}
