package me.jack.LD36.Inventory.Item.Tools;

import me.jack.LD36.Inventory.Item.Item;

/**
 * Created by Jack on 27/08/2016.
 */
public class Tool extends Item {

    private ItemMaterial material;

    public Tool(int x, int y, int id, ItemMaterial material, String name, String desc) {
        super(x, y, id, name, desc);
        this.material = material;
    }

    public ItemMaterial getMaterial() {
        return material;
    }
}
