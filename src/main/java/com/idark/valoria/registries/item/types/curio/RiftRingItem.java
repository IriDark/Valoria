package com.idark.valoria.registries.item.types.curio;

import com.idark.valoria.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.entity.*;
import com.idark.valoria.registries.item.types.*;
import net.minecraft.*;
import net.minecraft.core.*;
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
import net.minecraft.world.level.*;
import net.minecraft.world.level.chunk.*;
import net.minecraftforge.registries.*;
import pro.komaru.tridot.api.interfaces.*;
import pro.komaru.tridot.common.registry.item.*;
import pro.komaru.tridot.common.registry.item.components.*;
import pro.komaru.tridot.util.struct.data.*;
import top.theillusivec4.curios.api.*;

public class RiftRingItem extends ValoriaCurioItem implements CooldownNotifyItem, AbilityInputListener, TooltipComponentItem{
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
        var tag = stack.getOrCreateTag();
        tag.putInt("targetPositionX", (int)pPlayer.getX());
        tag.putInt("targetPositionY", (int)pPlayer.getY());
        tag.putInt("targetPositionZ", (int)pPlayer.getZ());
        tag.putString("targetDimension", pLevel.dimension().location().toString());

        pPlayer.displayClientMessage(Component.translatable("message.valoria.rift_ring_bound", pPlayer.getOnPos().above().toShortString()), true);
        pLevel.playSound(null, pPlayer.blockPosition(), this.getSoundEvent(), SoundSource.PLAYERS, 1.0F, 1.0F);
    }

    @Override
    public boolean isFoil(ItemStack pStack){
        var tag = pStack.getTag();
        return isBound(pStack, tag);
    }

    private boolean isBound(ItemStack pStack, CompoundTag tag){
        return pStack.hasTag() && tag.contains("targetDimension");
    }

    public Seq<TooltipComponent> getTooltips(ItemStack pStack){
        Seq<TooltipComponent> seq = Seq.with(
        new AbilityComponent(Component.translatable("tooltip.valoria.rift_ring").withStyle(ChatFormatting.GRAY), Valoria.loc("textures/gui/tooltips/rift.png"))
        );

        var tag = pStack.getTag();
        if(isBound(pStack, tag)){
            var x = tag.getInt("targetPositionX");
            var y = tag.getInt("targetPositionY");
            var z = tag.getInt("targetPositionZ");
            var dim = tag.getString("targetDimension");

            BlockPos pos = new BlockPos(x, y, z);
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
        return rmbEquip && !slot.getWearer().isShiftKeyDown();
    }

    public void applyCooldown(Player playerIn, int time){
        for(Item item : ForgeRegistries.ITEMS){
            if(item instanceof RiftRingItem){
                playerIn.getCooldowns().addCooldown(item, time);
            }
        }
    }

    @Override
    public Seq<CurioAbility> getCurioAbilities(ItemStack stack) {
        return Seq.with(
            new CurioAbility(0, Component.translatable("ability.valoria.rift_teleport"), Valoria.loc("textures/gui/tooltips/rift.png"), (player, st) -> {
                var tag = st.getOrCreateTag();
                if (!isBound(stack, tag)) {
                    player.displayClientMessage(Component.translatable("message.valoria.rift_ring_unbound"), true);
                    return;
                }

                var x = tag.getInt("targetPositionX");
                var y = tag.getInt("targetPositionY");
                var z = tag.getInt("targetPositionZ");
                var dim = tag.getString("targetDimension");
                var targetPos = new BlockPos(x, y, z);
                
                ResourceKey<Level> dimKey = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(dim));
                ServerLevel targetLevel = player.getServer().getLevel(dimKey);
                
                if (targetLevel == null) return;
                
                ChunkPos targetChunk = new ChunkPos(targetPos);
                
                targetLevel.getChunkSource().addRegionTicket(TicketType.PORTAL, targetChunk, 3, targetPos);
                targetLevel.getChunkSource().getChunkFuture(targetChunk.x, targetChunk.z, ChunkStatus.FULL, true).thenAccept(either -> {
                    either.ifLeft(chunk -> {
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

                applyCooldown(player, 800);
                player.level().playSound(null, player.blockPosition(), SoundEvents.END_PORTAL_SPAWN, SoundSource.PLAYERS, 0.1F, 1.5F);
            }),

            new CurioAbility(1, Component.translatable("ability.valoria.rift_bound"), Valoria.loc("textures/gui/tooltips/rift_bound.png"), (player, st) -> {
                boundRift(player.level(), player, stack);
            })
        );
    }
}