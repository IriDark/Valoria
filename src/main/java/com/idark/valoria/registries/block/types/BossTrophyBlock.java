package com.idark.valoria.registries.block.types;

import com.idark.valoria.core.mixin.*;
import com.idark.valoria.registries.block.entity.*;
import net.minecraft.core.*;
import net.minecraft.nbt.*;
import net.minecraft.sounds.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.registries.*;
import org.jetbrains.annotations.*;

import java.util.function.*;

public class BossTrophyBlock extends Block implements EntityBlock{
    private Supplier<EntityType<?>> entity;
    public BossTrophyBlock(Supplier<EntityType<?>> entity, Properties pProperties){
        super(pProperties.noOcclusion());
        this.entity = entity;
    }

    public BossTrophyBlock(Properties pProperties){
        super(pProperties.noOcclusion());
        this.entity = null;
    }

    public Supplier<EntityType<?>> getEntity(){
        return entity;
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack){
        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
        if (pLevel.getBlockEntity(pPos) instanceof BossTrophyBlockEntity trophyBlockEntity && entity != null) {
            trophyBlockEntity.setEntity(entity.get().create(pLevel));
        }
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit){
        ItemStack heldStack = pPlayer.getItemInHand(pHand);
        BlockEntity tile = pLevel.getBlockEntity(pPos);
        if(tile instanceof BossTrophyBlockEntity trophyBlockEntity){
            if(pPlayer.isCreative()){
                if(heldStack.getItem() instanceof SpawnEggItem egg){
                    trophyBlockEntity.setEntity(egg.getType(heldStack.getTag()).create(pLevel)); // replacing entity with entity from spawn egg instead
                    return InteractionResult.SUCCESS;
                }
            }

            if(!(heldStack.getItem() instanceof SpawnEggItem)){
                if(trophyBlockEntity.entity == null) return InteractionResult.FAIL;

                Entity entity;
                var tag = new CompoundTag();
                tag.putString("id", ForgeRegistries.ENTITY_TYPES.getKey(trophyBlockEntity.entity).toString());
                entity = EntityType.loadEntityRecursive(tag, pLevel, Function.identity());
                if(entity != null && entity instanceof Mob mob){
                    if(((MobAmbientSoundInvoker)mob).invokeGetAmbientSound() == null) return InteractionResult.FAIL; // no sound
                    pLevel.playSound(null, pPos, ((MobAmbientSoundInvoker)mob).invokeGetAmbientSound(), SoundSource.AMBIENT);
                }

                return InteractionResult.SUCCESS;
            }
        }

        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState){
        return new BossTrophyBlockEntity(pPos, pState);
    }
}
