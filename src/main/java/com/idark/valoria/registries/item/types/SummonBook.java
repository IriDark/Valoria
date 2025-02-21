package com.idark.valoria.registries.item.types;

import com.google.common.collect.*;
import com.idark.valoria.*;
import com.idark.valoria.core.network.*;
import com.idark.valoria.core.network.packets.particle.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.util.Pal;
import com.idark.valoria.util.Styles;
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
import pro.komaru.tridot.client.graphics.*;
import pro.komaru.tridot.registry.entity.*;

import java.util.*;

import static com.idark.valoria.Valoria.*;
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
        super(pProperties.stacksTo(1));
        this.hasLimitedLife = true;
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(AttributeReg.NECROMANCY_LIFETIME.get(), new AttributeModifier(BASE_NECROMANCY_LIFETIME_UUID, "Tool modifier", lifetime, AttributeModifier.Operation.ADDITION));
        builder.put(AttributeReg.NECROMANCY_COUNT.get(), new AttributeModifier(BASE_NECROMANCY_COUNT_UUID, "Tool modifier", count, AttributeModifier.Operation.ADDITION));
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
        return compoundtag != null && compoundtag.contains("color", 99) ? compoundtag.getInt("color") : Clr.colorToDecimal(Pal.lightViolet);
    }

    public int getLifetime(Player player){
        return (int)(player.getAttributeValue(AttributeReg.NECROMANCY_LIFETIME.get()) * 20);
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
            for(int i = 0; i < (int)(player.getAttributeValue(AttributeReg.NECROMANCY_COUNT.get())); ++i){
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