package com.idark.valoria.registries;

import com.idark.valoria.Valoria;
import com.idark.valoria.registries.menus.JewelryMenu;
import com.idark.valoria.registries.menus.ManipulatorMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MenuRegistry{
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, Valoria.ID);

    public static final RegistryObject<MenuType<JewelryMenu>> JEWELRY_MENU = MENUS.register("jewelry_menu",
    () -> IForgeMenuType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level world = inv.player.getCommandSenderWorld();
        return new JewelryMenu(windowId, world, pos, inv, inv.player);
    })
    );

    public static final RegistryObject<MenuType<ManipulatorMenu>> MANIPULATOR_MENU = MENUS.register("manipulator_menu",
    () -> IForgeMenuType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level world = inv.player.getCommandSenderWorld();
        return new ManipulatorMenu(windowId, world, pos, inv, inv.player);
    })
    );

    public static void register(IEventBus eventBus){
        MENUS.register(eventBus);
    }
}