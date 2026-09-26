package com.idark.valoria.registries.item.types;

import com.idark.valoria.*;
import net.minecraft.core.*;
import net.minecraft.core.dispenser.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.gameevent.*;
import net.neoforged.bus.api.*;
import net.neoforged.fml.common.*;
import net.neoforged.fml.event.lifecycle.*;
import org.jetbrains.annotations.*;

import java.util.*;
import java.util.function.*;

public class TexturedSpawnEggItem extends SpawnEggItem{
    private static final List<TexturedSpawnEggItem> MOD_EGGS = new ArrayList<>();
    private static final Map<EntityType<? extends Mob>, TexturedSpawnEggItem> TYPE_MAP = new IdentityHashMap<>();
    private final Supplier<? extends EntityType<? extends Mob>> typeSupplier;

    public TexturedSpawnEggItem(Supplier<? extends EntityType<? extends Mob>> type, Properties props){
        super(null, 0, 0, props);
        this.typeSupplier = type;
        MOD_EGGS.add(this);
    }

    @Override
    public EntityType<?> getType(ItemStack stack){
        EntityType<?> type = super.getType(stack);
        return type != null ? type : typeSupplier.get();
    }

    @Nullable
    protected DispenseItemBehavior createDispenseBehavior(){
        return DEFAULT_DISPENSE_BEHAVIOR;
    }

    @Nullable
    public static SpawnEggItem fromEntityType(@Nullable EntityType<?> type){
        SpawnEggItem ret = TYPE_MAP.get(type);
        return ret != null ? ret : SpawnEggItem.byId(type);
    }

    @Override
    protected EntityType<?> getDefaultType(){
        return this.typeSupplier.get();
    }

    private static final DispenseItemBehavior DEFAULT_DISPENSE_BEHAVIOR = (source, stack) -> {
        Direction face = source.state().getValue(DispenserBlock.FACING);
        EntityType<?> type = ((SpawnEggItem)stack.getItem()).getType(stack);

        try{
            type.spawn(source.level(), stack, null, source.pos().relative(face), MobSpawnType.DISPENSER, face != Direction.UP, false);
        }catch(Exception exception){
            DispenseItemBehavior.LOGGER.error("Error while dispensing spawn egg from dispenser at {}", source.pos(), exception);
            return ItemStack.EMPTY;
        }

        stack.shrink(1);
        source.level().gameEvent(GameEvent.ENTITY_PLACE, source.pos(), GameEvent.Context.of(source.state()));
        return stack;
    };

    @EventBusSubscriber(modid = Valoria.ID, bus = EventBusSubscriber.Bus.MOD)
    private static class CommonHandler{
        @SubscribeEvent
        public static void onCommonSetup(FMLCommonSetupEvent event){
            MOD_EGGS.forEach(egg ->
            {
                DispenseItemBehavior dispenseBehavior = egg.createDispenseBehavior();
                if(dispenseBehavior != null){
                    DispenserBlock.registerBehavior(egg, dispenseBehavior);
                }

                TYPE_MAP.put(egg.typeSupplier.get(), egg);
            });
        }
    }
}