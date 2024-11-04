package com.idark.valoria.registries;

import com.idark.valoria.*;
import com.idark.valoria.client.ui.menus.*;
import net.minecraft.core.*;
import net.minecraft.world.inventory.*;
import net.minecraft.world.level.*;
import net.minecraftforge.common.extensions.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.registries.*;

public class MenuRegistry {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, Valoria.ID);
    public static final RegistryObject<MenuType<JewelryMenu>> JEWELRY_MENU = MENUS.register("jewelry_menu", () -> IForgeMenuType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level world = inv.player.getCommandSenderWorld();
        return new JewelryMenu(windowId, world, pos, inv, inv.player);
        })
    );

    public static final RegistryObject<MenuType<ArchaeologyMenu>> ARCHAEOLOGY_MENU = MENUS.register("archaeology_menu", () -> IForgeMenuType.create((windowId, inv, data) -> new ArchaeologyMenu(windowId, inv)));
    public static final RegistryObject<MenuType<ManipulatorMenu>> MANIPULATOR_MENU = MENUS.register("manipulator_menu", () -> IForgeMenuType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level world = inv.player.getCommandSenderWorld();
        return new ManipulatorMenu(windowId, world, pos, inv, inv.player);
        })
    );

    public static final RegistryObject<MenuType<KegMenu>> KEG_MENU = MENUS.register("keg_menu", () -> IForgeMenuType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level world = inv.player.getCommandSenderWorld();
        return new KegMenu(windowId, world, pos, inv.player, inv);
        })
    );

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}