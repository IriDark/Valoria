package com.idark.valoria.core.command;

import com.idark.valoria.*;
import net.neoforged.bus.api.*;
import net.neoforged.fml.common.*;
import net.neoforged.neoforge.event.*;

@EventBusSubscriber(modid = Valoria.ID, bus = EventBusSubscriber.Bus.GAME)
public class CommandRegister{

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent e){
        ModCommand.register(e.getDispatcher());
    }
}
