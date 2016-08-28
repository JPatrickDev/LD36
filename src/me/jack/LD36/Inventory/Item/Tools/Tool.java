package me.jack.LD36.Inventory.Item.Tools;

import me.jack.LD36.Inventory.Item.Item;

/**
 * Created by Jack on 27/08/2016.
 */
public class Tool extends Item {

    private ItemMaterial material;
    private ToolType type;

    public Tool(int x, int y, int id, ItemMaterial material, String name, String desc,ToolType type) {
        super(x, y, id, name, desc);
        this.material = material;
        this.type = type;
    }

    public ItemMaterial getMaterial() {
        return material;
    }

    public ToolType getType() {
        return type;
    }
}
