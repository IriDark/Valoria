package com.idark.valoria;

import com.idark.valoria.core.capability.IUnlockable;
import com.idark.valoria.core.capability.UnloackbleCap;
import com.idark.valoria.core.network.PacketHandler;
import com.idark.valoria.core.network.packets.UnlockableUpdatePacket;
import com.idark.valoria.registries.ItemsRegistry;
import com.idark.valoria.registries.TagsRegistry;
import com.idark.valoria.util.RandomUtil;
import com.idark.valoria.util.ValoriaUtils;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.BasicItemListing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.ShieldBlockEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.List;
import java.util.stream.Stream;

@SuppressWarnings("removal")
public class Events {

    @SubscribeEvent
    public void attachEntityCaps(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player)
            event.addCapability(new ResourceLocation(Valoria.ID, "pages"), new UnloackbleCap());
    }

    @SubscribeEvent
    public void onTooltip(ItemTooltipEvent e) {
        if (ValoriaUtils.isIDE) {
            ItemStack itemStack = e.getItemStack();
            Stream<ResourceLocation> itemTagStream = itemStack.getTags().map(TagKey::location);
            if (Minecraft.getInstance().options.advancedItemTooltips) {
                if (Screen.hasControlDown()) {
                    if (!itemStack.getTags().toList().isEmpty()) {
                        e.getToolTip().add(Component.empty());
                        e.getToolTip().add(Component.literal("ItemTags: " + itemTagStream.toList()).withStyle(ChatFormatting.DARK_GRAY));
                    }

                    if (itemStack.getItem() instanceof BlockItem blockItem) {
                        BlockState blockState = blockItem.getBlock().defaultBlockState();
                        Stream<ResourceLocation> blockTagStream = blockState.getTags().map(TagKey::location);
                        if (!blockState.getTags().map(TagKey::location).toList().isEmpty()) {
                            if (itemStack.getTags().toList().isEmpty())
                                e.getToolTip().add(Component.empty());

                            e.getToolTip().add(Component.literal("BlockTags: " + blockTagStream.toList()).withStyle(ChatFormatting.DARK_GRAY));
                        }
                    }
                } else if (!itemStack.getTags().toList().isEmpty() || itemStack.getItem() instanceof BlockItem blockItem && !blockItem.getBlock().defaultBlockState().getTags().toList().isEmpty()) {
                    e.getToolTip().add(Component.empty());
                    e.getToolTip().add(Component.literal("Press [Control] to get tags info").withStyle(ChatFormatting.GRAY));
                }
            }
        }
    }

    @SubscribeEvent
    public void disableBlock(ShieldBlockEvent event) {
        if (event.getDamageSource().getDirectEntity() instanceof Player player) {
            LivingEntity mob = event.getEntity();
            ItemStack weapon = player.getMainHandItem();
            if (!weapon.isEmpty() && weapon.is(TagsRegistry.CAN_DISABLE_SHIELD) && mob instanceof Player attacked) {
                attacked.disableShield(true);
            }
        }
    }
    
    @SubscribeEvent
    public void critDamage(CriticalHitEvent event) {
        if (CuriosApi.getCuriosHelper().findEquippedCurio(ItemsRegistry.RUNE_OF_ACCURACY.get(), event.getEntity()).isPresent()) {
            if (RandomUtil.percentChance(0.1f)) {
                event.getTarget().hurt(event.getEntity().level().damageSources().playerAttack(event.getEntity()), (float) (event.getEntity().getAttributeValue(Attributes.ATTACK_DAMAGE) * 1.5f));
            }
        }
    }

    @SubscribeEvent
    public void onClone(PlayerEvent.Clone event) {
        Capability<IUnlockable> PAGE = IUnlockable.INSTANCE;

        event.getOriginal().reviveCaps();
        event.getEntity().getCapability(PAGE).ifPresent((k) -> event.getOriginal().getCapability(PAGE).ifPresent((o) ->
                ((INBTSerializable<CompoundTag>) k).deserializeNBT(((INBTSerializable<CompoundTag>) o).serializeNBT())));
        if (!event.getEntity().level().isClientSide) {
            PacketHandler.sendTo((ServerPlayer) event.getEntity(), new UnlockableUpdatePacket(event.getEntity()));
        }
    }

    @SubscribeEvent
    public void registerCustomAI(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof LivingEntity && !event.getLevel().isClientSide) {
            if (event.getEntity() instanceof Player) {
                PacketHandler.sendTo((ServerPlayer) event.getEntity(), new UnlockableUpdatePacket((Player) event.getEntity()));
            }
        }
    }

    @SubscribeEvent
    public static void onVillagerTrades(VillagerTradesEvent event) {
        Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
        VillagerProfession profession = event.getType();
        ResourceLocation professionKey = ForgeRegistries.VILLAGER_PROFESSIONS.getKey(profession);
        if (professionKey != null) {
            if (professionKey.getPath().equals("toolsmith")) {
                trades.clear();
                trades.get(1).add(itemPurchase(6, ItemsRegistry.IRON_RING.get(), 1, 4, 2));
                trades.get(2).add(itemPurchase(16, ItemsRegistry.IRON_GLOVES.get(), 1, 1, 12));
                trades.get(1).add(itemPurchase(4, ItemsRegistry.IRON_CHAIN.get(), 1, 2, 6));
                trades.get(3).add(itemPurchase(18, ItemsRegistry.IRON_RING_EMERALD.get(), 1, 1, 12));
            }

            if (professionKey.getPath().equals("weaponsmith")) {
                trades.get(2).add(itemPurchase(14, ItemsRegistry.IRON_KATANA.get(), 1, 1, 12));
                trades.get(1).add(itemPurchase(6, ItemsRegistry.IRON_RAPIER.get(), 1, 1, 6));
                trades.get(1).add(itemPurchase(2, ItemsRegistry.CLUB.get(), 1, 2, 6));
                trades.get(2).add(itemPurchase(22, ItemsRegistry.IRON_SCYTHE.get(), 1, 1, 16));
            }

            if (professionKey.getPath().equals("librarian")) {
                trades.get(1).add(itemPurchase(4, ItemsRegistry.LEXICON.get(), 1, 12, 5));
            }
        }
    }


    @SubscribeEvent
    public static void onWandererTrades(WandererTradesEvent event) {
        List<VillagerTrades.ItemListing> trades = event.getGenericTrades();
        trades.add(itemSell(4, ItemsRegistry.IRON_RING.get(), 1, 12));
        trades.add(itemPurchase(6, ItemsRegistry.KARUSAKAN_ROOT.get(), 1, 2, 12));
        trades.add(itemPurchase(6, ItemsRegistry.GAIB_ROOT.get(), 1, 2, 12));
        trades.add(itemPurchase(4, ItemsRegistry.ALOE_BANDAGE.get(), 2, 4, 6));
        trades.add(itemPurchase(4, ItemsRegistry.ALOE_PIECE.get(), 1, 2, 12));
    }

    public static BasicItemListing itemPurchase(int pEmeralds, ItemLike item, int count, int maxTrades, int xp) {
        return new BasicItemListing(new ItemStack(item, count), new ItemStack(Items.EMERALD, pEmeralds), maxTrades, xp, 0.05F);
    }

    public static BasicItemListing itemSell(int pEmeralds, ItemLike item, int maxTrades, int xp) {
        return new BasicItemListing(pEmeralds, new ItemStack(item), maxTrades, xp, 0.05F);
    }
}