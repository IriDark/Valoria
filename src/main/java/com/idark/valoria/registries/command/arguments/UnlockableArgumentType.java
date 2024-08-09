package com.idark.valoria.registries.command.arguments;

import com.idark.valoria.api.unlockable.*;
import com.mojang.brigadier.*;
import com.mojang.brigadier.arguments.*;
import com.mojang.brigadier.context.*;
import com.mojang.brigadier.exceptions.*;
import com.mojang.brigadier.suggestion.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;

import java.util.concurrent.*;

public class UnlockableArgumentType implements ArgumentType<Unlockable>{
    private static final DynamicCommandExceptionType UNKNOWN = new DynamicCommandExceptionType((obj) -> Component.translatable("gui.valoria.unknown"));

    public static Unlockable getUnlockable(final CommandContext<?> context, final String name){
        return context.getArgument(name, Unlockable.class);
    }

    public static UnlockableArgumentType unlockableArgumentType(){
        return new UnlockableArgumentType();
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(final CommandContext<S> context, final SuggestionsBuilder builder){
        for(Unlockable s : Unlockables.getUnlockables()){
            if(s.getId().startsWith(builder.getRemainingLowerCase())){
                builder.suggest(s.getId());
            }
        }
        return builder.buildFuture();
    }

    @Override
    public Unlockable parse(StringReader reader) throws CommandSyntaxException{
        ResourceLocation location = ResourceLocation.read(reader);
        Unlockable unlockable = Unlockables.getUnlockable(location.toString());
        if(unlockable == null){
            throw UNKNOWN.create(location.toString());
        }

        return unlockable;
    }
}