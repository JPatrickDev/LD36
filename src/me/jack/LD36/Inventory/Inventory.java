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


    public boolean addItem(Item i, int count){
        for(ItemStack stack : items){
            if(stack == null)continue;
            if(stack.getItem().getId() == i.getId()){
                int added = stack.add(count);
                if(added == -1){
                    System.out.println("All added");
                    return true;
                }else{
                    System.out.println("Could not add " + added);
                    count = added;
                }
            }
        }

        ItemStack stack = new ItemStack(count,i);
        for(int ii = 0;ii!= MAX_SIZE;ii++){
            if(items[ii] == null){
                items[ii] = stack;
                break;
            }
        }
        return true;
    }


    public ItemStack[] getItems() {
        return items;
    }
}
