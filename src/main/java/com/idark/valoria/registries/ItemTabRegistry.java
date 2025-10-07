package com.idark.valoria.registries;

import com.idark.valoria.*;
import com.idark.valoria.registries.item.types.*;
import com.idark.valoria.registries.item.types.consumables.*;
import com.idark.valoria.registries.item.types.curio.*;
import net.minecraft.core.*;
import net.minecraft.core.registries.*;
import net.minecraft.nbt.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraft.tags.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.decoration.*;
import net.minecraft.world.item.*;
import net.minecraftforge.common.*;
import net.minecraftforge.event.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.registries.*;
import pro.komaru.tridot.api.*;
import pro.komaru.tridot.common.registry.entity.*;
import pro.komaru.tridot.common.registry.item.types.*;
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
    () -> CreativeModeTab.builder().icon(() -> new ItemStack(ItemsRegistry.pumpkinBomb.get()))
    .hideTitle()
    .title(Component.translatable("itemGroup.valoriaMiscModTab"))
    .withTabsImage(getTabsImage())
    .withTabsAfter(ItemTabRegistry.VALORIA_TOOLS.getKey())
    .backgroundSuffix("valoria_item.png").withBackgroundLocation(getBackgroundImage()).build());

    public static final RegistryObject<CreativeModeTab> VALORIA_TOOLS = CREATIVE_MODE_TABS.register("valoria_tools",
    () -> CreativeModeTab.builder().icon(() -> new ItemStack(ItemsRegistry.dreadAxe.get()))
    .hideTitle()
    .title(Component.translatable("itemGroup.valoriaToolsModTab"))
    .withTabsImage(getTabsImage())
    .withTabsAfter(ItemTabRegistry.VALORIA_CONSUMABLES.getKey())
    .backgroundSuffix("valoria_item.png").withBackgroundLocation(getBackgroundImage()).build());

    public static final RegistryObject<CreativeModeTab> VALORIA_CONSUMABLES = CREATIVE_MODE_TABS.register("valoria_consumables",
    () -> CreativeModeTab.builder().icon(() -> new ItemStack(ItemsRegistry.candyCorn.get()))
    .hideTitle()
    .title(Component.translatable("itemGroup.valoriaConsumablesModTab"))
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
        var tabKey = event.getTabKey();
        BiConsumer<Predicate<Item>, Boolean> addItems = (filter, fromBlocks) -> {
            var entries = fromBlocks ? ItemsRegistry.BLOCK_ITEMS.getEntries() : ItemsRegistry.ITEMS.getEntries();
            for (RegistryObject<Item> item : entries) {
                Item i = item.get();
                if (!new ItemStack(i).is(TagsRegistry.EXCLUDED_FROM_TAB) && filter.test(i)) {
                    event.accept(i.getDefaultInstance());
                }
            }
        };

        if (tabKey == ItemTabRegistry.VALORIA_BLOCKS_TAB.getKey()) {
            addItems.accept(i -> true, true);
            event.getParameters().holders().lookup(MiscRegistry.PAINTING_TYPES.getRegistryKey())
            .ifPresent(paintings ->
            generatePresetPaintings(event, paintings,
            holder -> holder.is(TagsRegistry.MODDED),
            CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS)
            );
        } else if (tabKey == ItemTabRegistry.VALORIA_TAB.getKey()) {
            addItems.accept(i -> !isAccessory(i) && !isArmor(i) && !(i instanceof SummonBook) && !isTool(i) && !isConsumable(i), false);
        } else if(tabKey == ItemTabRegistry.VALORIA_CONSUMABLES.getKey()){
            addItems.accept(ItemTabRegistry::isConsumable, false);
        } else if(tabKey == ItemTabRegistry.VALORIA_TOOLS.getKey()) {
            if (Utils.isDevelopment) event.accept(ItemsRegistry.debugItem);
            addItems.accept(ItemTabRegistry::isTool, false);

            event.getParameters().holders().lookup(ForgeRegistries.ENTITY_TYPES.getRegistryKey()).ifPresent(entities -> generateMinionItems(event, entities, holder -> holder.is(TagsRegistry.MINIONS), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS));
        } else if (tabKey == ItemTabRegistry.VALORIA_ARMOR_TAB.getKey()) {
            addItems.accept(ItemTabRegistry::isArmor, false);
        } else if (tabKey == ItemTabRegistry.VALORIA_ACCESSORIES_TAB.getKey()) {
            addItems.accept(ItemTabRegistry::isAccessory, false);
        }
    }

    public static boolean isArmor(Item i) {
        return i instanceof ArmorItem;
    }

    public static boolean isAccessory(Item i) {
        return i instanceof ICurioItem || i instanceof AbstractTalismanItem || i.getDefaultInstance().is(Tags.Items.TOOLS_SHIELDS);
    }

    public static boolean isTool(Item i) {
        return i.getDefaultInstance().is(ItemTags.TOOLS) || i.getDefaultInstance().is(Tags.Items.TOOLS) || i.getDefaultInstance().is(Tags.Items.TOOLS_CROSSBOWS) || i.getDefaultInstance().is(Tags.Items.TOOLS_BOWS);
    }

    public static boolean isConsumable(Item i) {
        return i instanceof AbstractConsumableItem || i instanceof ValoriaFood || i instanceof PlaceableDrinkItem || i.isEdible();
    }

    @SuppressWarnings("unchecked")
    private static void generateMinionItems(CreativeModeTab.Output output, HolderLookup.RegistryLookup<EntityType<?>> entityLookup, Predicate<Holder<EntityType<?>>> predicate, CreativeModeTab.TabVisibility visibility){
        output.accept(new ItemStack(ItemsRegistry.summonBook.get()));
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