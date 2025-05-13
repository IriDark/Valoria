package com.idark.valoria.api.unlockable.types;

import com.idark.valoria.api.unlockable.*;
import com.idark.valoria.registries.*;
import net.minecraft.resources.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.world.entity.item.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.storage.loot.*;
import net.minecraft.world.level.storage.loot.parameters.*;

public class Unlockable {
    private ResourceLocation[] loot;
    private ItemStack[] items;
    private int experience;
    public String id;
    public Item icon;
    public final boolean randomObtainable;

    public Unlockable(String id){
        this(ItemsRegistry.page.get(), id, true);
    }

    public Unlockable(Item icon, String id){
        this(icon, id, true);
    }

    public Unlockable(String id, boolean rndObtain){
        this(ItemsRegistry.page.get(), id, rndObtain);
    }

    public Unlockable(Item icon, String id, boolean rndObtain){
        this.icon = icon;
        this.id = id;
        this.randomObtainable = rndObtain;
    }

    @Override
    public String toString(){
        return getClass().getSimpleName() + "{ id='" + id + '\'' + '}';
    }

    public boolean canObtain(Player player) {
        return false;
    }

    public boolean canObtainByRandom() {
        return randomObtainable;
    }

    public boolean hasAwards() {
        return items != null || loot != null || experience > 0;
    }

    public boolean tick(ServerPlayer player) {
        if (canObtain(player)) {
            UnlockUtils.add(player, this);
            return true;
        }

        return false;
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
                    addReward(pPlayer, itemstack, flag);
                    flag = true;
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

//    public static class Serializer {
//        public Serializer() {
//        }
//
//        public Unlockable deserialize(JsonObject object, JsonDeserializationContext pContext){
//            String id = GsonHelper.getAsObject(object, "unlockable", pContext, String.class);
//            return Unlockables.getUnlockable(id);
//        }
//
//        public JsonObject serialize(JsonObject json, Unlockable unlock, JsonSerializationContext pContext) {
//            json.add("unlockable", pContext.serialize(unlock.id));
//            return json;
//        }
//    }
}