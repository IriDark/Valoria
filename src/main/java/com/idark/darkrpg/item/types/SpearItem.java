package com.idark.darkrpg.item.types;

import com.idark.darkrpg.item.ModItems;
import com.idark.darkrpg.block.ModBlocks;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.google.common.collect.Multimap;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.IVanishable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.tags.BlockTags;
import net.minecraft.particles.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeMod;

import java.util.UUID;
import java.util.function.Supplier;
import java.util.Random;

public class SpearItem extends TieredItem implements IVanishable {
    Random rand = new Random();
	private final float attackDamage;
	private final float attackSpeed;
	protected static final UUID ATTACK_RANGE_MODIFIER = UUID.fromString("B2392114-1C63-2123-AB29-6456FD734102");


	private Supplier<Multimap<Attribute, AttributeModifier>> attributeModifiers = Suppliers.memoize(this::createAttributes);

	public SpearItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Item.Properties builderIn) {
		super(tier, builderIn);
		this.attackDamage = (float)attackDamageIn + tier.getAttackDamageBonus();
		this.attackSpeed = attackSpeedIn;
	}

	private Multimap<Attribute, AttributeModifier> createAttributes(){
		Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", (double)this.attackDamage, AttributeModifier.Operation.ADDITION));
		builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", (double)attackSpeed, AttributeModifier.Operation.ADDITION));
		builder.put(ForgeMod.REACH_DISTANCE.get(), new AttributeModifier(ATTACK_RANGE_MODIFIER,"Spear modifier",10, AttributeModifier.Operation.ADDITION));
		return builder.build();
	}

	public float getAttackDamage() {
		return this.attackDamage;
	}

	public boolean canAttackBlock(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
		return !player.isCreative();
	}

	public float getDestroySpeed(ItemStack stack, BlockState state) {
		if (state.is(Blocks.COBWEB)) {
			return 2.0F;
		} else {
			Material material = state.getMaterial();
			return material != Material.PLANT && material != Material.REPLACEABLE_PLANT && material != Material.CORAL && !state.is(BlockTags.LEAVES) && material != Material.VEGETABLE ? 1.0F : 1.5F;
		}
	}

	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		stack.hurtAndBreak(1, attacker, (entity) -> {
			entity.broadcastBreakEvent(EquipmentSlotType.MAINHAND);
		});
		
		return true;
	}

	public boolean mineBlock(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
		if (state.getDestroySpeed(worldIn, pos) != 0.0F) {
			stack.hurtAndBreak(2, entityLiving, (entity) -> {
				entity.broadcastBreakEvent(EquipmentSlotType.MAINHAND);
			});
		}

		return true;
	}

	public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlotType equipmentSlot) {
		return equipmentSlot == EquipmentSlotType.MAINHAND ? this.attributeModifiers.get() : super.getDefaultAttributeModifiers(equipmentSlot);
	}
	
	@Override
    public ActionResultType onItemUseFirst(ItemStack stack, ItemUseContext context) {
        World worldIn = context.getLevel();		
        BlockState state = worldIn.getBlockState(context.getClickedPos());
        BlockPos pos = context.getClickedPos();
        PlayerEntity player = context.getPlayer();
		Hand handIn = context.getHand();
        rightClickOnCertainBlockState(stack, handIn, player, worldIn, state, pos);		
        return super.onItemUseFirst(stack, context);		
	}

    private void rightClickOnCertainBlockState(ItemStack stack, Hand handIn, PlayerEntity player, World worldIn, BlockState state, BlockPos pos) {
		if ((state.is(ModBlocks.CHARGED_VOID_PILLAR.get())) || (state.is(ModBlocks.VOID_PILLAR_AMETHYST.get()))) {
			worldIn.playSound(player, player.blockPosition(), SoundEvents.RESPAWN_ANCHOR_AMBIENT, SoundCategory.BLOCKS, 10f, 1f);
			worldIn.playSound(player, player.blockPosition(), SoundEvents.RESPAWN_ANCHOR_CHARGE, SoundCategory.BLOCKS, 1.0F, 1.0F);
			for (int i = 0;i<16;i++) {
				worldIn.addParticle(ParticleTypes.POOF, pos.getX() + rand.nextDouble(), pos.getY() + 0.5F + rand.nextDouble() * 1.1, pos.getZ() + 0.5F + rand.nextDouble(), 0d, 0.05d, 0d);
			}

			worldIn.setBlockAndUpdate(pos, ModBlocks.VOID_PILLAR.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, state.getValue(RotatedPillarBlock.AXIS)));		
			if (!worldIn.isClientSide) {
				if (player == null || !player.abilities.instabuild) {
					player.drop(new ItemStack(ModItems.UNCHARGED_STONE.get()), true);
					if (stack.getItem() instanceof SpearItem) {
						worldIn.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.RESPAWN_ANCHOR_CHARGE, SoundCategory.BLOCKS, 1.0F, 1.0F);
						stack.hurtAndBreak(10, player, (playerEntity) -> {
						playerEntity.broadcastBreakEvent(handIn);
						});		
					}
				}
			}
		}
	}
}