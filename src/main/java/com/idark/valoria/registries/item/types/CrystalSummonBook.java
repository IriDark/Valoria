package com.idark.valoria.registries.item.types;

import com.idark.valoria.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.entity.ai.movements.*;
import com.idark.valoria.registries.entity.living.minions.*;
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
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraftforge.registries.*;
import org.jetbrains.annotations.*;

import java.util.*;

import static net.minecraftforge.registries.ForgeRegistries.Keys.ENTITY_TYPES;

public class CrystalSummonBook extends SummonBook{
    private static final ResourceKey<EntityType<?>> DEFAULT_VARIANT = ResourceKey.create(ENTITY_TYPES, new ResourceLocation(Valoria.ID, "crystal"));

    /**
     * @param lifetime Summoned mob lifetime, specified in Seconds
     * @param count Count of summoned mobs
     */
    public CrystalSummonBook(int lifetime, int count, Properties pProperties){
        super(lifetime, count, pProperties);
    }

    @Override
    public ItemStack getDefaultInstance(){
        ItemStack itemStack = new ItemStack(this);
        CompoundTag tag = itemStack.getOrCreateTagElement("EntityTag");
        tag.putString("id", DEFAULT_VARIANT.location().toString());
        return itemStack;
    }

    protected EntityType<?> getDefaultType(ItemStack stack){
        return ForgeRegistries.ENTITY_TYPES.getValue(DEFAULT_VARIANT.location());
    }

    private void spawn(ServerLevel level, BlockPos blockpos, Player player, float angle){
        CrystalEntity crystal = EntityTypeRegistry.CRYSTAL.get().create(player.level());
        if(crystal != null && level.isEmptyBlock(blockpos)){
            crystal.moveTo(blockpos, 0.0F, 0.0F);
            crystal.finalizeSpawn(level, player.level().getCurrentDifficultyAt(blockpos), MobSpawnType.MOB_SUMMONED, null, null);
            crystal.setOwner(player);
            crystal.movement = new FlyingAroundMovement(crystal, player);
            crystal.movement.setRadius(3f);
            crystal.movement.setAngle(angle);
            crystal.setBoundOrigin(blockpos);
            crystal.setLimitedLife(285);
            level.addFreshEntity(crystal);
        }
    }

    public void applyCooldown(Player playerIn){
        for(Item item : ForgeRegistries.ITEMS){
            if(item instanceof SummonBook){
                playerIn.getCooldowns().addCooldown(item, 325);
            }
        }
    }

    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn){
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        if(!playerIn.isShiftKeyDown()){
            playerIn.startUsingItem(handIn);
            return InteractionResultHolder.consume(itemstack);
        }

        return InteractionResultHolder.pass(itemstack);
    }

    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entityLiving){
        Player player = (Player)entityLiving;
        if(level instanceof ServerLevel serverLevel){
            for(int i = 0; i < 4; ++i){
                float initialAngle = (float)((2 * Math.PI / 4) * i);
                spawn(serverLevel, player.blockPosition().above(), player, initialAngle);
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
    public Component getHighlightTip(ItemStack stack, Component displayName){
        return displayName.copy().append(Component.literal(" [" + getDefaultType(stack).getDescription().getString() + "]").withStyle(Styles.arcaneGold));
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, Level world, @NotNull List<Component> tooltip, @NotNull TooltipFlag flags){
        super.appendHoverText(stack, world, tooltip, flags);
        tooltip.add(Component.translatable("tooltip.valoria.summons", getDefaultType(stack).getDescription()).withStyle(ChatFormatting.GRAY));
    }
}