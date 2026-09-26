package com.idark.valoria.core.command.arguments;

import com.idark.valoria.*;
import net.minecraft.commands.synchronization.*;
import net.minecraft.core.registries.*;
import net.neoforged.bus.api.*;
import net.neoforged.neoforge.registries.*;

public class ModArgumentTypes{
    public static final DeferredRegister<ArgumentTypeInfo<?, ?>> ARG_TYPES = DeferredRegister.create(Registries.COMMAND_ARGUMENT_TYPE, Valoria.ID);
    public static final DeferredHolder<ArgumentTypeInfo<?, ?>, ArgumentTypeInfo<?, ?>> UNLOCKABLE_ARG = ARG_TYPES.register("unlockable", () -> ArgumentTypeInfos.registerByClass(UnlockableArgumentType.class, SingletonArgumentInfo.contextFree(UnlockableArgumentType::unlockableArgumentType)));

    public static void register(IEventBus eventBus){
        ARG_TYPES.register(eventBus);
    }
}
