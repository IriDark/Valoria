package com.idark.valoria.registries.entity.projectile;

import com.google.common.collect.*;
import net.minecraft.nbt.*;
import net.minecraft.network.protocol.*;
import net.minecraft.network.protocol.game.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.*;
import net.minecraft.world.level.*;
import net.minecraftforge.network.*;
import org.jetbrains.annotations.*;

import java.util.*;

public abstract class AbstractValoriaArrow extends AbstractArrow {
    public ItemStack arrowItem = ItemStack.EMPTY;
    private final Set<MobEffectInstance> effects = Sets.newHashSet();
    public AbstractValoriaArrow(EntityType<? extends AbstractArrow> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public AbstractValoriaArrow(EntityType<? extends AbstractArrow> pEntityType, Level worldIn, LivingEntity thrower, ItemStack thrownStackIn, int baseDamage) {
        super(pEntityType, thrower, worldIn);
        arrowItem = new ItemStack(thrownStackIn.getItem());
        this.baseDamage = baseDamage;
    }

    public void doPostSpawn(){
    }

    public void tick(){
        super.tick();
        if(this.level().isClientSide()){
            this.spawnParticlesTrail();
        }
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public void addEffect(MobEffectInstance pEffectInstance) {
        this.effects.add(pEffectInstance);
    }

    public void addAdditionalSaveData(@NotNull CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        if (!this.effects.isEmpty()) {
            ListTag listtag = new ListTag();
            for (MobEffectInstance mobeffectinstance : this.effects) {
                listtag.add(mobeffectinstance.save(new CompoundTag()));
            }

            compound.put("CustomPotionEffects", listtag);
        }
    }

    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        for (MobEffectInstance mobeffectinstance : PotionUtils.getCustomEffects(pCompound)) {
            this.addEffect(mobeffectinstance);
        }
    }

    protected void doPostHurtEffects(LivingEntity pLiving) {
        super.doPostHurtEffects(pLiving);
        Entity entity = this.getEffectSource();
        if (!this.effects.isEmpty()) {
            for (MobEffectInstance effect : this.effects) {
                pLiving.addEffect(effect, entity);
            }
        }
    }

    public void setEffectsFromList(ImmutableList<MobEffectInstance> effects) {
        for (MobEffectInstance mobeffectinstance : effects) {
            this.effects.add(new MobEffectInstance(mobeffectinstance));
        }
    }

    public void spawnParticlesTrail() {
    }

    @Override
    public ItemStack getPickupItem() {
        return arrowItem;
    }
}