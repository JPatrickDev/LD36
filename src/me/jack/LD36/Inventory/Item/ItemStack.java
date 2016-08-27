package me.jack.LD36.Inventory.Item;

/**
 * Created by Jack on 27/08/2016.
 */
public class ItemStack {
    public static final int MAX_SIZE = 48;

    private int stackSize;
    private Item item;


    public ItemStack(int stackSize, Item item) {
        this.stackSize = stackSize;
        this.item = item;
    }

    public int getStackSize() {
        return stackSize;
    }

    public Item getItem() {
        return item;
    }

    public boolean remove(){
        if(stackSize > 0){
            stackSize--;
            return true;
        }
        return false;
    }

    public int add(int count){
        if(stackSize+count <= MAX_SIZE){
            stackSize+=count;
            return -1;
        }else{
            int canAdd = MAX_SIZE - stackSize;
            stackSize += canAdd;
            return count - canAdd;
        }
    }
}
