package com.idark.valoria.core.command;

import com.idark.valoria.Valoria;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Valoria.ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CommandRegister{

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent e){
        ModCommand.register(e.getDispatcher());
    }
}
