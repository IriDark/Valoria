package com.idark.darkrpg.command;

import com.idark.darkrpg.DarkRPG;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DarkRPG.MOD_ID,bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CommandRegister {

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent e){
        ModCommand.register(e.getDispatcher());
    }
}
