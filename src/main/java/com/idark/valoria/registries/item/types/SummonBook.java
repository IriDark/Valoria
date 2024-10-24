package com.idark.valoria.registries.item.types;

import com.google.common.collect.*;
import com.idark.valoria.core.network.*;
import com.idark.valoria.core.network.packets.particle.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.entity.living.minions.AbstractMinionEntity;
import net.minecraft.*;
import net.minecraft.core.*;
import net.minecraft.network.chat.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.registries.*;
import org.jetbrains.annotations.*;

import java.util.*;
import java.util.function.*;

import static com.idark.valoria.Valoria.*;

public class SummonBook extends Item {
    private final Supplier<? extends EntityType<? extends AbstractMinionEntity>> summonedEntity;
    public final Multimap<Attribute, AttributeModifier> defaultModifiers;
    public boolean hasLimitedLife;
    /**
     * @param summoned Mob to summon, must be an extent of AbstractMinionEntity
     * @param lifetime Summoned mob lifetime, specified in Seconds
     * @param count    Count of summoned mobs
     */
    public SummonBook(Supplier<? extends EntityType<? extends AbstractMinionEntity>> summoned, int lifetime, int count, Properties pProperties) {
        super(pProperties);
        this.summonedEntity = summoned;
        this.hasLimitedLife = true;
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(AttributeRegistry.NECROMANCY_LIFETIME.get(), new AttributeModifier(BASE_NECROMANCY_LIFETIME_UUID, "Tool modifier", lifetime, AttributeModifier.Operation.ADDITION));
        builder.put(AttributeRegistry.NECROMANCY_COUNT.get(), new AttributeModifier(BASE_NECROMANCY_COUNT_UUID, "Tool modifier", count, AttributeModifier.Operation.ADDITION));
        this.defaultModifiers = builder.build();
    }

    /**
     * Summons a minions without limited lifetime
     * @param summoned Mob to summon, must be an extent of AbstractMinionEntity
     * @param count    Count of summoned mobs
     */
    public SummonBook(Supplier<? extends EntityType<? extends AbstractMinionEntity>> summoned, int count, Properties pProperties) {
        super(pProperties);
        this.summonedEntity = summoned;
        this.hasLimitedLife = false;
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(AttributeRegistry.NECROMANCY_COUNT.get(), new AttributeModifier(BASE_NECROMANCY_COUNT_UUID, "Tool modifier", count, AttributeModifier.Operation.ADDITION));
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

    private void spawnMinions(ServerLevel serverLevel, Player player){
        BlockPos blockpos = player.getOnPos().above();
        AbstractMinionEntity summoned = getDefaultType().create(player.level());
        var rand = serverLevel.random;
        double x = (double)blockpos.getX() + (rand.nextDouble() - rand.nextDouble()) * 6;
        double y = blockpos.getY() + rand.nextInt(1, 2);
        double z = (double)blockpos.getZ() + (rand.nextDouble() - rand.nextDouble()) * 6;
        BlockPos spawnPos = BlockPos.containing(new Vec3(x, y, z));
        if(summoned != null && serverLevel.isEmptyBlock(blockpos)){
            summoned.moveTo(spawnPos, 0.0F, 0.0F);
            summoned.finalizeSpawn(serverLevel, player.level().getCurrentDifficultyAt(blockpos), MobSpawnType.MOB_SUMMONED, null, null);
            summoned.setOwner(player);
            summoned.setBoundOrigin(blockpos);
            if(hasLimitedLife) summoned.setLimitedLife(getLifetime(player) + serverLevel.random.nextInt(60));
            serverLevel.addFreshEntity(summoned);
            PacketHandler.sendToTracking(serverLevel, blockpos, new MinionSummonParticlePacket(summoned.getId(), player.getOnPos().above()));
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

    public SoundEvent getUseSound() {
        return SoundsRegistry.NECROMANCER_SUMMON_AIR.get();
    }

    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entityLiving) {
        Player player = (Player) entityLiving;
        if (level instanceof ServerLevel server) {
            for (int i = 0; i < (int) (player.getAttributeValue(AttributeRegistry.NECROMANCY_COUNT.get())); ++i) {
                spawnMinions(server, player);
            }

            if (!player.isCreative()) {
                stack.hurtAndBreak(1, player, (plr) -> plr.broadcastBreakEvent(EquipmentSlot.MAINHAND));
            }

            level.playSound(null, player.blockPosition(), getUseSound(), SoundSource.PLAYERS);
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