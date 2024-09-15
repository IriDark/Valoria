package com.idark.valoria.registries.command.arguments;

import com.idark.valoria.Valoria;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.ArgumentTypeInfos;
import net.minecraft.commands.synchronization.SingletonArgumentInfo;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModArgumentTypes {
    public static final DeferredRegister<ArgumentTypeInfo<?, ?>> ARG_TYPES = DeferredRegister.create(ForgeRegistries.COMMAND_ARGUMENT_TYPES, Valoria.ID);
    public static final RegistryObject<ArgumentTypeInfo<?, ?>> UNLOCKABLE_ARG = ARG_TYPES.register("unlockable", () -> ArgumentTypeInfos.registerByClass(UnlockableArgumentType.class, SingletonArgumentInfo.contextFree(UnlockableArgumentType::unlockableArgumentType)));

    public static void register(IEventBus eventBus) {
        ARG_TYPES.register(eventBus);
    }
}
