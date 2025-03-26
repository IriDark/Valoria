package com.idark.valoria.registries;

import com.idark.valoria.*;
import com.idark.valoria.registries.item.types.*;
import net.minecraft.core.*;
import net.minecraft.core.registries.*;
import net.minecraft.nbt.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.decoration.*;
import net.minecraft.world.item.*;
import net.minecraftforge.event.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.registries.*;
import pro.komaru.tridot.api.*;
import pro.komaru.tridot.common.registry.entity.*;
import pro.komaru.tridot.util.*;
import top.theillusivec4.curios.api.type.capability.*;

import java.util.*;
import java.util.function.*;

@Mod.EventBusSubscriber(modid = Valoria.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public abstract class ItemTabRegistry{
    private static final Comparator<Holder<PaintingVariant>> PAINTING_COMPARATOR = Comparator.comparing(Holder::value, Comparator.<PaintingVariant>comparingInt((p_270004_) -> p_270004_.getHeight() * p_270004_.getWidth()).thenComparing(PaintingVariant::getWidth));
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Valoria.ID);

    public static final RegistryObject<CreativeModeTab> VALORIA_BLOCKS_TAB = CREATIVE_MODE_TABS.register("valoria_blocks",
    () -> CreativeModeTab.builder().icon(() -> new ItemStack(BlockRegistry.jewelerTable.get()))
    .hideTitle()
    .title(Component.translatable("itemGroup.valoriaBlocksModTab"))
    .withTabsImage(getTabsImage())
    .backgroundSuffix("valoria_item.png").withBackgroundLocation(getBackgroundImage()).build());

    public static final RegistryObject<CreativeModeTab> VALORIA_TAB = CREATIVE_MODE_TABS.register("valoria_misc",
    () -> CreativeModeTab.builder().icon(() -> new ItemStack(ItemsRegistry.suspciousGem.get()))
    .hideTitle()
    .title(Component.translatable("itemGroup.valoriaMiscModTab"))
    .withTabsImage(getTabsImage())
    .withTabsAfter(ItemTabRegistry.VALORIA_ARMOR_TAB.getKey())
    .backgroundSuffix("valoria_item.png").withBackgroundLocation(getBackgroundImage()).build());

    public static final RegistryObject<CreativeModeTab> VALORIA_ARMOR_TAB = CREATIVE_MODE_TABS.register("valoria_armor",
    () -> CreativeModeTab.builder().icon(() -> new ItemStack(ItemsRegistry.etherealHelmet.get()))
    .hideTitle()
    .title(Component.translatable("itemGroup.valoriaArmorModTab"))
    .withTabsImage(getTabsImage())
    .withTabsAfter(ItemTabRegistry.VALORIA_ACCESSORIES_TAB.getKey())
    .backgroundSuffix("valoria_item.png").withBackgroundLocation(getBackgroundImage()).build());

    public static final RegistryObject<CreativeModeTab> VALORIA_ACCESSORIES_TAB = CREATIVE_MODE_TABS.register("valoria_accessories",
    () -> CreativeModeTab.builder().icon(() -> new ItemStack(ItemsRegistry.goldenRingRuby.get()))
    .hideTitle()
    .title(Component.translatable("itemGroup.valoriaAccessoriesModTab"))
    .withTabsImage(getTabsImage())
    .withTabsAfter(ItemTabRegistry.VALORIA_BLOCKS_TAB.getKey())
    .backgroundSuffix("valoria_item.png").withBackgroundLocation(getBackgroundImage()).build());

    public static ResourceLocation getBackgroundImage(){
        return new ResourceLocation(Valoria.ID, "textures/gui/container/tab_valoria_item_legacy.png");
    }

    public static ResourceLocation getTabsImage(){
        return new ResourceLocation(Valoria.ID, "textures/gui/container/tabs_valoria_legacy.png");
    }

    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TABS.register(eventBus);
    }

    public static void addCreative(BuildCreativeModeTabContentsEvent event){
        if(event.getTabKey() == ItemTabRegistry.VALORIA_BLOCKS_TAB.getKey()){
            for(RegistryObject<Item> item : ItemsRegistry.BLOCK_ITEMS.getEntries()){
                if(!new ItemStack(item.get()).is(TagsRegistry.EXCLUDED_FROM_TAB)) event.accept(item.get());
            }

            event.getParameters().holders().lookup(MiscRegistry.PAINTING_TYPES.getRegistryKey()).ifPresent((p_270026_) -> generatePresetPaintings(event, p_270026_, (p_270037_) -> p_270037_.is(TagsRegistry.MODDED), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS));
        }

        if(event.getTabKey() == ItemTabRegistry.VALORIA_TAB.getKey()){
            if(Utils.isDevelopment) event.accept(ItemsRegistry.debugItem);
            for(RegistryObject<Item> item : ItemsRegistry.ITEMS.getEntries()){
                if(!new ItemStack(item.get()).is(TagsRegistry.EXCLUDED_FROM_TAB)){
                    if(!(item.get() instanceof ICurioItem || item.get() instanceof ArmorItem)){
                        if(item.get() instanceof SummonBook){
                            event.accept(item.get().getDefaultInstance());
                            event.getParameters().holders().lookup(ForgeRegistries.ENTITY_TYPES.getRegistryKey()).ifPresent(entityLookup -> generateMinionItems(event, entityLookup, (holder) -> holder.is(TagsRegistry.MINIONS), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS));
                        }else{
                            event.accept(item.get().getDefaultInstance());
                        }
                    }
                }
            }
        }

        if(event.getTabKey() == ItemTabRegistry.VALORIA_ARMOR_TAB.getKey()){
            for(RegistryObject<Item> item : ItemsRegistry.ITEMS.getEntries()){
                if(!new ItemStack(item.get()).is(TagsRegistry.EXCLUDED_FROM_TAB)){
                    if(item.get() instanceof ArmorItem){
                        event.accept(item.get().getDefaultInstance());
                    }
                }
            }
        }

        if(event.getTabKey() == ItemTabRegistry.VALORIA_ACCESSORIES_TAB.getKey()){
            for(RegistryObject<Item> item : ItemsRegistry.ITEMS.getEntries()){
                if(!new ItemStack(item.get()).is(TagsRegistry.EXCLUDED_FROM_TAB)){
                    if(item.get() instanceof ICurioItem){
                        event.accept(item.get().getDefaultInstance());
                    }
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    private static void generateMinionItems(CreativeModeTab.Output output, HolderLookup.RegistryLookup<EntityType<?>> entityLookup, Predicate<Holder<EntityType<?>>> predicate, CreativeModeTab.TabVisibility visibility){
        entityLookup.listElements()
                .filter(predicate)
                .forEach(holder -> {
                    ItemStack itemStack = new ItemStack(ItemsRegistry.summonBook.get());
                    CompoundTag tag = itemStack.getOrCreateTagElement("EntityTag");
                    SummonBook.storeVariant(tag, holder);
                    SummonBook.setColor(itemStack, Col.colorToDecimal(AbstractMinionEntity.getColor((EntityType<? extends AbstractMinionEntity>)holder.get())));
                    output.accept(itemStack, visibility);
                });
    }


    private static void generatePresetPaintings(CreativeModeTab.Output pOutput, HolderLookup.RegistryLookup<PaintingVariant> pPaintingVariants, Predicate<Holder<PaintingVariant>> pPredicate, CreativeModeTab.TabVisibility pTabVisibility){
        pPaintingVariants.listElements().filter(pPredicate).sorted(PAINTING_COMPARATOR).forEach((p_269979_) -> {
            ItemStack itemstack = new ItemStack(Items.PAINTING);
            CompoundTag compoundtag = itemstack.getOrCreateTagElement("EntityTag");
            Painting.storeVariant(compoundtag, p_269979_);
            pOutput.accept(itemstack, pTabVisibility);
        });
    }
}