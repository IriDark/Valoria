package com.idark.valoria.core.command;

import com.idark.valoria.*;
import net.minecraftforge.event.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.fml.common.*;

@Mod.EventBusSubscriber(modid = Valoria.ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CommandRegister {

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent e) {
        ModCommand.register(e.getDispatcher());
    }
}
