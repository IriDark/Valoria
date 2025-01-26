package com.idark.valoria.registries.block.entity;

import com.idark.valoria.client.render.tile.TickableBlockEntity;
import com.idark.valoria.registries.BlockEntitiesRegistry;
import com.idark.valoria.registries.EntityTypeRegistry;
import com.idark.valoria.registries.SoundsRegistry;
import com.idark.valoria.registries.entity.living.boss.NecromancerEntity;
import com.idark.valoria.util.Pal;
import com.idark.valoria.util.ValoriaUtils;
import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class CrypticAltarBlockEntity extends BlockSimpleInventory implements TickableBlockEntity{
    public int progress = 0;
    public int progressMax = 0;
    public boolean isSummoning = false;

    public CrypticAltarBlockEntity(BlockPos pos, BlockState state){
        super(BlockEntitiesRegistry.CRYPTIC_ALTAR.get(), pos, state);
    }

    public void startSummoning(){
        this.isSummoning = true;
        this.progress = 0;
    }

    public void tick(){
        int tick = 0;
        tick++;
        if(isSummoning){
            if(level.isClientSide()){
                double angle = (tick + progress * Math.PI) * 0.925;
                double y = angle * 0.01025;
                for(int a = 0; a < 3; a++){
                    double radius = 0.5 * (1 - ((double)progress / progressMax)) * (1 - ((double)a * 2.5f));
                    double x = Math.cos(angle) * radius;
                    double z = Math.sin(angle) * radius;
                    ParticleBuilder.create(FluffyFurParticles.WISP)
                            .setColorData(ColorParticleData.create(Pal.vividGreen, Color.darkGray).build())
                            .setTransparencyData(GenericParticleData.create(0.125f, 0f).build())
                            .setScaleData(GenericParticleData.create((((float)a * 0.125f)), 0.1f, 0).build())
                            .setLifetime(35)
                            .spawn(this.level, (this.worldPosition.getX() + 0.5f) + x, this.worldPosition.getY() + (1 - ((double)a / 1.25)) + y, (this.worldPosition.getZ() + 0.5f) + z);
                }

                level.addParticle(ParticleTypes.ENCHANT, this.worldPosition.getX() + level.random.nextFloat(), this.worldPosition.getY() + 1.85 + y, this.worldPosition.getZ() + level.random.nextFloat(), (Math.random() - 0.5) * 0.1, Math.random() * 0.1, (Math.random() - 0.5) * 0.1);
            }

            increaseCraftingProgress(60);
            setChanged(level, getBlockPos(), getBlockState());
            if(progress >= 60){
                finishSummoning();
                resetProgress();
            }

            ValoriaUtils.tileEntity.SUpdateTileEntityPacket(this);
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
                NecromancerEntity boss = new NecromancerEntity(EntityTypeRegistry.NECROMANCER.get(), level);
                boss.moveTo(this.worldPosition.getX() + 0.5, this.worldPosition.getY() + 1.85, this.worldPosition.getZ() + 0.5, 0.0F, 0.0F);
                level.addFreshEntity(boss);
            }

            level.playSound(null, this.worldPosition, SoundsRegistry.NECROMANCER_SUMMON.get(), SoundSource.PLAYERS, 1, 1);
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
            ValoriaUtils.tileEntity.SUpdateTileEntityPacket(this);
        }
    }
}