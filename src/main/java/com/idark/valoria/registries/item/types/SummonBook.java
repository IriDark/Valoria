package com.idark.valoria.registries.item.types;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.idark.valoria.registries.AttributeRegistry;
import com.idark.valoria.registries.entity.living.AbstractMinionEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

public class SummonBook extends Item {
    private final Supplier<? extends EntityType<? extends AbstractMinionEntity>> summonedEntity;
    public final Multimap<Attribute, AttributeModifier> defaultModifiers;

    /**
     * @param summoned Mob to summon, must be an extent of AbstractMinionEntity
     * @param lifetime Summoned mob lifetime, specified in Seconds
     * @param count    Count of summoned mobs
     */
    public SummonBook(Supplier<? extends EntityType<? extends AbstractMinionEntity>> summoned, int lifetime, int count, Properties pProperties) {
        super(pProperties);
        this.summonedEntity = summoned;
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(AttributeRegistry.NECROMANCY_LIFETIME.get(), new AttributeModifier(UUID.fromString("09a12525-61a5-4d57-a125-2561a56d578e"), "Tool modifier", lifetime, AttributeModifier.Operation.ADDITION));
        builder.put(AttributeRegistry.NECROMANCY_COUNT.get(), new AttributeModifier(UUID.fromString("ed80691e-f153-4b5e-8069-1ef153bb5eed"), "Tool modifier", count, AttributeModifier.Operation.ADDITION));
        this.defaultModifiers = builder.build();
    }

    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot pEquipmentSlot) {
        return pEquipmentSlot == EquipmentSlot.MAINHAND ? defaultModifiers : super.getDefaultAttributeModifiers(pEquipmentSlot);
    }

    protected EntityType<? extends AbstractMinionEntity> getDefaultType() {
        return summonedEntity.get();
    }

    public void applyCooldown(Player playerIn) {
        for (Item item : ForgeRegistries.ITEMS) {
            if (item instanceof SummonBook) {
                playerIn.getCooldowns().addCooldown(item, 175);
            }
        }
    }

    public int getLifetime(Player player) {
        return (int) (player.getAttributeValue(AttributeRegistry.NECROMANCY_LIFETIME.get()) * 20);
    }

    private void spawnMinions(ServerLevel serverLevel, Player player) {
        BlockPos blockpos = player.getOnPos().above();
        AbstractMinionEntity summoned = getDefaultType().create(player.level());
        if (summoned != null && serverLevel.isEmptyBlock(blockpos)) {
            summoned.moveTo(blockpos, 0.0F, 0.0F);
            summoned.finalizeSpawn(serverLevel, player.level().getCurrentDifficultyAt(blockpos), MobSpawnType.MOB_SUMMONED, null, null);
            summoned.setOwner(player);
            summoned.setBoundOrigin(blockpos);
            summoned.setLimitedLife(getLifetime(player) + serverLevel.random.nextInt(60));
            serverLevel.addFreshEntity(summoned);
        }
    }

    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        if (!playerIn.isShiftKeyDown()) {
            playerIn.startUsingItem(handIn);
            return InteractionResultHolder.consume(itemstack);
        }

        return InteractionResultHolder.pass(itemstack);
    }

    public int getUseDuration(ItemStack stack) {
        return 7;
    }

    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entityLiving) {
        Player player = (Player) entityLiving;
        if (level instanceof ServerLevel server) {
            for (int i = 0; i < (int) (player.getAttributeValue(AttributeRegistry.NECROMANCY_COUNT.get())); ++i) {
                spawnMinions(server, player);
            }

            applyCooldown(player);
        }

        return stack;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, Level world, @NotNull List<Component> tooltip, @NotNull TooltipFlag flags) {
        super.appendHoverText(stack, world, tooltip, flags);
        tooltip.add(Component.translatable("tooltip.valoria.necromancy").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.empty());
        tooltip.add(Component.translatable("tooltip.valoria.summons", getDefaultType().getDescription()).withStyle(ChatFormatting.GRAY));
    }
}