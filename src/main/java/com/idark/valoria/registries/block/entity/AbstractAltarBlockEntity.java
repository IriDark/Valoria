package com.idark.valoria.registries.block.entity;

import com.idark.valoria.Valoria;
import com.idark.valoria.util.*;
import net.minecraft.client.*;
import net.minecraft.core.*;
import net.minecraft.core.particles.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.protocol.game.*;
import net.minecraft.sounds.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import org.jetbrains.annotations.*;
import pro.komaru.tridot.client.cinema.*;
import pro.komaru.tridot.common.registry.block.entity.*;
import pro.komaru.tridot.util.math.*;
import pro.komaru.tridot.util.struct.data.*;

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
        if(this.level != null && this.level.isClientSide()){
            DistExecutor.unsafeCallWhenOn(Dist.CLIENT, () -> () -> {
                playCutscene();
                return new Object();
            });
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void playCutscene() {
        Seq<CutsceneNode> nodes = Seq.with();
        Vec3 tablePos = this.getBlockPos().getCenter();
        Player plr = Valoria.proxy.getPlayer();
        if (plr == null) return;

        Vec3 approachPos = plr.getEyePosition().lerp(tablePos, 0.6).add(0, 1.5, 0);
        nodes.add(new CutsceneNode(approachPos, Interp.smooth, 15)
        .yawToTarget(tablePos)
        .pitchToTarget(tablePos)
        .setFov(60)
        );

        Vec3 mid = tablePos.add(1, 2.5, 1);
        nodes.add(new CutsceneNode(mid, Interp.pow5, 25)
        .yawToTarget(tablePos)
        .pitchToTarget(tablePos)
        .setFov(90)
        );

        Vec3 topDown = tablePos.add(-2, 3, -2);
        nodes.add(new CutsceneNode(topDown, Interp.swing, 35)
        .yawToTarget(tablePos)
        .pitchToTarget(tablePos)
        .setFov(90)
        );

        CutsceneManager.start(nodes);
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