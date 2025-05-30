package com.idark.valoria.registries.block.entity;

import com.idark.valoria.util.*;
import net.minecraft.core.*;
import net.minecraft.core.particles.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.protocol.game.*;
import net.minecraft.sounds.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.*;
import org.jetbrains.annotations.*;
import pro.komaru.tridot.common.registry.block.entity.*;

public abstract class AbstractAltarBlockEntity extends BlockSimpleInventory implements TickableBlockEntity{
    public int progress = 0;
    public int progressMax = 0;
    public boolean isSummoning = false;

    public AbstractAltarBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state){
        super(type, pos, state);
    }

    public void startSummoning(){
        this.isSummoning = true;
        this.progress = 0;
    }

    public boolean canSummon(Level level) {
        var boss = this.getBoss(level);
        if(boss == null) return false;

        BlockPos pos = this.getBlockPos();
        AABB region = new AABB(pos.getX() - 15, pos.getY() - 15, pos.getZ() - 15, pos.getX() + 15, pos.getY() + 15, pos.getZ() + 15);
        return level.getEntitiesOfClass(boss.getClass(), region).isEmpty();
    }

    public abstract LivingEntity getBoss(Level level);

    public abstract void summonBoss(Level level);

    public abstract void summonParticles(int tick);

    public abstract SoundEvent getSummonSound();
    public void tick(){
        int tick = 0;
        tick++;
        if(isSummoning){
            if(level.isClientSide()){
                summonParticles(tick);
            }

            increaseCraftingProgress(60);
            setChanged(level, getBlockPos(), getBlockState());
            if(progress >= 60){
                finishSummoning();
                resetProgress();
            }

            ValoriaUtils.SUpdateTileEntityPacket(this);
        }
    }

    private void increaseCraftingProgress(int time){
        progressMax = time;
        if(progress < time){
            progress++;
        }
    }

    private void resetProgress(){
        progress = 0;
    }

    private void finishSummoning(){
        this.isSummoning = false;
        this.progress = 0;
        if(!this.getItemHandler().getItem(0).isEmpty()){
            this.getItemHandler().removeItem(0, 1);
            Level level = this.getLevel();
            if(level == null) return;
            if(level.isClientSide()){
                for(int i = 0; i < 4; i++){
                    level.addParticle(ParticleTypes.POOF, this.worldPosition.getX() + 0.5, this.worldPosition.getY() + 2.85, this.worldPosition.getZ() + 0.5, (Math.random() - 0.5) * 0.1, Math.random() * 0.1, (Math.random() - 0.5) * 0.1);
                }
            }else{
                summonBoss(level);
            }

            level.playSound(null, this.worldPosition, getSummonSound(), SoundSource.PLAYERS, 1, 1);
        }
    }

    @Override
    public void saveAdditional(CompoundTag pTag){
        pTag.putInt("progress", progress);
        pTag.putInt("progressMax", progressMax);
        pTag.putBoolean("summoning", isSummoning);
        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag){
        super.load(pTag);
        progress = pTag.getInt("progress");
        progressMax = pTag.getInt("progressMax");
        isSummoning = pTag.getBoolean("summoning");
    }

    @Override
    protected SimpleContainer createItemHandler(){
        return new SimpleContainer(1){
            @Override
            public int getMaxStackSize(){
                return 1;
            }
        };
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket(){
        return ClientboundBlockEntityDataPacket.create(this, BlockEntity::getUpdateTag);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt){
        super.onDataPacket(net, pkt);
        handleUpdateTag(pkt.getTag());
    }

    @NotNull
    @Override
    public final CompoundTag getUpdateTag(){
        var tag = new CompoundTag();
        saveAdditional(tag);
        return tag;
    }

    @Override
    public void setChanged(){
        super.setChanged();
        if(level != null && !level.isClientSide){
            ValoriaUtils.SUpdateTileEntityPacket(this);
        }
    }
}