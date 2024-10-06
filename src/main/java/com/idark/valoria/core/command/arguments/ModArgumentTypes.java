package com.idark.valoria.core.command.arguments;

import com.idark.valoria.*;
import net.minecraft.commands.synchronization.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.registries.*;

public class ModArgumentTypes {
    public static final DeferredRegister<ArgumentTypeInfo<?, ?>> ARG_TYPES = DeferredRegister.create(ForgeRegistries.COMMAND_ARGUMENT_TYPES, Valoria.ID);
    public static final RegistryObject<ArgumentTypeInfo<?, ?>> UNLOCKABLE_ARG = ARG_TYPES.register("unlockable", () -> ArgumentTypeInfos.registerByClass(UnlockableArgumentType.class, SingletonArgumentInfo.contextFree(UnlockableArgumentType::unlockableArgumentType)));

    public static void register(IEventBus eventBus) {
        ARG_TYPES.register(eventBus);
    }
}
