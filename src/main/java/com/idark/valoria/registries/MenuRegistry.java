package com.idark.valoria.registries;

import com.idark.valoria.*;
import com.idark.valoria.client.ui.menus.*;
import net.minecraft.core.*;
import net.minecraft.core.registries.*;
import net.minecraft.world.inventory.*;
import net.minecraft.world.level.*;
import net.neoforged.bus.api.*;
import net.neoforged.neoforge.common.extensions.*;
import net.neoforged.neoforge.registries.*;

public class MenuRegistry{
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(Registries.MENU, Valoria.ID);
    public static final DeferredHolder<MenuType<?>, MenuType<JewelryMenu>> JEWELRY_MENU = MENUS.register("jewelry_menu", () -> IMenuTypeExtension.create((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                Level world = inv.player.getCommandSenderWorld();
                return new JewelryMenu(windowId, world, pos, inv, inv.player);
            })
    );

    public static final DeferredHolder<MenuType<?>, MenuType<AlchemyStationMenu>> ALCHEMY = MENUS.register("alchemy", () -> IMenuTypeExtension.create(AlchemyStationMenu::new));

    public static final DeferredHolder<MenuType<?>, MenuType<HeavyWorkbenchMenu>> HEAVY_WORKBENCH = MENUS.register("heavy_workbench", () -> IMenuTypeExtension.create(HeavyWorkbenchMenu::new));
    public static final DeferredHolder<MenuType<?>, MenuType<KilnMenu>> KILN_MENU = MENUS.register("kiln_menu", () -> IMenuTypeExtension.create((windowId, inv, data) -> new KilnMenu(windowId, inv)));
    public static final DeferredHolder<MenuType<?>, MenuType<ManipulatorMenu>> MANIPULATOR_MENU = MENUS.register("manipulator_menu", () -> IMenuTypeExtension.create((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                Level world = inv.player.getCommandSenderWorld();
                return new ManipulatorMenu(windowId, world, pos, inv, inv.player);
            })
    );

    public static final DeferredHolder<MenuType<?>, MenuType<SoulInfuserMenu>> SOUL_INFUSER_MENU = MENUS.register("soul_infuser_menu", () -> IMenuTypeExtension.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level world = inv.player.getCommandSenderWorld();
        return new SoulInfuserMenu(windowId, world, pos, inv, inv.player);
    })
    );

    public static final DeferredHolder<MenuType<?>, MenuType<KegMenu>> KEG_MENU = MENUS.register("keg_menu", () -> IMenuTypeExtension.create((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                Level world = inv.player.getCommandSenderWorld();
                return new KegMenu(windowId, world, pos, inv.player, inv);
            })
    );

    public static void register(IEventBus eventBus){
        MENUS.register(eventBus);
    }
}