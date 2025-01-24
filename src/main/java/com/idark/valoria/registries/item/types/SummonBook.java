package com.idark.valoria.registries.item.types;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.idark.valoria.Valoria;
import com.idark.valoria.core.network.PacketHandler;
import com.idark.valoria.core.network.packets.particle.MinionSummonParticlePacket;
import com.idark.valoria.registries.AttributeRegistry;
import com.idark.valoria.registries.SoundsRegistry;
import com.idark.valoria.registries.TagsRegistry;
import com.idark.valoria.registries.entity.living.minions.AbstractMinionEntity;
import com.idark.valoria.util.ColorUtil;
import com.idark.valoria.util.Pal;
import com.idark.valoria.util.Styles;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.idark.valoria.Valoria.BASE_NECROMANCY_COUNT_UUID;
import static com.idark.valoria.Valoria.BASE_NECROMANCY_LIFETIME_UUID;
import static net.minecraftforge.registries.ForgeRegistries.Keys.ENTITY_TYPES;

public class SummonBook extends Item{
    public final Multimap<Attribute, AttributeModifier> defaultModifiers;
    public boolean hasLimitedLife;
    private static final ResourceKey<EntityType<?>> DEFAULT_VARIANT = ResourceKey.create(ENTITY_TYPES, new ResourceLocation(Valoria.ID, "undead"));

    /**
     * @param lifetime Summoned mob lifetime, specified in Seconds
     * @param count    Count of summoned mobs
     */
    public SummonBook(int lifetime, int count, Properties pProperties){
        super(pProperties);
        this.hasLimitedLife = true;
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(AttributeRegistry.NECROMANCY_LIFETIME.get(), new AttributeModifier(BASE_NECROMANCY_LIFETIME_UUID, "Tool modifier", lifetime, AttributeModifier.Operation.ADDITION));
        builder.put(AttributeRegistry.NECROMANCY_COUNT.get(), new AttributeModifier(BASE_NECROMANCY_COUNT_UUID, "Tool modifier", count, AttributeModifier.Operation.ADDITION));
        this.defaultModifiers = builder.build();
    }

    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot pEquipmentSlot){
        return pEquipmentSlot == EquipmentSlot.MAINHAND ? defaultModifiers : super.getDefaultAttributeModifiers(pEquipmentSlot);
    }

    protected EntityType<?> getDefaultType(ItemStack stack){
        String entityId = stack.getOrCreateTagElement("EntityTag").getString("id");
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
        return compoundtag != null && compoundtag.contains("color", 99) ? compoundtag.getInt("color") : ColorUtil.colorToDecimal(Pal.lightViolet);
    }

    public int getLifetime(Player player){
        return (int)(player.getAttributeValue(AttributeRegistry.NECROMANCY_LIFETIME.get()) * 20);
    }

    public void applyCooldown(Player playerIn){
        for(Item item : ForgeRegistries.ITEMS){
            if(item instanceof SummonBook){
                playerIn.getCooldowns().addCooldown(item, 175);
            }
        }
    }

    private void spawnMinions(ServerLevel serverLevel, Player player, ItemStack stack){
        BlockPos blockpos = player.getOnPos().above();
        Entity base = getDefaultType(stack).create(player.level());
        if(base instanceof AbstractMinionEntity summoned){
            var rand = serverLevel.random;
            double x = (double)blockpos.getX() + (rand.nextDouble() - rand.nextDouble()) * 6;
            double y = blockpos.getY() + rand.nextInt(1, 2);
            double z = (double)blockpos.getZ() + (rand.nextDouble() - rand.nextDouble()) * 6;
            BlockPos spawnPos = BlockPos.containing(new Vec3(x, y, z));
            if(serverLevel.isEmptyBlock(blockpos)){
                summoned.moveTo(spawnPos, 0.0F, 0.0F);
                summoned.finalizeSpawn(serverLevel, player.level().getCurrentDifficultyAt(blockpos), MobSpawnType.MOB_SUMMONED, null, null);
                summoned.setOwner(player);
                summoned.setBoundOrigin(blockpos);
                if(hasLimitedLife) summoned.setLimitedLife(getLifetime(player) + serverLevel.random.nextInt(60));
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
            for(int i = 0; i < (int)(player.getAttributeValue(AttributeRegistry.NECROMANCY_COUNT.get())); ++i){
                spawnMinions(server, player, stack);
            }

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
            return displayName.copy().append(Component.literal(" [" + getDefaultType(stack).getDescription().getString() + "]").withStyle(new Styles().create(AbstractMinionEntity.getColor((EntityType<? extends AbstractMinionEntity>)getDefaultType(stack)).brighter().brighter())));
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
    }
}