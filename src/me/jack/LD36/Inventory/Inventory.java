package me.jack.LD36.Inventory;

import me.jack.LD36.Inventory.Item.Item;
import me.jack.LD36.Inventory.Item.ItemStack;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Jack on 27/08/2016.
 */
public class Inventory {


    public static final int MAX_SIZE = 18;


    private ItemStack[] items = new ItemStack[MAX_SIZE];

    private int itemInHand = 0;

    public boolean addItem(Item i, int count) {
        for (ItemStack stack : items) {
            if (stack == null) continue;
            if (stack.getItem().getId() == i.getId()) {
                int added = stack.add(count);
                if (added == -1) {
                    System.out.println("All added");
                    return true;
                } else {
                    System.out.println("Could not add " + added);
                    count = added;
                }
            }
        }

        ItemStack stack = new ItemStack(count, i);
        for (int ii = 0; ii != MAX_SIZE; ii++) {
            if (items[ii] == null) {
                items[ii] = stack;
                break;
            }
        }
        return true;
    }


    public ItemStack[] getItems() {
        return items;
    }

    public boolean contains(ItemStack stack) {
        int found = 0;
        for (int ii = 0; ii != MAX_SIZE; ii++) {
            if (items[ii] != null) {
                ItemStack invStack = items[ii];
                if (invStack.getItem().getId() == stack.getItem().getId()) {
                    found += invStack.getStackSize();
                }
            }
        }
        if(found >= stack.getStackSize()){
            return true;
        }
        return false;
    }

    //only call once you've checked using contain
    public void remove(ItemStack stack) {
        int toRemove = stack.getStackSize();
        int remaining = toRemove;
        for (int ii = 0; ii != MAX_SIZE; ii++) {
            if(remaining == 0)break;
            if (items[ii] != null) {
                ItemStack invStack = items[ii];
                if (stack.getItem().getId() == invStack.getItem().getId()) {
                    int removing = stack.getStackSize();
                    if(stack.getStackSize()  > invStack.getStackSize()){
                        removing = invStack.getStackSize();
                    }
                    invStack.remove(removing);
                    if(invStack.getStackSize() <= 0){
                        items[ii] = null;
                    }
                    remaining = toRemove - removing;
                    if (remaining < 0)
                        remaining = 0;
                    toRemove = remaining;
                }
            }
        }
    }


    public int getItemInHand() {
        return itemInHand;
    }

    public void setItemInHand(int itemInHand) {
        this.itemInHand = itemInHand;
    }

    public void removeItemStack(int dropping) {
        items[dropping] = null;
    }
}
