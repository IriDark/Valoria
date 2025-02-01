package com.idark.valoria.registries.item.types;

import com.google.common.base.*;
import com.google.common.collect.*;
import com.idark.valoria.client.model.animations.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.block.entity.*;
import com.idark.valoria.registries.block.types.*;
import com.idark.valoria.registries.item.types.curio.necklace.*;
import net.minecraft.core.*;
import net.minecraft.core.particles.*;
import net.minecraft.server.network.*;
import net.minecraft.sounds.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.api.distmarker.*;
import pro.komaru.tridot.client.graphics.render.animation.*;
import pro.komaru.tridot.core.interfaces.*;
import top.theillusivec4.curios.api.*;

import java.util.*;
import java.util.function.Supplier;

@SuppressWarnings("removal")
public class PickItem extends Item implements ICustomAnimationItem, Vanishable{
    public static CrushingAnimation animation = new CrushingAnimation();
    @Deprecated
    public static final double MAX_BRUSH_DISTANCE = Math.sqrt(ServerGamePacketListenerImpl.MAX_INTERACTION_DISTANCE) - 1.0D;
    public float excavationSpeed;
    public float attackDamageIn;
    public float attackSpeedIn;
    private final Supplier<Multimap<Attribute, AttributeModifier>> attributeModifiers = Suppliers.memoize(this::createAttributes);

    public PickItem(Item.Properties pProperties, int attackDamageIn, float attackSpeedIn, int speed){
        super(pProperties);
        this.attackDamageIn = (float)attackDamageIn;
        this.attackSpeedIn = attackSpeedIn;
        this.excavationSpeed = (float)speed;
    }

    public static List<ItemStack> getExcavationAccessories(Player player){
        List<ItemStack> items = new ArrayList<>();
        List<SlotResult> curioSlots = CuriosApi.getCuriosHelper().findCurios(player, (i) -> true);
        for(SlotResult slot : curioSlots){
            if(slot.stack().getItem() instanceof PickNecklace){
                items.add(slot.stack());
            }
        }

        return items;
    }

    private Multimap<Attribute, AttributeModifier> createAttributes(){
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(AttributeReg.EXCAVATION_SPEED.get(), new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Pick modifier", excavationSpeed, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", attackDamageIn, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", attackSpeedIn, AttributeModifier.Operation.ADDITION));
        return builder.build();
    }

    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot equipmentSlot){
        return equipmentSlot == EquipmentSlot.MAINHAND ? this.attributeModifiers.get() : super.getDefaultAttributeModifiers(equipmentSlot);
    }

    public InteractionResult useOn(UseOnContext pContext){
        Player player = pContext.getPlayer();
        if(player != null && this.calculateHitResult(player).getType() == HitResult.Type.BLOCK){
            player.startUsingItem(pContext.getHand());
        }

        return InteractionResult.CONSUME;
    }

    public UseAnim getUseAnimation(ItemStack pStack){
        return UseAnim.CUSTOM;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public ItemAnimation getAnimation(ItemStack stack){
        return animation;
    }

    public int getUseDuration(ItemStack pStack){
        return 200;
    }

    public void onUseTick(Level pLevel, LivingEntity pLivingEntity, ItemStack pStack, int pRemainingUseDuration){
        if(pRemainingUseDuration >= 0 && pLivingEntity instanceof Player player){
            HitResult hitresult = this.calculateHitResult(pLivingEntity);
            if(hitresult instanceof BlockHitResult blockhitresult){
                if(hitresult.getType() == HitResult.Type.BLOCK){
                    int i = this.getUseDuration(pStack) - pRemainingUseDuration + 1;
                    int speed = getExcavationAccessories(player).isEmpty() ? (int)excavationSpeed + 15 : (int)excavationSpeed + 10;
                    if(i % speed == 5){
                        BlockPos blockpos = blockhitresult.getBlockPos();
                        BlockState blockstate = pLevel.getBlockState(blockpos);
                        HumanoidArm humanoidarm = pLivingEntity.getUsedItemHand() == InteractionHand.MAIN_HAND ? player.getMainArm() : player.getMainArm().getOpposite();
                        this.spawnDustParticles(pLevel, blockhitresult, blockstate, pLivingEntity.getViewVector(0.0F), humanoidarm);
                        Block pBlock = blockstate.getBlock();
                        SoundEvent soundevent;
                        if(pBlock instanceof CrushableBlock block){
                            soundevent = block.getCrushSound();
                        }else{
                            soundevent = SoundEvents.BRUSH_GENERIC;
                        }

                        pLevel.playSound(player, blockpos, soundevent, SoundSource.BLOCKS);
                        if(!pLevel.isClientSide()){
                            BlockEntity blockentity = pLevel.getBlockEntity(blockpos);
                            if(blockentity instanceof CrushableBlockEntity blockEntity){
                                if(blockEntity.crushing(pLevel.getGameTime(), player, blockhitresult.getDirection())){
                                    EquipmentSlot equipmentslot = pStack.equals(player.getItemBySlot(EquipmentSlot.OFFHAND)) ? EquipmentSlot.OFFHAND : EquipmentSlot.MAINHAND;
                                    pStack.hurtAndBreak(1, pLivingEntity, (p_279044_) -> p_279044_.broadcastBreakEvent(equipmentslot));
                                }
                            }
                        }
                    }

                    return;
                }
            }

            pLivingEntity.releaseUsingItem();
        }else{
            pLivingEntity.releaseUsingItem();
        }
    }

    private HitResult calculateHitResult(LivingEntity pEntity){
        return ProjectileUtil.getHitResultOnViewVector(pEntity, (p_281111_) -> !p_281111_.isSpectator() && p_281111_.isPickable(), MAX_BRUSH_DISTANCE);
    }

    public void spawnDustParticles(Level pLevel, BlockHitResult pHitResult, BlockState pState, Vec3 pPos, HumanoidArm pArm){
        int i = pArm == HumanoidArm.RIGHT ? 1 : -1;
        int j = pLevel.getRandom().nextInt(7, 12);
        BlockParticleOption blockparticleoption = new BlockParticleOption(ParticleTypes.BLOCK, pState);
        Direction direction = pHitResult.getDirection();
        PickItem.DustParticlesDelta brushitem$dustparticlesdelta = PickItem.DustParticlesDelta.fromDirection(pPos, direction);
        Vec3 vec3 = pHitResult.getLocation();

        for(int k = 0; k < j; ++k){
            pLevel.addParticle(blockparticleoption, vec3.x - (double)(direction == Direction.WEST ? 1.0E-6F : 0.0F), vec3.y, vec3.z - (double)(direction == Direction.NORTH ? 1.0E-6F : 0.0F), brushitem$dustparticlesdelta.xd() * (double)i * 3.0D * pLevel.getRandom().nextDouble(), 0.0D, brushitem$dustparticlesdelta.zd() * (double)i * 3.0D * pLevel.getRandom().nextDouble());
        }

    }

    record DustParticlesDelta(double xd, double yd, double zd){
        public static PickItem.DustParticlesDelta fromDirection(Vec3 pPos, Direction pDirection){

            return switch(pDirection){
                case DOWN, UP -> new DustParticlesDelta(pPos.z(), 0.0D, -pPos.x());
                case NORTH -> new DustParticlesDelta(1.0D, 0.0D, -0.1D);
                case SOUTH -> new DustParticlesDelta(-1.0D, 0.0D, 0.1D);
                case WEST -> new DustParticlesDelta(-0.1D, 0.0D, -1.0D);
                case EAST -> new DustParticlesDelta(0.1D, 0.0D, 1.0D);
            };
        }
    }
}