package com.idark.valoria.registries.item.types;

import com.idark.valoria.*;
import com.idark.valoria.core.network.*;
import com.idark.valoria.core.network.packets.particle.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.util.*;
import net.minecraft.*;
import net.minecraft.core.*;
import net.minecraft.nbt.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
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
import pro.komaru.tridot.common.registry.entity.*;
import pro.komaru.tridot.util.*;

import java.util.*;

import static net.minecraftforge.registries.ForgeRegistries.Keys.ENTITY_TYPES;

public class SummonBook extends Item {
    private static final ResourceKey<EntityType<?>> DEFAULT_VARIANT = ResourceKey.create(ENTITY_TYPES, Valoria.loc("undead"));

    protected final int slotCost;

    public SummonBook(Properties pProperties, int slotCost){
        super(pProperties.stacksTo(1));
        this.slotCost = slotCost;
    }

    public SummonBook(Properties pProperties){
        this(pProperties, 1);
    }

    public int getSlotCost() {
        return slotCost;
    }

    protected EntityType<?> getDefaultType(ItemStack stack){
        String entityId = stack.getOrCreateTagElement("EntityTag").getString("id");
        if(entityId.isEmpty()) return ForgeRegistries.ENTITY_TYPES.getValue(DEFAULT_VARIANT.location());
        ResourceLocation entityLocation = new ResourceLocation(entityId);
        return ForgeRegistries.ENTITY_TYPES.getValue(entityLocation);
    }

    public static void storeVariant(CompoundTag pTag, Holder<EntityType<?>> pType){
        pTag.putString("id", pType.unwrapKey().orElse(DEFAULT_VARIANT).location().toString());
    }

    public static void setColor(ItemStack pStack, int pColor){
        pStack.getOrCreateTagElement("DisplayColor").putInt("color", pColor);
    }

    public static int getColor(ItemStack pStack){
        CompoundTag compoundtag = pStack.getTagElement("DisplayColor");
        return compoundtag != null && compoundtag.contains("color", 99) ? compoundtag.getInt("color") : Col.toDecimal(Pal.lightViolet);
    }

    public void applyCooldown(Player playerIn){
        for(Item item : ForgeRegistries.ITEMS){
            if(item instanceof SummonBook){
                playerIn.getCooldowns().addCooldown(item, 10);
            }
        }
    }

    protected boolean checkAndClearCapacity(ServerLevel serverLevel, Player player) {
        List<AbstractMinionEntity> activeMinions = serverLevel.getEntitiesOfClass(
            AbstractMinionEntity.class,
            player.getBoundingBox().inflate(48),
            minion -> minion.getOwner() == player
        );

        int maxMinions = (int)player.getAttributeValue(AttributeReg.NECROMANCY_COUNT.get());
        if (this.slotCost > maxMinions) {
            player.displayClientMessage(Component.translatable("message.valoria.not_enough_minion_slots").withStyle(ChatFormatting.RED), true);
            return false;
        }

        int currentSlots = activeMinions.stream().mapToInt(m -> m.getPersistentData().contains("MinionSlots") ? m.getPersistentData().getInt("MinionSlots") : 1).sum();
        while (currentSlots + this.slotCost > maxMinions && !activeMinions.isEmpty()) {
            AbstractMinionEntity oldest = activeMinions.stream()
                .max(Comparator.comparingInt(e -> e.tickCount))
                .orElse(null);

            int freed = oldest.getPersistentData().contains("MinionSlots") ? oldest.getPersistentData().getInt("MinionSlots") : 1;
            oldest.discard();
            activeMinions.remove(oldest);
            currentSlots -= freed;
        }

        return true;
    }

    protected void spawnMinion(ServerLevel serverLevel, Player player, ItemStack stack){
        if(!checkAndClearCapacity(serverLevel, player)) return;

        BlockPos blockpos = player.getOnPos().above();
        Entity base = getDefaultType(stack).create(player.level());
        if(base instanceof AbstractMinionEntity summoned){
            var rand = serverLevel.random;
            double x = (double)blockpos.getX() + (rand.nextDouble() - rand.nextDouble()) * 4;
            double y = blockpos.getY() + rand.nextInt(1, 2);
            double z = (double)blockpos.getZ() + (rand.nextDouble() - rand.nextDouble()) * 4;
            BlockPos spawnPos = BlockPos.containing(new Vec3(x, y, z));
            if(serverLevel.isEmptyBlock(blockpos)){
                summoned.moveTo(spawnPos, 0.0F, 0.0F);
                summoned.finalizeSpawn(serverLevel, player.level().getCurrentDifficultyAt(blockpos), MobSpawnType.MOB_SUMMONED, null, null);
                summoned.setOwner(player);
                summoned.setBoundOrigin(blockpos);
                summoned.getPersistentData().putBoolean("PlayerSummoned", true);
                summoned.getPersistentData().putInt("MinionSlots", this.slotCost);
                
                AttributeInstance attackDamage = summoned.getAttribute(Attributes.ATTACK_DAMAGE);
                if(attackDamage != null){
                    double multiplier = player.getAttributeValue(AttributeReg.SUMMON_DAMAGE.get());
                    attackDamage.setBaseValue(attackDamage.getBaseValue() * multiplier);
                }

                serverLevel.addFreshEntity(summoned);
                PacketHandler.sendToTracking(serverLevel, blockpos, new MinionSummonParticlePacket(summoned.getId(), player.getOnPos().above()));
            }
        }
    }

    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn){
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        if(getDefaultType(itemstack).is(TagsRegistry.MINIONS)){
            if(!playerIn.isShiftKeyDown()){
                playerIn.startUsingItem(handIn);
                return InteractionResultHolder.consume(itemstack);
            }
        }

        return InteractionResultHolder.pass(itemstack);
    }

    public int getUseDuration(ItemStack stack){
        return 7;
    }

    public SoundEvent getUseSound(){
        return SoundsRegistry.NECROMANCER_SUMMON_AIR.get();
    }

    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entityLiving){
        Player player = (Player)entityLiving;
        if(level instanceof ServerLevel server){
            spawnMinion(server, player, stack);
            if(!player.isCreative()){
                stack.hurtAndBreak(1, player, (plr) -> plr.broadcastBreakEvent(EquipmentSlot.MAINHAND));
            }

            level.playSound(null, player.blockPosition(), getUseSound(), SoundSource.PLAYERS);
            applyCooldown(player);
        }

        return stack;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Component getHighlightTip(ItemStack stack, Component displayName){
        if(getDefaultType(stack).is(TagsRegistry.MINIONS)){
            return displayName.copy().append(Component.literal(" [" + getDefaultType(stack).getDescription().getString() + "]").withStyle(Styles.create(Col.fromColor(AbstractMinionEntity.getColor((EntityType<? extends AbstractMinionEntity>)getDefaultType(stack)).brighter().brighter()))));
        }

        return super.getHighlightTip(stack, displayName);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, Level world, @NotNull List<Component> tooltip, @NotNull TooltipFlag flags){
        super.appendHoverText(stack, world, tooltip, flags);
        tooltip.add(Component.translatable("tooltip.valoria.necromancy").withStyle(ChatFormatting.GRAY));
        if(getDefaultType(stack).is(TagsRegistry.MINIONS)){
            tooltip.add(Component.translatable("tooltip.valoria.summons", getDefaultType(stack).getDescription()).withStyle(ChatFormatting.GRAY));
        }

        tooltip.add(Component.empty());
        tooltip.add(Component.translatable("tooltip.valoria.hold_rmb").withStyle(style -> style.withFont(Valoria.FONT)));
    }
}