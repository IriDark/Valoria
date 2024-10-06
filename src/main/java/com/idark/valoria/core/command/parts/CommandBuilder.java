package com.idark.valoria.core.command.parts;

import com.mojang.brigadier.builder.*;
import net.minecraft.commands.*;

import java.util.*;
import java.util.function.*;

public class CommandBuilder extends CommandVariant {

    private String command;
    private Predicate<CommandSourceStack> permission = (p) -> true;
    private LinkedList<CommandVariant> branches = new LinkedList<>();


    public CommandBuilder(String command) {
        this.command = command;
    }

    public CommandBuilder variants(CommandVariant... branches) {
        this.branches.addAll(Arrays.asList(branches));
        return this;
    }

    public CommandBuilder permission(Predicate<CommandSourceStack> permission) {
        this.permission = permission;
        return this;
    }

    @Override
    public LiteralArgumentBuilder build() {
        ArgumentBuilder res = Commands.literal(command).requires(permission);
        for (CommandVariant branch : branches) {
            res = res.then(branch.build());
        }
        boolean hasArguments = getArguments() != null;
        if (hasArguments) {
            hasArguments = getArguments().length > 0;
        }
        if (hasArguments) {
            res = mergeArguments(getArguments(), 0);
        }
        if (getExecutable() != null) {
            res = res.executes(getExecutable());
        }
        return (LiteralArgumentBuilder) res;
    }

    public void addVariant(CommandVariant branch) {
        this.branches.add(branch);
    }

}

