package com.idark.valoria.registries.entity.living.elemental;

import net.minecraft.core.*;
import net.minecraft.sounds.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.navigation.*;
import net.minecraft.world.entity.ai.util.*;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.dimension.*;
import net.minecraft.world.level.pathfinder.*;
import pro.komaru.tridot.common.registry.entity.*;

import java.util.function.*;

public class AbstractDevil extends MultiAttackMob implements Enemy{
    public AbstractDevil(EntityType<? extends MultiAttackMob> pEntityType, Level pLevel){
        super(pEntityType, pLevel);
        this.xpReward = 5;
        this.getNavigation().setCanFloat(false);
        applyOpenDoorsAbility();
        this.setPathfindingMalus(BlockPathTypes.LAVA, 8.0F);
        this.setPathfindingMalus(BlockPathTypes.DAMAGE_OTHER, 8.0F);
        this.setPathfindingMalus(BlockPathTypes.POWDER_SNOW, 8.0F);
    }

    /**
     * Static predicate for determining if the current light level and environmental conditions allow for a monster to
     * spawn.
     */
    public static boolean isDarkEnoughToSpawn(ServerLevelAccessor pLevel, BlockPos pPos, RandomSource pRandom){
        if(pLevel.getBrightness(LightLayer.SKY, pPos) > pRandom.nextInt(32)){
            return false;
        }else{
            DimensionType dimensiontype = pLevel.dimensionType();
            int i = dimensiontype.monsterSpawnBlockLightLimit();
            if(i < 15 && pLevel.getBrightness(LightLayer.BLOCK, pPos) > i){
                return false;
            }else{
                int j = pLevel.getLevel().isThundering() ? pLevel.getMaxLocalRawBrightness(pPos, 10) : pLevel.getMaxLocalRawBrightness(pPos);
                return j <= dimensiontype.monsterSpawnLightTest().sample(pRandom);
            }
        }
    }

    /**
     * Static predicate for determining whether a monster can spawn at the provided location, incorporating a check of
     * the current light level at the location.
     */
    public static boolean checkMonsterSpawnRules(EntityType<? extends Devil> pType, ServerLevelAccessor pLevel, MobSpawnType pSpawnType, BlockPos pPos, RandomSource pRandom){
        return pLevel.getDifficulty() != Difficulty.PEACEFUL && isDarkEnoughToSpawn(pLevel, pPos, pRandom) && checkMobSpawnRules(pType, pLevel, pSpawnType, pPos, pRandom) && (!pLevel.getBlockState(pPos.below()).is(Blocks.NETHER_WART_BLOCK) || !pLevel.getBlockState(pPos.below()).is(Blocks.WARPED_WART_BLOCK));
    }

    public SoundSource getSoundSource(){
        return SoundSource.HOSTILE;
    }

    public void aiStep(){
        this.updateSwingTime();
        super.aiStep();
    }

    protected boolean shouldDespawnInPeaceful(){
        return true;
    }

    protected SoundEvent getSwimSound(){
        return SoundEvents.HOSTILE_SWIM;
    }

    protected SoundEvent getSwimSplashSound(){
        return SoundEvents.HOSTILE_SPLASH;
    }

    protected SoundEvent getHurtSound(DamageSource pDamageSource){
        return SoundEvents.HOSTILE_HURT;
    }

    protected SoundEvent getDeathSound(){
        return SoundEvents.HOSTILE_DEATH;
    }

    public LivingEntity.Fallsounds getFallSounds(){
        return new LivingEntity.Fallsounds(SoundEvents.HOSTILE_SMALL_FALL, SoundEvents.HOSTILE_BIG_FALL);
    }

    public float getWalkTargetValue(BlockPos pPos, LevelReader pLevel){
        return -pLevel.getPathfindingCostFromLightLevels(pPos);
    }

    /**
     * Entity won't drop experience points if this returns false
     */
    public boolean shouldDropExperience(){
        return true;
    }

    /**
     * Entity won't drop items if this returns false
     */
    protected boolean shouldDropLoot(){
        return true;
    }

    public ItemStack getProjectile(ItemStack pShootable){
        if(pShootable.getItem() instanceof ProjectileWeaponItem){
            Predicate<ItemStack> predicate = ((ProjectileWeaponItem)pShootable.getItem()).getSupportedHeldProjectiles();
            ItemStack itemstack = ProjectileWeaponItem.getHeldProjectile(this, predicate);
            return net.minecraftforge.common.ForgeHooks.getProjectile(this, pShootable, itemstack.isEmpty() ? new ItemStack(Items.ARROW) : itemstack);
        }else{
            return net.minecraftforge.common.ForgeHooks.getProjectile(this, pShootable, ItemStack.EMPTY);
        }
    }

    private void applyOpenDoorsAbility(){
        if(GoalUtils.hasGroundPathNavigation(this)){
            ((GroundPathNavigation)this.getNavigation()).setCanOpenDoors(true);
        }
    }

    public boolean isAdult(){
        return !this.isBaby();
    }

    public MobType getMobType(){
        return MobType.UNDEAD;
    }

    protected void registerGoals(){
        this.targetSelector.addGoal(0, new PrepareGoal());
    }

    public int getExperienceReward(){
        if(this.isBaby()){
            this.xpReward = this.xpReward / 2;
        }

        return super.getExperienceReward();
    }
}
