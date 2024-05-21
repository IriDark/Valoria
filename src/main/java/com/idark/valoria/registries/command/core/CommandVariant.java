package com.idark.valoria.registries.command.core;

import com.mojang.brigadier.*;
import com.mojang.brigadier.builder.*;
import net.minecraft.commands.*;

public class CommandVariant{

    private CommandPart[] arguments;
    private Command<CommandSourceStack> executable;


    public CommandVariant(CommandPart... arguments){
        this.arguments = arguments;
    }

    public static CommandVariant arguments(CommandPart... arguments){
        return new CommandVariant(arguments);
    }

    public CommandVariant execute(Command<CommandSourceStack> executable){
        this.executable = executable;
        return this;
    }

    public CommandPart[] getArguments(){
        return arguments;
    }

    public CommandVariant setArguments(CommandPart... arguments){
        this.arguments = arguments;
        return this;
    }

    public Command<CommandSourceStack> getExecutable(){
        return executable;
    }

    public ArgumentBuilder build(){
        return mergeArguments(arguments, 0);
    }

    public ArgumentBuilder mergeArguments(CommandPart[] arguments, int index){
        if(index < arguments.length - 1){
            ArgumentBuilder next = mergeArguments(arguments, index + 1);
            return arguments[index].build().then(next);
        }else{
            return arguments[index].build().executes(executable);
        }
    }
}
