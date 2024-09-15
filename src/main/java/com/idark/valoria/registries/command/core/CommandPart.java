package com.idark.valoria.registries.command.core;

import com.mojang.brigadier.builder.ArgumentBuilder;
import net.minecraft.commands.Commands;

public class CommandPart {

    private String argumentName;

    public CommandPart(String argumentName) {
        this.argumentName = argumentName;
    }

    public static CommandPart create(String name) {
        return new CommandPart(name);
    }

    public ArgumentBuilder build() {
        return Commands.literal(getArgumentName());
    }

    public String getArgumentName() {
        return argumentName;
    }

}
