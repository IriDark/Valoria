package com.idark.valoria.core.command.parts;

import com.mojang.brigadier.builder.*;
import net.minecraft.commands.*;

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