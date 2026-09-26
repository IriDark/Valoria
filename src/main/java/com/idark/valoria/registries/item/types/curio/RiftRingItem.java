package com.idark.valoria.registries.item.types.curio;

import com.idark.valoria.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.entity.*;
import com.idark.valoria.registries.item.types.*;
import net.minecraft.*;
import net.minecraft.core.*;
import net.minecraft.core.component.*;
import net.minecraft.core.registries.*;
import net.minecraft.nbt.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.inventory.tooltip.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.chunk.status.*;
import org.jetbrains.annotations.*;
import pro.komaru.tridot.api.interfaces.*;
import pro.komaru.tridot.common.registry.item.*;
import pro.komaru.tridot.common.registry.item.components.*;
import pro.komaru.tridot.util.struct.data.*;
import top.theillusivec4.curios.api.*;

public class RiftRingItem extends ValoriaCurioItem implements CooldownNotifyItem, AbilityInputListener, TooltipComponentItem{
    public static final String LEGACY_X = "targetPositionX";
    public static final String LEGACY_Y = "targetPositionY";
    public static final String LEGACY_Z = "targetPositionZ";
    public static final String LEGACY_DIM = "targetDimension";

    public RiftRingItem(Properties properties){
        super(properties);
    }

    @Override
    public SoundEvent getSoundEvent(){
        return SoundEvents.END_PORTAL_FRAME_FILL;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand){
        ItemStack stack = pPlayer.getItemInHand(pUsedHand);
        if (pPlayer.isShiftKeyDown()) {
            boundRift(pLevel, pPlayer, stack);
            return InteractionResultHolder.success(stack);
        }

        return super.use(pLevel, pPlayer, pUsedHand);
    }

    private void boundRift(Level pLevel, Player pPlayer, ItemStack stack){
        BlockPos pos = pPlayer.blockPosition();
        GlobalPos target = GlobalPos.of(pLevel.dimension(), pos);
        stack.set(DataComponentsRegistry.RIFT_TARGET, target);

        CustomData customData = stack.get(DataComponents.CUSTOM_DATA);
        if (customData != null && customData.contains(LEGACY_DIM)) {
            CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> {
                tag.remove(LEGACY_X);
                tag.remove(LEGACY_Y);
                tag.remove(LEGACY_Z);
                tag.remove(LEGACY_DIM);
            });
        }

        pPlayer.displayClientMessage(Component.translatable("message.valoria.rift_ring_bound", pos.toShortString()), true);
        pLevel.playSound(null, pPlayer.blockPosition(), this.getSoundEvent(), SoundSource.PLAYERS, 1.0F, 1.0F);
    }

    @Override
    public boolean isFoil(ItemStack pStack){
        return super.isFoil(pStack) || isBound(pStack);
    }

    public static boolean isBound(ItemStack pStack){
        if (pStack.has(DataComponentsRegistry.RIFT_TARGET)) {
            return true;
        }
        CustomData customData = pStack.get(DataComponents.CUSTOM_DATA);
        return customData != null && customData.contains(LEGACY_DIM);
    }

    @Nullable
    public static GlobalPos getTarget(ItemStack pStack){
        GlobalPos pos = pStack.get(DataComponentsRegistry.RIFT_TARGET);
        if (pos != null) {
            return pos;
        }
        CustomData customData = pStack.get(DataComponents.CUSTOM_DATA);
        if (customData != null && customData.contains(LEGACY_DIM)) {
            CompoundTag tag = customData.copyTag();
            String dim = tag.getString(LEGACY_DIM);
            int x = tag.getInt(LEGACY_X);
            int y = tag.getInt(LEGACY_Y);
            int z = tag.getInt(LEGACY_Z);
            ResourceKey<Level> dimKey = ResourceKey.create(Registries.DIMENSION, ResourceLocation.parse(dim));
            return GlobalPos.of(dimKey, new BlockPos(x, y, z));
        }
        return null;
    }

    @Override
    public Seq<TooltipComponent> getTooltips(ItemStack pStack){
        Seq<TooltipComponent> seq = Seq.with(
            new AbilityComponent(Component.translatable("tooltip.valoria.rift_ring").withStyle(ChatFormatting.GRAY), Valoria.loc("textures/gui/tooltips/rift.png"))
        );

        GlobalPos target = getTarget(pStack);
        if(target != null){
            BlockPos pos = target.pos();
            String dim = target.dimension().location().toString();

            var dimComp = Component.translatable("dimension." + dim.replace(":", ".")).withStyle(ChatFormatting.GRAY);
            var posComp = Component.literal(pos.toShortString()).withStyle(ChatFormatting.DARK_GRAY);
            seq.add(new TextComponent(Component.translatable("tooltip.valoria.bound_to").append(dimComp).append(" ").append(posComp)));
        }

        seq.add(new TextComponent(Component.translatable("tooltip.valoria.rmb_shift.bind").withStyle(style -> style.withFont(Valoria.FONT))));
        seq.add(new EmptyComponent(10));
        seq.add(new TextComponent(Component.translatable("tooltip.valoria.jewelry_bonus", ValoriaClient.JEWELRY_BONUSES_KEY.getKey().getDisplayName()).withStyle(ChatFormatting.GREEN)));
        return seq;
    }

    @Override
    public boolean canEquipFromUse(SlotContext slot, ItemStack stack){
        return rmbEquip && !slot.entity().isShiftKeyDown();
    }

    @Override
    public Seq<CurioAbility> getCurioAbilities(ItemStack stack) {
        return Seq.with(
            new CurioAbility(0, Component.translatable("ability.valoria.rift_teleport"), Valoria.loc("textures/gui/tooltips/rift.png"), (player, st) -> {
                GlobalPos target = getTarget(st);
                if (target == null) {
                    player.displayClientMessage(Component.translatable("message.valoria.rift_ring_unbound"), true);
                    return;
                }

                BlockPos targetPos = target.pos();
                ResourceKey<Level> dimKey = target.dimension();
                ServerLevel targetLevel = player.getServer().getLevel(dimKey);
                
                if (targetLevel == null) return;
                
                ChunkPos targetChunk = new ChunkPos(targetPos);
                
                targetLevel.getChunkSource().addRegionTicket(TicketType.PORTAL, targetChunk, 3, targetPos);
                targetLevel.getChunkSource().getChunkFuture(targetChunk.x, targetChunk.z, ChunkStatus.FULL, true).thenAccept(either -> {
                    either.ifSuccess(chunk -> {
                        targetLevel.getServer().execute(() -> {
                            RiftEntity targetRift = EntityTypeRegistry.RIFT.get().create(targetLevel);
                            RiftEntity playerRift = EntityTypeRegistry.RIFT.get().create(player.serverLevel());
                            if (targetRift != null && playerRift != null) {
                                targetRift.setConnection(playerRift);
                                targetRift.setOwner(player);
                                targetRift.setPos(targetPos.getX(), targetPos.getY(), targetPos.getZ());
                                targetLevel.addFreshEntity(targetRift);

                                playerRift.setOwner(player);
                                playerRift.setConnection(targetRift);

                                double yaw = Math.toRadians(player.getYRot());
                                double dx = -Math.sin(yaw) * 4;
                                double dz = Math.cos(yaw) * 4;
                                playerRift.setPos(player.getX() + dx, player.getY(), player.getZ() + dz);
                                playerRift.setYRot(player.getYRot());
                                player.serverLevel().addFreshEntity(playerRift);
                            }
                        });
                    });
                });

                player.level().playSound(null, player.blockPosition(), SoundEvents.END_PORTAL_SPAWN, SoundSource.PLAYERS, 0.1F, 1.5F);
            }),

            new CurioAbility(1, Component.translatable("ability.valoria.rift_bound"), Valoria.loc("textures/gui/tooltips/rift_bound.png"), (player, st) -> {
                boundRift(player.level(), player, st);
            })
        );
    }
}