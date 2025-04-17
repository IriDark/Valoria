package com.idark.valoria.api.unlockable;

import net.minecraft.resources.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.world.entity.item.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.storage.loot.*;
import net.minecraft.world.level.storage.loot.parameters.*;

public class Unlockable{
    private ResourceLocation[] loot;
    private ItemStack[] items;
    private int experience;
    public String id;

    public Unlockable(String id){
        this.id = id;
    }

    public boolean hasAwards() {
        return items != null || loot != null || experience > 0;
    }

    public Unlockable addAward(ItemStack... items) {
        this.items = items;
        return this;
    }

    public Unlockable addAward(ResourceLocation... loot) {
        this.loot = loot;
        return this;
    }

    public Unlockable exp(int experience) {
        this.experience = experience;
        return this;
    }

    public String getId(){
        return id;
    }

    public ResourceLocation[] getLoot() {
        return loot;
    }

    public ItemStack[] getItems() {
        return items;
    }

    public void award(ServerPlayer pPlayer){
        pPlayer.giveExperiencePoints(this.experience);
        LootParams lootparams = (new LootParams.Builder(pPlayer.serverLevel())).withParameter(LootContextParams.THIS_ENTITY, pPlayer).withParameter(LootContextParams.ORIGIN, pPlayer.position()).withLuck(pPlayer.getLuck()).create(LootContextParamSets.ADVANCEMENT_REWARD); // Forge: Add luck to LootContext
        boolean flag = false;
        if(items != null){
            for(ItemStack itemstack : items){
                flag = addReward(pPlayer, itemstack.copy(), flag);
            }
        }

        if(loot != null){
            for(ResourceLocation resourcelocation : this.loot){
                for(ItemStack itemstack : pPlayer.server.getLootData().getLootTable(resourcelocation).getRandomItems(lootparams)){
                    flag = addReward(pPlayer, itemstack, flag);
                }
            }
        }

        if (flag) {
            pPlayer.containerMenu.broadcastChanges();
        }
    }

    private static boolean addReward(ServerPlayer pPlayer, ItemStack itemstack, boolean flag){
        if(pPlayer.addItem(itemstack)){
            pPlayer.level().playSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 0.2F, ((pPlayer.getRandom().nextFloat() - pPlayer.getRandom().nextFloat()) * 0.7F + 1.0F) * 2.0F);
            flag = true;
        }else{
            ItemEntity itementity = pPlayer.drop(itemstack, false);
            if(itementity != null){
                itementity.setNoPickUpDelay();
                itementity.setTarget(pPlayer.getUUID());
            }
        }

        return flag;
    }
}