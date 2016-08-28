package me.jack.LD36.Inventory.Item.Tools;

import me.jack.LD36.Inventory.Item.Item;

/**
 * Created by Jack on 28/08/2016.
 */
public class ItemWoodSword extends Weapon{

    public ItemWoodSword() {
        super(0, 2, 7, ItemMaterial.WOOD, "Wooden Sword", "A basic sword.", ToolType.SWORD, 10, 100);
    }

}
