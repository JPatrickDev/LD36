package me.jack.LD36.Inventory.Item.Shelters;

import me.jack.LD36.Inventory.Inventory;
import me.jack.LD36.Inventory.Item.Item;

/**
 * Created by Jack on 28/08/2016.
 */
public class Shelter extends Item{

    boolean furnace;
    public int size;
    public Inventory inv = null;
    public Shelter(int x, int y, int id, String name, String description,boolean furnace, int size) {
        super(x, y, id, name, description);
        this.furnace = furnace;
        this.size = size;
        inv = new Inventory();
        inv.MAX_SIZE = size;
    }

    public boolean isFurnace() {
        return furnace;
    }

    public int getSize() {
        return size;
    }
}
