package me.jack.LD36.Inventory.Item;

/**
 * Created by Jack on 27/08/2016.
 */
public class CraftingRequirements {

    private ItemStack[] stack;


    public CraftingRequirements(ItemStack[] stack){
        this.stack = stack;
    }

    public ItemStack[] getStack() {
        return stack;
    }
}
