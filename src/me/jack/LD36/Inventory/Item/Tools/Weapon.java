package me.jack.LD36.Inventory.Item.Tools;

/**
 * Created by Jack on 28/08/2016.
 */
public class Weapon extends Tool{

    private int damage;
    private long cooldown;
    public Weapon(int x, int y, int id, ItemMaterial material, String name, String desc, ToolType type,int dmg,long cooldown) {
        super(x, y, id, material, name, desc, type);
        this.damage = dmg;
        this.cooldown = cooldown;
    }

    public int getDamage() {
        return damage;
    }
}
