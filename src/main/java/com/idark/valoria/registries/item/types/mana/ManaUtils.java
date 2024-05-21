package com.idark.valoria.registries.item.types.mana;

import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import top.theillusivec4.curios.api.*;

import java.util.*;

@SuppressWarnings("removal")
public class ManaUtils{

    public static int getAddManaRemain(int current, int mana, int max){
        int remain = 0;
        if(max < (current + mana)){
            remain = (current + mana) - max;
        }

        return remain;
    }

    public static int getRemoveManaRemain(int current, int mana){
        int remain = 0;
        if(0 > (current - mana)){
            remain = -(current - mana);
        }
        return remain;
    }

    public static boolean canAddMana(int current, int mana, int max){
        return (max >= (current + mana));
    }

    public static boolean canRemoveMana(int current, int mana){
        return (0 <= (current - mana));
    }

    public static List<ItemStack> getManaItems(Player player){
        List<ItemStack> items = new ArrayList<>();
        items.addAll(getManaItemsInventory(player));
        items.addAll(getManaItemsCurios(player));

        return items;
    }

    public static List<ItemStack> getManaItemsActive(Player player){
        List<ItemStack> items = new ArrayList<>();
        items.addAll(getManaItemsHotbar(player));
        items.addAll(getManaItemsCurios(player));

        return items;
    }

    public static List<ItemStack> getManaItemsInventory(Player player){
        List<ItemStack> items = new ArrayList<>();
        for(int i = 0; i < player.getInventory().getContainerSize(); i++){
            if(player.getInventory().getItem(i).getItem() instanceof IManaItem){
                items.add(player.getInventory().getItem(i));
            }
        }

        return items;
    }


    public static List<ItemStack> getManaItemsHotbar(Player player){
        List<ItemStack> items = new ArrayList<>();
        for(int i = 0; i < 9; i++){
            if(player.getInventory().getItem(i).getItem() instanceof IManaItem){
                items.add(player.getInventory().getItem(i));
            }
        }

        return items;
    }

    public static List<ItemStack> getManaItemsCurios(Player player){
        List<ItemStack> items = new ArrayList<>();
        List<SlotResult> curioSlots = CuriosApi.getCuriosHelper().findCurios(player, (i) -> true);
        for(SlotResult slot : curioSlots){
            if(slot.stack().getItem() instanceof IManaItem){
                items.add(slot.stack());
            }
        }

        return items;
    }

    public static List<ItemStack> getManaItemsOff(List<ItemStack> items){
        List<ItemStack> filteredItems = new ArrayList<>();
        for(ItemStack stack : items){
            if(stack.getItem() instanceof IManaItem manaItem){
                if(manaItem.getManaItemType() == ManaItemType.OFF){
                    filteredItems.add(stack);
                }
            }
        }

        return filteredItems;
    }

    public static List<ItemStack> getManaItemsNone(List<ItemStack> items){
        List<ItemStack> filteredItems = new ArrayList<>();
        for(ItemStack stack : items){
            if(stack.getItem() instanceof IManaItem manaItem){
                if(manaItem.getManaItemType() == ManaItemType.NONE){
                    filteredItems.add(stack);
                }
            }
        }

        return filteredItems;
    }

    public static List<ItemStack> getManaItemsUsing(List<ItemStack> items){
        List<ItemStack> filteredItems = new ArrayList<>();
        for(ItemStack stack : items){
            if(stack.getItem() instanceof IManaItem manaItem){
                if(manaItem.getManaItemType() == ManaItemType.USING){
                    filteredItems.add(stack);
                }
            }
        }

        return filteredItems;
    }

    public static List<ItemStack> getManaItemsStorage(List<ItemStack> items){
        List<ItemStack> filteredItems = new ArrayList<>();
        for(ItemStack stack : items){
            if(stack.getItem() instanceof IManaItem manaItem){
                if(manaItem.getManaItemType() == ManaItemType.STORAGE){
                    filteredItems.add(stack);
                }
            }
        }

        return filteredItems;
    }

    public static List<ItemStack> getManaItemsNoneAndStorage(List<ItemStack> items){
        List<ItemStack> filteredItems = new ArrayList<>();
        for(ItemStack stack : items){
            if(stack.getItem() instanceof IManaItem manaItem){
                if(manaItem.getManaItemType() == ManaItemType.NONE || manaItem.getManaItemType() == ManaItemType.STORAGE){
                    filteredItems.add(stack);
                }
            }
        }

        return filteredItems;
    }

    public static int getManaInItems(List<ItemStack> items){
        int mana = 0;
        for(ItemStack stack : items){
            ManaItemUtils.existMana(stack);
            mana = mana + ManaItemUtils.getMana(stack);
        }

        return mana;
    }

    public static int getMaxManaInItems(List<ItemStack> items){
        int maxMana = 0;
        for(ItemStack stack : items){
            if(stack.getItem() instanceof IManaItem manaItem){
                maxMana = maxMana + manaItem.getMaxMana();
            }
        }

        return maxMana;
    }

    public static void removeManaFromManaItems(List<ItemStack> items, int mana){
        for(ItemStack stack : items){
            ManaItemUtils.existMana(stack);
            int remain = ManaItemUtils.getRemoveManaRemain(stack, mana);
            if(ManaItemUtils.canRemoveMana(stack, mana)){
                ManaItemUtils.removeMana(stack, mana);
            }
            if(remain > 0){
                mana = remain;
                ManaItemUtils.setMana(stack, 0);
            }
        }
    }
}
