package com.idark.valoria.registries.block.entity;

import com.google.common.collect.*;
import com.idark.valoria.client.render.tile.*;
import com.idark.valoria.client.ui.menus.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.item.recipe.*;
import it.unimi.dsi.fastutil.objects.*;
import net.minecraft.core.*;
import net.minecraft.nbt.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraft.server.level.*;
import net.minecraft.tags.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.*;

import javax.annotation.*;
import java.util.*;

public class KilnBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer, RecipeHolder, StackedContentsCompatible, TickableBlockEntity{
    private static final int[] SLOTS_FOR_UP = new int[]{0};
    private static final int[] SLOTS_FOR_DOWN = new int[]{2, 1};
    private static final int[] SLOTS_FOR_SIDES = new int[]{1};
    private final RecipeType<? extends AbstractCookingRecipe> recipeType;
    protected NonNullList<ItemStack> items = NonNullList.withSize(3, ItemStack.EMPTY);
    int litTime;
    int litDuration;
    int cookingProgress;
    int cookingTotalTime;
    protected final ContainerData dataAccess = new ContainerData(){
        public int get(int p_58431_){
            return switch(p_58431_){
                case 0 -> KilnBlockEntity.this.litTime;
                case 1 -> KilnBlockEntity.this.litDuration;
                case 2 -> KilnBlockEntity.this.cookingProgress;
                case 3 -> KilnBlockEntity.this.cookingTotalTime;
                default -> 0;
            };
        }

        public void set(int p_58433_, int p_58434_){
            switch(p_58433_){
                case 0:
                    KilnBlockEntity.this.litTime = p_58434_;
                    break;
                case 1:
                    KilnBlockEntity.this.litDuration = p_58434_;
                    break;
                case 2:
                    KilnBlockEntity.this.cookingProgress = p_58434_;
                    break;
                case 3:
                    KilnBlockEntity.this.cookingTotalTime = p_58434_;
            }

        }

        public int getCount(){
            return 4;
        }
    };

    private final Object2IntOpenHashMap<ResourceLocation> recipesUsed = new Object2IntOpenHashMap<>();
    private final RecipeManager.CachedCheck<Container, ? extends AbstractCookingRecipe> quickCheck;

    public KilnBlockEntity(BlockPos pPos, BlockState pBlockState){
        super(BlockEntitiesRegistry.KILN.get(), pPos, pBlockState);
        this.quickCheck = RecipeManager.createCheck(KilnRecipe.Type.INSTANCE);
        this.recipeType = KilnRecipe.Type.INSTANCE;
    }

    protected Component getDefaultName() {
        return Component.translatable("menu.valoria.kiln");
    }

    protected AbstractContainerMenu createMenu(int pId, Inventory pPlayer) {
        return new KilnMenu(pId, pPlayer, this, this.dataAccess);
    }

    private static boolean isNeverAFurnaceFuel(Item pItem){
        return pItem.builtInRegistryHolder().is(ItemTags.NON_FLAMMABLE_WOOD);
    }

    public void load(CompoundTag pTag){
        super.load(pTag);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(pTag, this.items);
        this.litTime = pTag.getInt("BurnTime");
        this.cookingProgress = pTag.getInt("CookTime");
        this.cookingTotalTime = pTag.getInt("CookTimeTotal");
        this.litDuration = this.getBurnDuration(this.items.get(1));
        CompoundTag compoundtag = pTag.getCompound("RecipesUsed");

        for(String s : compoundtag.getAllKeys()){
            this.recipesUsed.put(new ResourceLocation(s), compoundtag.getInt(s));
        }

    }

    protected void saveAdditional(CompoundTag pTag){
        super.saveAdditional(pTag);
        pTag.putInt("BurnTime", this.litTime);
        pTag.putInt("CookTime", this.cookingProgress);
        pTag.putInt("CookTimeTotal", this.cookingTotalTime);
        ContainerHelper.saveAllItems(pTag, this.items);
        CompoundTag compoundtag = new CompoundTag();
        this.recipesUsed.forEach((p_187449_, p_187450_) -> {
            compoundtag.putInt(p_187449_.toString(), p_187450_);
        });
        pTag.put("RecipesUsed", compoundtag);
    }

    @Override
    public void tick(){
        Level pLevel = this.getLevel();
        var pBlockEntity = this;
        BlockState pState = this.getBlockState();
        BlockPos pPos = this.worldPosition;
        boolean flag = pBlockEntity.isLit();
        boolean flag1 = false;
        if(pBlockEntity.isLit()){
            --pBlockEntity.litTime;
        }

        ItemStack itemstack = pBlockEntity.items.get(1);
        boolean flag2 = !pBlockEntity.items.get(0).isEmpty();
        boolean flag3 = !itemstack.isEmpty();
        if(pBlockEntity.isLit() || flag3 && flag2){
            Recipe<?> recipe;
            if(flag2){
                recipe = pBlockEntity.quickCheck.getRecipeFor(pBlockEntity, pLevel).orElse(null);
            }else{
                recipe = null;
            }

            int i = pBlockEntity.getMaxStackSize();
            if(!pBlockEntity.isLit() && pBlockEntity.canBurn(pLevel.registryAccess(), recipe, pBlockEntity.items, i)){
                pBlockEntity.litTime = pBlockEntity.getBurnDuration(itemstack);
                pBlockEntity.litDuration = pBlockEntity.litTime;
                if(pBlockEntity.isLit()){
                    flag1 = true;
                    if(itemstack.hasCraftingRemainingItem())
                        pBlockEntity.items.set(1, itemstack.getCraftingRemainingItem());
                    else if(flag3){
                        Item item = itemstack.getItem();
                        itemstack.shrink(1);
                        if(itemstack.isEmpty()){
                            pBlockEntity.items.set(1, itemstack.getCraftingRemainingItem());
                        }
                    }
                }
            }

            if(pBlockEntity.isLit() && pBlockEntity.canBurn(pLevel.registryAccess(), recipe, pBlockEntity.items, i)){
                ++pBlockEntity.cookingProgress;
                if(pBlockEntity.cookingProgress == pBlockEntity.cookingTotalTime){
                    pBlockEntity.cookingProgress = 0;
                    pBlockEntity.cookingTotalTime = getTotalCookTime(pLevel, pBlockEntity);
                    if(pBlockEntity.burn(pLevel.registryAccess(), recipe, pBlockEntity.items, i)){
                        pBlockEntity.setRecipeUsed(recipe);
                    }

                    flag1 = true;
                }
            }else{
                pBlockEntity.cookingProgress = 0;
            }
        }else if(!pBlockEntity.isLit() && pBlockEntity.cookingProgress > 0){
            pBlockEntity.cookingProgress = Mth.clamp(pBlockEntity.cookingProgress - 2, 0, pBlockEntity.cookingTotalTime);
        }

        if(flag != pBlockEntity.isLit()){
            flag1 = true;
            pState = pState.setValue(AbstractFurnaceBlock.LIT, Boolean.valueOf(pBlockEntity.isLit()));
            pLevel.setBlock(pPos, pState, 3);
        }

        if(flag1){
            setChanged(pLevel, pPos, pState);
        }
    }

    private boolean isLit() {
        return this.litTime > 0;
    }

    private boolean canBurn(RegistryAccess pRegistryAccess, @javax.annotation.Nullable Recipe<?> pRecipe, NonNullList<ItemStack> pInventory, int pMaxStackSize){
        if(!pInventory.get(0).isEmpty() && pRecipe != null){
            ItemStack itemstack = ((Recipe<WorldlyContainer>)pRecipe).assemble(this, pRegistryAccess);
            if(itemstack.isEmpty()){
                return false;
            }else{
                ItemStack itemstack1 = pInventory.get(2);
                if(itemstack1.isEmpty()){
                    return true;
                }else if(!ItemStack.isSameItem(itemstack1, itemstack)){
                    return false;
                }else if(itemstack1.getCount() + itemstack.getCount() <= pMaxStackSize && itemstack1.getCount() + itemstack.getCount() <= itemstack1.getMaxStackSize()){ // Forge fix: make furnace respect stack sizes in furnace recipes
                    return true;
                }else{
                    return itemstack1.getCount() + itemstack.getCount() <= itemstack.getMaxStackSize(); // Forge fix: make furnace respect stack sizes in furnace recipes
                }
            }
        }else{
            return false;
        }
    }

    private boolean burn(RegistryAccess pRegistryAccess, @javax.annotation.Nullable Recipe<?> pRecipe, NonNullList<ItemStack> pInventory, int pMaxStackSize){
        if(pRecipe != null && this.canBurn(pRegistryAccess, pRecipe, pInventory, pMaxStackSize)){
            ItemStack itemstack = pInventory.get(0);
            ItemStack itemstack1 = ((Recipe<WorldlyContainer>)pRecipe).assemble(this, pRegistryAccess);
            ItemStack itemstack2 = pInventory.get(2);
            if(itemstack2.isEmpty()){
                pInventory.set(2, itemstack1.copy());
            }else if(itemstack2.is(itemstack1.getItem())){
                itemstack2.grow(itemstack1.getCount());
            }

            if(itemstack.is(Blocks.WET_SPONGE.asItem()) && !pInventory.get(1).isEmpty() && pInventory.get(1).is(Items.BUCKET)){
                pInventory.set(1, new ItemStack(Items.WATER_BUCKET));
            }

            itemstack.shrink(1);
            return true;
        }else{
            return false;
        }
    }

    protected int getBurnDuration(ItemStack pFuel){
        if(pFuel.isEmpty()){
            return 0;
        }else{
            Item item = pFuel.getItem();
            return net.minecraftforge.common.ForgeHooks.getBurnTime(pFuel, this.recipeType);
        }
    }

    private static int getTotalCookTime(Level pLevel, KilnBlockEntity pBlockEntity){
        return pBlockEntity.quickCheck.getRecipeFor(pBlockEntity, pLevel).map(AbstractCookingRecipe::getCookingTime).orElse(200);
    }

    public static boolean isFuel(ItemStack pStack){
        return net.minecraftforge.common.ForgeHooks.getBurnTime(pStack, null) > 0;
    }

    public int[] getSlotsForFace(Direction pSide){
        if(pSide == Direction.DOWN){
            return SLOTS_FOR_DOWN;
        }else{
            return pSide == Direction.UP ? SLOTS_FOR_UP : SLOTS_FOR_SIDES;
        }
    }

    /**
     * Returns {@code true} if automation can insert the given item in the given slot from the given side.
     */
    public boolean canPlaceItemThroughFace(int pIndex, ItemStack pItemStack, @javax.annotation.Nullable Direction pDirection){
        return this.canPlaceItem(pIndex, pItemStack);
    }

    /**
     * Returns {@code true} if automation can extract the given item in the given slot from the given side.
     */
    public boolean canTakeItemThroughFace(int pIndex, ItemStack pStack, Direction pDirection){
        if(pDirection == Direction.DOWN && pIndex == 1){
            return pStack.is(Items.WATER_BUCKET) || pStack.is(Items.BUCKET);
        }else{
            return true;
        }
    }

    /**
     * Returns the number of slots in the inventory.
     */
    public int getContainerSize(){
        return this.items.size();
    }

    public boolean isEmpty(){
        for(ItemStack itemstack : this.items){
            if(!itemstack.isEmpty()){
                return false;
            }
        }

        return true;
    }

    /**
     * Returns the stack in the given slot.
     */
    public ItemStack getItem(int pIndex){
        return this.items.get(pIndex);
    }

    /**
     * Removes up to a specified number of items from an inventory slot and returns them in a new stack.
     */
    public ItemStack removeItem(int pIndex, int pCount){
        return ContainerHelper.removeItem(this.items, pIndex, pCount);
    }

    /**
     * Removes a stack from the given slot and returns it.
     */
    public ItemStack removeItemNoUpdate(int pIndex){
        return ContainerHelper.takeItem(this.items, pIndex);
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     */
    public void setItem(int pIndex, ItemStack pStack){
        ItemStack itemstack = this.items.get(pIndex);
        boolean flag = !pStack.isEmpty() && ItemStack.isSameItemSameTags(itemstack, pStack);
        this.items.set(pIndex, pStack);
        if(pStack.getCount() > this.getMaxStackSize()){
            pStack.setCount(this.getMaxStackSize());
        }

        if(pIndex == 0 && !flag){
            this.cookingTotalTime = getTotalCookTime(this.level, this);
            this.cookingProgress = 0;
            this.setChanged();
        }
    }

    /**
     * Don't rename this method to canInteractWith due to conflicts with Container
     */
    public boolean stillValid(Player pPlayer){
        return Container.stillValidBlockEntity(this, pPlayer);
    }

    /**
     * Returns {@code true} if automation is allowed to insert the given stack (ignoring stack size) into the given slot.
     * For guis use Slot.isItemValid
     */
    public boolean canPlaceItem(int pIndex, ItemStack pStack){
        if(pIndex == 2){
            return false;
        }else if(pIndex != 1){
            return true;
        }else{
            ItemStack itemstack = this.items.get(1);
            return net.minecraftforge.common.ForgeHooks.getBurnTime(pStack, this.recipeType) > 0 || pStack.is(Items.BUCKET) && !itemstack.is(Items.BUCKET);
        }
    }

    public void clearContent(){
        this.items.clear();
    }

    public void setRecipeUsed(@javax.annotation.Nullable Recipe<?> pRecipe){
        if(pRecipe != null){
            ResourceLocation resourcelocation = pRecipe.getId();
            this.recipesUsed.addTo(resourcelocation, 1);
        }

    }

    @javax.annotation.Nullable
    public Recipe<?> getRecipeUsed(){
        return null;
    }

    public void awardUsedRecipes(Player pPlayer, List<ItemStack> pItems){
    }

    public void awardUsedRecipesAndPopExperience(ServerPlayer pPlayer){
        List<Recipe<?>> list = this.getRecipesToAwardAndPopExperience(pPlayer.serverLevel(), pPlayer.position());
        pPlayer.awardRecipes(list);

        for(Recipe<?> recipe : list){
            if(recipe != null){
                pPlayer.triggerRecipeCrafted(recipe, this.items);
            }
        }

        this.recipesUsed.clear();
    }

    public List<Recipe<?>> getRecipesToAwardAndPopExperience(ServerLevel pLevel, Vec3 pPopVec){
        List<Recipe<?>> list = Lists.newArrayList();

        for(Object2IntMap.Entry<ResourceLocation> entry : this.recipesUsed.object2IntEntrySet()){
            pLevel.getRecipeManager().byKey(entry.getKey()).ifPresent((p_155023_) -> {
                list.add(p_155023_);
                createExperience(pLevel, pPopVec, entry.getIntValue(), ((AbstractCookingRecipe)p_155023_).getExperience());
            });
        }

        return list;
    }

    private static void createExperience(ServerLevel pLevel, Vec3 pPopVec, int pRecipeIndex, float pExperience){
        int i = Mth.floor((float)pRecipeIndex * pExperience);
        float f = Mth.frac((float)pRecipeIndex * pExperience);
        if(f != 0.0F && Math.random() < (double)f){
            ++i;
        }

        ExperienceOrb.award(pLevel, pPopVec, i);
    }

    public void fillStackedContents(StackedContents pHelper){
        for(ItemStack itemstack : this.items){
            pHelper.accountStack(itemstack);
        }
    }

    net.minecraftforge.common.util.LazyOptional<? extends net.minecraftforge.items.IItemHandler>[] handlers = net.minecraftforge.items.wrapper.SidedInvWrapper.create(this, Direction.UP, Direction.DOWN, Direction.NORTH);
    @Override
    public <T> net.minecraftforge.common.util.LazyOptional<T> getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @Nullable Direction facing){
        if(!this.remove && facing != null && capability == net.minecraftforge.common.capabilities.ForgeCapabilities.ITEM_HANDLER){
            if(facing == Direction.UP)
                return handlers[0].cast();
            else if(facing == Direction.DOWN)
                return handlers[1].cast();
            else
                return handlers[2].cast();
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void invalidateCaps(){
        super.invalidateCaps();
        for(net.minecraftforge.common.util.LazyOptional<? extends net.minecraftforge.items.IItemHandler> handler : handlers) handler.invalidate();
    }

    @Override
    public void reviveCaps(){
        super.reviveCaps();
        this.handlers = net.minecraftforge.items.wrapper.SidedInvWrapper.create(this, Direction.UP, Direction.DOWN, Direction.NORTH);
    }
}