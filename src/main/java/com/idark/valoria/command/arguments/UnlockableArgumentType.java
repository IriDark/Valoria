package com.idark.valoria.command.arguments;

import com.idark.valoria.api.unlockable.Unlockable;
import com.idark.valoria.api.unlockable.Unlockables;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.concurrent.CompletableFuture;

public class UnlockableArgumentType implements ArgumentType<Unlockable> {
    private static final DynamicCommandExceptionType UNKNOWN = new DynamicCommandExceptionType((obj) -> Component.translatable("gui.valoria.unknown"));

    public static Unlockable getUnlockable(final CommandContext<?> context, final String name) {
        return context.getArgument(name, Unlockable.class);
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(final CommandContext<S> context, final SuggestionsBuilder builder) {
        for (Unlockable s : Unlockables.getUnlockables())
            if (s.getId().startsWith(builder.getRemainingLowerCase()))
                builder.suggest(s.getId());
        return builder.buildFuture();
    }

    @Override
    public Unlockable parse(StringReader reader) throws CommandSyntaxException {
        ResourceLocation rl = ResourceLocation.read(reader);
        Unlockable s = Unlockables.getUnlockable(rl.toString());
        if (s == null) throw UNKNOWN.create(rl.toString());
        return s;
    }

    public static UnlockableArgumentType unlockableArgumentType() {
        return new UnlockableArgumentType();
    }
}